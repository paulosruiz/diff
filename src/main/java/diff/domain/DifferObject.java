package diff.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "differ")
public class DifferObject {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "LEFT")
	private String left;

	@Column(name = "RIGHT")
	private String right;

	public DifferObject() {
		
	}
	public DifferObject(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "DifferObject [id=" + id + ", left=" + left + ", right=" + right + "]";
	}
	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}
	/**
	 * @return the left
	 */
	public final String getLeft() {
		return left;
	}
	/**
	 * @return the right
	 */
	public final String getRight() {
		return right;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}
	/**
	 * @param left the left to set
	 */
	public final void setLeft(String left) {
		this.left = left;
	}
	/**
	 * @param right the right to set
	 */
	public final void setRight(String right) {
		this.right = right;
	}

	

}
