package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class VueJoueurInfo extends VueJoueursInfos {
    private IJoueur joueur;
    private ImageView cube;
    public VueJoueurInfo(IJoueur joueur) {
        super (new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new CouleursJoueurs(), new BorderPane());
        this.joueur = joueur;
        this.cube = new ImageView(new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(joueur.getCouleur()) + ".png"));

        fillVue(cube);
        this.getChildren().add(vue);
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: " + couleursJoueurs.getCouleur(joueur.getCouleur()));

    }
}