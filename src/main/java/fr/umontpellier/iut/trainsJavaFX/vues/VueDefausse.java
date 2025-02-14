package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class VueDefausse extends StackPane {

    private ImageView imageCarte;


    public VueDefausse( IJeu jeu) {
        Label nom = new Label("Défausse");
        nom.setStyle("-fx-background-color: white");
        Circle cercle = new Circle(15);
        Label label = new Label();

        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur joueur, IJoueur t1) {
                label.textProperty().bind(new ObjectBinding<String>() {
                    {
                        super.bind(t1.defausseProperty());
                    }
                    @Override
                    protected String computeValue() {
                        return String.valueOf(t1.defausseProperty().size());
                    }
                });
            }
        });
        cercle.setFill(Color.WHITE);
        cercle.setStroke(Color.BLACK);
        StackPane conteur = new StackPane(cercle);

        Image image = new Image("images/boutons/defausse.png");
        imageCarte = new ImageView(image);
        imageCarte.setPreserveRatio(true);
        imageCarte.setFitWidth(130);
        getChildren().addAll(imageCarte, conteur, label, nom);
        setAlignment(Pos.CENTER);
        StackPane.setAlignment(nom, Pos.TOP_CENTER);
    }
}
