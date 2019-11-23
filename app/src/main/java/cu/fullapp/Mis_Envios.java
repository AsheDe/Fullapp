package cu.fullapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by renier on 10/11/2016.
 */
public class Mis_Envios extends AppCompatActivity implements View.OnClickListener {

    Soporte_Elementos_Comunes soporte;
    Manejo_DB basedatos;
    RecyclerView mispubs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_envios);
        soporte = new Soporte_Elementos_Comunes(this);
        soporte.setupToolbar(true, "Mis Envios");
        soporte.setupDrawerLayout();

        basedatos = new Manejo_DB(this);
        ArrayList<Object> envios = basedatos.ObtenerListadoMisEnvios();
        if(envios.size() > 0) {
            ImageView enviar = (ImageView) findViewById(R.id.enviartodo);
            soporte.PintarIconos(R.drawable.img_send_all, R.color.blanco);
            mispubs = (RecyclerView) findViewById(R.id.publicaciones);

            LinearLayoutManager mLayoutManager;
            mLayoutManager = new LinearLayoutManager(this);
            mispubs.setLayoutManager(mLayoutManager);


            Clase_AdaptadorMisEnvios mAdapter = new Clase_AdaptadorMisEnvios(envios, this, mispubs);
            mispubs.setAdapter(mAdapter);

            final TextView numbernsend = (TextView) findViewById(R.id.numbernsend);
            int sinenv = basedatos.SinEnviar();
            if (sinenv > 0)
                numbernsend.setText(sinenv + " sin enviar");

            enviar.setOnClickListener(this);

        }
        else {
            View   nadaquemostrar = findViewById(R.id.nadaquemostrar);
            nadaquemostrar.setVisibility(View.VISIBLE);

        }


    }

    private String ConfSendMailComentario(Comentario coment) {
        String comentariofinal = "";
        comentariofinal += "<c1>" + coment.getEntrada() + "</c1>";
        comentariofinal += "<c2>" + coment.getComentario() + "</c2>";
        comentariofinal += "<c3>" + coment.getAutor() + "</c3>";
        long fecha = new Date().getTime();
        basedatos.ActualizarFechasComentarios(fecha, coment.getIdentificador());
        comentariofinal += "<c4>" + fecha + "</c4>";
        comentariofinal += "<c5>" + coment.getRating() + "</c5></sc>";
        return comentariofinal;
    }

    private String ConfSendMailPublicaciones(Publicacion publicacion) {
        //String publicacionfinal="<sp"+numero+">";
        String publicacionfinal = "";
        publicacionfinal += "<p1>" + publicacion.getIdent() + "</p1>";
        publicacionfinal += "<p2>" + publicacion.getCorreo_usuario() + "</p2>";
        publicacionfinal += "<p3>" + publicacion.getTitulo() + "</p3>";
        publicacionfinal += "<p4>" + publicacion.getPrecio() + "</p4>";
        publicacionfinal += "<p5>" + publicacion.getContenido() + "</p5>";
        publicacionfinal += "<p6>" + publicacion.getCategoria() + "</p6>";
        publicacionfinal += "<p7>" + publicacion.getSubcategoria() + "</p7>";
        publicacionfinal += "<p8>" + publicacion.getMoneda() + "</p8>";
        long fecha = new Date().getTime();
        basedatos.ActualizarFechasPublicaciones(fecha, publicacion.getIdent());
        publicacionfinal += "<p9>" + fecha + "</p9>";
        publicacionfinal += "<p10>" + publicacion.getProvincia() + "</p10>";
        publicacionfinal += "<p11>" + publicacion.getBusco() + "</p11>";
        publicacionfinal += "<p12>" + publicacion.getPatrocinado() + "</p12></sp>";
        return publicacionfinal;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.enviartodo) {

            boolean estaactivado = false;
            //// cambiar USUARIO de activado a registrado REGISTRADO
            String fechar = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("ac42txf", "Sinfecha", this);
            String activacion = "No registrado";
            if (fechar.compareTo("Sinfecha") != 0) {
                activacion = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br", "No registrado", this);
                if (activacion.compareTo("APLICACIÓN ACTIVADA") == 0) {
                    String diasleft = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("db11k9t", "0", this);
                    String periodo = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("oft963p", "0", this);
                    int diasl = Integer.parseInt(diasleft);  // dias restantes
                    long ahora = new Date().getTime();
                    long fechact = Long.parseLong(fechar);
                    int iperiodo = Integer.parseInt(periodo);
                    int dias = (int) ((ahora - fechact) / 86400000);  /// dias q han pasado desde que activaste
                    if (dias > iperiodo) {
                        Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t", "0", this);
                        Soporte_Elementos_Comunes.cambiar_preferencia_encritada("kr9t4br", "USUARIO REGISTRADO", this);
                    } else {
                        int timeleft = iperiodo - dias;
                        Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t", timeleft + "", this);

                    }
                    estaactivado = true;
                }

            } else {
                Toast.makeText(this, "Se ha violado la seguridad de la aplicación.", Toast.LENGTH_LONG).show();
                SystemClock.sleep(2500);
                finish();
            }

            if (activacion.compareTo("No registrado") != 0) {
                // ArrayList<String> grupos = new ArrayList<String>();

                //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String[] categorias = getResources().getStringArray(R.array.arCategorias);
                //String[] allgroups = getResources().getStringArray(R.array.arGrupoCategoria);
                Usuario userlocal = new Usuario().CRARUSERLOCAL(this);
                String usuario = "";
                for (final String categoria : categorias) {
                    ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categoria);
                    ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categoria);
                    String storecomentarios = "";
                    String storepublicaciones = "";
                    boolean hayparaenviar = false;
                    if (coments.size() > 0) {
                        hayparaenviar = true;
                        for (int i = 0; i < coments.size(); i++) {
                            storecomentarios += ConfSendMailComentario(coments.get(i));
                        }
                    } else {
                        storecomentarios = "<null>";
                    }

                    if (pubspc.size() > 0) {
                        hayparaenviar = true;
                        for (int i = 0; i < pubspc.size(); i++) {
                            storepublicaciones += ConfSendMailPublicaciones(pubspc.get(i));
                        }
                    } else {
                        storepublicaciones = "<null>";
                    }
                    if (hayparaenviar) {
                        userlocal.Publicar(this, pubspc.size());
                        usuario = userlocal.toString();
                        String textopublicacion = usuario + "</tp>" + storepublicaciones + "</tp>" + storecomentarios + "</tp>";
                        String asunto = "FACIL-" + categoria.toUpperCase() + "-" + userlocal.getProvincia();
                            new Correo_Enviar_AsyncTask(this, pubspc.size()).execute(getString(R.string.anakasparian), asunto, textopublicacion + "<firma>", this);

                        //final Handler manejador = new Handler();
                        Timer timer = new Timer();
                        TimerTask cicloespera = new TimerTask() {
                            @Override
                            public void run() {
                                new AsyncTask_CopiarMensajes().execute(getBaseContext(), categoria);

                            }
                        };
                        timer.schedule(cicloespera, 20000);


                    }


                }
            }
            else {
                Toast.makeText(this, "No se puede enviar, porque no estás registrado. REGÍSTRATE, es gratis y no consume saldo.", Toast.LENGTH_LONG).show();
            }
        }

    }
}









  /* enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //// cambiar USUARIO REGISTRADO por "APLICACIÓN ACTIVADA" ESTA OPCIÓN SE DESHABILITA HASTA QUE PAGUE
                String fechar =  Soporte_Elementos_Comunes.obtener_preferencia_encriptada("ac42txf","Sinfecha",this);

                if(fechar.compareTo("Sinfecha")!=0)
                {
                    String activacion = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br","No registrado",this);
                    if(activacion.compareTo("APLICACIÓN ACTIVADA")==0) {
                        String diasleft = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("db11k9t", "0",this);
                        String periodo = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("oft963p", "0",this);
                        int diasl = Integer.parseInt(diasleft);  // dias restantes
                        long ahora = new Date().getTime();
                        long fechact = Long.parseLong(fechar);
                        int iperiodo = Integer.parseInt(periodo);
                        int dias = (int) ((ahora - fechact) / 86400000);  /// dias q han pasado desde que activaste
                        if (dias > iperiodo) {
                            Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t", "0",this);
                            Soporte_Elementos_Comunes.cambiar_preferencia_encritada("kr9t4br", "USUARIO REGISTRADO",this);
                        }
                        else{
                            int timeleft = iperiodo-dias;
                            Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t", timeleft + "",this);

                        }
                    }

                }
                else
                {
                    Toast.makeText(this, "Se ha violado la seguridad de la aplicación.", Toast.LENGTH_LONG).show();
                    SystemClock.sleep(2500);
                    this.finish();
                }

                String activacion = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br", "No Registrado",this);
                if (activacion.compareTo("APLICACIÓN ACTIVADA") != 0) {

                    Toast.makeText(this, "Debes ACTIVAR la aplicación para hacer esto.", Toast.LENGTH_LONG).show();

                } else {

                    basedatos = new Manejo_DB(this);
                    ArrayList<ArrayList<Comentario>> comensentcategori = new ArrayList<ArrayList<Comentario>>();

                    ArrayList<ArrayList<Publicacion>> pubensentcatelist = new ArrayList<ArrayList<Publicacion>>();
                    ArrayList<String> grupos = new ArrayList<String>();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                    String[] categorias = this.getResources().getStringArray(R.array.arCategorias);
                    String[] allgroups = this.getResources().getStringArray(R.array.arGrupoCategoria);
                    if (preferences.getBoolean("subcripcion_computadora", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[0]);
                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[0]);

                        //if(coments.size()>0)
                            comensentcategori.add(coments);

                        //if(pubspc.size()>0 )
                            pubensentcatelist.add(pubspc);

                      //  if(coments.size()>0 || pubspc.size()>0 )
                            grupos.add(allgroups[0]);
                    }
                    if (preferences.getBoolean("subcripcion_celular", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[1]);
                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[1]);
                       // if(coments.size()>0)
                            comensentcategori.add(coments);

                      //  if(pubspc.size()>0 )
                            pubensentcatelist.add(pubspc);

                      //  if(coments.size()>0 || pubspc.size()>0 )
                            grupos.add(allgroups[1]);
                    }
                    if (preferences.getBoolean("subcripcion_transporte", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[2]);
                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[2]);
                      //  if(coments.size()>0)
                            comensentcategori.add(coments);

                      //  if(pubspc.size()>0 )
                            pubensentcatelist.add(pubspc);

                      //  if(coments.size()>0 || pubspc.size()>0 )
                            grupos.add(allgroups[2]);
                    }
                    if (preferences.getBoolean("subcripcion_hogar", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[3]);

                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[3]);
                     //   if(coments.size()>0)
                            comensentcategori.add(coments);

                     //   if(pubspc.size()>0 )
                            pubensentcatelist.add(pubspc);

                     //   if(coments.size()>0 || pubspc.size()>0 )
                            grupos.add(allgroups[3]);

                    }
                    if (preferences.getBoolean("subcripcion_miscelaneas", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[4]);
                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[4]);
                      //  if(coments.size()>0)
                            comensentcategori.add(coments);

                     //   if(pubspc.size()>0 )
                            pubensentcatelist.add(pubspc);

                     //   if(coments.size()>0 || pubspc.size()>0 )
                            grupos.add(allgroups[4]);

                    }

                    Usuario us = new Usuario().CRARUSERLOCAL(this);

                    String usuario = "";


                    boolean algoenviado = false;
                    for (int contador = 0; contador < grupos.size(); contador++) {

                        String storecomentarios = null;
                        String storepublicaciones = null;
                        ArrayList<Comentario> comensent = comensentcategori.get(contador);
                        ArrayList<Publicacion> pubensent = pubensentcatelist.get(contador);

                        if (comensent.size() > 0) {

                            storecomentarios = "";
                            for (int i = 0; i < comensent.size(); i++) {
                                storecomentarios += ConfSendMailComentario(comensent.get(i));

                            }
                        }
                        Integer numpubs = null;
                        if (pubensent.size() > 0) {
                            numpubs = pubensent.size();
                            storepublicaciones = "";
                            for (int i = 0; i < pubensent.size(); i++) {
                                storepublicaciones += ConfSendMailPublicaciones(pubensent.get(i));
                            }
                        }


                        if (storecomentarios != null && storepublicaciones != null) {
                            //ArrayList<String> destinatarios = new ArrayList<>();
                            //destinatarios.add("facil-app-publicaciones@googlegroups.com");///// aqui va la direccion del grupo google
                            us.Publicar(this,numpubs);
                            usuario = us.toString();
                            String textoencriptado = Soporte_Elementos_Comunes.Encriptar(usuario + "</tp>" + storepublicaciones + "</tp>" + storecomentarios + "</tp>",this);
                            long moment = new Date().getTime();
                            String asto = "FACIL-ACTUALIZACION";
                            //String asuntoencriptado = Soporte_Elementos_Comunes.Encriptar(asto,this);
                           // String momentenc = Soporte_Elementos_Comunes.Encriptar(moment + "",this);
                           new Correo_Enviar_AsyncTask(this,numpubs).execute(grupos.get(contador), asto, textoencriptado + "<firma>", this);
                            algoenviado = true;
                        } else if (storecomentarios == null && storepublicaciones != null) {

                            us.Publicar(this,numpubs);
                            usuario = us.toString();
                              String textoencriptado = Soporte_Elementos_Comunes.Encriptar(usuario + "</tp>" + storepublicaciones + "</tp>" + "<null>"  + "</tp>",this);
                            long moment = new Date().getTime();
                            //String asuntoencriptado = Soporte_Elementos_Comunes.Encriptar("FACIL-ACTUALIZACION",this);
                            String momenc = Soporte_Elementos_Comunes.Encriptar(moment + "",this);
                            new Correo_Enviar_AsyncTask(this,numpubs).execute(grupos.get(contador), "FACIL-ACTUALIZACION", textoencriptado + "<firma>", this);

                            algoenviado = true;
                        } else if (storepublicaciones == null && storecomentarios != null) {
                             usuario = us.toString();
                            String textoencriptado = Soporte_Elementos_Comunes.Encriptar(usuario + "</tp>" + "<null>"+ "</tp>" + storecomentarios  + "</tp>",this);

                            long moment = new Date().getTime();
                            //String asuntoencriptado = Soporte_Elementos_Comunes.Encriptar("FACIL-ACTUALIZACION",this);
                           // String momenc = Soporte_Elementos_Comunes.Encriptar(moment + "",this);

                             new Correo_Enviar_AsyncTask(this,numpubs).execute(grupos.get(contador), "FACIL-ACTUALIZACION", textoencriptado + "<firma>", this);
                            algoenviado = true;

                        }
                    }
                    if (!algoenviado) {
                        Toast.makeText(this, "No tiene nada para enviar", Toast.LENGTH_LONG).show();
                    }
                 /*   else {
                        //basedatos.ActualizarEnviados();
                        if (this instanceof Mis_Envios) {
                            RecyclerView recycler = (RecyclerView) this.findViewById(R.id.publicaciones);
                            AdaptadorMisEnvios adptmisenv = (AdaptadorMisEnvios) (recycler).getAdapter();
                            adptmisenv.envios = basedatos.ObtenerListadoMisEnvios();
                            adptmisenv.notifyDataSetChanged();
                            TextView numbernsend = (TextView) this.findViewById(R.id.numbernsend);
                            numbernsend.setText(basedatos.SinEnviar()+" sin enviar");
                        }
                    }


                }

            }

        });*/