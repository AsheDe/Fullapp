package cu.fullapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.sun.mail.imap.YoungerTerm;

import org.w3c.dom.Element;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.ParseException;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.DateTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.HeaderTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

/**
 * Created by renier on 21/11/2016.
 */
public class Correo_Leer extends javax.mail.Authenticator {

    private Session session;
    private Store store;
    private Message[] mensajes;
    private boolean propcorrectas;
    private boolean errorconnect;
    private String msgerror = "";
    private ArrayList<String> textoscorreos;
    private Folder folder;
    ArrayList<String> asuntos = new ArrayList<String>();
    SharedPreferences prefs;
    Context contexto;
    int cantidadactual;
    int cantadescargar;
    int iniciodedescargas=0;
    String mcategoria;


    public boolean isProcesoregistro() {
        return procesoregistro;
    }


    String asuntomailregistro = "Siguelo Intentando";
    boolean procesoregistro = false;

    public Correo_Leer(Context context, String categoria) {
        contexto = context;

        propcorrectas = true;
        errorconnect = false;
        /// ESTOS DATOS DEBEN VENIR DE LAS PREFERENCIAS DE USUARIO
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        mcategoria = categoria;
      /*  String user = prefs.getString("correo", "");
        String password = prefs.getString("fckclave", "");
        String host = prefs.getString("serverreceive", "");
        String[] hostconf = host.split(":");
        host = hostconf[0];
        int puerto = Integer.parseInt(hostconf[1]);
        String protocol = prefs.getString("protocolo", "");*/


        String user = contexto.getResources().getString(R.string.anakasparian);  /// prefs.getString("correo", "");
        String password = contexto.getResources().getString(R.string.tytnetwork); // prefs.getString("fckclave", "");
        String host = "imap.nauta.cu"; //contexto.getResources().getString(R.string.anakasparian); //prefs.getString("serverreceive", "");
        //String[] hostconf = host.split(":");
        //host =  hostconf[0];
        int puerto = 143; //Integer.parseInt(hostconf[1]);
        String protocol = "imap"; ///prefs.getString("protocolo", "");


        Properties props = System.getProperties();
        if (props == null) {
            propcorrectas = false;
        } else {
            props.setProperty("mail.store.protocol", protocol);
            props.setProperty("mail.transport.protocol", protocol);

            if (prefs.getString("usessl", "None").compareTo("STARTTLS") == 0 || prefs.getString("usessl", "None").compareTo("SSL/TLS") == 0) {
                props.put("mail.smtp.starttls.enable", "false");
                props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.setProperty("mail.pop3.socketFactory.fallback", "false");

                props.setProperty("mail.pop3.port", puerto + "");
                props.setProperty("mail.pop3.socketFactory.port", puerto + "");
            }

        }
        try {
            session = Session.getDefaultInstance(props, null);
            store = session.getStore(protocol);
            // store.connect(mailhost,user, password);
            store.connect(host, puerto, user, password);

         /*  folder = store.getFolder("Inbox");

            if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0){
                Folder nuevacarpeta =  store.getFolder("Inbox."+categoria);
                nuevacarpeta.create(Folder.HOLDS_MESSAGES);
                nuevacarpeta.open(Folder.READ_ONLY);
                nuevacarpeta.close(false);
            }
            else
            {
                errorconnect = true;
                msgerror = "No permite crear carpetas imap";
            }*/
           /* String activacion = Soporte_Elementos_Comunes.obtener_preferencia_encriptada("kr9t4br", "No registrado", contexto);
            if (activacion.compareTo("No registrado") == 0) {
                asuntomailregistro = "Debes registrarte para realizar esta acción";
                procesoregistro = true;
            }*/


            //      else if (activacion.compareTo("USUARIO REGISTRADO") == 0
            //           ||activacion.compareTo("APLICACIÓN ACTIVADA") == 0)
            //  {
            // Pattern patron = Pattern.compile("facil-app-\\.[publicaciones\\$]|[miscelaneas]|[hogar]|[transporte]|[celular]|[computadora$]\\.@googlegroups.com");
            //SearchTerm fromStringTerm = new HeaderTerm("Sender", "facil-app-\\^publicaciones\\$\\|\\^miscelaneas\\$\\|\\^hogar\\$\\|\\^transporte\\$\\|\\^celular\\$\\|\\^computadora\\$@googlegroups.com");
            // ] \| [facil-app-celular@googlegroups.com
            // Pattern patron = Pattern.compile("facil-app-.+[publicaciones$|computadora$|celular$|transporte$|hogar$|miscelaneas$]@googlegroups.com");
            //Log.d("patron",patron.toString());
            //  SearchTerm fromStringTerm = new HeaderTerm("Sender","facil-app-.+[publicaciones$|computadora$|celular$|transporte$|hogar$|miscelaneas$]@googlegroups.com");
             /*   SearchTerm [] fromgroup = new SearchTerm[]{
                new HeaderTerm("Sender","facil-app-publicaciones@googlegroups.com"),
                new HeaderTerm("Sender","facil-app-computadora@googlegroups.com"),
                new HeaderTerm("Sender","facil-app-celular@googlegroups.com"),
                new HeaderTerm("Sender","facil-app-transporte@googlegroups.com"),
                new HeaderTerm("Sender","facil-app-hogar@googlegroups.com"),
                new HeaderTerm("Sender","facil-app-miscelaneas@googlegroups.com")};*/

           /* Date fecha = new Date(2017,6,6);

            SimpleDateFormat df1 = new SimpleDateFormat( "yyyy-MM-dd-HH:mm" );
            String dt="2017-08-08-08:40";

            try {
               fecha = df1.parse(dt);
            }
            catch (Exception e)
            {
                errorconnect = true;
                msgerror = e.getMessage();
            }*/


            //  mensajes =  ((UIDFolder) folder).getMessagesByUID( 50000147317L ,50000147355L);


            //((UIDFolder) folder).
           /* SearchTerm [] fromgroup = new SearchTerm[]{
                    //new ReceivedDateTerm(DateTerm.GT,fecha),
                   // new SubjectTerm("FACIL-"+categoria.toUpperCase()+"-6"),
                    //new YoungerTerm((int) TimeUnit.MINUTES.toSeconds(300000))
                    new FromTerm(new InternetAddress("yudel@stgo.sasa.co.cu"))

            };*/

               /* if(mensajes.length>0) {
                    for (Message msg : mensajes) {
                         msg.setFlag(Flags.Flag.DELETED,true);
                    }

              //  }


            }*/
        } catch (NoSuchProviderException e) {
            errorconnect = true;
            msgerror = e.getMessage();
        } catch (MessagingException e) {
            errorconnect = true;
            msgerror = e.getMessage();
        }


    }

    public void CopiarMensajes() {
       try{
           folder = store.getFolder("Inbox");
           Message [] mensajesCopiar ;
           Folder carpetacat = store.getFolder("Inbox."+mcategoria);
           carpetacat.open (Folder.READ_WRITE);

           folder.open (Folder.READ_WRITE);
           //mensajesCopiar[0] = folder.getMessage(0);

          // int provincia = Integer.valueOf( prefs.getInt("prov", 13));
           SearchTerm fromStringTerm = new SubjectTerm("FACIL-" + mcategoria );
           mensajesCopiar = folder.search(fromStringTerm);

           folder.copyMessages(mensajesCopiar, carpetacat);


           for (int i =0; i < mensajesCopiar.length ; i++)
           {
               mensajesCopiar[i].setFlag(Flags.Flag.DELETED,true);
           }

           folder.close(true);
           carpetacat.close(false);

       }

       catch (NoSuchProviderException e) {
           errorconnect = true;
           msgerror = e.getMessage();
       } catch (
               MessagingException e
               )

       {
           errorconnect = true;
           msgerror = e.getMessage();
       }
    }

    public void CantidaddeMensajes() {
        try {

            folder = store.getFolder("Inbox."+mcategoria);
            folder.open (Folder.READ_ONLY);
            int conteo = prefs.getInt("contadordecorreos" + mcategoria, 1);
            int limpiezas = prefs.getInt("limpieza"+mcategoria,0);
            cantidadactual = folder.getMessageCount();

            SearchTerm searchlimpieza = new SubjectTerm("limpieza" + mcategoria);
            Message[] contlimpiezas = folder.search(searchlimpieza);

            int diflimpiezas = contlimpiezas.length -limpiezas ;
            boolean countchange = false;

            // hay nuevas limpiezas
            if( diflimpiezas > 0 )
            {
                conteo -= diflimpiezas * 100 ;
                countchange = true;
            }
            if( diflimpiezas < 0 )
            {
                conteo -= ((10+diflimpiezas) * 100);
                countchange = true;
            }
            if(conteo < 0 )
            {
                conteo = 1;
                countchange = true;
            }

            if(countchange)
            {
                prefs.edit().putInt("contadordecorreos" + mcategoria,conteo).commit();
                prefs.edit().putInt("limpieza" + mcategoria,contlimpiezas.length).commit();

            }
            //Log.d("clean",conteo + "" + contlimpiezas.length + "" + + diflimpiezas + "limpieza" + mcategoria  ) ;

            if (cantidadactual >= conteo) {
                Message[] mensajestemporal = folder.getMessages(conteo, cantidadactual);
                if (prefs.getBoolean("todacuba", true)) {
                    SearchTerm fromStringTerm = new SubjectTerm("FACIL-" + mcategoria);
                    mensajes = folder.search(fromStringTerm, mensajestemporal);
                    ////////// descargar correo solo de la provincia del usuario

                } else   ////////// descargar correo  de todas las provincias
                {
                    int provincia = Integer.parseInt(prefs.getString("prov", "13"));
                    SearchTerm fromStringTerm = new SubjectTerm("FACIL-" + mcategoria + "-" + provincia);
                    mensajes = folder.search(fromStringTerm, mensajestemporal);
                }
            }
        } catch (NoSuchProviderException e) {
            errorconnect = true;
            msgerror = e.getMessage();
        } catch (
                MessagingException e
                )

        {
            errorconnect = true;
            msgerror = e.getMessage();
        }

    }

    public Message[] getMensajes() {
        return mensajes;
    }

    public ArrayList<Object> Actualizaciones() throws MessagingException
    {
        ArrayList<Object> updates = new ArrayList<Object>();

        for (int i=0;i<textoscorreos.size();i++) {

            String correo = textoscorreos.get(i);

            try {
                String textcoded = correo.split("<firma>")[0] ;
               // String  finalmail = Soporte_Elementos_Comunes.Desencriptar(textcoded,contexto);

                String[] temporal = textcoded.split("</tp>");
                String todaspubs = "", todoscomments = "", todosusers = "";
                if (temporal.length > 0) {
                    todosusers = temporal[0];
                    todaspubs = temporal[1];
                    todoscomments = temporal[2];
                }

                String[] publicacionescrudas = todaspubs.split("</sp>");
                if(publicacionescrudas[0].compareTo("<null>")!=0) {
                    for (int j = 0; j < publicacionescrudas.length; j++) {
                        //String[] publicacioncaract = publicacionescrudas[j].split("</p\\d+>");
                        Publicacion publicacion = CrearPublicacion(publicacionescrudas[j]);
                        updates.add(publicacion);

                    }
                }

                String[] comentarioscrudos = todoscomments.split("</sc>");
                if(comentarioscrudos[0].compareTo("<null>")!=0) {
                for (int k = 0; k < comentarioscrudos.length; k++) {
                    // String[] comentcaract = comentarioscrudos[k].split("</c\\d+>");
                    Comentario comentario = CrearComentario(comentarioscrudos[k]);
                    updates.add(comentario);
                }
                }
                String[] usuarioscrudos = todosusers.split("</su>");
                if(usuarioscrudos[0].compareTo("<null>")!=0) {
                    for (int l = 0; l < usuarioscrudos.length; l++) {
                        // String[] usercaract = usuarioscrudos[l].split("</u\\d+>");
                        Usuario user = CrearUsuario(usuarioscrudos[l]);
                        updates.add(user);

                    }
                }
            }
              catch (Exception except)
              {
                  updates.add(except.getMessage());
              }
        }
        folder.close(true);
        store.close();

        int conteo = prefs.getInt("contadordecorreos"+mcategoria, 1);
        prefs.edit().putInt("contadordecorreos"+mcategoria,conteo+cantadescargar+iniciodedescargas).commit();


            return updates;

    }

    public Usuario CrearUsuario(String usuario)
    {
        Usuario user1= new Usuario();

        String [] caracteristicas = usuario.split("</u\\d+>");

        for (String caract:  caracteristicas) {

            if (caract.startsWith("<u1>")) {

                caract = caract.replaceFirst("<u1>", "");
                user1.setNombre(caract);
                continue;
            }
            if (caract.startsWith("<u2>")) {

                caract = caract.replaceFirst("<u2>", "");
                user1.setCorreo(caract);
                continue;
            }
            if (caract.startsWith("<u3>")) {

                caract = caract.replaceFirst("<u3>", "");
                user1.setTelefono(Integer.parseInt(caract));
                continue;
            }
            if (caract.startsWith("<u4>")) {

                caract = caract.replaceFirst("<u4>", "");
                user1.setProvincia(Integer.parseInt(caract));
                continue;
            }
            if (caract.startsWith("<u5>")) {

                caract = caract.replaceFirst("<u5>", "");
                user1.setActividad_lic(caract);
                continue;
            }
            if (caract.startsWith("<u6>")) {

                caract = caract.replaceFirst("<u6>", "");
                user1.setNolicencia(caract);
                continue;
            }
            if (caract.startsWith("<u7>")) {

                caract = caract.replaceFirst("<u7>", "");
                user1.setValoracion(Float.parseFloat(caract));
                continue;
            }
            if (caract.startsWith("<u8>")) {

                caract = caract.replaceFirst("<u8>", "");
                user1.setCant_post(Integer.parseInt(caract));
                continue;
            }
            if (caract.startsWith("<u9>")) {

                caract = caract.replaceFirst("<u9>", "");
                user1.setCantidad_votos(Integer.parseInt(caract));
                continue;
            }
            if (caract.startsWith("<u10>")) {

                caract = caract.replaceFirst("<u10>", "");
                user1.setFechar(new Date(Long.parseLong(caract)));
                continue;
            }
        }
        return user1;
    }
    public Comentario CrearComentario(String comentario)
    {
            Comentario comentario1= new Comentario();
        String[] caracteristicas = comentario.split("</c\\d+>");
        for (String caract:  caracteristicas) {

            if (caract.startsWith("<c1>")) {

                caract = caract.replaceFirst("<c1>", "");
                comentario1.setEntrada(caract);

                continue;
            }
            if (caract.startsWith("<c2>")) {
                caract = caract.replaceFirst("<c2>", "");
                comentario1.setComentario(caract);

                continue;
            }
            if (caract.startsWith("<c3>")) {
                caract = caract.replaceFirst("<c3>", "");
                comentario1.setAutor(caract);

                continue;
            }
            if (caract.startsWith("<c4>")) {
                caract = caract.replaceFirst("<c4>", "");
                comentario1.setMomento(Long.parseLong(caract));

                continue;
            }
            if (caract.startsWith("<c5>")) {
                caract = caract.replaceFirst("<c5>", "");
                comentario1.setRating(Float.parseFloat(caract));

                continue;
            }

        }
            return comentario1;
    }


    public Publicacion CrearPublicacion(String  publicacion)
    {
        Publicacion publicacion1= new Publicacion();
        String[] caracteristicas = publicacion.split("</p\\d+>");
      for (String caract:  caracteristicas)
      {

          if(caract.startsWith("<p1>"))
          {

              caract =   caract.replaceFirst("<p1>","");
              publicacion1.setIdent(caract);

              continue;
          }
          if(caract.startsWith("<p2>"))
          {
              caract =  caract.replaceFirst("<p2>","");
              publicacion1.setCorreo_usuario(caract);

              continue;
          }
          if(caract.startsWith("<p3>"))
          {
              caract =  caract.replaceFirst("<p3>","");
              publicacion1.setTitulo(caract);

              continue;
          }
          if(caract.startsWith("<p4>"))
          {
              caract = caract.replaceFirst("<p4>","");
              publicacion1.setPrecio(Float.parseFloat(caract) );

              continue;
          }
          if(caract.startsWith("<p5>"))
          {
              caract = caract.replaceFirst("<p5>","");
              publicacion1.setContenido(caract);

              continue;
          }
          if(caract.startsWith("<p6>"))
          {
              caract = caract.replaceFirst("<p6>","");
              publicacion1.setCategoria(caract);

              continue;
          }
          if(caract.startsWith("<p7>"))
          {
              caract =caract.replaceFirst("<p7>","");
              publicacion1.setSubcategoria(caract);

              continue;
          }
          if(caract.startsWith("<p8>"))
          {
              caract =caract.replaceFirst("<p8>","");
              publicacion1.setMoneda(caract);

              continue;
          }
          if(caract.startsWith("<p9>"))
          {
              caract = caract.replaceFirst("<p9>","");
             long moment = Long.parseLong(caract);
             // Date fecha = new Date(moment);
              publicacion1.setMomento(moment);

              continue;
          }
          if(caract.startsWith("<p10>"))
          {
              caract = caract.replaceFirst("<p10>","");
              publicacion1.setProvincia(Integer.parseInt(caract));
               continue;
          }
          if(caract.startsWith("<p11>"))
          {
              caract = caract.replaceFirst("<p11>","");
              publicacion1.setBusco(Integer.parseInt(caract));
              continue;
          }
          if(caract.startsWith("<p12>"))
          {
              caract = caract.replaceFirst("<p12>","");
              publicacion1.setPatrocinado(Integer.parseInt(caract));
              continue;
          }
      }

            return publicacion1;
    }
    public int cantidad(){return (mensajes==null)? 0:mensajes.length;}

   /* public void setMensajes(Message[] mensajes) {
        this.mensajes = mensajes;
    }*/

    public boolean isErrorconnect() {
        return errorconnect;
    }

   /* public void setErrorconnect(boolean errorconnect) {
        this.errorconnect = errorconnect;
    }*/

    public String getMsgerror() {
        return msgerror;
    }

   /* public void setMsgerror(String msgerror) {
        this.msgerror = msgerror;
    }*/
    public void ExtraerTextoCorreos(int cuantoscorreos,int inicio_actualizaciones)throws MessagingException,IOException
    {
        cantadescargar = cuantoscorreos;
        iniciodedescargas = inicio_actualizaciones;
     //   Log.d("body", cantadescargar + " - " +  mensajes.length );

        if(cantadescargar <= mensajes.length && inicio_actualizaciones < mensajes.length )
        {
         //   Log.d("body", "entro" );
            textoscorreos = new ArrayList<String>();
        for (int i = inicio_actualizaciones; i < cantadescargar ;i++)
        {
            Message msg = mensajes[i];


            if (msg.isMimeType("text/*")) {

                BufferedInputStream bis = new BufferedInputStream(msg.getInputStream());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();


                while (true) {
                    int c = bis.read();
                    if (c == -1) {
                        break;
                    }
                    baos.write(c);
                }

                String bodytext = new String(baos.toByteArray());
                //Log.d("bodyda", bodytext.toString() );
                textoscorreos.add(bodytext);

                }
            }
        }

    }






}

