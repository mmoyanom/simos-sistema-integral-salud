package gob.sis.simos.service.impl;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.entity.medicine.Medicamento_ABCD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import roboguice.inject.ContextSingleton;

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
		try {
			String str = text.toLowerCase();
			if(str.startsWith("a")
					|| str.startsWith("b")
						||str.startsWith("c")
							|| str.startsWith("d")){
				System.out.println("entrando");
				Dao<Medicamento_ABCD, String> dao = (Dao<Medicamento_ABCD, String>) getHelper().getMedicamentoDao(Medicamento_ABCD.class);
				QueryBuilder builder = dao.queryBuilder();
				builder.setWhere(builder.where().like("med_name", text+"%"));
				List items = dao.query(builder.prepare());
				return items;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		List<Insumo> items = new ArrayList<Insumo>();
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
