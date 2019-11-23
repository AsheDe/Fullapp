package cu.fullapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yudel on 09/08/2017.
 */
public class AsyncTask_CantActualizaciones extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity receiveMailActivity;
    private Context context;
    private Correo_Leer read;
    private int cantidaddecorreos;
    private int indiceinicio=0;
    SwipeRefreshLayout pulltorefresh=null;

    public AsyncTask_CantActualizaciones(Activity receiveMailActivity) {
        this.receiveMailActivity = receiveMailActivity;
        context = (Context)receiveMailActivity;
        statusDialog = new ProgressDialog(context);
    }

    @Override
    protected Object doInBackground(Object[] params) {

        if(params.length > 2)
            if(params[2]!=null && params[2] instanceof SwipeRefreshLayout )
            {pulltorefresh = (SwipeRefreshLayout)params[2];
                 statusDialog.dismiss();}
            else
            {
                publishProgress("Calculando Actualizaciones ...");
            }
        try {
            read = new Correo_Leer((Context) params[0], params[1].toString());
            read.CantidaddeMensajes();
            if (read.isErrorconnect()) {
                return read.getMsgerror();
            }
            else
            {
                return read.cantidad();
            }
        }
        catch (Exception e) {
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(Object o) {
       // super.onPostExecute(o);
       if(pulltorefresh!=null)
       {
           pulltorefresh.setRefreshing(false);
       }
        else
       {
           statusDialog.dismiss();
       }
    if( o instanceof Integer) {
        int cantactualizaciones = (Integer)o;
        if(cantactualizaciones > 0)
        {
            AlertDialog.Builder descargar = new AlertDialog.Builder(context);
        descargar.setTitle("Descargar Actualizaciones");
        descargar.setMessage("Hay - " + cantactualizaciones + " - nuevas actualizaciones. ¿Cuantas desea descargar? Recuerde que esta acción puede consumir saldo.");

           if(cantactualizaciones < 10) {
               LinearLayout vista = new LinearLayout(context);
               vista.setOrientation(LinearLayout.VERTICAL);
               vista.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
               SeekBar barra = new SeekBar(context);
               final TextView tvcantidad = new TextView(context);
               tvcantidad.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
               tvcantidad.setGravity(Gravity.CENTER);
               tvcantidad.setText(cantactualizaciones + "");
               cantidaddecorreos = cantactualizaciones;
               tvcantidad.setTextSize(20);

               barra.setMax(cantactualizaciones);
               barra.setProgress(cantactualizaciones);
               barra.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
               barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                   @Override
                   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                         tvcantidad.setText(progress+"");
                       cantidaddecorreos = progress;
                   }

                   @Override
                   public void onStartTrackingTouch(SeekBar seekBar) {

                   }

                   @Override
                   public void onStopTrackingTouch(SeekBar seekBar) {

                   }
               });
               vista.addView(barra);
               vista.addView(tvcantidad);


               descargar.setView(vista);
           }else{
               View cantupds =   LayoutInflater.from(context).inflate(R.layout.conf_cant_actualizaciones,null);
               RangeBar updates = (RangeBar)cantupds.findViewById(R.id.rangodeactualizaciones);
               cantidaddecorreos = cantactualizaciones;
               updates.setTickEnd(cantactualizaciones);
               updates.setTickStart(0);
               //updates.setRangePinsByValue(0,cantactualizaciones);
               final TextView tvcantidad = (TextView) cantupds.findViewById(R.id.cantadescargar);
               tvcantidad.setText(cantactualizaciones+"");
               updates.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                   @Override
                   public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                       int izq = Integer.valueOf(leftPinValue);
                       int der = Integer.valueOf(rightPinValue);
                       cantidaddecorreos = der - izq;
                       tvcantidad.setText(cantidaddecorreos + " ( del "+ izq + " - al " + der + " ) ");
                       indiceinicio = izq;
                   }
               });
               descargar.setView(cantupds);
           }



        descargar.setPositiveButton("DESCARGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Correo_Leer_AsyncTask(receiveMailActivity).execute(read,cantidaddecorreos,indiceinicio);
            }
        });
        descargar.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // dialog.dismiss();

            }
        });

            descargar.setNeutralButton("Obviar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("contadordecorreos"+read.mcategoria,read.cantidadactual+1).commit();
                }
            });
        descargar.create().show();
           // statusDialog.dismiss();

        }else
            Toast.makeText(context,"No hay nuevas actualizaciones",Toast.LENGTH_LONG).show();

    }
        else if( o instanceof  String)
    {

       // statusDialog.dismiss();
        Toast.makeText(context,o.toString(),Toast.LENGTH_LONG).show();

    }
      //  statusDialog.dismiss();

    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        statusDialog.show();
        statusDialog.setMessage(values.toString());
    }


    @Override
    protected void onPreExecute() {
        statusDialog = new ProgressDialog(context);
        statusDialog.setIndeterminate(false);
       statusDialog.setCancelable(true);
        statusDialog.show();
        statusDialog.setMessage("Calculando Actualizaciones ...");
    }
}
