package gob.sis.simos.ui;

import gob.sis.simos.R;
import gob.sis.simos.entity.Respuesta;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.TextView;

public class UITextView extends TextView {

	private int preguntaId;
	private int opcionRespuestaId;
	private int preguntaParentId;

	public UITextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
		// TODO Auto-generated constructor stub
	}

	public int getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(int preguntaId) {
		this.preguntaId = preguntaId;
	}

	public int getOpcionRespuestaId() {
		return opcionRespuestaId;
	}

	public void setOpcionRespuestaId(int opcionRespuestaId) {
		this.opcionRespuestaId = opcionRespuestaId;
	}

	public int getPreguntaParentId() {
		return preguntaParentId;
	}

	public void setPreguntaParentId(int preguntaParentId) {
		this.preguntaParentId = preguntaParentId;
	}

	public UITextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		// TODO Auto-generated constructor stub
	}

	public UITextView(Context context) {
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
	    a.recycle();
	}
	
	public Respuesta getRespuesta(){
		Respuesta r = new Respuesta();
		r.setPreguntaId(getPreguntaId());
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
