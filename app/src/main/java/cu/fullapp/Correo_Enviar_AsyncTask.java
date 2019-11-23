package cu.fullapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by renier on 21/11/2016.
 */
public class Correo_Enviar_AsyncTask extends AsyncTask {
    private ProgressDialog statusDialog;
    private Activity sendMailActivity;
    private Context context;
    Integer sendall = null;
    String asunto="";

    public Correo_Enviar_AsyncTask(Activity activity,Integer sena) {
        sendMailActivity = activity;
        sendall = sena;
        context = (Context)activity;
    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(context);
        statusDialog.setMessage("Abriendo Conexion...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
        statusDialog.setMessage("Intentando Enviar...");



    }

    @Override
    protected Object doInBackground(Object... args) {

            try {

                publishProgress("Procesando....");
                Correo_Enviar androidEmail;
                if (args[0] instanceof ArrayList)
                    androidEmail = new Correo_Enviar((ArrayList<String>) args[0], args[1].toString(), args[2].toString(), (Context) args[3]);
                else
                    androidEmail = new Correo_Enviar((String) args[0], args[1].toString(), args[2].toString(), (Context) args[3]);
                publishProgress("Preparando ....");
                androidEmail.createEmailMessage();
                publishProgress("Enviando ....");
                androidEmail.Enviar_Correo();
                publishProgress("Enviado.");
            } catch (Exception e) {
                // publishProgress(e.getMessage());
                return e.getMessage();
            }
         asunto=args[1].toString();
            return true;

    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());
    }

    @Override
    public void onPostExecute(Object result) {
        if(result instanceof String)
        {
            statusDialog.dismiss();
            Toast.makeText(sendMailActivity.getBaseContext(),result.toString(),Toast.LENGTH_LONG).show();
            if(sendall!=null)
            {
                new Usuario().CRARUSERLOCAL(sendMailActivity.getBaseContext()).DeshacerPublicar(sendMailActivity.getBaseContext(),sendall);
            }
        }
        else if((boolean)result)
        {
            statusDialog.setMessage("Actualizando base de datos");
            if(sendMailActivity instanceof  Vista_Send_Email)
            {
                ((Vista_Send_Email)sendMailActivity).texto.setText("");
            }

                    /*
                if(sendMailActivity instanceof Mis_Envios) {
                    RecyclerView recycler = (RecyclerView) sendMailActivity.findViewById(R.id.publicaciones);
                    AdaptadorMisEnvios adptmisenv = (AdaptadorMisEnvios) (recycler).getAdapter();
                    adptmisenv.envios = basedatos.ObtenerListadoMisEnvios();
                    adptmisenv.notifyDataSetChanged();
                    //  recycler.invalidate();

                }*/
            statusDialog.dismiss();
            Toast.makeText(sendMailActivity.getBaseContext(),"Enviado.",Toast.LENGTH_LONG).show();
           // else {
                if(asunto.startsWith("FACIL-")) {
                    Manejo_DB basedatos = new Manejo_DB(context);
                    basedatos.ActualizarEnviados();
                    if (sendMailActivity instanceof Mis_Envios) {
                        RecyclerView recycler = (RecyclerView) sendMailActivity.findViewById(R.id.publicaciones);
                        Clase_AdaptadorMisEnvios adptmisenv = (Clase_AdaptadorMisEnvios) (recycler).getAdapter();

                        adptmisenv.envios = basedatos.ObtenerListadoMisEnvios();
                        adptmisenv.notifyDataSetChanged();
                        TextView numbernsend = (TextView) sendMailActivity.findViewById(R.id.numbernsend);
                        numbernsend.setText(basedatos.SinEnviar() + " sin enviar");
                    }
                }
        }
        else if(!(boolean)result)
        {
            Toast.makeText(sendMailActivity.getBaseContext(),"Error al enviar.",Toast.LENGTH_LONG).show();
        }
        statusDialog.dismiss();



    }




}

