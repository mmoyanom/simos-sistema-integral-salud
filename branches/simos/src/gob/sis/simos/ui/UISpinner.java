package gob.sis.simos.ui;

import gob.sis.simos.R;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Spinner;

public class UISpinner extends Spinner {

	public String value;
	private Integer preguntaId;
	private Integer preguntaParentId;
	private Integer respuestaParentId;
	
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

	public Integer getRespuestaParentId() {
		return respuestaParentId;
	}

	public void setRespuestaParentId(Integer respuestaParentId) {
		this.respuestaParentId = respuestaParentId;
	}

	public UISpinner(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public UISpinner(Context context, int mode) {
		super(context, mode);
		// TODO Auto-generated constructor stub
	}

	public UISpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(attrs);
		// TODO Auto-generated constructor stub
	}

	public UISpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init(attrs);
		// TODO Auto-generated constructor stub
	}

	public UISpinner(Context context, AttributeSet attrs, int defStyle, int mode) {
		super(context, attrs, defStyle, mode);
		this.init(attrs);
		// TODO Auto-generated constructor stub
	}
	
	private void init(AttributeSet attrs) { 
	    TypedArray a=getContext().obtainStyledAttributes(
	         attrs,
	         R.styleable.UIOpcionRespuesta);
	    this.preguntaId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaId, -1);
	    this.preguntaParentId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaParentId, -1);
	    this.respuestaParentId = a.getInteger(R.styleable.UIOpcionRespuesta_respuestaParentId, -1);
	    a.recycle();
	}
	
	public Respuesta getRespuesta(){
		OpcionRespuesta o = (OpcionRespuesta)getSelectedItem();
		Respuesta r = new Respuesta();
		r.setOpcionRespuestaId(o.getOpcionRespuestaId());
		r.setPreguntaId(getPreguntaId());
		r.setPreguntaParentId(getPreguntaParentId());
		return r;
	}

}
