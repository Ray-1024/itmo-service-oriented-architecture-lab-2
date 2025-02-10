package soa.navigatorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soa.navigatorservice.model.entity.LocationEntity;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByName(String name);
}
