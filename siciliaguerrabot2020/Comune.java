/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siciliaguerrabot2020;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Bi-Rabittoh
 */
public class Comune implements Comparable<Comune> {
    private boolean vivo;
    private String nome;
    private Centroide pos;
    private LinkedList<Territorio> territori;
    private final int pop;
    private double winrate;

    public Comune(String nome, int pop, Centroide pos) {
        this.nome = nome;
        this.pop = pop;
        this.pos = pos;
        this.vivo = true;
        territori = new LinkedList<>();
        territori.add(new Territorio(nome, this, pos));
    }
    
    public boolean isVivo() {
        return vivo;
    }

    public void kill() {
        this.vivo = false;
    }

    public String getNome() {
        return nome;
    }

    public int getPop() {
        return pop;
    }

    public Centroide getPos() {
        return pos;
    }

    
    public LinkedList<Territorio> getTerritori() {
        return territori;
    }

    
    @Override
    public String toString() {
        return "Comune{" + "vivo=" + vivo + ", nome=" + nome + ", pop=" + pop + ", pos=" + pos + '}';
    }
    
    //FUNZIONI GEOMETRICHE
    
    public void aggiornaCentroide(){
        Territorio territorio;
        Centroide temp = territori.get(0).getPos();
        for(int i = 0; i < territori.size(); i++){
            territorio = territori.get(i);
            
        }
        
        
        for (Territorio t : territori){
            temp = temp.puntoMedio(t.getPos());
        }
        pos = temp;
    }
    
    //FUNZIONI BELLE
    public boolean conquista(Territorio target){
        boolean ris = false;
        target.getProprietario().territori.remove(target);
        if(target.getProprietario().territori.isEmpty()){
            target.getProprietario().kill();
            ris = true;
        }else
            target.getProprietario().aggiornaCentroide();
        
        target.setProprietario(this);
        this.territori.add(target);
        this.aggiornaCentroide();
        return ris;
    }
    
    public Territorio trovaVicino(ArrayList<Comune> lista){
        
        Territorio trovato = new Territorio("Errore", null, null);
        double minD = 999999999, cur;

        for(int i = 0; i < lista.size(); i++){
            Comune comune = lista.get(i);
            //scorro i comuni
            if(comune.isVivo() && !comune.equals(this)){
                //mi trovo in un comune VIVO e diverso da se stesso
                for(int j = 0; j < comune.territori.size(); j++){
                    Territorio territorio = comune.territori.get(j);
                    //mi trovo in un territorio generico di questo comune
                    cur = this.pos.distanza(territorio.getPos());
                    //oonfronto la distanza con minD
                    if(cur < minD){
                        minD = cur;
                        trovato = territorio;
                    }
                }
            }
        }
        return trovato;
    }
    
    @Override
    public int compareTo(Comune target){
        if((!this.isVivo()) && target.isVivo()){
            return 1;
        } else if (this.isVivo() && (!target.isVivo())){
            return -1;
        } else return 0;
    }
    //FINE FILE

    
}
