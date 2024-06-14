package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.StringBinding;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, ses cartes en main, son score, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends GridPane {

    private final IJeu jeu;
    private HBox vueBas;
    private VBox vueDroit;
    private VBox vueCentre;
    private VuePlateau plateau;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;

        plateau = new VuePlateau();
        vueCentre = new VBox(plateau);
        Button passer = new Button("Passer");
        Label instruction = new Label();
        instruction.textProperty().bind(jeu.instructionProperty());
        passer.setOnMouseClicked(mouseEvent -> jeu.passerAEteChoisi());
        vueDroite();
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(70);

        HBox vueHaut = new HBox();
        vueHaut.setMinHeight(500);
        add(new HBox(), 0, 0);


        vueBas = new HBox();
        //add(vueGauche, 0, 0);
        add(vueCentre, 1, 0);
        add(vueDroit, 2, 0, 1, 2);

        this.setStyle("-fx-background-color: #2c2c2c");

        vueBas();
    }

    public void vueDroite(){
        VBox instructions = new VueInstructionsBoutons(jeu);
        vueDroit = new VBox(new VueAutresJoueurs(jeu), instructions, new VueJoueurCourant(jeu));
        vueDroit.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(instructions, Priority.ALWAYS);
        vueDroit.prefHeightProperty().bind(this.heightProperty());
    }

    public void vueBas(){
        vueBas = new HBox();
        for (Carte t : jeu.joueurCourantProperty().getValue().mainProperty().get()){
            String nomCarte = t.getNom();
            nomCarte = nomCarte.toLowerCase();
            nomCarte = nomCarte.replaceAll(" ", "_");
            ImageView imageView = new ImageView(new Image("images/cartes/" + nomCarte + ".jpg"));
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            vueBas.getChildren().add(imageView);
        }
        vueBas.setStyle("-fx-background-color: #efefef");
        vueBas.prefWidthProperty().bind(this.widthProperty());
        vueBas.setAlignment(Pos.BOTTOM_CENTER);
        vueBas.setMaxHeight(this.getPrefHeight());
        add(vueBas, 1,1);
        vueBas.setSpacing(10);
    }

    public void creerBindings() {
        plateau.prefWidthProperty().bind(vueCentre.widthProperty());
        //vueCentre.setAlignment(Pos.CENTER);
        //plateau.prefHeightProperty().bind(vueCentre.heightProperty());
        plateau.creerBindings();
        jeu.joueurCourantProperty().getValue().mainProperty().addListener((ListChangeListener<Carte>) change -> {
            this.getChildren().remove(vueBas);
            vueBas.getChildren().clear();
            vueBas();
        });
    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> System.out.println("Passer a été demandé"));

}
