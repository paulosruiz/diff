package diff.services;

import java.util.Base64;
import java.util.List;

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
		LOG.info("getDiffer method started");
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
		LOG.info("createDiffer method started");
		DifferObject newDiff = null;
		try {
			if (id != null) {
				newDiff = new DifferObject(id);
				repository.save(newDiff);
				LOG.info("Differ created");
				LOG.debug(newDiff.toString());
			}

		} catch (Exception e) {
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
		LOG.info("defineData method started");

		DifferObject differ = null;
		if (id != null && data != null && side != null) {
			LOG.debug("Id: " + id.toString());
			LOG.debug("Data: " + data);
			LOG.debug("Side: " + side.name());
			differ = this.getDiffer(id);
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

	/**
	 * Validate is the object is ready to be compared (if the Left and right exists)
	 * 
	 * @param diff
	 * @return
	 */
	private boolean validateToCompare(DifferObject diff) {
		if (diff != null) {
			if (diff.getLeft() != null && diff.getRight() != null && !diff.getLeft().isEmpty()
					&& !diff.getRight().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public DifferObject getDiff(Long id) {
		LOG.info("getDiff method started");

		DifferObject differToCompare = getDiff(id);
		if (validateToCompare(differToCompare)) {
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

	@Override
	public List<DifferObject> retrieveAll() {
		LOG.info("getAll method started");

		return repository.findAll();
	}
}
