package cu.fullapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by yudel on 14/06/2017.
 */
public class Item_Slide extends FrameLayout {
    ImageView circulo,icono;
    public Item_Slide(Context context) {
        super(context);
        init();
    }

    private void init() {
        // En primer lugar inflamos la vista de nuestro control personalizado. Al método iniflate
        // le pasamos el layout de nuestro control, el ViewGroup al que pertenecerá la vista (this)
        // y si se debe añadir a este ViewGroup (en este caso sí).
        ((LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_sliding, this, true);
        // getContext().getLayoutInflater().inflate(R.layout.drawer_bar,fra);
        // LayoutInflater().infl
        // Inicializamos nuestros controles.

       circulo = (ImageView) findViewById(R.id.circuloslide);
        icono = (ImageView) findViewById(R.id.iconoslide);


    }
}
