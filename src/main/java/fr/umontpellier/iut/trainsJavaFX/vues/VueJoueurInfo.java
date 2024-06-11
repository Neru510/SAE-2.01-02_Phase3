package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class VueJoueurInfo extends HBox {
    private ChangeListener<IJoueur> joueurChangeListener;
    private IJeu jeu;
    private HBox vue;

    private Image cube;
    private Label argentLabel;
    private Label railsLabel;
    private Label pointsVictoire;
    private Label cubes;
    private Label cartesEnMain;
    private Label pioche;
    private Label defausse;

    CouleursJoueurs couleursJoueurs;
    Label nomJoueur;


    public VueJoueurInfo(IJeu jeu){
        this.jeu = jeu;
        this.vue = new HBox();
        this.cube = null;
        this.couleursJoueurs = new CouleursJoueurs();
        this.nomJoueur = new Label();
        this.argentLabel = new Label();
        this.railsLabel = new Label();
        this.pointsVictoire = new Label();
        this.cubes = new Label();
        this.cartesEnMain = new Label();
        this.pioche = new Label();
        this.defausse = new Label();
        cube = new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(jeu.joueurCourantProperty().getValue().getCouleur()) + ".png");
        creerBindings();
        fillVue();
        this.getChildren().add(vue);
        this.setPadding(new Insets(10));
    }

    public void creerBindings(){

        joueurChangeListener = (observableValue, joueur, t1) -> {
            cube = new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(jeu.joueurCourantProperty().getValue().getCouleur()) + ".png");
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

        StringBinding cubesBinding = new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return jeu.joueurCourantProperty().getValue().nbJetonsRailsProperty().toString();
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
                System.out.println(couleursJoueurs.getCouleur(jeu.joueurCourantProperty().getValue().getCouleur()));
                System.out.println(couleursJoueurs.getCouleurAnglais(jeu.joueurCourantProperty().getValue().getCouleur()));
                return "-fx-background-color: " + couleursJoueurs.getCouleur(jeu.joueurCourantProperty().getValue().getCouleur());
            }
        });

        argentLabel.textProperty().bind(argentBinding);
        railsLabel.textProperty().bind(railsBinding);
        pointsVictoire.textProperty().bind(nbPointVictoireBinding);
        cubes.textProperty().bind(cubesBinding);
        cartesEnMain.textProperty().bind(cartesEnMainBinding);
        pioche.textProperty().bind(piocheBinding);
        defausse.textProperty().bind(defausseBinding);

        nomJoueur.textProperty().bind(nomJoueurBinding);

        jeu.joueurCourantProperty().addListener(joueurChangeListener);
    }

    public void fillVue(){
        vue.getChildren().add(nomJoueur);
        List<Label> labelList = new ArrayList<>();
        labelList.add(argentLabel);
        labelList.add(railsLabel);
        labelList.add(pointsVictoire);
        labelList.add(new Label(""));
        labelList.add(cartesEnMain);
        labelList.add(pioche);
        labelList.add(defausse);
        List<ImageView> viewList = new ArrayList<>();
        viewList.add(new ImageView(new Image("images/boutons/coins.png")));
        viewList.add(new ImageView(new Image("images/boutons/rail.png")));
        viewList.add(new ImageView(new Image("images/boutons/star.png")));
        viewList.add(new ImageView(cube));
        viewList.add(new ImageView(new Image("images/icons/main.png")));
        viewList.add(new ImageView(new Image("images/icons/pioche.png")));
        viewList.add(new ImageView(new Image("images/icons/cards.png")));
        for (int i = 0; i < labelList.size(); i++){

            viewList.get(i).setFitWidth(20);
            viewList.get(i).setFitHeight(20);
            HBox hbox = new HBox(viewList.get(i), labelList.get(i));
            vue.getChildren().add(hbox);
        }
        vue.setAlignment(Pos.BASELINE_CENTER);
        vue.setSpacing(10);
    }

}
