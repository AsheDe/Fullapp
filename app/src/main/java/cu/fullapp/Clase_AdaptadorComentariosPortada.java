package cu.fullapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by renier on 22/09/2016.
 */
public class Clase_AdaptadorComentariosPortada extends RecyclerView.Adapter {

    ArrayList<Comentario> comentarios;
    Manejo_DB bd;
    boolean isportada;
    Soporte_Elementos_Comunes soporte;
    Context contexto;
    public Clase_AdaptadorComentariosPortada(Context cont, ArrayList<Comentario> comenta, boolean portada)
    {
        contexto = cont;
        comentarios = comenta;
        bd = new Manejo_DB(cont);
        isportada = portada;
        soporte = new Soporte_Elementos_Comunes(cont);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(isportada)
           v = LayoutInflater.from(parent.getContext()).inflate(R.layout.portada_vista_item_comentario, parent, false);
        // set the view's size, margins, paddings and layout parameters
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicacion_comentario_item, parent, false);

        Comentario_ViewHolder vh = new Comentario_ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
       // ((Comentario_ViewHolder)holder).autor.setText(comentarios.get(position).autor);
        Publicacion publi = bd.Obtener_PublicacionPorIdentificador(comentarios.get(position).getEntrada());
        String pub = publi.getTitulo();
        ((Comentario_ViewHolder)holder).titulo.setText(pub);
        Drawable imgcat = soporte.ImgPorSubCategoria(publi.getSubcategoria(),publi.getCategoria());
        ((Comentario_ViewHolder)holder).categoria.setImageDrawable(imgcat);

        ((Comentario_ViewHolder)holder).comentario.setText(comentarios.get(position).getComentario());

        ((Comentario_ViewHolder)holder).vCOMENTARIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent publicacion = new Intent("cu.fullapp.PUBLICACION");
                publicacion.putExtra("identificador",comentarios.get(position).getEntrada());
                contexto.startActivity(publicacion);
            }
        });

    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class Comentario_ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case

        //  public TextView autor;

        public TextView titulo;
        public TextView comentario;
        public ImageView categoria;

        View vCOMENTARIO;

        public Comentario_ViewHolder(View COMENTARIO)
        {
            super(COMENTARIO);

            titulo = (TextView) COMENTARIO.findViewById(R.id.tvportada_comment_TITULO);
            comentario = (TextView) COMENTARIO.findViewById(R.id.tvportada_comment_comentario);
            categoria = (ImageView) COMENTARIO.findViewById(R.id.imagecatcomentario);
            vCOMENTARIO = COMENTARIO;
        }
    }


}
