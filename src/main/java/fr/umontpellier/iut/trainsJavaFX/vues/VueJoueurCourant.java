package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.vues.vuesJoueursInfos.VueJoueurCourantInfo;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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
    @FXML
    private HBox cartesRecuesVue;

    private VBox conteneur;
    private HBox cartesRecues;
    private ArrayList<ImageView> cartesR;
    private StringProperty couleur;
    private CouleursJoueurs couleursJoueurs;
    ListChangeListener<Carte> changeListener;

    public VueJoueurCourant(IJeu jeu){
        this.jeu = jeu;
        this.joueurCourantInfo = new VueJoueurCourantInfo(jeu, jeu.joueurCourantProperty());
        this.cartesenJeuVue = new HBox();
        this.cartesRecuesVue = new HBox();
        this.couleur = new SimpleStringProperty();
        this.cartesR = new ArrayList<>();
        this.couleursJoueurs = new CouleursJoueurs();
        this.cartesRecues = new HBox();
        this.cartesEnJeu = new HBox();
        changeListener = null;
        Pane separator = new Pane();
        separator.setMinHeight(15);
        this.conteneur = new VBox(cartesenJeuVue, separator, cartesRecuesVue);
        this.getChildren().addAll(joueurCourantInfo, conteneur);

        creerBindings();
        style();
    }

    public void creerBindings(){
        jeu.joueurCourantProperty().addListener((observableValue, joueur, t1) -> {
            t1.cartesEnJeuProperty().addListener((ListChangeListener<Carte>) change -> {
                cartesenJeuVue.getChildren().clear();
                int i = 0;
                for (Carte c : t1.cartesEnJeuProperty().get()) {
                    i++;
                    ImageView imageView = new ImageView(new Image(creerURL(c)));
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);
                        VBox.setMargin(imageView, new Insets(120));

                    cartesenJeuVue.getChildren().add(imageView);
                }
            });
            t1.cartesRecuesProperty().addListener((ListChangeListener<Carte>) change -> {
                cartesRecuesVue.getChildren().clear();
                int i = 0;
                for (Carte c : t1.cartesRecuesProperty().get()) {
                    i++;
                    ImageView imageView = new ImageView(new Image(creerURL(c)));
                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);
                    VBox.setMargin(imageView, new Insets(120));
                    cartesRecuesVue.getChildren().add(imageView);
                }
            });

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
        cartesenJeuVue.setStyle("-fx-background-color: white; -fx-background-radius: 10");
        cartesenJeuVue.setSpacing(-120);

        cartesRecuesVue.setStyle("-fx-background-color: white; -fx-background-radius: 10");
        cartesRecuesVue.setSpacing(-120);

        conteneur.setPadding(new Insets(10, 10, 10, 10));


        cartesenJeuVue.setPadding(new Insets(10));
        StackPane carteJeu = new StackPane(cartesEnJeu);
        StackPane carteRecu = new StackPane(cartesRecues);

        cartesenJeuVue.getChildren().add(carteJeu);
        cartesRecuesVue.getChildren().add(carteRecu);
        cartesenJeuVue.prefWidthProperty().bind(conteneur.widthProperty());

        HBox.setHgrow(cartesenJeuVue, Priority.ALWAYS);
        HBox.setHgrow(cartesRecuesVue, Priority.ALWAYS);
        cartesenJeuVue.setMinHeight(220);
        cartesRecuesVue.setMinHeight(220);

        conteneur.prefWidthProperty().bind(this.widthProperty());
    }

    public static String creerURL(ICarte c){
        String nomCarte = c.getNom().toLowerCase().replaceAll(" ", "_").replaceAll("é", "e").replaceAll("ô", "o");
        return "images/cartes/" + nomCarte + ".jpg";
    }

}
