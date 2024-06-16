package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.TrainsIHM;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Comparator;

import static javafx.collections.FXCollections.observableArrayList;

public class VueResultats extends GridPane {

    private TrainsIHM ihm;

    public VueResultats(TrainsIHM ihm) {
        this.ihm = ihm;

        ihm.getJeu().finDePartieProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afficherResultats();
            }
        });
    }

    public void afficherResultats() {
        Stage fenetreResultats = new Stage();
        fenetreResultats.initModality(Modality.APPLICATION_MODAL); //fenêtre modale (qui devient principale) obliger de la fermer pour interagir avec les autres fenêtres)
        fenetreResultats.setTitle("Résultats");

        VBox vbox = new VBox();

        ObservableList<? extends IJoueur> joueursTries = FXCollections.observableArrayList(ihm.getJeu().getJoueurs());
        joueursTries.sort(Comparator.comparing(IJoueur::getScoreTotal).reversed());

        Label plan = new Label(" Joueur :" + " Totale :");
        vbox.getChildren().add(plan);

        for (IJoueur joueur : joueursTries) {
            Label label = new Label(joueur.getNom() + " " + joueur.getScoreTotal());
            vbox.getChildren().add(label);
        }

        Button boutonRelancer = new Button("Relancer le jeu");
        boutonRelancer.setOnAction(e -> {
            fenetreResultats.close();
            Platform.exit();
            this.ihm = new TrainsIHM();
            ihm.start(ihm.getPrimaryStage());
        });

        Button boutonEteindre = new Button("Éteindre le jeu");
        boutonEteindre.setOnAction(e -> ihm.arreterJeu());

        vbox.getChildren().addAll(boutonRelancer, boutonEteindre);

        Scene scene = new Scene(vbox);
        fenetreResultats.setScene(scene);
        fenetreResultats.show();
    }

}
