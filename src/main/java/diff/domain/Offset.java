package diff.domain;

/**
 * Offset object 
 * offset has the position
 * length has the size of the offset
 * @author paulo.almeida.junior
 *
 */
public class Offset {
	private int offset;
	private int length = 0;

	/**
	 * @param offset
	 * @param length
	 */
	public Offset(final int offset, final int length) {
		this.offset = offset;
		this.length = length;
	}

	public Offset() {
		// TODO Auto-generated constructor stub
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}

	public void setOffset(final int offset) {
		this.offset = offset;
	}

	public void setLength(final int length) {
		this.length = length;
	}

}
