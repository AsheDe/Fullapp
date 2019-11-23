package cu.fullapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by renier on 14/11/2016.
 */
public class Secciones_ViewHolder extends RecyclerView.ViewHolder  {
    // each data item is just a string in this case
    public ImageView nuevapub;
    public TextView tvTituloPub;
    // public TextView tvContenidoPub;
    public TextView tvPrecioPub;
    public TextView comentarios;
    public ImageView opciones;
   public ImageView provincia;
    public ImageView subcategoria;
    public ImageView pubbusco;
    public RatingBar ratBarpub;
    public TextView autor;
    View vpublicacion;
  //  String categoria_m;

    //ViewGroup padre;
    public Secciones_ViewHolder(View publicacion) {
        super(publicacion);
        nuevapub = (ImageView) publicacion.findViewById(R.id.nuevapub);
        tvTituloPub = (TextView) publicacion.findViewById(R.id.tvTituloPub);

        tvPrecioPub = (TextView) publicacion.findViewById(R.id.tvPrecioPub);
        comentarios = (TextView) publicacion.findViewById(R.id.numcoments);
        opciones = (ImageView) publicacion.findViewById(R.id.opciones_pub);
      // provincia = (ImageView) publicacion.findViewById(R.id.provcoment);
        ratBarpub = (RatingBar) publicacion.findViewById(R.id.ratingBar_item_pub);
        //autor = (TextView) publicacion.findViewById(R.id.autorpub);
        subcategoria = (ImageView) publicacion.findViewById(R.id.cuadro_subcategoria);
        pubbusco = (ImageView) publicacion.findViewById(R.id.pubbusco);
        vpublicacion = publicacion;
      //  categoria_m = cat;
        //padre = (ViewGroup) actividad.findViewById(R.id.contedor_pub_list);
        //vpublicacion.setOnClickListener(this);
        // opciones.setOnClickListener(this);



    }
}