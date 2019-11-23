package cu.fullapp;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Vista_Todos_Usuarios extends AppCompatActivity
{
			//final int REQUEST_CODE_ASK_PERMISSIONS_CALL = 1987;
			static boolean islarge;
	Soporte_Elementos_Comunes soporte;

	View vista_fr;
	ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	RecyclerView lst_usuarios;
	RecyclerView.Adapter mAdapter;
	RecyclerView.LayoutManager mLayoutManager;
	Manejo_DB base_datos;
	Soporte_Comunicaciones comunicaciones;

	ArrayList<Integer> filtrosprovincias = new ArrayList<Integer>();
	boolean abierto = false;
	String textquery="";
	boolean isbusqueda=false;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_todos_usuarios);

		//FrameLayout fra = (FrameLayout) findViewById(R.id.frame_trans);
		//View vist = getLayoutInflater().inflate(R.layout.drawer_bar_noslide,fra);

		soporte = new Soporte_Elementos_Comunes(this);
		comunicaciones = new Soporte_Comunicaciones(this);

		soporte.setupToolbar(true,"Usuarios");
		soporte.setupDrawerLayout();

		base_datos = new Manejo_DB(this);
		//---------Mostrar Listado

		lst_usuarios = (RecyclerView) findViewById(R.id.lvUsuarios);
		//	mRecyclerView.setHasFixedSize(true);

			mLayoutManager = new LinearLayoutManager(this);
			lst_usuarios.setLayoutManager(mLayoutManager);
			lst_usuarios.setItemAnimator(new DefaultItemAnimator());

			usuarios = base_datos.Obtener_Usuarios();


			mAdapter = new AdaptadorUsuarios(usuarios);
			lst_usuarios.setAdapter(mAdapter);
		final Context contexto = this;
		final Activity actividad = this;
		final TextView resultadobusqueda = (TextView) findViewById(R.id.resultadosbuscar);
		final ImageView ampliarformsearch = (ImageView) findViewById(R.id.ampliarformsearch);

		ImageView rankear = (ImageView) findViewById(R.id.ivPorRanking);
		rankear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((AdaptadorUsuarios)mAdapter).usuarios_list = base_datos.OrdenarUsuarios();
				mAdapter.notifyDataSetChanged();
				Toast.makeText(contexto,"Usuarios organizados por valoración",Toast.LENGTH_LONG).show();
			}
		});

		ampliarformsearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final RelativeLayout linersearch = (RelativeLayout) findViewById(R.id.linersearch);

				//float position = ampliarformsearch.getTranslationY();
				WindowManager wm = (WindowManager) actividad.getSystemService(Context.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				final int widthscreen = display.getWidth();
				final int altopantalla = display.getHeight();
				float lastwidth = (float)( widthscreen*0.01);
				float height= 0;
				int padding = (int) (6 * getResources().getDisplayMetrics().density);
				int paddingtop = (int) (3 * getResources().getDisplayMetrics().density);

				if(!abierto) {
					String[] provincias = contexto.getResources().getStringArray(R.array.arProvincias);

					final SearchView buscador = new SearchView(contexto);
					buscador.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
					buscador.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					height = buscador.getMeasuredHeight();
					buscador.setIconified(false);
					buscador.setQuery(textquery,false);

					buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
						@Override
						public boolean onQueryTextSubmit(String query) {
							return false;
						}

						@Override
						public boolean onQueryTextChange(String newText) {



							//AdaptadorUsuarios usuarios =  (AdaptadorUsuarios)((RecyclerView) ((Fragment)fragmento).getView().findViewById(R.id.lvPublicaciones)).getAdapter();
							//basededatos.BuscarPublicaciones(texto.getQuery().toString(),provincia.getSelectedItemPosition(),subcategoria.getSelectedItem().toString(),categoria );
							//publics.EjecutarBuscar(((TextView)v).getText().toString());
							textquery = buscador.getQuery().toString();
							((AdaptadorUsuarios)mAdapter).BuscarUsuarios();
							resultadobusqueda.setText("Se encontraron " + ((AdaptadorUsuarios)mAdapter).getItemCount() + " resultados");

							return false;
						}
					});

					buscador.setQueryHint("Buscar");
					linersearch.addView(buscador);


					final TextView todo = new TextView(contexto);
					todo.setText("Todo");
					todo.setBackgroundResource(R.drawable.img_fondo_textos_buscar);
					todo.setPadding(padding,paddingtop,padding,paddingtop);
					todo.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
					todo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
					todo.measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
					todo.setTranslationX(lastwidth);
					todo.setTranslationY(height);
					lastwidth += todo.getMeasuredWidth()+(float)( widthscreen*0.004);

					todo.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {


							((AdaptadorUsuarios)mAdapter).LimpiarFiltros();
							ampliarformsearch.setRotationX(0);

							linersearch.removeAllViews();
							linersearch.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
							ampliarformsearch.setTranslationY(0);

							abierto =false;

							filtrosprovincias = new ArrayList<Integer>();
							textquery = "" ;
						}
					});
					linersearch.addView(todo);

					/// PROVINCIAS
					float alto = 0;
					for (int i = 0; i <provincias.length; i++) {


						final TextView provincia = new TextView(contexto);
						provincia.setText(provincias[i]);
						if (filtrosprovincias.contains(i)) {
							provincia.setBackgroundResource(R.drawable.img_fondo_texto_buscar_selected);
							provincia.setTextColor(contexto.getResources().getColor(R.color.blanco));
							provincia.setActivated(true);
						} else
						{
							provincia.setBackgroundResource(R.drawable.img_fondo_textos_buscar);
							provincia.setTextColor(contexto.getResources().getColor(R.color.colorFont));
							provincia.setActivated(false);
						}

						provincia.setPadding(padding,paddingtop,padding,paddingtop);
						provincia.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
						provincia.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
						provincia.measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
						float width = provincia.getMeasuredWidth()+(float)( widthscreen*0.006);
						alto = provincia.getMeasuredHeight()+(float)( altopantalla*0.008);
						provincia.setTag(provincia.getId(),i);
						//final int posicion = i;
						provincia.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if(v.isActivated()==false)
								{
									v.setBackgroundResource(R.drawable.img_fondo_texto_buscar_selected);
									((TextView)v).setTextColor(contexto.getResources().getColor(R.color.blanco));
									v.setActivated(true);
									filtrosprovincias.add(Integer.parseInt((provincia.getTag(provincia.getId()).toString())));
								}
								else
								{
									v.setActivated(false);
									v.setBackgroundResource(R.drawable.img_fondo_textos_buscar);
									((TextView)v).setTextColor(contexto.getResources().getColor(R.color.colorFont));
									filtrosprovincias.remove( (Object) provincia.getTag(provincia.getId()));
								}

								//AdaptadorPublicaciones publics =  (AdaptadorPublicaciones)((RecyclerView) ((Fragment)fragmento).getView().findViewById(R.id.lvPublicaciones)).getAdapter();
								//basededatos.BuscarPublicaciones(texto.getQuery().toString(),provincia.getSelectedItemPosition(),subcategoria.getSelectedItem().toString(),categoria );
								//publics.EjecutarBuscar(((TextView)v).getText().toString());

								//publics.EjecutarBuscar(buscador.getQuery().toString(),filtrosprovincias,filtrossubcats,categoria);
								((AdaptadorUsuarios)mAdapter).BuscarUsuarios();
								resultadobusqueda.setText("Se encontraron " + ((AdaptadorUsuarios)mAdapter).getItemCount() + " resultados");


							}
						});


						if(lastwidth+width<widthscreen)
						{
							provincia.setTranslationX(lastwidth);
							provincia.setTranslationY(height);
							lastwidth+=width;
						}
						else
						{
							lastwidth =(float)( widthscreen*0.01);
							height +=alto;
							provincia.setTranslationY(height);
							provincia.setTranslationX(lastwidth);
							lastwidth+=width;
						}
						linersearch.addView(provincia);

					}
					final int desplazamiento = (int) (height + alto  + altopantalla*0.008);
					int heightamp = ampliarformsearch.getHeight();

					linersearch.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, desplazamiento+heightamp));
					ampliarformsearch.setTranslationY( desplazamiento );
					resultadobusqueda.setTranslationY(desplazamiento);
					ampliarformsearch.setRotationX(180);
					abierto =true;
					/*float inicialposimg = ampliarformsearch.getY();
					float inicialposrec = lst_usuarios.getY();

					ObjectAnimator animacion1 = ObjectAnimator.ofFloat(ampliarformsearch, "Y", inicialposimg, desplazamiento + inicialposimg);
					animacion1.setDuration(4000);
					animacion1.setInterpolator(new AccelerateInterpolator());
					animacion1.start();



					ObjectAnimator animacion2 = ObjectAnimator.ofFloat(lst_usuarios, "Y", inicialposrec, inicialposrec + desplazamiento);
					animacion2.setDuration(4000);
					animacion2.setInterpolator(new AccelerateInterpolator());
					animacion2.start();*/


				}
				else
				{
					ampliarformsearch.setRotationX(0);
					linersearch.removeAllViews();
					abierto =false;
					linersearch.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
					ampliarformsearch.setTranslationY(0);
					resultadobusqueda.setTranslationY(0 );
					resultadobusqueda.setText("");

				}

			}
		});


			//registerForContextMenu(lst_usuarios);


		/*Fragment listado = new Ver_Lista_Users();
				getSupportFragmentManager().beginTransaction().replace(R.id.frame_noslide, listado).commit();
		islarge = (findViewById(R.id.frUsuario)!=null);*/



	}

			public void showPopUpMenu( View v)
			{
				/*PopupMenu popup = new PopupMenu(this , v);
				MenuInflater inflater = popup.getMenuInflater();
				inflater.inflate(R.menu.menu_item_usuario, popup.getMenu());
				popup.setOnMenuItemClickListener(this);
				popup.show();*/



			}




	///////////////--------ADAPTADOR Usuarios list
	public class AdaptadorUsuarios extends RecyclerView.Adapter {


		ArrayList<Usuario> usuarios_list;

		public class MyViewHolder extends RecyclerView.ViewHolder {
			// each data item is just a string in this case
			public ImageView imagen;
			public TextView nombre;

			View item;
			public MyViewHolder(View user) {
				super(user);
				imagen = (ImageView) user.findViewById(R.id.img_cta_usuario);
				nombre = (TextView) user.findViewById(R.id.tvUserNombre);
				item = user;
			}
		}

		public AdaptadorUsuarios(ArrayList<Usuario> users)
		{
			usuarios_list = users;
		}


		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			// create a new view
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_items_list_usuarios, parent, false);
			// set the view's size, margins, paddings and layout parameters

			MyViewHolder vh = new MyViewHolder(v);
			return vh;


		}



		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
		{
			int provincia = usuarios_list.get(position).getProvincia();
			 Drawable imag_prov = soporte.ImagenesRedondeadas(soporte.ImagenesPorprovincia[provincia] );
			((MyViewHolder)holder).imagen.setImageDrawable(imag_prov);
			((MyViewHolder)holder).nombre.setText(usuarios_list.get(position).getNombre());

			final int posicion = position;
			((MyViewHolder) holder).item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					/*if (islarge) {

						/*Bundle publ = new Bundle();
						publ.putString("correo", usuarios.get(posicion).getCorreo());
						Fragment frag = new Vista_Usuario_Fragment();
						frag.setArguments(publ);
						//getChildFragmentManager().beginTransaction().replace(R.id.frUsuario, frag).commit();


					} else {*/
						Intent user_unic = new Intent("cu.fullapp.USUARIO_UNICO");
						user_unic.putExtra("listausuarios", ((AdaptadorUsuarios) mAdapter).usuarios_list);
						user_unic.putExtra("posicion",posicion);

						startActivity(user_unic);

				//	}
				}
			});

			///////////---------- me quede aqui

			ImageView opc = (ImageView) ((MyViewHolder) holder).item.findViewById(R.id.menu_item_user);
			opc.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					View opciones = getLayoutInflater().inflate(R.layout.vista_opciones_usuario,(ViewGroup)v.getParent());
					ImageView cerrar = (ImageView)  opciones.findViewById(R.id.comones_cerrar);
					cerrar.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							ViewGroup x =  (ViewGroup) v.getParent();
							ViewGroup x2 = (ViewGroup) x.getParent();
							x2.removeView((View) x);

						}
					});
					ImageView btllamar = (ImageView)  opciones.findViewById(R.id.comones_telefono);
					ImageView btsms = (ImageView)  opciones.findViewById(R.id.comones_sms);
					ImageView btcorreo = (ImageView)  opciones.findViewById(R.id.comones_correo);

					btllamar.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Usuario autor =  base_datos.Usuario_por_Correo( ((AdaptadorUsuarios) mAdapter).usuarios_list.get(posicion).getCorreo());
							comunicaciones.Llamar(autor.getTelefono());
						}
					});


					btsms.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Usuario autor =  base_datos.Usuario_por_Correo(  ((AdaptadorUsuarios) mAdapter).usuarios_list.get(posicion).getCorreo());
							comunicaciones.EnviarSMS(autor.getTelefono());
						}
					});

					btcorreo.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Usuario autor =  base_datos.Usuario_por_Correo(  ((AdaptadorUsuarios) mAdapter).usuarios_list.get(posicion).getCorreo());
							comunicaciones.Lanzar_Correo(autor.getCorreo(),null);
						}
					});
				}
			});
			//final
			//opciones.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT));
			//opciones.setY(vpublicacion.getY()-20);



		}


		public void BuscarUsuarios()
		{

			/*for( int i =0; i < getItemCount() ; i++ )
			{
				View item = ((ViewGroup) lst_usuarios).getChildAt(i);
				((ViewGroup) lst_usuarios).removeView(item);
			}
			notifyItemRangeRemoved(0,usuarios.size());*/
			isbusqueda =true;
			usuarios_list = base_datos.BuscarUsuarios(textquery,filtrosprovincias);
//Log.d("cantu",)
			notifyDataSetChanged();

		}
		public void LimpiarFiltros()
		{
			isbusqueda =false;
			/*for( int i =0; i < getItemCount() ; i++ )
			{
				View item = ((ViewGroup) lst_usuarios).getChildAt(i);
				((ViewGroup) lst_usuarios).removeView(item);
			}*/
			//notifyItemRangeRemoved(0,usuarios.size());
			usuarios_list = base_datos.Obtener_Usuarios() ;
			//notifyItemRangeInserted(0, usuarios.size());
			notifyDataSetChanged();
		}

		@Override
		public int getItemCount() {
			return usuarios_list.size();

		}
	}



}








	

	


