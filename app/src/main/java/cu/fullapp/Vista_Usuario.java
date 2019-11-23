package cu.fullapp;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Vista_Usuario extends AppCompatActivity
{

	
	Soporte_Elementos_Comunes soporte;
	Soporte_Comunicaciones comunicaciones;
	ViewPager pagerusuarios;
	Toolbar toolbar;

	//Manejo_DB base_datos;
	////---- codigo si extiende de activity
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_usuario);
		soporte = new Soporte_Elementos_Comunes(this);
		comunicaciones = new Soporte_Comunicaciones(this);



		toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		this.soporte.setupDrawerLayout(toolbar);

		pagerusuarios = (ViewPager) findViewById(R.id.pager_vista_usuario);

		final ArrayList<Usuario> listadousuarios = getIntent().getExtras().getParcelableArrayList("listausuarios");
		int posicion = getIntent().getExtras().getInt("posicion");
		pagerusuarios.setAdapter(new AdapterSingleUserPager(getSupportFragmentManager(),listadousuarios));
		pagerusuarios.setCurrentItem(posicion);


		
	}


	
	 class AdapterSingleUserPager extends FragmentStatePagerAdapter {
		 ArrayList<Usuario> listadousuarios;


		
		public AdapterSingleUserPager(FragmentManager fm,ArrayList<Usuario> lista) {
			super(fm);
			listadousuarios = lista;
		}

		@Override
		public int getCount() {
			return listadousuarios.size();
		}

		@Override
		public Fragment getItem(int position) {
			Fragment usuario = new AdapterFragmentUser();
			Bundle argumentos = new Bundle();
			Usuario useractual = listadousuarios.get(position) ;
			argumentos.putParcelable ("usuario",useractual);
			usuario.setArguments(argumentos);
			return usuario;
		}


	}
	
	static public class AdapterFragmentUser extends Fragment implements View.OnClickListener {

		Soporte_Elementos_Comunes soporte;
		Soporte_Comunicaciones comunicaciones;

		ImageView img_prov_usuario;
		ProgressBar progressBar;

		TextView tvnombre;

		ImageView correo;
		ImageView telefono;
		ImageView sms;

		TextView votos;
		TextView publicaciones;
		TextView ranking;
		RatingBar rbCalificacion;
		TextView valoracionsignal;
		TextView actlic;
		TextView nolic;
		 Usuario usuarioamostrar;
		View usuario;
		ObjectAnimator animation;
		RecyclerView rec_comentarios;

		public AdapterFragmentUser() {

		}
		
		
		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



			usuarioamostrar =  getArguments().getParcelable("usuario");
			Manejo_DB basedatos = new Manejo_DB(getContext());
			usuarioamostrar = basedatos.Usuario_por_Correo(usuarioamostrar.getCorreo());
			soporte = new Soporte_Elementos_Comunes(getContext());
			comunicaciones = new Soporte_Comunicaciones(getContext());

			usuario = inflater.inflate(R.layout.vista_usuario_fragmento, null);

			
			progressBar = (ProgressBar) usuario.findViewById(R.id.userprogressBar);
			img_prov_usuario = (ImageView) usuario.findViewById(R.id.user_img_provincia);
			tvnombre = (TextView) usuario.findViewById(R.id.usertvnombre);

			correo = (ImageView) usuario.findViewById(R.id.comones_correo);
			telefono = (ImageView) usuario.findViewById(R.id.comones_telefono);
			sms = (ImageView) usuario.findViewById(R.id.comones_sms);

			correo.setOnClickListener(this);
			telefono.setOnClickListener(this);
			sms.setOnClickListener(this);

			votos = (TextView) usuario.findViewById(R.id.uservotos);
			publicaciones = (TextView) usuario.findViewById(R.id.userpublicaciones);
			ranking = (TextView) usuario.findViewById(R.id.userranking);

			rbCalificacion = (RatingBar) usuario.findViewById(R.id.userrating);
			valoracionsignal = (TextView) usuario.findViewById(R.id.valoracionsignal);
			actlic = (TextView) usuario.findViewById(R.id.useractlic);
			nolic = (TextView) usuario.findViewById(R.id.usernumlic);
			
		//	CargarDatosUsuario();

			Drawable imgr = soporte.ImagenesRedondeadas(soporte.ImagenesPorprovincia[usuarioamostrar.getProvincia()]);
			img_prov_usuario.setImageDrawable(imgr);


			tvnombre.setText(usuarioamostrar.getNombre());

////sacar de la BD BD
			votos.setText(usuarioamostrar.getCantidad_votos() + "");


			publicaciones.setText(usuarioamostrar.getCant_post() + "");


			ranking.setText(usuarioamostrar.getRanking() + "");

			Float valoracion = Float.valueOf(usuarioamostrar.getValoracion()) ;

			if(Float.isNaN(valoracion))
			{
				valoracion = 1f;
				PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("valoracion","1").apply();
			}
			rbCalificacion.setRating(valoracion );
			rbCalificacion.invalidate();
			valoracionsignal.setText("valoración --- ( " + valoracion + " )");

			actlic.setText(usuarioamostrar.getActividad_lic());


			nolic.setText(usuarioamostrar.getNolicencia());

			animation = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) (usuarioamostrar.getValoracion() * 100)); // see this max value coming back here, we animale towards that value
			animation.setDuration(300); //in milliseconds
			animation.setInterpolator(new DecelerateInterpolator());
			animation.start();
			progressBar.clearAnimation();

			rec_comentarios = (RecyclerView) usuario.findViewById(R.id.comentarios);

			ArrayList<Comentario> comentarios = basedatos.ComentariosAUser(usuarioamostrar.getCorreo());
			Clase_Adaptador_Comentarios_Publicacion adapter = new Clase_Adaptador_Comentarios_Publicacion(getContext(),comentarios);

			RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
			rec_comentarios.setLayoutManager(mLayoutManager);
			rec_comentarios.setItemAnimator(new DefaultItemAnimator());
			Drawable divider = getResources().getDrawable(R.drawable.separadordecomentarioizq);
			rec_comentarios.addItemDecoration(new DividerItemDecoration(divider));
			rec_comentarios.setAdapter(adapter);
			TextView numcomentarios = (TextView) usuario.findViewById(R.id.numcomentspublicacion);
			if(comentarios.size()==0)
				numcomentarios.setText(" Sin comentarios aún") ;
			else
				numcomentarios.setText(comentarios.size() + " COMENTARIOS");

			return usuario;


		}





		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.comones_telefono: {
					comunicaciones.Llamar(usuarioamostrar.getTelefono());
					break;
				}
				case R.id.comones_correo: {
					comunicaciones.Lanzar_Correo(usuarioamostrar.getCorreo(), null);
					break;
				}
				case R.id.comones_sms: {
					comunicaciones.EnviarSMS(usuarioamostrar.getTelefono());
					break;
				}
			}
		}

	}
}
	

	


