package cu.fullapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by renier on 12/01/2017.
 */
public class Vista_Item_Ayuda extends LinearLayout implements ViewGroup.OnClickListener {
    TextView descripcion ;
    ImageView ampliar ;
    Button accion ;
    boolean estado_ampliar	;
    String textoresumen;
    String textoampliado;
    int posicion;
    Context contexto;
    public Vista_Item_Ayuda(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        contexto = context;

        //estado_ampliar	 = false ;
    }

    public void Configurar(int posicion)
    {
        textoresumen =  contexto.getResources().getStringArray(R.array.preguntas)[posicion];
        textoampliado =  contexto.getResources().getStringArray(R.array.respuestas)[posicion];
        descripcion.setText(textoresumen);
        estado_ampliar = false;

    }


    private void init() {
        // En primer lugar inflamos la vista de nuestro control personalizado. Al método iniflate
        // le pasamos el layout de nuestro control, el ViewGroup al que pertenecerá la vista (this)
        // y si se debe añadir a este ViewGroup (en este caso sí).
        ((LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ayuda_item, this, true);
       // getContext().getLayoutInflater().inflate(R.layout.drawer_bar,fra);
       // LayoutInflater().infl
        // Inicializamos nuestros controles.

        descripcion = (TextView) findViewById(R.id.ayuda_descripcion);
        ampliar = (ImageView) findViewById(R.id.ayuda_expandir);
        accion = (Button) findViewById(R.id.ayuda_accion);

	ampliar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.ayuda_expandir)
        {

            if(estado_ampliar==false)
            {
                v.setRotationX(180);
                estado_ampliar=true;
                descripcion.setText(textoampliado);
                descripcion.invalidate();
            }
            else
            {
                v.setRotationX(0);
                estado_ampliar=false;
                descripcion.setText(textoresumen);
            }

        }
    }
}
