package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.*;
import vue.HBoxGlobal;
import vue.VBoxSolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Controleur implements EventHandler {

    private static ToggleGroup toggleGroupScenario;
    private final ToggleGroup toggleGroupSolution;
    private final TextField champK;
    private final CheckBox checkBoxLimite;
    private final Button buttonCalculer;
    private static Menu menuFile;
    private static Menu menuModifier;
    private final boolean debug = true;


    public Controleur(ToggleGroup toggleGroupScenario,ToggleGroup toggleGroupSolution, TextField champK,CheckBox checkBoxLimite, Button buttonCalculer,Menu menuFile,Menu menuModifier) {
        this.toggleGroupScenario = toggleGroupScenario;
        this.toggleGroupSolution = toggleGroupSolution;
        this.champK = champK;
        this.checkBoxLimite = checkBoxLimite;
        this.buttonCalculer = buttonCalculer;
        this.menuFile = menuFile;
        this.menuModifier = menuModifier;


        //Change les labels et l'accès de certains élément en fonction du type de solution chosie
        this.toggleGroupSolution.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {

            if (newToggle != null) {
                String selectedSolution = newToggle.getUserData().toString();
                HBoxGlobal.getVBoxSolution().setLabelTypeSolution(selectedSolution);
            }

            if(newToggle.getUserData().equals("Multiple")) {
                champK.setDisable(false);
                checkBoxLimite.setDisable(false);

            } else {
                champK.setDisable(true);
                champK.clear();
                checkBoxLimite.setDisable(true);
                checkBoxLimite.setSelected(false);

            }
        });
    }

    //Gère les réponses lors de l'appuie sur le bouton Calculer en fonction du type de solution choisie
    @Override
    public void handle(Event event) {
        VBoxSolution vBoxSolution = HBoxGlobal.getVBoxSolution();

        if (event.getSource() instanceof Button) {
            if(event.getSource() == buttonCalculer) {
                Toggle selectedToggleScenario = toggleGroupScenario.getSelectedToggle();
                String numScenario = (String) selectedToggleScenario.getUserData();

                Toggle selectedToggleSolution = toggleGroupSolution.getSelectedToggle();
                String typeSolution = selectedToggleSolution.getUserData().toString();

                String solution = "";
                int distance;

                System.out.println(solution);

                switch (typeSolution) {
                    case "Simple":
                        try {
                            SolutionSimple solutionSimple = new SolutionSimple(numScenario);
                            distance = solutionSimple.getDistanceParcourue();
                            for (String ville : solutionSimple.getSolution()) {
                                solution += ville + " -> ";
                            }
                            solution = solution.substring(0, solution.length() - 4);
                            if(debug) {
                                System.out.println(solution);
                            }
                            vBoxSolution.setLabelSolution(solution);
                            vBoxSolution.setLabelDistance(distance);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Heuristique":
                        try {
                            SolutionHeuristique solutionHeuristique = new SolutionHeuristique(numScenario);
                            distance = solutionHeuristique.getDistanceParcourue();
                            for (String ville : solutionHeuristique.getSolution()) {
                                solution += ville + " -> ";
                            }
                            solution = solution.substring(0, solution.length() - 4);
                            if(debug) {
                                System.out.println(solution);
                            }
                            vBoxSolution.setLabelSolution(solution);
                            vBoxSolution.setLabelDistance(distance);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Multiple":
                        int k = Integer.parseInt(champK.getText());
                        boolean limite = checkBoxLimite.isSelected();
                        try {
                            KMeilleurSolution kMeilleurSolution = new KMeilleurSolution(numScenario, k, limite);
                            for (Map.Entry<Integer, List<List<String>>> entry : kMeilleurSolution.getSolution().entrySet()) {
                                for (List<String> liste : entry.getValue()) {
                                    for (String ville : liste) {
                                        solution += ville + " -> "; // A changer pour les multiples solutions
                                    }
                                    solution = solution.substring(0, solution.length() - 4) + " | Distance : " + entry.getKey() + "km" + "\n";
                                }
                            }
                            if(debug) {
                                System.out.println(solution);
                            }
                            vBoxSolution.setLabelSolution(solution);
                            vBoxSolution.setLabelDistance2(kMeilleurSolution.getSolution().firstKey());
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
            }
            else {
                try {
                    ouvrirFenetreCreer();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void ouvrirFenetreModifier(File file) throws FileNotFoundException {
        Stage fenetreModifier = new Stage();
        fenetreModifier.setTitle("Modification de " + file.getName());


        TextArea textArea = new TextArea();
        textArea.setPrefSize(600,400);

        StringBuilder text = new StringBuilder();
        Scanner Reader = new Scanner(file);
        while (Reader.hasNextLine()) {
            text.append(Reader.nextLine()).append("\n");
        }
        textArea.setText(text.toString());

        Button buttonSauvegarder = new Button("_Sauvegarder");
        buttonSauvegarder.setOnAction(event -> {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(textArea.getText());
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fenetreModifier.close();
        });

        Button buttonAnnuler = new Button("_Annuler");
        buttonAnnuler.setOnAction(event -> {
            fenetreModifier.close();
        });

        HBox HBoxButtons = new HBox(buttonAnnuler,buttonSauvegarder);


        VBox vBoxModifier = new VBox(10,textArea,HBoxButtons);
        Scene sceneModifier = new Scene(vBoxModifier);
        fenetreModifier.setScene(sceneModifier);
        fenetreModifier.show();
    }


    public void ouvrirFenetreCreer() throws FileNotFoundException {

        Stage fenetreCreer = new Stage();
        fenetreCreer.setTitle("Création d'un nouveau scénario");


        TextArea textArea = new TextArea();
        textArea.setPrefSize(600,400);

        StringBuilder text = new StringBuilder();

        Button buttonSauvegarder = new Button("Sauvegarder");
        buttonSauvegarder.setOnAction(event -> {

            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Nom du fichier");
            textInputDialog.setHeaderText("Entrez le nom du fichier (sans extension) :");
            textInputDialog.setContentText("Nom :");

            textInputDialog.showAndWait().ifPresent(fileNom -> {
                try {
                    File fileNouveau = new File("data/scenario/" + fileNom + ".txt");
                    FileWriter fileWriter = new FileWriter(fileNouveau);
                    fileWriter.write(textArea.getText());
                    fileWriter.close();
                    fenetreCreer.close();
                    reloadScenario();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        Button buttonAnnuler = new Button("Annuler");
        buttonAnnuler.setOnAction(event -> {
            fenetreCreer.close();
        });

        HBox HBoxButtons = new HBox(buttonAnnuler,buttonSauvegarder);

        VBox vBoxModifier = new VBox(10,textArea,HBoxButtons);
        Scene sceneModifier = new Scene(vBoxModifier);
        fenetreCreer.setScene(sceneModifier);
        fenetreCreer.show();
    }


    public static void reloadScenario() throws FileNotFoundException {
        menuFile.getItems().clear();
        menuModifier.getItems().clear();

        File[] fichierScenario = new File("data/scenario").listFiles();
        int counter = 0;
        for (File file : fichierScenario) {
            RadioMenuItem menuItem = new RadioMenuItem(file.getName());
            menuItem.setUserData("scenario_" + counter);
            menuItem.setToggleGroup(toggleGroupScenario);
            menuFile.getItems().add(menuItem);

            MenuItem menuItemModifier = new MenuItem(file.getName());
            menuItemModifier.setOnAction(e -> {
                try {
                    Controleur.ouvrirFenetreModifier(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
            menuModifier.getItems().add(menuItemModifier);


            if (counter == 0) {
                menuItem.setSelected(true);
            }
            counter++;
        }
    }
}