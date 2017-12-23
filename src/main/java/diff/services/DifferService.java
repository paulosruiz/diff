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

	/**
	 * Set data on DifferSide position
	 * @param id
	 * @param left
	 * @param side
	 * @return
	 */
	public DifferObject defineData(final Long id, final String data, final DifferSide side);

	/**
	 * Compare DifferObject values
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DifferResponse compare(final Long id) throws Exception;

	/**
	 * Retrieve all DifferObjects
	 * @return
	 */
	public List<DifferObject> retrieveAll();

}
