package diff.services;

import java.util.Base64;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diff.domain.DifferObject;
import diff.repositories.DifferRepository;

@Service
public class DifferServiceImpl implements DifferService {

	final static Logger LOG = Logger.getLogger(DifferServiceImpl.class);

	@Autowired
	private DifferRepository repository;

	private DifferObject getDiffer(final Long id) {
		DifferObject newDiff = null;
		try {
			if (id != null) {
				newDiff = repository.findOne(id);
				if (newDiff != null) {
					LOG.info("Differ was found");
				}
			}
		} catch (Exception e) {
			LOG.error("Error during getDiffer", e);
		}
		return newDiff;
	}

	private DifferObject createDiffer(final Long id) {
		DifferObject newDiff = null;
		try {
			if (id != null)

				newDiff = new DifferObject(id);
			repository.save(newDiff);
			LOG.info("Differ created");
			LOG.debug(newDiff.toString());

		} catch (

		Exception e) {
			LOG.error("Error during createDiffer", e);
		}
		return newDiff;
	}

	@Override
	public DifferObject setLeftDiff(final Long id, final String left) {
		/*
		 * if (id != null && !id.isEmpty()) { return
		 * repository.findByIdAndAdminAndAdminOnly(id, null, false); }
		 */
		LOG.info("setLeftDiff method started");
		if (id != null) {
			repository.findOne(id);
		}
		return null;
	}

	@Override
	public DifferObject setRightDiff(Long id, String right) {
		/*
		 * if (id != null && !id.isEmpty()) { return
		 * repository.findByIdAndAdminAndAdminOnly(id, null, false); }
		 */
		// return repository.findByIdAndAdminAndAdminOnly(id, admin, true);

		LOG.info("setRightDiff method started");
		if (id != null) {
			repository.findOne(id);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DifferObject getDiff(Long id) {
		LOG.info("getDiff method started");

		DifferObject differToCompare = getDiff(id);
		if (differToCompare != null) {
			final byte[] leftDecoded = Base64.getDecoder().decode(differToCompare.getLeft());

			final byte[] rightDecode = Base64.getDecoder().decode(differToCompare.getRight());
			
			LOG.trace("Starting comparing");
			LOG.debug("Comparing: " + differToCompare.toString());
			//TODO tratar sem left ou right
			if(leftDecoded.length != rightDecode.length) {
				
			}else {
				
			}
		}
		// TODO Auto-generated method stub
		return null;
	}
}
