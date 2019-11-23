package cu.fullapp;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

/**
 * Created by renier on 03/09/2016.
 */
public class EditTextPers extends LinearLayout {
    Spinner spiner;
    TextInputEditText texto;
    TextInputEditText centavos;
    public EditTextPers(Context context, AttributeSet attrs) {
        super(context, attrs);


        init();
    }


    private void init() {
        // En primer lugar inflamos la vista de nuestro control personalizado. Al método iniflate
        // le pasamos el layout de nuestro control, el ViewGroup al que pertenecerá la vista (this)
        // y si se debe añadir a este ViewGroup (en este caso sí).
        ((LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.vista_edit_text_pers, this, true);
       // getContext().getLayoutInflater().inflate(R.layout.drawer_bar,fra);
       // LayoutInflater().infl
        // Inicializamos nuestros controles.

         texto = (TextInputEditText) findViewById(R.id.tiprecio);
         spiner = (Spinner) findViewById(R.id.spMoneda);
        centavos = (TextInputEditText) findViewById(R.id.ticent);
        centavos.setText("00");
        setSpinAdapter();


    }

    public void setSpinAdapter(){

        ArrayAdapter<CharSequence> adter = ArrayAdapter.createFromResource(getContext(),R.array.arMoneda, android.R.layout.simple_spinner_item);
        adter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adter);
    }
    public float getPrecio()
    {
       float prec = Float.parseFloat(texto.getText().toString());
        float cent = Float.parseFloat(centavos.getText().toString())/100;
        return prec + cent ;
    }
}
