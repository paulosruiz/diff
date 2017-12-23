package diff.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import diff.domain.DifferObject;

/**
 * Repository interface for DifferObjects
 * 
 * @author paulo.almeida.junior
 *
 */

public interface DifferRepository extends JpaRepository<DifferObject, Long> {
	/**
	 * Find DifferObject by Id
	 * @param id
	 * @return
	 */
	public DifferObject findById(String id);
}
