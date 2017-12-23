package diff.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import diff.services.DifferService;

@RestController
@RequestMapping("/v1/diff")
public class DifferController {

	final static Logger LOG = Logger.getLogger(DifferController.class);

	@Autowired
	DifferService differService;

	@PostMapping(value = "/{id}/left", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public String left(@RequestParam(value = "id") long id, String encodedLeft) {
		LOG.debug("Starting getToogle");
		differService.setLeftDiff(id, encodedLeft);
		return encodedLeft;

	}

	@PostMapping(value = "/{id}/right", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public String right(@RequestParam(value = "id") long id, String encodedRight) {
		LOG.debug("Starting getToogle");
		differService.setRightDiff(id, encodedRight);
		return encodedRight;
	}

	@RequestMapping("/get")
	public String get(@RequestParam(value = "id") long id) {
		LOG.debug("Starting getToogleByAdmin");
		differService.getDiff(id);
		return null;
	}

}
