package diff.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import diff.domain.DifferObject;

/**
 * Repository interface for DifferObjects
 * @author paulo.almeida.junior
 *
 */

public interface DifferRepository extends JpaRepository<DifferObject, Long> {

}

