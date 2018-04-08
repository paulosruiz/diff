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

/**
 * Differ Service methods implementation
 * 
 * @author paulo.almeida.junior
 *
 */
@Service
public class DifferServiceImpl implements DifferService {

	final static Logger LOG = Logger.getLogger(DifferServiceImpl.class);

	@Autowired
	private DifferRepository repository;

	private DifferObject getDiffer(final String id) {
		LOG.info("Starting getDiffer method");
		DifferObject newDiff = null;

		try {
			if (id != null) {
				newDiff = repository.findById(id);

			}
		} catch (final Exception e) {
			LOG.error("Differ not found", e);
		}

		LOG.debug("Differ was found");
		return newDiff;
	}

	private DifferObject createDiffer(final String id) {
		LOG.info("Starting createDiffer method");
		DifferObject newDiff = null;
		try {
			if (id != null) {
				newDiff = new DifferObject(id);
				repository.save(newDiff);

				LOG.debug(newDiff.toString());
			}

		} catch (final Exception e) {
			LOG.error("Error during createDiffer", e);
		}
		return newDiff;
	}

	@Override
	public DifferObject defineData(final String id, final String data, final DifferSide side) {
		LOG.info("Starting defineData method");

		DifferObject differ = null;
		if (id != null && data != null && side != null) {
			LOG.debug("Id: " + id.toString());
			LOG.debug("Data: " + data);
			LOG.debug("Side: " + side.name());
			try {
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
			} catch (final NullPointerException e) {
				LOG.error("Nullpointer when definedData");
			}
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
		LOG.info("Starting validateToCompareRight method");
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
		LOG.info("Starting validateToCompareLeft method");
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
		LOG.info("Starting validateToCompare method");
		boolean isValid = true;

		if (!validateToCompareRight(differToCompare)) {
			response.setStatus(DifferResponseStatus.MISSING_RIGHT.value());
			isValid = false;
		} else if (!validateToCompareLeft(differToCompare)) {
			response.setStatus(DifferResponseStatus.MISSING_LEFT.value());
			isValid = false;
		}

		return isValid;
	}

	/**
	 * Compare the DifferObject by Id
	 * 
	 * @param id
	 * @return
	 * 
	 */
	@Override
	public DifferResponse compare(final String id) {
		LOG.info("Starting comparing method");

		final DifferResponse response = new DifferResponse();
		response.setId(id);
		DifferObject differToCompare = null;
		byte[] leftDecoded;
		byte[] rightDecode;

		differToCompare = getDiffer(id);

		if (differToCompare == null) {
			response.setStatus(DifferResponseStatus.NOT_FOUND.value());
			return response;
		} else if (validateToCompare(differToCompare, response)) {

			try {
				leftDecoded = Base64.getDecoder().decode(differToCompare.getLeft());
				rightDecode = Base64.getDecoder().decode(differToCompare.getRight());
			} catch (final IllegalArgumentException e) {
				LOG.error("Error Base64 could not be decoded");
				response.setStatus(DifferResponseStatus.BASE64_ERROR.value());
				return response;
			}

			LOG.info("Comparing: " + differToCompare.toString());
			LOG.debug("Right Size: " + rightDecode.length);
			LOG.debug("Left Size: " + leftDecoded.length);

			compare(response, leftDecoded, rightDecode);
		}

		return response;

	}

	/**
	 * @param response
	 * @param leftDecoded
	 * @param rightDecode
	 */
	private void compare(DifferResponse response, byte[] leftDecoded, byte[] rightDecode) {
		// Check is size is the same
		if (leftDecoded.length != rightDecode.length) {
			LOG.debug("Arrays size are not equals");
			response.setEquals(false);
			response.setSameSize(false);
			response.setStatus(DifferResponseStatus.SIZE_MISMATCH.value());
		} else {
			// Same size = true
			response.setSameSize(true);
			LOG.debug("Arrays size are equals");

			// Check if arrays are equals
			if (Arrays.equals(leftDecoded, rightDecode)) {
				response.setEquals(true);
				LOG.debug("Contents are the same");
				response.setStatus(DifferResponseStatus.EQUALS.value());
			} else {
				// Size are equals but the contents are different
				LOG.debug("Contents are NOT the same");
				response.setEquals(false);
				response.setStatus(DifferResponseStatus.CONTENT_MISMATCH.value());

				// verify the differences
				final List<Offset> offsets = checkOffsets(leftDecoded, rightDecode);
				response.setOffsets(offsets);
			}
		}
	}

	private List<Offset> checkOffsets(final byte[] leftDecoded, final byte[] rightDecode) {
		LOG.info("Starting checkOffsets method");
		final List<Offset> listOffsets = new ArrayList<Offset>();
		int offset = -1;
		int length = 0;
		int i = 0;
		for (i = 0; i < leftDecoded.length; i++) {
			LOG.debug("i:" + i);
			if (offset == -1 && leftDecoded[i] != rightDecode[i]) {
				offset = i;
				LOG.debug("offset:" + offset);
			}
			LOG.debug("leftDecoded:" + Byte.toString(leftDecoded[i]));
			LOG.debug("rightDecode:" + Byte.toString(rightDecode[i]));
			if (offset >= 0 && leftDecoded[i] == rightDecode[i]) {
				length = i - offset;
				listOffsets.add(new Offset(offset, length));
				LOG.debug("Length:" + length);
				offset = -1;
			}

		}
		// In case of the final byte is different
		if (offset >= 0 && leftDecoded[i - 1] != rightDecode[i - 1]) {
			length = i - offset;
			listOffsets.add(new Offset(offset, length));
		}

		return listOffsets;
	}

	@Override
	public List<DifferObject> retrieveAll() {
		LOG.info("getAll method started");

		return repository.findAll();
	}

}
