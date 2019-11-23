package cu.fullapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by renier on 11/11/2016.
 */
public class Clase_AdaptadorMisEnvios extends RecyclerView.Adapter<RecyclerView.ViewHolder>

{

    ArrayList<Object> envios;
    private final int MPublicacion = 0, MComentario = 1;
    Soporte_Elementos_Comunes soporte;
    Soporte_Comunicaciones comunicaciones;
    RecyclerView recycler;
    Context contexto;
    Manejo_DB basedatos;

    public Clase_AdaptadorMisEnvios( ArrayList<Object> envio, Context cont, RecyclerView recyc)  {

        this.envios = envio;
        soporte = new Soporte_Elementos_Comunes(cont);
        comunicaciones = new Soporte_Comunicaciones(cont);
        recycler = recyc;
        contexto = cont;
        basedatos = new Manejo_DB(cont);
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        //final ArrayList<Object> enviostodel = envios;
        ItemTouchHelper.Callback callback =
                new ItemTouchHelper.SimpleCallback(dragFlags,swipeFlags) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                       if( envios.get(viewHolder.getAdapterPosition())  instanceof Comentario ){
                           basedatos.Eliminar_MiComentario(((Comentario) envios.get(viewHolder.getAdapterPosition())).getIdentificador());
                          // ArrayList<Object> envi = basedatos.ObtenerListadoMisEnvios();
                           envios.remove(viewHolder.getAdapterPosition());
  //                         enviostodel.remove(viewHolder.getAdapterPosition());
                           //CambiarListado(envi);
                           notifyItemRemoved(viewHolder.getAdapterPosition());
                           notifyDataSetChanged();
                           recycler.invalidate();

                           TextView numbernsend = (TextView) ((Activity) contexto).findViewById(R.id.numbernsend);
                           int sinenv = basedatos.SinEnviar();
                           if (sinenv > 0)
                               numbernsend.setText(sinenv + " sin enviar");
                       }
                        else if(  envios.get(viewHolder.getAdapterPosition())  instanceof Publicacion  ) {
                           basedatos.Eliminar_MiPublicacion(((Publicacion) envios.get(viewHolder.getAdapterPosition())).getIdent());

                           envios.remove(viewHolder.getAdapterPosition());
//                           enviostodel.remove(viewHolder.getAdapterPosition());
                           //CambiarListado(envi);
                           notifyItemRemoved(viewHolder.getAdapterPosition());
                           notifyDataSetChanged();

                           // ArrayList<Object> envi = basedatos.ObtenerListadoMisEnvios();
                          // CambiarListado(envi);
                           //notifyItemRemoved(viewHolder.getAdapterPosition());
                          // notifyDataSetChanged();
                          // recycler.invalidate();

                           //notifyItemRangeChanged(viewHolder.getAdapterPosition(), envi.size());
                           TextView numbernsend = (TextView) ((Activity) contexto).findViewById(R.id.numbernsend);
                           int sinenv = basedatos.SinEnviar();
                           if (sinenv > 0)
                               numbernsend.setText(sinenv + " sin enviar");
                       }
                        else
                       {
                           Toast.makeText(contexto,"Error al borrar",Toast.LENGTH_LONG).show();
                       }



                    }
                };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recycler);
    }


   /* public void CambiarListado(ArrayList<Object> env)
    {
        this.envios = env;
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(contexto);

        if (viewType == MComentario )
        {
            View v1 = inflater.inflate(R.layout.item_micomentario, parent, false);
            viewHolder = new ViewHolder_Micomentario(v1);

        }
        else
        {
                View v2 = inflater.inflate(R.layout.vista_item_list_publicacion, parent, false);
                viewHolder = new Secciones_ViewHolder(v2);
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        switch (holder.getItemViewType()) {
            case MComentario:
            {
                ViewHolder_Micomentario vh1 = (ViewHolder_Micomentario) holder;
                ConfigureViewHolderComentario(vh1, position);
                break;
            }

            case MPublicacion:
            {
                Secciones_ViewHolder vh2 = (Secciones_ViewHolder) holder;
                ConfigureViewHolderPublicacion(vh2, position);
                break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return envios.size();
    }

    @Override
    public int getItemViewType(int position) {
        //More to come
        if (envios.get(position) instanceof Comentario) {
            return MComentario ;
        } else
        {
            return MPublicacion ;
        }

    }


 public  void ConfigureViewHolderComentario(ViewHolder_Micomentario holder, final int position)
 {
     Publicacion publi = basedatos.Obtener_PublicacionPorIdentificador(((Comentario)envios.get(position)).getEntrada());
     String pub = publi.getTitulo();
     holder.tvTituloPub.setText(pub);

     Drawable imgencat = soporte.ImgPorSubCategoria(publi.getSubcategoria(),publi.getCategoria());
     holder.catpub.setImageDrawable(imgencat);

     holder.ratingcoment.setRating(((Comentario)envios.get(position)).getRating());

     holder.tvContenidoComen.setText(((Comentario)envios.get(position)).getComentario());
     if(((Comentario)envios.get(position)).isEnviado()==0)
         holder.tvfecha.setText("Sin enviar");
     else
         holder.tvfecha.setText(((Comentario)envios.get(position)).getFechaConfigurada());
     holder.comentario.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent publicacion = new Intent("cu.fullapp.PUBLICACION");
             publicacion.putExtra("identificador",((Comentario)envios.get(position)).getEntrada());
          //   publicacion.putExtra("posicion",position);
             contexto.startActivity(publicacion);
         }
     });

    /* holder.comentario.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
    if(Build.VERSION.SDK_INT>19)
    {
        if(event.getAction() == MotionEvent.ACTION_MOVE ) {

        int diferencia = Math.abs((int) (event.getHistoricalX(0) - event.getX()));
        if (diferencia > 6) {
            basedatos.Eliminar_MiComentario(((Comentario) envios.get(position)).getIdentificador());
            envios = basedatos.ObtenerListadoMisEnvios();
            notifyItemRemoved(position);

            ViewGroup x = (ViewGroup) v.getParent();
            x.removeView(v);

            final TextView numbernsend = (TextView) ((Activity) contexto).findViewById(R.id.numbernsend);
            int sinenv = basedatos.SinEnviar();
            if (sinenv > 0)
                numbernsend.setText(sinenv + " sin enviar");
        }


    }
             }

             return false;
         }


     });*/
 }


    public  void ConfigureViewHolderPublicacion(final Secciones_ViewHolder holder,final int position)
    {
       // if (! ((Publicacion) envios.get(position)).isVisitada())
             holder.nuevapub.setVisibility(View.INVISIBLE);

        holder.tvTituloPub.setText(((Publicacion)envios.get(position)).getTitulo());

        holder.tvPrecioPub.setText(((Publicacion)envios.get(position)).getPrecio() + " " + ((Publicacion)envios.get(position)).getMoneda());
        //holder.tvMonedaPub.setText(((Publicacion)envios.get(position)).getMoneda());

        // color por categoria
        Integer color = soporte.ColorPorCategoria(((Publicacion)envios.get(position)).getCategoria());
       // holder.categoria.setImageResource(color);

//rating
        //holder.ratBarpub.setRating(((Publicacion)envios.get(position)).getValoracion());
        holder.ratBarpub.setVisibility(View.INVISIBLE);
        holder.opciones.setVisibility(View.INVISIBLE);
        //holder.opciones.setImageResource(R.drawable.img_comunicarse);
        if(((Publicacion)envios.get(position)).isEnviado()==0)
        {
            holder.comentarios.setText("Sin Enviar");
        }
        else {
            holder.comentarios.setText("Enviado");
        }

        if(((Publicacion)envios.get(position)).getBusco()>0)
          holder.pubbusco.setVisibility(View.VISIBLE);
        else
           holder.pubbusco.setVisibility(View.INVISIBLE);

       // holder.tvfecha.setText("Sin Enviar");
       // else
       // holder.tvfecha.setText(((Publicacion)envios.get(position)).getFechaConfigurada());
        //subcategoria
        Drawable imagensubcat = soporte.ImgPorSubCategoria(((Publicacion)envios.get(position)).getSubcategoria(), ((Publicacion)envios.get(position)).getCategoria());
        holder.subcategoria.setImageDrawable(imagensubcat);

        //  final View vist = holder.vpublicacion;

    /*    if (((Publicacion)envios.get(position)).isVisitada()) {
            holder.nuevapub.setVisibility(View.INVISIBLE);
        }*/



    //    final int posicion = holder.getAdapterPosition();
        holder.vpublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recycler.isLayoutFrozen())
                {
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Publicacion publicacion = (Publicacion)envios.get(position);
                        // We can access the data within the views
                        if(publicacion.isEnviado()==0) {
                            Intent publicaci = new Intent("cu.fullapp.NUEVA_PUBLICACION");
                            publicaci.putExtra("identificador", ((Publicacion) envios.get(position)).getIdent());
                            publicaci.putExtra("posicion", position);
                            contexto.startActivity(publicaci);
                            ((Activity)contexto).finish();

                        }
                        else
                        {
                            Intent publicaci = new Intent("cu.fullapp.PUBLICACION");
                           // publicaci.putExtra("identificador", ((Publicacion) envios.get(position)).getIdent());

                            ArrayList<Publicacion> mispubspub = new ArrayList<Publicacion>();////basedatos.MisPublicacionesPublicadas( ((Publicacion) envios.get(position)).getIdent() );
                            publicaci.putExtra("posicion", 0);
                            mispubspub.add((Publicacion)envios.get(position));
                            publicaci.putParcelableArrayListExtra("ultimalista",mispubspub);
                            contexto.startActivity(publicaci);
                        }
                    }
                }
                else
                {
                    Toast.makeText(contexto,"Debe cerrar cerrar las opciones antes de continuar",Toast.LENGTH_LONG).show();
                }

            }
        });

     /*   holder.vpublicacion.setOnTouchListener(new View.OnTouchListener() {
                                                   @Override
                                                   public boolean onTouch(View v, MotionEvent event) {
                                                       if(Build.VERSION.SDK_INT>19) {
                                                           if (event.getAction() == MotionEvent.ACTION_MOVE) {
                                                               int diferencia = Math.abs((int) (event.getHistoricalX(0) - event.getX()));
                                                               if (diferencia > 8) {

                                                                   basedatos.Eliminar_MiPublicacion(((Publicacion) envios.get(position)).getIdent());
                                                                   envios = basedatos.ObtenerListadoMisEnvios();
                                                                   notifyItemRemoved(position);
                                                                   notifyItemRangeChanged(position, envios.size());

                                                                   ViewGroup x = (ViewGroup) v.getParent();
                                                                   x.removeView(v);

                                                                   final TextView numbernsend = (TextView) ((Activity) contexto).findViewById(R.id.numbernsend);
                                                                   int sinenv = basedatos.SinEnviar();
                                                                   if (sinenv > 0)
                                                                       numbernsend.setText(sinenv + " sin enviar");
                                                               }
                                                           }
                                                       }
                                                       return false;
                                                   }
                                               });*/

      /*  holder.opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!recycler.isLayoutFrozen())
                {
                    View comones_opcion = ((AppCompatActivity)contexto).getLayoutInflater().inflate(R.layout.opciones_comunicacion, (ViewGroup) holder.itemView);
                    recycler.setLayoutFrozen(true);
                    ImageView cerrar = (ImageView) comones_opcion.findViewById(R.id.comones_cerrar);
                    ImageView borrar = (ImageView) comones_opcion.findViewById(R.id.comones_borrar);
                    ImageView btllamar = (ImageView) comones_opcion.findViewById(R.id.comones_telefono);
                    ImageView btsms = (ImageView) comones_opcion.findViewById(R.id.comones_sms);
                    ImageView btcorreo = (ImageView) comones_opcion.findViewById(R.id.comones_correo);
                    ImageView btcomentar = (ImageView) comones_opcion.findViewById(R.id.comones_comentar);
                    ImageView btcompartir = (ImageView) comones_opcion.findViewById(R.id.comones_compartir);

                    btllamar.setVisibility(View.INVISIBLE);
                    btsms.setVisibility(View.INVISIBLE);
                    btcorreo.setVisibility(View.INVISIBLE);
                    btcompartir.setVisibility(View.INVISIBLE);

                    recycler.setLayoutFrozen(true);
                    btcomentar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recycler.setLayoutFrozen(false);
                            DialogFragment comentario = new Vista_Nueco_Comentario();
                            Bundle argu = new Bundle();
                            argu.putString("identificador", ((Publicacion) envios.get(position)).getIdent());
                            comentario.setArguments(argu);
                            comentario.show( ((AppCompatActivity) contexto).getSupportFragmentManager(), "interfaz_comentario");

                            ViewGroup x = (ViewGroup) v.getParent();
                            ViewGroup x2 = (ViewGroup) x.getParent();
                            x2.removeView((View) x);
                        }


                    });
                    btllamar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recycler.setLayoutFrozen(false);
                            Usuario autor = basedatos.Usuario_por_Correo(((Publicacion)envios.get(position)).getCorreo_usuario());
                            comunicaciones.Llamar(autor.getTelefono());
                            ViewGroup x = (ViewGroup) v.getParent();
                            ViewGroup x2 = (ViewGroup) x.getParent();
                            x2.removeView((View) x);
                        }
                    });


                    btsms.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recycler.setLayoutFrozen(false);
                            Usuario autor = basedatos.Usuario_por_Correo(((Publicacion)envios.get(position)).getCorreo_usuario());
                            comunicaciones.EnviarSMS(autor.getTelefono());
                            ViewGroup x = (ViewGroup) v.getParent();
                            ViewGroup x2 = (ViewGroup) x.getParent();
                            x2.removeView((View) x);
                        }
                    });

                    btcorreo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recycler.setLayoutFrozen(false);
                            Usuario autor = basedatos.Usuario_por_Correo(((Publicacion)envios.get(position)).getCorreo_usuario());
                            comunicaciones.Lanzar_Correo(autor.getCorreo(),((Publicacion)envios.get(position)).getTitulo());
                            ViewGroup x = (ViewGroup) v.getParent();
                            ViewGroup x2 = (ViewGroup) x.getParent();
                            x2.removeView((View) x);
                        }
                    });


                    cerrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recycler.setLayoutFrozen(false);
                            ViewGroup x = (ViewGroup) v.getParent();
                            ViewGroup x2 = (ViewGroup) x.getParent();
                            x2.removeView((View) x);

                        }
                    });
                    borrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recycler.setLayoutFrozen(false);
                            basedatos.Eliminar_MiPublicacion(((Publicacion)envios.get(position)).getIdent());
                            envios = basedatos.ObtenerListadoMisEnvios();
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, envios.size());
                            ViewGroup x = (ViewGroup) v.getParent();
                            ViewGroup x2 = (ViewGroup) x.getParent();
                            x2.removeView((View) x);

                            final TextView numbernsend = (TextView) ((Activity)contexto).findViewById(R.id.numbernsend);
                            int sinenv = basedatos.SinEnviar();
                            if(sinenv>0)
                                numbernsend.setText(sinenv +" sin enviar");

                        }
                    });
                }
                else
                {
                    Toast.makeText(contexto,"Debe cerrar cerrar las opciones antes de continuar",Toast.LENGTH_LONG).show();
                }
            }


        });*/




    }




}
