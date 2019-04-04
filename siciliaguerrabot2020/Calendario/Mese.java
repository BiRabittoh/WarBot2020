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
class Mese {
    private final String nome_mese;
    private final int n_giorni;

    public Mese(String nome, int n_giorni) {
        this.nome_mese = nome;
        this.n_giorni = n_giorni;
    }

    public String getNome_mese() {
        return nome_mese;
    }

    public int getN_giorni() {
        return n_giorni;
    }
    
    
}
