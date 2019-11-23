package cu.fullapp;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by renier on 01/12/2016.
 */
public class Vista_Registro extends DialogFragment implements View.OnClickListener,DialogInterface.OnDismissListener{


    boolean terminado=false;
    enum Estados_Registro {presentacion,advertencia,nombre,confcorreo,opccorreo,protocolo,telefono,stprovincia,inflicencia,categorias,terminar};
    Estados_Registro estado_reg;
    Soporte_Elementos_Comunes soporte;
    AlertDialog.Builder builder;
    SharedPreferences.Editor editor;

    SharedPreferences preferencias;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        soporte = new Soporte_Elementos_Comunes(getContext());
        registro(Estados_Registro.presentacion,null);
        preferencias = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        return builder.create();
    }


    public void Salir() {
           // getActivity().finish();
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
       /* RelativeLayout bt = (RelativeLayout) getActivity().findViewById(R.id.screensplash);
        if(terminado) {
            if(bt!=null) {

                if(bt.getAlpha()>0){
                    //Button comn = (Button) getActivity().findViewById(R.id.boton_continuar);
                 //   Button regi = (Button) getActivity().findViewById(R.id.boton_registrarse);
                    //comn.setVisibility(View.GONE);
                  //  regi.setVisibility(View.GONE);
                    //new Carga_Portada_AsyncTask(getContext()).execute(getContext(),true).notifyAll();

                }

            }
        }
       else
        {
            if(bt!=null)
            {
                if(bt.getAlpha()>0)
                    new Carga_Portada_AsyncTask(getContext()).execute(getContext(),true);
            }
        }*/


    }
    View formulario;
    TextInputLayout campo1,campo2;
    TextInputEditText text1,text2;

    public void registro(final Estados_Registro estado, View form) {
        formulario = form;
        estado_reg = estado;
        TextView textopresentacion;
        switch (estado_reg) {
            case presentacion: {
                builder = new AlertDialog.Builder(getActivity());

                // Get the layout inflater
                LayoutInflater inflater = getActivity().getLayoutInflater();
               // builder.setCancelable(false);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                formulario = inflater.inflate(R.layout.vista_registros, null);
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                logo.setVisibility(View.GONE);
                ImageView anterior = (ImageView) formulario.findViewById(R.id.reganterior);
                anterior.setVisibility(View.GONE);
                ImageView cerrar = (ImageView) formulario.findViewById(R.id.regcerrar);

                Drawable close = soporte.PintarIconos(R.drawable.img_cerrar, R.color.blanco);
                cerrar.setImageDrawable(close);
                cerrar.setOnClickListener(this);
                Button botonaceptar = (Button) formulario.findViewById(R.id.botonaceptar);
                botonaceptar.setText("COMENZAR");
                botonaceptar.setOnClickListener(this);
                 campo1 = (TextInputLayout) formulario.findViewById(R.id.campo1);
                campo1.setVisibility(View.GONE);
                 campo2 = (TextInputLayout) formulario.findViewById(R.id.campo2);
                campo2.setVisibility(View.GONE);

                TextView tvhola = (TextView) formulario.findViewById(R.id.TVHOLA);
                tvhola.setVisibility(View.VISIBLE);
                TextView tvbienv = (TextView) formulario.findViewById(R.id.TVbienvenido);
                tvbienv.setVisibility(View.VISIBLE);
                TextView tvadvert = (TextView) formulario.findViewById(R.id.TVadvertencia);
                tvadvert.setVisibility(View.GONE);
                builder.setView(formulario);

                break;

            }
            case advertencia: {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                logo.setVisibility(View.GONE);
                ImageView anterior = (ImageView) formulario.findViewById(R.id.reganterior);
                Drawable ant = soporte.PintarIconos(R.drawable.img_anterior, R.color.blanco);
                anterior.setImageDrawable(ant);
                anterior.setVisibility(View.VISIBLE);
                anterior.setOnClickListener(this);
                TextView tvhola = (TextView) formulario.findViewById(R.id.TVHOLA);
                tvhola.setText("ADVERTENCIA!");
                tvhola.setVisibility(View.VISIBLE);
                tvhola.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                TextView tvbienv = (TextView) formulario.findViewById(R.id.TVbienvenido);
                tvbienv.setVisibility(View.GONE);

                campo2.setVisibility(View.GONE);
                TextView tvadvert = (TextView) formulario.findViewById(R.id.TVadvertencia);
                tvadvert.setVisibility(View.VISIBLE);
                tvadvert.setText("La información que proveas debe ser correcta, para que otros usuarios puedan contactarte y responder a tus propuestas. Este proceso no consume saldo, pues solo configura tus datos de forma local.");
                Button botonaceptar = (Button) formulario.findViewById(R.id.botonaceptar);
                botonaceptar.setText("ACEPTAR");
                break;

            }
            case nombre: {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                Drawable imgen = soporte.PintarIconos(R.drawable.img_usuario, R.color.blanco);
                logo.setImageDrawable(imgen);
                logo.setVisibility(View.VISIBLE);
                TextView tvHOLA = (TextView) formulario.findViewById(R.id.TVHOLA);
                tvHOLA.setVisibility(View.GONE);


                campo1.setVisibility(View.GONE);

                campo2.setHint("NOMBRE DE USUARIO");
                campo2.setVisibility(View.VISIBLE);
                text2 = (TextInputEditText) formulario.findViewById(R.id.tvcampo2);
                text2.setText(preferencias.getString("nombre",""));
                text2.setTransformationMethod(null);
                text2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                TextView tvadvert = (TextView) formulario.findViewById(R.id.TVadvertencia);
                tvadvert.setVisibility(View.GONE);
                break;
            }
            case confcorreo: {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                Drawable imgen = soporte.PintarIconos(R.drawable.img_correo, R.color.blanco);
                logo.setImageDrawable(imgen);

                campo1.setVisibility(View.VISIBLE);
                campo1.setHint("CORREO");
               text1 = (TextInputEditText) formulario.findViewById(R.id.tvcampo1);
                text1.setText(preferencias.getString("correo",""));
                text1.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


                //campo2.setHint("CLAVE");
                campo2.setVisibility(View.INVISIBLE);
               // text2.setText(preferencias.getString("fckclave",""));
                text1.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
              //  text2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                break;
            }
          /*  case opccorreo: {

                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                Drawable imgen = soporte.PintarIconos(R.drawable.img_settings, R.color.blanco);
                logo.setImageDrawable(imgen);

                campo1.setVisibility(View.VISIBLE);
                campo1.setHint("SERVIDOR DE ENVIO : PUERTO");

                text1.setText(preferencias.getString("serversend","envio.servidor:25"));
                text1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


                campo2.setHint("SERVIDOR DE RECIBO : PUERTO");
                campo2.setVisibility(View.VISIBLE);

                text2.setText(preferencias.getString("serverreceive","recibo.servidor:110"));
                text2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                text2.setTransformationMethod(null);

                LinearLayout opcprotocol = (LinearLayout) formulario.findViewById(R.id.opcprotocol);
                opcprotocol.setVisibility(View.GONE);

                break;
            }*/
          /*  case protocolo: {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                Drawable imgen = soporte.PintarIconos(R.drawable.img_protocolo, R.color.blanco);
                logo.setImageDrawable(imgen);

                campo1.setVisibility(View.GONE);


                campo2.setVisibility(View.GONE);
                LinearLayout opcprotocol = (LinearLayout) formulario.findViewById(R.id.opcprotocol);
                opcprotocol.setVisibility(View.VISIBLE);
                Spinner sslopc = (Spinner) opcprotocol.findViewById(R.id.opcssl);
                sslopc.setBackgroundDrawable(soporte.PintarIconos(R.drawable.airplay, R.color.blanco));

                ArrayAdapter<CharSequence> spadpt = ArrayAdapter.createFromResource(getContext(),R.array.opcssl,android.R.layout.simple_spinner_item);
                spadpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sslopc.setAdapter(spadpt);

               String prot = preferencias.getString("protocolo","pop3");
                if(prot.compareTo("imap")==0)
                {
                    ((RadioButton) opcprotocol.findViewById(R.id.rbImap)).setChecked(true);
                }
                else if (prot.compareTo("pop3")==0)
                {
                    ((RadioButton) opcprotocol.findViewById(R.id.rbPop3)).setChecked(true);
                }
                String opssl = preferencias.getString("usessl","None");

                int posicion = ((ArrayAdapter) sslopc.getAdapter()).getPosition(opssl);
                sslopc.setSelection(posicion);
                break;
            }*/
            case telefono: {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                Drawable imgen = soporte.PintarIconos(R.drawable.img_telefono, R.color.blanco);
                logo.setImageDrawable(imgen);

                campo1.setVisibility(View.GONE);
                ScrollView provincias = (ScrollView) formulario.findViewById(R.id.radioprovincias);
                provincias.setVisibility(View.GONE);

                campo2.setVisibility(View.VISIBLE);
                campo2.setHint("TELEFONO");


                text2.setText(preferencias.getString("telef",""));
                text2.setInputType(InputType.TYPE_CLASS_PHONE | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                text2.setTransformationMethod(null);

                LinearLayout opcprotocol = (LinearLayout) formulario.findViewById(R.id.opcprotocol);
                opcprotocol.setVisibility(View.GONE);
                break;
            }
            case stprovincia: {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                Drawable imgen = soporte.PintarIconos(R.drawable.img_mapmark, R.color.blanco);
                logo.setImageDrawable(imgen);

                campo1.setVisibility(View.GONE);


                campo2.setVisibility(View.GONE);

                ScrollView provincias = (ScrollView) formulario.findViewById(R.id.radioprovincias);
                provincias.setVisibility(View.VISIBLE);


                LinearLayout opcprotocol = (LinearLayout) formulario.findViewById(R.id.opcprotocol);
                opcprotocol.setVisibility(View.GONE);
                break;
            }
            case inflicencia: {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                Drawable imgen = soporte.PintarIconos(R.drawable.img_nolic, R.color.blanco);
                logo.setImageDrawable(imgen);
                logo.setVisibility(View.VISIBLE);
                campo1.setVisibility(View.VISIBLE);
                campo1.setHint("Actividad de la Licencia");
                text2.setTransformationMethod(null);
                text1.setText(preferencias.getString("actlic","Sin Licencia"));
                text1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


                campo2.setVisibility(View.VISIBLE);
                campo2.setHint("Número de Licencia");

                text2.setText(preferencias.getString("nolic","00000"));
                text2.setInputType(InputType.TYPE_CLASS_NUMBER);
                Button botonaceptar = (Button) formulario.findViewById(R.id.botonaceptar);
                botonaceptar.setText("ACEPTAR");

                ScrollView provincias = (ScrollView) formulario.findViewById(R.id.radioprovincias);
                provincias.setVisibility(View.GONE);
                TextView tvadvert = (TextView) formulario.findViewById(R.id.TVadvertencia);
                tvadvert.setVisibility(View.GONE);

                LinearLayout opcprotocol = (LinearLayout) formulario.findViewById(R.id.opcprotocol);
                opcprotocol.setVisibility(View.GONE);
                CheckBox checkboxCOMPUTADORA = (CheckBox) formulario.findViewById(R.id.checkboxCOMPUTADORA);
                checkboxCOMPUTADORA.setVisibility(View.GONE);
                CheckBox checkboxCELULAR = (CheckBox) formulario.findViewById(R.id.checkboxCELULAR);
                checkboxCELULAR.setVisibility(View.GONE);
                CheckBox checkboxTRANSPORTE = (CheckBox) formulario.findViewById(R.id.checkboxTRANSPORTE);
                checkboxTRANSPORTE.setVisibility(View.GONE);
                CheckBox checkboxHOGAR = (CheckBox) formulario.findViewById(R.id.checkboxHOGAR);
                checkboxHOGAR.setVisibility(View.GONE);
                CheckBox checkboxMISCELANEAS = (CheckBox) formulario.findViewById(R.id.checkboxMISCELANEAS);
                checkboxMISCELANEAS.setVisibility(View.GONE);
                break;
            }
          /*  case categorias:
            {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
               // Drawable imgen = soporte.PintarIconos(R.drawable.img_filter, R.color.blanco);
                logo.setVisibility(View.GONE);
                CheckBox checkboxCOMPUTADORA = (CheckBox) formulario.findViewById(R.id.checkboxCOMPUTADORA);
                checkboxCOMPUTADORA.setVisibility(View.VISIBLE);
                checkboxCOMPUTADORA.setChecked(true);
                CheckBox checkboxCELULAR = (CheckBox) formulario.findViewById(R.id.checkboxCELULAR);
                checkboxCELULAR.setVisibility(View.VISIBLE);
                checkboxCELULAR.setChecked(true);
                CheckBox checkboxTRANSPORTE = (CheckBox) formulario.findViewById(R.id.checkboxTRANSPORTE);
                checkboxTRANSPORTE.setVisibility(View.VISIBLE);
                checkboxTRANSPORTE.setChecked(true);
                CheckBox checkboxHOGAR = (CheckBox) formulario.findViewById(R.id.checkboxHOGAR);
                checkboxHOGAR.setVisibility(View.VISIBLE);
                checkboxHOGAR.setChecked(true);
                CheckBox checkboxMISCELANEAS = (CheckBox) formulario.findViewById(R.id.checkboxMISCELANEAS);
                checkboxMISCELANEAS.setVisibility(View.VISIBLE);
                checkboxMISCELANEAS.setChecked(true);
                TextView tvadvert = (TextView) formulario.findViewById(R.id.TVadvertencia);
                tvadvert.setVisibility(View.GONE);
                TextView tvhola = (TextView) formulario.findViewById(R.id.TVHOLA);
                tvhola.setVisibility(View.VISIBLE);
                tvhola.setText("¿Qué Temas te interesan?");
                tvhola.setTextSize(TypedValue.COMPLEX_UNIT_SP,19);
                campo1.setVisibility(View.GONE);

                campo2.setVisibility(View.GONE);
                Button botonaceptar = (Button) formulario.findViewById(R.id.botonaceptar);
                botonaceptar.setText("ACEPTAR");
                break;
            }*/
            case terminar: {
                ImageView logo = (ImageView) formulario.findViewById(R.id.img_current);
                Drawable imgen = soporte.PintarIconos(R.drawable.img_guardar, R.color.blanco);
                TextView tvhola = (TextView) formulario.findViewById(R.id.TVHOLA);
                tvhola.setVisibility(View.GONE);
                logo.setImageDrawable(imgen);
                logo.setVisibility(View.VISIBLE);
                TextView tvadvert = (TextView) formulario.findViewById(R.id.TVadvertencia);
                tvadvert.setVisibility(View.VISIBLE);
                tvadvert.setText("Has terminado. Con estos datos, otros usuarios podrán contactarte. GRACIAS POR USAR FÁCIL.");
                Button botonaceptar = (Button) formulario.findViewById(R.id.botonaceptar);
                botonaceptar.setText("TERMINAR");

                LinearLayout opcprotocol = (LinearLayout) formulario.findViewById(R.id.opcprotocol);
                opcprotocol.setVisibility(View.GONE);
                CheckBox checkboxCOMPUTADORA = (CheckBox) formulario.findViewById(R.id.checkboxCOMPUTADORA);
                checkboxCOMPUTADORA.setVisibility(View.GONE);
                CheckBox checkboxCELULAR = (CheckBox) formulario.findViewById(R.id.checkboxCELULAR);
                checkboxCELULAR.setVisibility(View.GONE);
                CheckBox checkboxTRANSPORTE = (CheckBox) formulario.findViewById(R.id.checkboxTRANSPORTE);
                checkboxTRANSPORTE.setVisibility(View.GONE);
                CheckBox checkboxHOGAR = (CheckBox) formulario.findViewById(R.id.checkboxHOGAR);
                checkboxHOGAR.setVisibility(View.GONE);
                CheckBox checkboxMISCELANEAS = (CheckBox) formulario.findViewById(R.id.checkboxMISCELANEAS);
                checkboxMISCELANEAS.setVisibility(View.GONE);

                campo1.setVisibility(View.GONE);

                campo2.setVisibility(View.GONE);

                break;
            }
        }
    }


        @Override
        public void onClick(View v) {

            switch (v.getId())
        {
            case R.id.regcerrar:
            {
                AlertDialog.Builder construct = new AlertDialog.Builder(getActivity());
               final DialogFragment padre = this;
// 2. Chain together various setter methods to set the dialog characteristics
                construct.setMessage("¿Estás seguro que deseas salir del formulario de registro?")
                        .setTitle("Salir sin registrarse")
                        .setNegativeButton("Continuar Registro", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                             dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                              Salir();

                            }
                        });

// 3. Get the AlertDialog from create()
                AlertDialog dialog = construct.create();
                dialog.show();
                break;
            }
            case R.id.reganterior:
            {
                switch (estado_reg)
                {
                    case advertencia: {
                        View padre = v.getRootView();
                        //// guardardatos
                        ImageView anterior = (ImageView) padre.findViewById(R.id.reganterior);
                        anterior.setVisibility(View.GONE);

                        Button botonaceptar = (Button) padre.findViewById(R.id.botonaceptar);
                        botonaceptar.setText("COMENZAR");


                        TextView tvhola = (TextView) padre.findViewById(R.id.TVHOLA);
                        tvhola.setVisibility(View.VISIBLE);
                        tvhola.setText("¡HOLA!");
                        TextView tvbienv = (TextView) padre.findViewById(R.id.TVbienvenido);
                        tvbienv.setVisibility(View.VISIBLE);
                        TextView tvadvert = (TextView) padre.findViewById(R.id.TVadvertencia);
                        tvadvert.setVisibility(View.GONE);

                        estado_reg = Estados_Registro.presentacion;

                        break;
                    }
                    case nombre: {

                        registro(Estados_Registro.advertencia, v.getRootView());
                        break;
                    }
                    case confcorreo:
                    {

                        registro(Estados_Registro.nombre,v.getRootView());
                        break;
                    }
                 /*   case opccorreo:
                    {

                        registro(Estados_Registro.confcorreo,v.getRootView());
                        break;
                    }
                    case protocolo:
                    {

                        registro(Estados_Registro.opccorreo,v.getRootView());
                        break;
                    }*/
                    case telefono:
                    {

                        registro(Estados_Registro.confcorreo,v.getRootView());
                        break;
                    }
                    case stprovincia:
                    {

                        registro(Estados_Registro.telefono,v.getRootView());
                        break;
                    }
                    case inflicencia:
                    {

                        registro(Estados_Registro.stprovincia,v.getRootView());
                        break;
                    }
                  /*  case categorias:
                    {

                        registro(Estados_Registro.inflicencia,v.getRootView());
                        break;
                    }*/
                    case terminar:
                    {

                        registro(Estados_Registro.inflicencia,v.getRootView());
                        break;
                    }

                }
                break;
            }
            case R.id.botonaceptar:
            {
                switch (estado_reg) {
                    case presentacion: {

                        registro(Estados_Registro.advertencia, v.getRootView());
                        break;
                    }
                    case advertencia: {

                        registro(Estados_Registro.nombre, v.getRootView());
                        break;
                    }
                    case nombre: {

                        String nombre =  text2.getText().toString();
                        if(nombre.length()>1) {
                            editor.putString("nombre", nombre);
                            editor.commit();


                            registro(Estados_Registro.confcorreo, v.getRootView());
                        }
                        else
                        Toast.makeText(getContext(),"Nombre muy corto",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case confcorreo:
                    {

                        String correo =  text1.getText().toString();
                        String patron =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";;
                        boolean correovalido = correo.matches(patron);
                       // String clave = text2.getText().toString();

                        if(correovalido ) {
                            editor.putString("correo", correo);

                           // editor.putString("fckclave", clave);
                            editor.commit();

                          /*  correo = correo.split("@")[1];
                            if (correo.compareTo("nauta.com.cu") == 0 || correo.compareTo("nauta.cu") == 0) {
                                editor.putString("serversend", "smtp.nauta.cu:25");
                                editor.putString("serverreceive", "pop.nauta.cu:110");
                                editor.putString("protocolo", "pop3");
                                editor.putString("usessl", "None");
                                editor.commit();*/
                                registro(Estados_Registro.telefono, v.getRootView());
                                break;
                            }

                            //// SOPORTE DEFAULT PARA GMAIL
                           /* if (correo.compareTo("gmail.com") == 0) {
                                editor.putString("serversend", "smtp.gmail.com:465");
                                editor.putString("serverreceive", "imap.gmail.com:993");
                                editor.putBoolean("usessl", true);
                                editor.putString("protocol", "imap");
                                editor.commit();

                                registro(Estados_Registro.telefono, v.getRootView());

                                break;
                            }*/

                          //  registro(Estados_Registro.opccorreo, v.getRootView());
                       // }
                        else
                            Toast.makeText(getContext(),"Correo o clave no válidos",Toast.LENGTH_LONG).show();
                        break;

                    }
                   /* case opccorreo:
                    {
                        //// guardardatos
                        /// si el correo es nauta o gmail ir directo a telefono y configurar opciones directamente

                        String servenv =  text1.getText().toString();
                        String servrec =  text2.getText().toString();
                        String puertoe[],puertorec[];

                        //Integer.parseInt(puertoe[1])
                        if(servenv.length()>3 && servrec.length()>3) {
                            if(servenv.contains(":") && servrec.contains(":")) {
                                puertoe = servenv.split(":");
                                puertorec = servrec.split(":");
                                if (puertoe[1].matches("\\d+") && puertorec[1].matches("\\d+")) {
                                    editor.putString("serversend", servenv);
                                    editor.putString("serverreceive", servrec);
                                    editor.commit();
                                    registro(Estados_Registro.protocolo, v.getRootView());
                                } else
                                    Toast.makeText(getContext(), "Los puertos deben ser números.", Toast.LENGTH_LONG).show();
                            }else
                                Toast.makeText(getContext(), "Debe configurar los puertos después de los 2 puntos, ej \"imap.nauta.cu:143\" ", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getContext(),"Configure correctamente la información del servidor",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case protocolo:
                    {
                        //// guardardatos
                        RadioGroup radioprot = (RadioGroup) formulario.findViewById(R.id.protocolo);

                       switch (radioprot.getCheckedRadioButtonId())
                       {

                            case R.id.rbImap:
                            {
                                editor.putString("protocolo","imap");
                               break;
                            }
                           case R.id.rbPop3:
                           {
                               editor.putString("protocolo","pop3");
                               break;
                           }
                       }
                        Spinner chssl = (Spinner) formulario.findViewById(R.id.opcssl);
                        String usessl = chssl.getSelectedItem().toString();
                        editor.putString("usessl",usessl);
                        editor.commit();
                        registro(Estados_Registro.telefono,v.getRootView());
                        break;
                    }*/
                    case telefono:
                    {
                        //// guardardatos

                        String telefono =  text2.getText().toString();
                        if(telefono.length()>4 && telefono.length() <= 8) {
                            editor.putString("telef", telefono);
                            editor.commit();
                            registro(Estados_Registro.stprovincia, v.getRootView());
                        }
                        else
                            Toast.makeText(getContext(),"Teléfono no válido, evite el 53 del código de país y asegúrese de que el número que introdujo no tiene más de 8 dígitos.",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case stprovincia:
                    {
                        //// guardardatos

                        RadioGroup radioprov = (RadioGroup) formulario.findViewById(R.id.rgprovincias);

                        RadioButton provselect = (RadioButton) formulario.findViewById( radioprov.getCheckedRadioButtonId());
                        if(provselect!=null) {
                            String psel = provselect.getText().toString();
                            String[] provincias = getActivity().getResources().getStringArray(R.array.arProvincias);
                            for (int i = 0; i < provincias.length; i++) {

                                if (provincias[i].compareTo(psel) == 0) {
                                    editor.putString("prov", i + "");
                                    editor.commit();
                                    continue;
                                }
                            }

                            registro(Estados_Registro.inflicencia, v.getRootView());
                        }
                        else
                            Toast.makeText(getContext(),"Seleccione su provincia",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case inflicencia:
                    {
                        //// guardardatos
                        String actlic =  text1.getText().toString();
                        String nolic =  text2.getText().toString();
                        if(actlic.length()>2 && nolic.length()>2) {
                            editor.putString("actlic", actlic);
                            editor.putString("nolic", nolic);
                            editor.commit();
                            registro(Estados_Registro.terminar, v.getRootView());
                        }
                        else
                            Toast.makeText(getContext(),"Información no válida",Toast.LENGTH_LONG).show();
                        break;
                    }
                   /* case categorias:
                    {
                        //// guardardatos
                        CheckBox checkboxCOMPUTADORA = (CheckBox) formulario.findViewById(R.id.checkboxCOMPUTADORA);
                        checkboxCOMPUTADORA.setVisibility(View.VISIBLE);
                        CheckBox checkboxCELULAR = (CheckBox) formulario.findViewById(R.id.checkboxCELULAR);
                        checkboxCELULAR.setVisibility(View.VISIBLE);
                        CheckBox checkboxTRANSPORTE = (CheckBox) formulario.findViewById(R.id.checkboxTRANSPORTE);
                        checkboxTRANSPORTE.setVisibility(View.VISIBLE);
                        CheckBox checkboxHOGAR = (CheckBox) formulario.findViewById(R.id.checkboxHOGAR);
                        checkboxHOGAR.setVisibility(View.VISIBLE);
                        CheckBox checkboxMISCELANEAS = (CheckBox) formulario.findViewById(R.id.checkboxMISCELANEAS);
                        checkboxMISCELANEAS.setVisibility(View.VISIBLE);
                        boolean categoriacheck = false;

                        if(checkboxCOMPUTADORA.isChecked() )
                        {
                            editor.putBoolean("subcripcion_computadora",true);
                            categoriacheck = true;

                        }
                        else
                        {
                            editor.putBoolean("subcripcion_computadora",false);
                        }
                        if(checkboxCELULAR.isChecked() )
                        {
                            editor.putBoolean("subcripcion_celular",true);
                            categoriacheck = true;
                        }
                        else
                        {
                            editor.putBoolean("subcripcion_celular",false);
                        }
                        if(checkboxTRANSPORTE.isChecked() )
                        {
                            editor.putBoolean("subcripcion_transporte",true);
                            categoriacheck = true;
                        }
                        else
                        {
                            editor.putBoolean("subcripcion_transporte",false);
                        }
                        if(checkboxHOGAR.isChecked() )
                        {
                            editor.putBoolean("subcripcion_hogar",true);
                            categoriacheck = true;
                        }
                        else
                        {
                            editor.putBoolean("subcripcion_hogar",false);
                        }
                        if(checkboxMISCELANEAS.isChecked() )
                        {
                            editor.putBoolean("subcripcion_miscelaneas",true);
                            categoriacheck = true;
                        }
                        else
                        {
                            editor.putBoolean("subcripcion_miscelaneas",false);
                        }
                        if(!categoriacheck)
                        {
                            Toast.makeText(getContext(),"Debe Seleccionar al menos una categoría",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                                editor.commit();
                                registro(Estados_Registro.terminar, v.getRootView());
                        }

                        break;
                    }*/
                    case terminar:
                    {
                        //// guardardatos y enviar correo

                        ObjectAnimator animacionregist = ObjectAnimator.ofFloat(v.getRootView(), "alpha", 1f, 0f);
                        animacionregist.setDuration(500);
                        animacionregist.setInterpolator(new AccelerateInterpolator());
                        animacionregist.start();
                        terminado = true;



                    /*    getActivity().findViewById(R.id.nav_view).invalidate();
                    if(preferencias.getBoolean("subcripcion_computadora",false)) {
                        ArrayList<String> destinatariopc = new ArrayList<>();
                        destinatariopc.add("facil-app-computadora+join@googlegroups.com");
                        new Enviar_Registro_AsyncTask(getActivity(), true, "computadora", false).execute(destinatariopc, "", "", getContext());
                        preferencias.edit().putBoolean("subcripcion_computadora",false).apply();
                    }
                        if(preferencias.getBoolean("subcripcion_celular",false)) {
                            ArrayList<String> destinatariocel = new ArrayList<>();

                            destinatariocel.add("facil-app-celular+join@googlegroups.com");
                            new Enviar_Registro_AsyncTask(getActivity(), true, "celular", false).execute(destinatariocel, "", "", getContext());
                            preferencias.edit().putBoolean("subcripcion_celular",false).apply();
                        }
                        if(preferencias.getBoolean("subcripcion_transporte",false)) {
                            ArrayList<String> destinatariotra = new ArrayList<>();
                            destinatariotra.add("facil-app-transporte+join@googlegroups.com");
                            new Enviar_Registro_AsyncTask(getActivity(), true, "transporte", false).execute(destinatariotra, "", "", getContext());
                            preferencias.edit().putBoolean("subcripcion_transporte",false).apply();
                        }
                        if(preferencias.getBoolean("subcripcion_hogar",false)) {
                            ArrayList<String> destinatariohog = new ArrayList<>();
                            destinatariohog.add("facil-app-hogar+join@googlegroups.com");
                            new Enviar_Registro_AsyncTask(getActivity(), true, "hogar", false).execute(destinatariohog, "", "", getContext());
                            preferencias.edit().putBoolean("subcripcion_hogar",false).apply();
                        }
                        if(preferencias.getBoolean("subcripcion_miscelaneas",false)) {
                            ArrayList<String> destinatariomisc = new ArrayList<>();
                            destinatariomisc.add("facil-app-miscelaneas+join@googlegroups.com");
                            new Enviar_Registro_AsyncTask(getActivity(), true, "miscelaneas", false).execute(destinatariomisc, "", "", getContext());
                            preferencias.edit().putBoolean("subcripcion_miscelaneas",false).apply();
                        }
                        ArrayList<String> destinatariopub = new ArrayList<>();
                        destinatariopub.add("facil-app-publicaciones+join@googlegroups.com");
                        new Enviar_Registro_AsyncTask(getActivity(), true, "publicaciones", false).execute(destinatariopub, "", "", getContext());*/


                        //Set<String> prefcategorias= new HashSet<String>();
                        //MultiSelectListPreference multi = (MultiSelectListPreference)PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet("subcripciones",null);
                        // prefcategorias= PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet("subcripciones",null);


                       /* for(int i = 0 ; i < destinatarios.size() ; i++)
                            multi.setEntryValues((CharSequence[])destinatarios.toArray());
                            //prefcategorias.add(destinatarios.get(i));
                        editor.putStringSet("subcripciones",prefcategorias);
                        editor.commit();*/

                        //editor.putString("ac42txf","REGISTRADO").commit();

                        dismiss();
                        //Toast.makeText(getContext(),"Por favor, espera que se registren todas las categorías que seleccionaste",Toast.LENGTH_LONG).show();

                        Soporte_Elementos_Comunes.cambiar_preferencia_encritada ("kr9t4br","USUARIO REGISTRADO",getContext());

                        if(getActivity() instanceof Portada_Fragment)
                        {

                            ((TextView) getActivity().findViewById(R.id.portada_saludo)).setText("Hola, " + preferencias.getString("nombre", "") + "!");
                            ImageView imgdraw = ((ImageView) getActivity().findViewById(R.id.portada_img_provincia));
                            if(imgdraw!=null) {
                               // imgdraw.setImageDrawable(soporte.Avatar());
                                soporte.Avatar(getContext(),imgdraw);
                              // imgdraw.setImageDrawable(soporte.ImagenesRedondeadas(soporte.ImagenesPorprovincia[Integer.parseInt(preferencias.getString("prov", ""))]));
                                ((TextView) getActivity().findViewById(R.id.actv_lic_portada)).setText(preferencias.getString("actlic","Sin licencia"));
                                ((Button) getActivity().findViewById(R.id.botonregistroportada)).setText("CONTRIBUIR");
                                ((Button) getActivity().findViewById(R.id.botonregistroportada)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Soporte_Elementos_Comunes.MostrarActivacion(getContext());
                                    }
                                });
                            }
                        }
                        Toast.makeText(getContext(),"Gracias por registrarte. Ya puedes publicar y comentar.",Toast.LENGTH_LONG).show();
                        break;
                    }

                }
            }
        }
    }

}
