package cu.fullapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Manejo_DB {

	DB_Helper dbhelper;
	Context contexto;

	//String last_query_perform;

	public Manejo_DB(Context context) {
		dbhelper = new DB_Helper(context, "CPropuestas", null, 2);
		//SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		//dbhelper = new DB_Helper()
		contexto = context;
		//last_query_perform = " SELECT * FROM Publicaciones ";
	}


	///Funciones

	public void Insertar_Post_enBD(Publicacion pub) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		if (base_de_datos != null) {
			Cursor c = base_de_datos.rawQuery("SELECT ident FROM Publicaciones WHERE ident = '" + pub.getIdent() + "'", null);
			if (c.moveToFirst()==false)
			//Insertamos los datos en la tabla Publicaciones
			{
				base_de_datos.execSQL("INSERT INTO Publicaciones (ident, correo, titulo, precio, contenido, valoracion, categoria, subcategoria, moneda, fecha, nueva, provincia,cantcomentarios,busco,patrocinado) "
						+ "VALUES ('" + pub.getIdent() + "', '" + pub.getCorreo_usuario() + "', '" + pub.getTitulo() + "', '" + pub.getPrecio() + "', '" + pub.getContenido() + "', '" + 2 + "', '"
						+ pub.getCategoria() + "', '" + pub.getSubcategoria() + "', '" + pub.getMoneda() + "', '" + pub.getMomento() + "', '" + 0 + "', '" + pub.getProvincia() + "', '" + 1 + "', '" + pub.getBusco()+"', '"+ pub.getPatrocinado() +  "');");

			}
		}

		dbhelper.CerrarBasedeDatos();
	}

	public void Insertar_Post_enMisPublicaciones(Publicacion pub) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		if (base_de_datos != null) {
			Cursor c = base_de_datos.rawQuery("SELECT ident FROM MisPublicaciones WHERE ident = '" + pub.getIdent() + "'", null);
			//Insertamos los datos en la tabla Publicaciones
			if(c.moveToFirst())
			{
				ContentValues updatepub = new ContentValues();
				updatepub.put("titulo" ,  pub.getTitulo());
				updatepub.put("precio" ,  pub.getPrecio());
				updatepub.put("contenido" ,  pub.getContenido());
				updatepub.put("categoria" ,  pub.getCategoria());
				updatepub.put("subcategoria" ,  pub.getSubcategoria());
				updatepub.put("moneda" ,  pub.getMoneda());
				updatepub.put("fecha" ,  pub.getFecha().getTime());
				updatepub.put("provincia" ,  pub.getProvincia());
				updatepub.put("busco" ,  pub.getBusco());
				updatepub.put("patrocinado" ,  pub.getPatrocinado());
				base_de_datos.update("MisPublicaciones",updatepub,"ident=='"+c.getString(0)+"'",null);
			}
			else {
				base_de_datos.execSQL("INSERT INTO MisPublicaciones (ident, correo, titulo, precio, contenido, valoracion, categoria, subcategoria, moneda, fecha, nueva, provincia, enviado,busco,patrocinado) "
						+ "VALUES ('" + pub.getIdent() + "', '" + pub.getCorreo_usuario() + "', '" + pub.getTitulo() + "', '" + pub.getPrecio() + "', '" + pub.getContenido() + "', '" + pub.getValoracion() + "', '"
						+ pub.getCategoria() + "', '" + pub.getSubcategoria() + "', '" + pub.getMoneda() + "', '" + pub.getFecha().getTime() + "', '" + pub.isVisitada() + "', '" + pub.getProvincia() + "', '" + pub.isEnviado() + "', '" + pub.getBusco()+"', '"+ pub.getPatrocinado()+ "');");

				//Log.d("busc", pub.getBusco()+"-"+ pub.getPatrocinado());
			}
			}

		dbhelper.CerrarBasedeDatos();
	}
	/// Parametros Usuario user
	public void Insertar_Usuario_enBD(Usuario user) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		if (base_de_datos != null) {
			Cursor c = base_de_datos.rawQuery("SELECT correo FROM Usuarios WHERE correo = '" + user.getCorreo() + "'", null);
			if (c.moveToFirst())
			{
				ContentValues INFOUSER = new ContentValues();

				INFOUSER.put("nombre" ,  user.getNombre());
				INFOUSER.put("valoracion",user.getValoracion());
				INFOUSER.put("nolicencia",user.getNolicencia());
				INFOUSER.put("actividad_lic",user.getActividad_lic());
				INFOUSER.put("provincia",user.getProvincia());
				INFOUSER.put("telefono",user.getTelefono());
				INFOUSER.put("cant_post",user.getCant_post());
				INFOUSER.put("fechar",user.getFechar().getTime());
				INFOUSER.put("ranking",user.getRanking());
				INFOUSER.put("votos",user.getCantidad_votos());
				//correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking
				base_de_datos.update("Usuarios",INFOUSER,"correo=='"+c.getString(0)+"'",null);

			//	base_de_datos.execSQL("UPDATE Usuarios SET   + "',  = '" +  + "',  = '"+  + " = '"+  + "',  = '" +  + "' WHE; ") ;
			}
			else
			{
				base_de_datos.execSQL("INSERT INTO Usuarios (correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking,votos) "
						+ "VALUES ('" + user.getCorreo() + "', '" + user.getNombre() + "', '" + user.getValoracion() + "', '" + user.getNolicencia() + "', '" + user.getActividad_lic() + "', '"
						+ user.getProvincia() + "', '" + user.getTelefono() + "', '" + user.getCant_post() + "', '" + user.getFechar() + "', '" + user.getRanking() + "', '" + user.getCantidad_votos() + "');");
			}

				}
		dbhelper.CerrarBasedeDatos();
	}


	public ArrayList<Publicacion> Obtener_Publicaciones() {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT * FROM Publicaciones", null);
		ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();

		//Nos aseguramos de que existe al menos un registro

		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do {

				Publicacion pub1 = CrearPublicacion(c);

				publicaciones.add(pub1);

			} while (c.moveToNext());
		} else {
			publicaciones.add(PublicacionFantasma( "COMPUTADORA",null));
			publicaciones.add(PublicacionFantasma( "CELULAR",null));
			publicaciones.add(PublicacionFantasma( "TRANSPORTE",null));
			publicaciones.add(PublicacionFantasma( "HOGAR",null));
			publicaciones.add(PublicacionFantasma( "MISCELANEAS",null));

		}

		dbhelper.CerrarBasedeDatos();
		return publicaciones;
	}


	public ArrayList<Usuario> Obtener_Usuarios() {

		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT correo,nombre,provincia,cant_post  FROM Usuarios", null);
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		//Nos aseguramos de que existe al menos un registro

		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do {

				Usuario user1 = new Usuario();
				user1.setCorreo(c.getString(0));
				user1.setNombre(c.getString(1));
				user1.setProvincia(c.getInt(2));
				user1.setCantidad_votos(c.getInt(3));
				usuarios.add(user1);

			} while (c.moveToNext());
		}

		dbhelper.CerrarBasedeDatos();
		return usuarios;
	}

	public Usuario Usuario_por_Correo(String mail) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery("SELECT * FROM Usuarios WHERE correo=='" + mail + "'", null);
		Usuario user = new Usuario();
		if (c.moveToFirst()) {

		//	"CREATE TABLE Usuarios (correo TEXT, nombre TEXT, telefono INTEGER, provincia INTEGER, nolicencia TEXT, actividad_lic TEXT, cant_post INTEGER, valoracion FLOAT, fechar REAL,ranking INTEGER,votos INTEGER )";
			//Recorremos el cursor hasta que no haya más registros

			user.setCorreo(c.getString(0));
			user.setNombre(c.getString(1));
			user.setTelefono(c.getInt(2));
			user.setProvincia(c.getInt(3));
			user.setNolicencia(c.getString(4));
			user.setActividad_lic(c.getString(5));
			user.setCant_post(c.getInt(6));
			user.setValoracion(c.getFloat(7));
			Date fechar = new Date(c.getLong(8));
			user.setFechar(fechar);
			user.setRanking(c.getInt(9));
			user.setCantidad_votos(c.getInt(10));

		}
		dbhelper.CerrarBasedeDatos();
		return user;
	}

	public void Eliminar_Publicacion(String id) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		base_de_datos.execSQL("DELETE FROM Publicaciones WHERE ident=='" + id + "';");
		base_de_datos.execSQL("DELETE FROM Comentarios WHERE publicacion=='" + id + "';");
		base_de_datos.execSQL("DELETE FROM MisComentarios WHERE publicacion=='" + id + "';");

		dbhelper.CerrarBasedeDatos();
	}

	public void Eliminar_MiPublicacion(String id) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		base_de_datos.execSQL("DELETE FROM MisPublicaciones WHERE ident=='" + id + "';");

		dbhelper.CerrarBasedeDatos();
	}

	public void Eliminar_MiComentario(String id)
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		base_de_datos.execSQL("DELETE FROM MisComentarios WHERE ident=='" + id + "';");

		dbhelper.CerrarBasedeDatos();
	}

	public void SetVisitada(String id, int vis) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		ContentValues vist=new ContentValues();
		vist.put("nueva",vis);
					;
		base_de_datos.update("Publicaciones",vist,"ident=='"+id+"'",null);
		dbhelper.CerrarBasedeDatos();
	}


/*
	public void CrearUsuarioLocal( Usuario userlocal) {

		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();

		if (base_de_datos != null) {
			base_de_datos.execSQL("INSERT INTO MiUsuario (correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking,votos) "
					+ "VALUES ('" + userlocal.getCorreo() + "', '" + userlocal.getNombre() + "', '" + userlocal.getValoracion() + "', '" + userlocal.getNolicencia() + "', '" + userlocal.getActividad_lic() + "', '"
					+ userlocal.getProvincia() + "', '" + userlocal.getTelefono() + "', '" + userlocal.getCant_post() + "', '" + userlocal.getFechar().getTime() + "', '" + userlocal.getRanking() + "');");
		}
		else
		{
			Log.d("Error", "Error Obteniendo la base de datos");
		}
		dbhelper.CerrarBasedeDatos();
	}*/

/*	public Usuario Obtener_Datos_Usuario_Local() {
		Usuario user = new Usuario();

		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery("SELECT * FROM MiUsuario", null);
		if (c.moveToFirst()) {

			user.setCorreo(c.getString(0));
			user.setNombre(c.getString(1));
			user.setTelefono(c.getInt(2));
			user.setProvincia(c.getInt(3));
			user.setNolicencia(c.getString(4));
			user.setActividad_lic(c.getString(5));
			user.setCant_post(c.getInt(6));
			user.setValoracion(c.getFloat(7));
			Date fechar = new Date(c.getLong(8));
			user.setFechar(fechar);
			user.setRanking(c.getInt(9));
		}
		dbhelper.CerrarBasedeDatos();
		return user;
	}*/

	public ArrayList<Publicacion> Ultimas_Publicaciones() {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery("SELECT * FROM Publicaciones ORDER BY fecha DESC LIMIT 15;", null);
		//last_query_perform = "SELECT * FROM Publicaciones ORDER BY fecha DESC LIMIT 15;";

		ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();

		//Nos aseguramos de que existe al menos un registro

		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do {

				Publicacion pub1 = CrearPublicacion(c);

				publicaciones.add(pub1);

			} while (c.moveToNext());
		} else {
			publicaciones.add(PublicacionFantasma( "COMPUTADORA",null));
			publicaciones.add(PublicacionFantasma( "CELULAR",null));
			publicaciones.add(PublicacionFantasma( "TRANSPORTE",null));
			publicaciones.add(PublicacionFantasma( "HOGAR",null));
			publicaciones.add(PublicacionFantasma( "MISCELANEAS",null));

		}

		dbhelper.CerrarBasedeDatos();
		return publicaciones;
	}

	public void SetupBaseDatos() {
		//SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();

		//base_de_datos.execSQL("DROP TABLE IF EXISTS 'MiUsuario'");
		//base_de_datos.execSQL("DROP TABLE IF EXISTS  Usuarios");
		//base_de_datos.execSQL(dbhelper.crear_tusuarios);
		//base_de_datos.execSQL("DELETE FROM Usuarios WHERE nombre LIKE '%" + "regis" + "%';");
		//dbhelper.CerrarBasedeDatos();
		/*base_de_datos.execSQL("DROP TABLE IF EXISTS  Usuarios");
		base_de_datos.execSQL("DROP TABLE IF EXISTS  Publicaciones");
		base_de_datos.execSQL("DROP TABLE IF EXISTS  Comentarios");
		base_de_datos.execSQL("DROP TABLE IF EXISTS  MiUsuario");
		base_de_datos.execSQL("DROP TABLE IF EXISTS 'MisPublicaciones'");
		base_de_datos.execSQL("DROP TABLE IF EXISTS 'MisComentarios'");

		base_de_datos.execSQL(dbhelper.crear_tusuarios);
		base_de_datos.execSQL(dbhelper.crear_tposts);
		base_de_datos.execSQL(dbhelper.crear_tcomentarios);
		base_de_datos.execSQL(dbhelper.crear_tmiusuario);
		base_de_datos.execSQL(dbhelper.crear_tmposts);
		base_de_datos.execSQL(dbhelper.crear_tmcomentarios);

		//CrearUsuarioLocal();*/
		//dbhelper.AbrirBasedeDatos();
	/*	SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		base_de_datos.execSQL("DROP TABLE IF EXISTS 'MisPublicaciones'");
		base_de_datos.execSQL("DROP TABLE IF EXISTS 'MisComentarios'");
		base_de_datos.execSQL(dbhelper.crear_tmposts);
		base_de_datos.execSQL(dbhelper.crear_tmcomentarios);
		base_de_datos.close();
		Random numb = new Random();
		for (int i = 0; i < 1; i++) {

			Publicacion x = new Publicacion();
			x.setTitulo("SAMSUNG GALAXY S7 EDGE");
			x.setCorreo_usuario("yudeelito@gmail.com");
			x.setContenido("Acabado de traer, nuevo en su caja.");
			x.Crear_Identificador();
			x.setPrecio(440);
			x.setValoracion(4.8f);

			String[] categorias = contexto.getResources().getStringArray(R.array.arCategorias);
			String[] subcategorias = contexto.getResources().getStringArray(R.array.arCatCEL);
			x.setCategoria(categorias[1]);
			x.setSubcategoria(subcategorias[0]);
			String[] monedas = new String[]{"CUC", "MN"};
			x.setMoneda(monedas[0]);
			x.setFecha(new Date());
			x.setVisitada(0);
			x.setProvincia(15);
			x.setEnviado(1);
			Insertar_Post_enBD(x);
		}
/*
		 x = new Publicacion();
		x.setTitulo("telefono viejo, bien cuidado" );
		x.setCorreo_usuario("yudeelito@gmail.com");
		x.setContenido( "Acabado de traer, nuevo en su caja." );
		x.Crear_Identificador();
		x.setPrecio(25);
		x.setValoracion(3.9f);


		x.setCategoria(categorias[1]);
		x.setSubcategoria(subcategorias[5]);

		x.setMoneda(monedas[0]);
		x.setFecha(new Date());
		x.setVisitada(0);
		x.setProvincia(12);
		x.setEnviado(1);
		Insertar_Post_enBD(x);

		x = new Publicacion();
		x.setTitulo("KINDLE ULTIMA GENERACIÓN" );
		x.setCorreo_usuario("yudeelito@gmail.com");
		x.setContenido( "Acabado de traer, nuevo en su caja." );
		x.Crear_Identificador();
		x.setPrecio(140);
		x.setValoracion(4.2f);


		x.setCategoria(categorias[1]);
		x.setSubcategoria(subcategorias[4]);

		x.setMoneda(monedas[0]);
		x.setFecha(new Date());
		x.setVisitada(0);
		x.setProvincia(12);
		x.setEnviado(1);
		Insertar_Post_enBD(x);

		x = new Publicacion();
		x.setTitulo("VENDO MI LINEA" );
		x.setCorreo_usuario("yudeelito@gmail.com");
		x.setContenido( "Acabado de traer, nuevo en su caja." );
		x.Crear_Identificador();
		x.setPrecio(28);
		x.setValoracion(3.1f);


		x.setCategoria(categorias[1]);
		x.setSubcategoria(subcategorias[6]);

		x.setMoneda(monedas[0]);
		x.setFecha(new Date());
		x.setVisitada(0);
		x.setProvincia(12);
		x.setEnviado(1);
		Insertar_Post_enBD(x);

		x = new Publicacion();
		x.setTitulo("IPAD " );
		x.setCorreo_usuario("yudeelito@gmail.com");
		x.setContenido( "Acabado de traer, nuevo en su caja." );
		x.Crear_Identificador();
		x.setPrecio(220);
		x.setValoracion(4.4f);


		x.setCategoria(categorias[1]);
		x.setSubcategoria(subcategorias[1]);

		x.setMoneda(monedas[0]);
		x.setFecha(new Date());
		x.setVisitada(0);
		x.setProvincia(12);
		x.setEnviado(1);
		Insertar_Post_enBD(x);



			//Random numb = new Random();
			//String[] categorias = contexto.getResources().getStringArray(R.array.arCategorias);
			//Publicacion publicacion = PublicacionFantasma(categorias[numb.nextInt(5)],"#"+i+ ".." + numb.nextInt(200));
			//	Insertar_Post_enBD(x);


			/*Comentario coment = new Comentario();
			coment.setEntrada(x.getIdent());
			coment.setComentario("No está mal el precio, pero no llego. Si me la dejas en 600 te la compro esta misma tarde.");
			coment.setRating(4.5f);
			coment.setAutor("guanchon@nauta.cu");
			coment.setEnviado(1);
			coment.setMomento(new Date().getTime());
			coment.CrearIdentificador();
			Insertar_Comentario(coment);
		//}
		//SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		//base_de_datos.execSQL("DROP TABLE IF EXISTS  Usuarios");
		//base_de_datos.execSQL(dbhelper.crear_tusuarios);

		//Random numb = new Random();
		/*for (int i = 1; i < 50; i++) {
			Usuario user = new Usuario();

			user.setCorreo("manager"+i+"@stgo.sasa.co.cu");
			user.setNombre("Nombre" + i);
			user.setTelefono((int) (i * 12 + 5555));
			user.setProvincia(numb.nextInt(16));
			user.setNolicencia(i + 5000 + "");
			user.setActividad_lic("vendedor" + i);
			user.setCant_post(i);
			user.setValoracion(numb.nextInt(5) + numb.nextFloat());
			Date fechar = new Date();
			user.setFechar(fechar);

			Insertar_Usuario_enBD(user);
		}*/



			//Insertar_Post_enMisPublicaciones(x);

			//	Insertar_Comentario(x.getIdent(), "comentario" + i * 5 + " este es un comentario de prueba que estamos haciendo para probar la portada y como funcionan alli las cosas", 3, user.getCorreo());
			//	Insertar_Comentario(x.getIdent(), "comentario" + i * 10 + " este es un comentario de prueba que estamos haciendo para probar la portada y como funcionan alli las cosas", 4.3f, user.getCorreo());
			//	Insertar_Comentario(x.getIdent(), "comentario" + i * 15 + " este es un comentario de prueba que estamos haciendo para probar la portada y como funcionan alli las cosas", 2.5f, user.getCorreo());



	}

	public ArrayList<Publicacion> Obtener_PublicacionesPorCategoria(String categoria) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT * FROM Publicaciones WHERE categoria=='" + categoria + "'  ORDER BY fecha DESC  ", null);
	//	last_query_perform = " SELECT * FROM Publicaciones WHERE categoria=='" + categoria + "'  ORDER BY fecha DESC " ;
		ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();

		//Nos aseguramos de que existe al menos un registro

		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do {

				Publicacion pub1 = CrearPublicacion(c);
				publicaciones.add(pub1);

			} while (c.moveToNext());
		}
			publicaciones.add(PublicacionFantasma( categoria,null));



		dbhelper.CerrarBasedeDatos();
		return publicaciones;
	}

	public void ActualizarPatrocinados()
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT ident,fecha FROM Publicaciones WHERE patrocinado=='1'  ", null);
		if(c.moveToFirst())
		{
			do {
				Date fechapub = new Date(c.getLong(1));
				Date currentdate = new Date();
				if(currentdate.getTime() - fechapub.getTime() > 2592000000L)
				{
					ContentValues values = new ContentValues();
					values.put("patrocinado", 0);
					base_de_datos.update("Publicaciones", values, "ident=='" + c.getString(0) + "'", null);
				}


			} while (c.moveToNext());
		}
		dbhelper.CerrarBasedeDatos();
	}


	/*public void HelpMe()
	{
		SQLiteDatabase arg0 =  dbhelper.AbrirBasedeDatos();

		String agregar_columna_BUSCO_pUB = " ALTER TABLE Publicaciones ADD COLUMN busco INTEGER" ;
		String agregar_columna_BUSCO_Mpub = " ALTER TABLE MisPublicaciones ADD COLUMN busco INTEGER" ;
		String agregar_columna_PATROCINADO_pUB = " ALTER TABLE Publicaciones ADD COLUMN patrocinado INTEGER" ;
		String agregar_columna_PATROCINADO_Mpub = " ALTER TABLE MisPublicaciones ADD COLUMN patrocinado INTEGER" ;

		arg0.execSQL(agregar_columna_BUSCO_pUB);
		arg0.execSQL(agregar_columna_BUSCO_Mpub);
		arg0.execSQL(agregar_columna_PATROCINADO_pUB);
		arg0.execSQL(agregar_columna_PATROCINADO_Mpub);
		arg0.close();

	}*/

	public Publicacion Obtener_PublicacionPorIdentificador(String identificador) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT * FROM Publicaciones WHERE ident=='" + identificador + "'", null);


		//Nos aseguramos de que existe al menos un registro

		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			Publicacion pub1 = CrearPublicacion(c);
			dbhelper.CerrarBasedeDatos();
			return pub1;
		} else {
			Publicacion publicacion = PublicacionFantasma("COMPUTADORA",identificador);
			dbhelper.CerrarBasedeDatos();
			return publicacion;

		}


	}

	public Publicacion Obtener_MiEnvioPorIdentificador(String identificador) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();


		Cursor c = base_de_datos.rawQuery(" SELECT * FROM MisPublicaciones WHERE ident=='" + identificador + "'", null);


		//Nos aseguramos de que existe al menos un registro

		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			Publicacion pub1 = CrearPublicacion(c);
			dbhelper.CerrarBasedeDatos();
			return pub1;
		} else {
			Publicacion publicacion = PublicacionFantasma("CELULAR",identificador);
			dbhelper.CerrarBasedeDatos();
			return publicacion;

		}


	}
	public void Insertar_Comentario(Comentario coment) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		if (base_de_datos != null) {

			Cursor c = base_de_datos.rawQuery("SELECT cantcomentarios,valoracion,correo FROM Publicaciones WHERE ident=='"+ coment.getEntrada() +"'" , null);
			if(c.moveToFirst()) {
				base_de_datos.execSQL("INSERT INTO Comentarios (publicacion, texto, correo_autor, fechacom, valoracion) "
						+ "VALUES ('" + coment.getEntrada() + "', '" + coment.getComentario() + "', '" + coment.getAutor() + "', '" + coment.getMomento() + "', '" + coment.getRating() + "');");

				String correo = c.getString(2);

				ContentValues updatepub = new ContentValues();
				int cant_comments = c.getInt(0);
				float valoracion = 	c.getFloat(1);
				float avgvalpub = valoracion*cant_comments;
				avgvalpub+=coment.getRating();
				cant_comments++;
				valoracion = avgvalpub/cant_comments ;

				updatepub.put("cantcomentarios",cant_comments);
				updatepub.put("valoracion",valoracion);
				base_de_datos = dbhelper.AbrirBasedeDatos();
				base_de_datos.update("Publicaciones",updatepub,"ident=='"+ coment.getEntrada() + "'",null);




				Cursor user = base_de_datos.rawQuery("SELECT * FROM Usuarios WHERE correo=='"+ correo +"'" , null);
				//Cursor user = base_de_datos.rawQuery("SELECT cant_post,valoracion FROM Usuarios WHERE correo=='"+ correo +"'" , null);
				//Cursor user = base_de_datos.rawQuery("SELECT cant_post,valoracion,votos FROM Usuarios WHERE correo=='"+ correo +"'" , null);
				if (user.moveToFirst())
				{

					int cant_post = user.getInt(6);

					float valor = 	user.getFloat(7);
					int votos =  user.getInt(10);
					votos++;
					float valprom = valor*(cant_post-1);
					valprom+=valoracion;
					valoracion =  (valprom/cant_post) ;
					ContentValues valsuser = new ContentValues();
					valsuser.put("valoracion",valoracion);
					valsuser.put("votos",votos);
					base_de_datos.update("Usuarios",valsuser,"correo=='"+ correo + "'",null);

					String local = PreferenceManager.getDefaultSharedPreferences(contexto).getString("correo","");
					if(correo.compareTo(local)==0)
					{
						PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("cant_votos",votos+"").apply();
						PreferenceManager.getDefaultSharedPreferences(contexto).edit().putString("valoracion",valoracion+"").apply();
					}



				}


			}
			}

		dbhelper.CerrarBasedeDatos();
	}


	public void Insertar_MisComentario(Comentario coment) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		if (base_de_datos != null) {

			//Insertamos los datos en la tabla Publicaciones
			base_de_datos.execSQL("INSERT INTO MisComentarios (publicacion, texto, correo_autor, fechacom, valoracion, enviado, ident,categoria) "
					+ "VALUES ('" + coment.getEntrada() + "', '" + coment.getComentario() + "', '" + coment.getAutor() + "', '" + coment.getMomento() + "', '" + coment.getRating() + "', '" + coment.isEnviado() + "', '" + coment.getIdentificador() + "', '" + coment.getCategoria() +"');");
		}

		dbhelper.CerrarBasedeDatos();
	}



	//// Se llama desde la portada
	public ArrayList<Comentario> Obtener_Cometarios() {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT * FROM Comentarios ORDER BY fechacom DESC LIMIT 15", null);

		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
		//Nos aseguramos de que existe al menos un registro

		if (c.moveToFirst()) {
			do {
				Comentario comment = new Comentario();
				comment.setEntrada(c.getString(0));
				comment.setComentario(c.getString(1));
				comment.setAutor(c.getString(2));


				Date fecha = new Date(c.getLong(3));
				comment.setFecha(fecha);
				comment.setRating(c.getFloat(4));
				comentarios.add(comment);
			} while (c.moveToNext());
			//Recorremos el cursor hasta que no haya más registros
			//publicacion, texto, correo_autor, fechacom)


		} else {

				Comentario comment = new Comentario();
				comment.setComentario(" Ecuentra rápido, vende FÁCIL");

				Date fecha = new Date();
				comment.setFecha(fecha);
				comment.setRating(5);
				comentarios.add(comment);

		}

		dbhelper.CerrarBasedeDatos();
		return comentarios;
	}

	public ArrayList<Comentario> Obtener_Cometarios_PorPublicacion(String ident_pub) {
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT * FROM Comentarios WHERE publicacion = '" + ident_pub + "' ORDER BY fechacom DESC", null);

		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
		//Nos aseguramos de que existe al menos un registro

		if (c.moveToFirst()) {
			do {
				Comentario comment = new Comentario();
				comment.setEntrada(c.getString(0));
				comment.setComentario(c.getString(1));
				comment.setAutor(c.getString(2));


				Date fecha = new Date(c.getLong(3));
				comment.setFecha(fecha);
				comment.setRating(c.getFloat(4));
				comentarios.add(comment);
			} while (c.moveToNext());

		} /*else {
			//int x = 200;
			//while (x>0) {
			Comentario comment = new Comentario();
			comment.setEntrada(null);
			comment.setComentario("Sin comentarios aún, quieres ser el primero");
			comment.setAutor(null);


			Date fecha = new Date();
			comment.setFecha(fecha);
			comment.setRating(5);
			comentarios.add(comment);
			//	x--;
			//}


		}*/


		dbhelper.CerrarBasedeDatos();
		return comentarios;
	}

	 ;

	public ArrayList<Object> ObtenerListadoMisEnvios() {
		ArrayList<Object> envios = new ArrayList<Object>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
		ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();

		Cursor MPUBS = base_de_datos.rawQuery(" SELECT * FROM MisPublicaciones  ORDER BY fecha DESC", null);
		//last_query_perform = " SELECT * FROM MisPublicaciones  ORDER BY fecha DESC" ;
		Cursor Mcomments = base_de_datos.rawQuery(" SELECT * FROM MisComentarios ORDER BY fechacom DESC", null);
		if (Mcomments.moveToFirst())
		{
			do {
				Comentario comment = new Comentario();
				comment.setEntrada(Mcomments.getString(0));
				comment.setComentario(Mcomments.getString(1));
				comment.setAutor(Mcomments.getString(2));

				Date fecha = new Date(Mcomments.getLong(3));
				comment.setFecha(fecha);
				comment.setRating(Mcomments.getFloat(4));
				comment.setEnviado(Mcomments.getInt(5));
				comment.setIdentificador(Mcomments.getString(6));
				comment.setCategoria(Mcomments.getString(6));

				comentarios.add(comment);


			} while (Mcomments.moveToNext());
		}
			if (MPUBS.moveToFirst())
			{
				do {
				 	//Recorremos el cursor hasta que no haya más registros
					//Log.d("ew","busco" + MPUBS.getColumnIndex("busco") + "pat " + MPUBS.getColumnIndex("patrocinado"));
					Publicacion pub1 = CrearPublicacion(MPUBS);
					pub1.setEnviado(MPUBS.getInt(12));

					publicaciones.add(pub1);

				} while (MPUBS.moveToNext());
			}
			dbhelper.CerrarBasedeDatos();
		envios = OrdenarMisPublicaciones(publicaciones,comentarios);
		return envios;
	}



	public ArrayList<Object> OrdenarMisPublicaciones(ArrayList<Publicacion> publicaciones, ArrayList<Comentario> comentarios)
	{
		ArrayList<Object> envios = new ArrayList<Object>();
		while (!publicaciones.isEmpty() && !comentarios.isEmpty())
		{
			if(publicaciones.get(0).getFecha().getTime() > comentarios.get(0).getFecha().getTime())
			{
				envios.add(publicaciones.get(0));
				publicaciones.remove(0);
			}
			else
			{
				envios.add(comentarios.get(0));
				comentarios.remove(0);
			}
		}
		if(!publicaciones.isEmpty())
		{
			envios.addAll(publicaciones);
		}
		if(!comentarios.isEmpty())
		{
			envios.addAll(comentarios);
		}

		return envios;
	}

	public ArrayList<Comentario> ComentariosPorEnviar() {
		ArrayList<Comentario> comments = new ArrayList<Comentario>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
//AND categoria=='"+categoria+"'
		Cursor mcomments = base_de_datos.rawQuery("SELECT * FROM MisComentarios WHERE enviado=='0'" , null);
		if (mcomments.moveToFirst()) {
			do {
				Comentario comment = new Comentario();
				comment.setEntrada(mcomments.getString(0));
				comment.setComentario(mcomments.getString(1));
				comment.setAutor(mcomments.getString(2));


				Date fecha = new Date(mcomments.getLong(3));
				comment.setFecha(fecha);
				comment.setRating(mcomments.getFloat(4));
				comment.setEnviado(mcomments.getInt(5));
				comment.setIdentificador(mcomments.getString(6));
				comment.setCategoria(mcomments.getString(7));
				comments.add(comment);

			} while (mcomments.moveToNext());
		}
		dbhelper.CerrarBasedeDatos();
		return comments;
	}

	public ArrayList<Comentario> ComentariosPorEnviar(String categoria) {
		ArrayList<Comentario> comments = new ArrayList<Comentario>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
//AND categoria=='"+categoria+"'
		Cursor mcomments = base_de_datos.rawQuery("SELECT * FROM MisComentarios WHERE enviado=='0' AND categoria=='" + categoria+ "'" , null);
		if (mcomments.moveToFirst()) {
			do {
						Comentario comment = new Comentario();
						comment.setEntrada(mcomments.getString(0));
						comment.setComentario(mcomments.getString(1));
						comment.setAutor(mcomments.getString(2));


						Date fecha = new Date(mcomments.getLong(3));
						comment.setFecha(fecha);
						comment.setRating(mcomments.getFloat(4));
						comment.setEnviado(mcomments.getInt(5));
						comment.setIdentificador(mcomments.getString(6));
						comment.setCategoria(mcomments.getString(7));
						comments.add(comment);

			} while (mcomments.moveToNext());
		}
		dbhelper.CerrarBasedeDatos();
			return comments;
	}
	public ArrayList<Publicacion> PublicacionesPorEnviar(String categoria) {
		ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor MPUBS = base_de_datos.rawQuery("SELECT * FROM MisPublicaciones WHERE enviado=='0' AND categoria=='" + categoria+"'", null);
		if (MPUBS.moveToFirst()) {
			do {
				Publicacion pub1 = CrearPublicacion(MPUBS);
				pub1.setEnviado(MPUBS.getInt(12));
				publicaciones.add(pub1);
			} while (MPUBS.moveToNext());
		}
		dbhelper.CerrarBasedeDatos();
		return publicaciones;
	}
	public ArrayList<Publicacion> PublicacionesPorEnviar() {
		ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor MPUBS = base_de_datos.rawQuery("SELECT * FROM MisPublicaciones WHERE enviado=='0'", null);
		if (MPUBS.moveToFirst()) {
			do {
				Publicacion pub1 = CrearPublicacion(MPUBS);
				pub1.setEnviado(MPUBS.getInt(12));
				publicaciones.add(pub1);
			} while (MPUBS.moveToNext());
		}
		dbhelper.CerrarBasedeDatos();
		return publicaciones;
	}

	public void ActualizarEnviados()
	{

		ContentValues values = new ContentValues();
		values.put("enviado","1");
		ContentValues valuespub = new ContentValues();
		valuespub.put("enviado","1");
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		base_de_datos.update("MisComentarios",values,"enviado=='0'",null);
		base_de_datos.update("MisPublicaciones",valuespub,"enviado=='0'",null);
		dbhelper.CerrarBasedeDatos();

	}

	public void ActualizarFechasPublicaciones(long fecha,String ident )
	{
		ContentValues values = new ContentValues();
		values.put("fecha",fecha+"");

		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();

		base_de_datos.update("MisPublicaciones",values,"ident=='"+ident+"'",null);
		//Cursor MPUBS = base_de_datos.rawQuery("SELECT fecha FROM MisPublicaciones WHERE ident=='"+ident+"'", null);

		dbhelper.CerrarBasedeDatos();

	}
	public void ActualizarFechasComentarios(long fecha,String ident )
	{
		ContentValues valuesc = new ContentValues();
		valuesc.put("fechacom",fecha+"");
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		base_de_datos.update("MisComentarios",valuesc,"ident=='"+ident+"'",null);

		//Cursor MPUBS = base_de_datos.rawQuery("SELECT fechacom,ident,publicacion FROM MisComentarios WHERE ident=='"+ident+"'", null);
		//Cursor MPUBS = base_de_datos.rawQuery("SELECT ident FROM MisComentarios", null);

		dbhelper.CerrarBasedeDatos();
	}

	public Publicacion PublicacionFantasma(String categoria,String ident)
	{
		Publicacion publicacion = new Publicacion();
if(ident!=null)
{
	switch (ident)
	{
		case "pcghost":
		{
			categoria = "COMPUTADORA";
			break;
		}
		case "telghost":
		{
			categoria = "CELULAR";
			break;
		}
		case "traghost":
		{
			categoria = "TRANSPORTE";
			break;
		}
		case "casghost":
		{
			categoria = "HOGAR";
			break;
		}
		case "misghost":
		{
			categoria = "MISCELANEAS";
			break;
		}
	}

}
		switch (categoria)
		{
			case "COMPUTADORA":
			{
				publicacion.setIdent("pcghost");
				publicacion.setTitulo(" PC al pedido");
				publicacion.setCorreo_usuario("yudeelito@gmail.com");
				publicacion.setPrecio(300f);
				publicacion.setContenido("Vendo todo Tipo de PC y accesorios para PC (siempre ajustado a lo que usted desea y puede pagar), se instala en su casa y se deja corriendo, garantía según el tipo de equipo. CONTÁCTEME Y NO SE ARREPENTIRÁ.");
				publicacion.setValoracion(5);
				publicacion.setCategoria("COMPUTADORA");
				publicacion.setSubcategoria("PC (TODO INCLUIDO)");
				publicacion.setMoneda("CUC");
				Date fecha = new Date();
				publicacion.setFecha(fecha);
				publicacion.setVisitada(0);
				publicacion.setProvincia(13);

				break;
			}
			case "CELULAR":
			{
				publicacion.setIdent("telghost");
				publicacion.setTitulo("Se venden CELULARES IPHONE Y ANDROID los mejores que se puedan en contrar en el mercado cubano");
				publicacion.setCorreo_usuario("guanchon@nauta.cu");
				publicacion.setPrecio(150f);
				publicacion.setContenido("LOS MEJORES PRECIOS PARA TELÉFONOS. TELÉFONOS NUEVOS, USTED HACE SU PEDIDO Y SE LE MANDA A BUSCAR Y EN MENOS DE DOS SEMANAS ESTÁ EN SUS MANOS.");
				publicacion.setValoracion(5);
				publicacion.setCategoria("CELULAR");
				publicacion.setSubcategoria("ANDROID");
				publicacion.setMoneda("CUC");
				Date fecha = new Date();
				publicacion.setFecha(fecha);
				publicacion.setVisitada(0);
				publicacion.setProvincia(13);

				break;
			}
			case "TRANSPORTE":
			{
				publicacion.setIdent("traghost");
				publicacion.setTitulo("Yo te llevo Cuba");
				publicacion.setCorreo_usuario("martin@yotellevocuba.com");
				publicacion.setPrecio(20f);
				publicacion.setContenido("Te ponemos en contacto con hasta 3 de nuestros choferes para que acuerdes tu viaje directamente con ellos via correo electrónico antes de llegar a la isla. Los choferes te darán sus precios y tú puedes preguntar cualquier cosa relativa al viaje. Conoce un poco a los choferes mientras intercambian correos. Contrata al chofer que creas mejor de acuerdo a tu presupuesto o necesidades especiales. O simplemente haz un amigo que te lleve en su auto a donde quieras ir. Para más información   www.yotellevocuba.com ");
				publicacion.setValoracion(5);
				publicacion.setCategoria("TRANSPORTE");
				publicacion.setSubcategoria("ALQUILER");
				publicacion.setMoneda("CUC");
				Date fecha = new Date();
				publicacion.setFecha(fecha);
				publicacion.setVisitada(0);
				publicacion.setProvincia(11);

				break;
			}
			case "HOGAR":
			{
				publicacion.setIdent("casghost");
				publicacion.setTitulo("Legalización de vivienda en 2 semanas");
				publicacion.setCorreo_usuario("yudeelito@gmail.com");
				publicacion.setPrecio(50f);
				publicacion.setContenido("Todos los trámites para legalizar viviendas, permutas, adjudicarse una vivienda, licencia de conStrucción y otros. Todos en un tiempo récord.");
				publicacion.setValoracion(5);
				publicacion.setCategoria("HOGAR");
				publicacion.setSubcategoria("PERMUTA");
				publicacion.setMoneda("CUC");
				Date fecha = new Date();
				publicacion.setFecha(fecha);
				publicacion.setVisitada(0);
				publicacion.setProvincia(13);
				break;
			}
			case "MISCELANEAS":
			{
				publicacion.setIdent("misghost");
				publicacion.setTitulo("ESTUDIO CREATIVO CHOY");
				publicacion.setCorreo_usuario("blanca.arrieta@nauta.cu");
				publicacion.setPrecio(50f);
				publicacion.setContenido("Somos un equipo de profesionales que trabajamos para satisfacer a los clientes más exigentes, con equipos de última generación y diseños innovadores.\n" +
						"Diseñamos su imagen, estilo e identidad, mediante el desarrollo y ejecución de ambientes y objetos gráficos para garantizar la publicidad de su negocio. Trabajamos  tanto para el sector privado como para el estatal.\n\n Servicios \n \n" +
						"Gigantografía en Lona  m²$ 45,00$\n\n" +
						"Gigantografía sobre PVC  m²$ 60,00$\n\n" +
						"Cartel en Vinilo sobre PVC  m²$ 50,00$ \n\n" +
						"Cartel Lumínico (dos caras)  m²$ 200,00$ \n\n" +
						"Vinilo Decorativo  m²$ 30,00$ 750,00\n\n" +
						"Rotulación de Vehículos  m²$ 30,00$ \n\n" +
						"Trofeo de Acrílico Grabado (h=40 cm)  u$ 30,00$\n\n" +
						"Trofeo de Acrílico Grabado (h=25 cm)  u$ 20,00$ \n\n" +
						"Trofeo de Acrílico Grabado (h=12 cm)  u$ 5,00$ \n\n" +
						"Llavero de Acrílico Grabado   u$ 1,50$ \n\n" +
						"Solapines   u$ 1,00$\n\n" +
						"Reconocimientos Plasticados  u$ 3,00$ \n\n" +
						"Reconocimientos en Cartulina  u$ 1,50$\n\n" +
						"Tarjetas de Presentación en Cartulina 100 u$ 10,00$\n\n" +
						"Cuños Modernos Autoentintados  u$ 15,00$\n\n" +
						"Cartas Menú Plasticadas (hoja)  u$ 3,00$ \n\n" +
						"Tablillero Simple (10 capacidades)  u$ 20,00$\n\n" +
						"Tablillero Doble (20 capacidades)  u$ 35,00$ \n\n" +
						"Tablillero de Pie (30 capacidades)  u$ 50,00$ \n");
				publicacion.setValoracion(5);
				publicacion.setCategoria("MISCELANEAS");
				publicacion.setSubcategoria("SERVICIOS");
				publicacion.setMoneda("CUC");
				Date fecha = new Date();
				publicacion.setFecha(fecha);
				publicacion.setVisitada(0);
				publicacion.setProvincia(13);
				break;

			}
		}

		return publicacion;
	}


 public int EntradasNuevasPorCategoria(String categoria)
 {
	 SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
	 Cursor MPUBS = base_de_datos.rawQuery("SELECT COUNT (nueva) FROM Publicaciones WHERE categoria=='"+ categoria +"' AND nueva=='0'", null);
	 if (MPUBS.moveToFirst()) {
		int cantidad = MPUBS.getInt(0);
		 return cantidad;
	 }
	 dbhelper.CerrarBasedeDatos();
	 return 0;
 }

	public int EntradasPorCategoria(String categoria)
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor MPUBS = base_de_datos.rawQuery("SELECT COUNT (ident) FROM Publicaciones WHERE categoria=='"+ categoria +"'", null);
		int cantidad = 0;
		if (MPUBS.moveToFirst()) {
			cantidad = MPUBS.getInt(0);
		}
		dbhelper.CerrarBasedeDatos();
		return cantidad;
	}
	public int BorrarTodasEntradasPorCategoria(String categoria)
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		base_de_datos.execSQL("DELETE FROM Publicaciones WHERE categoria=='" + categoria + "';");
		dbhelper.CerrarBasedeDatos();
		return 0;
	}

	public ArrayList<Publicacion> BuscarPublicaciones(String texto,ArrayList<Integer> filtrosprovincias,ArrayList<String> filtrossubcatsa,String categoria,float preciomax,float preciomin)
	{
		String queryprov = "";
		if(filtrosprovincias.size()>0)
			queryprov+="(";
		for (int i = 0; i < filtrosprovincias.size();i++)
		{

			if(i<filtrosprovincias.size()-1)
				queryprov+= "Publicaciones.provincia == '" + filtrosprovincias.get(i) + "' OR ";
			else
				queryprov+= "Publicaciones.provincia == '" + filtrosprovincias.get(i) + "')";
		}

		String querysubcate = "";
		if(filtrossubcatsa.size()>0)
			querysubcate+="(";
		for (int i = 0; i < filtrossubcatsa.size();i++)
		{
			if(i<filtrossubcatsa.size()-1)
				querysubcate+= "subcategoria == '" + filtrossubcatsa.get(i) + "' OR ";
			else
				querysubcate+= "subcategoria == '" + filtrossubcatsa.get(i) + "')";
		}
		boolean preciob=false;
		if(preciomin>-1 && preciomax>0 && preciomin < preciomax)
			preciob = true;


		ArrayList<Publicacion> resultbusqueda = new ArrayList<Publicacion>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		String querycompleta = "SELECT * FROM Publicaciones WHERE ";
		boolean needAND = false;
		if(queryprov.compareTo("")!=0)
		{
			querycompleta+= queryprov;
			needAND =true;

		}
		if(querysubcate.compareTo("")!=0)
		{
			if(needAND)
			querycompleta+= " AND " + querysubcate;
			else
			querycompleta+= querysubcate;
			needAND = true;

		}

			if(needAND)
			{
				querycompleta+= " AND categoria == '"+ categoria +"' AND ( titulo LIKE '%"+ texto +"%' OR contenido LIKE '%" +texto+"%' )";

			}
			else
			{
				querycompleta+= " categoria == '"+ categoria +"' AND ( titulo LIKE '%"+ texto +"%' OR contenido LIKE '"+texto+"%')" ;

			}


		if(preciob)
		{
			querycompleta+= " AND ( precio >= '"+ preciomin +"' AND precio <= '" +preciomax+"')";
		}

		//Log.d("Tag",querycompleta);
		Cursor busqueda = base_de_datos.rawQuery(querycompleta,null);
		//last_query_perform = querycompleta;
		if (busqueda.moveToFirst()) {
			do {
				Publicacion pub1 = CrearPublicacion(busqueda);
				resultbusqueda.add(pub1);
			} while (busqueda.moveToNext());
		}

		dbhelper.CerrarBasedeDatos();
		return resultbusqueda;

	}

	public ArrayList<Usuario> BuscarUsuarios(String texto, ArrayList<Integer> filtrosprovincias)
	{

		String queryprov = "";
		if(filtrosprovincias.size()>0)
			queryprov+="(";
		for (int i = 0; i < filtrosprovincias.size();i++)
		{

			if(i<filtrosprovincias.size()-1)
				queryprov+= "provincia == '" + filtrosprovincias.get(i) + "' OR ";
			else
				queryprov+= "provincia == '" + filtrosprovincias.get(i) + "')";
		}

		ArrayList<Usuario> resultbusqueda = new ArrayList<Usuario>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		String querycompleta = "SELECT correo,nombre,provincia FROM Usuarios WHERE ";
		boolean needAND = false;
		if(queryprov.compareTo("")!=0)
		{
			querycompleta+= queryprov;
			needAND =true;

		}
		if(needAND)
		{
			querycompleta+= " AND ( nombre LIKE '%"+ texto +"%')";
		}
		else
			querycompleta+= " ( nombre LIKE '%"+ texto +"%')" ;
		Cursor busqueda = base_de_datos.rawQuery(querycompleta,null);

		if (busqueda.moveToFirst()) {
			do {

				Usuario user1 = new Usuario();
				user1.setCorreo(busqueda.getString(0));
				user1.setNombre(busqueda.getString(1));
				user1.setProvincia(busqueda.getInt(2));
				resultbusqueda.add(user1);
			} while (busqueda.moveToNext());
		}

		dbhelper.CerrarBasedeDatos();
		return resultbusqueda;
	}

	public ArrayList<Publicacion> RankingPropuestas(String categoria)
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT * FROM Publicaciones WHERE categoria=='" + categoria + "' ORDER BY valoracion DESC ", null);
	//	last_query_perform = " SELECT * FROM Publicaciones WHERE categoria=='" + categoria + "' ORDER BY valoracion DESC " ;
		ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();

		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya más registros
			do {

				Publicacion pub1 = CrearPublicacion(c);
				publicaciones.add(pub1);

			} while (c.moveToNext());
		} else {
			publicaciones.add(PublicacionFantasma( categoria,null));

		}

		dbhelper.CerrarBasedeDatos();
		return publicaciones;
	}

	public ArrayList<Usuario> OrdenarUsuarios()
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT correo,nombre,provincia FROM Usuarios ORDER BY valoracion DESC ", null);
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		if (c.moveToFirst()) {
			do {

				Usuario user1 = new Usuario();
				user1.setCorreo(c.getString(0));
				user1.setNombre(c.getString(1));
				user1.setProvincia(c.getInt(2));
				usuarios.add(user1);
			} while (c.moveToNext());
		}

		dbhelper.CerrarBasedeDatos();
		return usuarios;

	}

	private Publicacion CrearPublicacion(Cursor cursor)
	{
		Publicacion pub1 = new Publicacion();
		pub1.setIdent(cursor.getString(0));
		pub1.setCorreo_usuario(cursor.getString(1));
		pub1.setTitulo(cursor.getString(2));
		pub1.setPrecio(cursor.getFloat(3));
		pub1.setContenido(cursor.getString(4));
		pub1.setValoracion(cursor.getFloat(5));
		pub1.setCategoria(cursor.getString(6));
		pub1.setSubcategoria(cursor.getString(7));
		pub1.setMoneda(cursor.getString(8));
		Date fecha = new Date(cursor.getLong(9));
		pub1.setFecha(fecha);
		pub1.setVisitada(cursor.getInt(10));
		pub1.setProvincia(cursor.getInt(11));
		//Log.d("13","" + cursor.getInt(13));
		pub1.setBusco(cursor.getInt(13));
		//Log.d("14","" + cursor.getInt(14));
		pub1.setPatrocinado(cursor.getInt(14));
		return pub1;
	}

	public String categoriaPorID(String id)
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT categoria FROM MisPublicaciones WHERE ident=='" +id+"'", null);
		String categoria="MISCELANEAS";
		if(c.moveToFirst())
			categoria = c.getString(0);
		else
		{
			Cursor ot = base_de_datos.rawQuery(" SELECT categoria FROM Publicaciones WHERE ident=='" +id+"'", null);
			if(ot.moveToFirst())
				categoria = ot.getString(0);
		}
		dbhelper.CerrarBasedeDatos();
			return categoria;

	}

	public void ActualizarRanking()
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT correo FROM Usuarios ORDER BY valoracion DESC", null);
		int posiciones=1;
		if(c.moveToFirst()) {
			do {
				ContentValues values = new ContentValues();
				values.put("ranking", posiciones);
				base_de_datos.update("Usuarios", values, "correo=='" + c.getString(0) + "'", null);
				posiciones++;
			} while (c.moveToNext());
		}
		dbhelper.CerrarBasedeDatos();

	}

	public int RankingCurrentUser()
	{
		String correo = PreferenceManager.getDefaultSharedPreferences(contexto).getString("correo","");
		int rank = 0;
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor c = base_de_datos.rawQuery(" SELECT ranking FROM Usuarios WHERE correo=='" + correo +"'", null);
		if(c.moveToNext())
		{
			rank = c.getInt(0);
		}
		return rank;
	}

	public int SinEnviar()
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor MPUBS = base_de_datos.rawQuery("SELECT COUNT (enviado) FROM MisPublicaciones WHERE enviado=='0'", null);
		Cursor MCOM = base_de_datos.rawQuery("SELECT COUNT (enviado) FROM MisComentarios WHERE enviado=='0'", null);
		int cantidad = 0;
		if (MPUBS.moveToFirst()) {
			cantidad += MPUBS.getInt(0);

		}
		if (MCOM.moveToFirst()) {
			cantidad += MCOM.getInt(0);

		}

		dbhelper.CerrarBasedeDatos();
		return cantidad;
	}

	public ArrayList<Publicacion> ListaporQuery(String query)
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor MPUBS = base_de_datos.rawQuery(query, null);
		ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
		if(MPUBS.moveToFirst())
		{
			do{
				publicaciones.add(CrearPublicacion(MPUBS));
			}while (MPUBS.moveToNext());
		}
		return publicaciones;
	}

	public int CantComentariosEntrada(String identificador)
	{
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor MPUBS = base_de_datos.rawQuery("SELECT COUNT (texto) FROM Comentarios WHERE publicacion=='"+ identificador +"'", null);
		if (MPUBS.moveToFirst()) {
			int cantidad = MPUBS.getInt(0);
			return cantidad;
		}
		dbhelper.CerrarBasedeDatos();
		return 0;
	}

	public float[] MinMaxporCategoria(String categoria)
	{
		float[] minmax = new float[2];
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor MPUBS = base_de_datos.rawQuery("SELECT MIN (precio) as minimo, MAX (precio) as maximo  FROM Publicaciones WHERE categoria=='"+ categoria +"'", null);
		if (MPUBS.moveToFirst()) {
			minmax[0] = MPUBS.getFloat(0);
			minmax[1] = MPUBS.getFloat(1);
			return minmax;
		}

		dbhelper.CerrarBasedeDatos();
		return null;
	}

	public ArrayList<Object> MisPublicacionesPublicadas(String id)
	{
		ArrayList<Object> mispublicacionespublicadas = new ArrayList<Object>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		int pos=0;
		Cursor MPUBS = base_de_datos.rawQuery("SELECT * FROM MisPublicaciones WHERE enviado=='1'", null);
		if(MPUBS.moveToFirst()){
			do {

				Publicacion pub1 = CrearPublicacion(MPUBS);
				if(pub1.getIdent().compareTo(id)==0)
					pos = mispublicacionespublicadas.size();
				mispublicacionespublicadas.add(pub1);

			} while (MPUBS.moveToNext());
		}
		mispublicacionespublicadas.add(pos);
		return mispublicacionespublicadas;
	}

	public ArrayList<Comentario> ComentariosAUser(String id)
	{
		ArrayList<Comentario> comments = new ArrayList<Comentario>();
		SQLiteDatabase base_de_datos = dbhelper.AbrirBasedeDatos();
		Cursor mcomments = base_de_datos.rawQuery("SELECT * FROM Comentarios INNER JOIN Publicaciones ON Comentarios.publicacion == Publicaciones.ident WHERE Publicaciones.correo == '"+ id +"'  ", null);
		if(mcomments.moveToFirst()){
			do {

				Comentario comment = new Comentario();
				comment.setEntrada(mcomments.getString(0));
				comment.setComentario(mcomments.getString(1));
				comment.setAutor(mcomments.getString(2));


				Date fecha = new Date(mcomments.getLong(3));
				comment.setFecha(fecha);
				comment.setRating(mcomments.getFloat(4));
				comment.setEnviado(mcomments.getInt(5));
				comment.setIdentificador(mcomments.getString(6));
				comment.setCategoria(mcomments.getString(7));
				comments.add(comment);



			} while (mcomments.moveToNext());
		}

		return comments;
	}

}

