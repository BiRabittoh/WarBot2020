/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siciliaguerrabot2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Bi-Rabittoh
 */
public class SiciliaGuerraBot2020 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final String nomefile = "data.txt";
        final int soglia_popolazione = 12000;
        
        //LEGGO I DATI DA FILE
        ArrayList<Comune> comuni_unfiltered = new ArrayList<>();
        Centroide temp;
        BufferedReader reader;
        StringTokenizer st;
        try {
            reader = new BufferedReader(new FileReader(nomefile));
            String line = reader.readLine();
            while (line != null){
                st = new StringTokenizer(line);
                comuni_unfiltered.add(new Comune(st.nextToken(), Integer.parseInt(st.nextToken()), new Centroide(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()))));
                line = reader.readLine();
            }
        } catch (IOException e){
            System.out.println("File non trovato");
        }
        
        //FILTRO LE CITTA' PER POPOLAZIONE
        ArrayList<Comune> comuni = new ArrayList<>();
        for(Comune c : comuni_unfiltered){
            if(c.getPop() >= soglia_popolazione)
                comuni.add(c);
        }
        System.out.println("I comuni in guerra sono " + comuni.size() + ":");
        for(Comune c : comuni){
            System.out.println(c.getNome());
        }
        suspance();
        Comune vincitore = combatteteSchiavi(comuni);
        System.out.println("Il vincitore è " + vincitore.getNome() + " con " + vincitore.getTerritori().size() + " territori");
        
        //TODO: analizzare la probabilità di vittoria di ciascun comune
        
        //FINE MAIN
    }
    
    private static int randomRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Il secondo parametro deve essere maggiore del primo");
	}
	return (int)(Math.random() * ((max - min) + 1)) + min;
    }
    
    private static Comune combatteteSchiavi(ArrayList<Comune> lista){
        
        int turno = 1;
        Comune attaccante;
        Territorio vittima;
        int vivi = lista.size();
        int random;
        while(true){
            //scelgo un comune casuale come attaccante
            random = randomRange(0, vivi - 1);
            attaccante = lista.get(random);
            vittima = attaccante.trovaVicino(lista);
            if(attaccante.conquista(vittima))
                vivi--;
            System.out.println("Turno " + turno + ": " + attaccante.getNome() + " conquista il territorio di " + vittima.getNome() + " e arriva a " + attaccante.getTerritori().size() + " territori");
            Collections.sort(lista);
            //controllo se la partita è finita
            if (vivi == 1){
                break;
            }
            
            turno++;
        }
        return attaccante;
    }
    
    public static void suspance(){
        System.out.println("Al mio \"INVIO\" scatenate l'inferno...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    
    public static void currentStatus(ArrayList<Comune> comuni){
        //fine turno. DEBUG TIME
            for(Comune c : comuni){
                System.out.println(c.getNome() + ": Territori:");
                for(Territorio t : c.getTerritori()){
                    System.out.println("\t" + t.getNome());
                }
                System.out.println();
            }
            System.out.println("\n");
            //pause();
            //FINE DEBUG*/
    }
}
