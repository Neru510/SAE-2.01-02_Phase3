package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.ListeDeCartes;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.tournormal.AchatCarte;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.Map;


public class VueReserve extends HBox {


    private final ListeDeCartes reserve;

    private final Map<String, IntegerProperty> taillePileParCarte;

    private FlowPane vue;


    public VueReserve(IJeu jeu) {
        this.reserve = jeu.getReserve();
        this.taillePileParCarte = jeu.getTaillesPilesReserveProperties();
        vue = new FlowPane();
        ScrollPane conteneur = new ScrollPane();

        vue.setPrefWrapLength(50);

        vue.setVgap(10);
        vue.setHgap(10);


        for (Carte carte : reserve){
            VueCarte imageCarte = new VueCarte(carte, jeu);
            setAlignment(Pos.TOP_LEFT);
            vue.getChildren().add(imageCarte);
            imageCarte.setOnMouseClicked(mouseEvent -> {
                ICarte t = imageCarte.getCarte();
                EtatJoueur etatJoueur = new AchatCarte((Joueur) jeu.joueurCourantProperty().getValue());
                etatJoueur.carteEnReserveChoisie(t.getNom());
            });
        }
        vue.prefWidthProperty().bind(conteneur.widthProperty());
        conteneur.setContent(vue);
        HBox.setHgrow(conteneur, Priority.ALWAYS);
        getChildren().add(conteneur);
    }
}