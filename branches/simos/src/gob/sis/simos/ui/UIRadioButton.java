package gob.sis.simos.ui;

import gob.sis.simos.R;
import gob.sis.simos.entity.Respuesta;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class UIRadioButton extends RadioButton {

	public String value;
	private Integer preguntaId;
	private Integer preguntaParentId;
	private Integer opcionRespuestaId;
	
	public UIRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public UIRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	private void init(AttributeSet attrs) { 
	    TypedArray a=getContext().obtainStyledAttributes(
	         attrs,
	         R.styleable.UIOpcionRespuesta);
	    this.preguntaId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaId, -1);
	    this.opcionRespuestaId = a.getInteger(R.styleable.UIOpcionRespuesta_opcionRespuestaId, -1);
	    this.preguntaParentId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaParentId, -1);
	    /*Log.i("test",a.getInteger(
	         R.styleable.UICheckBox_preguntaId,-1));
	    Log.i("test",""+a.getColor(
	         R.styleable.MyCustomView_android_textColor, Color.BLACK));
	    Log.i("test",a.getString(
	         R.styleable.MyCustomView_extraInformation));*/

	    //Don't forget this
	    a.recycle();
	}

	public UIRadioButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Integer getPreguntaId() {
		return preguntaId;
	}
	
	public void setPreguntaId(Integer preguntaId) {
		this.preguntaId = preguntaId;
	}

	public Integer getPreguntaParentId() {
		return preguntaParentId;
	}

	public void setPreguntaParentId(Integer preguntaParentId) {
		this.preguntaParentId = preguntaParentId;
	}

	public Integer getOpcionRespuestaId() {
		return opcionRespuestaId;
	}

	public void setOpcionRespuestaId(Integer opcionRespuestaId) {
		this.opcionRespuestaId = opcionRespuestaId;
	}
	
	public Respuesta getRespuesta(){
		Respuesta r = new Respuesta();
		r.setPreguntaId(getPreguntaId());
		r.setOpcionRespuestaId(getOpcionRespuestaId());
		return r;
	}
	
	
}
