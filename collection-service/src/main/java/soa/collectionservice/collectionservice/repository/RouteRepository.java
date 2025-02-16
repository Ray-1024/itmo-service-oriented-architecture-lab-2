package soa.collectionservice.collectionservice.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import soa.collectionservice.collectionservice.config.HibernateConfig;
import soa.collectionservice.collectionservice.exception.InternalServerException;
import soa.collectionservice.collectionservice.exception.InvalidQueryParamException;
import soa.collectionservice.collectionservice.model.dto.ErrorDto;
import soa.collectionservice.collectionservice.model.dto.GroupInfoDto;
import soa.collectionservice.collectionservice.model.dto.InvalidParamDto;
import soa.collectionservice.collectionservice.model.dto.RouteDto;
import soa.collectionservice.collectionservice.model.entity.RouteEntity;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RouteRepository {
    @Inject
    private HibernateConfig hibernateConfig;

    public List<RouteEntity> getAll(int page, int size, String sortBy, boolean ascending, String filterBy, String filterPredicate, String filterValue) {
        EntityManager entityManager = hibernateConfig.getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<RouteEntity> criteriaQuery = criteriaBuilder.createQuery(RouteEntity.class);
            Root<RouteEntity> root = criteriaQuery.from(RouteEntity.class);
            criteriaQuery.select(root);

            if (sortBy != null && !sortBy.isEmpty()) {
                String[] fields = sortBy.split("\\.");
                criteriaQuery.orderBy(ascending ?
                        criteriaBuilder.asc(fields.length == 1 ? root.get(sortBy) : root.get(fields[0]).get(fields[1])) :
                        criteriaBuilder.desc(fields.length == 1 ? root.get(sortBy) : root.get(fields[0]).get(fields[1]))
                );
            }
            if (filterBy != null && !filterBy.isEmpty()) {
                String[] fields = filterBy.split("\\.");
                switch (filterPredicate) {
                    case "=":
                        criteriaQuery.where(criteriaBuilder.equal(fields.length == 1 ? root.get(filterBy) : root.get(fields[0]).get(fields[1]), filterValue));
                        break;
                    case "!=":
                        criteriaQuery.where(criteriaBuilder.notEqual(fields.length == 1 ? root.get(filterBy) : root.get(fields[0]).get(fields[1]), filterValue));
                        break;
                    case "<=":
                        criteriaQuery.where(criteriaBuilder.lessThanOrEqualTo(fields.length == 1 ? root.get(filterBy) : root.get(fields[0]).get(fields[1]), filterValue));
                        break;
                    case ">=":
                        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(fields.length == 1 ? root.get(filterBy) : root.get(fields[0]).get(fields[1]), filterValue));
                        break;
                    case "<":
                        criteriaQuery.where(criteriaBuilder.lessThan(fields.length == 1 ? root.get(filterBy) : root.get(fields[0]).get(fields[1]), filterValue));
                        break;
                    case ">":
                        criteriaQuery.where(criteriaBuilder.greaterThan(fields.length == 1 ? root.get(filterBy) : root.get(fields[0]).get(fields[1]), filterValue));
                        break;
                }
            }

            TypedQuery<RouteEntity> query = entityManager.createQuery(criteriaQuery);

            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            return query.getResultList();
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    public RouteEntity getById(Long id) {
        EntityManager entityManager = hibernateConfig.getEntityManager();
        try {
            return entityManager.find(RouteEntity.class, id);
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    public RouteEntity create(RouteDto route) {
        EntityManager entityManager = hibernateConfig.getEntityManager();
        try {
            entityManager.persist(RouteEntity.fromDto(route));
            return getById(route.getId());
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    public RouteEntity update(RouteDto route) {
        EntityManager entityManager = hibernateConfig.getEntityManager();
        try {
            entityManager.persist(RouteEntity.fromDto(route));
            return getById(route.getId());
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    public void deleteById(long routeId) {
        EntityManager entityManager = hibernateConfig.getEntityManager();
        try {
            entityManager.detach(getById(routeId));
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    public List<GroupInfoDto> getGroupsInfo() {
        EntityManager entityManager = hibernateConfig.getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<RouteEntity> root = criteriaQuery.from(RouteEntity.class);

            criteriaQuery.multiselect(
                    root.get("name"),
                    criteriaBuilder.count(root.get("name"))
            ).groupBy(root.get("name"));

            return entityManager.createQuery(criteriaQuery).getResultList().stream().map(objects ->
                    GroupInfoDto.builder()
                            .name((String) objects[0])
                            .count((Integer) objects[1])
                            .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    public long getEqualDistanceRoutesCount(int distance) {
        if (distance <= 1) throw InvalidQueryParamException.builder()
                .invalidParams(List.of(InvalidParamDto.builder()
                        .paramName("distance")
                        .message("Distance can't be less or equal to 1")
                        .build()))
                .error(ErrorDto.builder()
                        .message("Invalid query param distance")
                        .time(Instant.now())
                        .build())
                .build();

        EntityManager entityManager = hibernateConfig.getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<RouteEntity> root = criteriaQuery.from(RouteEntity.class);

            criteriaQuery.select(criteriaBuilder.count(root.get("distance")))
                    .where(criteriaBuilder.equal(root.get("distance"), distance));

            return entityManager.createQuery(criteriaQuery).getResultList().get(0);
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }
}
