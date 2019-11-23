package cu.fullapp;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by renier on 21/09/2016.
 */
class Clase_AdaptadorPublicaciones extends RecyclerView.Adapter  {

    ArrayList<Publicacion> list_publicaciones;
    Context contexto;
    Fragment fragmento_padre;
    boolean islarge;
    int cantidad;
    Integer layout;
    boolean isportada;
    int ancho, alto;
    Drawable img_comunca;
    AppCompatActivity actividad;
    Soporte_Elementos_Comunes soporte;
    Manejo_DB basedatos;
    RecyclerView recycler;
    Soporte_Comunicaciones comunicaciones;
    boolean isbusqueda = false;
    String busqtexto="";
    ArrayList<Integer> busqfiltrosprovincias = new ArrayList<Integer>();
    ArrayList<String> busqfiltrossubcatsa = new ArrayList<String>();
    float preciomaximo,preciominimo;
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    String categoria;

    public Clase_AdaptadorPublicaciones(ArrayList<Publicacion> pubs, Context cont, Fragment padre, final boolean portada, RecyclerView rec, String cat) {

       list_publicaciones = pubs;

        contexto = cont;
        actividad = ((AppCompatActivity) contexto);
        fragmento_padre = padre;
        isportada = portada;
        alto = ((WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        ancho = ((WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        islarge = ancho > 600;


            layout = R.layout.vista_item_list_publicacion;


        soporte = new Soporte_Elementos_Comunes(contexto);
        // img_comunca = soporte.PintarIconos(R.drawable.img_comunicarse, R.color.colorPrincipalIconos);
        //  img_comunca =
        basedatos = new Manejo_DB(cont);
        recycler = rec;
        categoria = cat;
        comunicaciones = new Soporte_Comunicaciones(contexto);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View vista = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        // set the view's size, margins, paddings and layout parameters

        Secciones_ViewHolder vhold = new Secciones_ViewHolder(vista);

        return vhold;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Publicacion publicacionactual = list_publicaciones.get(position);

        ((Secciones_ViewHolder) holder).nuevapub.setImageResource(R.drawable.img_noleidos);
        if (publicacionactual.isVisitada())
            ((Secciones_ViewHolder) holder).nuevapub.setVisibility(View.INVISIBLE);
        else
            ((Secciones_ViewHolder) holder).nuevapub.setVisibility(View.VISIBLE);

        ((Secciones_ViewHolder) holder).tvTituloPub.setText(publicacionactual.getTitulo());

        ((Secciones_ViewHolder) holder).tvPrecioPub.setText(publicacionactual.getPrecio() + "  " + publicacionactual.getMoneda());
        // ((Secciones_ViewHolder) holder).tvMonedaPub.setText(publicacionactual.getMoneda());

        // color por categoria
        //  Integer color = soporte.ColorPorCategoria(publicacionactual.getCategoria());
        // ((Secciones_ViewHolder) holder).categoria.setImageResource(color);

//rating
        ((Secciones_ViewHolder) holder).ratBarpub.setRating(publicacionactual.getValoracion());

        ((Secciones_ViewHolder) holder).opciones.setImageResource(R.drawable.img_delete);

        int numcomentarios = basedatos.CantComentariosEntrada(publicacionactual.getIdent());
        ((Secciones_ViewHolder) holder).comentarios.setText(numcomentarios + "");
        //subcategoria


        if (publicacionactual.getBusco() > 0)
            ((Secciones_ViewHolder) holder).pubbusco.setVisibility(View.VISIBLE);
        else
            ((Secciones_ViewHolder) holder).pubbusco.setVisibility(View.INVISIBLE);



        if (publicacionactual.getIdent().endsWith("ghost")) {

            ((Secciones_ViewHolder) holder).subcategoria.setImageDrawable(ImagenPromocion(publicacionactual.getIdent()));
        } else {
            Drawable imagensubcat = soporte.ImgPorSubCategoria(publicacionactual.getSubcategoria(), publicacionactual.getCategoria());
            ((Secciones_ViewHolder) holder).subcategoria.setImageDrawable(imagensubcat);
        }


        //  ( (Secciones_ViewHolder) holder).autor.setText(basedatos.Usuario_por_Correo(publicacionactual.getCorreo_usuario()).getNombre());

        // ( (Secciones_ViewHolder) holder).provincia.setImageDrawable(soporte.ImagenesRedondeadas( soporte.ImagenesPorprovincia[publicacionactual.getProvincia()]));

        //   final int posicion = holder.getAdapterPosition();
        ((Secciones_ViewHolder) holder).vpublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (!recycler.isLayoutFrozen())
                //  {
                if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it

                       /* if(Build.VERSION.SDK_INT>=21)
                        {
                          //  int centroX,centroY;
                           // centroX = ((Secciones_ViewHolder) holder).vpublicacion.getWidth()/2;
                           // centroY = ((Secciones_ViewHolder) holder).vpublicacion.getHeight()/2;

                            //ViewAnimationUtils.createCircularReveal(((Secciones_ViewHolder) holder).vpublicacion,centroX,centroY,0,(int)(centroX*1.3)).setDuration(350).start();



                            Log.d("a","animo");
                        }*/


                    Publicacion publicacion = list_publicaciones.get(position);
                    ;
                    // We can access the data within the views
                    Intent publicaci = new Intent("cu.fullapp.PUBLICACION");
                    //publicaci.putExtra("identificador", publicacionactual.getIdent());
                    publicaci.putExtra("posicion", position);
                       /* if(isbusqueda)
                        {
                            publicaci.putExtra("ultimalista",basedatos.last_query_perform);
                        }
                        else {
                            publicaci.putExtra("ultimalista","SELECT * FROM Publicaciones where categoria = '" + categoria + "'  ORDER BY fecha DESC");
                        }*/


                    //publicaci.putExtra("buscando",isbusqueda);
                    publicaci.putParcelableArrayListExtra("ultimalista", list_publicaciones);


                    // Pair<View, String>  transicion2 = new Pair<>( (View)((Secciones_ViewHolder) holder).tvTituloPub,contexto.getString(R.string.transiciontit));
                    // Pair<View, String>  transicion3 = new Pair<>( (View)((Secciones_ViewHolder) holder).autor,contexto.getString(R.string.transicionautor));
                    // Pair<View, String>  transicion4 = new Pair<>( (View)((Secciones_ViewHolder) holder).provincia,contexto.getString(R.string.transicionprov));
                    // transiciones.add(transicion1);
                    // Bundle transiciones = ActivityOptionsCompat.makeSceneTransitionAnimation(actividad,transicion1,transicion2,transicion3,transicion4).toBundle();

                    //publicaci.putExtra( "transiciontit" , ActivityOptionsCompat.makeSceneTransitionAnimation(actividad,((Secciones_ViewHolder) holder).tvTituloPub,contexto.getString(R.string.transiciontit)).toBundle());
                    //publicaci.putExtra( "transicionautor" , ActivityOptionsCompat.makeSceneTransitionAnimation(actividad,((Secciones_ViewHolder) holder).subcategoria,contexto.getString(R.string.transicionimg)).toBundle());
                    // publicaci.putExtra( "transicion" , ActivityOptionsCompat.makeSceneTransitionAnimation(actividad,((Secciones_ViewHolder) holder).tvTituloPub,contexto.getString(R.string.transiciontit)).toBundle());
                    // ViewGroup padre = (ViewGroup) actividad.findViewById(android.R.id.content);

                    if (!publicacion.isVisitada())
                        basedatos.SetVisitada(publicacion.getIdent(), 1);
                    if (actividad instanceof Anuncios && isbusqueda == false) {
                        ViewPager pager = (ViewPager) actividad.findViewById(R.id.pager);
                        pager.getAdapter().notifyDataSetChanged();
                        pager.invalidate();
                        notifyItemChanged(position);
                    }
                       /* if(Build.VERSION.SDK_INT>=19)
                        {
                            //  Fade ocultar = new Fade(Fade.OUT);
                            //ViewGroup padre = (ViewGroup) actividad.findViewById(android.R.id.content);
                            //TransitionManager.go(Scene.getSceneForLayout(padre,R.layout.publicacion_completa,contexto));

                            //Slide desplazar = new Slide(Gravity.LEFT);
                            Fade ocultar = new Fade(Fade.OUT);
                            ocultar.set
                            //change.

                        }*/
                        /*if(Build.VERSION.SDK_INT>=21)
                        {
                            Slide desplazar = new Slide(Gravity.BOTTOM);
                            View vist = actividad.findViewById(R.id.app_barlayout);
                            desplazar.addTarget(vist);
                            desplazar.setInterpolator(AnimationUtils.loadInterpolator(
                                    contexto,android.R.interpolator.accelerate_decelerate
                            ));
                            desplazar.setDuration(800);
                            actividad.getWindow().setExitTransition(desplazar);

                        }*/

                    actividad.startActivity(publicaci);

                }
                //   }
                  /*  else
                    {
                        Toast.makeText(contexto,"Debe cerrar cerrar las opciones antes de continuar",Toast.LENGTH_LONG).show();
                    }*/

            }
        });


            ((Secciones_ViewHolder) holder).opciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    basedatos.Eliminar_Publicacion(list_publicaciones.get(position).getIdent());

                    // if (isportada)
                    //   list_publicaciones = basedatos.Ultimas_Publicaciones();
                    if (isbusqueda)
                        EjecutarBuscar(busqtexto, busqfiltrosprovincias, busqfiltrossubcatsa, categoria, preciomaximo, preciominimo);
                    else
                        list_publicaciones = basedatos.Obtener_PublicacionesPorCategoria(categoria);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list_publicaciones.size());
                    ViewGroup x = (ViewGroup) v.getParent();
                    // ViewGroup x2 = (ViewGroup) x.getParent();
                    x.removeView(v);

                }
            });

    }
                   /* if (!recycler.isLayoutFrozen())
                    {
                        View comones_opcion = actividad.getLayoutInflater().inflate(R.layout.opciones_comunicacion, (ViewGroup) holder.itemView);
                        recycler.setLayoutFrozen(true);
                        ImageView cerrar = (ImageView) comones_opcion.findViewById(R.id.comones_cerrar);
                        ImageView borrar = (ImageView) comones_opcion.findViewById(R.id.comones_borrar);
                        if(isportada)
                            borrar.setVisibility(View.GONE);
                        ImageView btllamar = (ImageView) comones_opcion.findViewById(R.id.comones_telefono);
                        ImageView btsms = (ImageView) comones_opcion.findViewById(R.id.comones_sms);
                        ImageView btcorreo = (ImageView) comones_opcion.findViewById(R.id.comones_correo);
                        ImageView btcomentar = (ImageView) comones_opcion.findViewById(R.id.comones_comentar);
                        ImageView btcompartir = (ImageView) comones_opcion.findViewById(R.id.comones_compartir);
                        recycler.setLayoutFrozen(true);
                        btcomentar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recycler.setLayoutFrozen(false);
                                //Intent coment = new Intent("cu.fullapp.NEWCOMMENT");
                                //coment.putExtra("publicacion", list_publicaciones.get(position).getIdent());
                                //contexto.startActivity(coment);

                                DialogFragment comentario = new Vista_Nueco_Comentario();
                                Bundle argu = new Bundle();
                                argu.putString("identificador",list_publicaciones.get(position).getIdent());
                                comentario.setArguments(argu);
                                comentario.show( actividad.getSupportFragmentManager(), "interfaz_comentario");

                                ViewGroup x = (ViewGroup) v.getParent();
                                ViewGroup x2 = (ViewGroup) x.getParent();
                                x2.removeView((View) x);


                            }


                        });

                        btcompartir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                AlertDialog.Builder compartir = new AlertDialog.Builder(contexto);
                                final EditText destinatarios = new EditText(contexto);

                                destinatarios.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                compartir.setMessage("Introduzca los correos de las personas con quienes desea compartir esta entrada, hágalo separandolos solo por punto y coma. \';\'")
                                        .setTitle("COMPARTIR")
                                        .setView(destinatarios)
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();


                                            }
                                        })
                                        .setPositiveButton("Compartir", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ArrayList<String> reciben = new ArrayList<String>();
                                                boolean allgood = false;
                                                if(destinatarios.getText().toString().compareTo("")!=0)
                                                {
                                                    String [] tdestinatarios = destinatarios.getText().toString().split(";");
                                                    for (String mail:tdestinatarios)
                                                    {
                                                        String patron =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";;
                                                        boolean correovalido = mail.matches(patron);
                                                        if(correovalido)
                                                        {
                                                            reciben.add(mail);
                                                            allgood = true;
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(contexto,"Hay correos invalidos en la lista",Toast.LENGTH_LONG).show();
                                                            allgood=false;
                                                            break;
                                                        }

                                                    }
                                                    if(allgood) {
                                                        String texto = list_publicaciones.get(position).paraCompartir(contexto);
                                                        new Correo_Enviar_AsyncTask(actividad,null).execute(reciben, "Tomado de Facil App", texto,contexto);
                                                    }

                                                }

                                            }
                                        });

                                Dialog compart = compartir.create();
                                compart.show();
                            }
                        });

                        btllamar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recycler.setLayoutFrozen(false);
                                Usuario autor = basedatos.Usuario_por_Correo(list_publicaciones.get(position).getCorreo_usuario());
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
                                Usuario autor = basedatos.Usuario_por_Correo(list_publicaciones.get(position).getCorreo_usuario());
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
                                Usuario autor = basedatos.Usuario_por_Correo(list_publicaciones.get(position).getCorreo_usuario());
                                comunicaciones.Lanzar_Correo(autor.getCorreo(),list_publicaciones.get(position).getTitulo());
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
                                basedatos.Eliminar_Publicacion(list_publicaciones.get(position).getIdent());

                                if (isportada)
                                    list_publicaciones = basedatos.Ultimas_Publicaciones();
                                else if (isbusqueda)
                                    EjecutarBuscar(busqtexto,busqfiltrosprovincias,busqfiltrossubcatsa,categoria,preciomaximo,preciominimo);
                                else
                                    list_publicaciones = basedatos.Obtener_PublicacionesPorCategoria(categoria);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list_publicaciones.size());
                                ViewGroup x = (ViewGroup) v.getParent();
                                ViewGroup x2 = (ViewGroup) x.getParent();
                                x2.removeView((View) x);

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(contexto,"Debe cerrar cerrar las opciones antes de continuar",Toast.LENGTH_LONG).show();
                    }
                }


            });*/
     //}


    public Drawable ImagenPromocion(String indent)
    {
        Drawable promociones = null;

        if (indent.startsWith("pc"))
        {
            promociones =  contexto.getResources().getDrawable(R.mipmap.pcpromocion);
        }
        else if (indent.startsWith("tel"))
        {
            promociones =  contexto.getResources().getDrawable(R.mipmap.samspromocion);
        }
        else if (indent.startsWith("tra"))
        {
            promociones =  contexto.getResources().getDrawable(R.mipmap.driverpromocion);
        }
        else if (indent.startsWith("cas"))
        {
            promociones =  contexto.getResources().getDrawable(R.mipmap.viviendaprom);
        }
        else if (indent.startsWith("mis"))
        {
            promociones =  contexto.getResources().getDrawable(R.mipmap.papyrusprom);
        }
        return promociones;
    }

    public void SetupTransitionNames(Secciones_ViewHolder holder,int position)
    {
        if(Build.VERSION.SDK_INT>=21)
        {
            holder.subcategoria.setTransitionName(contexto.getString(R.string.transicionimg)+position);
        }
    }

    public void EjecutarBuscar(String texto,ArrayList<Integer> filtrosprovincias,ArrayList<String> filtrossubcatsa,String categori,float preciomax,float preciomin)
    {
        busqtexto =texto;
        busqfiltrosprovincias = filtrosprovincias;
        busqfiltrossubcatsa = filtrossubcatsa;
        preciomaximo = preciomax;
        preciominimo = preciomin;
        for( int i =0; i < getItemCount() ; i++ )
        {
            View item = ((ViewGroup) recycler).getChildAt(i);
            ((ViewGroup) recycler).removeView(item);
        }
        notifyItemRangeRemoved(0,list_publicaciones.size());
        list_publicaciones = basedatos.BuscarPublicaciones(texto,filtrosprovincias,filtrossubcatsa,categori,preciomax,preciomin);
        notifyItemRangeInserted(0, list_publicaciones.size());
        isbusqueda = true;

    }



    public void LimpiarFiltros(String categoria)
    {
        isbusqueda = false;
        for( int i =0; i < getItemCount() ; i++ )
        {
            View item = ((ViewGroup) recycler).getChildAt(i);
            ((ViewGroup) recycler).removeView(item);
        }
        notifyItemRangeRemoved(0,list_publicaciones.size());
        list_publicaciones = basedatos.Obtener_PublicacionesPorCategoria(categoria);
        notifyItemRangeInserted(0, list_publicaciones.size());
    }

    public ArrayList<Publicacion> getList_publicaciones() {
        return list_publicaciones;
    }

    public void setList_publicaciones(ArrayList<Publicacion> list_publicaciones) {
        this.list_publicaciones = list_publicaciones;
    }

    public void RankearPubs(String categoria)
    {
        ArrayList<Publicacion> publicacionesordenadas =	basedatos.RankingPropuestas(categoria);
        for( int i =0; i < getItemCount() ; i++ )
        {
            View item = ((ViewGroup) recycler).getChildAt(i);
            ((ViewGroup) recycler).removeView(item);
        }
        notifyItemRangeRemoved(0,list_publicaciones.size());
        list_publicaciones = publicacionesordenadas;
        notifyItemRangeInserted(0, list_publicaciones.size());
    }


    @Override
    public int getItemCount() {
        return list_publicaciones.size();
    }



////********** View Holder


}



