package fr.umontpellier.iut.trainsJavaFX.vues.vuesJoueursInfos;

import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.vues.CouleursJoueurs;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class VueJoueurInfo extends VueJoueursInfos {
    private IJoueur joueur;
    private ImageView cube;
    public VueJoueurInfo(IJoueur joueur) {
        super (new Label(String.valueOf(joueur.argentProperty().getValue())), new Label(String.valueOf(joueur.pointsRailsProperty().getValue())), new Label(String.valueOf(joueur.getScoreTotal())), new Label(String.valueOf(joueur.nbJetonsRailsProperty().getValue())), new Label(String.valueOf(joueur.mainProperty().getValue().size())), new Label(String.valueOf(joueur.piocheProperty().getValue().size())), new Label(String.valueOf(joueur.defausseProperty().getValue().size())), new Label(joueur.getNom()), new CouleursJoueurs(), new BorderPane());
        this.joueur = joueur;
        this.cube = new ImageView(new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(joueur.getCouleur()) + ".png"));

        fillVue(cube);
        this.getChildren().add(vue);
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: " + couleursJoueurs.getCouleur(joueur.getCouleur()));

    }
}