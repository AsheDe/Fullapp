package cu.fullapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yudel on 05/06/2017.
 */
public class Clase_Adaptador_Comentarios_Publicacion extends RecyclerView.Adapter {

    ArrayList<Comentario> comentarios;
    Manejo_DB bd;
    Soporte_Elementos_Comunes soporte;
    Context contexto;
    int izquierdo = 0, derecho = 1;
    public Clase_Adaptador_Comentarios_Publicacion(Context cont,ArrayList<Comentario> comenta)
    {
        contexto = cont;
        comentarios = comenta;
        bd = new Manejo_DB(cont);

        soporte = new Soporte_Elementos_Comunes(cont);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

      //  if(viewType==izquierdo)
       //{
           // carga comentario izquierdo
           v = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicacion_comentario_item, parent, false);
      /* }
        else {
           // carga comentario derecho
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicacion_comentario_item_derecho, parent, false);
       }*/
        Comentario_ViewHolder vh = new Comentario_ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // ((Comentario_ViewHolder)holder).autor.setText(comentarios.get(position).autor);
        Publicacion publi = bd.Obtener_PublicacionPorIdentificador(comentarios.get(position).getEntrada());
        String pub = publi.getTitulo();
        //((Comentario_ViewHolder)holder).titulo.setText(pub);
        //Drawable imgcat = soporte.ImgPorSubCategoria(publi.getSubcategoria(),publi.getCategoria());
        //((Comentario_ViewHolder)holder).categoria.setImageDrawable(imgcat);
        ((Comentario_ViewHolder)holder).fecha.setText(comentarios.get(position).getFechaConfigurada());
        ((Comentario_ViewHolder)holder).comentario.setText(comentarios.get(position).getComentario());

        Usuario userautor = bd.Usuario_por_Correo(comentarios.get(position).getAutor());

        ((Comentario_ViewHolder)holder).autor.setText(userautor.getNombre());
        ((Comentario_ViewHolder)holder).valoracion.setRating(comentarios.get(position).getRating());



        ((Comentario_ViewHolder)holder).provusuario.setImageDrawable( soporte.ImagenesRedondeadas(soporte.ImagenesPorprovincia[userautor.getProvincia()]) );


        ((Comentario_ViewHolder)holder).vCOMENTARIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// ir al autor...

              /*  Intent publicacion = new Intent("cu.fullapp.PUBLICACION");
                publicacion.putExtra("identificador",comentarios.get(position).getEntrada());
                contexto.startActivity(publicacion);*/
            }
        });

    }

  /*  @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if(position%2==0)
            return izquierdo;
        return derecho;

    }*/

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class Comentario_ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case

          public TextView autor;

      //  public TextView titulo;
        public TextView comentario;
       public ImageView provusuario;
        public TextView fecha;
        public RatingBar valoracion;
        View vCOMENTARIO;

        public Comentario_ViewHolder(View COMENTARIO)
        {
            super(COMENTARIO);

            autor = (TextView) COMENTARIO.findViewById(R.id.tvautorcoment);
            comentario = (TextView) COMENTARIO.findViewById(R.id.tvpub_comment_comentario);
            valoracion = (RatingBar) COMENTARIO.findViewById(R.id.pubrating_comment);
            fecha = (TextView) COMENTARIO.findViewById(R.id.tvfechacoment);
            provusuario = (ImageView) COMENTARIO.findViewById(R.id.imvuser);
            vCOMENTARIO = COMENTARIO;
        }
    }
}


/*
*
* TextView comment = (TextView) coment.findViewById(R.id.tvpub_comment_comentario);
						comment.setText(comentarios.get(i).getComentario());

						TextView fecha = (TextView) coment.findViewById(R.id.tvfechacoment);
						fecha.setText(comentarios.get(i).getFechaConfigurada());

						String Autor = base_datos.Usuario_por_Correo(comentarios.get(i).getAutor()).getNombre();
						TextView autor = (TextView) coment.findViewById(R.id.tvautorcoment);
						autor.setText(Autor);

						RatingBar rbcom = (RatingBar) coment.findViewById(R.id.pubrating_comment);
						rbcom.setRating(comentarios.get(i).getRating());

						//coment.setY(indice);
						//indice = indice + coment.getHeight() + 20;
						//int sd = coment.getHeight();
						//indice=indice + coment.getMeasuredHeight() + 20;

					coments.addView(coment);
* */