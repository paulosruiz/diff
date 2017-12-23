package diff.domain;

import java.util.List;

import diff.util.DifferResponseStatus;

public class DifferResponse {
	private Long id;
	private Boolean equals = false;
	private Boolean sameSize = false;
	private List<Offset> offsets;
	private DifferResponseStatus status;

	/**
	 * @return the status
	 */
	public final DifferResponseStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public final void setStatus(DifferResponseStatus status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @return the equal
	 */
	public final Boolean getEquals() {
		return equals;
	}

	/**
	 * @return the sameSize
	 */
	public final Boolean getSameSize() {
		return sameSize;
	}

	/**
	 * @return the offsets
	 */
	public final List<Offset> getOffsets() {
		return offsets;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @param equals
	 *            the equals to set
	 */
	public final void setEquals(final Boolean equasl) {
		this.equals = equals;
	}

	/**
	 * @param sameSize
	 *            the sameSize to set
	 */
	public final void setSameSize(final Boolean sameSize) {
		this.sameSize = sameSize;
	}

	/**
	 * @param offsets
	 *            the offsets to set
	 */
	public final void setOffsets(final List<Offset> offsets) {
		this.offsets = offsets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
