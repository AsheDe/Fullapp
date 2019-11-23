package cu.fullapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class Preferencias_Usuario extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

	/* (non-Javadoc)
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	private AppCompatDelegate mDelegate;
	Toolbar toolbar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias_usuario);
		setContentView(R.layout.vista_preferencias);
		//getDelegate().installViewFactory();
		//getDelegate().onCreate(savedInstanceState);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("CONFIGURACIÓN");
		//getDelegate().setSupportActionBar(toolbar);
		//getDelegate().getSupportActionBar().setTitle("CONFIGURACIÓN");
		//getDelegate().getSupportActionBar().setDisplayShowTitleEnabled(true);
		//getDelegate().getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
		//getDelegate().setHandleNativeActionModesEnabled(true);
		toolbar.setTitleTextColor(getResources().getColor(R.color.blanco));
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		/*
		setActionBar(toolba);
		getSupportActionBar().setDisplayShowTitleEnabled(showtitle);
*/

		SharedPreferences shared = getPreferenceScreen().getSharedPreferences();
		shared.registerOnSharedPreferenceChangeListener(this);
		EditTextPreference nombrePref = (EditTextPreference) findPreference("nombre");
		nombrePref.setSummary(shared.getString("nombre", "Escriba su nombre"));
		Preference correoPref = (Preference) findPreference("correo");
		correoPref.setSummary(shared.getString("correo", "Correo Registrado"));
		EditTextPreference passwPref = (EditTextPreference) findPreference("fckclave");

		EditTextPreference telefonoPref = (EditTextPreference) findPreference("telef");
		telefonoPref.setSummary(shared.getString("telef", "Escriba el teléfono"));
		ListPreference provinciPref = (ListPreference) findPreference("prov");
		provinciPref.setIcon(PintarIconos(R.drawable.img_mapmark,R.color.iconsettings));
		int prov = Integer.parseInt(shared.getString("prov", "-1"));
		switch (prov)
		{
			case -1:
			{
				provinciPref.setSummary("Seleccione Provincia");
				break;
			}
			case 0:
			{
				provinciPref.setSummary("Pinar del Río");
				break;
			}
			case 1:
			{
				provinciPref.setSummary("Habana");
				break;
			}
			case 2:
			{
				provinciPref.setSummary("Artemisa");
				break;
			}
			case 3:
			{
				provinciPref.setSummary("Mayabeque");
				break;
			}
			case 4:
			{
				provinciPref.setSummary("Matanzas");
				break;
			}
			case 5:
			{
				provinciPref.setSummary("Cienfuegos");
				break;
			}
			case 6:
			{
				provinciPref.setSummary("Villa Clara");
				break;
			}case 7:
			{
			provinciPref.setSummary("Sancti Spiritus");
			break;
			}
			case 8:
			{
			provinciPref.setSummary("Ciego de Ávila");
			break;
			}
			case 9:
			{
				provinciPref.setSummary("Camaguey");
				break;
			}
			case 10:
			{
				provinciPref.setSummary("Las Tunas");
				break;
			}
			case 11:
			{
				provinciPref.setSummary("Granma");
				break;
			}
			case 12:
			{
				provinciPref.setSummary("Holguín");
				break;
			}
			case 13:
			{
				provinciPref.setSummary("Santiago de Cuba");
				break;
			}
			case 14:
			{
				provinciPref.setSummary("Guantánamo");
				break;
			}
			case 15:
			{
				provinciPref.setSummary("Isla de La Juventud");
				break;
			}

			default: {
				provinciPref.setSummary("Seleccione Provincia");
				break;
			}

		}

		EditTextPreference actcpPref = (EditTextPreference) findPreference("actlic");
		actcpPref.setSummary(shared.getString("actlic", "Actividad por cuenta propia (Opcional)"));
		EditTextPreference numlicPref = (EditTextPreference) findPreference("nolic");
		numlicPref.setSummary(shared.getString("nolic", "Número de Licencia (Opcional)"));
		/*EditTextPreference servePref = (EditTextPreference) findPreference("serversend");
		servePref.setSummary(shared.getString("serversend", "SERVIDOR DE ENVIO DE CORREOS"));
		EditTextPreference servrec = (EditTextPreference) findPreference("serverreceive");
		servrec.setSummary(shared.getString("serverreceive", "SERVIDOR DE RECEPCIÓN DE CORREOS"));
		ListPreference protocol = (ListPreference) findPreference("protocolo");
		protocol.setSummary(protocol.getEntry());
		ListPreference usessl = (ListPreference) findPreference("usessl");
		usessl.setSummary(usessl.getEntry());

		/*Preference registPref = (Preference)findPreference("ac42txf");
		registPref.setIcon(PintarIconos(R.drawable.img_guardar,R.color.iconsettings));
		registPref.setSummary(shared.getString("kr9t4br","No registrado"));*/
		//

		CheckBoxPreference todacuba = (CheckBoxPreference) findPreference("todacuba");
		todacuba.setChecked(todacuba.isChecked());
	/*	cate = (CheckBoxPreference) findPreference("subcripcion_hogar");
		cate.setChecked(cate.isChecked());
		cate = (CheckBoxPreference) findPreference("subcripcion_transporte");
		cate.setChecked(cate.isChecked());
		cate = (CheckBoxPreference) findPreference("subcripcion_celular");
		cate.setChecked(cate.isChecked());
		cate = (CheckBoxPreference) findPreference("subcripcion_computadora");
		cate.setChecked(cate.isChecked());*/



		/*Preference cvotos = (Preference) findPreference("cant_votos");
		cvotos.setSummary(shared.getString("cant_votos","0"));

		Preference cposts = (Preference) findPreference("cant_post");
		cposts.setSummary(shared.getString("cant_post","0"));
		Preference valoracion = (Preference) findPreference("valoracion");
		valoracion.setSummary(shared.getString("valoracion","0"));
		Preference ranking = (Preference) findPreference("ranking");
		ranking.setSummary(shared.getString("ranking","0"));*/
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

		Preference pref = findPreference(key);
		if (pref instanceof EditTextPreference) {
			EditTextPreference etp = (EditTextPreference) pref;
			pref.setSummary(etp.getText());
		}

		if(key.compareTo("prov")==0)
		{
			((ListPreference)pref).setValue(sharedPreferences.getString("prov","Seleccione su provincia"));
			((ListPreference)pref).setSummary(((ListPreference)pref).getEntry()); ;
		}
		/*if(key.compareTo("solomiprov")==0)
		{
			((ListPreference)pref).setValue(sharedPreferences.getString("solomiprov","Seleccione su provincia"));
			((ListPreference)pref).setSummary(((ListPreference)pref).getEntry()); ;
		}
	/*	if(key.compareTo("protocolo")==0)
		{
			((ListPreference)pref).setValue(sharedPreferences.getString("protocolo","Protocolo de recepción de correos"));
			((ListPreference)pref).setSummary(((ListPreference)pref).getEntry());

		}
	/*	if(key.compareTo("subcripcion_computadora")==0)
		{

			boolean checked = sharedPreferences.getBoolean("subcripcion_computadora",false);

			if( checked )
			{
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-computadora+join@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "computadora",false).execute(destinatario, "", "", getBaseContext());
			}
			else {
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-computadora+unsubscribe@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "computadora",true).execute(destinatario, "", "", getBaseContext());
			}
			Toast.makeText(getBaseContext(),"Por favor, espera unos segundos a que la operación se complete", Toast.LENGTH_LONG).show();
		}
		if(key.compareTo("subcripcion_celular")==0)
		{
			boolean checked = sharedPreferences.getBoolean("subcripcion_celular",false);
			if( checked )
			{
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-celular+join@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "celular",false).execute(destinatario, "", "", getBaseContext());
			}
			else {
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-celular+unsubscribe@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "celular",true).execute(destinatario, "", "", getBaseContext());
			}
			Toast.makeText(getBaseContext(),"Por favor, espera unos segundos a que la operación se complete", Toast.LENGTH_LONG).show();

		}
		if(key.compareTo("subcripcion_transporte")==0)
		{
			boolean checked = sharedPreferences.getBoolean("subcripcion_transporte",false);
			if( checked )
			{
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-transporte+join@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "transporte",false).execute(destinatario, "", "", getBaseContext());
			}
			else {
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-transporte+unsubscribe@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "transporte",true).execute(destinatario, "", "", getBaseContext());
			}
			Toast.makeText(getBaseContext(),"Por favor, espera unos segundos a que la operación se complete", Toast.LENGTH_LONG).show();
		}
		if(key.compareTo("subcripcion_hogar")==0)
		{
			boolean checked = sharedPreferences.getBoolean("subcripcion_hogar",false);
			if( checked )
			{
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-hogar+join@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "hogar",false).execute(destinatario, "", "", getBaseContext());
			}
			else {
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-hogar+unsubscribe@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "hogar",true).execute(destinatario, "", "", getBaseContext());
			}
			Toast.makeText(getBaseContext(),"Por favor, espera unos segundos a que la operación se complete", Toast.LENGTH_LONG).show();
		}
		if(key.compareTo("subcripcion_miscelaneas")==0)
		{
			boolean checked = sharedPreferences.getBoolean("subcripcion_miscelaneas",false);
			if( checked )
			{
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-miscelaneas+join@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "miscelaneas",false).execute(destinatario, "", "", getBaseContext());
			}
			else {
				ArrayList<String> destinatario = new ArrayList<>();
				destinatario.add("facil-app-miscelaneas+unsubscribe@googlegroups.com");
				new Enviar_Registro_AsyncTask(this, true, "miscelaneas",true).execute(destinatario, "", "", getBaseContext());
			}

			Toast.makeText(getBaseContext(),"Por favor, espera unos segundos a que la operación se complete", Toast.LENGTH_LONG).show();

		}*/

	}

	public Drawable PintarIconos(Integer drawable, Integer color) {
		Drawable imgs = ContextCompat.getDrawable(getBaseContext(), drawable);
		Drawable mutado = imgs.mutate();
		mutado = DrawableCompat.wrap(mutado);
		DrawableCompat.setTint(mutado, getBaseContext().getResources().getColor(color));
		DrawableCompat.setTintMode(mutado, PorterDuff.Mode.SRC_IN);
		return mutado;
	}

	private AppCompatDelegate getDelegate() {
		if (mDelegate == null) {
			mDelegate = AppCompatDelegate.create(this, null);
		}
		return mDelegate;
	}

}
