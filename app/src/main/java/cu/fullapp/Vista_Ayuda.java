package cu.fullapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by renier on 29/12/2016.
 */
public class Vista_Ayuda extends AppCompatActivity  {

    Soporte_Elementos_Comunes soporte;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_ayuda);
        soporte = new Soporte_Elementos_Comunes(this);
        soporte.setupToolbar(false,"");
        soporte.setupDrawerLayout();
        Vista_Item_Ayuda quenecesito = (Vista_Item_Ayuda)findViewById(R.id.quenecesito);
        Vista_Item_Ayuda importante = (Vista_Item_Ayuda)findViewById(R.id.importante);
        Vista_Item_Ayuda comoregistrarse = (Vista_Item_Ayuda)findViewById(R.id.comoregistrarse);
        Vista_Item_Ayuda comocambiarcategorias = (Vista_Item_Ayuda)findViewById(R.id.comocambiarcategorias);
       // Vista_Item_Ayuda reregistro =(Vista_Item_Ayuda)findViewById(R.id.reregistro);
       // Vista_Item_Ayuda comoactivar = (Vista_Item_Ayuda)findViewById(R.id.comoactivar);
        //Vista_Item_Ayuda sobrelaactiv = (Vista_Item_Ayuda)findViewById(R.id.sobrelaactiv);
       // Vista_Item_Ayuda Rcambiotel = (Vista_Item_Ayuda)findViewById(R.id.Rcambiotel);
        //Vista_Item_Ayuda Rbaja = (Vista_Item_Ayuda)findViewById(R.id.Rbaja);
        Vista_Item_Ayuda Rnoact = (Vista_Item_Ayuda)findViewById(R.id.Rnoact);
        Vista_Item_Ayuda Rderchos = (Vista_Item_Ayuda)findViewById(R.id.Rderchos);

        Vista_Item_Ayuda autorapp = (Vista_Item_Ayuda)findViewById(R.id.autorapp);

        quenecesito.Configurar(0);
        quenecesito.accion.setVisibility(View.GONE);

        importante.Configurar(1);
        importante.accion.setVisibility(View.GONE);

        comoregistrarse.Configurar(2);
        comoregistrarse.accion.setVisibility(View.GONE);
        final Context contexto = this;
        final Activity activity = this;
        comocambiarcategorias.Configurar(3);
        comocambiarcategorias.accion.setVisibility(View.GONE);
        /*reregistro.Configurar(11);
        reregistro.accion.setText("Volver a Registrarse");
        reregistro.accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contexto,"Paciencia, espera unos minutos mientras se rectifican las categorías para las que estás registrado",Toast.LENGTH_LONG).show();
                SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(contexto);
                if(preferencias.getBoolean("subcripcion_computadora",false)) {
                    ArrayList<String> destinatariopc = new ArrayList<>();
                    destinatariopc.add("facil-app-computadora+join@googlegroups.com");
                    new Enviar_Registro_AsyncTask(activity, true, "computadora", false).execute(destinatariopc, "", "", contexto);

                }
                if(preferencias.getBoolean("subcripcion_celular",false)) {
                    ArrayList<String> destinatariocel = new ArrayList<>();

                    destinatariocel.add("facil-app-celular+join@googlegroups.com");
                    new Enviar_Registro_AsyncTask(activity, true, "celular", false).execute(destinatariocel, "", "", contexto);
                }
                if(preferencias.getBoolean("subcripcion_transporte",false)) {
                    ArrayList<String> destinatariotra = new ArrayList<>();
                    destinatariotra.add("facil-app-transporte+join@googlegroups.com");
                    new Enviar_Registro_AsyncTask(activity, true, "transporte", false).execute(destinatariotra, "", "", contexto);
                }
                if(preferencias.getBoolean("subcripcion_hogar",false)) {
                    ArrayList<String> destinatariohog = new ArrayList<>();
                    destinatariohog.add("facil-app-hogar+join@googlegroups.com");
                    new Enviar_Registro_AsyncTask(activity, true, "hogar", false).execute(destinatariohog, "", "", contexto);
                }
                if(preferencias.getBoolean("subcripcion_miscelaneas",false)) {
                    ArrayList<String> destinatariomisc = new ArrayList<>();
                    destinatariomisc.add("facil-app-miscelaneas+join@googlegroups.com");
                    new Enviar_Registro_AsyncTask(activity, true, "miscelaneas", false).execute(destinatariomisc, "", "", contexto);
                }
                ArrayList<String> destinatariopub = new ArrayList<>();
                destinatariopub.add("facil-app-publicaciones+join@googlegroups.com");
                new Enviar_Registro_AsyncTask(activity, true, "publicaciones", false).execute(destinatariopub, "", "", contexto);


            }
        });*/
       // comoactivar.Configurar(4);
        //comoactivar.accion.setText("ACTIVAR");

      /*  comoactivar.accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registed = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br", "No registrado", contexto);
                /*if (registed.compareTo("No registrado") == 0) {
                    Toast.makeText(contexto, "Primero debes registrarte", Toast.LENGTH_LONG).show();
                } else {*/
                /*    AlertDialog.Builder construir = new AlertDialog.Builder(contexto);
                    final EditText codigo = new EditText(contexto);
                    codigo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    codigo.setTypeface(Typeface.SERIF);
                    construir.setMessage("Para adquirir el código de activación, debes realizar una transferencia al número \n "+ getResources().getString(R.string.minumerodetelefono) + "\n , desde el número de teléfono con que te registraste y recibirás por correo el código de activación. Tarifa --- Recarga de: \n" +
                            " 0.50 CUC 60 días (2 meses) \n" +
                            " 1 CUC 150 días (5 meses)\n" +
                            " 2 CUC 365 días (1 año) \n" )
                            .setTitle("Entrar código de activación")
                            .setView(codigo)
                            .setNegativeButton("Solicitar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder activacion = new AlertDialog.Builder(contexto);
                                    activacion.setMessage("Entre los datos de la transferencia");

                                    final Vista_Dialogo_Datos_Transf datosrecarga = new Vista_Dialogo_Datos_Transf(contexto);
                                    activacion.setView(datosrecarga);
                                    activacion.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Soporte_Comunicaciones comunicaciones = new Soporte_Comunicaciones(contexto);
                                            if(!datosrecarga.clavetransf.getText().toString().isEmpty())
                                                comunicaciones.Recargar(Integer.parseInt(datosrecarga.clavetransf.getText().toString()),datosrecarga.valuetransfer);
                                            else
                                                Toast.makeText(contexto,"Inserte una clave válida",Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    AlertDialog solicitaractivar = activacion.create();
                                    solicitaractivar.show();

                                }
                            })
                            .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {
                                        String coded = codigo.getText().toString();

                                        String mail = PreferenceManager.getDefaultSharedPreferences(contexto).getString("correo", "");
                                        String fechar = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("ac42txf", "-1", contexto);
                                        String dias = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("db11k9t", "0", contexto);

                                        if (coded.endsWith("F")) {
                                            coded = coded.substring(0, coded.length() - 1);
                                            coded = coded.concat("\n");

                                            String codigodecripted = Soporte_Elementos_Comunes.Desencriptar(coded,contexto);


                                            String telefono = PreferenceManager.getDefaultSharedPreferences(contexto).getString("telef", "");
                                            if(codigodecripted.matches("<\\d+/><\\d+/><"+mail+"/>") || codigodecripted.matches("<\\d+/><\\d+/><"+telefono+"/>") || codigodecripted.matches("<\\d+/><\\d+/><"+"53"+telefono+"/>")) {

                                                String[] datos = codigodecripted.split("/>");
                                                datos[0] = datos[0].replaceFirst("<", "");
                                                datos[1] = datos[1].replaceFirst("<", "");
                                                long ultimafecha = Long.parseLong(fechar);
                                                if (Long.parseLong(datos[1]) > ultimafecha ) {

                                                    int ndias = Integer.parseInt(datos[0]) + Integer.parseInt(dias);
                                                    Soporte_Elementos_Comunes.cambiar_preferencia_encritada("kr9t4br", "APLICACIÓN ACTIVADA", contexto);
                                                    Soporte_Elementos_Comunes.cambiar_preferencia_encritada("ac42txf", datos[1] + "", contexto);
                                                    Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t", ndias + "", contexto);
                                                    Soporte_Elementos_Comunes.cambiar_preferencia_encritada("oft963p", ndias + "", contexto);
                                                    Soporte_Elementos_Comunes.cambiar_preferencia_encritada("xbn5pz8", coded, contexto);

                                                    Toast.makeText(contexto, "Podrás PUBLICAR durante " + ndias + " días a partir de hoy.", Toast.LENGTH_LONG).show();

                                                } else {
                                                    Toast.makeText(contexto, "Código de registro obsoleto.", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                            else {
                                                //dialog.dismiss();
                                                Toast.makeText(contexto, "Código de activación Incorrecto", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                        else {
                                            //dialog.dismiss();
                                            Toast.makeText(contexto, "Código de activación Incorrecto", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception exc) {
                                        Toast.makeText(contexto, exc.getMessage(), Toast.LENGTH_LONG).show();
                                    }


                                }
                            });

// 3. Get the AlertDialog from create()
                    AlertDialog dialog = construir.create();
                    dialog.show();
                }
           // }
        });*/

       // sobrelaactiv.Configurar(5);
       // sobrelaactiv.accion.setVisibility(View.GONE);

       // Rcambiotel.Configurar(6);
       // Rcambiotel.accion.setVisibility(View.GONE);
      /*  Rbaja.Configurar(7);
        Rbaja.accion.setText("DARSE BAJA");

        Rbaja.accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registed = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br", "No registrado", contexto);
                if (registed.compareTo("No registrado") == 0) {
                    Toast.makeText(contexto, "Primero debes registrarte", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(contexto, "Paciencia, no cierres la app hasta recibir los mensajes de desregistro", Toast.LENGTH_LONG).show();
               SharedPreferences prefs =    PreferenceManager.getDefaultSharedPreferences(contexto);
                    ArrayList<String> destinatario;
                    if(prefs.getBoolean("subcripcion_computadora",false)) {
                        destinatario = new ArrayList<>();
                       destinatario.add("facil-app-computadora+unsubscribe@googlegroups.com");
                       new Enviar_Registro_AsyncTask(activity, true, "computadora", true).execute(destinatario, "", "", getBaseContext());
                   }
                    if(prefs.getBoolean("subcripcion_celular",false)) {
                        destinatario = new ArrayList<>();
                        destinatario.add("facil-app-celular+unsubscribe@googlegroups.com");
                        new Enviar_Registro_AsyncTask(activity, true, "celular", false).execute(destinatario, "", "", getBaseContext());
                    }
                    if(prefs.getBoolean("subcripcion_miscelaneas",false)) {
                        destinatario = new ArrayList<>();
                        destinatario.add("facil-app-miscelaneas+unsubscribe@googlegroups.com");
                        new Enviar_Registro_AsyncTask(activity, true, "celular", true).execute(destinatario, "", "", getBaseContext());
                    }
                    if(prefs.getBoolean("subcripcion_transporte",false)) {
                        destinatario = new ArrayList<>();
                        destinatario.add("facil-app-transporte+unsubscribe@googlegroups.com");
                        new Enviar_Registro_AsyncTask(activity, true, "transporte", true).execute(destinatario, "", "", getBaseContext());
                    }
                    if(prefs.getBoolean("subcripcion_hogar",false)) {
                        destinatario = new ArrayList<>();
                        destinatario.add("facil-app-hogar+unsubscribe@googlegroups.com");
                        new Enviar_Registro_AsyncTask(activity, true, "hogar", true).execute(destinatario, "", "", getBaseContext());
                    }
                    if(prefs.getBoolean("subcripcion_publicaciones",false)) {
                        destinatario = new ArrayList<>();
                        destinatario.add("facil-app-publicaciones+unsubscribe@googlegroups.com");
                        new Enviar_Registro_AsyncTask(activity, true, "publicaciones", true).execute(destinatario, "", "", getBaseContext());

                    }
                    Soporte_Elementos_Comunes.cambiar_preferencia_encritada("kr9t4br", "No registrado", activity);
                }
            }
        });*/



        Rnoact.Configurar(4);
        Rnoact.accion.setText("CONTRIBUIR");
        Rnoact.accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Soporte_Elementos_Comunes.MostrarActivacion(contexto);
            }
        });

        Rderchos.Configurar(5);
        Rderchos.accion.setBackgroundDrawable(getResources().getDrawable(R.drawable.licencia));
        Rderchos.accion.setText("");

        autorapp.Configurar(6);
        autorapp.accion.setVisibility(View.GONE);

    }


}
