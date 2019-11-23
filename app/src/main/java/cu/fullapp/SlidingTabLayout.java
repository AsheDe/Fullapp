package cu.fullapp;

/**
 * Created by renier on 15/08/2016.
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(ViewPager)} providing it the ViewPager this layout is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * via {@link #setSelectedIndicatorColors(int...)}. The
 * alternative is via the {@link TabColorizer} interface which provides you complete control over
 * which color is used for any individual position.
 * <p>
 * The views used as tabs can be customized by calling {@link #setCustomTabView(int, int)},
 * providing the layout ID of your custom layout.
 */
public class SlidingTabLayout extends HorizontalScrollView {
    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     * {@link #setCustomTabColorizer(TabColorizer)}.
     */

    Toolbar toolbar;

    public interface TabColorizer {

        /**
         * @return return the color of the indicator used when {@code position} is selected.
         */
        int getIndicatorColor(int position);


    }

    private Integer [] list_imagenes = new Integer[]
            {
                    R.drawable.img_computer,
                    R.drawable.img_celular,
                    R.drawable.img_transporte,
                    R.drawable.img_casa,
                    R.drawable.img_shop_car

            };
    private Integer [] list_COLORES = new Integer[]
            {
                    R.color.colorOverPC,
                    R.color.colorOverCel,
                    R.color.colorOverTrans,
                    R.color.colorOverHogar,
                    R.color.colorOverMisc

            };

    private String[] transiciones = new String[]{
            getContext().getString(R.string.transicion_portada_pc),
            getContext().getString(R.string.transicion_portada_cel),
            getContext().getString(R.string.transicion_portada_trans),
            getContext().getString(R.string.transicion_portada_home),
            getContext().getString(R.string.transicion_portada_misc),
    };

    Drawable img_circulo;


    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

    private int mTitleOffset;
    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private boolean mDistributeEvenly;

    private ViewPager mViewPager;
    private SparseArray<String> mContentDescriptions = new SparseArray<String>();
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final SlidingTabStrip mTabStrip;


    public SlidingTabLayout(Context context) {
        this(context, null);
        setHorizontalScrollBarEnabled(true);
        // this.setBackgroundResource(R.drawable.airplay);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setHorizontalScrollBarEnabled(true);
        // this.setBackgroundResource(R.drawable.airplay);
    }
    int width,height;
    TextView subtitulocat;
    Manejo_DB basededatos;
    TextView cantpublicacionesnuevas;

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(true);
        // Make sure that the Tab Strips fills this View
        setFillViewport(false);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mTabStrip = new SlidingTabStrip(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();


        width = display.getWidth()/9;
        height = (int)( display.getHeight()*0.07);
        this.getWidth();
        //  this.setLayoutParams(new ViewGroup.LayoutParams(this.getWidth()/2+20,height));
        Soporte_Elementos_Comunes soport = new Soporte_Elementos_Comunes(getContext());
        Drawable backgr = soport.PintarIconos(R.drawable.airplay, android.R.color.transparent);


        this.setBackgroundDrawable(backgr);

        addView(mTabStrip, width,height);


        img_circulo = soport.PintarIconos(R.drawable.circle,R.color.blanco);

        basededatos = new Manejo_DB(getContext());

    }

    /**
     * Set the custom {@link TabColorizer} to be used.
     *
     * If you only require simple custmisation then you can use
     * {@link #setSelectedIndicatorColors(int...)} to achieve
     * similar effects.
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setDistributeEvenly(boolean distributeEvenly) {
        mDistributeEvenly = distributeEvenly;
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array. Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Set the {@link ViewPager.OnPageChangeListener}. When using {@link SlidingTabLayout} you are
     * required to set any {@link ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId id of the {@link TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                outValue, true);
        textView.setBackgroundResource(outValue.resourceId);
        // textView.setAllCaps(true);

        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);

        return textView;
    }

    public void setTextiew(TextView tv)
    {
        subtitulocat = tv;
    }

    ////mio yudel

    protected ImageView createDefaultTabView(Context context,int pos) {


        ImageView icono = new ImageView(context);
        icono.setImageResource(Imagenes(pos));
        icono.setLayoutParams(new LayoutParams(width,height));
       if(Build.VERSION.SDK_INT>=21)
        icono.setTransitionName(transiciones[pos]);
        //icono.setScaleType(ImageView.ScaleType.FIT_XY);


       /* icono.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        /*TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                outValue, true);
        icono.setBackgroundResource(outValue.resourceId);*/

       //int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
       //icono.setPadding(padding, padding, padding, padding);*/

        //
        int padding = (int)(width*0.15);
        icono.setPadding(padding,padding,padding,padding);

        return icono;
    }



    private Integer Imagenes(int pos)
    {
        return list_imagenes[pos];
    }


    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            //TextView tabTitleView = null;


            if (mTabViewLayoutId != 0) {
                // If there is a custom tab view layout id set, try and inflate it
                tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip,false);
                //tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
            }

            if (tabView == null) {
                tabView = createDefaultTabView(getContext(),i);
            }

          /*  if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }*/

            /*if (mDistributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }*/

//            tabTitleView.setText(adapter.getPageTitle(i));
            tabView.setOnClickListener(tabClickListener);
            String desc = mContentDescriptions.get(i, null);
            if (desc != null) {
                tabView.setContentDescription(desc);
            }

            mTabStrip.addView(tabView);
            if (i == mViewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }

            // ((ImageView)tabView).setBackgroundColor(getResources().getColorStateList(R.color.selector));
            // tabView = (ImageView)tabView;
            // ((ImageView) tabView).setImageResource( getResources().getColorStateList(R.color.selector));
        }
    }


    public void setTabsBackgroundColor() {
        int pos = mViewPager.getCurrentItem();
        Soporte_Elementos_Comunes soporte = new Soporte_Elementos_Comunes(getContext());
        for(int i = 0; i < mTabStrip.getChildCount() ; i++ )
        {
            if (i!=pos)
            {
                Drawable imgs =   soporte.PintarIconos(Imagenes(i),R.color.colorPrincipalSwipe);
                ((ImageView) mTabStrip.getChildAt(i)).setImageDrawable(imgs);
                ((ImageView) mTabStrip.getChildAt(i)).setBackgroundDrawable(null);
            }
            else
            {
                Drawable imgs =   soporte.PintarIconos(Imagenes(i),list_COLORES[i]);
                ((ImageView) mTabStrip.getChildAt(i)).setImageDrawable(imgs);
                ((ImageView) mTabStrip.getChildAt(i)).setBackgroundDrawable(img_circulo);
            }
        }


    }

    /*  private void populateTabStrip() {
          final PagerAdapter adapter = mViewPager.getAdapter();
          final View.OnClickListener tabClickListener = new TabClickListener();

          for (int i = 0; i < adapter.getCount(); i++) {
              View tabView = null;

              tabView = LayoutInflater.from(getContext()).inflate(R.layout.vista_tab_title, mTabStrip,false);

              ImageView iconImageView = (ImageView) tabView.findViewById(R.id.tab_layout_icon);
              iconImageView.setImageDrawable(getContext().getResources().getDrawable(Imagenes(i)));
              //int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
              //iconImageView.setPadding(padding, padding, padding, padding);
              tabView.setOnClickListener(tabClickListener);

              TypedValue outValue = new TypedValue();
              getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,outValue, true);
              iconImageView.setBackgroundResource(outValue.resourceId);

              scrollToTab(mViewPager.getCurrentItem(), 0);

              mTabStrip.addView(tabView);
              if (i == mViewPager.getCurrentItem()) {
                  tabView.setSelected(true);

              }
          }
      }
  */
    public void setContentDescription(int i, String desc) {
        mContentDescriptions.put(i, desc);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }


    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= mTitleOffset;
            }

            scrollTo(targetScrollX, 0);
        }
        setTabsBackgroundColor();
        String page = mViewPager.getAdapter().getPageTitle(tabIndex).toString();
        toolbar.setTitle(page);
        //subtitulocat.setText(mViewPager.getAdapter().getPageTitle(tabIndex));

        int nuevas = basededatos.EntradasNuevasPorCategoria(mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()).toString());
        int total = basededatos.EntradasPorCategoria(mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()).toString());
        cantpublicacionesnuevas = (TextView) ((Activity)getContext()).findViewById(R.id.cantpublicacionesnuevas);
        cantpublicacionesnuevas.setText(nuevas+"/"+total);

       // if(((Anuncios)getContext()).abierto)
        //{

            //linersearch.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT));
            //linersearch.measure(CoordinatorLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
          //  ((Anuncios)getContext()).considerarprecio = false;

           // mViewPager.setTranslationY(((Anuncios)getContext()).viewpagerposition );
            //((Anuncios)getContext()).ampliarformsearch.setTranslationY(((Anuncios)getContext()).ampliarformsearch.getTranslationY()-longitud - (float)( height*0.001));
            //((Anuncios)getContext()).resultadobusqueda.setTranslationY(((Anuncios)getContext()).resultadobusqueda.getTranslationY()-longitud - (float)( height*0.001) );

            //LinearLayout.LayoutParams linesearchparmsb = (LinearLayout.LayoutParams) ((Anuncios)getContext()).linersearch.getLayoutParams();
            //linesearchparmsb.height = 0;
          //  ((Anuncios)getContext()).linersearch.setLayoutParams(linesearchparmsb);

           // ((Anuncios)getContext()).resultadobusqueda.setText("");



      //  }
        ((Anuncios)getContext()).filtrosprovincias = new  ArrayList<Integer>();
        ((Anuncios)getContext()).filtrossubcats = new  ArrayList<String>();
        //((Anuncios)getContext()).considerarprecio = false;
        ((Anuncios)getContext()).textquery = "";
       // ((Anuncios)getContext()).preciomaximo =10;
       // ((Anuncios)getContext()).preciominimo=5;
        ((Anuncios)getContext()).CalcularpreciosCategoria(page);
       int cantidadsubcats=12;
        switch (tabIndex){
            case 0:
            {
                ((Anuncios)getContext()).listasubcategoriasitems =  getResources().getStringArray(R.array.arCatPC);
                cantidadsubcats = 12;
                break;
            }
            case 1:
            {
                ((Anuncios)getContext()).listasubcategoriasitems =  getResources().getStringArray(R.array.arCatCEL);
                cantidadsubcats = 9;
                break;
            }
            case 2:
            {
                ((Anuncios)getContext()).listasubcategoriasitems =  getResources().getStringArray(R.array.arCatTRANS);
                cantidadsubcats = 7;
                break;
            }
            case 3:
            {
                ((Anuncios)getContext()).listasubcategoriasitems =  getResources().getStringArray(R.array.arCatINMOB);
                cantidadsubcats = 8;
                break;
            }
            case 4:
            {
                ((Anuncios)getContext()).listasubcategoriasitems =  getResources().getStringArray(R.array.arCatMISCELANEAS);
                cantidadsubcats = 8;
                break;
            }
        }
        ((Anuncios)getContext()).subcategoriassactivas = new boolean[cantidadsubcats];
        ((Anuncios)getContext()).provinciasactivas = new boolean[17];
        ExpandableButtonView expandableButtonView = (ExpandableButtonView)  ((Anuncios)getContext()).findViewById(R.id.expandirfiltros);
        if(expandableButtonView.getState()== ExpandableButtonView.State.FINISHED)
        {
            expandableButtonView.removeBottomToolbar();
            expandableButtonView.setState(ExpandableButtonView.State.IDLE);
        }

        long fechalastupdate = PreferenceManager.getDefaultSharedPreferences(getContext()).getLong("fechaultimaactualizacion"+page,1);
        Date fechaconvert = new Date(fechalastupdate);
        String finalmente = "Hasta: " + fechaconvert.getDate() + "/" + (fechaconvert.getMonth() + 1) + "/"  + (1900 + fechaconvert.getYear()) ;
        ((Anuncios)getContext()).ultimaupdate.setText(finalmente);


    }



    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = mTabStrip.getChildAt(position);

            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                mTabStrip.getChildAt(i).setSelected(position == i);
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }



    }

    private class TabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

}
