package diff.controller;

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

import diff.domain.DiffBean;
import diff.domain.DifferObject;
import diff.services.DifferService;
import diff.util.DiffSide;

@RestController
@RequestMapping("/v1/diff")
public class DifferController {

	final static Logger LOG = Logger.getLogger(DifferController.class);

	@Autowired
	DifferService differService;

	@PostMapping(value = "/{id}/left", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<DifferObject> left(@PathVariable("id") Long id, @RequestBody DiffBean encodedLeft) {
		LOG.info("Starting left");
		LOG.info(encodedLeft);
		DifferObject newDiffer = differService.defineData(id, encodedLeft.getData(), DiffSide.LEFT);
		return new ResponseEntity<>(newDiffer, HttpStatus.CREATED);

	}

	@PostMapping(value = "/{id}/right", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<DifferObject> right(@PathVariable("id") Long id, @RequestBody DiffBean encodedRight) {
		LOG.debug("Starting right");
		DifferObject newDiffer = differService.defineData(id, encodedRight.getData(),DiffSide.RIGHT);
		return new ResponseEntity<>(newDiffer, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public String compare(@PathVariable long id) {
		LOG.info("Starting compare");
		differService.getDiff(id);
		return null;
	}

}
