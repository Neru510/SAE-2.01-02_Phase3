package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Cette classe représente la vue d'une carte.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarte extends VBox {

    private final ICarte carte;


    public VueCarte(ICarte carte) {
        this.carte = carte;
        Circle cercle = new Circle(10);
        cercle.setFill(Color.WHITE);
        cercle.setStroke(Color.BLACK);
        StackPane conteur = new StackPane(cercle);

        Image image = new Image(creerURL(carte));
        ImageView imageCarte = new ImageView(image);
        imageCarte.setPreserveRatio(true);
        imageCarte.setFitWidth(130);
        getChildren().add(imageCarte);
        setAlignment(Pos.CENTER);

    }
    public void setCarteChoisieListener(EventHandler<MouseEvent> quandCarteEstChoisie) {
        setOnMouseClicked(quandCarteEstChoisie);
    }
    public String creerURL(ICarte c){
        String nomCarte = c.getNom();
        nomCarte = nomCarte.toLowerCase();
        nomCarte = nomCarte.replaceAll(" ", "_");
        nomCarte = nomCarte.replaceAll("é", "e");
        nomCarte = nomCarte.replaceAll("ô", "o");
        nomCarte = "images/cartes/" + nomCarte + ".jpg";
        return nomCarte;
    }

    public ICarte getCarte(){
        return carte;
    }

}
