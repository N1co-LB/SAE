package vue;

import javafx.scene.layout.HBox;


public class HBoxGlobal extends HBox {
    private static VBoxSolution VBoxSolution;

    public HBoxGlobal() {
        super(40);
        VBoxSolution = new VBoxSolution();

        getChildren().addAll(VBoxSolution);
    }

    public static VBoxSolution getVBoxSolution() {
        return VBoxSolution;
    }

}
