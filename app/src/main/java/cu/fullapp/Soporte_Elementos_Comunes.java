package cu.fullapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by renier on 14/09/2016.
 */
public class Soporte_Elementos_Comunes implements NavigationView.OnNavigationItemSelectedListener
{
    Context contexto;
    Toolbar toolbar;
    AppCompatActivity activity;
    DrawerLayout drawer;
    Manejo_DB basedatos;

   // Enum Categorias;

    Integer [] ImagenesSubcategorias = new Integer[]{
            R.drawable.img_computer,
            R.drawable.img_laptop,
            R.drawable.img_consolas,
            R.drawable.img_torre,
            R.drawable.img_monitor,
            R.drawable.img_partespc,
            R.drawable.img_hdd,
            R.drawable.img_tarjvideo,
            R.drawable.img_bateria,
            R.drawable.img_software,
            R.drawable.img_mouse,
            R.drawable.img_android,
            R.drawable.img_iphone,
            R.drawable.img_w2phone,
            R.drawable.img_tablet,
            R.drawable.img_kindle,
            R.drawable.img_cellbasic,
            R.drawable.img_lineas,
            R.drawable.img_accesoriostel,
            R.drawable.img_transporte,
            R.drawable.img_motor,
            R.drawable.img_bicicleta,
            R.drawable.img_pieza_carro,
            R.drawable.img_renta,
            R.drawable.img_reparaciones,
            R.drawable.img_vtacasas,
            R.drawable.img_apartmentos,
            R.drawable.img_alquiler,
            R.drawable.img_permuta,
            R.drawable.img_aireacond,
            R.drawable.img_electrodomesticos,
            R.drawable.img_arthogar,
            R.drawable.img_restaurantes,
            R.drawable.img_arte,
            R.drawable.img_cash,
            R.drawable.img_bisuteria,
            R.drawable.img_empleo,
            R.drawable.img_servicios,
            R.drawable.img_mascotas,
            R.drawable.img_otros
    };

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

    Soporte_Elementos_Comunes(Context cont) {
        contexto = cont;
        activity = ((AppCompatActivity) contexto);
        initImageLoader(cont);
       // Categorias = {"PC","C"};
    }
    Soporte_Elementos_Comunes()
    {

    }


    public void hideStatusBar() {

        if (Build.VERSION.SDK_INT < 16) { //ye olde method
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else { // Jellybean and up, new hotness*/
            View decorView = activity.getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN ;
            //int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //int uiOptions2 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.setSystemUiVisibility(uiOptions);

            decorView.setBackgroundColor(Color.TRANSPARENT);
           // activity.getWindow().setStatusBarColor(decorView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent) );.TRANSPARENT);
            //decorView.setSystemUiVisibility(uiOptions2);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.

        }
    }



    //@TargetApi(16)
    public void OverlayStatusBar() {

        if (Build.VERSION.SDK_INT < 16) { //ye olde method
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                   WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else { // Jellybean and up, new hotness
            View decorView = activity.getWindow().getDecorView();
            // Hide the status bar.
            //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        int uiOptions =  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |  View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
            decorView.setSystemUiVisibility(  uiOptions );
            //int uiOptions2 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //SYSTEM_UI_FLAG_LAYOUT_STABLE
            //decorView.setSystemUiVisibility(uiOptions);
            //decorView.setSystemUiVisibility(uiOptions2);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            //activity.
            decorView.setBackgroundColor(Color.TRANSPARENT);
            //decorView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent) );
        }
    }

   /* public void StatusBarTransparente()
    {
        if (Build.VERSION.SDK_INT < 16)
        { //the old method
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else
        {
            Window window = activity.getWindow();
            window.setStatusBarColor(window.getContext().getResources().getColor(android.R.color.transparent));
        }
    }*/


    /*public static void setTranslucentStatusBar( Window window) {

        if (window == null) return;
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslucentStatusBarLollipop(window);
        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatusBarKiKat(window);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setTranslucentStatusBarLollipop(Window window) {
        window.setStatusBarColor(
                window.getContext()
                        .getResources()
                        .getColor(android.R.color.transparent));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setTranslucentStatusBarKiKat(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }*/


    static public void FullScreen(AppCompatActivity actividad)
    {
        if (Build.VERSION.SDK_INT <= 19)
        {
            actividad.getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
    }

    public void setupToolbar(boolean showtitle, String title) {
        Toolbar toolba = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolba != null) {
            toolbar = toolba;
            toolbar.setTitleTextColor(contexto.getResources().getColor(R.color.blanco));

           // if(showtitle) {
                activity.setSupportActionBar(toolba);
                activity.getSupportActionBar().setDisplayShowTitleEnabled(showtitle);
            if(showtitle)
            {
                activity.getSupportActionBar().setTitle(title);

            }
              //  View decorView = activity.getWindow().getDecorView();
               // decorView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent) );
//                ((TextView) toolbar.findViewById(R.id.tituloactividad)).setText(title);
          //  }
           /* ImageView invitar = (ImageView) toolbar.findViewById(R.id.ivInvitar);
            if (invitar!= null)
            {
                invitar.setImageDrawable(PintarIconos(R.drawable.img_invitar,R.color.blanco));
            }*/
            ImageView ivPorRanking = (ImageView) toolbar.findViewById(R.id.ivPorRanking);
            if (ivPorRanking!= null)
            {
                ivPorRanking.setImageDrawable(PintarIconos(R.drawable.img_pub_por_destacadas,R.color.blanco));
            }
           /* ImageView nuevapub = (ImageView) toolbar.findViewById(R.id.nuevapubLIC);
            if (nuevapub!= null)
            {
               // nuevapub.setImageDrawable(PintarIconos(R.drawable.img_crear_nueva_publicacion,R.color.blanco));
                nuevapub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nuevap = new Intent("cu.fullapp.NUEVA_PUBLICACION");
                        activity.startActivity(nuevap);
                    }
                });
            }*/
        }



    }

    public void setupDrawerLayout() {
        drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView mDrawerList = (NavigationView) activity.findViewById(R.id.nav_view);
        mDrawerList.setNavigationItemSelectedListener(this);
        View header = mDrawerList.getHeaderView(0);
        ImageView img_nav = (ImageView) header.findViewById(R.id.imag_nav_prov);


        ImageView pc = (ImageView) header.findViewById(R.id.gopc);
        ImageView cel = (ImageView) header.findViewById(R.id.gocel);
        ImageView trans = (ImageView) header.findViewById(R.id.gotrans);
        ImageView casa = (ImageView) header.findViewById(R.id.gocasa);
        ImageView misc = (ImageView) header.findViewById(R.id.gomisc);

        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(0);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 0);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });

        cel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(1);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 1);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });

        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(2);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 2);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });

        casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(3);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 3);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });

        misc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(4);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 4);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });


        Usuario usuario = new Usuario().CRARUSERLOCAL(contexto);


        ((TextView) header.findViewById(R.id.nombre_nav)).setText(usuario.getNombre());
        ((TextView) header.findViewById(R.id.tvrank_nav)).setText("Ranking " + usuario.getRanking());
        ((RatingBar) header.findViewById(R.id.rbptos_nav)).setRating(usuario.getValoracion());
       // img_nav.setImageDrawable(ImagenesRedondeadas(ImagenesPorprovincia[usuario.getProvincia()]));
       // img_nav.setImageDrawable(Avatar());
        if(activity instanceof Portada_Fragment)
        {
            Drawable imgr = ImagenesRedondeadas(ImagenesPorprovincia[new Usuario().CRARUSERLOCAL(contexto).getProvincia()]);
            img_nav.setImageDrawable(imgr);
        }
        else
        {
            Avatar(contexto,img_nav);
        }
      final Button register =    (Button) header.findViewById(R.id.btnregistrodrawer);

        String activacion = obtener_preferencia_encriptada("kr9t4br","No registrado",contexto);

if(activacion.compareTo("No registrado")==0) {
    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            drawer.closeDrawer(GravityCompat.START);
            DialogFragment registro = new Vista_Registro();
            registro.show(activity.getSupportFragmentManager(), "form_registro");
        }
    });
}



        if(activacion.compareTo("USUARIO REGISTRADO")==0 || activacion.compareTo("APLICACIÓN ACTIVADA")==0){

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);

                    Soporte_Elementos_Comunes.MostrarActivacion(contexto);
                   // PROCEDIMIENTO PARA COMPRAR
                  /*  AlertDialog.Builder construir = new AlertDialog.Builder(contexto);
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
                                        String telefono = PreferenceManager.getDefaultSharedPreferences(contexto).getString("telef", "");
                                        String fechar = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("ac42txf", "-1", contexto);
                                        String dias = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("db11k9t", "0", contexto);

                                        if (coded.endsWith("F")) {
                                            coded = coded.substring(0, coded.length() - 1);
                                            coded = coded.concat("\n");

                                            String codigodecripted = Soporte_Elementos_Comunes.Desencriptar(coded,contexto);

                                            if(codigodecripted.matches("<\\d+/><\\d+/><"+mail+"/>") || codigodecripted.matches("<\\d+/><\\d+/><"+telefono+"/>") || codigodecripted.matches("<\\d+/><\\d+/><"+"53"+telefono+"/>")) {
                                          //  if (codigodecripted.matches("<\\d+/><\\d+/><" + mail + "/>")) {
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
                    dialog.show();*/

                }
            });
          //  String dias = obtener_preferencia_encriptada("db11k9t","0",contexto);

            register.setText("CONTRIBUIR " );
            register.invalidate();
        }


     //   drawer.setStatusBarBackgroundColor(
       //        contexto.getResources().getColor(R.color.colorT));

    }




    public void setupDrawerLayout(Toolbar tb) {
        drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView mDrawerList = (NavigationView) activity.findViewById(R.id.nav_view);
        mDrawerList.setNavigationItemSelectedListener(this);
        View header = mDrawerList.getHeaderView(0);
        ImageView img_nav = (ImageView) header.findViewById(R.id.imag_nav_prov);
        //  img_nav.setImageDrawable(ImagenesRedondeadas(R.drawable.img_santiago));

       // final Spinner spinner = (Spinner) header.findViewById(R.id.Nav_Spinner_Categorias);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(contexto,
                R.array.arCategorias, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ImageView pc = (ImageView) header.findViewById(R.id.gopc);
        ImageView cel = (ImageView) header.findViewById(R.id.gocel);
        ImageView trans = (ImageView) header.findViewById(R.id.gotrans);
        ImageView casa = (ImageView) header.findViewById(R.id.gocasa);
        ImageView misc = (ImageView) header.findViewById(R.id.gomisc);

        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(0);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 0);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });

        cel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(1);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 1);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });

        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(2);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 2);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });

        casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(3);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 3);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });

        misc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( drawer.isDrawerOpen(GravityCompat.START))
                {
                    if (activity instanceof Anuncios) {
                        ((Anuncios) activity).mViewPager.setCurrentItem(4);
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Intent anuncios = new Intent("cu.fullapp.ANUNCIOS");
                        anuncios.putExtra("categoria", 4);
                        drawer.closeDrawer(GravityCompat.START);
                        contexto.startActivity(anuncios);
                    }
                }
            }
        });
        Usuario usuario = new Usuario().CRARUSERLOCAL(contexto);


        ((TextView) header.findViewById(R.id.nombre_nav)).setText(usuario.getNombre());
        ((TextView) header.findViewById(R.id.tvrank_nav)).setText("Ranking " + usuario.getRanking());
        ((RatingBar) header.findViewById(R.id.rbptos_nav)).setRating(usuario.getValoracion());
       // img_nav.setImageDrawable(ImagenesRedondeadas(ImagenesPorprovincia[usuario.getProvincia()]));
     //   img_nav.setImageDrawable(Avatar());

        if(activity instanceof Portada_Fragment)
        {
            Drawable imgr = ImagenesRedondeadas(ImagenesPorprovincia[new Usuario().CRARUSERLOCAL(contexto).getProvincia()]);
            img_nav.setImageDrawable(imgr);
        }
        else
        {
            Avatar(contexto,img_nav);
        }
        final Button register =    (Button) header.findViewById(R.id.btnregistrodrawer);

        String activacion = obtener_preferencia_encriptada("kr9t4br","No registrado",contexto);

        if(activacion.compareTo("No registrado")==0) {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    DialogFragment registro = new Vista_Registro();
                    registro.show(activity.getSupportFragmentManager(), "form_registro");
                }
            });
        }


        if(activacion.compareTo("USUARIO REGISTRADO")==0 || activacion.compareTo("APLICACIÓN ACTIVADA")==0){

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    Soporte_Elementos_Comunes.MostrarActivacion(contexto);
                    // PROCEDIMIENTO PARA COMPRAR
          /*          AlertDialog.Builder construir = new AlertDialog.Builder(contexto);
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


                                            String telefono = PreferenceManager.getDefaultSharedPreferences(contexto).getString("telef", "");
                                            if(codigodecripted.matches("<\\d+/><\\d+/><"+mail+"/>") || codigodecripted.matches("<\\d+/><\\d+/><"+telefono+"/>") || codigodecripted.matches("<\\d+/><\\d+/><"+"53"+telefono+"/>")) {

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

*/

                                }
                            });

// 3. Get the AlertDialog from create()
                  //  AlertDialog dialog = construir.create();
                   // dialog.show();

              //  }
            //});
          //  String dias = obtener_preferencia_encriptada("db11k9t","0",contexto);

            register.setText("CONTRIBUIR " );
            register.invalidate();
        }


        //   drawer.setStatusBarBackgroundColor(
        //        contexto.getResources().getColor(R.color.colorT));

    }

    public void MostrarRegistro()
    {
        DialogFragment registro = new Vista_Registro();
        registro.show(activity.getSupportFragmentManager(), "form_registro");
    }
    static public void MostrarActivacion(final Context contexto)
    {
        // PROCEDIMIENTO PARA COMPRAR

        AlertDialog.Builder construir = new AlertDialog.Builder(contexto);
        //final EditText codigo = new EditText(contexto);
        //codigo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //codigo.setTypeface(Typeface.SERIF);
        construir.setMessage("Para contribuir con nuestro proyecto, puedes realizar una transferencia de saldo al número \n "+ contexto.getResources().getString(R.string.minumerodetelefono) + "\n , mantenemos este servicio con nuestros propios recursos y toda ayuda se agradece. Muchísimas gracias por tu colaboración. " )
                .setTitle("Contribuir")
                //.setView(codigo)
                .setNegativeButton("Contribuir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder activacion = new AlertDialog.Builder(contexto);
                        activacion.setMessage("Entre los datos de la transferencia");

                        final Vista_Dialogo_Datos_Transf datosrecarga = new Vista_Dialogo_Datos_Transf(contexto);
                        activacion.setView(datosrecarga);
                        activacion.setPositiveButton("Transferir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Soporte_Comunicaciones comunicaciones = new Soporte_Comunicaciones(contexto);
                                if(!datosrecarga.clavetransf.getText().toString().isEmpty())
                                    comunicaciones.Recargar(Integer.parseInt(datosrecarga.clavetransf.getText().toString()),datosrecarga.saldoatransf.getText().toString());
                                else
                                    Toast.makeText(contexto,"Inserte una clave válida",Toast.LENGTH_LONG).show();
                            }
                        });

                        AlertDialog solicitaractivar = activacion.create();
                        solicitaractivar.show();

                    }
                })
                .setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                      /*  try {
                            String coded = codigo.getText().toString();

                            String mail = PreferenceManager.getDefaultSharedPreferences(contexto).getString("correo", "");
                            String fechar = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("ac42txf", "-1", contexto);
                            String dias = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("db11k9t", "0", contexto);

                            if (coded.endsWith("F")) {
                                coded = coded.substring(0, coded.length() - 1);
                                coded = coded.concat("\n");

                                String codigodecripted = Soporte_Elementos_Comunes.Desencriptar(coded,contexto);


                                String telefono = PreferenceManager.getDefaultSharedPreferences(contexto).getString("telef", "");
                                if(codigodecripted.matches("<\\d+/><\\d+/><"+mail+"/>") || codigodecripted.matches("<\\d+/><\\d+/><"+telefono+"/>") || codigodecripted.matches("<\\d+/><\\d+/><"+"53"+telefono+"/>")) {

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
                        }*/



                    }
                });

// 3. Get the AlertDialog from create()
        AlertDialog dialog = construir.create();
        dialog.show();

    }




    public Drawable PintarIconos(Integer drawable, Integer color) {
        Drawable imgs = ContextCompat.getDrawable(contexto, drawable);
        Drawable mutado = imgs.mutate();
        mutado = DrawableCompat.wrap(mutado);
        DrawableCompat.setTint(mutado, contexto.getResources().getColor(color));
        DrawableCompat.setTintMode(mutado, PorterDuff.Mode.SRC_IN);
        return mutado;
    }

    public Drawable ImagenesRedondeadas(Integer drawable) {
        Drawable originalDrawable = contexto.getResources().getDrawable(drawable);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(contexto.getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        return roundedDrawable;
    }

   public void Avatar(Context cont,final ImageView iview)
    {

        String path = PreferenceManager.getDefaultSharedPreferences(contexto).getString("avatar","noavatar").toString();
        File archivo = new File(path);
       if(  archivo.exists())
       {
           if(path.compareTo("noavatar")!=0)
           {

               try {


                   // Bitmap foto = BitmapFactory.decodeFile(path);
                   //RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(contexto.getResources(), foto);
                   //roundedDrawable.setCornerRadius(foto.getHeight());
                   //  Uri urifromfile = Uri.fromFile(new File(path));


                   String uriform = "file://"+path;
                   // ImageLoader.getInstance().displayImage(uriform,iview);
                   //ImageLoader.getInstance().loadImage();
                   ImageLoader cargaimg = ImageLoader.getInstance();
                   ImageSize targetSize = new ImageSize(iview.getWidth(), iview.getHeight()); // result Bitmap will be fit to this size
                   try {
                       cargaimg.loadImage(uriform, targetSize, new SimpleImageLoadingListener() {
                           @Override
                           public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                               // Do whatever you want with Bitmap
                               try {
                                   RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(contexto.getResources(), loadedImage);
                                   roundedDrawable.setCornerRadius(loadedImage.getHeight());
                                   iview.setImageDrawable(roundedDrawable);

                               }
                               catch (Exception e)
                               {
                                   Drawable imgr = ImagenesRedondeadas(ImagenesPorprovincia[new Usuario().CRARUSERLOCAL(contexto).getProvincia()]);
                                   iview.setImageDrawable(imgr);
                                   PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("avatar","noavatar").commit();
                               }

                           }
                       });
                   }  catch (Exception e)
                   {

                       Drawable imgr = ImagenesRedondeadas(ImagenesPorprovincia[new Usuario().CRARUSERLOCAL(contexto).getProvincia()]);
                       iview.setImageDrawable(imgr);
                       PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("avatar","noavatar").commit();
                   }


                   //return roundedDrawable;
               }
               catch (Exception e)
               {

                   Drawable imgr = ImagenesRedondeadas(ImagenesPorprovincia[new Usuario().CRARUSERLOCAL(contexto).getProvincia()]);
                   iview.setImageDrawable(imgr);
                   PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("avatar","noavatar").commit();
               }
           }
           else {
               Drawable imgr = ImagenesRedondeadas(ImagenesPorprovincia[  new Usuario().CRARUSERLOCAL(contexto).getProvincia()]);
               iview.setImageDrawable(imgr);
           }
       }
       else {
           Drawable imgr = ImagenesRedondeadas(ImagenesPorprovincia[  new Usuario().CRARUSERLOCAL(contexto).getProvincia()]);
           iview.setImageDrawable(imgr);
           PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("avatar","noavatar").commit();
       }


    }

    @SuppressWarnings("deprecation")
    private void initImageLoader(Context contexto) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                contexto)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024) // 缓冲大小
                .discCacheFileCount(100) // 缓冲文件数目
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(contexto)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // 2.单例ImageLoader类的初始化
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {



        switch (item.getItemId()) {
            case R.id.muPortada: {
                drawer.closeDrawer(GravityCompat.START);
                Intent portada = NavUtils.getParentActivityIntent(activity);
                portada.putExtra("boton_portada",true);
                NavUtils.navigateUpTo(activity,portada);

                //NavUtils.navigateUpFromSameTask(activity);

                break;
            }
            case R.id.muNueva_Pub: {
                Intent NUEVAPUB = new Intent("cu.fullapp.NUEVA_PUBLICACION");
                contexto.startActivity(NUEVAPUB);
                drawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.muMis_Prefs: {
                String activacion = obtener_preferencia_encriptada("kr9t4br", "No registrado",contexto);
                if (activacion.compareTo("APLICACIÓN ACTIVADA") == 0 || activacion.compareTo("USUARIO REGISTRADO") == 0 ) {
                    Intent prefs = new Intent("cu.fullapp.PREF");
                    contexto.startActivity(prefs);
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    Toast.makeText(contexto, "Debes estar registrado para hacer esto.", Toast.LENGTH_LONG).show();
                    drawer.closeDrawer(GravityCompat.START);

                }
                break;
            }
            case R.id.muUsuarios: {
                Intent users = new Intent("cu.fullapp.USUARIOS");
                contexto.startActivity(users);
                drawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.muMisEnvios: {
                Intent users = new Intent("cu.fullapp.MISENVIOS");
                contexto.startActivity(users);
                drawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.muAyuda: {
                drawer.closeDrawer(GravityCompat.START);
                Intent ayuda = new Intent("cu.fullapp.AYUDA");
                contexto.startActivity(ayuda);

                break;
            }


           /* case R.id.muActualizar: {
                String activacion = obtener_preferencia_encriptada("kr9t4br", "No registrado",contexto);
                if (activacion.compareTo("APLICACIÓN ACTIVADA") == 0 || activacion.compareTo("USUARIO REGISTRADO") == 0 ) {
                    drawer.closeDrawer(GravityCompat.START);
                    new Correo_Leer_AsyncTask(activity).execute(contexto);

                } else {
                    drawer.closeDrawer(GravityCompat.START);
                    Toast.makeText(contexto, "Debes estar registrado para hacer esto.", Toast.LENGTH_LONG).show();
                }


                break;

            }

            case R.id.muEnviar_todo: {

                //// cambiar USUARIO REGISTRADO por "APLICACIÓN ACTIVADA" ESTA OPCIÓN SE DESHABILITA HASTA QUE PAGUE

                String fechar =  Soporte_Elementos_Comunes.obtener_preferencia_encriptada("ac42txf","Sinfecha",contexto);

                if(fechar.compareTo("Sinfecha")!=0)
                {
                    String activacion = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br","No registrado",contexto);
                    if(activacion.compareTo("APLICACIÓN ACTIVADA")==0) {
                        String diasleft = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("db11k9t", "0",contexto);
                        String periodo = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("oft963p", "0",contexto);
                        int diasl = Integer.parseInt(diasleft);  // dias restantes
                        long ahora = new Date().getTime();
                        long fechact = Long.parseLong(fechar);
                        int iperiodo = Integer.parseInt(periodo);
                        int dias = (int) ((ahora - fechact) / 86400000);  /// dias q han pasado desde que activaste
                        if (dias > iperiodo) {
                            Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t", "0",contexto);
                            Soporte_Elementos_Comunes.cambiar_preferencia_encritada("kr9t4br", "USUARIO REGISTRADO",contexto);
                        }
                        else{
                            int timeleft = iperiodo-dias;
                            Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t", timeleft + "",contexto);

                        }
                    }

                }
                else
                {
                    Toast.makeText(contexto, "Se ha violado la seguridad de la aplicación.", Toast.LENGTH_LONG).show();
                    SystemClock.sleep(2500);
                    activity.finish();
                }

                String activacion = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br", "No Registrado",contexto);
                if (activacion.compareTo("APLICACIÓN ACTIVADA") != 0) {

                    Toast.makeText(contexto, "Debes ACTIVAR la aplicación para hacer esto.", Toast.LENGTH_LONG).show();

                } else {

                    basedatos = new Manejo_DB(contexto);
                    ArrayList<ArrayList<Comentario>> comensentcategori = new ArrayList<ArrayList<Comentario>>();

                    ArrayList<ArrayList<Publicacion>> pubensentcatelist = new ArrayList<ArrayList<Publicacion>>();
                    ArrayList<String> grupos = new ArrayList<String>();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(contexto);
                    String[] categorias = contexto.getResources().getStringArray(R.array.arCategorias);
                    String[] allgroups = contexto.getResources().getStringArray(R.array.arGrupoCategoria);
                    if (preferences.getBoolean("subcripcion_computadora", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[0]);
                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[0]);

                        //if(coments.size()>0)
                        comensentcategori.add(coments);

                        //if(pubspc.size()>0 )
                        pubensentcatelist.add(pubspc);

                        //  if(coments.size()>0 || pubspc.size()>0 )
                        grupos.add(allgroups[0]);
                    }
                    if (preferences.getBoolean("subcripcion_celular", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[1]);
                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[1]);
                        // if(coments.size()>0)
                        comensentcategori.add(coments);

                        //  if(pubspc.size()>0 )
                        pubensentcatelist.add(pubspc);

                        //  if(coments.size()>0 || pubspc.size()>0 )
                        grupos.add(allgroups[1]);
                    }
                    if (preferences.getBoolean("subcripcion_transporte", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[2]);
                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[2]);
                        //  if(coments.size()>0)
                        comensentcategori.add(coments);

                        //  if(pubspc.size()>0 )
                        pubensentcatelist.add(pubspc);

                        //  if(coments.size()>0 || pubspc.size()>0 )
                        grupos.add(allgroups[2]);
                    }
                    if (preferences.getBoolean("subcripcion_hogar", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[3]);

                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[3]);
                        //   if(coments.size()>0)
                        comensentcategori.add(coments);

                        //   if(pubspc.size()>0 )
                        pubensentcatelist.add(pubspc);

                        //   if(coments.size()>0 || pubspc.size()>0 )
                        grupos.add(allgroups[3]);

                    }
                    if (preferences.getBoolean("subcripcion_miscelaneas", false)) {
                        ArrayList<Publicacion> pubspc = basedatos.PublicacionesPorEnviar(categorias[4]);
                        ArrayList<Comentario> coments = basedatos.ComentariosPorEnviar(categorias[4]);
                        //  if(coments.size()>0)
                        comensentcategori.add(coments);

                        //   if(pubspc.size()>0 )
                        pubensentcatelist.add(pubspc);

                        //   if(coments.size()>0 || pubspc.size()>0 )
                        grupos.add(allgroups[4]);

                    }

                    Usuario us = new Usuario().CRARUSERLOCAL(contexto);

                    String usuario = "";


                    boolean algoenviado = false;
                    for (int contador = 0; contador < grupos.size(); contador++) {

                        String storecomentarios = null;
                        String storepublicaciones = null;
                        ArrayList<Comentario> comensent = comensentcategori.get(contador);
                        ArrayList<Publicacion> pubensent = pubensentcatelist.get(contador);

                        if (comensent.size() > 0) {

                            storecomentarios = "";
                            for (int i = 0; i < comensent.size(); i++) {
                                storecomentarios += ConfSendMailComentario(comensent.get(i));

                            }
                        }
                        Integer numpubs = null;
                        if (pubensent.size() > 0) {
                            numpubs = pubensent.size();
                            storepublicaciones = "";
                            for (int i = 0; i < pubensent.size(); i++) {
                                storepublicaciones += ConfSendMailPublicaciones(pubensent.get(i));
                            }
                        }


                        if (storecomentarios != null && storepublicaciones != null) {
                            //ArrayList<String> destinatarios = new ArrayList<>();
                            //destinatarios.add("facil-app-publicaciones@googlegroups.com");///// aqui va la direccion del grupo google
                            us.Publicar(contexto,numpubs);
                            usuario = us.toString();
                            String textoencriptado = Soporte_Elementos_Comunes.Encriptar(usuario + "</tp>" + storepublicaciones + "</tp>" + storecomentarios + "</tp>",contexto);
                            long moment = new Date().getTime();
                            String asto = "FACIL-ACTUALIZACION";
                            //String asuntoencriptado = Soporte_Elementos_Comunes.Encriptar(asto,contexto);
                            //String momentenc = Soporte_Elementos_Comunes.Encriptar(moment + "",contexto);
                            new Correo_Enviar_AsyncTask(activity,numpubs).execute(grupos.get(contador), asto, textoencriptado + "<firma>", contexto);
                            algoenviado = true;
                        } else if (storecomentarios == null && storepublicaciones != null) {

                            us.Publicar(contexto,numpubs);
                            usuario = us.toString();
                            String textoencriptado = Soporte_Elementos_Comunes.Encriptar(usuario + "</tp>" + storepublicaciones + "</tp>" + "<null>"  + "</tp>",contexto);
                            long moment = new Date().getTime();
                           // String asuntoencriptado = Soporte_Elementos_Comunes.Encriptar("FACIL-ACTUALIZACION</TOP>",contexto);
                           // String momenc = Soporte_Elementos_Comunes.Encriptar(moment + "",contexto);
                            new Correo_Enviar_AsyncTask(activity,numpubs).execute(grupos.get(contador), "FACIL-ACTUALIZACION", textoencriptado + "<firma>", contexto);

                            algoenviado = true;
                        } else if (storepublicaciones == null && storecomentarios != null) {
                            usuario = us.toString();
                            String textoencriptado = Soporte_Elementos_Comunes.Encriptar(usuario + "</tp>" + "<null>"+ "</tp>" + storecomentarios  + "</tp>",contexto);

                            long moment = new Date().getTime();
                           // String asuntoencriptado = Soporte_Elementos_Comunes.Encriptar("FACIL-ACTUALIZACION</TOP>",contexto);
                           // String momenc = Soporte_Elementos_Comunes.Encriptar(moment + "",contexto);

                            new Correo_Enviar_AsyncTask(activity,numpubs).execute(grupos.get(contador), "FACIL-ACTUALIZACION", textoencriptado + "<firma>", contexto);
                            algoenviado = true;

                        }
                    }
                    if (!algoenviado) {
                        Toast.makeText(contexto, "No tiene nada para enviar", Toast.LENGTH_LONG).show();
                    }
                   /* else {
                        basedatos.ActualizarEnviados();
                        if (activity instanceof Mis_Envios) {
                            RecyclerView recycler = (RecyclerView) activity.findViewById(R.id.publicaciones);
                            AdaptadorMisEnvios adptmisenv = (AdaptadorMisEnvios) (recycler).getAdapter();
                            adptmisenv.envios = basedatos.ObtenerListadoMisEnvios();
                            adptmisenv.notifyDataSetChanged();
                            TextView numbernsend = (TextView) activity.findViewById(R.id.numbernsend);
                            numbernsend.setText(basedatos.SinEnviar()+" sin enviar");
                        }
                    }


                }

            }*/
        }
            return false;

    }



    private String ConfSendMailComentario(Comentario coment)
    {
        String comentariofinal="";
        comentariofinal+="<c1>"+coment.getEntrada()+"</c1>";
        comentariofinal+="<c2>"+coment.getComentario()+"</c2>";
        comentariofinal+="<c3>"+coment.getAutor()+"</c3>";
        long fecha = new Date().getTime();
        basedatos.ActualizarFechasComentarios(fecha,coment.getIdentificador());
        comentariofinal+="<c4>"+fecha+"</c4>";
        comentariofinal+="<c5>"+coment.getRating()+"</c5></sc>";
        return comentariofinal;
    }

    private String ConfSendMailPublicaciones(Publicacion publicacion)
    {

        //String publicacionfinal="<sp"+numero+">";
        String publicacionfinal="";
        publicacionfinal+="<p1>"+ publicacion.getIdent() +"</p1>";
        publicacionfinal+="<p2>"+publicacion.getCorreo_usuario()+"</p2>";
        publicacionfinal+="<p3>"+publicacion.getTitulo()+"</p3>";
        publicacionfinal+="<p4>"+publicacion.getPrecio()+"</p4>";
        publicacionfinal+="<p5>"+publicacion.getContenido()+"</p5>";
        publicacionfinal+="<p6>"+ publicacion.getCategoria() +"</p6>";
        publicacionfinal+="<p7>"+publicacion.getSubcategoria()+"</p7>";
        publicacionfinal+="<p8>"+publicacion.getMoneda()+"</p8>";
        long fecha = new Date().getTime();
        basedatos.ActualizarFechasPublicaciones(fecha,publicacion.getIdent());
        publicacionfinal+="<p9>"+fecha+"</p9>";
        publicacionfinal+="<p10>"+publicacion.getProvincia()+"</p10></sp>";

        return publicacionfinal;
    }

    static public String Desencriptar(String textoEncriptado,Context contexto) throws Exception {

        String secretKey = contexto.getResources().getString(R.string.tqmafrty); //llave para desenciptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decode(textoEncriptado,Base64.DEFAULT);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex)
        {
            return ex.getMessage();
        }
        return base64EncryptedString;
    }

    public static String Encriptar(String texto,Context contexto) {


        String secretKey = contexto.getResources().getString(R.string.tqmafrty); //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encode(buf,Base64.DEFAULT);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }



    public Integer ColorPorCategoria(String categoria)
    {
        switch (categoria)
        {
            case "COMPUTADORA":
            {
                return R.color.colorOverPC;
            }
            case "CELULAR":
            {
                return R.color.colorOverCel;
            }
            case "TRANSPORTE":
            {
                return R.color.colorOverTrans;
            }
            case "HOGAR":
            {
                return R.color.colorOverHogar;
            }
            case "MISCELANEAS":
            {
                return R.color.colorOverMisc;
            }
        }
        return R.color.colorOverMisc;

    }

    public Integer ColorPorCategoriaTransparencia(String categoria)
    {
        switch (categoria)
        {
            case "COMPUTADORA":
            {
                return R.color.colorOverPCTpat;

            }
            case "CELULAR":
            {
                return R.color.colorOverCelTpat;
            }
            case "TRANSPORTE":
            {
                return R.color.colorOverTransTpat;
            }
            case "HOGAR":
            {
                return R.color.colorOverHogarTpat;
            }
            case "MISCELANEAS":
            {
                return R.color.colorOverMiscTpat;
            }
        }
        return R.color.colorOverMiscTpat;

    }

    public Drawable ImgPorSubCategoria(String subcategoria,String categoria)
    {
        String[] arreglo_subcatnomb = contexto.getResources().getStringArray(R.array.NombresSubcategorias) ;
        Integer imagen=null;
        for (int i = 0; i < arreglo_subcatnomb.length; i++ )
        {
            if(arreglo_subcatnomb[i].compareTo(subcategoria)==0)
            {
                imagen =  ImagenesSubcategorias[i];
            }

        }
        if (imagen!=null) {
           // Drawable result = PintarIconos(imagen,ColorPorCategoria(categoria));
            Drawable result = contexto.getResources().getDrawable(imagen);
            return result;
        }
        else
        {
            Drawable result = PintarIconos(R.drawable.img_otros,ColorPorCategoria(categoria));
            return result;
        }


    }

    public Drawable ImgProvincia(int provincia)
    {
        return contexto.getResources().getDrawable(ImagenesPorprovincia[provincia]);

    }

    static String obtener_preferencia_encriptada(String prefname,String defaultv,Context contexto)
    {
        String resultado="";
        try {
            resultado = PreferenceManager.getDefaultSharedPreferences(contexto).getString(prefname,defaultv);
            resultado = Desencriptar(resultado,contexto);

        }
        catch (Exception excepcion)
        {
            Toast.makeText(contexto,excepcion.getMessage(),Toast.LENGTH_LONG).show();
        }
        if(resultado.compareTo("bad base-64")==0)
            resultado = defaultv;
        return resultado;
    }

    static void cambiar_preferencia_encritada(String prefname,String nuevovalor,Context contexto)
    {
        nuevovalor = Encriptar(nuevovalor,contexto);
        PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString(prefname,nuevovalor).commit();
    }

    static void ConteodeCorreosRecibidos(int nuevovalor,Context contexto)
    {

        PreferenceManager.getDefaultSharedPreferences(contexto).edit().putInt("contadordecorreos",nuevovalor).commit();
    }

    static int CantidadCorreosRecibidos(Context contexto)
    {

      return   PreferenceManager.getDefaultSharedPreferences(contexto).getInt("contadordecorreos",0);
    }
}
