package cu.fullapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.util.Date;

public class DB_Helper extends SQLiteOpenHelper {


	public String crear_tposts =
			"CREATE TABLE Publicaciones (ident TEXT, correo TEXT, titulo TEXT, precio FLOAT, contenido TEXT, valoracion FLOAT, categoria TEXT, subcategoria TEXT, moneda TEXT, fecha REAL, nueva INTEGER,provincia INTEGER,cantcomentarios INTEGER, busco INTEGER, patrocinado INTEGER)";

	public String crear_tusuarios =
			"CREATE TABLE Usuarios (correo TEXT, nombre TEXT, telefono INTEGER, provincia INTEGER, nolicencia TEXT, actividad_lic TEXT, cant_post INTEGER, valoracion FLOAT, fechar REAL,ranking INTEGER,votos INTEGER )";

	/*public String crear_tmiusuario =
			"CREATE TABLE MiUsuario (correo TEXT, nombre TEXT, telefono INTEGER, provincia INTEGER, nolicencia TEXT, actividad_lic TEXT, cant_post INTEGER, valoracion FLOAT, fechar REAL,ranking INTEGER )";
*/
	public String crear_tcomentarios =
			"CREATE TABLE Comentarios (publicacion TEXT, texto TEXT, correo_autor TEXT, fechacom REAL,valoracion FLOAT )";

	public String crear_tmposts =
			"CREATE TABLE MisPublicaciones (ident TEXT, correo TEXT, titulo TEXT, precio FLOAT, contenido TEXT, valoracion FLOAT, categoria TEXT, subcategoria TEXT, moneda TEXT, fecha REAL, nueva INTEGER,provincia INTEGER, enviado INTEGER, busco INTEGER, patrocinado INTEGER)";

	public String crear_tmcomentarios =
			"CREATE TABLE MisComentarios (publicacion TEXT, texto TEXT, correo_autor TEXT, fechacom REAL,valoracion FLOAT,enviado INTEGER,ident TEXT,categoria TEXT)";

	Context contexto;

	public DB_Helper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub

		contexto = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

		arg0.execSQL("DROP TABLE IF EXISTS 'Publicaciones'");
		arg0.execSQL("DROP TABLE IF EXISTS 'Usuarios'");
		//arg0.execSQL("DROP TABLE IF EXISTS 'MiUsuario'");
		arg0.execSQL("DROP TABLE IF EXISTS 'Comentarios'");
		arg0.execSQL("DROP TABLE IF EXISTS 'MisPublicaciones'");
		arg0.execSQL("DROP TABLE IF EXISTS 'MisComentarios'");

		arg0.execSQL(crear_tposts);
		arg0.execSQL(crear_tusuarios);
		//arg0.execSQL(crear_tmiusuario);
		arg0.execSQL(crear_tcomentarios);
		arg0.execSQL(crear_tmposts);
		arg0.execSQL(crear_tmcomentarios);

		arg0.execSQL("INSERT INTO Usuarios (correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking) "
				+ "VALUES ('" + "yudeelito@gmail.com" + "', '" + "Yudel Martínez (CREADOR)" + "', '" + 5 + "', '" + "A49813" + "', '" + "PROGRAMADOR" + "', '"
				+ "13" + "', '" + 55954611 + "', '" + 1 + "', '" + 0 + "', '" + 0 + "');");


		arg0.execSQL("INSERT INTO Usuarios (correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking) "
				+ "VALUES ('" + "guanchon@nauta.cu" + "', '" + "GUANCHO" + "', '" + 5 + "', '" + "21165" + "', '" + "Gestor" + "', '"
				+ "13" + "', '" + 58022690 + "', '" + 1 + "', '" + 0 + "', '" + 0 + "');");

		arg0.execSQL("INSERT INTO Usuarios (correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking) "
				+ "VALUES ('" + "martin@yotellevocuba.com" + "', '" + "MARTÍN PROENZA" + "', '" + 5 + "', '" + "478S5" + "', '" + "TRANSPORTISTA PRIVADO" + "', '"
				+ "11" + "', '" + 4444444 + "', '" + 1 + "', '" + 0 + "', '" + 0 + "');");

		arg0.execSQL("INSERT INTO Usuarios (correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking) "
				+ "VALUES ('" + "blanca.arrieta@nauta.cu" + "', '" + "Abel Orúe Choy" + "', '" + 5 + "', '" + "55954" + "', '" + "Grabador de Objetos" + "', '"
				+ "13" + "', '" + 53736539 + "', '" + 1 + "', '" + 0 + "', '" + 0 + "');");

		Soporte_Elementos_Comunes.cambiar_preferencia_encritada("ac42txf",new Date().getTime()+"",contexto);
		Soporte_Elementos_Comunes.cambiar_preferencia_encritada("db11k9t","0",contexto);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

		String agregar_columna_BUSCO_pUB = " ALTER TABLE Publicaciones ADD COLUMN busco INTEGER" ;
		String agregar_columna_BUSCO_Mpub = " ALTER TABLE MisPublicaciones ADD COLUMN busco INTEGER" ;
		String agregar_columna_PATROCINADO_pUB = " ALTER TABLE Publicaciones ADD COLUMN patrocinado INTEGER" ;
		String agregar_columna_PATROCINADO_Mpub = " ALTER TABLE MisPublicaciones ADD COLUMN patrocinado INTEGER" ;

		arg0.execSQL("INSERT INTO Usuarios (correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking) "
				+ "VALUES ('" + "martin@yotellevocuba.com" + "', '" + "MARTÍN PROENZA" + "', '" + 5 + "', '" + "478S5" + "', '" + "TRANSPORTISTA PRIVADO" + "', '"
				+ "11" + "', '" + 4444444 + "', '" + 1 + "', '" + 0 + "', '" + 0 + "');");

		arg0.execSQL("INSERT INTO Usuarios (correo, nombre,valoracion,nolicencia,actividad_lic, provincia,telefono,cant_post,fechar,ranking) "
				+ "VALUES ('" + "blanca.arrieta@nauta.cu" + "', '" + "Abel Orúe Choy" + "', '" + 5 + "', '" + "55954" + "', '" + "Grabador de Objetos" + "', '"
				+ "13" + "', '" + 53736539 + "', '" + 1 + "', '" + 0 + "', '" + 0 + "');");


		arg0.execSQL(agregar_columna_BUSCO_pUB);
		arg0.execSQL(agregar_columna_BUSCO_Mpub);
		arg0.execSQL(agregar_columna_PATROCINADO_pUB);
		arg0.execSQL(agregar_columna_PATROCINADO_Mpub);

	}
	public SQLiteDatabase AbrirBasedeDatos()
	{
		return this.getWritableDatabase();
	}
	public void CerrarBasedeDatos()
{
	this.close();
}


	
}
