package gob.sis.simos;

import gob.sis.simos.R;
import java.util.ArrayList;
import java.util.List;

import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.ui.UICheckBoxGroup;
import gob.sis.simos.ui.UIRadioGroupQuestion;
import gob.sis.simos.ui.UITextQuestion;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DynamicActivity extends Activity implements OnCheckedChangeListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dynamic);
		
		LinearLayout rootLayout = (LinearLayout)findViewById(R.id.rootLayout);
		
		UIRadioGroupQuestion rd = new UIRadioGroupQuestion(this,"Texto de la pregunta");
		rootLayout.addView(rd,0);
		
		List<Respuesta> items = new ArrayList<Respuesta>();
		for(int i = 0; i < 4 ; i++){
			Respuesta a = new Respuesta();
			//a.set("Texto de la respuesta "+(i+1));
			//a.setValue("Valor "+(i+1));
			items.add(a);
		}
		rd.setItems(items);
		rd.setOnCheckedChangeListener(this);
		
		UITextQuestion tq = new UITextQuestion(this);
		tq.setLabelText("Texto de la pregunta");
		rootLayout.addView(tq,1);
		
		UICheckBoxGroup cbg = new UICheckBoxGroup(this);
		cbg.setLabelText("UICheckBoxGroup");
		cbg.setItems(items);
		rootLayout.addView(cbg);
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		System.out.println("checked : "+checkedId);
	}
}
