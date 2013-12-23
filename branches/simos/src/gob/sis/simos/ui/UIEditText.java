package gob.sis.simos.ui;

import gob.sis.simos.R;
import gob.sis.simos.entity.Respuesta;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;
import android.text.InputFilter;

public class UIEditText extends EditText {
	
	private Integer preguntaId;
	private Integer preguntaParentId;
	private Integer opcionRespuestaId;
	
	protected int max_value = Integer.MAX_VALUE;
    protected int min_value = Integer.MIN_VALUE;

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
		this.setInputType(InputType.TYPE_CLASS_NUMBER);
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
	
	// checks whether the limits are set and corrects them if not within limits
    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        if (max_value != Integer.MAX_VALUE) {
            try {
                if (Integer.parseInt(this.getText().toString()) > max_value) {
                    // change value and keep cursor position
                    int selection = this.getSelectionStart();
                    this.setText(String.valueOf(max_value));
                    if (selection >= this.getText().toString().length()) {
                        selection = this.getText().toString().length();
                    }
                    this.setSelection(selection);
                }
            } catch (NumberFormatException exception) {
                super.onTextChanged(text, start, before, after);
            }
        }
        if (min_value != Integer.MIN_VALUE) {
            try {
                if (Integer.parseInt(this.getText().toString()) < min_value) {
                    // change value and keep cursor position
                    int selection = this.getSelectionStart();
                    this.setText(String.valueOf(min_value));
                    if (selection >= this.getText().toString().length()) {
                        selection = this.getText().toString().length();
                    }
                    this.setSelection(selection);
                }
            } catch (NumberFormatException exception) {
                super.onTextChanged(text, start, before, after);
            }
        }
        super.onTextChanged(text, start, before, after);
    }

    // set the max number of digits the user can enter
    public void setMaxLength(int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(8);
        this.setFilters(FilterArray);
    }

    // set the maximum integer value the user can enter.
    // if exeeded, input value will become equal to the set limit
    public void setMaxValue(int value) {
        max_value = value;
    }
    // set the minimum integer value the user can enter.
    // if entered value is inferior, input value will become equal to the set limit
    public void setMinValue(int value) {
        min_value = value;
    }

    // returns integer value or 0 if errorous value
    public int getValue() {
        try {
            return Integer.parseInt(this.getText().toString());
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

}
