/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siciliaguerrabot2020.Calendario;

import java.util.ArrayList;

/**
 *
 * @author Marco
 */
public class Calendario {
    private ArrayList<Mese> mesi;
    private int anno;
    private int mese;
    private int giorno;

    public Calendario(Data inizio) {
        this.giorno = inizio.getGiorno();
        this.anno = inizio.getAnno();
        this.mese = inizio.getMese();
        
        this.mesi = new ArrayList<>(12);
        mesi.add(new Mese("Gennaio", 31));
        if(isBisestile(anno))
            mesi.add(new Mese("Febbraio", 29));
        else
            mesi.add(new Mese("Febbraio", 28));
        mesi.add(new Mese("Marzo", 31));
        mesi.add(new Mese("Aprile", 30));
        mesi.add(new Mese("Maggio", 31));
        mesi.add(new Mese("Giugno", 30));
        mesi.add(new Mese("Luglio", 31));
        mesi.add(new Mese("Agosto", 31));
        mesi.add(new Mese("Settembre", 30));
        mesi.add(new Mese("Ottobre", 31));
        mesi.add(new Mese("Novembre", 30));
        mesi.add(new Mese("Dicembre", 31));
    }
    
    private boolean isBisestile(int year){
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }
    
    public Data nextDay(){
        if(giorno <= mesi.get(mese - 1).getN_giorni() - 1){
            //incremento il giorno
            giorno++;
        } else {
            if(mese < 12){
                //incremento il mese
                mese++;
            } else {
                //incremento l'anno
                mese = 1;
                anno++;
                if(isBisestile(anno)){
                    mesi.set(1, new Mese("Febbraio", 29));
                } else {
                    mesi.set(1, new Mese("Febbraio", 28));
                }
            }
            giorno = 1;
        }
        return new Data(giorno, mese, anno);
    }
    
    public String nextString(){
        Data d = nextDay();
        return d.getGiorno() + " " + mesi.get(d.getMese() - 1).getNome_mese() + " " + d.getAnno();
    }
}
