package com.journaldev.expandablelistview;

/**
 * Created by adolf on 26/01/2017.
 */

public class Jugador {
    String nombreEquipo;
    String nombreJugador;
    int dorsal;


    public Jugador(){
        nombreEquipo="";
        nombreJugador="";
        dorsal = 0;
    };

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }


    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Jugador(String nombreEquipo, String nombreJugador) {
        this.nombreEquipo = nombreEquipo;
        this.nombreJugador = nombreJugador;
    }
}
