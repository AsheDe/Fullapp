package cu.fullapp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yudel on 23/06/2017.
 */
public class AsyncTaskPortada2 extends AsyncTask {
        private Context contexto;


    private AppCompatActivity actividad;
    ProgressBar progressBar;
    Usuario usuario_local;
    ObjectAnimator animation;
    //RecyclerView pub_recientes;

    Manejo_DB base_datos;
    ArrayList<Publicacion> publicaciones = null;
    //RecyclerView.LayoutManager mLayoutManager;
   // Map<Integer,Float> factor_prvinciaX ;
    //Map<Integer,Float> factor_prvinciaY ;
   // ImageView mapacuba;
    //RecyclerView reciclerpubs;
    //RecyclerView.Adapter adapter;
    int posicion = 1;
    Soporte_Elementos_Comunes soporte;
    boolean reallysmall;
    public AsyncTaskPortada2(Context cont) {
        contexto = cont;
        actividad = (AppCompatActivity) contexto;
        reallysmall = contexto.getResources().getBoolean(R.bool.reallysmall);
    }

    @Override
    protected Object doInBackground(Object[] params) {


        progressBar = (ProgressBar) actividad.findViewById(R.id.progressBar);
        usuario_local = new Usuario().CRARUSERLOCAL(contexto);
        if (usuario_local != null) {
            animation = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) (usuario_local.getValoracion() * 100)); // see this max value coming back here, we animale towards that value
            animation.setDuration(500); //in milliseconds
            animation.setInterpolator(new DecelerateInterpolator());

        }
        base_datos = new Manejo_DB(contexto);
       // base_datos.SetupBaseDatos();
      //  if(!reallysmall)
       // { pub_recientes = (RecyclerView) actividad.findViewById(R.id.portada_list_nuevas_publicaciones);

       /* if (base_datos != null) {
            publicaciones = base_datos.Ultimas_Publicaciones();
            mAdapter = new Clase_Adaptador_Publicaciones_Portada (publicaciones, contexto, null, pub_recientes, null);
            mLayoutManager = new LinearLayoutManager(contexto, LinearLayoutManager.HORIZONTAL, false);


        }
        }*/

        soporte = new Soporte_Elementos_Comunes(contexto);
        boolean haydatosmovil = Soporte_Comunicaciones.datosmovilesEncendidos(actividad);
        boolean datosmovilesconectando = Soporte_Comunicaciones.datosmovilesConectando(actividad);
        if(!haydatosmovil && !datosmovilesconectando)
        {
            try{
                Soporte_Comunicaciones.setMobileDataEnabled(contexto, true);
            }
            catch (Exception e)
            {
                // publishProgress(e.getMessage());
                boolean encender =   Soporte_Comunicaciones.EncenderDatosMoviles(contexto);
                if(!encender)
                    publishProgress(contexto.getString(R.string.datosmov));
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        progressBar.clearAnimation();
        animation.start();

      /*  if (!reallysmall) {
            pub_recientes.setLayoutManager(mLayoutManager);
            pub_recientes.setItemAnimator(new DefaultItemAnimator());
            pub_recientes.addItemDecoration(new DividerItemDecoration(contexto, null));
            pub_recientes.setAdapter(mAdapter);

            final Handler manejador = new Handler();
            final Timer timer = new Timer();

            mapacuba = (ImageView) actividad.findViewById(R.id.mapa_cuba_portada);

            reciclerpubs = (RecyclerView) actividad.findViewById(R.id.portada_list_nuevas_publicaciones);
            adapter = reciclerpubs.getAdapter();
            int provincia = ((Clase_Adaptador_Publicaciones_Portada) adapter).list_publicaciones.get(4).getProvincia();

            Drawable porvinc = soporte.ImagenesRedondeadas(soporte.ImagenesPorprovincia[provincia]);
            mapacuba.setImageDrawable(porvinc);

            final TimerTask cicloespera = new TimerTask() {
                @Override
                public void run() {
                    manejador.post(new Runnable() {
                        @Override
                        public void run() {

                            if (reciclerpubs != null && adapter != null) {
                                if (reciclerpubs.getAdapter().getItemCount() > 1) {
                                    reciclerpubs.smoothScrollToPosition(posicion);

                                    int provincia = ((Clase_Adaptador_Publicaciones_Portada) adapter).list_publicaciones.get(posicion).getProvincia();
                                    // int coordenadaX = (int)(mapacuba.getX()+ mapacuba.getWidth() *factor_prvinciaX.get(provincia ));
                                    //  int coordenadaY = (int)(mapacuba.getTranslationY()+ mapacuba.getHeight() *factor_prvinciaY.get(provincia ));
                                    // provscroll.setTranslationY(coordenadaY);
                                    // provscroll.setTranslationX(coordenadaX);
                                    Drawable porvinc = soporte.ImagenesRedondeadas(soporte.ImagenesPorprovincia[provincia]);
                                    mapacuba.setImageDrawable(porvinc);
                                    // Animation sgAnimation = AnimationUtils.loadAnimation(contexto, R.anim.animacion_expandir);
                                    //provscroll.startAnimation(sgAnimation);

                                    ObjectAnimator growimg = ObjectAnimator.ofFloat(mapacuba, "scaleX", 0, 1f); // see this max value coming back here, we animale towards that value
                                    growimg.setDuration(300); //in milliseconds
                                    growimg.setInterpolator(new AccelerateDecelerateInterpolator());

                                    ObjectAnimator growimg2 = ObjectAnimator.ofFloat(mapacuba, "scaleY", 0, 1f); // see this max value coming back here, we animale towards that value
                                    growimg2.setDuration(300); //in milliseconds
                                    growimg2.setInterpolator(new AccelerateDecelerateInterpolator());
                                    growimg.start();
                                    growimg2.start();


                                    posicion++;
                                    if (reciclerpubs.getAdapter().getItemCount() == posicion) {
                                        posicion = 0;
                                    }
                                }
                                manejador.postDelayed(this, 4500);
                            } else {
                                manejador.postDelayed(this, 4500);
                            }

                        }
                    });

                }
            };

            timer.schedule(cicloespera, 4500);
        }
       // else
     //   pub_recientes.setVisibility(View.GONE);
     */
    }


}
