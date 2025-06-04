package modele;

import java.io.FileNotFoundException;
import java.util.*;

public class KMeilleurSolution {

    private TreeMap<Integer, List<List<String>>> listeDesSolutions = new TreeMap<>();
    private int distanceParcourue = 0;
    private int nb_calculation = 0;
    private int nb_solution_found = 0;
    private int nb_solution_wanted;
    private int seuil;
    private Donnees modele;
    private SolutionHeuristique heuristique;
    private Map<String, List<String>> listeVoisins;
    private boolean limite;



    public KMeilleurSolution(String parDonnee,int parNum, boolean parLimite) throws FileNotFoundException {
        modele = new Donnees(parDonnee);
        heuristique = new SolutionHeuristique(parDonnee);
        listeVoisins = modele.getListeVoisins();
        nb_solution_wanted = parNum;
        limite = parLimite;
        if(parNum == 1) {
            seuil = heuristique.getDistanceParcourue();
        }
        Map<String, Integer> listeDegreesEntrants = modele.getDegreesEntrant();
        List<String> source = new ArrayList<>();
        List<String> solution = new ArrayList<>();

        for (Map.Entry<String, Integer> ville : listeDegreesEntrants.entrySet()) {
            if (ville.getValue() == 0) {
                source.add(ville.getKey());
            }
        }

        getNewSolution(solution, source, listeDegreesEntrants,distanceParcourue);
    }

    public void getNewSolution(List<String> solution, List<String> source, Map<String, Integer> listeDegreesEntrants, Integer distanceParcourue) {


        //Limite de calcul
        if(nb_calculation == 50000000 && limite) {
            return;
        }
        else {
            if (nb_solution_found > nb_solution_wanted && distanceParcourue > seuil) {
                nb_calculation++;
                return;
            } else {


                //Ajout des solutions trouver à la liste des solutions

                if (source.isEmpty()) {
                    nb_solution_found++;
                    if (!listeDesSolutions.containsKey(distanceParcourue)) {
                        listeDesSolutions.put(distanceParcourue, new ArrayList<>());
                    }
                    listeDesSolutions.get(distanceParcourue).add(new ArrayList<>(solution));


                    if(nb_solution_found > nb_solution_wanted) {
                        if(listeDesSolutions.size() <= nb_solution_wanted) {
                            if (listeDesSolutions.get(listeDesSolutions.lastKey()).size() > 1) {
                                listeDesSolutions.get(listeDesSolutions.lastKey()).removeLast();
                            }
                            else {
                                listeDesSolutions.remove(listeDesSolutions.lastKey());
                            }
                        }
                        else {
                            listeDesSolutions.remove(listeDesSolutions.lastKey());
                        }
                    }
                    if (nb_solution_wanted > 1 || listeDesSolutions.lastKey() <= seuil) {
                        setSeuil(listeDesSolutions.lastKey());
                    }

                } else {

                    // Test pour chaque source
                    for (String s : source) {
                        if (s.equals("Vélizy-") && source.size() > 1) {
                            continue;
                        } else {

                            ArrayList<String> tempSource = new ArrayList<>(source);
                            ArrayList<String> newSolution = new ArrayList<>(solution);
                            Map<String, Integer> templisteDegreesEntrants = new HashMap<>(listeDegreesEntrants);
                            tempSource.remove(s);

                            Integer tempDistanceParcourue = distanceParcourue;
                            if (!newSolution.isEmpty()) {
                                tempDistanceParcourue += Donnees.distanceVilleAVille(newSolution.getLast(), s.substring(0, s.length() - 1));
                            }

                            if (newSolution.isEmpty() || !s.substring(0, s.length() - 1).equals(newSolution.getLast())) {
                                newSolution.add(s.substring(0, s.length() - 1));

                            }
                            if (listeVoisins.containsKey(s)) {
                                for (String ville : listeVoisins.get(s)) {
                                    templisteDegreesEntrants.put(ville, templisteDegreesEntrants.get(ville) - 1);
                                    if (templisteDegreesEntrants.get(ville) == 0) {
                                        tempSource.add(ville);
                                    }
                                }
                            }
                            getNewSolution(newSolution, tempSource, templisteDegreesEntrants, tempDistanceParcourue);
                        }
                    }
                }
            }
        }
    }



    /**
     * Méthode permettant de récupérer la solution du scénario
     *
     * @return Une liste contentant les villes à parcourir dans le bonne ordre.
     */


    public TreeMap<Integer, List<List<String>>> getSolution () {
        if(listeDesSolutions.firstKey() > heuristique.getDistanceParcourue()) {
            listeDesSolutions.put(heuristique.getDistanceParcourue(), new ArrayList<>());
            listeDesSolutions.get(heuristique.getDistanceParcourue()).add(heuristique.getSolution());
            if(listeDesSolutions.size() <= nb_solution_wanted) {
                if (listeDesSolutions.get(listeDesSolutions.lastKey()).size() > 1) {
                    listeDesSolutions.get(listeDesSolutions.lastKey()).removeLast();
                }
                else {
                    listeDesSolutions.remove(listeDesSolutions.lastKey());
                }
            }
            else {
                listeDesSolutions.remove(listeDesSolutions.lastKey());
            }
        }
        return listeDesSolutions;
    }



    /**
     * Permet de définir un nouveau seuil
     * @param parVal correspond au nouveau seuil
     */

    public void setSeuil(int parVal) {
        seuil = parVal;
    }

}


