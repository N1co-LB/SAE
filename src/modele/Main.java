package modele;


import java.io.FileNotFoundException;
import java.util.ArrayList;

import static modele.Constante.listeScenario;
import static modele.Constante.Membre_Ville;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        new Constante();
        System.out.println(Constante.Membre_Ville);
        System.out.println(Constante.listeScenario);

        ArrayList<String> solution = new ArrayList<>();
        for(String e : listeScenario) {
            solution.add(Membre_Ville.get(e));
        }
        System.out.println(solution);
    }
}