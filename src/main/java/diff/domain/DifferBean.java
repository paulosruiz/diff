package diff.domain;

/**
 * DifferBean used for POST methods
 * @author paulo.almeida.junior
 *
 */
public class DifferBean {

	String data = null;

	/**
	 * @return the data
	 */
	public final String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public final void setData(String data) {
		this.data = data;
	}

	public DifferBean() {
		super();
	}

	public DifferBean(final String data) {
		super();
		this.data = data;
	}

	@Override
	public String toString() {
		return "DifferBean [data=" + data + "]";
	}
}
