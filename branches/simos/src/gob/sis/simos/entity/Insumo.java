package gob.sis.simos.entity;

public class Insumo {

	private String id;
	private String name;
	private String category;
	private String description;
	private Integer prescribed;
	private Integer commited;
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPrescribed() {
		return prescribed;
	}
	public void setPrescribed(Integer prescribed) {
		this.prescribed = prescribed;
	}
	public Integer getCommited() {
		return commited;
	}
	public void setCommited(Integer commited) {
		this.commited = commited;
	}
	
	
	
}
