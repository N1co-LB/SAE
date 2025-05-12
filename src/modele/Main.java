package modele;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import static modele.Constante.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        new Constante();
        System.out.println(Constante.Membre_Ville);
        System.out.println(mapScenario);

        ArrayList<String> solution = new ArrayList<>();
        for (Map.Entry<String,String> entry : mapScenario.entrySet()) {
            solution.add(entry.getKey());
        }
        System.out.println(solution);
    }
}