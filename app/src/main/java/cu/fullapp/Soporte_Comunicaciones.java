package cu.fullapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by renier on 24/10/2016.
 */
public class Soporte_Comunicaciones {
    Context contexto;
    AppCompatActivity actividad;
    final int REQUEST_CODE_ASK_PERMISSIONS_CALL = 1987;
    final int REQUEST_CODE_ASK_PERMISSIONS_SMS = 1985;
    int telefono_user;
    String recarga;
    boolean recargar = false;
    public Soporte_Comunicaciones(Context c) {
        contexto =c;
        actividad = (AppCompatActivity)c ;

    }

    public void Llamar(final int telefono)
    {
        telefono_user = telefono;
                int hasCALLPermission = ActivityCompat.checkSelfPermission(contexto, Manifest.permission.CALL_PHONE);
                if (hasCALLPermission != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS_CALL);

                }
                else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:+" + telefono));
                    //callIntent.setData(Uri.parse("tel:5555555"));
                    contexto.startActivity(callIntent);
                }
    }
    public void Recargar(int clave,String importe)
    {
        recargar = true;

        int hasCALLPermission = ActivityCompat.checkSelfPermission(contexto, Manifest.permission.CALL_PHONE);
        if (hasCALLPermission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS_CALL);
            recarga = "*234*1*"+contexto.getResources().getString(R.string.minumerodetelefono) +"*"+clave+"*"+importe+"#";

        }
        else {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            if(importe.compareTo("0.50")==0)
                callIntent.setData(Uri.parse("tel:" + Uri.encode( "*234*1*"+contexto.getResources().getString(R.string.minumerodetelefono) +"*"+clave+ "#")));
            else
                callIntent.setData(Uri.parse("tel:" + Uri.encode( "*234*1*"+contexto.getResources().getString(R.string.minumerodetelefono) +"*"+clave+"*"+ importe + "#")));

            contexto.startActivity(callIntent);
        }

    }

    public void EnviarSMS(final int telefono) {
        telefono_user = telefono;
        int hasSMSPermission = ActivityCompat.checkSelfPermission(contexto, Manifest.permission.SEND_SMS);
        if (hasSMSPermission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE_ASK_PERMISSIONS_SMS);
        }
        else
        {

            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:+" + telefono));
            contexto.startActivity(smsIntent);

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS_CALL: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    if(recargar)
                        callIntent.setData(Uri.parse("tel:+" + Uri.encode( recarga)));
                    else
                        callIntent.setData(Uri.parse("tel:+" + telefono_user));
                    contexto.startActivity(callIntent);
                } else {
                    // Permission Denied
                    Toast.makeText(contexto, "NO TIENE PERMISO PARA EFECTUAR LLAMADAS", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CODE_ASK_PERMISSIONS_SMS:
            {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission Granted
                        Intent callIntent = new Intent(Intent.ACTION_SENDTO);
                        callIntent.setData(Uri.parse("tel:+" + telefono_user));
                        contexto.startActivity(callIntent);
                    } else {
                        // Permission Denied
                        Toast.makeText(contexto, "NO TIENE PERMISO PARA ENVIAR MENSAJES", Toast.LENGTH_SHORT).show();
                        Toast.makeText(contexto, "En Configuración->Apps->FACIL->Permisos  puede cambiar los permisos de la aplicación", Toast.LENGTH_SHORT).show();

                    }
                    break;

            }
            //default:
              // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void Lanzar_Correo( String correo,String publicacion)
    {
        Intent emailIntent = new Intent("cu.fullapp.SENDEMAIL");
        emailIntent.putExtra("correo",correo);
        emailIntent.putExtra("publicacion",publicacion);
        contexto.startActivity(emailIntent);
    }

  /*  public void EnviarSMS(int telefono)
    {
        Intent smsIntent = new Intent("cu.fullapp.SENDSMS");
        smsIntent.putExtra("numero",telefono);
        contexto.startActivity(smsIntent);

    }*/

   /* @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.muContactarCelUI: {
                int hasCALLPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
                if (hasCALLPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS_CALL);
                    return false;
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    //callIntent.setData(Uri.parse("tel:+"+user.getTelefono()));
                    callIntent.setData(Uri.parse("tel:5555555"));
                    startActivity(callIntent);
                }
                break;
            }
            case R.id.muContactarSMSUI: {
                Intent smsIntent = new Intent("cu.fullapp.SENDSMS");
                //callIntent.puExtra("tel:+"+user.getTelefono()));
                smsIntent.putExtra("numero", "555555");
                startActivity(smsIntent);
                break;
            }


        }
        return false;
    }*/

   static public void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }

    static  public boolean wifiEncendida(Activity actividad)
    {
        ConnectivityManager conMgr =  (ConnectivityManager) actividad.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null)
            return info.isConnected();
         return false ;

    }
    static  public boolean datosmovilesEncendidos(Activity actividad)
    {
        ConnectivityManager conMgr =  (ConnectivityManager) actividad.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null)
           return false;
        return true;


    }

    static  public boolean datosmovilesConectando(Activity actividad)
    {
        ConnectivityManager conMgr =  (ConnectivityManager) actividad.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null)
            return false;
        else if(netInfo.getState() == NetworkInfo.State.CONNECTING)
        return true;
        return false;

    }

    static public void ApagarDatosMoviles(Context contexto) {
        try {
            setMobileDataEnabled(contexto, false);
        } catch (Exception excep) {
            Toast.makeText(contexto, excep.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    static public boolean EncenderDatosMoviles(Context contexto)
    {
        Method dataConnSwitchmethod;
        Class telephonyManagerClass;
        Object ITelephonyStub;
        Class ITelephonyClass;
        boolean isEnabled;
        TelephonyManager telephonyManager = (TelephonyManager) contexto
                .getSystemService(Context.TELEPHONY_SERVICE);

        if(telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED){
            isEnabled = true;
        }else{
            isEnabled = false;
        }
        try {
            telephonyManagerClass = Class.forName(telephonyManager.getClass().getName());
            Method getITelephonyMethod = telephonyManagerClass.getDeclaredMethod("getITelephony");
            getITelephonyMethod.setAccessible(true);
            ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
            ITelephonyClass = Class.forName(ITelephonyStub.getClass().getName());

            if (!isEnabled) {
              //  dataConnSwitchmethod = ITelephonyClass
                //        .getDeclaredMethod("disableDataConnectivity");
           // } else {
                dataConnSwitchmethod = ITelephonyClass
                        .getDeclaredMethod("enableDataConnectivity");
                dataConnSwitchmethod.setAccessible(true);
                dataConnSwitchmethod.invoke(ITelephonyStub);
            }

        }
        catch (Exception exc)
        {
           return false;
        }
        return true;
    }



}
