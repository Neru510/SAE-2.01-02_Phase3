package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.TypeCarte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.suitechoix.*;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VueInstructionsBoutons extends VBox {
    private IJeu jeu;
    private Label instruction;
    private Button passer;
    private HBox cartes;
    public VueInstructionsBoutons(IJeu jeu){
        this.jeu = jeu;
        this.instruction = new Label();
        this.passer = new Button("Passer");
        this.cartes = new HBox();
        VBox vue = new VBox(instruction, passer, cartes);
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

    public void modifierListeButtonEchangeur(List<Carte> buttons){
        Set<String> cartesNoms = new HashSet<>();
        for (Carte c : buttons){
            ImageView imageView = new ImageView(new Image(VueJoueurCourant.creerURL(c)));
            cartes.getChildren().add(imageView);
            jeu.joueurCourantProperty().getValue().mainProperty().remove(c);
            cartesNoms.add(c.getNom());
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(mouseEvent -> {
                buttons.remove(c);
                for (Carte c1 : buttons){
                    jeu.joueurCourantProperty().getValue().mainProperty().add(c1);
                }
                ChoixTrainEchangeur choix = new ChoixTrainEchangeur((Joueur) jeu.joueurCourantProperty().getValue(), cartesNoms);
                choix.carteEnJeuChoisie(c.getNom());
                cartes.getChildren().clear();
            });
        }
    }

    public void modifierListeButtonCentreRenseignements(){
        final int[] i = {0};
        List<Carte> buttons = new ArrayList<>();
         while (i[0] < 4 && !jeu.joueurCourantProperty().getValue().piocheProperty().isEmpty()){
            i[0]++;
            jeu.joueurCourantProperty().getValue().cartesAChoisir().add(jeu.joueurCourantProperty().getValue().piocheProperty().get(0));
            buttons.add(jeu.joueurCourantProperty().getValue().piocheProperty().remove(0));
        }
        ChoixCarteCentreDeRenseignements choix = new ChoixCarteCentreDeRenseignements((Joueur) jeu.joueurCourantProperty().getValue());
         i[0] = 0;
        for (Carte c : buttons){
            ImageView imageView = new ImageView(new Image(VueJoueurCourant.creerURL(c)));
            cartes.getChildren().add(imageView);
            jeu.joueurCourantProperty().getValue().mainProperty().remove(c);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(mouseEvent -> {
                if (i[0] == 0){
                    choix.actionCarteChoisie(c.getNom());
                    cartes.getChildren().remove(imageView);
                    i[0]++;
                }
                else {
                    jeu.joueurCourantProperty().getValue().piocheProperty().add(c);
                    cartes.getChildren().remove(imageView);
                }
                if (cartes.getChildren().isEmpty()){
                    choix.passer();
                    System.out.println(jeu.joueurCourantProperty().getValue().piocheProperty());
                }
            });
            passer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!cartes.getChildren().isEmpty()){
                        choix.passer();
                        cartes.getChildren().clear();
                    }
                    else {
                        jeu.passerAEteChoisi();
                    }
                }
            });
        }
    }
}
