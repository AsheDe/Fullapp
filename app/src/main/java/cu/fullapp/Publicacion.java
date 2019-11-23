package cu.fullapp;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Publicacion implements Parcelable{
	private String ident;
	private	String correo_usuario;
	private String titulo;
	private float precio;
	private String moneda;
	private String contenido;
	private float valoracion;
	private String categoria;
	private String subcategoria;
	private int provincia;
	private Date fecha;
	private int visitada;
	private int enviado;
	private int cantcomentarios;
	private long momento;
	private int patrocinado;
	private int busco;
	public long getMomento() {
		return momento;
	}

	public void setMomento(long momento) {
		this.momento = momento;
		this.fecha = new Date(momento);
	}




	public Publicacion()
	{

	}
	public Publicacion(Parcel parcel)
	{
		ident =	parcel.readString(  );
		correo_usuario =	parcel.readString(	 );
		titulo = parcel.readString(  );
		precio = parcel.readFloat( );
		moneda =parcel.readString(  );
		contenido = parcel.readString(  );
		valoracion = parcel.readFloat(  );
		categoria = parcel.readString(  );
		subcategoria = parcel.readString(  );
		provincia = parcel.readInt(  );
		fecha = new Date(parcel.readLong( ));
		visitada = parcel.readInt(  );
		enviado = parcel.readInt(  );
		cantcomentarios = parcel.readInt(  );
		momento = parcel.readLong();
		busco = parcel.readInt();
	}

	public void Crear_Identificador()
	{
		String id = "";
		id = this.correo_usuario + "";
		id +="#";
		fecha = new Date();
		id += fecha.getTime() + "";
		this.ident = id;
	}
	

	public String getCorreo_usuario() {
		return correo_usuario;
	}

	public void setCorreo_usuario(String correo_usuario) {
		this.correo_usuario = correo_usuario;
	}
	
	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
	}


	public String getContenido() {
		return contenido;
	}


	public void setContenido(String contenido) {
		this.contenido = contenido;
	}


	public float getValoracion() {
		return valoracion;
	}


	public void setValoracion(float valoracion) {
		this.valoracion = valoracion;
	}

	/**
	 * @param ident the ident to set
	 */
	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getIdent() {
		return ident;
	}
	
	/**
	 * @return the categoria
	 */

	
	/**
	 * @return the fecha
	 */
	public Date getFecha()
	{
		return fecha;
	}

	public String getFechaConfigurada()
	{
		int dia = fecha.getDate();
		int mes = fecha.getMonth()+1;
		int year = fecha.getYear()+1900;
		int hora = fecha.getHours();
		int minuto = fecha.getMinutes();
		String minutoconv;
		if(minuto<10)
			minutoconv = "0" +minuto;
		else
			minutoconv = minuto+"";
		String ampm;
		if(hora/12==0)
			ampm=" AM";
		else
	 		ampm=" PM";
		String tureturn = dia+"-"+mes+"-"+year + " " + hora+ ":"+minutoconv+ ampm;
		return tureturn ;
	}

	public void setFecha(long fecha) {
		this.fecha = new Date(fecha);
		this.momento = fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.momento = fecha.getTime();
		this.fecha = fecha;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Publicacion correo_usuario=" + correo_usuario + ", titulo="
				+ titulo + ", precio=" + precio + ", contenido=" + contenido
				+ ", valoracion=" + valoracion + ", ident=" + ident + "]";
	}

	public boolean isVisitada() {
		return visitada!=0;
	}

	public void setVisitada(int visitada) {
		this.visitada = visitada;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public int getProvincia() {
		return provincia;
	}

	public void setProvincia(int provincia) {
		this.provincia = provincia;
	}

	public String getMoneda() {
		return moneda;
	}

	public String getCategoria() {
		return categoria;
	}

	public int isEnviado() {
		return enviado;
	}

	public void setEnviado(int enviado) {
		this.enviado = enviado;
	}

	public int getCantcomentarios() {
		return cantcomentarios;
	}

	public void setCantcomentarios(int cantcomentarios) {
		this.cantcomentarios = cantcomentarios;
	}

	public int getPatrocinado() {
		return patrocinado;
	}

	public void setPatrocinado(int patrocinado) {
		this.patrocinado = patrocinado;
	}

	public int getBusco() {
		return busco;
	}

	public void setBusco(int busco) {
		this.busco = busco;
	}

	public String paraCompartir(Context contexto)
	{
		String [] provs = contexto.getResources().getStringArray(R.array.arProvincias);
		return "Mira lo que encontré en \"Fácil\": " + titulo + " a " + precio + " "+ moneda + ". Detalles: " + contenido + " , eso lo propone alguien en " + provs[provincia] + ".";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(  ident);
		dest.writeString(	 correo_usuario);
		dest.writeString(  titulo);
		dest.writeFloat( precio);
		dest.writeString(  moneda);
		dest.writeString(  contenido);
		dest.writeFloat(  valoracion);
		dest.writeString(  categoria);
		dest.writeString(  subcategoria);
		dest.writeInt(  provincia);
		dest.writeLong( fecha.getTime());
		dest.writeInt(  visitada);
		dest.writeInt(  enviado);
		dest.writeInt(  cantcomentarios);
		dest.writeLong(momento);
		dest.writeInt(busco);
	}

	public static final Parcelable.Creator<Publicacion> CREATOR = new Parcelable.Creator<Publicacion>()
	{
		public Publicacion createFromParcel(Parcel in)
		{
			return new Publicacion(in);
		}
		public Publicacion[] newArray(int size)
		{
			return new Publicacion[size];
		}
	};

}
