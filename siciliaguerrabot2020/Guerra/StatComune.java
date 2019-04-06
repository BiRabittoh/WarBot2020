/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siciliaguerrabot2020.Guerra;

/**
 *
 * @author Marco
 */
public class StatComune implements Comparable<StatComune> {
    private final String nome;
    private int n_vincite;

    public StatComune(String nome) {
        this.nome = nome;
        this.n_vincite = 0;
    }

    public String getNome() {
        return nome;
    }

    public double getWinrate(int n_partite) {
        return (100 * (double)n_vincite / (double)n_partite);
    }
    
    public void winWar(){
        n_vincite++;
    }

    @Override
    public int compareTo(StatComune thi) {
        return (thi.n_vincite - this.n_vincite);
    }
}
