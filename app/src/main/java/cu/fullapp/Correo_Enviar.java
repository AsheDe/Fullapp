package cu.fullapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.ParseException;

/**
 * Created by renier on 21/11/2016.
 */
public class Correo_Enviar {
    String emailPort;// gmail's smtp port
    int emailpueto;
    String smtpAuth;
    String starttls ;
    String emailHost ;

    String fromEmail;
    String fromPassword;
    List<String> toEmailList;
    String emailSubject;
    String emailBody;

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;
    Context context;
    SharedPreferences prefs;

    public Correo_Enviar(ArrayList<String> toEmailList, String emailSubject, String emailBody, Context context) {


            this.context = context;
            this.toEmailList = toEmailList;
            this.emailSubject = emailSubject;
            this.emailBody = emailBody;
            ////////-- estos datos deben guardarse en preferencias al iniciar
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
            this.fromEmail = context.getResources().getString(R.string.anakasparian); // prefs.getString("correo", "");
            this.fromPassword = context.getResources().getString(R.string.tytnetwork);        //prefs.getString("fckclave", "");
           /// String host =   "192.168.8.5" //prefs.getString("serversend", "");
           // String[] hostconf = host.split(":");
            emailHost = "smtp.nauta.cu"; //hostconf[0];
            emailPort = "25" ; //hostconf[1];
            smtpAuth = "true";
            String protocol =  "smtp" ; // prefs.getString("protocolo", "");
           // starttls = "true";
      /* this.fromEmail = "yudel@stgo.sasa.co.cu";
        this.fromPassword = "aalicia";

        emailPort = "587";// gmail's smtp port
        smtpAuth = "true";
        starttls = "false";
        emailHost = "192.168.8.5";
         emailProperties.put("mail.smtp.starttls.enable", starttls);*/

///-------------//////////////
            emailProperties = System.getProperties();
            emailProperties.put("mail.smtp.port", emailPort);
            emailProperties.put("mail.smtp.auth", smtpAuth);

        if(prefs.getString("usessl","None").compareTo("STARTTLS")==0)
        {
            emailProperties.put("mail.smtp.starttls.enable", "true");
        }
        else if(prefs.getString("usessl","None").compareTo("SSL/TLS")==0)
        {
            emailProperties.put("mail.smtp.socketFactory.port", emailPort);
            emailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            emailProperties.put("mail.smtp.socketFactory.fallback", "false");

        }

    }
    public Correo_Enviar(String toEmailList, String emailSubject, String emailBody, Context context) {


        this.context = context;
        this.toEmailList = new ArrayList<String>();
        this.toEmailList.add(toEmailList) ;


        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        ////////-- estos datos deben guardarse en preferencias al iniciar
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
      /*  this.fromEmail = prefs.getString("correo", "");
        this.fromPassword = prefs.getString("fckclave", "");
        String host = prefs.getString("serversend", "");
        String[] hostconf = host.split(":");
        emailHost = hostconf[0];
        emailPort = hostconf[1];
        smtpAuth = "true";
        String protocol = prefs.getString("protocolo", "");*/

        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.fromEmail = context.getResources().getString(R.string.anakasparian); // prefs.getString("correo", "");
        this.fromPassword = context.getResources().getString(R.string.tytnetwork);        //prefs.getString("fckclave", "");
        /// String host =   "192.168.8.5" //prefs.getString("serversend", "");
        // String[] hostconf = host.split(":");
        emailHost = "smtp.nauta.cu"; //hostconf[0];
        emailPort = "25" ; //hostconf[1];
        smtpAuth = "true";
        emailpueto = 25 ;
        String protocol =  "smtp" ;

      /* this.fromEmail = "yudel@stgo.sasa.co.cu";
        this.fromPassword = "aalicia";

        emailPort = "587";// gmail's smtp port
        smtpAuth = "true";
        starttls = "false";
        emailHost = "192.168.8.5";
         emailProperties.put("mail.smtp.starttls.enable", starttls);*/

///-------------//////////////


        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
       if(prefs.getString("usessl","None").compareTo("STARTTLS")==0)
       {
           emailProperties.put("mail.smtp.starttls.enable", "true");
       }
       else if(prefs.getString("usessl","None").compareTo("SSL/TLS")==0)
        {
            emailProperties.put("mail.smtp.socketFactory.port", emailPort);
            emailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            emailProperties.put("mail.smtp.socketFactory.fallback", "false");

        }

    }


    public MimeMessage createEmailMessage() throws AddressException,
            MessagingException, UnsupportedEncodingException, java.text.ParseException {

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);


      //  String maildef =  context.getString(R.string.anakasparian);  //prefs.getString("correo",context.getResources().getString(R.string.anakasparian));
        emailMessage.setFrom(new InternetAddress(fromEmail));
        String replyto = prefs.getString("correo",context.getResources().getString(R.string.anakasparian));

        emailMessage.setSender(new InternetAddress(replyto));
        emailMessage.setReplyTo(new Address[] {new InternetAddress(replyto)} );
        for (String toEmail : toEmailList) {
            emailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail));
        }

        emailMessage.setSubject(emailSubject);



       // emailMessage.setContent(emailBody, "text/html");// for a html email
        emailMessage.setText(emailBody);// for a text email
         return emailMessage;
    }

    public void Enviar_Correo() throws AddressException, MessagingException {


            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailHost, fromEmail, fromPassword);
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();





    }



}
