package cu.fullapp;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import cu.fullapp.R;

/**
 * Created by yudel on 07/08/2017.
 */
public class Anuncios extends AppCompatActivity implements View.OnClickListener {
    /* Integer[] arrays_subcategorias = { Integer.valueOf(R.array.arCatPC), Integer.valueOf(R.array.arCatCEL), Integer.valueOf
                (R.array.arCatTRANS), Integer.valueOf(R.array.arCatINMOB), Integer.valueOf(R.array.arCatMISCELANEAS) };*/
    Manejo_DB basededatos;
    Context contexto;
    boolean executedsearch = false;
    ArrayList<Integer> filtrosprovincias = new ArrayList();
    ArrayList<String> filtrossubcats = new ArrayList();
    int lastcategory = 0;
    CharSequence[] listasubcategoriasitems;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    float preciomaximo = 10;
    float preciominimo = 5;
    float preciominimoactivo = 5 , preciomaximoactivo = 10 ;
    boolean[] provinciasactivas;
    Soporte_Elementos_Comunes soporte;
    boolean[] subcategoriassactivas;
    SlidingTabLayout tabs;
    String textquery = "";
    float viewpagerposition;
    TextView ultimaupdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncios);

        if (Build.VERSION.SDK_INT <= 19)  //ye olde method
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,       WindowManager.LayoutParams.FLAG_FULLSCREEN);

        basededatos = new Manejo_DB(this);
        this.soporte = new Soporte_Elementos_Comunes(this);
        ultimaupdate = (TextView)findViewById(R.id.ultactualizacion);

        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.mViewPager, this);
        this.mViewPager = ((ViewPager)findViewById(R.id.pager));
        this.mViewPager.setAdapter(this.mSectionsPagerAdapter);
        this.mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //paramBundle = (TextView)findViewById(2131755133);
        this.tabs = ((SlidingTabLayout)findViewById(R.id.tabs));
        this.tabs.setViewPager(this.mViewPager);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco));
        this.tabs.setToolbar(toolbar);
        this.soporte.setupDrawerLayout(toolbar);
        this.viewpagerposition = this.mViewPager.getTranslationY();
        this.contexto = this;

         setupExpandirFiltros();
        if (getIntent().hasExtra("categoria"))
        {
            int i = ((Integer)getIntent().getExtras().get("categoria")).intValue();
            this.mViewPager.setCurrentItem(i);
            this.lastcategory = i;
        }
        ImageView imgordenranking = (ImageView) findViewById(R.id.ivPorRanking);
        imgordenranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String categoria =	mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()).toString();

                Object fragmento = mViewPager.getAdapter().instantiateItem(mViewPager,mViewPager.getCurrentItem());
                Clase_AdaptadorPublicaciones publics =  (Clase_AdaptadorPublicaciones)((RecyclerView)
                        ((Fragment)fragmento).getView().findViewById(R.id.lvPublicaciones)).getAdapter();
                publics.RankearPubs(categoria);
                Toast.makeText(contexto,"Entradas organizadas por valoración",Toast.LENGTH_LONG).show();
            }
        });


        ((ImageView)findViewById(R.id.borrartodoencat)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.ivActualizarCategoria)).setOnClickListener(this);





    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void onClick(View paramView)
    {
      /*  if (paramView.getId() == R.id.ultactualizacion)
        {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.contexto);
            CalendarView localCalendarView = new CalendarView(this.contexto);
            localCalendarView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            localBuilder.setTitle("ACTUALIZAR A PARTIR DE:").setView(localCalendarView).setPositiveButton("ACEPTAR", new
DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
            }).setNegativeButton("NO", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                    paramAnonymousDialogInterface.dismiss();
                }
            });
            localBuilder.create().show();
        }*/



        if (paramView.getId() == R.id.borrartodoencat)
        {

            AlertDialog.Builder  alerta = new AlertDialog.Builder(this.contexto);
            alerta.setTitle("BORRAR TODO");
            alerta.setMessage("Confirma que desea borrar todas las entradas de esta categoría.").setPositiveButton("Sí", new
DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                    Anuncios.this.basededatos.BorrarTodasEntradasPorCategoria(Anuncios.this.mViewPager.getAdapter
().getPageTitle(Anuncios.this.mViewPager.getCurrentItem()).toString());
                    Anuncios.this.mViewPager.getAdapter().notifyDataSetChanged();
                    Anuncios.this.mViewPager.invalidate();
                    Toast.makeText(Anuncios.this.contexto, "Se han borrado todas las entradas en \"" +
Anuncios.this.mViewPager.getAdapter().getPageTitle(Anuncios.this.mViewPager.getCurrentItem()).toString() + "\"",
Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                    paramAnonymousDialogInterface.dismiss();
                }
            });
            alerta.create().show();
        }

        if (paramView.getId() == R.id.ivActualizarCategoria)
        {
          /*  PreferenceManager.getDefaultSharedPreferences(contexto).edit().putInt("contadordecorreosCOMPUTADORA",1).commit();
            PreferenceManager.getDefaultSharedPreferences(contexto).edit().putInt("contadordecorreosCELULAR",1).commit();
            PreferenceManager.getDefaultSharedPreferences(contexto).edit().putInt("contadordecorreosTRANSPORTE",1).commit();
            PreferenceManager.getDefaultSharedPreferences(contexto).edit().putInt("contadordecorreosHOGAR",1).commit();
            PreferenceManager.getDefaultSharedPreferences(contexto).edit().putInt("contadordecorreosMISCELANEAS",1).commit();*/
            new AsyncTask_CantActualizaciones(this).execute(contexto,mViewPager.getAdapter().getPageTitle(Anuncios.this.mViewPager.getCurrentItem()).toString().toUpperCase());
        }

    }




    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (findViewById(R.id.pager)!=null)
        {

            if (mViewPager.getCurrentItem()==0)
                super.onBackPressed();
            else
                mViewPager.setCurrentItem(0);

        }
        else
        {
            super.onBackPressed();
        }
    }




    public void setupExpandirFiltros()
    {
        final ExpandableButtonView localExpandableButtonView = (ExpandableButtonView)findViewById(R.id.expandirfiltros);

        localExpandableButtonView.setButtonColor(getResources().getColor(R.color.modelo));
        localExpandableButtonView.setToolbarColor(getResources().getColor(R.color.modelo));
        localExpandableButtonView.addItem(new ButtonItem[] { new ButtonItem.Builder(this).setImageDrawable
                (this.soporte.PintarIconos(Integer.valueOf(R.drawable.img_texto_filtro), Integer.valueOf
                        (R.color.blanco))).setClickListener(new View.OnClickListener()
        {
            public void onClick(View filtro)
            {
                localExpandableButtonView.removeBottomToolbar();
                AlertDialog.Builder texto = new AlertDialog.Builder(Anuncios.this.contexto);
                View localView = LayoutInflater.from(Anuncios.this.contexto).inflate(R.layout.filtro_buscador, null);
                final SearchView localSearchView = (SearchView)localView.findViewById(R.id.buscador);
                localSearchView.setQuery(Anuncios.this.textquery, false);
                localSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
                {
                    public boolean onQueryTextChange(String paramAnonymous2String)
                    {
                        return false;
                    }

                    public boolean onQueryTextSubmit(String paramAnonymous2String)
                    {
                        Anuncios.this.textquery = paramAnonymous2String;
                        Anuncios.this.EjecutarBusqueda();
                        Anuncios.this.executedsearch = true;

                        return false;
                    }
                });
                texto.setView(localView).setPositiveButton("BUSCAR", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        if (!Anuncios.this.executedsearch)
                        {
                            Anuncios.this.textquery = localSearchView.getQuery().toString();
                            Anuncios.this.EjecutarBusqueda();
                        }
                        paramAnonymous2DialogInterface.dismiss();
                        Anuncios.this.executedsearch = false;
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        paramAnonymous2DialogInterface.dismiss();
                    }
                }).setTitle("Buscar título o contenido");
                texto.create().show();
            }
        }).build() });
        localExpandableButtonView.addItem(new ButtonItem[] { new ButtonItem.Builder(this).setImageDrawable
                (this.soporte.PintarIconos(R.drawable.img_partes , Integer.valueOf(R.color.blanco))).setClickListener(new
                                                                                                                                   View.OnClickListener()
                                                                                                                                   {
                                                                                                                                       public void onClick(View filtro)
                                                                                                                                       {
                              localExpandableButtonView.removeBottomToolbar();
                            AlertDialog.Builder subcategorias = new AlertDialog.Builder(Anuncios.this.contexto);
                           subcategorias.setPositiveButton("OK", new DialogInterface.OnClickListener()
                                {
                               public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                               {
                               Anuncios.this.EjecutarBusqueda();
                                 paramAnonymous2DialogInterface.dismiss();
                                            }
                                });
                                 subcategorias.setMultiChoiceItems(Anuncios.this.listasubcategoriasitems, Anuncios.this.subcategoriassactivas,
                                         new DialogInterface.OnMultiChoiceClickListener()
                                         {
                                             public void onClick(DialogInterface paramAnonymous2DialogInterface, int posicion, boolean  paramAnonymous2Boolean)
                                             {
                        Anuncios.this.subcategoriassactivas[posicion] = paramAnonymous2Boolean;
                                                 String subcategoria = new StringBuilder(listasubcategoriasitems[posicion]).toString();
                        if (paramAnonymous2Boolean)
                        {
                            Anuncios.this.filtrossubcats.add(subcategoria);
                            return;
                        }
                       else {
                            Anuncios.this.filtrossubcats.remove(subcategoria);

                        }
                   }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        paramAnonymous2DialogInterface.dismiss();
                    }
                }).setTitle("Filtrar Subcategoría");
                subcategorias.create().show();
            }
        }).build() });
        localExpandableButtonView.addItem(new ButtonItem[] { new ButtonItem.Builder(this).setImageDrawable
(this.soporte.PintarIconos(Integer.valueOf(R.drawable.img_mapmark), Integer.valueOf(R.color.blanco))).setClickListener(new
View.OnClickListener()
        {
            public void onClick(View filtro)
            {
                localExpandableButtonView.removeBottomToolbar();
                AlertDialog.Builder provincia = new AlertDialog.Builder(Anuncios.this.contexto);
                provincia.setPositiveButton("BUSCAR", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        Anuncios.this.EjecutarBusqueda();
                        paramAnonymous2DialogInterface.dismiss();
                    }
                });
                provincia.setMultiChoiceItems(Anuncios.this.getResources().getStringArray(R.array.arProvincias),
Anuncios.this.provinciasactivas, new DialogInterface.OnMultiChoiceClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int, boolean
paramAnonymous2Boolean)
                    {
                        Anuncios.this.provinciasactivas[paramAnonymous2Int] = paramAnonymous2Boolean;
                        if (paramAnonymous2Boolean)

                            Anuncios.this.filtrosprovincias.add(Integer.valueOf(paramAnonymous2Int));
                        else
                            Anuncios.this.filtrosprovincias.remove((Object)paramAnonymous2Int);

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                        paramAnonymous2DialogInterface.dismiss();
                    }
                }).setTitle("Filtrar Provincias");
                provincia.create().show();
            }
        }).build() });

        ButtonItem precio = new ButtonItem(this);
        precio.setImageDrawable(soporte.PintarIconos(R.drawable.img_dinero,R.color.blanco));
        precio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localExpandableButtonView.removeBottomToolbar();
                AlertDialog.Builder precio = new AlertDialog.Builder(Anuncios.this.contexto);
                View vistafilt  = LayoutInflater.from(contexto).inflate(R.layout.filtro_precio,null);
                precio.setView(vistafilt);
                RangeBar filtroprecio = (RangeBar) vistafilt.findViewById(R.id.rangebarprecios) ;
                ((TextView) vistafilt.findViewById(R.id.preciominimo)).setText("Mínimo: " + preciominimo);
                ((TextView) vistafilt.findViewById(R.id.preciomaximo)).setText("Máximo: " + preciomaximo);
                //Log.d("max","max" + preciomaximo + "min" + preciominimo) ;
                filtroprecio.setTickEnd(preciomaximo);
                filtroprecio.setTickStart(preciominimo);

                filtroprecio.setRangePinsByValue(preciominimoactivo,preciomaximoactivo);
                filtroprecio.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                        preciominimoactivo = Float.valueOf(leftPinValue);
                        preciomaximoactivo = Float.valueOf(rightPinValue);
                    }
                });

                precio.setPositiveButton("BUSCAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EjecutarBusqueda();
                        dialog.dismiss();
                    }
                });
                precio.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                precio.create().show();
            }
        });

        localExpandableButtonView.addItem(precio);

        localExpandableButtonView.addItem(new ButtonItem[] { new ButtonItem.Builder(this).setImageDrawable
(this.soporte.PintarIconos(Integer.valueOf(R.drawable.img_cerrar), Integer.valueOf(R.color.blanco))).setClickListener(new
View.OnClickListener()
        {
            public void onClick(View filtro)
            {
                localExpandableButtonView.removeBottomToolbar();

                String categoria =	mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()).toString();
                Object fragmento = mViewPager.getAdapter().instantiateItem(mViewPager,mViewPager.getCurrentItem());
                Clase_AdaptadorPublicaciones publics =  (Clase_AdaptadorPublicaciones)((RecyclerView) ((Fragment)fragmento).getView().findViewById(R.id.lvPublicaciones)).getAdapter();
                //basededatos.BuscarPublicaciones(texto.getQuery().toString(),provincia.getSelectedItemPosition(),subcategoria.getSelectedItem().toString(),categoria );
                //publics.EjecutarBuscar(((TextView)v).getText().toString());

                publics.LimpiarFiltros(categoria);

                filtrosprovincias = new  ArrayList<Integer>();
               filtrossubcats = new  ArrayList<String>();
                //((Anuncios)getContext()).considerarprecio = false;
                textquery = "";
                CalcularpreciosCategoria(categoria);

            }
        }).build() });
    }

    public void EjecutarBusqueda()
    {
        String str = this.mViewPager.getAdapter().getPageTitle(this.mViewPager.getCurrentItem()).toString();
        Clase_AdaptadorPublicaciones localClase_AdaptadorPublicaciones = (Clase_AdaptadorPublicaciones)((RecyclerView)
                ((Fragment)this.mViewPager.getAdapter().instantiateItem(this.mViewPager, this.mViewPager              .getCurrentItem
                        ())).getView().findViewById(R.id.lvPublicaciones)).getAdapter();
        localClase_AdaptadorPublicaciones.EjecutarBuscar(this.textquery, this.filtrosprovincias, this.filtrossubcats, str,
                this.preciomaximoactivo, this.preciominimoactivo);
        Toast.makeText(this.contexto, "Se encontraron " + localClase_AdaptadorPublicaciones.getItemCount() + " resultados",
                Toast.LENGTH_LONG).show();
    }


    public void CalcularpreciosCategoria(String paramString)
    {
        float[]  resultado = this.basededatos.MinMaxporCategoria(paramString);
        if(resultado!=null) {
            float diferencia = resultado[1] - resultado[0];
            if ((paramString != null) && (resultado[0] > 5) && (resultado[1] > 10) && diferencia > 5) {
                this.preciominimo = resultado[0];
                preciominimoactivo = resultado[0];
                if (diferencia < 10000)
                {
                    this.preciomaximo = resultado[1];
                    preciomaximoactivo = resultado[1];
                }
                else {
                    this.preciomaximo = resultado[0]+10000;
                    preciomaximoactivo = resultado[0]+10000;
                }

            }
        }
    }

}


