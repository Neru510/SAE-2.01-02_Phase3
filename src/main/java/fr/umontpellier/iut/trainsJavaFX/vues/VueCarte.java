package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Binding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
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

import java.util.Map;

/**
 * Cette classe représente la vue d'une carte.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarte extends StackPane {

    private final ICarte carte;


    public VueCarte(ICarte carte, IJeu jeu) {
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

        Image image = new Image(creerURL(carte));
        ImageView imageCarte = new ImageView(image);
        imageCarte.setPreserveRatio(true);
        imageCarte.setFitWidth(130);
        getChildren().addAll(imageCarte, conteur, label);
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
