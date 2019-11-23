package cu.fullapp;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by renier on 29/10/2016.
 */

public class Vista_Nueco_Comentario extends DialogFragment {
    Soporte_Elementos_Comunes soporte;
    AlertDialog.Builder builder;
    String identificador="";


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View dialogo = getActivity().getLayoutInflater().inflate(R.layout.dialog_nuevo_comment,null);
        final Context contexto = getContext();
        soporte = new Soporte_Elementos_Comunes(contexto);

        identificador = getArguments().getString("identificador");
        //final String ident =  getIntent().getExtras().getString("publicacion") ;
        final RatingBar rcoment = (RatingBar) dialogo.findViewById(R.id.rbCalificacion);
        final TextInputEditText texto = (TextInputEditText) dialogo.findViewById(R.id.etOpinion);
        ImageView cerrar,guardar;
        cerrar = (ImageView) dialogo.findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        guardar = (ImageView) dialogo.findViewById(R.id.guardar);
        Drawable save = soporte.PintarIconos(R.drawable.img_guardar,R.color.colorPrincipalApp);
        guardar.setImageDrawable(save);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manejo_DB base_datos = new Manejo_DB(contexto);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
                String autor= prefs.getString("correo","") ;
                Comentario coment = new Comentario();
                coment.setEntrada(identificador);
                coment.setComentario(texto.getText().toString());
                coment.setRating(rcoment.getRating());
                coment.setAutor(autor);
                coment.setEnviado(0);
                coment.setMomento(new Date().getTime());
                coment.setCategoria(base_datos.categoriaPorID(identificador));
                coment.CrearIdentificador();
                base_datos.Insertar_MisComentario(coment);
                dismiss();
                Toast.makeText(contexto,"Se ha guardado su comentario",Toast.LENGTH_LONG).show();
            }
        });
        builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogo);
        return builder.create();

        //setContentView(R.layout.insertar_comentario);

        //soporte.setupToolbar(true,"OPINAR");
       // soporte.setupDrawerLayout();



    }
}
