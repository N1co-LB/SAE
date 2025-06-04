package vue;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VBoxSolution  extends VBox {
    private final Label labelTypeSolution = new Label("Solution Simple");
    private final Label labelSolution = new Label("Solution : " );
    private final Label labelDistance = new Label("Distance parcourue : ");

    public VBoxSolution() {
        labelTypeSolution.setId("labelTypeSolution");
        labelSolution.setId("labelSolution");
        labelDistance.setId("labelDistance");
        getChildren().addAll(labelTypeSolution, labelSolution,labelDistance);
    }


    public void setLabelSolution(String parString) {
        labelSolution.setText("Solution : " + "\n" + parString);
    }


    public void setLabelTypeSolution(String parString) {
        labelTypeSolution.setText("Solution " + parString);
    }


    public void setLabelDistance(int parDistance) {
        labelDistance.setText("Distance parcourue : " + parDistance + "km");
    }

    public void setLabelDistance2(int parDistance) {
        labelDistance.setText("Plus petite distance : " + parDistance + "km");
    }

}
