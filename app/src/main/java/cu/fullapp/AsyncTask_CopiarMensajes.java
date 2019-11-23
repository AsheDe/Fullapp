package cu.fullapp;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by yudel on 09/08/2017.
 */
public class AsyncTask_CopiarMensajes extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        Correo_Leer mail = new Correo_Leer((Context) params[0],params[1].toString());
        mail.CopiarMensajes();
        return null;
    }
}
