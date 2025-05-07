package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static modele.Constante.*;

public class SolutionSimple {
    public Map<String,ArrayList<String>> Liste_voisin = new HashMap();


    public ArrayList<String> getVille() {
        ArrayList<String> solution = new ArrayList<>();
        for(String e : listeScenario) {
            solution.add(Membre_Ville.get(e));
        }
        return solution;

    }
}