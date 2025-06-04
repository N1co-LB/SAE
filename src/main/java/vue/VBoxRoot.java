package vue;

import controleur.Controleur;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;


public class VBoxRoot extends VBox {
    private static HBoxGlobal HBoxGlobal;


    public VBoxRoot() {
        super(40);
        HBoxGlobal = new HBoxGlobal();
        Menu menuFile = new Menu("Scénarios");
        Menu menuModifier = new Menu("Modifier");

        //Création Menu Bar et MenuFile
        MenuBar menuBar = new MenuBar();
        ToggleGroup toggleGroupScenario = new ToggleGroup();

        // Récupéraion des ficihiers scénarios
        File [] fichierScenario = new File("data/scenario").listFiles();


        // Création des boutons pour les scénarios
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

        //Menu de sélection du type de solution
        Menu menuSolution = new Menu("Solutions");
        RadioMenuItem menuItemSolutionSimple = new RadioMenuItem("Solution Simple");
        RadioMenuItem menuItemSolutionHeuristique = new RadioMenuItem("Solution Heuristique");
        RadioMenuItem menuItemKMeilleurSolution = new RadioMenuItem("Solution KMeilleurSolution");

        ToggleGroup toggleGroupSolution = new ToggleGroup();
        menuItemSolutionSimple.setToggleGroup(toggleGroupSolution);
        menuItemSolutionSimple.setUserData("Simple");
        menuItemSolutionHeuristique.setToggleGroup(toggleGroupSolution);
        menuItemSolutionHeuristique.setUserData("Heuristique");
        menuItemKMeilleurSolution.setToggleGroup(toggleGroupSolution);
        menuItemKMeilleurSolution.setUserData("Multiple");

        menuItemSolutionSimple.setSelected(true);

        menuSolution.getItems().addAll(menuItemSolutionSimple,menuItemSolutionHeuristique,menuItemKMeilleurSolution);

        menuBar.getMenus().addAll(menuFile,menuSolution,menuModifier);

        //Nombre de solution voulue dans le cas de la solution multiple
        TextField champK = new TextField();
        champK.setPromptText("Combien de solutions voulez vous ?");
        champK.setDisable(true);

        Button buttonCreer = new Button("Créer");

        Button buttonCalculer = new Button("_Calculer");
        buttonCalculer.setMnemonicParsing(true);
        buttonCalculer.setId("buttonCalculer");
        CheckBox checkBoxLimite = new CheckBox("Limiter le nombre de calcul");
        checkBoxLimite.setDisable(true);

        ToolBar toolBar = new ToolBar(menuBar,buttonCreer,champK,checkBoxLimite,buttonCalculer);

        //Action des boutons
        buttonCalculer.setOnAction(new Controleur(toggleGroupScenario,toggleGroupSolution,champK,checkBoxLimite,buttonCalculer,menuFile,menuModifier));
        buttonCreer.setOnAction(new Controleur(toggleGroupScenario,toggleGroupSolution,champK,checkBoxLimite,buttonCalculer,menuFile,menuModifier));

        this.getChildren().addAll(toolBar,HBoxGlobal);


    }

}