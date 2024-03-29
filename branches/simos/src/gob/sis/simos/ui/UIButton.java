package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

public class UIButton extends Button {

	private Integer preguntaId;
	private Integer opcionRespuestaId;
	private Integer preguntaParentId;

	public UIButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
		// TODO Auto-generated constructor stub
	}

	public UIButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		// TODO Auto-generated constructor stub
	}

	public UIButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private void init(AttributeSet attrs){
		TypedArray a=getContext().obtainStyledAttributes(
		         attrs,
		         R.styleable.UIOpcionRespuesta);
		    this.preguntaId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaId, -1);
		    this.opcionRespuestaId = a.getInteger(R.styleable.UIOpcionRespuesta_opcionRespuestaId, -1);
		    this.preguntaParentId = a.getInteger(R.styleable.UIOpcionRespuesta_preguntaParentId, -1);
		    a.recycle();
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
	
	

}
