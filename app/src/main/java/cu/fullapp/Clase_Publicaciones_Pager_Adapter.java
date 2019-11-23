package cu.fullapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by yudel on 26/06/2017.
 */
public class Clase_Publicaciones_Pager_Adapter extends FragmentStatePagerAdapter {
    ViewPager mViewPager;
    Context contexto;
    AppCompatActivity actividad;
    ArrayList<Publicacion> publicaciones;
    Publicacion mpublicacion;


    Soporte_Comunicaciones comunicaciones;
    Manejo_DB base_datos;

    public Clase_Publicaciones_Pager_Adapter(FragmentManager fm, Context contexto, ViewPager mViewPager, ArrayList<Publicacion> publicacions) {
        super(fm);
        this.contexto = contexto;
        this.mViewPager = mViewPager;
        publicaciones = publicacions;
        comunicaciones = new Soporte_Comunicaciones(contexto);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle publicacion = new Bundle();
        base_datos = new Manejo_DB(contexto);
        //publicacion.putString("identificador",publicaciones.get(position).getIdent());
        mpublicacion = publicaciones.get(position);
        publicacion.putParcelable("publicacion",mpublicacion);
        Fragment  fragment = new Publicacion_Completa();
        fragment.setArguments(publicacion);
        return fragment;


    }

    @Override
    public int getCount() {
        return publicaciones.size();
    }







  /*  @Override
    public int getItemPosition(Object object) {
        super.getItemPosition(object);
        return POSITION_NONE;
    }
*/

    @Override
    public CharSequence getPageTitle(int position) {
        return publicaciones.get(position).getSubcategoria();
    }


}