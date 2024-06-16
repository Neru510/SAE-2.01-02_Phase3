package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Map;

/**
 * Cette classe représente la vue d'une carte.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteReserve extends StackPane {

    private final ICarte carte;
    private ImageView imageCarte;


    public VueCarteReserve(ICarte carte, IJeu jeu) {
        this.carte = carte;
        Circle cercle = new Circle(20);
        Label label = new Label();

        Map<String, IntegerProperty> taillePileParCarte = jeu.getTaillesPilesReserveProperties();
        IntegerProperty size = taillePileParCarte.get(carte.getNom());
        label.textProperty().bind(new StringBinding() {
            {
                super.bind(size);
            }
            @Override
            protected String computeValue() {
                return String.valueOf(size.getValue());
            }
        });
        cercle.setFill(Color.WHITE);
        cercle.setStroke(Color.BLACK);
        StackPane conteur = new StackPane(cercle);

        Image image = new Image(VueJoueurCourant.creerURL(carte));
        imageCarte = new ImageView(image);
        imageCarte.setPreserveRatio(true);
        imageCarte.setFitWidth(130);
        getChildren().addAll(imageCarte, conteur, label);
        setAlignment(Pos.CENTER);
        ImageView imageCopie = new ImageView(image);
        imageCopie.setPreserveRatio(true);
        imageCopie.setFitWidth(500);

    }
    public void setCarteChoisieListener(EventHandler<MouseEvent> quandCarteEstChoisie) {
        setOnMouseClicked(quandCarteEstChoisie);
    }

    public ICarte getCarte(){
        return carte;
    }

    public void carteMouseEntered(){
        imageCarte.setFitWidth(250);
    }
    public void carteMouseExit(){
        imageCarte.setFitWidth(130);
    }

}
