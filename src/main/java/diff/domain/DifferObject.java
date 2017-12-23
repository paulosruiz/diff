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
	private Long id;

	public DifferObject(Long id) {
		this.id = id;
	}

	@Column(name = "LEFT")
	private String left;
	
	@Column(name = "RIGHT")
	private String right;

	@Override
	public String toString() {
		return "DifferObject [id=" + id + ", left=" + left + ", right=" + right + "]";
	}

	public Long getId() {
		return id;
	}

	public String getLeft() {
		return left;
	}

	public String getRight() {
		return right;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public void setRight(String right) {
		this.right = right;
	}

}
