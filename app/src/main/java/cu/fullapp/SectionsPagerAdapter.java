package cu.fullapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by renier on 09/09/2016.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter
{
    ViewPager mViewPager;
    Context contexto;
    String[] categorias;
   /* public SectionsPagerAdapter(ViewPager mVP) {

        //super(fm);
        mViewPager = mVP;
    }*/

    public SectionsPagerAdapter(FragmentManager fm, ViewPager mVP, Context cont) {

        super(fm);
        mViewPager = mVP;
        contexto = cont;
        categorias = contexto.getResources().getStringArray(R.array.arCategorias);
    }



    @Override
    public Fragment getItem(int position) {
        Bundle categoria = new Bundle();
            categoria.putString("categoria",categorias[position]);
            Fragment  fragment = new Secciones();
            fragment.setArguments(categoria);
            return fragment;
      //  }


    }


    @Override
    public int getCount() {

        return 5;
    }

    @Override
    public int getItemPosition(Object object) {
         super.getItemPosition(object);
        return POSITION_NONE;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return categorias[position];
    }


}