package cu.fullapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by renier on 08/02/2017.
 */
public class Vista_Dialogo_Datos_Transf extends LinearLayout   {

    EditText clavetransf;
    EditText saldoatransf;
 //   NumberPicker saldoatransferir;
    String valuetransfer = "1";

    public Vista_Dialogo_Datos_Transf(Context context) {
        super(context);
        init();
    }


    private void init() {
        // En primer lugar inflamos la vista de nuestro control personalizado. Al método iniflate
        // le pasamos el layout de nuestro control, el ViewGroup al que pertenecerá la vista (this)
        // y si se debe añadir a este ViewGroup (en este caso sí).
        ((LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.vista_dialogo_datos_transf, this, true);
        // getContext().getLayoutInflater().inflate(R.layout.drawer_bar,fra);
        // LayoutInflater().infl
        // Inicializamos nuestros controles.

        clavetransf = (EditText) findViewById(R.id.clavetransf);
       // saldoatransferir = (NumberPicker) findViewById(R.id.numberPicker);
       // String [] valores = new String[]{"0.5","1","2","3","2","1","2","1","2"};
       /* saldoatransferir.setMinValue(0);
        saldoatransferir.setMaxValue(10);
        saldoatransferir.setDisplayedValues(valores);
        saldoatransferir.setWrapSelectorWheel(false);
        saldoatransferir.setValue(1);*/
        final TextView saldof = (TextView) findViewById(R.id.saldo);
        saldoatransf  = (EditText) findViewById(R.id.saldoatransf);
       /* saldoatransferir.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
               int valor = saldoatransferir.getValue();

                switch (valor)
                {

                    case 0:
                    {
                       // saldof.setText( "0.50" + " CUC - " + 60 + " DÍAS (2 meses)" );
                        Toast.makeText(getContext(),"Para transferencias de menos de 1 CUC, deberá introducir el monto manualmente",Toast.LENGTH_SHORT).show();
                        valuetransfer = "0.50";
                        break;
                    }
                    case 1:
                    {
                       // saldof.setText( valor + " CUC - " + 150 + " DÍAS (5 meses)" );
                        valuetransfer = "1";
                        break;
                    }
                    case 2:
                    {
                      //  saldof.setText( valor + " CUC - " + 365 + " DÍAS (1 año)" );
                        valuetransfer = "2";
                        break;
                    }

                }

            }
        });*/

    }


}
