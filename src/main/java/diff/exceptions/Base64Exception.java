package diff.exceptions;

public class Base64Exception extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param errorMessage
	 *            Message used to describe the error that happened while performing the Base64 decode
	 *            
	 */
	public Base64Exception(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor
	 * 
	 * @param errorMessage
	 *            Message used to describe the error that happened while performing the Base64 decode
	 *           
	 * @param t
	 *            
	 */
	public Base64Exception(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}

}
