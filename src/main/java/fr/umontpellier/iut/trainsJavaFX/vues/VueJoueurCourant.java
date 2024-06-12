package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    private IJeu jeu;
    @FXML
    private VueJoueurCourantInfo joueurCourantInfo;
    @FXML
    private HBox cartesenJeuVue;
    private HBox cartesEnJeu;
    private ArrayList<ImageView> cartesJ;
    @FXML
    private HBox cartesRecuesVue;

    private VBox conteneur2;
    private VBox conteneur1;
    private HBox cartesRecues;
    private ArrayList<ImageView> cartesR;
    private StringProperty couleur;
    private CouleursJoueurs couleursJoueurs;

    public VueJoueurCourant(IJeu jeu){
        this.jeu = jeu;
        this.joueurCourantInfo = new VueJoueurCourantInfo(jeu);
        this.cartesenJeuVue = new HBox();
        this.cartesRecuesVue = new HBox();
        this.couleur = new SimpleStringProperty();
        this.cartesR = new ArrayList<>();
        this.cartesJ = new ArrayList<>();
        this.couleursJoueurs = new CouleursJoueurs();
        this.cartesRecues = new HBox();
        this.cartesEnJeu = new HBox();
        this.conteneur1 = new VBox();
        Pane separator = new Pane();
        separator.setMinHeight(15);
        for (int i = 0; i < jeu.getJoueurs().size(); i++){

        }
        this.conteneur2 = new VBox(cartesenJeuVue, separator, cartesRecuesVue);
        this.getChildren().addAll(conteneur1, joueurCourantInfo, conteneur2);

        creerBindings();
        style();
    }

    public void creerBindings(){

        jeu.joueurCourantProperty().getValue().cartesEnJeuProperty().addListener((ListChangeListener<Carte>) change -> {
            cartesJ.clear();
            for (Carte c : jeu.joueurCourantProperty().get().cartesEnJeuProperty().get()){
                String string = c.getNom();
                string = string.replaceAll(" ", "_").toLowerCase();
                ImageView imageView = new ImageView(new Image("images/cartes" + string + ".png"));
                cartesJ.add(imageView);
            }
            cartesEnJeu.getChildren().addAll(cartesJ);
        });

        jeu.joueurCourantProperty().getValue().cartesRecuesProperty().addListener((ListChangeListener<Carte>) change -> {
            cartesR.clear();
            for (Carte c : jeu.joueurCourantProperty().get().cartesRecuesProperty().get()){
                String string = c.getNom();
                string = string.replaceAll(" ", "_").toLowerCase();
                ImageView imageView = new ImageView(new Image("images/cartes" + string + ".png"));
                cartesR.add(imageView);
            }
            cartesRecues.getChildren().clear();
            cartesRecues.getChildren().addAll(cartesR);
        });

        this.styleProperty().bind(new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return "-fx-background-color: " + couleursJoueurs.getCouleur(jeu.joueurCourantProperty().getValue().getCouleur());
            }
        });
    }

    public void style(){
        /*cartesenJeuVue.setStyle("-fx-background-color: white");
        cartesenJeuVue.setOpacity(0.5);
        StackPane carteJeu = new StackPane(cartesEnJeu);
        cartesEnJeu.setStyle("-fx-background-color: red");
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: red");
        hBox.setMinHeight(50);
        hBox.setMinWidth(50);
        cartesEnJeu.getChildren().add(hBox);

        cartesenJeuVue.getChildren().add(carteJeu);

        conteneur.setPadding(new Insets(0, 10, 10, 10));*/


        cartesenJeuVue.setStyle("-fx-background-color: white; -fx-background-radius: 10");
        cartesenJeuVue.setOpacity(0.5);

        cartesRecuesVue.setStyle("-fx-background-color: white; -fx-background-radius: 10");
        cartesRecuesVue.setOpacity(0.5);

        conteneur2.setPadding(new Insets(0, 10, 10, 10));

        cartesenJeuVue.setPadding(new Insets(10));
        StackPane carteJeu = new StackPane(cartesEnJeu);
        StackPane carteRecu = new StackPane(cartesRecues);

        /*
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: red");
        hBox.setMinHeight(50);
        hBox.setMinWidth(50);
        cartesEnJeu.getChildren().add(hBox);*/

        cartesenJeuVue.getChildren().add(carteJeu);
        cartesRecuesVue.getChildren().add(carteRecu);
        cartesenJeuVue.prefWidthProperty().bind(conteneur2.widthProperty());


        cartesenJeuVue.setMinHeight(200);
        cartesRecuesVue.setMinHeight(200);

        conteneur2.prefWidthProperty().bind(this.widthProperty());
    }

}
