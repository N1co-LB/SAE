package modele;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * La classe SolutionSimple permet de faire le calcul de la solution et de la distance à parcourir pour un scénario donné à l'aide d'un tri topologique
 * @author Nicolas LE BRETON
 */

public class SolutionSimple {

    private List<String> listeSolution = new ArrayList<>();
    private Donnees modele;
    private int distanceParcourue;


    /**
     * Constructeur qui initialise la liste des solutions et la distance parcourue.
     */

    public SolutionSimple(String parDonne) throws FileNotFoundException {
        modele = new Donnees(parDonne);
        Map<String, List<String>> listeVoisins = modele.getListeVoisins();
        Map<String, Integer> listeDegreesEntrants = modele.getDegreesEntrant();
        List<String> source = new ArrayList<>();

        //Récupération des sources
        for(Map.Entry<String, Integer> ville : listeDegreesEntrants.entrySet()) {
            if (ville.getValue() == 0) {
                source.add(ville.getKey());
            }
        }
        String s;

        //Algo
        while(!source.isEmpty()) {
            s = source.getFirst();
            if (listeVoisins.containsKey(s)) {
                for (String villeAcheteur : listeVoisins.get(s)) {
                    listeDegreesEntrants.put(villeAcheteur, listeDegreesEntrants.get(villeAcheteur) - 1);
                    if (listeDegreesEntrants.get(villeAcheteur) == 0) {
                        source.add(villeAcheteur);
                    }
                }
            }
            source.removeFirst();
            if(!listeSolution.isEmpty()) {
                distanceParcourue += Donnees.distanceVilleAVille(listeSolution.getLast(), s.substring(0,s.length()- 1));
            }
            listeSolution.add(s.substring(0, s.length() - 1));
        }
    }

    /**
     * Méthode permettant de récupérer la solution du scénario
     * @return Une liste contentant les villes à parcourir dans le bonne ordre.
     */

    public List<String> getSolution() {return listeSolution;}

    /**
     * Méthode permettant de récupérer la distance de la solution du scénario
     * @return Un entier correspondant à la distance parcourue pour la solution actuelle.
     */

    public int getDistanceParcourue() {return distanceParcourue;}


}




