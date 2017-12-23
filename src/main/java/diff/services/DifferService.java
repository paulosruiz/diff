package diff.services;

import java.util.List;

import org.springframework.stereotype.Service;

import diff.domain.DifferObject;
import diff.util.DiffSide;

/**
 * Differ service interface
 * 
 * @author paulo.almeida.junior
 *
 */
@Service
public interface DifferService {

	public DifferObject defineData(Long id, String left, DiffSide side);

	public DifferObject getDiff(Long id);

	public List<DifferObject> retrieveAll();

}
