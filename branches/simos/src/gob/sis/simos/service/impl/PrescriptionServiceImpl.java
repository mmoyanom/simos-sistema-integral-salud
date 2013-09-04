package gob.sis.simos.service.impl;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.entity.medicine.MedicamentoNotAlpha;
import gob.sis.simos.entity.medicine.Medicamento_ABCD;
import gob.sis.simos.entity.medicine.Medicamento_EFGH;
import gob.sis.simos.entity.medicine.Medicamento_IJKL;
import gob.sis.simos.entity.medicine.Medicamento_MNENIEO;
import gob.sis.simos.entity.medicine.Medicamento_PQRST;
import gob.sis.simos.entity.medicine.Medicamento_UVWXYZ;
import gob.sis.simos.entity.supplies.InsumoNotAlpha;
import gob.sis.simos.entity.supplies.Insumo_ABCD;
import gob.sis.simos.entity.supplies.Insumo_EFGH;
import gob.sis.simos.entity.supplies.Insumo_IJKL;
import gob.sis.simos.entity.supplies.Insumo_MNENIEO;
import gob.sis.simos.entity.supplies.Insumo_PQRST;
import gob.sis.simos.entity.supplies.Insumo_UVWXYZ;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

@ContextSingleton
public class PrescriptionServiceImpl { // implements PrescriptionService {

	private DBHelper dbhelper;

	@Inject
	private Context context;
	
	//@Override
	public List<Medicamento> getListaMedicamento() {
		List<Medicamento> items = new ArrayList<Medicamento>();
		for(int i=0; i < 3; i++){
			Medicamento m = new Medicamento();
			m.setId(""+(i+1));
			m.setNombre("Medicamento "+(i+1));
			m.setDescripcion("Medida");
			m.setRecetado(3);
			m.setEntregado(2);
			m.setCategoria("Categoria");
			items.add(m);
		}
		return items;
	}
	
	public List<Medicamento> findMedicamento(String text) {
		/*List<Medicamento> items = new ArrayList<Medicamento>();
		for(int i=0; i < 10; i++){
			Medicamento m = new Medicamento();
			m.setId(""+(i+1));
			m.setNombre("Medicamento "+(i+1));
			m.setDescripcion("Medida");
			m.setRecetado(3);
			m.setEntregado(2);
			m.setCategoria("Categoria");
			items.add(m);
		}
		return items;*/
		List items = new ArrayList<Medicamento>();
		try {
			String str = text.toLowerCase();
			if(str.startsWith("a") || str.startsWith("b") ||str.startsWith("c") || str.startsWith("d")){
				Dao<Medicamento_ABCD, String> dao = (Dao<Medicamento_ABCD, String>) getHelper().getMedicamentoDao(Medicamento_ABCD.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("med_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else if(str.startsWith("e") || str.startsWith("f") ||str.startsWith("g") || str.startsWith("h")){
				Dao<Medicamento_EFGH, String> dao = (Dao<Medicamento_EFGH, String>) getHelper().getMedicamentoDao(Medicamento_EFGH.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("med_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else if(str.startsWith("i") || str.startsWith("j") ||str.startsWith("k") || str.startsWith("l")){
				Dao<Medicamento_IJKL, String> dao = (Dao<Medicamento_IJKL, String>) getHelper().getMedicamentoDao(Medicamento_IJKL.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("med_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else if(str.startsWith("m") || str.startsWith("n") ||str.startsWith("ñ") || str.startsWith("o")){
				Dao<Medicamento_MNENIEO, String> dao = (Dao<Medicamento_MNENIEO, String>) getHelper().getMedicamentoDao(Medicamento_MNENIEO.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("med_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else if(str.startsWith("p") || str.startsWith("q") ||str.startsWith("r") || str.startsWith("s") || str.startsWith("t")){
				Dao<Medicamento_PQRST, String> dao = (Dao<Medicamento_PQRST, String>) getHelper().getMedicamentoDao(Medicamento_PQRST.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("med_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			}  else if(str.startsWith("u") || str.startsWith("v") ||str.startsWith("w") || str.startsWith("x") || str.startsWith("y") || str.startsWith("z")){
				Dao<Medicamento_UVWXYZ, String> dao = (Dao<Medicamento_UVWXYZ, String>) getHelper().getMedicamentoDao(Medicamento_UVWXYZ.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("med_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else {
				Dao<MedicamentoNotAlpha, String> dao = (Dao<MedicamentoNotAlpha, String>) getHelper().getMedicamentoDao(MedicamentoNotAlpha.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("med_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	//@Override
	public List<Insumo> getListaInsumos() {
		List<Insumo> items = new ArrayList<Insumo>();
		for(int i=0;i < 3; i++){
			Insumo in = new Insumo();
			in.setId(""+(i+1));
			in.setNombre("Insumo "+(i+1));
			in.setDescripcion("Medida");
			in.setRecetado(3);
			in.setEntregado(2);
			in.setCategoria("Categoria");
			items.add(in);
		}
		return items;
	}

	public List<Insumo> findInsumos(String text) {
		/*List<Insumo> items = new ArrayList<Insumo>();
		for(int i=0;i < 5; i++){
			Insumo in = new Insumo();
			in.setId(""+(i+1));
			in.setNombre("Insumo "+(i+1));
			in.setDescripcion("Medida");
			in.setRecetado(3);
			in.setEntregado(2);
			in.setCategoria("Categoria");
			items.add(in);
		}
		return items;*/
		List items = new ArrayList<Insumo>();
		try {
			String str = text.toLowerCase();
			if(str.startsWith("a") || str.startsWith("b") ||str.startsWith("c") || str.startsWith("d")){
				Dao<Insumo_ABCD, String> dao = (Dao<Insumo_ABCD, String>) getHelper().getMedicamentoDao(Insumo_ABCD.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("sup_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else if(str.startsWith("e") || str.startsWith("f") ||str.startsWith("g") || str.startsWith("h")){
				Dao<Insumo_EFGH, String> dao = (Dao<Insumo_EFGH, String>) getHelper().getMedicamentoDao(Insumo_EFGH.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("sup_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else if(str.startsWith("i") || str.startsWith("j") ||str.startsWith("k") || str.startsWith("l")){
				Dao<Insumo_IJKL, String> dao = (Dao<Insumo_IJKL, String>) getHelper().getMedicamentoDao(Insumo_IJKL.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("sup_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else if(str.startsWith("m") || str.startsWith("n") ||str.startsWith("ñ") || str.startsWith("o")){
				Dao<Insumo_MNENIEO, String> dao = (Dao<Insumo_MNENIEO, String>) getHelper().getMedicamentoDao(Insumo_MNENIEO.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("sup_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else if(str.startsWith("p") || str.startsWith("q") ||str.startsWith("r") || str.startsWith("s") || str.startsWith("t")){
				Dao<Insumo_PQRST, String> dao = (Dao<Insumo_PQRST, String>) getHelper().getMedicamentoDao(Insumo_PQRST.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("sup_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			}  else if(str.startsWith("u") || str.startsWith("v") ||str.startsWith("w") || str.startsWith("x") || str.startsWith("y") || str.startsWith("z")){
				Dao<Insumo_UVWXYZ, String> dao = (Dao<Insumo_UVWXYZ, String>) getHelper().getMedicamentoDao(Insumo_UVWXYZ.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("sup_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			} else {
				Dao<InsumoNotAlpha, String> dao = (Dao<InsumoNotAlpha, String>) getHelper().getMedicamentoDao(InsumoNotAlpha.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("sup_name", text+"%"));
				items = dao.query(builder.prepare());
				return items;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(this.context, //this._context,
					DBHelper.class);
		}
		return this.dbhelper;
	}

}
