package cu.fullapp;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yudel on 09/09/2016.
 */

public class Portada_Fragment extends AppCompatActivity implements View.OnClickListener

{


    Soporte_Elementos_Comunes soporte;
    AlertDialog.Builder builder;
    ImageView avatar;
    final int PERMISSION_REQUEST_CODE = 2;
    private FileChooserDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_portada);

        if (Build.VERSION.SDK_INT <= 19)  //ye olde method
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,       WindowManager.LayoutParams.FLAG_FULLSCREEN);


        soporte = new Soporte_Elementos_Comunes(this);
        soporte.setupToolbar(false,"");
        soporte.setupDrawerLayout();



     String regist =  Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br","Sin configurar",this);
        if(regist.compareTo("Sin configurar")==0 )
            Soporte_Elementos_Comunes.cambiar_preferencia_encritada("kr9t4br","No registrado",this);


        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int height = display.getHeight();

        CardView cardportada = (CardView) findViewById(R.id.card_perfil_portada);

        CoordinatorLayout.LayoutParams cardparams = (CoordinatorLayout.LayoutParams) cardportada.getLayoutParams();
        int bottommargin = (int) ((getResources().getDimension(R.dimen.margeninferior) + getResources().getDimension(R.dimen.portada_img_categ))*1.5);
        int topmargin = (int) (height * 0.2f) ;

        cardparams.height = height - (bottommargin + topmargin)  ;
        cardparams.topMargin = topmargin;
        cardportada.setLayoutParams(cardparams);

        CoordinatorLayout.LayoutParams appbarlay = (CoordinatorLayout.LayoutParams) findViewById(R.id.app_barlayout).getLayoutParams();
        appbarlay.height = (int) (height * 0.5) ;


    }



    @Override
    protected void onResume() {
        super.onResume();
       //



       new Carga_Portada_AsyncTask(this).execute(this,true);
        new AsyncTaskPortada2(this).execute();
        avatar = (ImageView) findViewById(R.id.portada_img_provincia);
        avatar.setOnClickListener(this);



            }












  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_portada, menu);
        return true;
    }*/



    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub\

        if(v.getId()==R.id.portada_img_provincia)
        {  int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        else {
            // Your app already has the permission to access files and folders
            // so you can simply open FileChooser here.
            try {
                dialog = new FileChooserDialog.Builder(FileChooserDialog.ChooserType.FILE_CHOOSER, new FileChooserDialog.ChooserListener() {
                    @Override
                    public void onSelect(String path) {

                  /*  Bitmap foto = BitmapFactory.decodeFile(path);
                        RoundedBitmapDrawable roundedDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), foto);

                        //asignamos el CornerRadius
                        roundedDrawable.setCornerRadius(foto.getHeight());

                    avatar.setImageDrawable(roundedDrawable);*/
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("avatar",path).commit();
                        soporte.Avatar(getBaseContext(),avatar);
                       // DrawerLayout  drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                     //   ImageView imgdrawer = (ImageView) findViewById(R.id.imag_nav_prov);
                       // soporte.Avatar(getBaseContext(),imgdrawer);
                    }
                }).build();
                dialog.show(getSupportFragmentManager(), null);

            }
            catch (Exception e)
            {
                Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();

            }
        }}

    }

   /* public void SetupDrawer()
    {
        Toolbar toolba = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolba);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolba, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView mDrawerList = (NavigationView) findViewById(R.id.nav_view);
        //  mDrawerList.setNavigationItemSelectedListener(soporte.onNavigationItemSelected());
        View header = mDrawerList.getHeaderView(0);
        ImageView img_nav = (ImageView) header.findViewById(R.id.imag_nav_prov);
        //  img_nav.setImageDrawable(ImagenesRedondeadas(R.drawable.img_santiago));

        final Spinner spinner = (Spinner) header.findViewById(R.id.Nav_Spinner_Categorias);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arCategorias, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setBackgroundDrawable(soporte.PintarIconos(R.drawable.airplay, R.color.blanco));
        //spinner.setOnItemSelectedListener(this);

        ImageView ivbutgo = (ImageView) header.findViewById(R.id.button_go);
        ivbutgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = spinner.getSelectedItemPosition();
                if( drawer.isDrawerOpen(GravityCompat.START))
                {

                    Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                    anuncios.putExtra("categoria", position);
                    drawer.closeDrawer(GravityCompat.START);
                    startActivity(anuncios);

                }
            }
        });
        // Set the adapter for the list view
        // mDrawerList.setAdapter(new ListView_Nav_Drawer_Adapter(activity));
        // mDrawerList.setDivider(null);
        // Set the list's click listener
        //  mDrawerList.setOnItemClickListener(this);

        //Manejo_DB based = new Manejo_DB(contexto);
        Usuario usuario = new Usuario().CRARUSERLOCAL(this);


        ((TextView) header.findViewById(R.id.nombre_nav)).setText(usuario.getNombre());
        ((TextView) header.findViewById(R.id.tvrank_nav)).setText("Ranking " + usuario.getRanking());
        ((RatingBar) header.findViewById(R.id.rbptos_nav)).setRating(usuario.getValoracion());
        // img_nav.setImageDrawable(ImagenesRedondeadas(ImagenesPorprovincia[usuario.getProvincia()]));
        img_nav.setImageDrawable(soporte.Avatar());
        final Button register =    (Button) header.findViewById(R.id.btnregistrodrawer);

        String activacion = soporte.obtener_preferencia_encriptada("kr9t4br","No registrado",this);

        if(activacion.compareTo("No registrado")==0) {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    DialogFragment registro = new Vista_Registro();
                    registro.show(getSupportFragmentManager(), "form_registro");
                }
            });
        }

        final Context contexto = this;
        if(activacion.compareTo("USUARIO REGISTRADO")==0 || activacion.compareTo("APLICACIÓN ACTIVADA")==0){

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    // PROCEDIMIENTO PARA COMPRAR
                    AlertDialog.Builder construir = new AlertDialog.Builder(contexto);
                    final EditText codigo = new EditText(contexto);
                    codigo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    codigo.setTypeface(Typeface.SERIF);
                    construir.setMessage("Para adquirir el código de activación, debes realizar una transferencia al número \n "+ contexto.getResources().getString(R.string.minumerodetelefono) + "\n , desde el número de teléfono con que te registraste y recibirás por correo el código de activación. Tarifa --- Recarga de: \n" +
                            " 0.50 CUC - 60 días (2 meses) \n" +
                            " 1 CUC 150 días (5 meses)\n" +
                            " 2 CUC 365 días (1 año) \n"  )
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


                                            if (codigodecripted.matches("<\\d+/><\\d+/><" + mail + "/>")) {
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
            });
            String dias = soporte.obtener_preferencia_encriptada("db11k9t","0",contexto);

            register.setText("ACTIVAR, " + Integer.parseInt(dias) + " días");
            register.invalidate();
        }
    }
*/

  //  enum Estados_Registro {presentacion,advertencia,nombre,confcorreo,opccorreo,protocolo,telefono,stprovincia,inflicencia,terminar};
  //  Estados_Registro estado_reg;


}



