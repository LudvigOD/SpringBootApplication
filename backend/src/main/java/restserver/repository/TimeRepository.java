package restserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import restserver.entity.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {

  /**
   * Get all times for a specific start number.
   * 
   * @param startNbr
   * @return
   */
  public List<Time> findByStartNbr(String startNbr);

  /**
   * Get all times for a specific start number.
   * 
   * @param station
   * @return all entities containing station
   */
  public List<Time> findByStation(String station);

}
