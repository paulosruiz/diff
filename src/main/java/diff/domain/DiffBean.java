package diff.domain;

public class DiffBean {

	String data = null;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public DiffBean() {
		super();
	}

	public DiffBean(String data) {
		super();
		this.data = data;
	}

	@Override
	public String toString() {
		return "DiffBean [data=" + data + "]";
	}
}
