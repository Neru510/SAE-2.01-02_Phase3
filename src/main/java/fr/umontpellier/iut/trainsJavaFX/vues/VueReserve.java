package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.ListeDeCartes;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.tournormal.AchatCarte;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VueReserve extends HBox {


    private final ListeDeCartes reserve;

    private final Map<String, IntegerProperty> taillePileParCarte;
    private List<VueCarteReserve> vues;

    private FlowPane vue;


    public VueReserve(IJeu jeu) {
        this.reserve = jeu.getReserve();
        this.taillePileParCarte = jeu.getTaillesPilesReserveProperties();
        vue = new FlowPane();
        ScrollPane conteneur = new ScrollPane();

        vue.setPrefWrapLength(50);

        vue.setVgap(5);
        vue.setHgap(5);
        vues = new ArrayList<>();

        for (Carte carte : reserve){
            VueCarteReserve imageCarte = new VueCarteReserve(carte, jeu);
            setAlignment(Pos.TOP_LEFT);
            vues.add(imageCarte);
            imageCarte.setMinWidth(0);
            System.out.println(imageCarte.getMinWidth());
            imageCarte.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (imageCarte.getMinWidth() != 0.0){
                        ICarte t = imageCarte.getCarte();
                        EtatJoueur etatJoueur = new AchatCarte((Joueur) jeu.joueurCourantProperty().getValue());
                        etatJoueur.carteEnReserveChoisie(t.getNom());
                    }
                    imageCarte.setMinWidth(getWidth());
                    imageCarte.carteMouseEntered();
                    System.out.println(imageCarte.getMinWidth());
                }
            });

        }
        vue.getChildren().addAll(vues);
        vue.prefWidthProperty().bind(conteneur.widthProperty());
        conteneur.setContent(vue);
        HBox.setHgrow(conteneur, Priority.ALWAYS);
        getChildren().add(conteneur);
        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur joueur, IJoueur t1) {
                for (VueCarteReserve v : vues){
                    v.setMinWidth(0);
                    v.setMinHeight(0);
                    v.carteMouseExit();
                }
            }
        });
    }
}