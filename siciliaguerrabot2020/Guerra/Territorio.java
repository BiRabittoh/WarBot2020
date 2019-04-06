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
public class Territorio {
    private final String nome;
    private Comune proprietario;
    private final Posizione pos;

    public Territorio(String nome, Comune proprietario, Posizione pos) {
        this.nome = nome;
        this.proprietario = proprietario;
        this.pos = pos;
    }
    
    public Comune getProprietario() {
        return proprietario;
    }

    public void setProprietario(Comune proprietario) {
        this.proprietario = proprietario;
    }

    public Posizione getPos() {
        return pos;
    }

    public String getNome() {
        return nome;
    }
    
    
}
