/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siciliaguerrabot2020;

import siciliaguerrabot2020.Guerra.Comune;
import siciliaguerrabot2020.Guerra.Territorio;
import siciliaguerrabot2020.Guerra.Centroide;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import siciliaguerrabot2020.Calendario.Calendario;
import siciliaguerrabot2020.Calendario.Data;

/**
 *
 * @author Bi-Rabittoh
 */

public class SiciliaGuerraBot2020 {
    @Option(names = "-v", description = "only simulate one war (default ${DEFAULT-VALUE})")
    private boolean verbose = false;
    @Option(names = "-s", description = "load data from a specific file (default ${DEFAULT-VALUE})")
    private File source_file = new File("data.txt");
    @Option(names = "-m", description = "filter cities for max population (default ${DEFAULT-VALUE})")
    private int max_population = 12000;
    @Option(names = "-n", description = "sets number of wars to simulate (default ${DEFAULT-VALUE})")
    private int n_wars = 500;
    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested;
    
    
    public static void main(String[] args) {
        //String[] debug_args = {"-m=50000", "-h"}; SiciliaGuerraBot2020 par = CommandLine.populateCommand(new SiciliaGuerraBot2020(), debug_args);
        SiciliaGuerraBot2020 par = CommandLine.populateCommand(new SiciliaGuerraBot2020(), args);
        
        if(par.helpRequested){
            CommandLine.usage(par, System.out);
            System.exit(0);
        }
        
        final File file = par.source_file;
        final int soglia_popolazione = par.max_population;
        final boolean verbose = par.verbose;
        final int n_guerre = par.n_wars;
        
        //LEGGO I DATI DA FILE
        ArrayList<Comune> comuni_unfiltered = new ArrayList<>();
        BufferedReader reader;
        StringTokenizer st;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null){
                st = new StringTokenizer(line);
                comuni_unfiltered.add(new Comune(st.nextToken(), Integer.parseInt(st.nextToken()), new Centroide(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()))));
                line = reader.readLine();
            }
        } catch (IOException e){
            System.out.println("File non trovato.");
            System.exit(1);
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
            for(StatComune sc : statistiche){
                System.out.println(sc.getNome() + ": " + sc.getWinrate(n_guerre) + "%");
            }
        }
        //FINE MAIN
        System.exit(0);
    }
    
    private static Comune combatteteSchiavi(ArrayList<Comune> lista_comuni, boolean verbose){
        Calendario calendario = new Calendario(new Data(0, 1, 2020));
        ArrayList<Comune> lista = new ArrayList<>();
        for(Comune c: lista_comuni){
            lista.add(new Comune(c.getNome(), c.getPop(), new Centroide(c.getPos().x, c.getPos().y)));
        }
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
                System.out.print("\n" + calendario.nextString() + ", " + attaccante.getNome() + " ha conquistato il territorio di " + vittima.getNome());
                if(!propVittima.getNome().equals(vittima.getNome())){
                    System.out.print(" precedentemente occupato da " + propVittima.getNome());
                }
                System.out.print(".\n");
            }
            if(esito){
                vivi--;
                if(verbose)
                    System.out.print(propVittima.getNome() + " è stata completamente sconfitta. " + vivi + " comuni rimanenti.\n");
            }
            Collections.sort(lista);
            if (vivi == 1)
                break;
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
        
        for(int i = 0; i < n_guerre; i++){
            comuni = new ArrayList<>();
            for(Comune c : comuni_src){
                comuni.add(c);
            }
            vinc = combatteteSchiavi(comuni, verbose).getNome();
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
