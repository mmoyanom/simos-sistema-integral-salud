package gob.sis.simos.ui;

import gob.sis.simos.R;
import gob.sis.simos.entity.Respuesta;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

public class UIEditText extends EditText {
	
	private Integer preguntaId;
	private Integer preguntaParentId;
	private Integer opcionRespuestaId;

	public UIEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(attrs);
	}

	public UIEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public UIEditText(Context context) {
		super(context);
	}
	
	private void init(AttributeSet attrs) { 
	    TypedArray a=getContext().obtainStyledAttributes(
	         attrs,
	         R.styleable.UIOpcionRespuesta);
	    this.preguntaId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaId, -1);
	    this.opcionRespuestaId = a.getInteger(R.styleable.UIOpcionRespuesta_opcionRespuestaId, -1);
	    this.preguntaParentId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaParentId, -1);
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
	
	public Respuesta getRespuesta(){
		Respuesta r = new Respuesta();
		r.setPreguntaId(getPreguntaId());
		r.setPreguntaParentId(getPreguntaParentId());
		r.setOpcionRespuestaId(getOpcionRespuestaId());
		if(getInputType() == (InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL)){
			double value = Double.parseDouble(getText().toString());
			r.setRespuestaNumero(value);
		} else {
			r.setRespuestaTexto(getText().toString());
		}
		
		return r;
	}

}
