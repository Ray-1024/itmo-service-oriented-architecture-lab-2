package soa.collectionservice.collectionservice.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import soa.collectionservice.collectionservice.exception.InternalServerException;
import soa.collectionservice.collectionservice.exception.InvalidQueryParamException;
import soa.collectionservice.collectionservice.model.dto.ErrorDto;
import soa.collectionservice.collectionservice.model.dto.GroupInfoDto;
import soa.collectionservice.collectionservice.model.dto.InvalidParamDto;
import soa.collectionservice.collectionservice.model.dto.RouteDto;
import soa.collectionservice.collectionservice.model.entity.RouteEntity;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RouteRepository {
    @PersistenceContext(unitName = "studs")
    private EntityManager entityManager;

    @Transactional
    public List<RouteEntity> getAll(int page, int size, String sortBy, boolean ascending, String filterBy, String filterPredicate, String filterValue) {
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

    @Transactional
    public RouteEntity getById(Long id) {
        try {
            RouteEntity entity = entityManager.find(RouteEntity.class, id);
            if (entity == null) {
                throw InvalidQueryParamException.builder()
                        .invalidParams(List.of(
                                InvalidParamDto.builder()
                                        .paramName("id")
                                        .message("Route with this id doesn't exists")
                                        .build()
                        ))
                        .error(ErrorDto.builder()
                                .message("Route with id=" + id + "doesn't exists")
                                .time(Instant.now())
                                .build())
                        .build();
            }
            return entity;
        } catch (InvalidQueryParamException e) {
            throw e;
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    @Transactional
    public RouteEntity create(RouteDto route) {
        try {
            route.setId(null);
            route.setCreationDate(new Date());
            RouteEntity entity = RouteEntity.fromDto(route);
            entity.getCoordinates().setId(null);
            entity.getTo().setId(null);
            if (entity.getFrom() != null) {
                entity.getFrom().setId(null);
            }
            entityManager.persist(entity.getCoordinates());
            if (entity.getTo() != null) {
                entityManager.persist(entity.getFrom());
            }
            entityManager.persist(entity.getTo());
            entityManager.persist(entity);
            return getById(entity.getId());
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    @Transactional
    public RouteEntity update(RouteDto route) {

        try {
            RouteEntity entity = RouteEntity.fromDto(route);
            entityManager.merge(entity);
            return entity;
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    @Transactional
    public void deleteById(long routeId) {

        try {
            entityManager.remove(getById(routeId));
        } catch (Exception e) {
            throw InternalServerException.builder()
                    .error(ErrorDto.builder()
                            .message(e.getMessage())
                            .time(Instant.now())
                            .build())
                    .build();
        }
    }

    @Transactional
    public List<GroupInfoDto> getGroupsInfo() {
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
                            .count((int) (((Long) objects[1]).longValue()))
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

    @Transactional
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


        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<RouteEntity> root = criteriaQuery.from(RouteEntity.class);

            criteriaQuery.select(criteriaBuilder.count(root.get("distance")))
                    .where(criteriaBuilder.equal(root.get("distance"), distance));

            return entityManager.createQuery(criteriaQuery).getResultList().get(0);
        } catch (InvalidQueryParamException e) {
            throw e;
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
