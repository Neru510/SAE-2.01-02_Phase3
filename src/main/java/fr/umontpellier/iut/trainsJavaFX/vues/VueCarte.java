package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Cette classe représente la vue d'une carte.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarte extends StackPane {

    private final ICarte carte;


    public VueCarte(ICarte carte) {
        this.carte = carte;
        StackPane conteur = new StackPane();
        Circle cercle = new Circle(10);
        cercle.setFill(Color.WHITE);
        cercle.setStroke(Color.BLACK);
        Label chiffre = new Label();
        chiffre.setFont(Font.font("American typewriter", FontWeight.NORMAL, 10));
        conteur.getChildren().addAll(cercle, chiffre);
        conteur.setAlignment(Pos.CENTER);
        chiffre.toFront();
        String BonNom = correctionNomCarte(carte.getNom());
        chiffre.textProperty().bind(GestionJeu.getJeu().getTaillesPilesReserveProperties().get(carte.getNom()).asString());
        ImageView ImageCarte = new ImageView("images/cartes/" + BonNom + ".jpg");
        ImageCarte.setFitWidth(80);
        ImageCarte.setFitHeight(100);
        getChildren().addAll(ImageCarte, conteur);
        setAlignment(Pos.BASELINE_RIGHT);

    }
    public String correctionNomCarte(String nomBase) {
        String stringLowerCase = nomBase.toLowerCase();
        return stringLowerCase.replace("ô", "o").replace("É", "E").replace("é", "e").replace(" ", "_");
    }



    public void setCarteChoisieListener(EventHandler<MouseEvent> quandCarteEstChoisie) {
        setOnMouseClicked(quandCarteEstChoisie);
    }

}
