package diff.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diff.domain.DifferObject;
import diff.domain.DifferResponse;
import diff.domain.Offset;
import diff.repositories.DifferRepository;
import diff.services.DifferService;
import diff.util.DifferResponseStatus;
import diff.util.DifferSide;

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
	public DifferObject defineData(final Long id, final String data, final DifferSide side) {
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
			if (side.equals(DifferSide.LEFT)) {
				differ.setLeft(data);
			} else {
				differ.setRight(data);
			}
			repository.save(differ);
		}

		return differ;
	}

	/**
	 * Validate is the object is ready to be compared (Right)
	 * 
	 * @param diff
	 * @return
	 */
	private boolean validateToCompareRight(DifferObject diff) {
		LOG.info("Starting validateToCompareRight");
		if (diff != null) {
			if (diff.getRight() != null && !diff.getRight().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate is the object is ready to be compared (Left)
	 * 
	 * @param diff
	 * @return
	 */
	private boolean validateToCompareLeft(DifferObject diff) {
		LOG.info("Starting validateToCompareLeft");
		if (diff != null) {
			if (diff.getLeft() != null && !diff.getLeft().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate the Differ Object is is ready to be compared
	 * 
	 * @param differToCompare
	 * @param response
	 * @return
	 */
	private boolean validateToCompare(DifferObject differToCompare, DifferResponse response) {
		LOG.info("Starting validateToCompare");
		boolean isValid = true;
		if (differToCompare == null) {
			response.setStatus(DifferResponseStatus.NOT_FOUND);
			isValid = false;
		} else {
			if (!validateToCompareRight(differToCompare)) {
				response.setStatus(DifferResponseStatus.MISSING_RIGHT);
				isValid = false;
			} else if (!validateToCompareLeft(differToCompare)) {
				response.setStatus(DifferResponseStatus.MISSING_LEFT);
				isValid = false;
			}
		}
		return isValid;
	}

	@Override
	public DifferResponse compare(Long id) throws Exception {
		LOG.info("Starting comparing");
		LOG.info("ID: " + id);
		DifferResponse response = new DifferResponse();
		response.setId(id);
		try {
			DifferObject differToCompare = getDiffer(id);
			if (validateToCompare(differToCompare, response)) {

				final byte[] leftDecoded = Base64.getDecoder().decode(differToCompare.getLeft());

				final byte[] rightDecode = Base64.getDecoder().decode(differToCompare.getRight());

				LOG.debug("Comparing: " + differToCompare.toString());
				LOG.debug("Arrays Sizes");
				LOG.debug("Right: " + rightDecode.length);
				LOG.debug("Left: " + leftDecoded.length);

				// Check is size is the same
				if (leftDecoded.length != rightDecode.length) {
					LOG.debug("Arrays size are not equals");
					
					response.setEquals(false);
					response.setSameSize(false);
					response.setStatus(DifferResponseStatus.SIZE_MISMATCH);
				} else {
					// Same size = true
					response.setSameSize(true);
					LOG.debug("Arrays size are equals");
					
					// Check if arrays are equals
					if (Arrays.equals(leftDecoded, rightDecode)) {
						LOG.debug("Contents are the same");
					
						// // Equals = true
						response.setEquals(true);
						response.setStatus(DifferResponseStatus.EQUALS);
					} else {
						// Size are equals but the contents are different
						LOG.debug("Contents are NOT the same");
						response.setEquals(false);
						response.setStatus(DifferResponseStatus.CONTENT_MISMATCH);
					
						// verify the differences
						List<Offset> offsets = checkOffsets(leftDecoded, rightDecode);
						response.setOffsets(offsets);
					}
				}
			}

		} catch (Exception e) {
			LOG.error("error during compare", e);
			throw new Exception("Error during comparation", e);
		}
		return response;
	}

	private List<Offset> checkOffsets(final byte[] leftDecoded, final byte[] rightDecode) {
		LOG.info("Starting checkOffsets");
		List<Offset> listOffsets = new ArrayList<Offset>();
		int offset = -1;
		int limit = 0;
		for (int i = 0; i < leftDecoded.length; i++) {
			if (offset == -1 && leftDecoded[i] != rightDecode[i]) {
				offset = i;
			}
			if (offset >= 0 && leftDecoded[i] == rightDecode[i]) {
				limit = i - offset;
				listOffsets.add(new Offset(offset, limit));
			}
		}
		return listOffsets;
	}

	@Override
	public List<DifferObject> retrieveAll() {
		LOG.info("getAll method started");

		return repository.findAll();
	}
}
