package cu.fullapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yudel on 26/06/2017.
 */
public class Actividad_Publicacion_Completa extends AppCompatActivity implements View.OnClickListener {

    Clase_Publicaciones_Pager_Adapter mPublicacionesPagerAdpt;
    ViewPager mViewPager;

    ImageView btllamar;
    ImageView btcorreo;
    ImageView btcompartir;
    ImageView img_comentar;
    ImageView btsms;
    Usuario autor;

    Soporte_Comunicaciones comunicaciones;
    Manejo_DB base_datos;
    Publicacion mpublicacion;
    ArrayList<Publicacion> publicaciones;
    Soporte_Elementos_Comunes soporte;
    AppCompatActivity actividad;
    Context contexto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicacion_completa);
        soporte = new Soporte_Elementos_Comunes(this);
        comunicaciones = new Soporte_Comunicaciones(this);
        soporte.setupToolbar(true,"");
        soporte.setupDrawerLayout();
        actividad = this;
      //  publicaci.putExtra(, list_publicaciones.get(position).getIdent());
    //    publicaci.putExtra("posicion", position);
  //      publicaci.putExtra("buscando",isbusqueda);
//        publicaci.putExtra("ultimalista",basedatos.last_query_perform);

        Intent origen = getIntent();
       if( origen.hasExtra("posicion")&&origen.hasExtra("ultimalista")) {



           mViewPager = (ViewPager) findViewById(R.id.pager_publicacion_completa);
          base_datos = new Manejo_DB(this);
            publicaciones = origen.getExtras().getParcelableArrayList ("ultimalista") ; //basedatos.ListaporQuery(origen.getExtras().getString("ultimalista"));
           mPublicacionesPagerAdpt = new Clase_Publicaciones_Pager_Adapter(getSupportFragmentManager(), this, mViewPager, publicaciones);
           //mSectionsPagerAdapter = new SectionsPagerAdapter(mViewPager);
           final int posicion = origen.getExtras().getInt("posicion");
            mpublicacion = publicaciones.get(posicion);
           // Set up the ViewPager with the sections adapter.

           mViewPager.setAdapter(mPublicacionesPagerAdpt);
           mViewPager.setCurrentItem(posicion);
           btcompartir = (ImageView) findViewById(R.id.comones_compartir);
           img_comentar = (ImageView) findViewById(R.id.comones_comentar);
           btllamar = (ImageView) findViewById(R.id.comones_telefono);
           btcorreo = (ImageView) findViewById(R.id.comones_correo);
           btsms = (ImageView) findViewById(R.id.comones_sms);
           btllamar.setOnClickListener(this);
           btcorreo.setOnClickListener(this);
           btsms.setOnClickListener(this);
           btcompartir.setOnClickListener(this);
           img_comentar.setOnClickListener(this);
            contexto = this;


           mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
               @Override
               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                  mpublicacion = publicaciones.get(position) ;
                   autor = base_datos.Usuario_por_Correo(mpublicacion.getCorreo_usuario());
                   getSupportActionBar().setTitle( mpublicacion.getSubcategoria());
               }

               @Override
               public void onPageSelected(int position) {

               }

               @Override
               public void onPageScrollStateChanged(int state) {

               }
           });
       }

       // actividad = (AppCompatActivity) contexto;



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comones_telefono: {
                comunicaciones.Llamar(autor.getTelefono());
                break;
            }
            case R.id.comones_correo: {
                comunicaciones.Lanzar_Correo(autor.getCorreo(), mpublicacion.getTitulo());
                break;
            }
            case R.id.comones_sms: {
                comunicaciones.EnviarSMS(autor.getTelefono());
                break;
            }
            case R.id.comones_comentar: {

                DialogFragment comentario = new Vista_Nueco_Comentario();
                Bundle argu = new Bundle();
                argu.putString("identificador", mpublicacion.getIdent());
                comentario.setArguments(argu);
                comentario.show(getSupportFragmentManager(), "interfaz_comentario");
                break;
            }
            case R.id.comones_compartir:
            {

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (soporte.obtener_preferencia_encriptada("kr9t4br", "No registrado", contexto).compareTo("No registrado") != 0) {
                            AlertDialog.Builder compartir = new AlertDialog.Builder(contexto);
                            final EditText destinatarios = new EditText(actividad);

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
                                            if (destinatarios.getText().toString().compareTo("") != 0) {
                                                String[] tdestinatarios = destinatarios.getText().toString().split(";");
                                                for (String mail : tdestinatarios) {
                                                    String patron = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                                                    ;
                                                    boolean correovalido = mail.matches(patron);
                                                    if (correovalido) {
                                                        reciben.add(mail);
                                                        allgood = true;
                                                    } else {
                                                        Toast.makeText(contexto, "Hay correos invalidos en la lista", Toast.LENGTH_LONG).show();
                                                        allgood = false;
                                                        break;
                                                    }

                                                }
                                                if (allgood) {
                                                    String texto = mpublicacion.paraCompartir(contexto);
                                                    new Correo_Enviar_AsyncTask(actividad, null).execute(reciben, "Tomado de Facil App", texto, actividad);
                                                }

                                            }

                                        }
                                    });

                            Dialog compart = compartir.create();
                            compart.show();
                        }
                        else {
                            Toast.makeText(contexto, "Debes estar registrado para compartir una publicación", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }
    }


}
