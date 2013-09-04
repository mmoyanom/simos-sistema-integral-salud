package gob.sis.simos.entity.supplies;

import com.j256.ormlite.table.DatabaseTable;

import gob.sis.simos.entity.Insumo;

@DatabaseTable(tableName="supplies_not_alpha")
public class InsumoNotAlpha extends Insumo {

	private static final long serialVersionUID = 1L;

	public InsumoNotAlpha() {
		super();
		// TODO Auto-generated constructor stub
	}

}
