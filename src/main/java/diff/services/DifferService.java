package diff.services;

import java.util.List;

import org.springframework.stereotype.Service;

import diff.domain.DifferObject;
import diff.domain.DifferResponse;
import diff.util.DifferSide;

/**
 * Differ service interface
 * 
 * @author paulo.almeida.junior
 *
 */
@Service
public interface DifferService {

	public DifferObject defineData(final Long id, final String left, final DifferSide side);

	public DifferResponse compare(final Long id) throws Exception;

	public List<DifferObject> retrieveAll();

}
