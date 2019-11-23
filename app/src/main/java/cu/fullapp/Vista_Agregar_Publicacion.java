package cu.fullapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by renier on 03/09/2016.
 */
public class Vista_Agregar_Publicacion extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{

    Publicacion publicacion = null;
    SharedPreferences prefs;
    boolean sepuedeguardar = false;
    Soporte_Elementos_Comunes soporte;
    Integer [] arrays_subcategorias = new Integer[]{
            R.array.arCatPC,
            R.array.arCatCEL,
            R.array.arCatTRANS,
            R.array.arCatINMOB,
            R.array.arCatMISCELANEAS
    };
    Spinner subcategoria;
    Spinner categoria;
    Spinner provincia;
    TextInputEditText tvtitulop;
    TextInputEditText contenido;
     EditTextPers  etpersPrecio;
    CheckBox extoybuscando;

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_agregar_publicacion);
        soporte = new Soporte_Elementos_Comunes(this);
        soporte.setupToolbar(true,"Nueva Propuesta");
        soporte.setupDrawerLayout();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //LinearLayout contenedor_agregar_pub = (LinearLayout) findViewById(R.id.contenedor_agregar_pub);
       // contenedor_agregar_pub.setPadding(5,5,5,5);



/// definiedo categoria
        categoria = (Spinner) findViewById(R.id.spCategoria);
        subcategoria = (Spinner) findViewById(R.id.spSubcategoria);

        ArrayAdapter<CharSequence> adtcategoria = ArrayAdapter.createFromResource(this,R.array.arCategorias, android.R.layout.simple_spinner_item);
        adtcategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adtcategoria);


        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayAdapter<CharSequence> adtsubcategoria = ArrayAdapter.createFromResource(getBaseContext(), arrays_subcategorias[categoria.getSelectedItemPosition()], android.R.layout.simple_spinner_item);
                adtsubcategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subcategoria.setAdapter(adtsubcategoria);

            /*    if (prefs.getBoolean("subcripcion_"+categoria.getItemAtPosition(position).toString().toLowerCase(), false)==false)
                {
                    Toast.makeText(getBaseContext(),"No estás registrado en esta categoría, puedes registrarte en \"Configuración\" en el menú lateral",Toast.LENGTH_LONG).show();
                    sepuedeguardar = false;
                }
                else*/
                    sepuedeguardar = true;
                ////------////
                ImageView imgcat = (ImageView)findViewById(R.id.colorcat);
                imgcat.setImageResource( soporte.ColorPorCategoria(categoria.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ///-- FIN CATEGORÍA
        /// -- DEFINIR SUBCATEGORÍA
     /*   ArrayAdapter<CharSequence> adtsubcategoria = ArrayAdapter.createFromResource(getBaseContext(), arrays_subcategorias[categoria.getSelectedItemPosition()], android.R.layout.simple_spinner_item);
        adtsubcategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategoria.setAdapter(adtsubcategoria);*/

        subcategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ImageView imgscat = (ImageView)findViewById(R.id.colorsubcat);
                Drawable imgd = soporte.ImgPorSubCategoria(subcategoria.getSelectedItem().toString(),categoria.getSelectedItem().toString());
                imgscat.setImageDrawable(imgd);

               // subcategoria.setSelection(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        ///-- FIN SUBCATEGORÍA
        /////// ----- Spiner PROVINCIA
        provincia = (Spinner) findViewById(R.id.spProvincia);
        ArrayAdapter<CharSequence> adtPROV = ArrayAdapter.createFromResource(this,R.array.arProvincias, android.R.layout.simple_spinner_item);
        adtPROV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int provin = Integer.parseInt( prefs.getString("prov", "13"));

        provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView imgprov = (ImageView)findViewById(R.id.imgprov);
                imgprov.setImageDrawable( soporte.ImgProvincia(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        provincia.setAdapter(adtPROV);
        provincia.setSelection(provin,true);


         tvtitulop = (TextInputEditText) findViewById(R.id.tvtitulop);
         contenido = (TextInputEditText) findViewById(R.id.contenido);
         etpersPrecio = (EditTextPers) findViewById(R.id.etpersPrecio);

        extoybuscando = (CheckBox) findViewById(R.id.publicarcomoBusco);

        ImageView fbcerrar = (ImageView) findViewById(R.id.cerrar);
        fbcerrar.setOnClickListener(this);

        fbcerrar.setImageDrawable(soporte.PintarIconos(R.drawable.img_cerrar,R.color.blanco));
        ImageView fbguardar = (ImageView) findViewById(R.id.guardar);
        fbguardar.setOnClickListener(this);



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().hasExtra("identificador"))
        {
            String intent = getIntent().getExtras().getString("identificador");

            Manejo_DB basedat = new Manejo_DB(this);
            publicacion =  basedat.Obtener_MiEnvioPorIdentificador(intent);
            tvtitulop.setText(publicacion.getTitulo());
            contenido.setText(publicacion.getContenido());
            float precioalt = publicacion.getPrecio();

            String numerocorrecto = String.format(Locale.US, "%.2f",precioalt);
            float numc = Float.parseFloat(numerocorrecto);
            float partedec = (numc*100)%100;
            if(partedec == 0)
                etpersPrecio.centavos.setText("00");
            else if(partedec < 10)
                etpersPrecio.centavos.setText("0"+ (int) partedec);
            else
                etpersPrecio.centavos.setText( (int) partedec + "");
            etpersPrecio.texto.setText( (int) precioalt+"");

            extoybuscando.setChecked(publicacion.getBusco()>0);

            etpersPrecio.spiner.setSelection( ((ArrayAdapter) etpersPrecio.spiner.getAdapter()).getPosition(publicacion.getMoneda()));

            categoria.setSelection( ((ArrayAdapter)categoria.getAdapter()).getPosition(publicacion.getCategoria()));

            categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ArrayAdapter<CharSequence> adtsubcategoria = ArrayAdapter.createFromResource(getBaseContext(), arrays_subcategorias[categoria.getSelectedItemPosition()], android.R.layout.simple_spinner_item);
                    adtsubcategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subcategoria.setAdapter(adtsubcategoria);
                   /* if (prefs.getBoolean("subcripcion_"+categoria.getItemAtPosition(position).toString().toLowerCase(), false)==false)
                    {
                        Toast.makeText(getBaseContext(),"No estás registrado en esta categoría, puedes registrarte en \"Configuración\" en el menú lateral",Toast.LENGTH_LONG).show();
                        sepuedeguardar = false;
                    }
                    else*/
                        sepuedeguardar = true;

                    if(publicacion!=null)
                    {
                        int posicion = ((ArrayAdapter) subcategoria.getAdapter()).getPosition( publicacion.getSubcategoria());
                        subcategoria.setSelection(posicion);
                    }


                    ////------////
                    ImageView imgcat = (ImageView)findViewById(R.id.colorcat);
                    imgcat.setImageResource( soporte.ColorPorCategoria(categoria.getSelectedItem().toString()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            provincia.setSelection(publicacion.getProvincia());

        }





    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cerrar:
            {
               // cancelar el envio
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                tvtitulop.setText("");
                contenido.setText("");
                etpersPrecio.texto.setText("");
                etpersPrecio.spiner.setSelection(0);
                etpersPrecio.centavos.setText("00");
                categoria.setSelection(0);
                subcategoria.setSelection(0);
                int provin = Integer.parseInt( prefs.getString("prov", "13"));
                provincia.setSelection(provin);
                Toast.makeText(this,"Cambios cancelados",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.guardar:
            {
                if(sepuedeguardar) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    String register = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br", "No Registrado",this);
                    if (register.compareTo("No Registrado") != 0) {

                        Manejo_DB basedat = new Manejo_DB(this);
                        if (publicacion == null) {
                            publicacion = new Publicacion();
                            String correo = prefs.getString("correo", "");
                            publicacion.setCorreo_usuario(correo);
                            publicacion.Crear_Identificador();
                        }
                        if (tvtitulop.getText().toString().compareTo("") != 0) {
                            publicacion.setTitulo(tvtitulop.getText() + "");
                            if (contenido.getText().toString().compareTo("") != 0) {
                                publicacion.setContenido(contenido.getText() + "");
                                if (etpersPrecio.texto.getText().toString().compareTo("") != 0 && etpersPrecio.centavos.getText().toString().compareTo("") != 0) {
                                    publicacion.setPrecio(etpersPrecio.getPrecio());
                                    publicacion.setMoneda(etpersPrecio.spiner.getSelectedItem().toString());
                                    publicacion.setCategoria(categoria.getSelectedItem().toString());
                                    publicacion.setSubcategoria(subcategoria.getSelectedItem().toString());
                                    publicacion.setProvincia(provincia.getSelectedItemPosition());
                                    publicacion.setVisitada(0);
                                    publicacion.setValoracion(5);
                                    publicacion.setEnviado(0);
                                    publicacion.setFecha(new Date());
                                    publicacion.setCantcomentarios(1);
                                    int estoybuscando = 0;
                                    if(extoybuscando.isChecked())
                                        estoybuscando = 1;
                                    publicacion.setBusco(estoybuscando);
                                    int patrocinado = 0;
                                    if(register.compareTo("APLICACIÓN ACTIVADA")==0)
                                        patrocinado = 1;
                                    publicacion.setPatrocinado(patrocinado);
                                    //Log.d("act" , register + " - " + patrocinado + " busco?" + extoybuscando.isChecked() + " -- " + estoybuscando);
                                    //  basedat.Insertar_Post_enBD(publicacion);
                                    basedat.Insertar_Post_enMisPublicaciones(publicacion);

                                    Toast.makeText(this, "Su publicación se ha guardado para enviar, cuando desee puede subirla a través del menú \"Mis Envios\"", Toast.LENGTH_LONG).show();
                                    tvtitulop.setText("");
                                    contenido.setText("");
                                    etpersPrecio.texto.setText("");
                                    etpersPrecio.spiner.setSelection(0);
                                    etpersPrecio.centavos.setText("00");
                                    categoria.setSelection(0);
                                    subcategoria.setSelection(0);
                                    int provin = Integer.parseInt(prefs.getString("prov", "13"));
                                    provincia.setSelection(provin);
                                    extoybuscando.setChecked(false);

                                    publicacion = null;
                                } else
                                    Toast.makeText(this, "El precio debe ser válido.", Toast.LENGTH_LONG).show();

                            } else
                                Toast.makeText(this, "Debe especificar qué propone.", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(this, "Debe poner un título a la publicación.", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "Debe registrarte para poder guardar.", Toast.LENGTH_LONG).show();

                    /////////-------DESCOMENTAREARR
                }
                else
                    Toast.makeText(getBaseContext(),"Tu entrada no se guardó, porque no estás registrado en esta categoría, puedes registrarte en \"Configuración\" en el menú lateral",Toast.LENGTH_LONG).show();
                break;
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

/*  subcategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ImageView imgscat = (ImageView)findViewById(R.id.colorsubcat);
                    Drawable imgd = soporte.ImgPorSubCategoria(subcategoria.getSelectedItem().toString(),categoria.getSelectedItem().toString());
                    imgscat.setImageDrawable(imgd);
                    Log.d("onpause",position+"");
                    // subcategoria.setSelection(position);
//                    Log.d("onpause",((Spinner)view).getAdapter().getCount()+"");
                   parent.setSelection(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                   // subcategoria.setSelection(selected);
                }


            });*/

//   subcategoria.performItemClick(subcategoria,selected,subcategoria.getAdapter().getItemId(selected));
       /*
          subcategoria.post(new Runnable() {
              @Override
              public void run() {
                  subcategoria.setSelection(selected);
              }
          });*/
