/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siciliaguerrabot2020.Guerra;

/**
 *
 * @author Bi-Rabittoh
 */
public class Posizione {
    public double x;
    public double y;

    public Posizione(double x, double y) {
        this.x = x;
        this.y = y;
    }
  
    @Override
    public String toString() {
        return "Centroide{" + "x=" + x + ", y=" + y + '}';
    }
    
    //FORMULE GEOMETRICHE
    public Posizione puntoMedio(Posizione target){
        return new Posizione((this.x + target.x) / 2, (this.y + target.y) / 2);
    }
    
    public double distanza(Posizione target){
        return Math.sqrt(Math.pow(this.x - target.x, 2) + Math.pow(this.y - target.y, 2));
    }
}
