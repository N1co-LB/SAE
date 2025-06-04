package modele;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * La classe SolutionHeuristique contient un constructeur permettant de faire le calcul de la solution et de la distance à parcourir pour un scénario donné.
 * @author Nicolas LE BRETON
 */


public class SolutionHeuristique {

    private List<String> listeSolution = new ArrayList<>();
    private int distanceParcourue;
    private Donnees modele;


    /**
     * Constructeur qui initialise la solution et la distance parcourue.
     */



    public SolutionHeuristique(String parDonnne) throws FileNotFoundException {
        modele = new Donnees(parDonnne);
        Map<String, List<String>> listeVoisins = modele.getListeVoisins();
        Map<String, Integer> listeDegreesEntrants = modele.getDegreesEntrant();
        List<String> source = new ArrayList<>();

        //Récupération des sources
        for(Map.Entry<String, Integer> ville : listeDegreesEntrants.entrySet()) {
            if (ville.getValue() == 0) {
                source.add(ville.getKey());
            }
        }
        String s = "";
        while(!source.isEmpty()) {

            //Choix de la source en fonction de la distance
            s = source.getFirst();
            if(source.size() > 1) {
                for (int i = 1; i < source.size(); i++) {
                    if ((Donnees.distanceVilleAVille(listeSolution.getLast(), source.get(i).substring(0,source.get(i).length()-1)) < Donnees.distanceVilleAVille(listeSolution.getLast(), s.substring(0,s.length()- 1))) && (!source.get(i).substring(0,source.get(i).length()- 1).equals("Vélizy"))) {
                        s = source.get(i);
                    }
                }
            }
            if(!listeSolution.isEmpty()) {
                distanceParcourue += Donnees.distanceVilleAVille(listeSolution.getLast(), s.substring(0,s.length()- 1));
            }

            //Algo
            if (listeVoisins.containsKey(s)) {
                for (String villeAcheteur : listeVoisins.get(s)) {
                    listeDegreesEntrants.put(villeAcheteur, listeDegreesEntrants.get(villeAcheteur) - 1);
                    if (listeDegreesEntrants.get(villeAcheteur) == 0) {
                        source.add(villeAcheteur);
                    }
                }
            }
            source.remove(s);
            if (listeSolution.isEmpty() || !s.substring(0,s.length()- 1).equals(listeSolution.getLast())) {
                listeSolution.add(s.substring(0,s.length()- 1));
            }
        }
    }

    /**
     * Méthode permettant de récupérer la solution du scénario
     * @return Une liste contentant les villes à parcourir dans le bonne ordre.
     */

    public List<String> getSolution() {
        return listeSolution;
    }


    /**
     * Méthode permettant de récupérer la distance de la solution du scénario
     * @return Un entier correspondant à la distance parcourue pour la solution actuelle.
     */

    public int getDistanceParcourue() {return distanceParcourue;}

}

