package diff.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import diff.domain.DifferBean;
import diff.domain.DifferObject;
import diff.domain.DifferResponse;
import diff.services.DifferService;
import diff.util.DifferSide;

/**
 * DifferControler Class
 * It handles the DifferController exposed methods
 * @author paulo.almeida.junior
 *
 */
@RestController
@RequestMapping("/v1/diff")
public class DifferController {

	final static Logger LOG = Logger.getLogger(DifferController.class);

	@Autowired
	DifferService differService;

	/**
	 * POST method
	 * Creates (if the id does not exist yet) and saves the encodedValue
	 * @param id
	 * @param side
	 * @param encodedValue
	 * @return 
	 * 	DifferObject created via this method
	 */
	@PostMapping(value = "/{id}/{side}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<DifferObject> defineData(@PathVariable("id") final String id,
			@PathVariable("side") final String side, @RequestBody DifferBean encodedValue) {
		LOG.info("Received defineData request");
		LOG.info(encodedValue);
		LOG.info("Side:" + side);
		try {
			DifferSide enumSide = DifferSide.valueOf(side.toUpperCase());
			if (enumSide != null) {
				DifferObject newDiffer = differService.defineData(id, encodedValue.getData(), enumSide);
				return new ResponseEntity<>(newDiffer, HttpStatus.CREATED);
			}
		} catch (java.lang.IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return null;

	}
/**
 * GET method
 * Compares the data of the left & right based on the received id
 * @param id
 * @return
 * 	DifferResponse filled with the equals, sameSize and offSet information 
 * @throws Exception
 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<DifferResponse> compare(@PathVariable final String id) throws Exception {
		LOG.info("Received compare request");
		DifferResponse response = differService.compare(id);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/**
	 * Retrieve all existing Ids in the DB
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/retrieveAll")
	public List<DifferObject> retrieveAll() {
		LOG.info("Received retrieveAll request");
		return differService.retrieveAll();

	}

}
