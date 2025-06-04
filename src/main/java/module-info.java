module com.example.sae_dev {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.sae_dev to javafx.fxml;
    exports vue;
    //exports modele;
}