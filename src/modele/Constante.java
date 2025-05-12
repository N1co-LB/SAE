package modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Constante {

    public static final Map<String, String> Membre_Ville = new HashMap<>();
    public static final Map<String,String> mapScenario = new HashMap<>();
    public static final Map<String,List<String>> Liste_voisin = new HashMap();

    public Constante() throws FileNotFoundException {
        File membre = new File("data/membres_APPLI.txt");

        if (!membre.exists()) {
            throw new FileNotFoundException("Le fichier " + membre.getAbsolutePath() + " n'existe pas.");
        }

        Scanner Reader1 = new Scanner(membre);
        while (Reader1.hasNextLine()) {
            String data = Reader1.nextLine();
            String[] Split = data.split(" ");
            String Nom = Split[0];
            String Ville = Split[1];
            Membre_Ville.put(Nom, Ville);
        }
        Reader1.close();

        File scenario = new File("data/scenario/scenario_0.txt");

        if (!scenario.exists()) {
            throw new FileNotFoundException("Le fichier " + scenario.getAbsolutePath() + " n'existe pas.");
        }

        Scanner Reader2 = new Scanner(scenario);
        while (Reader2.hasNextLine()) {
            String data = Reader2.nextLine();
            String[] Split = data.split("->");
            String Ville1 = Split[0].trim();
            String Ville2 = Split[1].trim();

            // Ajouter les villes dans la liste principale
            mapScenario.put(Ville1,Ville2);
        }
        Reader2.close();
        }

        public Map<String,String> getMembre_Ville() {return Membre_Ville;}
        public Map<String, String> getScenario() {return mapScenario;}


    public Map<String,List<String>> getVille() {
        for(Map.Entry<String,String> entry : getScenario().entrySet()) {
            String vendeur = entry.getKey();
            String acheteur = entry.getValue();




        }
     return Liste_voisin;
    }

}

