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
import java.util.concurrent.ThreadLocalRandom;

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
        final int soglia_popolazione;
        final boolean verbose;
        final int n_guerre;
        
        //controllo se ci sono 3 argomenti
        if(args.length == 3){
            soglia_popolazione = Integer.parseInt(args[0]);
            verbose = Boolean.parseBoolean(args[1]);
            n_guerre = Integer.parseInt(args[2]);;
        } else {
            soglia_popolazione = 12000;
            verbose = true;
            n_guerre = 500;
            System.out.println("Parametri errati o assenti. Carico i valori di default.");
        }
        
        //LEGGO I DATI DA FILE
        ArrayList<Comune> comuni_unfiltered = new ArrayList<>();
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
            System.out.println("File non trovato.");
        }

        if(verbose){
            //FILTRO LE CITTA' PER POPOLAZIONE
            ArrayList<Comune> comuni = riempiLista(comuni_unfiltered, soglia_popolazione);
            System.out.println("I comuni in guerra sono " + comuni.size() + ":");
            for(Comune c : comuni){
                System.out.println(c.getNome());
            }
            System.out.println("\nLa guerra si è conclusa. L'intera regione è adesso unificata sotto il regno di " + combatteteSchiavi(comuni, verbose).getNome() + ".");
        } else {
            //ANALIZZO LE PERCENTUALI DI VITTORIA
            
            ArrayList<StatComune> statistiche = getStats(comuni_unfiltered, soglia_popolazione, verbose, n_guerre);
            Collections.sort(statistiche);
            float somma = 0;
            for(StatComune sc : statistiche){
                System.out.println(sc.getNome() + ": " + sc.getWinrate(n_guerre) + "%");
                somma += sc.getWinrate(n_guerre);
            }
            //System.out.println("Somma dei winrate: " + somma);
        }
        //FINE MAIN
    }
    
    private static Comune combatteteSchiavi(ArrayList<Comune> lista_comuni, boolean verbose){
        ArrayList<Comune> lista = new ArrayList<>();
        for(Comune c: lista_comuni){
            lista.add(new Comune(c.getNome(), c.getPop(), new Centroide(c.getPos().x, c.getPos().y)));
        }
        int turno = 1;
        Comune attaccante;
        Territorio vittima;
        int vivi = lista.size();
        int random;
        boolean esito;
        Comune propVittima;
        while(true){
            //scelgo un comune casuale come attaccante
            random = ThreadLocalRandom.current().nextInt(0, vivi); //dovrei avere "vivi - 1" ma non serve perchè questa funzione non include l'ultimo valore del range
            //System.out.println("rand tra 0 e " + (vivi - 1) + ": "+ random);
            attaccante = lista.get(random);
            vittima = attaccante.trovaVicino(lista);
            propVittima = vittima.getProprietario();
            esito = attaccante.conquista(vittima);
            
            if(verbose){
                System.out.print("Giorno " + turno + ", " + attaccante.getNome() + " ha conquistato il territorio di " + vittima.getNome());
                if(!propVittima.getNome().equals(vittima.getNome())){
                    System.out.print(" precedentemente occupato da " + propVittima.getNome());
                }
                System.out.print(".\n");
            }
            if(esito){
                vivi--;
                if(verbose)
                    System.out.println(propVittima.getNome() + " è stata completamente sconfitta.\n" + vivi + " comuni rimanenti.");
            }
            Collections.sort(lista);
            if (vivi == 1){
                break;
            }
            turno++;
        }
        return attaccante;
    }
    
    public static void pause(){
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    
    public static void currentStatus(ArrayList<Comune> comuni){
            for(Comune c : comuni){
                System.out.println(c.getNome() + ": Territori:");
                for(Territorio t : c.getTerritori()){
                    System.out.println("\t" + t.getNome());
                }
                System.out.println();
            }
            System.out.println("\n");
    }
    
    public static ArrayList<Comune> riempiLista(ArrayList<Comune> comuni_unfiltered, int soglia_popolazione){
        ArrayList<Comune> comuni = new ArrayList<>();
        for(Comune c : comuni_unfiltered){
            if(c.getPop() >= soglia_popolazione)
                comuni.add(new Comune(c.getNome(), c.getPop(), new Centroide(c.getPos().x, c.getPos().y)));
        }
        return comuni;
    }
    
    public static ArrayList<StatComune> getStats(ArrayList<Comune> comuni_unfiltered, int soglia_popolazione, boolean verbose, int n_guerre){
        ArrayList<Comune> comuni_src = riempiLista(comuni_unfiltered, soglia_popolazione);
        
        ArrayList<Comune> comuni;
        
        ArrayList<StatComune> stat = new ArrayList<>();
        for(Comune c : comuni_src){
            stat.add(new StatComune(c.getNome()));
        }
        String vinc;
        
        //currentStatus(comuni);
        
        for(int i = 0; i < n_guerre; i++){
            comuni = new ArrayList<>();
            for(Comune c : comuni_src){
                comuni.add(c);
            }
            vinc = combatteteSchiavi(comuni, verbose).getNome();
            //System.out.println(vinc + " vince la guerra n. " + (i + 1) + " su " + n_guerre);
            for(StatComune sc : stat){
                if(sc.getNome().equals(vinc)){
                    sc.winWar();
                    break;
                }
            }
        }
        return stat;
    }
}
