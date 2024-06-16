package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.EtatJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.tournormal.CarteEnMainChoisie;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

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
    private VueReserve vueGauche;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        vueGauche = new VueReserve(jeu);
        plateau = new VuePlateau();
        vueCentre = new VBox(plateau);
        Button passer = new Button("Passer");
        Label instruction = new Label();
        instruction.textProperty().bind(jeu.instructionProperty());
        passer.setOnMouseClicked(mouseEvent -> jeu.passerAEteChoisi());
        vueDroite();
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(70);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        this.getColumnConstraints().add(col1);



        vueBas = new HBox();
        add(vueGauche, 0, 0);
        add(vueCentre, 1, 0);
        add(vueDroit, 2, 0, 1, 2);

        this.setStyle("-fx-background-color: #2c2c2c");

        vueBas();
        vueGauche();
        vueCentre();
        creerBindings();
    }

    public void vueDroite(){
        VBox instructions = new VueInstructionsBoutons(jeu);
        vueDroit = new VBox(new VueAutresJoueurs(jeu), instructions, new VueJoueurCourant(jeu));
        vueDroit.minWidthProperty().bind(this.widthProperty().divide(5));
        HBox.setHgrow(vueDroit, Priority.NEVER);
        vueDroit.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(instructions, Priority.ALWAYS);
        //vueDroit.prefHeightProperty().bind(this.heightProperty());
    }

    public void vueGauche(){
        //vueGauche.prefWidthProperty().bind(this.widthProperty().divide(3));
        vueGauche.prefHeightProperty().bind(this.heightProperty());
        VBox.setVgrow(vueGauche, Priority.ALWAYS);
    }

    public void vueCentre(){
        vueCentre.minWidthProperty().bind(this.widthProperty().divide(5));
        plateau.prefWidthProperty().bind(vueCentre.widthProperty());
        plateau.prefHeightProperty().bind(vueCentre.heightProperty());
        vueCentre.setAlignment(Pos.CENTER);
    }

    public void vueBas(){
        vueBas.getChildren().clear();
        Map<ImageView, Carte> map = new HashMap<>();
        for (Carte c : jeu.joueurCourantProperty().getValue().mainProperty().get()){
            ImageView imageView = new ImageView(creerURL(c));
            imageView.fitHeightProperty().bind(this.heightProperty().divide(4));
            imageView.setPreserveRatio(true);
            vueBas.getChildren().add(imageView);
            map.put(imageView, c);
            if (jeu.joueurCourantProperty().getValue().nbJetonsRailsProperty().getValue() < 20){
                imageView.setOnMouseClicked(mouseEvent -> {
                    Carte t = map.get(imageView);
                    EtatJoueur etat = new CarteEnMainChoisie((Joueur) jeu.joueurCourantProperty().getValue());
                    etat.carteEnMainChoisie(t.getNom());
                    vueBas.getChildren().remove(imageView);
                });
            }
        }

        vueBas.setStyle("-fx-background-color: #efefef");
        vueBas.prefWidthProperty().bind(this.widthProperty());
        vueBas.setAlignment(Pos.BOTTOM_CENTER);
        vueBas.prefHeightProperty().bind(this.heightProperty().divide(4));
        vueBas.prefWidthProperty().bind(this.widthProperty());
        vueBas.setSpacing(10);
        HBox.setHgrow(vueBas, Priority.NEVER);
        this.getChildren().remove(vueBas);
        add(vueBas, 1,1);
    }

    public String creerURL(Carte c){
        String nomCarte = c.getNom();
        nomCarte = nomCarte.replaceAll(" ", "_");
        nomCarte = nomCarte.replaceAll("é", "e");
        nomCarte = nomCarte.replaceAll("ô", "o");
        char premiereLettre = nomCarte.charAt(0);
        char premiereLettreCopie = nomCarte.charAt(0);
        premiereLettre = Character.toLowerCase(premiereLettre);
        nomCarte = nomCarte.replaceFirst(String.valueOf(premiereLettreCopie), String.valueOf(premiereLettre));
        nomCarte = "images/cartes/" + nomCarte + ".jpg";
        return nomCarte;
    }

    public void creerBindings() {

        plateau.prefWidthProperty().bind(vueCentre.widthProperty());
        //vueCentre.setAlignment(Pos.CENTER);
        //plateau.prefHeightProperty().bind(vueCentre.heightProperty());
        plateau.creerBindings();
        jeu.joueurCourantProperty().getValue().mainProperty().addListener((ListChangeListener<Carte>) change -> {
            this.getChildren().remove(vueBas);
            vueBas();
        });
        jeu.joueurCourantProperty().addListener((observableValue, joueur, t1) -> vueBas());
        jeu.joueurCourantProperty().addListener((observableValue, joueur, t1) -> vueBas());
    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> System.out.println("Passer a été demandé"));

}
