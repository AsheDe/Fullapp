package cu.fullapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by renier on 14/11/2016.
 */
public class ViewHolder_Micomentario extends RecyclerView.ViewHolder {

    public ViewHolder_Micomentario(View itemView) {
        super(itemView);
        catpub = (ImageView) itemView.findViewById(R.id.imagecatcomentario);
        tvTituloPub = (TextView) itemView.findViewById(R.id.micomenttitulo) ;
        tvContenidoComen = (TextView) itemView.findViewById(R.id.tvpub_comment_comentario) ;
        tvfecha = (TextView) itemView.findViewById(R.id.tvfechacoment);
        ratingcoment = (RatingBar) itemView.findViewById(R.id.pubrating_comment) ;
        comentario = itemView;

    }

    public ImageView catpub;
    public TextView tvTituloPub;
    public TextView tvContenidoComen;
    public TextView tvfecha;
    public RatingBar ratingcoment;
    View comentario;

}
