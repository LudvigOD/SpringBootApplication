package restserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import restserver.entity.Race;

public interface RaceRepository extends JpaRepository<Race, Integer> {
}
