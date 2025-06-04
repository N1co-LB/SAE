package modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * La classe Donnees initialise toutes les données nécéssaires au fonctionnement des algorithmes
 * @author Nicolas LE BRETON
 */

public class Donnees {

    private final Map<String, String> membreVille = new HashMap<>();
    private final Map<String, String> mapScenario = new HashMap<>();
    private final Map<String, List<String>> voisins = new HashMap<>();
    private final Map<String, Integer> degreesEntrant = new HashMap<>();
    private static final LinkedHashMap<String, List<Integer>> distanceVille = new LinkedHashMap<>();


    // Sans paramètre uniquement pour test console
    public Donnees() throws FileNotFoundException {

        File fichierMembre = new File("data/membres_APPLI.txt");
        if (!fichierMembre.exists()) {
            throw new FileNotFoundException("Fichier non trouvé dans le répertoire " + fichierMembre.getAbsolutePath());
        }
        Scanner Reader1 = new Scanner(fichierMembre);
        while (Reader1.hasNextLine()) {
            String data = Reader1.nextLine();
            String[] Split = data.split(" ");
            String Nom = Split[0];
            String Ville = Split[1];
            membreVille.put(Nom, Ville);
        }
        Reader1.close();

        File scenario = new File("data/scenario/scenario_3.txt");
        if (!scenario.exists()) {
            throw new FileNotFoundException("Fichier non trouvé dans le répertoire " + fichierMembre.getAbsolutePath());
        }

        Scanner Reader2 = new Scanner(scenario);
        while (Reader2.hasNextLine()) {
            String data = Reader2.nextLine();
            String[] Split = data.split("->");
            String Ville1 = Split[0].trim();
            String Ville2 = Split[1].trim();

            mapScenario.put(Ville1, Ville2);
        }
        Reader2.close();

        Scanner Reader3 = new Scanner(new File("data/distances.txt"));
        while (Reader3.hasNextLine()) {
            String data = Reader3.nextLine();
            String[] Split = data.split(" ");
            String ville = Split[0];
            List<Integer> distances = new ArrayList<>();
            for(int i = 1; i < Split.length ; i++ ) {
                int distance = Integer.parseInt(Split[i]);
                distances.add(distance);

            distanceVille.put(ville, distances);
            }
        }
        Reader3.close();


        genererVoisins();
        genererDegreeEntrant();
    }


    /**
     * Constructeur qui initialise les données.
     * @param parName correspond au nom du fichier scénario
     */


    public Donnees(String parName) throws FileNotFoundException {

        File fichierMembre = new File("data/membres_APPLI.txt");
        if (!fichierMembre.exists()) {
            throw new FileNotFoundException("Fichier non trouvé dans le répertoire " + fichierMembre.getAbsolutePath());
        }
        Scanner Reader1 = new Scanner(fichierMembre);
        while (Reader1.hasNextLine()) {
            String data = Reader1.nextLine();
            String[] Split = data.split(" ");
            String Nom = Split[0];
            String Ville = Split[1];
            membreVille.put(Nom, Ville);
        }
        Reader1.close();

        File scenario = new File("data/scenario/" + parName + ".txt");
        if (!scenario.exists()) {
            throw new FileNotFoundException("Fichier non trouvé dans le répertoire " + fichierMembre.getAbsolutePath());
        }

        Scanner Reader2 = new Scanner(scenario);
        while (Reader2.hasNextLine()) {
            String data = Reader2.nextLine();
            String[] Split = data.split("->");
            String Ville1 = Split[0].trim();
            String Ville2 = Split[1].trim();

            // Ajouter les villes dans la liste principale
            mapScenario.put(Ville1, Ville2);
        }
        Reader2.close();

        Scanner Reader3 = new Scanner(new File("data/distances.txt"));
        while (Reader3.hasNextLine()) {
            String data = Reader3.nextLine();
            String[] Split = data.split(" ");
            String ville = Split[0];
            List<Integer> distances = new ArrayList<>();
            for(int i = 1; i < Split.length ; i++ ) {
                int distance = Integer.parseInt(Split[i]);
                distances.add(distance);

                distanceVille.put(ville, distances);
            }
        }
        Reader3.close();


        genererVoisins();
        genererDegreeEntrant();
    }

    /**
     * Génère les voisins en fonction des données
     */

    private void genererVoisins() {
        voisins.put("Vélizy+", new ArrayList<>());
        for (Map.Entry<String, String> entry : mapScenario.entrySet()) {
            String vendeur = entry.getKey();
            String acheteur = entry.getValue();
            String villeVendeur = membreVille.get(vendeur);
            String villeAcheteur = membreVille.get(acheteur);

            voisins.putIfAbsent(villeVendeur + "+", new ArrayList<>());
            voisins.putIfAbsent(villeVendeur + "-", new ArrayList<>());

            if (!voisins.get(villeVendeur + "+").contains(villeVendeur + "-") || !voisins.get(villeVendeur + "-").contains("Vélizy-")) {
                voisins.get(villeVendeur + "+").add(villeVendeur + "-");
                voisins.get(villeVendeur + "-").add("Vélizy-");
            }
            voisins.get(villeVendeur + "+").add(villeAcheteur + "-");
        }
        for (String entry : mapScenario.keySet()) {
            if (!voisins.get("Vélizy+").contains(membreVille.get(entry)+ "+")) {
                voisins.get("Vélizy+").add(membreVille.get(entry)+ "+");
            }
        }

    }

    /**
     * Génère les degrées entrant.
     */

    private void genererDegreeEntrant() {
        for (String villeVendeur : voisins.keySet()) {
            degreesEntrant.put(villeVendeur, 0);
        }
        for (Map.Entry<String, List<String>> entry : voisins.entrySet()) {
            for (String villeAcheteur : entry.getValue()) {
                if (villeAcheteur != null) {
                    degreesEntrant.put(villeAcheteur, degreesEntrant.getOrDefault(villeAcheteur, 0) + 1);
                }
            }
        }
    }

    /**
     * Permet de connaître la distance entre deux villes
     * @param villeDepart correspond à la ville actuel
     * @param villeArrivee correspond à la ville d'arriver
     * Retourne un int corrrespondant à la distance entre la ville de départ et la ville d'arriver
     */

    public static int distanceVilleAVille(String villeDepart, String villeArrivee) {
        List<String> indiceVille = new ArrayList<>(distanceVille.keySet());
        return distanceVille.get(villeDepart).get(indiceVille.indexOf(villeArrivee));
    }

    /**
     * Permet de récupperer membreVille
     * Retourne un dictionnaire de type clé string et valeur string
     */

    public Map<String, String> getMembreVille() {return membreVille;}

    /**
     * Permet de récupperer mapScenario
     * Retourne un dictionnaire de type clé string et valeur string
     */

    public Map<String, String> getScenario() {return mapScenario;}

    /**
     * Permet de récupperer voisins
     * Retourne un dictionnaire de type clé string et valeur List<String>
     */

    public Map<String, List<String>> getListeVoisins() {return voisins;}


    /**
     * Permet de récupperer degreesEntrant
     * Retourne un dictionnaire de type clé string et valeur int
     */

    public Map<String, Integer> getDegreesEntrant() {return degreesEntrant;}



    /**
     * Permet de récupperer mapScenario
     * Retourne un dictionnaire de type clé string et valeur List<Integer>
     */

    public LinkedHashMap<String, List<Integer>> getDistanceVille() {return distanceVille;}
}

