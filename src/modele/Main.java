package modele;


import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        new Constante();
        System.out.println(Constante.Membre_Ville);
        System.out.println(Constante.Liste_Villes);
    }
}