package cu.fullapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by renier on 21/11/2016.
 */
public class Correo_Leer_AsyncTask extends AsyncTask {
    private ProgressDialog statusDialog;
    private Activity receiveMailActivity;
    private Context context;
    Correo_Leer read;

    public Correo_Leer_AsyncTask(Activity receiveMailActivity) {
        this.receiveMailActivity = receiveMailActivity;
        context = (Context)receiveMailActivity;
        statusDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        statusDialog = new ProgressDialog(context);
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(true);
        statusDialog.show();
        statusDialog.setMessage("Leyendo ...");


    }

    @Override
    protected Object doInBackground(Object[] params) {
        publishProgress( " Buscando actualizaciones...");
        try {
            /// actualizar completo
           // read = new Correo_Leer((Context) params[0], params[1].toString());
            read = (Correo_Leer) params[0] ;
            if (read.isErrorconnect()) {
                    return read.getMsgerror();
                }

                if (read.cantidad() > 0) {
                    publishProgress("Descargando " + params[1].toString() + " actualizaciones ... ");
                    //Log.d("arrive",(int)params[1]+"");
                    read.ExtraerTextoCorreos((int)params[1],(int)params[2]);
                    ArrayList<Object> resultado = read.Actualizaciones();
                    return resultado;
                } else {
                    return "No hay nuevas propuestas.";
                }

            /// solo saber cantidad de actualizaciones


        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());
    }

    @Override
    public void onPostExecute(Object result) {

        if(result instanceof ArrayList)
        {
            //Log.d("is","arraylista");
            statusDialog.setMessage("Guardando actualizaciones");
                ArrayList<Object> resultado = (ArrayList<Object>)result;
                Manejo_DB basedatos = new Manejo_DB(receiveMailActivity.getBaseContext());

            for(Object updater: resultado )
                {
                    if(updater instanceof  Publicacion) {
                        //Log.d("pub",updater.toString());
                        basedatos.Insertar_Post_enBD((Publicacion)updater);

                    }
                   else if(updater instanceof  Comentario) {
                        basedatos.Insertar_Comentario((Comentario)updater);
                    }
                   else if(updater instanceof  Usuario) {
                        basedatos.Insertar_Usuario_enBD((Usuario)updater);
                    }
                  else if(updater instanceof String)
                    {
                        statusDialog.setMessage((String)updater);
                    }
                }
            basedatos.ActualizarRanking();
            int rankpos = basedatos.RankingCurrentUser();

            SharedPreferences.Editor editor =  PreferenceManager.getDefaultSharedPreferences(receiveMailActivity.getBaseContext()).edit();
            editor.putString("ranking", rankpos +"");
            editor.commit();

            if(receiveMailActivity instanceof Portada_Fragment)
            {
               Clase_AdaptadorComentariosPortada comentsportada = (Clase_AdaptadorComentariosPortada) ((RecyclerView)  receiveMailActivity.findViewById(R.id.portada_comments_recycler)).getAdapter();
                comentsportada.comentarios = basedatos.Obtener_Cometarios();
                comentsportada.notifyDataSetChanged();
                Clase_AdaptadorPublicaciones publics =  (Clase_AdaptadorPublicaciones)((RecyclerView)  receiveMailActivity.findViewById(R.id.portada_list_nuevas_publicaciones)).getAdapter();
                publics.list_publicaciones = basedatos.Ultimas_Publicaciones();
                publics.notifyDataSetChanged();

            }
            else if (receiveMailActivity instanceof Anuncios)
            {
                ViewPager pager = (ViewPager)receiveMailActivity.findViewById(R.id.pager);
                String categ = pager.getAdapter().getPageTitle(pager.getCurrentItem()).toString();
                Clase_AdaptadorPublicaciones publics =  (Clase_AdaptadorPublicaciones)((RecyclerView)  receiveMailActivity.findViewById(R.id.lvPublicaciones)).getAdapter();
                publics.list_publicaciones = basedatos.Obtener_PublicacionesPorCategoria(categ);
                publics.notifyDataSetChanged();
                pager.getAdapter().notifyDataSetChanged();
                pager.invalidate();
                TextView textact = (TextView) receiveMailActivity.findViewById(R.id.ultactualizacion);
                Date fechaconvert = new Date();
                PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("fechaultimaactualizacion"+categ,fechaconvert.getTime()).commit();

                String finalmente = "Hasta: " + fechaconvert.getDate() + "/" + (1 + fechaconvert.getMonth())  + "/"  + (1900 + fechaconvert.getYear()) ;
                textact.setText(finalmente);
            }
                   statusDialog.setMessage("Actualizaciones guardadas correctamente");
                       //    Toast.makeText(receiveMailActivity.getBaseContext(), ((ArrayList<String>)result).get(0).toString(), Toast.LENGTH_LONG).show();


        }
        if(result instanceof String)
        {
            statusDialog.dismiss();
            Toast.makeText(receiveMailActivity.getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();

            }


       /* try {
            setMobileDataEnabled(context,false);
        }
        catch (Exception excep)
        {
            Toast.makeText(context,excep.getMessage(),Toast.LENGTH_LONG).show();
        }*/
        statusDialog.dismiss();

    }

    public Drawable ImagenesRedondeadas(Integer drawable) {
        Drawable originalDrawable = ((Context) receiveMailActivity ).getResources().getDrawable(drawable);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create( ((Context) receiveMailActivity ).getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        return roundedDrawable;
    }
    Integer [] ImagenesPorprovincia = new Integer[]
            {
                    R.drawable.img_pinar,
                    R.drawable.img_provincia_habana,
                    R.drawable.img_artemisa,
                    R.drawable.img_mayabeque,
                    R.drawable.img_matanzas,
                    R.drawable.img_cienfuegos,
                    R.drawable.img_villaclara,
                    R.drawable.img_sanctispiritus,
                    R.drawable.img_ciego,
                    R.drawable.img_camaguey,
                    R.drawable.img_tunas,
                    R.drawable.img_granma,
                    R.drawable.img_holguin,
                    R.drawable.img_santiago,
                    R.drawable.img_guantanamo,
                    R.drawable.img_islajuventud

            };

}


/// acciones luego de leer resultados
       /* statusDialog.dismiss();
        if (result instanceof Message[])
        {
            Log.d("mail","devolvio listado");

           // statusDialog.setMessage("Se esta actualizando la app, por favor espere...");
            //  Toast.makeText(receiveMailActivity.getBaseContext(), "Se est", Toast.LENGTH_LONG).show();
            Manejo_DB basedatos = new Manejo_DB(receiveMailActivity.getBaseContext());
            //Message[] listmensajes = (Message[]) result;
           // Log.d("mail excepcion","num de mensajes " + listmensajes.length );
            try
            {
                String mesd = listmensajes[0].getSubject();
                Toast.makeText(receiveMailActivity.getBaseContext(), mesd , Toast.LENGTH_LONG).show();
            }
            catch (MessagingException e)
            {
                Log.d("mail excepcion",e.getMessage() );
            }
            ;
           /* for (Message mensaje : listmensajes) {
                Log.d("mail", "entro al for");
                try {
                    if (mensaje.isMimeType("text/*")) {
                        //result+ mensaje.getContent()
                        //  String contenido =.getContent().toString();
                        Log.d("mail", "es texto");
                        Toast.makeText(receiveMailActivity.getBaseContext(), "Es Texto", Toast.LENGTH_LONG).show();
                        //receiveMailActivity.addContentView();
                    }

                    if (mensaje.isMimeType("multipart/*")) {
                        //result ((Multipart) mensaje.getContent()).getBodyPart(0).toString()
                        //  String contenido =.getContent().toString();
                        Toast.makeText(receiveMailActivity.getBaseContext(), "Es Multipart", Toast.LENGTH_LONG).show();
                        Log.d("mail", "es multipart");
                        //receiveMailActivity.addContentView();
                    }
                } catch (Exception e) {
                    Toast.makeText(receiveMailActivity.getBaseContext(), "Hubo un problema", Toast.LENGTH_LONG).show();
                    Log.d("mail excepcion", "Hubo un problema");
                }
            }*/
      /*  }
            else{
            Log.d("mail","no devolvio listado");
            }


            //}

           // Toast.makeText(receiveMailActivity.getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();*/