package edu.unitec.futbolapp;

/**
 * Created by nivx1 on 09/05/2015.
 */
public class Falta extends Accion {
    public Falta(String nombreAccion) {
        super(nombreAccion);
    }

    public Falta(int idAccion, String nombreAccion) {
        super(idAccion, nombreAccion);
    }


    @Override
    public String toString() {
        return nombreAccion+",Falta";
    }
}
