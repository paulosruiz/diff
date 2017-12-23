package diff.util;

/**
 * Response Status for Differ
 * 
 * @author paulo.almeida.junior
 *
 */
public enum DifferResponseStatus {
	BASE64_ERROR("Base64 request error"),EQUALS("Equals"), NOT_FOUND("Not Found"), SIZE_MISMATCH(
			"Size does not match"), CONTENT_MISMATCH(
					"Content does not match"), MISSING_LEFT("Missing left data"), MISSING_RIGHT("Missing right data");

	private String value;

	DifferResponseStatus(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

}
