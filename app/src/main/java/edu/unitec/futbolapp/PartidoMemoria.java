package edu.unitec.futbolapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivx1 on 09/11/2015.
 */
public class PartidoMemoria implements Serializable{
    private ArrayList<AccionPartido> Acciones;
    private ArrayList<CambiosPartido> Cambios;
    private ArrayList<PasePartido> Pases;
    private ArrayList<FaltaPartido> Faltas;
    private ArrayList<JugadorPartido> Jugadores;

    public PartidoMemoria(){
        Acciones = new ArrayList();
        Cambios = new ArrayList();
        Pases = new ArrayList();
        Faltas = new ArrayList();
        Jugadores = new ArrayList();
    }

    public void initJugadoresCancha(List<Jugador> EnCancha){
        for(Jugador tmp: EnCancha){
            Jugadores.add(new JugadorPartido(0,tmp,""));
        }
    }

    public void updateTiempoJuegoJugador(Chronometer Cronometro){
        for (JugadorPartido tmp: Jugadores){
            if (tmp.getTIME().equals(""))
                tmp.setTIME(Cronometro.getDifference(tmp.getTiempoEntrada()));
        }
    }

    public void CambioJugador(Jugador Entra,Jugador Sale, Chronometer Cronometro){
        for (JugadorPartido tmp: Jugadores){
            if (tmp.jugador.getIdJugador() == Sale.getIdJugador()){
                tmp.setTIME(Cronometro.getTime());
                break;
            }
        }
        Jugadores.add(new JugadorPartido(Cronometro.getTimePassed(),Entra,""));
        Cambios.add(new CambiosPartido(Entra.getIdJugador(),Sale.getIdJugador(),Cronometro.getTime()));
    }

    public void PaseJugador(Pase Pa,TipoPase TipoPa,Jugador Envia,Jugador Recibe, Chronometer Cronometro){
        if (Recibe != null)
            Pases.add(new PasePartido(Pa,TipoPa,Envia.getIdJugador(),Recibe.getIdJugador(),Cronometro.getTime()));
        else
            Pases.add(new PasePartido(Pa,TipoPa,Envia.getIdJugador(),-1,Cronometro.getTime()));
    }

    public void FaltaJugador(Falta Fa,Jugador Player,int Cometida,int Tarjeta, Chronometer Cronometro){
        // Cometida = 0; NO COMETIO
        // Tarjeta = 0; NO TARJETA
        // Tarjeta = 1; AMARILLA
        // Tarjeta = 2; ROJA
        Faltas.add(new FaltaPartido(Fa,Player.getIdJugador(),Cometida,Tarjeta,Cronometro.getTime()));
        PartidoCanchaActivity.ACCION_PRINCIPAL = null;
        PartidoCanchaActivity.ENVIA_PASE = null;
    }

    public void AccionJugador(Accion Action,Jugador Player,Chronometer Cronometro){
        Acciones.add(new AccionPartido(Action,Player.getIdJugador(),Cronometro.getTime()));
    }

    public int getCountAllPasesExitosos(){
        int retVal = 0;
        for (PasePartido tmp : Pases){
            if (tmp.getIdJugadorRecibe() != -1)
                retVal++;
        }
        return retVal;
    }

    public int getCountAllPasesFallidos(){
        int retVal = 0;
        for (PasePartido tmp: Pases){
            if (tmp.getIdJugadorRecibe() == -1)
                retVal++ ;
        }
        return retVal;
    }

    public int getCountPasesExitosos(Jugador jugador){
        int retVal = 0;
        for (PasePartido tmp : Pases){
            if (tmp.getIdJugadorEnvia() == jugador.getIdJugador() && tmp.getIdJugadorRecibe() != -1)
                retVal++ ;
        }

        return retVal;
    }

    public int getCountPasesFallidos(Jugador jugador){
        int retVal = 0;
        for (PasePartido tmp : Pases){
            if (tmp.getIdJugadorEnvia() == jugador.getIdJugador() && tmp.getIdJugadorRecibe() == -1)
                retVal++ ;
        }

        return retVal;
    }

    public int getCountFaltasCometidasPartido(Jugador jugador){
        int retVal = 0;
        for (FaltaPartido tmp : Faltas){
            if (tmp.getIdJugador() == jugador.getIdJugador() && tmp.Cometida == 1)
                retVal++;
        }
        return retVal;
    }

    public int getCountFaltasRecibidasPartido(Jugador jugador){
        int retVal = 0;
        for (FaltaPartido tmp : Faltas){
            if (tmp.getIdJugador() == jugador.getIdJugador() && tmp.Cometida == 0)
                retVal++;
        }
        return retVal;
    }

    public int getCountAccionJugador(Accion accion, Jugador jugador){
        int retVal = 0;
        for (AccionPartido tmp: Acciones){
            if (tmp.getIdJugador() == jugador.getIdJugador() && accion.equals(tmp.getAccion()))
                retVal++;
        }
        return retVal;
    }

    public int getCountAllAccionJugador(Accion accion){
        int retVal = 0;
        for (AccionPartido tmp: Acciones){
            if (accion.equals(tmp.getAccion()))
                retVal++;
        }
        return retVal;
    }

    public int getCountPaseJugador(Accion accion, Jugador jugador){
        int retVal = 0;
        for (PasePartido tmp: Pases){
            if (tmp.getIdJugadorEnvia() == jugador.getIdJugador() && accion.equals(tmp.pase))
                retVal++;
        }
        return retVal;
    }

    public int getCountAllPaseJugador(Accion accion){
        int retVal = 0;
        for (PasePartido tmp: Pases){
            if (accion.equals(tmp.pase))
                retVal++;
        }
        return retVal;
    }

    public int getCountFaltaJugador(Accion accion, Jugador jugador){
        int retVal = 0;
        for (FaltaPartido tmp: Faltas){
            if (tmp.getIdJugador() == jugador.getIdJugador() && accion.equals(tmp.falta))
                retVal++;
        }
        return retVal;
    }

    public int getCountAllFaltaJugador(Accion accion){
        int retVal = 0;
        for (FaltaPartido tmp: Faltas){
            if (accion.equals(tmp.falta))
                retVal++;
        }
        return retVal;
    }

    public int getCountAllPaseTipoPase(Pase pase, TipoPase tipo){
        int retVal = 0;
        for (PasePartido tmp : Pases){
            if (tmp.pase.equals(pase) && tmp.getTipoPase().equals(tipo))
                retVal++;
        }

        return retVal;
    }

    public int getCountAllPaseTipoPaseJugador(Jugador jugador,Pase pase, TipoPase tipo){
        int retVal = 0;
        for (PasePartido tmp : Pases){
            if (jugador.getIdJugador() == tmp.getIdJugadorEnvia() && tmp.pase.equals(pase) && tmp.getTipoPase().equals(tipo))
                retVal++;
        }

        return retVal;
    }

    public int getCountAllGoles(){
        int retVal = 0;
        for (AccionPartido tmp: Acciones){
            if (tmp.getAccion().getAbreviacionAccion().equals("GOL."))
                retVal++ ;
        }
        return retVal;
    }

    public int getCountAllFaltas(){
        return Faltas.size();
    }

    public int getCountAllTarjetasAmarillas(){
        int retVal = 0;
        for (FaltaPartido tmp: Faltas){
            if ((tmp.getCometida()==1) && (tmp.getTarjeta()==1))
                retVal++ ;
        }
        return retVal;
    }

    public int getCountAllTarjetasRojas(){
        int retVal = 0;
        for (FaltaPartido tmp: Faltas){
            if ((tmp.getCometida()==1) && (tmp.getTarjeta()==2))
                retVal++ ;
        }
        return retVal;
    }

    public int getCountJugadorPasesCortos(Jugador j){
        int retVal = 0;
        for (PasePartido tmp: Pases){
            if ((tmp.getTipoPase().getName().equals("Corto") && (tmp.getIdJugadorEnvia()==j.getIdJugador())))
                retVal++ ;
        }
        return retVal;
    }

    public int getCountJugadorPasesMedios(Jugador j){
        int retVal = 0;
        for (PasePartido tmp: Pases){
            if ((tmp.getTipoPase().getName().equals("Medio") && (tmp.getIdJugadorEnvia()==j.getIdJugador())))
                retVal++ ;
        }
        return retVal;
    }

    public int getCountJugadorPasesLargos(Jugador j){
        int retVal = 0;
        for (PasePartido tmp: Pases){
            if ((tmp.getTipoPase().getName().equals("Largo") && (tmp.getIdJugadorEnvia()==j.getIdJugador())))
                retVal++ ;
        }
        return retVal;
    }

    public List<JugadorPartido> getJugadores(){
        return Jugadores;
    }

}

class CambiosPartido implements Serializable{
    int idJugadorEntra;
    int idJugadorSale;
    String TIME;

    public CambiosPartido(int idJugadorEntra, int idJugadorSale, String TIME) {
        this.idJugadorEntra = idJugadorEntra;
        this.idJugadorSale = idJugadorSale;
        this.TIME = TIME;
    }

    public int getIdJugadorEntra() {
        return idJugadorEntra;
    }

    public void setIdJugadorEntra(int idJugadorEntra) {
        this.idJugadorEntra = idJugadorEntra;
    }

    public int getIdJugadorSale() {
        return idJugadorSale;
    }

    public void setIdJugadorSale(int idJugadorSale) {
        this.idJugadorSale = idJugadorSale;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
class AccionPartido implements Serializable{
    Accion accion;
    int idJugador;
    String TIME;

    public AccionPartido(Accion accion, int idJugador, String TIME) {
        this.accion = accion;
        this.idJugador = idJugador;
        this.TIME = TIME;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
class FaltaPartido implements Serializable{
    Falta falta;
    int idJugador;
    int Cometida; // 0 = RECIBIDA; 1 = COMETIDA
    int Tarjeta; // 0 | NULL = Nada; 1 = Amarilla; 2 = Roja;
    String TIME;

    public FaltaPartido(Falta falta, int idJugador, int cometida, int tarjeta, String TIME) {
        this.falta = falta;
        this.idJugador = idJugador;
        Cometida = cometida;
        Tarjeta = tarjeta;
        this.TIME = TIME;
    }

    public Falta getFalta() {
        return falta;
    }

    public void setFalta(Falta falta) {
        this.falta = falta;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getCometida() {
        return Cometida;
    }

    public void setCometida(int cometida) {
        Cometida = cometida;
    }

    public int getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(int tarjeta) {
        Tarjeta = tarjeta;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
class PasePartido implements Serializable{
    Pase pase;
    TipoPase tipoPase;
    int idJugadorEnvia;
    int idJugadorRecibe; // -1 | NULL = FALLO
    String TIME;

    public PasePartido(Pase pase, TipoPase tipoPase, int idJugadorEnvia, int idJugadorRecibe, String TIME) {
        this.pase = pase;
        this.tipoPase = tipoPase;
        this.idJugadorEnvia = idJugadorEnvia;
        this.idJugadorRecibe = idJugadorRecibe;
        this.TIME = TIME;
    }

    public int getIdJugadorEnvia() {
        return idJugadorEnvia;
    }

    public void setIdJugadorEnvia(int idJugadorEnvia) {
        this.idJugadorEnvia = idJugadorEnvia;
    }

    public int getIdJugadorRecibe() {
        return idJugadorRecibe;
    }

    public void setIdJugadorRecibe(int idJugadorRecibe) {
        this.idJugadorRecibe = idJugadorRecibe;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public TipoPase getTipoPase(){return tipoPase;}
}
class JugadorPartido implements Serializable{
    long tiempoEntrada;
    Jugador jugador;
    String TIME;

    public JugadorPartido(long tiempoEntrada, Jugador jugador, String TIME) {
        this.tiempoEntrada = tiempoEntrada;
        this.jugador = jugador;
        this.TIME = TIME;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public long getTiempoEntrada() {
        return tiempoEntrada;
    }

    public void setTiempoEntrada(long tiempoEntrada) {
        this.tiempoEntrada = tiempoEntrada;
    }



    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    @Override
    public String toString() {
        return jugador.toString();
    }
}