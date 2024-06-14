package fr.umontpellier.iut.trainsJavaFX.vues.vuesJoueursInfos;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.vues.CouleursJoueurs;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class VueJoueurCourantInfo extends VueJoueursInfos {
    private IJeu jeu;
    private ImageView cube;

    public VueJoueurCourantInfo(IJeu jeu){
        super (new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new CouleursJoueurs(), new BorderPane());
        this.jeu = jeu;
        this.cube = new ImageView(new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(jeu.joueurCourantProperty().getValue().getCouleur()) + ".png"));
        creerBindings();
        fillVue(cube);
        this.getChildren().add(vue);
        this.setPadding(new Insets(10));

    }

    public void creerBindings(){

        cube.imageProperty().bind(new ObjectBinding<>(){
            {
                super.bind(jeu.joueurCourantProperty());
            }

            @Override
            protected Image computeValue() {
                return new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(jeu.joueurCourantProperty().getValue().getCouleur()) + ".png");
            }
        });

        StringBinding cubesBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return Integer.toString(jeu.joueurCourantProperty().getValue().nbJetonsRailsProperty().getValue());
            }
        };

        StringBinding nomJoueurBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return jeu.joueurCourantProperty().getValue().getNom();
            }
        };

        StringBinding argentBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return Integer.toString(jeu.joueurCourantProperty().getValue().argentProperty().getValue());
            }
        };

        StringBinding railsBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return Integer.toString(jeu.joueurCourantProperty().getValue().pointsRailsProperty().getValue());
            }
        };

        StringBinding nbPointVictoireBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return Integer.toString(jeu.joueurCourantProperty().getValue().scoreProperty().getValue());
            }
        };

        StringBinding cartesEnMainBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                int size = jeu.joueurCourantProperty().getValue().mainProperty().size();
                return Integer.toString(size);
            }
        };

        StringBinding piocheBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                int size = jeu.joueurCourantProperty().getValue().piocheProperty().size();
                return Integer.toString(size);
            }
        };

        StringBinding defausseBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                int size = jeu.joueurCourantProperty().getValue().defausseProperty().size();
                return Integer.toString(size);
            }
        };

        this.styleProperty().bind(new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return "-fx-background-color: " + couleursJoueurs.getCouleur(jeu.joueurCourantProperty().getValue().getCouleur());
            }
        });

        argentLabel.textProperty().bind(argentBinding);
        cubes.textProperty().bind(cubesBinding);
        railsLabel.textProperty().bind(railsBinding);
        pointsVictoire.textProperty().bind(nbPointVictoireBinding);
        cartesEnMain.textProperty().bind(cartesEnMainBinding);
        pioche.textProperty().bind(piocheBinding);
        defausse.textProperty().bind(defausseBinding);

        nomJoueur.textProperty().bind(nomJoueurBinding);

    }
}