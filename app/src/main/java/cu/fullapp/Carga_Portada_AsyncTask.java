package cu.fullapp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by renier on 30/11/2016.
 */
public class Carga_Portada_AsyncTask extends AsyncTask implements View.OnClickListener {

    AppCompatActivity actividad;
    Context contexto;
    Soporte_Elementos_Comunes soporte;
    Usuario usuario_local = null;
    ImageView imageViewpc ;
    ImageView imageViewtel ;
    ImageView imageViewt ;
    ImageView imageViewcasa ;
    ImageView imageViewmisc;
   // CollapsingToolbarLayout collapsingToolbarLayout;

    TextView saludo;

    Manejo_DB base_datos;
    ImageView img_prov_portada;
    RelativeLayout img_fond_val_;
    AppBarLayout appBarLayout;

    boolean continuar;

    public Carga_Portada_AsyncTask( Context context ) {
    this.actividad = (AppCompatActivity)context;
        this.contexto = context;
        soporte = new Soporte_Elementos_Comunes(context);

    }


    @Override
    protected void onPreExecute() {


         imageViewpc = (ImageView) actividad.findViewById(R.id.rounded_pc);
         imageViewtel = (ImageView) actividad.findViewById(R.id.rounded_tel);
         imageViewt = (ImageView) actividad.findViewById(R.id.rounded_transp);
         imageViewcasa = (ImageView) actividad.findViewById(R.id.rounded_casa);
         imageViewmisc = (ImageView) actividad.findViewById(R.id.rounded_misc);

        imageViewpc.setOnClickListener(this);
        imageViewtel.setOnClickListener(this);
        imageViewt.setOnClickListener(this);
        imageViewcasa.setOnClickListener(this);
        imageViewmisc.setOnClickListener(this);


       // collapsingToolbarLayout = (CollapsingToolbarLayout) actividad.findViewById(R.id.colapsingtoolbar_layout_portada);
        //collapsingToolbarLayout.setTitleEnabled(false);
        appBarLayout = (AppBarLayout) actividad.findViewById(R.id.app_barlayout);

        saludo = (TextView) actividad.findViewById(R.id.portada_saludo);

        img_prov_portada = (ImageView) actividad.findViewById(R.id.portada_img_provincia);
        img_fond_val_ = (RelativeLayout) actividad.findViewById(R.id.rel_val);

        ImageView estrella = (ImageView) actividad.findViewById(R.id.star);
        estrella.setImageDrawable(soporte.PintarIconos(R.drawable.img_pub_por_destacadas, R.color.blanco));

        base_datos = new Manejo_DB(contexto);
    }


    @Override
    protected Object doInBackground(Object[] params) {

        Context context = (Context) params[0];
     //   String sinregistro = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br","No registrado",context);

       // continuar = (boolean)params[1];

        // boolean expiredtrialperiod =  base_datos.ExpiroPeriododeEvaluacion(); // debe venir de bd y fichero etc
       // PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("db11k9t","1" ).apply();
     //   if (sinregistro.compareTo("No registrado")==0 && continuar==false ) {

       //     return "sinregistro";
       // }
      //  else
       // {


                ArrayList<Object> resultados = new ArrayList<Object>();
                if (base_datos != null) {
                   ////base_datos.SetupBaseDatos();


                    //// metodo  auxiliar
                //    PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("cant_post","0").commit();
                    usuario_local = new Usuario().CRARUSERLOCAL(context);
                    resultados.add(usuario_local);

                  //  base_datos.ActualizarPatrocinados();
                }
                else {

                    return "problemabasedatos";
                }

                //new Correo_Leer_AsyncTask(actividad).execute();             //// mandar a leer el correo vamos a ver como resulta esto....

       // resultados.add(soporte.Avatar());


              //  img_prov_portada.setX((int)(width*0.28));

                Drawable icon_cir = soporte.PintarIconos(R.drawable.circle, R.color.colorFinal_progress);
                resultados.add(icon_cir);
                //img_fond_val_.setX((int)(width*0.19));
//-  PARTE MEDIA -----------
                ///---- Pubs recientes


                ///---- comentarios recientes


         //   String fechar =   Soporte_Elementos_Comunes.obtener_preferencia_encriptada("ac42txf","Sinfecha",context);


           /* if(fechar.compareTo("Sinfecha")!=0)
            {

                if(Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br","No registrado",context).compareTo("APLICACIÓN ACTIVADA")==0) {

                   String periodo =Soporte_Elementos_Comunes.obtener_preferencia_encriptada("oft963p", "0",context);
                   String codigoreg = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("xbn5pz8","no code",contexto);
                   try {
                       codigoreg = Soporte_Elementos_Comunes.Desencriptar(codigoreg,contexto);
                   }
                   catch (Exception exc)
                   {
                       publishProgress(exc.getMessage());
                       SystemClock.sleep(2500);
                       actividad.finish();
                   }

                    if(codigoreg.matches("<\\d+/><\\d+/><"+usuario_local.getCorreo()+"/>") || codigoreg.matches("<\\d+/><\\d+/><"+usuario_local.getTelefono()+"/>") || codigoreg.matches("<\\d+/><\\d+/><"+"53"+usuario_local.getTelefono()+"/>"))
                    {
                        String [] datos  = codigoreg.split("/>");
                        datos[0] = datos[0].replaceFirst("<","");
                        datos[1] = datos[1].replaceFirst("<","");
                        long ultimafecha = Long.parseLong(fechar);

                        if (Long.parseLong(datos[1]) == ultimafecha)
                        {
                            String diasleft = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("db11k9t", "0",context);

                            int diasl = Integer.parseInt(diasleft);  // dias restantes
                            long ahora = new Date().getTime();
                            long fechact = Long.parseLong(fechar);
                            int iperiodo = Integer.parseInt(periodo);
                            int dias = (int) ((ahora - fechact) / 86400000);  /// dias q han pasado desde que activaste
                            if (dias > iperiodo) {
                                Soporte_Elementos_Comunes.cambiar_preferencia_encritada ("db11k9t", "0",context);
                                Soporte_Elementos_Comunes.cambiar_preferencia_encritada("kr9t4br", "USUARIO REGISTRADO",context);

                            }
                            else{
                                int timeleft = iperiodo-dias;
                                Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t", timeleft + "",context);

                            }
                        }
                        else
                        {
                            publishProgress("Se ha violado la seguridad de la aplicación 0.");
                            SystemClock.sleep(2500);

                           actividad.finish();
                        }
                    }
                    else
                    {
                        publishProgress("Se ha violado la seguridad de la aplicación.");
                        SystemClock.sleep(2500);

                        actividad.finish();
                    }

                }
               /* }catch (Exception exc)
                {
                    Toast.makeText(context,exc.getMessage(),Toast.LENGTH_LONG).show();
                }*/
          /*  }
            else
            {

               publishProgress("Se ha violado la seguridad de la aplicación.");
                SystemClock.sleep(2500);

                actividad.finish();
            }*/
               return resultados;

        }






    @Override
    public void onPostExecute(Object result) {
      //  if (result instanceof String)
       // {
         //   if (((String) result).compareTo("sinregistro") == 0) {
          //      ((Portada_Fragment)actividad).registro();
           // }
        //}
        if (result instanceof ArrayList)
        {
            //imgr = null
            Drawable icon_cir = null;
            ArrayList<Object> resultado = ((ArrayList<Object>) result);

            for (int i = 0; i < resultado.size(); i++) {
                switch (i) {
                    case 0: {
                        usuario_local = (Usuario) resultado.get(0);
                        break;
                    }
                 /*   case 1: {
                        imgr = (Drawable) resultado.get(1);
                        break;
                    }*/
                    case 1: {
                        icon_cir = (Drawable) resultado.get(1);
                        break;
                    }

                    /*case 3: {

                        break;
                    }*/


                }
            }
            soporte.Avatar(contexto,img_prov_portada);
            String sinregistro = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br","No registrado",contexto);
            Button  botonregistroportada  = (Button) actividad.findViewById(R.id.botonregistroportada);
            if (sinregistro.compareTo("No registrado")==0 )
            {
                botonregistroportada.setText("Registrarse");
                Toast.makeText(contexto,"REGÍSTRATE para que puedas publicar, es TODO GRATIS!!!!!",Toast.LENGTH_LONG).show();
                botonregistroportada.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        soporte.MostrarRegistro();
                    }
                });
            }
            else if ( sinregistro.compareTo("USUARIO REGISTRADO")==0 || sinregistro.compareTo("APLICACIÓN ACTIVADA")==0 )
            {

                //String dias = soporte.obtener_preferencia_encriptada("db11k9t","0",contexto);
                botonregistroportada.setText("CONTRIBUIR " );
                botonregistroportada.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Soporte_Elementos_Comunes.MostrarActivacion(contexto);
                    }
                });
            }


            saludo.setText("Hola, " + usuario_local.getNombre() + " !");


          //  img_prov_portada.setImageDrawable(imgr);

            ImageView img_fond_val_portada = (ImageView) actividad.findViewById(R.id.port_backg_valoracion);
            img_fond_val_portada.setImageDrawable(icon_cir);
            TextView portada_tvval = (TextView) actividad.findViewById(R.id.portada_valtv);

            float valor = usuario_local.getValoracion();


            if(Float.isNaN(valor))
            {
                valor = 1;
                PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("valoracion","1").apply();
            }

            portada_tvval.setText( valor  + "");
///////////////////publicaciones



            ////// Cardview
            if (usuario_local != null) {
                TextView actlic = (TextView) actividad.findViewById(R.id.actv_lic_portada);
                actlic.setText(usuario_local.getActividad_lic());

                TextView mvotos = (TextView) actividad.findViewById(R.id.uservotosport);
                mvotos.setText(usuario_local.getCantidad_votos()+"");
                TextView mpublicaciones = (TextView) actividad.findViewById(R.id.userpublicacionesport);
                mpublicaciones.setText(usuario_local.getCant_post()+"");
                TextView mranking = (TextView) actividad.findViewById(R.id.userrankingport);
                mranking.setText(usuario_local.getRanking()+"");
            }
            //////////////parte baja





            imageViewpc.setImageDrawable(soporte.ImagenesRedondeadas(R.drawable.img_pc_real));
            imageViewtel.setImageDrawable(soporte.ImagenesRedondeadas(R.drawable.img_telefono_real));
            imageViewt.setImageDrawable(soporte.ImagenesRedondeadas(R.drawable.img_almendron));
            imageViewcasa.setImageDrawable(soporte.ImagenesRedondeadas(R.drawable.img_casa_real));
            imageViewmisc.setImageDrawable(soporte.ImagenesRedondeadas(R.drawable.img_misce_real));


        }




    }



    @Override
    protected void onProgressUpdate(Object[] values) {
        Toast.makeText(contexto,(String)values[0],Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rounded_pc:
            {
                Intent iniciarist = new Intent("cu.fullapp.ANUNCIOS");
                iniciarist.putExtra("categoria",0);
                if(Build.VERSION.SDK_INT>=16)
                    actividad.startActivity(iniciarist,Transiciones());
                else
                    actividad.startActivity(iniciarist);
                break;
            }
            case R.id.rounded_tel:
            {
                Intent iniciarist = new Intent("cu.fullapp.ANUNCIOS");
                iniciarist.putExtra("categoria",1);
                if(Build.VERSION.SDK_INT>=16)
                    actividad.startActivity(iniciarist,Transiciones());
                else
                    actividad.startActivity(iniciarist);
                break;
            }
            case R.id.rounded_transp:
            {
                Intent iniciarist = new Intent("cu.fullapp.ANUNCIOS");
                iniciarist.putExtra("categoria",2);
                if(Build.VERSION.SDK_INT>=16)
                    actividad.startActivity(iniciarist,Transiciones());
                else
                    actividad.startActivity(iniciarist);
                break;
            }
            case R.id.rounded_casa:
            {
                Intent iniciarist = new Intent("cu.fullapp.ANUNCIOS");
                iniciarist.putExtra("categoria",3);
                if(Build.VERSION.SDK_INT>=16)
                    actividad.startActivity(iniciarist,Transiciones());
                else
                    actividad.startActivity(iniciarist);
                break;
            }
            case R.id.rounded_misc:
            {
                Intent iniciarist = new Intent("cu.fullapp.ANUNCIOS");
                iniciarist.putExtra("categoria",4);
                if(Build.VERSION.SDK_INT>=16)
                    actividad.startActivity(iniciarist,Transiciones());
                else
                    actividad.startActivity(iniciarist);
                break;
            }

        }

    }

    public Bundle Transiciones()
    {


        Pair<View, String> transicioncolaps = new Pair<>( (View)(appBarLayout),contexto.getString(R.string.transicion_portada_colaps));
        Pair<View, String> transicionpc = new Pair<>( (View)(imageViewpc),contexto.getString(R.string.transicion_portada_pc));
        Pair<View, String> transicioncel = new Pair<>( (View)(imageViewtel),contexto.getString(R.string.transicion_portada_cel));
        Pair<View, String> transiciontrans = new Pair<>( (View)(imageViewt),contexto.getString(R.string.transicion_portada_trans));
        Pair<View, String> transicionhome = new Pair<>( (View)(imageViewcasa),contexto.getString(R.string.transicion_portada_home));
        Pair<View, String> transicionmisc = new Pair<>( (View)(imageViewmisc),contexto.getString(R.string.transicion_portada_misc));
        if(Build.VERSION.SDK_INT>=21)
        {
           // RecyclerView listado = (RecyclerView) appBarLayout.findViewById(R.id.portada_list_nuevas_publicaciones);
            CardView card = (CardView) actividad.findViewById(R.id.card_perfil_portada);
            Fade moverfuera = new Fade(Fade.OUT);
            moverfuera.setDuration(450);
          //  moverfuera.addTarget(listado);
            moverfuera.addTarget(card);
            moverfuera.setInterpolator(AnimationUtils.loadInterpolator(contexto,android.R.interpolator.fast_out_slow_in));
            actividad.getWindow().setExitTransition(moverfuera);
        }

        Bundle bund = ActivityOptionsCompat.makeSceneTransitionAnimation(actividad,transicionpc,transicioncel,transiciontrans,transicionhome,transicionmisc,transicioncolaps).toBundle();
        return bund;

    }
}
