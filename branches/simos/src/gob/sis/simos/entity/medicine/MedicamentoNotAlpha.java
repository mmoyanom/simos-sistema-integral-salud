package gob.sis.simos.entity.medicine;

import com.j256.ormlite.table.DatabaseTable;

import gob.sis.simos.entity.Medicamento;

@DatabaseTable(tableName="medicine_not_alpha")
public class MedicamentoNotAlpha extends Medicamento {

	private static final long serialVersionUID = 1L;

	public MedicamentoNotAlpha() {
		// TODO Auto-generated constructor stub
	}

}
