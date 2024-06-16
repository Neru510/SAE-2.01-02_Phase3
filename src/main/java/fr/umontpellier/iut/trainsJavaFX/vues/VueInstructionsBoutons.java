package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.suitechoix.ChoixCarteRemorquage;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.suitechoix.ChoixFeuDeSignalisation;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.suitechoix.ChoixTrainParcAttraction;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VueInstructionsBoutons extends VBox {
    private IJeu jeu;
    private Label instruction;
    private Button passer;
    private HBox buttons;
    private HBox cartes;
    public VueInstructionsBoutons(IJeu jeu){
        this.jeu = jeu;
        this.instruction = new Label();
        this.passer = new Button("Passer");
        this.buttons = new HBox();
        this.cartes = new HBox();
        VBox vue = new VBox(instruction, passer, buttons, cartes);
        this.getChildren().add(vue);
        creerBindings();
        this.setStyle("-fx-background-color: #f1f1f1; -fx-end-margin: 10; -fx-start-margin: 10");
        vue.setSpacing(10);
        this.setPadding(new Insets(10));
    }

    public void creerBindings(){
        instruction.textProperty().bind(jeu.instructionProperty());
        passer.setOnMouseClicked(mouseEvent -> jeu.passerAEteChoisi());
    }

    public void modifierListeButtonsParcAttractions(List<Carte> buttons){
        Set<String> noms = new HashSet<>();
        for (Carte t : buttons){
            noms.add(t.getNom());
        }
        for (Carte t : buttons){
            ImageView imageView = new ImageView(new Image(VueJoueurCourant.creerURL(t)));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(200);
            imageView.setOnMouseClicked(mouseEvent -> {
                ChoixTrainParcAttraction choix = new ChoixTrainParcAttraction((Joueur) jeu.joueurCourantProperty().getValue(), noms);
                choix.carteEnJeuChoisie(t.getNom());
                cartes.getChildren().clear();
            });
            this.cartes.getChildren().add(imageView);
        }
    }

    public void modifierListeButtonsFeuDeSignalisation(Carte t){
        Button defausser = new Button("Défausser");
        Button piocher = new Button("Remettre sur le deck");
        cartes.getChildren().addAll(defausser, piocher);
        ImageView imageView = new ImageView(new Image(VueJoueurCourant.creerURL(t)));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(200);
        this.cartes.getChildren().add(imageView);
        jeu.joueurCourantProperty().getValue().cartesAChoisir().add(t);
        defausser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChoixFeuDeSignalisation choix = new ChoixFeuDeSignalisation((Joueur) jeu.joueurCourantProperty().getValue());
                choix.defausser();
                cartes.getChildren().clear();
            }
        });
        piocher.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChoixFeuDeSignalisation choix = new ChoixFeuDeSignalisation((Joueur) jeu.joueurCourantProperty().getValue());
                choix.piocheChoisie();
                cartes.getChildren().clear();
            }
        });

    }

    public void modifierListeButtonRemorquage(List<Carte> buttons){
        Set<String> noms = new HashSet<>();
        for (Carte t : buttons){
            noms.add(t.getNom());
            jeu.joueurCourantProperty().getValue().defausseProperty().retirer(t.getNom());
        }
        for (Carte t : buttons){
            ImageView imageView = new ImageView(new Image(VueJoueurCourant.creerURL(t)));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(200);
            imageView.setOnMouseClicked(mouseEvent -> {
                ChoixCarteRemorquage choix = new ChoixCarteRemorquage((Joueur) jeu.joueurCourantProperty().getValue());
                choix.actionCarteChoisie(t.getNom());
                buttons.remove(t);
                for (Carte c : buttons){
                    jeu.joueurCourantProperty().getValue().defausseProperty().add(c);
                }
                cartes.getChildren().clear();
            });
            this.cartes.getChildren().add(imageView);
        }
    }
}
