package cu.fullapp;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Publicacion_Completa extends Fragment implements View.OnClickListener {


	Publicacion publicacion;
	//AppCompatActivity contexto;
	Context context;
	RatingBar rbCalificacion;
	TextView tvAutor;
	TextView tvTitulo ;
	TextView tvPrecio ;
	TextView tvContenido ;
	TextView tvmoneda;
	TextView provinciapub;
	TextView tvfecha;
	RecyclerView imagenes;
	ImageView img_prov_publicacion;
	ImageView estoybuscando;
	Soporte_Elementos_Comunes soporte;
	Manejo_DB base_datos;
	int posicion;
	int cantidad;


	Usuario autor;
	//CollapsingToolbarLayout colapsing;

	//ImageView anterior,siguiente;

	boolean isbusqueda;
	View rootView;
	Context contexto;
	Bundle argumentos;
	AppCompatActivity actividad;
	TextView numcomentarios;
	int posicionlist=0;
	////---- codigo si extiende de activity
	//@Override
//	public View onCreateView(Bundle savedInstanceState) {

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			//return super.onCreateView(inflater, container, savedInstanceState);

		// TODO Auto-generated method stub
		//super.onCreate(savedInstanceState);

		//setContentView(R.layout.publicacion_completa);
		rootView = inflater.inflate(R.layout.publicaion_completa_fragment, container, false);
		actividad = (AppCompatActivity) getActivity();
		contexto = getContext();
		soporte = new Soporte_Elementos_Comunes(getContext());

		base_datos = new Manejo_DB(contexto);

		//soporte.hideStatusBar();


		/// tolbar test
		//Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
	//	setSupportActionBar(toolbar);

	/*	DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();*/
		////////

		/*WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();*/
		argumentos = getArguments();
			publicacion = argumentos.getParcelable("publicacion");
			posicionlist = argumentos.getInt("posicion");
		//String anuncio =  argumentos.getString("identificador");
		//if(argumentos.containsKey("posicion"))
		//	posicion = argumentos.getInt("posicion");

		//publicacion = base_datos.Obtener_PublicacionPorIdentificador(anuncio);


		//cantidad = base_datos.Obtener_PublicacionesPorCategoria (publicacion.getCategoria()).size();
		///--- COLLAPSING


	//	CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//		int colh = collapsingToolbarLayout.getLayoutParams().height;  //(int) (height*0.4);
//		int area_height = height - colh;


		//FloatingActionButton fbnuevap = (FloatingActionButton) findViewById(R.id.fbnuevapub);

		//----------------------------------

		 tvTitulo = (TextView) rootView.findViewById(R.id.tvTituloPub);
		 tvPrecio = (TextView) rootView.findViewById(R.id.tvPrecio);
		 tvContenido = (TextView) rootView.findViewById(R.id.tvContenido);
		 tvmoneda = (TextView) rootView.findViewById(R.id.tvMoneda);
		 tvAutor = (TextView) rootView.findViewById(R.id.autorpub);
			tvfecha = (TextView) rootView.findViewById(R.id.tvfecha);
		provinciapub = (TextView) rootView.findViewById(R.id.text_prov_publicacion);
		 rbCalificacion = (RatingBar) rootView.findViewById(R.id.rbCalificacion);
			estoybuscando = (ImageView) rootView.findViewById(R.id.pubbusco);
		//ImageView categoria = (ImageView) findViewById(R.id.color_porcategoria_public);
		 imagenes = (RecyclerView) rootView.findViewById(R.id.cuadro_subcategoria);
		 img_prov_publicacion = (ImageView) rootView.findViewById(R.id.img_prov_publicacion);
		//colapsing = (CollapsingToolbarLayout)  findViewById(R.id.toolbar_layout);

	//	SwitchCompat tbuton = (SwitchCompat) findViewById(R.id.toggleButton);
	//	opinion = (EditText) findViewById(R.id.etOpinion);
//		send = (ImageView) findViewById(R.id.btEnviar);
		//anterior = (ImageView) findViewById(R.id.fbanterior);
		//anterior.setOnClickListener(this);
		//Drawable danterior = soporte.PintarIconos(R.drawable.img_anterior,R.color.blanco);
		//anterior.setImageDrawable(danterior);
		//siguiente= (ImageView) findViewById(R.id.fbsiguiente);
		//siguiente.setOnClickListener(this);

		/*if(posicion==0)
			anterior.setVisibility(View.INVISIBLE);
		if(posicion==cantidad-1)
			siguiente.setVisibility(View.INVISIBLE);

		Drawable dsiguiente = soporte.PintarIconos(R.drawable.img_siguiente,R.color.blanco);
		siguiente.setImageDrawable(dsiguiente);
*/
	/*	if(getIntent().hasExtra("buscando"))
			isbusqueda = getIntent().getExtras().getBoolean("buscando");
		if(isbusqueda)
		{
			anterior.setVisibility(View.INVISIBLE);
			siguiente.setVisibility(View.INVISIBLE);
		}*/
		tvAutor.setOnClickListener(this);


	//	TextView mostrar_comentarios = (TextView) findViewById(R.id.mostrar_comentarios);
	//	mostrar_comentarios.setOnClickListener(this);

		Obtener_Datos();

			RecyclerView rec_comentarios = (RecyclerView) rootView.findViewById(R.id.comentarios);
			ArrayList<Comentario> comentarios =  base_datos.Obtener_Cometarios_PorPublicacion(publicacion.getIdent());
			Clase_Adaptador_Comentarios_Publicacion adapter = new Clase_Adaptador_Comentarios_Publicacion(contexto,comentarios);

			RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false);
			rec_comentarios.setLayoutManager(mLayoutManager);
			rec_comentarios.setItemAnimator(new DefaultItemAnimator());
			Drawable divider = getResources().getDrawable(R.drawable.separadordecomentarioizq);
			rec_comentarios.addItemDecoration(new DividerItemDecoration(divider));
			rec_comentarios.setAdapter(adapter);
			numcomentarios = (TextView) rootView.findViewById(R.id.numcomentspublicacion);
			if(comentarios.size()==0)
				numcomentarios.setText(" Sin comentarios aún") ;
			else
				numcomentarios.setText(comentarios.size() + " COMENTARIOS");


			return rootView;
	}

	public void Obtener_Datos()
	{

		/// esto tambien va ....
		autor = base_datos.Usuario_por_Correo(publicacion.getCorreo_usuario());
		tvAutor.setText(autor.getNombre());
		tvTitulo.setText(publicacion.getTitulo());

		//colapsing.setTitle(publicacion.getTitulo());
		//colapsing.setExpandedTitleColor(getResources().getColor(R.color.blanco));
		//colapsing.setCollapsedTitleTextAppearance(R.style.colapsed_publicacion);
		if(publicacion.getBusco()>0)
		{
			estoybuscando.setVisibility(View.VISIBLE);
		}
		tvPrecio.setText(publicacion.getPrecio()+ "  " + publicacion.getMoneda() + "");
		tvContenido.setText(publicacion.getContenido());
		rbCalificacion.setRating(publicacion.getValoracion());
		tvfecha.setText(publicacion.getFechaConfigurada());
		//categoria.setImageResource(soporte.ColorPorCategoriaTransparencia(publicacion.getCategoria()));
		//Drawable imgsubc = soporte.ImgPorSubCategoria(publicacion.getSubcategoria(),publicacion.getCategoria());
		//Drawable agragado = getResources().getDrawable(R.drawable.cuba_map);

		//subcategoria.setImageDrawable(imgsubc);
		//Drawable imgprov = soporte.ImgProvincia(publicacion.getProvincia());
		img_prov_publicacion.setImageDrawable( soporte.ImagenesRedondeadas(soporte.ImagenesPorprovincia[publicacion.getProvincia()]));
		provinciapub.setText( getResources().getStringArray(R.array.arProvincias)[publicacion.getProvincia()] );
		provinciapub.setOnClickListener(this);
		//LayerDrawable shape = (LayerDrawable) ContextCompat.getDrawable(this,R.drawable.publicacion_capas);
		//shape.setDrawableByLayerId(shape.getId(0),imgprov);

		//((CollapsingToolbarLayout)findViewById(R.id.toolbar_layout)).setBackgroundDrawable(shape);

		//colapsing.setBackgroundDrawable(shape);
		//ViewGroup coments = (ViewGroup) findViewById(R.id.comentarios);
		//coments.removeAllViews();
//		tvAutor.setOnClickListener(this);/*/

//////// conf view recycler imagenes
		//boolean tieneimagenes = false;
		ImagenesPublicacion adptr;
		if(publicacion.getIdent().endsWith("ghost"))
		{
			/*ArrayList<String> imgs = new ArrayList<String>();
			imgs.add("/storage/emulated/0/Pictures/AlamesaShare.jpg");
			imgs.add("/storage/emulated/0/Pictures/up2.jpg");
			imgs.add("/storage/emulated/0/Pictures/marsella.jpg");
			adptr = new ImagenesPublicacion( imgs);*/
			Drawable[] imgsprom = ImagenesPromo();
			adptr = new ImagenesPublicacion(imgsprom);


		}
		else {
			adptr = new ImagenesPublicacion( soporte.ImgPorSubCategoria(publicacion.getSubcategoria() , publicacion.getCategoria()));
		}

		LinearLayoutManager mangr = new LinearLayoutManager(contexto,LinearLayoutManager.HORIZONTAL,false);
		imagenes.setLayoutManager(mangr);
		imagenes.setItemAnimator(new DefaultItemAnimator());
		imagenes.addItemDecoration(new DividerItemDecoration(contexto, null));
		adptr.poslista = posicionlist;
		imagenes.setAdapter(adptr);


	}

	private Drawable[] ImagenesPromo()
	{
		Drawable[] imagenes = null;
		if(publicacion.getIdent().startsWith("pc"))
		{
			imagenes = new Drawable[]{
					contexto.getResources().getDrawable(R.mipmap.pcpromocion),
					contexto.getResources().getDrawable(R.mipmap.laptoppromocion)
			};
		}
		if(publicacion.getIdent().startsWith("tel"))
		{
			imagenes = new Drawable[]{
					contexto.getResources().getDrawable(R.mipmap.samspromocion),
					contexto.getResources().getDrawable(R.mipmap.celpromocion)
			};
		}
		if(publicacion.getIdent().startsWith("tra"))
		{
			imagenes = new Drawable[]{
					contexto.getResources().getDrawable(R.mipmap.driverpromocion),
					contexto.getResources().getDrawable(R.mipmap.destinospromocion)
			};
		}
		if(publicacion.getIdent().startsWith("cas"))
		{
			imagenes = new Drawable[]{
					contexto.getResources().getDrawable(R.mipmap.viviendaprom),
			};
		}
		if(publicacion.getIdent().startsWith("mis"))
		{
			imagenes = new Drawable[]{
					contexto.getResources().getDrawable(R.mipmap.pandpromocion),
					contexto.getResources().getDrawable(R.mipmap.papyrusprom),
					contexto.getResources().getDrawable(R.mipmap.abel3),
					contexto.getResources().getDrawable(R.mipmap.abel4)
			};
		}
		return imagenes;
	}



	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.autorpub || v.getId() == R.id.img_prov_publicacion  || v.getId() == R.id.text_prov_publicacion) {

			Intent user = new Intent("cu.fullapp.USUARIO_UNICO");
			ArrayList<Usuario> userunic = new ArrayList<Usuario>();
			Usuario usuario = base_datos.Usuario_por_Correo(autor.getCorreo());
			userunic.add(usuario);
			user.putExtra("listausuarios", userunic);
			user.putExtra("posicion", 0);
			Pair<View, String> transicionprov = new Pair<>((View) img_prov_publicacion, contexto.getString(R.string.transicionprov));
			Pair<View, String> autorpub = new Pair<>((View) img_prov_publicacion, contexto.getString(R.string.transicionautor));
			Pair<View, String> transicionratings = new Pair<>((View) img_prov_publicacion, contexto.getString(R.string.transicion_ratings));
			Bundle transiciones = ActivityOptionsCompat.makeSceneTransitionAnimation(actividad, transicionprov, autorpub, transicionratings).toBundle();
			if (Build.VERSION.SDK_INT >= 16)
				actividad.startActivity(user, transiciones);
			else
				actividad.startActivity(user);
		}

		/*	case R.id.fbanterior:
			{
				if(posicion>0)
				{
					posicion--;
					publicacion = base_datos.Obtener_PublicacionesPorCategoria (publicacion.getCategoria()).get(posicion);
					Obtener_Datos();
					siguiente.setVisibility(View.VISIBLE);
					if(posicion==0)
						anterior.setVisibility(View.INVISIBLE);
				}
				else  
				//	Snackbar.make(v,"Inicio de la lista",Snackbar.LENGTH_LONG).show();
				//notifyAll();
				anterior.setVisibility(View.INVISIBLE);

				break;
			}
			case R.id.fbsiguiente:
			{
				if(posicion<cantidad-1)
				{
					posicion++;
					publicacion = base_datos.Obtener_PublicacionesPorCategoria (publicacion.getCategoria()).get(posicion);
					Obtener_Datos();
					anterior.setVisibility(View.VISIBLE);
					if(posicion==cantidad-1)
						siguiente.setVisibility(View.INVISIBLE);
				}
				else
					//Snackbar.make(v,"Fin de la lista",Snackbar.LENGTH_LONG).show();
				//notifyAll();
					siguiente.setVisibility(View.INVISIBLE);

				break;
			}*/



		/*	case R.id.mostrar_comentarios:
			{
				ArrayList<Comentario> comentarios =  base_datos.Obtener_Cometarios_PorPublicacion(publicacion.getIdent());
				if(comentarios==null)
				{
					Snackbar.make(v,"No Hay Comentarios para mostrar",Snackbar.LENGTH_LONG).show();
				}
				else
				{
					ViewGroup coments = (ViewGroup) findViewById(R.id.comentarios);
					coments.removeAllViews();
					for (int i = 0; i < comentarios.size(); i++) {
						View coment ;//= new View(this);
						if (i % 2 == 0)
							coment = getLayoutInflater().inflate(R.layout.publicacion_comentario_item, coments,false);
						else
							coment = getLayoutInflater().inflate(R.layout.publicacion_comentario_item_derecho, coments,false);
						//((ImageView) coment.findViewById(R.id.)).setImageDrawable(soporte.ImgPorSubCategoria(publicacion.getSubcategoria(),publicacion.getCategoria()));
						TextView comment = (TextView) coment.findViewById(R.id.tvpub_comment_comentario);
						comment.setText(comentarios.get(i).getComentario());

						TextView fecha = (TextView) coment.findViewById(R.id.tvfechacoment);
						fecha.setText(comentarios.get(i).getFechaConfigurada());

						String Autor = base_datos.Usuario_por_Correo(comentarios.get(i).getAutor()).getNombre();
						TextView autor = (TextView) coment.findViewById(R.id.tvautorcoment);
						autor.setText(Autor);

						RatingBar rbcom = (RatingBar) coment.findViewById(R.id.pubrating_comment);
						rbcom.setRating(comentarios.get(i).getRating());

						//coment.setY(indice);
						//indice = indice + coment.getHeight() + 20;
						//int sd = coment.getHeight();
						//indice=indice + coment.getMeasuredHeight() + 20;

					coments.addView(coment);
					}
				}
				break;
			}*/


	}


	public class ImagenesPublicacion extends RecyclerView.Adapter
	{
		ArrayList<String> mimagenes=null;
		Drawable mimagen = null;
		Drawable[] mdrawables = null;
		ImageView cabecera;
		int poslista;
		public ImagenesPublicacion(ArrayList<String> imagenes) {
			mimagenes = imagenes;
		}
		public ImagenesPublicacion(Drawable imagen) {
			mimagen = imagen;
		}
		public ImagenesPublicacion(Drawable[] imagenes) {
			mdrawables = imagenes;
		}
		/*@Override
		public Fragment getItem(int position) {
			Bundle imagenes = new Bundle();
			//imagenes.putParcelable("img",getResources().getDrawable(R.drawable.img_star_fill));
			//publicacion.putString("identificador",publicaciones.get(position).getIdent());
			Fragment  fragment = new ImagenPublicacion();
			//imagenes.putStringArrayList("imgs",mimagenes);
			//imagenes.putInt("pos",position);
			//fragment.setArguments(imagenes );
			return fragment;

		}*/

		/*@Override
		public int getCount() {
			return mimagenes.size();
		}*/

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v;
			v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_imagen_pub, parent, false);
			ImagenPublicacion vh = new ImagenPublicacion(v);
			return vh;

		}


		@Override
		public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

			if (mimagen!=null)
			{
				((ImagenPublicacion)holder).img.setImageDrawable(mimagen);
			}
			else if(mdrawables!=null){
				((ImagenPublicacion)holder).img.setImageDrawable(mdrawables[position]);
				((ImagenPublicacion)holder).item.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder dialogo = new AlertDialog.Builder(contexto);
						RecyclerView imgs = new RecyclerView(contexto);
						LinearLayoutManager mangr = new LinearLayoutManager(contexto,LinearLayoutManager.HORIZONTAL,false);
						imgs.setLayoutManager(mangr);
						imgs.setAdapter(imagenes.getAdapter());
						imgs.scrollToPosition(position);

						dialogo.setView(imgs);

						AlertDialog most = dialogo.create();
						most.show();
					}
				});
			}
			else if(mimagenes!=null)
			{
				Bitmap imagen = BitmapFactory.decodeFile(mimagenes.get(position));
			((ImagenPublicacion)holder).img.setImageBitmap(imagen);
				((ImagenPublicacion)holder).item.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder dialogo = new AlertDialog.Builder(contexto);
						RecyclerView imgs = new RecyclerView(contexto);
						LinearLayoutManager mangr = new LinearLayoutManager(contexto,LinearLayoutManager.HORIZONTAL,false);
						imgs.setLayoutManager(mangr);
						imgs.setAdapter(imagenes.getAdapter());
						imgs.scrollToPosition(position);

						dialogo.setView(imgs);

						AlertDialog most = dialogo.create();
						most.show();
					}
				});

			}



		}

		@Override
		public int getItemCount() {
			if(mimagenes!=null)
				return mimagenes.size();
			else if(mdrawables!=null)
				return mdrawables.length;
			return 1;
		}
	}

	public class ImagenPublicacion extends RecyclerView.ViewHolder
	{

		ImageView img;
		View item;
		public ImagenPublicacion(View itemView) {
			super(itemView);
			img = (ImageView) 	itemView.findViewById(R.id.imagenpublicacion);
			item = itemView;
		}

		/*@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			//return super.onCreateView(inflater, container, savedInstanceState);
			View imagen =	inflater.inflate(R.layout.vista_imagen_pub,container);
		 	ImageView img = (ImageView) 	imagen.findViewById(R.id.imagenpublicacion);
			//ArrayList<String> imagens =  getArguments().getStringArrayList("imgs");
			//imagens.add("/storage/emulated/0/Music/100_7370.JPG");
			//imagens.add("/storage/emulated/0/Pictures/AlamesaShare.jpg");

			//Bitmap foto = BitmapFactory.decodeFile(imagens.get(getArguments().getInt("pos")));
			//img.setImageBitmap( foto);
			img.setImageDrawable(getResources().getDrawable(R.drawable.cuba_map));
			return imagen;
		}*/
	}



}






//collapsingToolbarLayout.setTitleEnabled(false);
//final String mExpandedTitle = "Titulo de la publicacion";
//final String mCollapsedTitle = "Tit en Venta";

		/*AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_barlayout_pub);

		appBarLayout.addOnOffsetChangedListener(new   AppBarLayout.OnOffsetChangedListener()
		{
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
			{
				appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrincipalApp));
				Snackbar.make(soporte.toolbar,"collapsed 1" + verticalOffset ,Snackbar.LENGTH_LONG).show();
				/*if(verticalOffset == 0 || verticalOffset <= soporte.toolbar.getHeight()+10 ){
					//collapsingToolbarLayout.setTitle(mCollapsedTitle);
					Snackbar.make(soporte.toolbar,"collapsed 1" + verticalOffset ,Snackbar.LENGTH_LONG).show();
				}
			/*	else if(!soporte.toolbar.getTitle().equals(mExpandedTitle)){
					collapsingToolbarLayout.setTitle(mExpandedTitle);
					Snackbar.make(collapsingToolbarLayout,"collapsed 2",Snackbar.LENGTH_LONG).show();
				}*/
				/*if (verticalOffset == -collapsingToolbarLayout.getHeight() + soporte.toolbar.getHeight()) {
					//toolbar is collapsed here
					//write your code here
					Snackbar.make(collapsingToolbarLayout,"collapsed",Snackbar.LENGTH_LONG).show();
				}/
			}


		});*/



//nested.setY((int)(height*0.9));
// nestedscroll = (NestedScrollView) findViewById(R.id.item_detail_container);
//nested.setLayoutParams(new CoordinatorLayout.LayoutParams(width,area_height));
//nested.setY(colh);
//nested.canScrollVertically()

//LinearLayout lin_contenido_pub = (LinearLayout) findViewById(R.id.lin_contenido_pub);
//lin_contenido_pub.setLayoutParams(new FrameLayout.LayoutParams(width,(int)(area_height*0.7)));
//lin_contenido_pub.setY((int)(area_height*0.1));
//lin_contenido_pub.scroll()
