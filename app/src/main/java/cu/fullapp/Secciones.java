package cu.fullapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cu.fullapp.scroller.ScrollListener;

/**
 * Created by renier on 09/09/2016.
 */

public class Secciones extends Fragment {
    ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
    Manejo_DB manejadorbd;
    RecyclerView listpublicaciones;
    Bundle argm;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    static boolean islarge;
    Soporte_Comunicaciones comunicaciones;
    String categoria;
    View rootView;
    SwipeRefreshLayout pulltorefresh;
    public Secciones() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        rootView = inflater.inflate(R.layout.paginador_fragment, container, false);
        comunicaciones = new Soporte_Comunicaciones(getContext());
        listpublicaciones = (RecyclerView) rootView.findViewById(R.id.lvPublicaciones);

        pulltorefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        pulltorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask_CantActualizaciones((AppCompatActivity)getContext()).execute(getContext(),categoria,pulltorefresh);

            }
        });
        pulltorefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        argm = getArguments();
        categoria = argm.getString("categoria");

        manejadorbd = new Manejo_DB(getContext());
      //  islarge = (rootView.findViewById(R.id.frUsuario) != null);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listpublicaciones.setLayoutManager(mLayoutManager);
        listpublicaciones.setItemAnimator(new DefaultItemAnimator());
        listpublicaciones.addItemDecoration(new DividerItemDecoration(getActivity(),null));
        this.publicaciones = manejadorbd.Obtener_PublicacionesPorCategoria(categoria);


        mAdapter = new Clase_AdaptadorPublicaciones(publicaciones,getContext(),this,false,listpublicaciones,categoria);
        listpublicaciones.setAdapter(mAdapter);

        //registerForContextMenu(listpublicaciones);

        //listpublicaciones.addOnScrollListener(new ScrollListener(expandableButtonView));
        listpublicaciones.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ExpandableButtonView expandableButtonView = (ExpandableButtonView) getActivity().findViewById(R.id.expandirfiltros);
               if(expandableButtonView.getState()== ExpandableButtonView.State.FINISHED)
               {
                   expandableButtonView.removeBottomToolbar();
                   expandableButtonView.setState(ExpandableButtonView.State.IDLE);
               }
            }
        });
        return rootView;

    }

    RecyclerView ObtenerListpublicaciones()
    {
        return listpublicaciones;
    }

   /* public void CambiarListadeDatos(ArrayList<Publicacion> datosnuevos)
    {
      //  ((AdaptadorPublicaciones)listpublicaciones.getAdapter()).list_publicaciones = datosnuevos;
        if(listpublicaciones==null)
            Log.d("no","pubs");
        /*mAdapter = new AdaptadorPublicaciones(datosnuevos,getContext(),this,false,listpublicaciones,categoria);
        listpublicaciones.setAdapter(mAdapter);
        listpublicaciones.getAdapter().notifyDataSetChanged();
        listpublicaciones.invalidate();
    }*/


}