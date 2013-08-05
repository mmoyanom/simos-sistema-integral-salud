package gob.sis.simos.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="eess")
public class EstablecimientoSalud {
	
	public static final String NAME_FIELD = "name";
	public static final String ADDRESS_FIELD = "address";
	
	@DatabaseField(columnName=NAME_FIELD)
	private String name;
	
	@DatabaseField(columnName=ADDRESS_FIELD)
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
