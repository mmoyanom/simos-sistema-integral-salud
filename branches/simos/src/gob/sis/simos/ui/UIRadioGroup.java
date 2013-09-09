package gob.sis.simos.ui;

import gob.sis.simos.R;
import gob.sis.simos.entity.Respuesta;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class UIRadioGroup extends RadioGroup {

	public String value;
	private Integer preguntaId;
	private Integer preguntaParentId;
	private Integer opcionRespuestaId;
	private Integer respuestaParentId;
	
	public UIRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(attrs);
	}

	public UIRadioGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private void init(AttributeSet attrs) { 
	    TypedArray a=getContext().obtainStyledAttributes(
	         attrs,
	         R.styleable.UIOpcionRespuesta);
	    this.preguntaId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaId, -1);
	    this.opcionRespuestaId = a.getInteger(R.styleable.UIOpcionRespuesta_opcionRespuestaId, -1);
	    this.preguntaParentId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaParentId, -1);
	    this.respuestaParentId = a.getInteger(R.styleable.UIOpcionRespuesta_respuestaParentId, -1);
	    a.recycle();
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

	public Integer getRespuestaParentId() {
		return respuestaParentId;
	}

	public void setRespuestaParentId(Integer respuestaParentId) {
		this.respuestaParentId = respuestaParentId;
	}
	
	public Respuesta getRespuesta(){
		int id = getCheckedRadioButtonId();
		UIRadioButton rb = (UIRadioButton)findViewById(id);
		return rb.getRespuesta();
	}
	

}
