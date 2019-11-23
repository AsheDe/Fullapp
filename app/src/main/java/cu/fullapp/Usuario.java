package cu.fullapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.ListPreference;
import android.preference.PreferenceManager;

import java.util.Date;

public class Usuario implements Parcelable {
	
	private String nombre;
	private String correo;	
	private float valoracion;

	private String nolicencia;
	private String actividad_lic;
	private int provincia;
	private	int telefono;
	private int cant_post;
	private int ranking;
	private Date fechar;
	private int cantidad_votos;
	public int getCantidad_votos() {
		return cantidad_votos;
	}

	public void setCantidad_votos(int cantidad_votos) {
		this.cantidad_votos = cantidad_votos;
	}






	/// aumenta de forma logica
	


	public Usuario() {
		this.nombre = "No registrado";
		this.correo = "correo@mail.cu";
		this.valoracion = 0f;
		this.nolicencia = "000000";
		this.actividad_lic = "Sin licencia";
		this.provincia = 13;
		this.telefono = 000000;
		this.ranking = 0;
		this.fechar = new Date();
		this.cant_post =0;
		this.cantidad_votos = 0;
	}


	public Usuario(Parcel parcel)
	{
		this.nombre = parcel.readString(  );;
		this.correo = parcel.readString(  );;
		this.valoracion =parcel.readFloat( );
		this.nolicencia = parcel.readString(  );;
		this.actividad_lic = parcel.readString(  );;
		this.provincia = parcel.readInt(  );
		this.telefono = parcel.readInt(  );
		this.ranking = parcel.readInt(  );
		this.fechar = new Date(parcel.readLong( ));
		this.cant_post = parcel.readInt(  );
		this.cantidad_votos = parcel.readInt(  );
		
	}


	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}



	public Date getFechar() {
		return fechar;
	}


	public void setFechar(Date fechar) {
		this.fechar = fechar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public float getValoracion() {
		return valoracion;
	}

	public void setValoracion(float valoracion) {
		this.valoracion = valoracion;
	}


	public String getNolicencia() {
		return nolicencia;
	}

	public void setNolicencia(String nolicencia) {
		this.nolicencia = nolicencia;
	}

	public String getActividad_lic() {
		return actividad_lic;
	}

	public void setActividad_lic(String actividad_lic) {
		this.actividad_lic = actividad_lic;
	}

	public int getProvincia() {
		return provincia;
	}

	public void setProvincia(int provincia) {
		this.provincia = provincia;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getCant_post() {
		return cant_post;
	}

	public void setCant_post(int cant_post) {
		this.cant_post = cant_post;
	}

	public String toString()
	{
		String usuario = "";
		usuario += "<u1>" + nombre + "</u1>";
		usuario += "<u2>" + correo + "</u2>";
		usuario += "<u3>" + telefono + "</u3>";
		usuario += "<u4>" + provincia + "</u4>";
		usuario += "<u5>" + actividad_lic + "</u5>";
		usuario += "<u6>" + nolicencia + "</u6>";
		usuario += "<u7>" + valoracion + "</u7>";
		usuario += "<u8>" + cant_post + "</u8>";
		usuario += "<u9>" + cantidad_votos + "</u9>";
		usuario += "<u10>" + fechar.getTime() + "</u10></su>";
	//	usuario += "<u11>" + cantidad_votos + "</u11>";
	//		usuario += "<u12>" + cantidad_votos + "</u12>";

		return usuario;
	}
	public Usuario CRARUSERLOCAL(Context context)
	{
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
		this.nombre = preferencias.getString("nombre","No registrado");
		this.correo = preferencias.getString("correo","correo@mail.cu");
		this.valoracion =Float.parseFloat(preferencias.getString("valoracion","0"));
		this.nolicencia = preferencias.getString("nolic","000000");
		this.actividad_lic = preferencias.getString("actlic","Sin licencia");
		this.provincia = Integer.parseInt( preferencias.getString("prov", "13"));
		this.telefono = Integer.parseInt( preferencias.getString("telef","000000"));
		this.fechar = new Date();
		this.cant_post = Integer.parseInt( preferencias.getString("cant_post","0"));
		this.cantidad_votos = Integer.parseInt( preferencias.getString("cant_votos","0"));
		this.ranking = Integer.parseInt( preferencias.getString("ranking","0"));

		return this;
	}

	public void Publicar(Context context,int cantpubs){
		float promed = (this.valoracion*this.cant_post)+(cantpubs*5);
		this.cant_post += cantpubs;
		this.valoracion = promed/this.cant_post;
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
		preferencias.edit().putString("cant_post",this.cant_post+"").commit();
		preferencias.edit().putString("valoracion",this.valoracion+"").commit();

	}
	public void DeshacerPublicar(Context context,int cantpubs){
		float promed = (this.valoracion*this.cant_post)-(cantpubs*5);
		this.cant_post -= cantpubs;
		this.valoracion = promed/this.cant_post;
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
		preferencias.edit().putString("cant_post",this.cant_post+"").commit();
		preferencias.edit().putString("valoracion",this.valoracion+"").commit();
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		

		 parcel.writeString( nombre );;
		 parcel.writeString( correo );;
		 parcel.writeFloat( valoracion);
		 parcel.writeString(nolicencia);;
		 parcel.writeString( actividad_lic );
		 parcel.writeInt( provincia );
		 parcel.writeInt( telefono );
		 parcel.writeInt( ranking  );
		 parcel.writeLong( fechar.getTime() );
		 parcel.writeInt( cant_post );
		 parcel.writeInt( cantidad_votos );
	}


	public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>()
	{
		public Usuario createFromParcel(Parcel in)
		{
			return new Usuario(in);
		}
		public Usuario[] newArray(int size)
		{
			return new Usuario[size];
		}
	};

}






	