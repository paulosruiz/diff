package diff.services;

import org.springframework.stereotype.Service;
import diff.domain.DifferObject;

/**
 * Differ service interface
 * 
 * @author paulo.almeida.junior
 *
 */
@Service
public interface DifferService {

	public DifferObject setLeftDiff(Long id, String left);

	public DifferObject setRightDiff(Long id, String right);

	public DifferObject getDiff(Long id);

}
