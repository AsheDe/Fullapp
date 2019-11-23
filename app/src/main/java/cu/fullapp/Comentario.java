package cu.fullapp;

import java.util.Date;

/**
 * Created by renier on 29/10/2016.
 */
public class Comentario {
    private String autor;
    private String entrada;
    private  String comentario;
    private float rating;
    private  Date fecha;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    private String categoria;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void CrearIdentificador()
    {
        String id = "";
        id = this.autor + "";
        id +="$";
        fecha = new Date();
        id += fecha.getTime() + "";
        this.identificador = id;
    }


    private String identificador;
    public long getMomento() {
        return momento;
    }

    public void setMomento(long momento) {
        this.momento = momento;
        fecha = new Date(momento);
    }

    private long momento;
    public int isEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
    }

    private int enviado;
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
        this.momento = fecha.getTime();
    }

    public String getFechaConfigurada() {
        int dia = fecha.getDate();
        int mes = fecha.getMonth()+1;
        int year = fecha.getYear()+1900;
        int hora = fecha.getHours();
        int minuto = fecha.getMinutes();
        int segundo = fecha.getSeconds();
       // fecha.
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
        String tureturn = dia+"-"+mes+"-"+year + " " + hora+ ":"+minutoconv + "," + segundo;
        return tureturn;
       /* Date ahora = new Date();
       // long diferencia = ahora - fecha.getTime();
       // Date dif = new Date(diferencia);

        String tureturn = "Hace un tiempo";
        if(ahora.getYear() - fecha.getYear() < 0 )
        {
            if(ahora.getMonth() - fecha.getMonth() < 0)
            {
                if(ahora.getDate() - fecha.getDate() < 0)
                {
                    if(ahora.getHours() - fecha.getHours() < 0)
                    {
                        if(ahora.getMinutes() - fecha.getMinutes() < 0)
                        {
                            tureturn = "Hace menos de un minuto";
                        }
                        else
                        {
                            tureturn = "Hace " + (ahora.getMinutes() - fecha.getMinutes()) + " minutos";
                        }
                    }
                    else
                    {
                        tureturn = "Hace " + (ahora.getHours() - fecha.getHours()) + " horas";
                    }
                }
                else
                {
                    tureturn = "Hace " + (ahora.getDate() - fecha.getDate()) + " dias";
                }

            }
            else
            {
                tureturn = "Hace " + (ahora.getMonth() - fecha.getMonth()) + " meses";
            }
        }
        else
        {
                tureturn = "Hace " + (ahora.getYear() - fecha.getYear()) + " años";
        }
        return tureturn ;*/
    }



}
