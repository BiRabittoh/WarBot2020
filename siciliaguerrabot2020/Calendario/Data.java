/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siciliaguerrabot2020.Calendario;

/**
 *
 * @author Marco
 */
public class Data {
    private final int giorno;
    private final int mese;
    private final int anno;

    public Data(int giorno, int mese, int anno) {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
    }

    public int getGiorno() {
        return giorno;
    }

    public int getMese() {
        return mese;
    }

    public int getAnno() {
        return anno;
    }

    public String getCompact() {
        return giorno + "/" + mese + "/" + anno;
    }

    @Override
    public String toString() {
        return "Data{" + "giorno=" + giorno + ", mese=" + mese + ", anno=" + anno + '}';
    }
}
