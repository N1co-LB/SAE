package modele;


import java.io.FileNotFoundException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Donnees modele = new Donnees();

        //System.out.println("Scénario : " + modele.getScenario());
        for (Map.Entry<String, String> entry : modele.getScenario().entrySet()) {
            String vendeur = entry.getKey();
            String acheteur = entry.getValue();
            System.out.println("Scénario Ville : " + modele.getMembreVille().get(vendeur) + "->" + modele.getMembreVille().get(acheteur));
        }

        //System.out.println("Ville et distance : " + modele.getDistanceVille());
        //System.out.println("Voisins : " + modele.getListeVoisins());
        System.out.println("Degrées Entrants  : " +modele.getDegreesEntrant());
        /*SolutionSimple solutionSimple = new SolutionSimple();
        System.out.println("Solution : " + solutionSimple.getSolution());
        System.out.println("Distance parcourue : " + solutionSimple.getDistanceParcourue() + "km");*/
        /*SolutionHeuristique solutionHeuristique = new SolutionHeuristique();
        System.out.println("Solution : " + solutionHeuristique.getSolution());
        System.out.println("Distance parcourue : " + solutionHeuristique.getDistanceParcourue() + "km");
        //SolutionHeuristiqueInverse solutionHeuristiqueInverse = new SolutionHeuristiqueInverse();*/
        //System.out.println("Solution : " + solutionHeuristiqueInverse.getSolution());
        //System.out.println("Distance parcourue : " + solutionHeuristiqueInverse.getDistanceParcourue() + "km");
        /*KMeilleurSolution meilleurSolution = new KMeilleurSolution(1);
        System.out.println(meilleurSolution.getSolution());
        System.out.println(meilleurSolution.getSolution().size());*/

    }
}