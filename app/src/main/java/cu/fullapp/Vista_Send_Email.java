package cu.fullapp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by renier on 06/09/2016.
 */
public class Vista_Send_Email extends AppCompatActivity implements View.OnClickListener {

   Manejo_DB basedatos;
   Soporte_Elementos_Comunes soporte;
    String correo;
    TextInputEditText tvasuntomail;
    TextInputEditText texto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_send_mail);
         correo = getIntent().getExtras().getString("correo");
        String publicac = getIntent().getExtras().getString("publicacion");
        basedatos = new Manejo_DB(this);
        soporte = new Soporte_Elementos_Comunes(this);
        soporte.setupToolbar(true,"Nuevo Correo");
        soporte.setupDrawerLayout();
        tvasuntomail = (TextInputEditText) findViewById(R.id.tvasuntomail);
        if(publicac!=null)
        {
            tvasuntomail.setText("Acerca de: " + publicac);
        }
        TextView destinatario = (TextView)findViewById(R.id.tvdestinatarioMail);
        if(correo!=null) {
          Usuario usuario =  basedatos.Usuario_por_Correo(correo);
            destinatario.setText("Para: " + usuario.getNombre());
        }

        texto = (TextInputEditText)findViewById(R.id.tvtextomail);
        texto.setText(" \n \n \n \n  Enviado desde Fácil ");
      ImageView enviar_correo = (ImageView) findViewById(R.id.enviar_correo);
        enviar_correo.setOnClickListener(this);

        ImageView cancelarmail = (ImageView) findViewById(R.id.cancelarmail);
        cancelarmail.setOnClickListener(this);
        cancelarmail.setImageDrawable(soporte.PintarIconos(R.drawable.img_cerrar,R.color.blanco));
    }

    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.enviar_correo)
       {
      String STATE  = soporte.obtener_preferencia_encriptada("kr9t4br","No registrado",this)   ;
  if(STATE.compareTo("USUARIO REGISTRADO")==0 || STATE.compareTo("APLICACIÓN ACTIVADA")==0) {
      ArrayList<String> destinatarios = new ArrayList<>();
      destinatarios.add(correo);
      new Correo_Enviar_AsyncTask(this, null).execute(destinatarios, tvasuntomail.getText().toString(), texto.getText().toString(), this);
  }
       }
        if(v.getId() == R.id.cancelarmail)
        {
            texto.setText(" \n \n \n \n  Enviado desde Fácil ");
        }
    }
}
