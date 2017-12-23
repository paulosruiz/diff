package diff.services;

import java.util.Base64;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diff.domain.DifferObject;
import diff.repositories.DifferRepository;
import diff.util.DiffSide;

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
	public DifferObject defineData(final Long id, final String data, final DiffSide side) {
		/*
		 * if (id != null && !id.isEmpty()) { return
		 * repository.findByIdAndAdminAndAdminOnly(id, null, false); }
		 */
		LOG.info("setLeftDiff method started");
		DifferObject differ = null;
		if (id != null && data != null && side != null) {
			differ = repository.findOne(id);
			if (differ == null) {
				differ = this.createDiffer(id);
			}
			if (side.equals(DiffSide.LEFT)) {
				differ.setLeft(data);
			} else {
				differ.setRight(data);
			}
			repository.save(differ);
		}

		return differ;
	}

	@Override
	public DifferObject defineRightData(Long id, String right) {
		/*
		 * if (id != null && !id.isEmpty()) { return
		 * repository.findByIdAndAdminAndAdminOnly(id, null, false); }
		 */
		// return repository.findByIdAndAdminAndAdminOnly(id, admin, true);

		LOG.info("setRightDiff method started");
		DifferObject differ = null;
		if (id != null) {
			differ = repository.findOne(id);
			if (differ == null) {
				differ = this.createDiffer(id);
			}
			differ.setRight(right);
			repository.save(differ);
		}
		// TODO Auto-generated method stub
		return differ;
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
			// TODO tratar sem left ou right
			if (leftDecoded.length != rightDecode.length) {

			} else {

			}
		}
		// TODO Auto-generated method stub
		return null;
	}
}
