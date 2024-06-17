package fr.umontpellier.iut.trainsJavaFX.vues.vuesJoueursInfos;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.vues.CouleursJoueurs;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Binding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class VueJoueurCourantInfo extends VueJoueursInfos {
    private ObjectProperty<IJoueur> joueur;
    private IJeu jeu;
    private ImageView cube;
    private int i;

    public VueJoueurCourantInfo(IJeu jeu, ObjectProperty<IJoueur> joueur){
        super (new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new Label(), new CouleursJoueurs(), new BorderPane());
        this.joueur = joueur;
        this.jeu = jeu;
        this.cube = new ImageView(new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(joueur.getValue().getCouleur()) + ".png"));
        creerBindings();
        fillVue(cube);
        this.getChildren().add(vue);
        this.setPadding(new Insets(10));
    }

    public void creerBindings(){
        nomJoueur.textProperty().bind(new StringBinding() {
            {
                super.bind(jeu.joueurCourantProperty());
            }
            @Override
            protected String computeValue() {
                return jeu.joueurCourantProperty().getValue().getNom();
            }
        });
        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur joueur, IJoueur t1) {
                argentLabel.textProperty().bind(t1.argentProperty().asString());
                railsLabel.textProperty().bind(t1.pointsRailsProperty().asString());
                pointsVictoire.textProperty().bind(t1.scoreProperty().asString());
                cubes.textProperty().bind(t1.nbJetonsRailsProperty().asString());
                cube.imageProperty().bind(new ObjectBinding<>() {
                    {
                        super.bind(jeu.joueurCourantProperty());
                    }

                    @Override
                    protected Image computeValue() {
                        return new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(t1.getCouleur()) + ".png");
                    }
                });
                pioche.textProperty().bind(new StringBinding() {
                    {
                        super.bind(t1.piocheProperty());
                    }
                    @Override
                    protected String computeValue() {
                        return String.valueOf(t1.piocheProperty().size());
                    }
                });
                defausse.textProperty().bind(new StringBinding() {
                    {
                        super.bind(t1.defausseProperty());
                    }
                    @Override
                    protected String computeValue() {
                        return String.valueOf(t1.defausseProperty().size());
                    }
                });
                cartesEnMain.textProperty().bind(new StringBinding() {
                    {
                        super.bind(t1.mainProperty());
                    }
                    @Override
                    protected String computeValue() {
                        return String.valueOf(t1.mainProperty().size());
                    }
                });
            }
        });

        /*

        cube.imageProperty().unbind();
        argentLabel.textProperty().unbind();
        cubes.textProperty().unbind();
        railsLabel.textProperty().unbind();
        pointsVictoire.textProperty().unbind();
        cartesEnMain.textProperty().unbind();
        pioche.textProperty().unbind();
        defausse.textProperty().unbind();
        nomJoueur.textProperty().unbind();



        cube.imageProperty().bind(new ObjectBinding<>(){
            {
                super.bind(joueur);
            }

            @Override
            protected Image computeValue() {
                return new Image("images/icons/cube_" + couleursJoueurs.getCouleurAnglais(joueur.getValue().getCouleur()) + ".png");
            }
        });

        StringBinding cubesBinding = new StringBinding() {
            {
                super.bind(joueur.getValue().nbJetonsRailsProperty());
            }
            @Override
            protected String computeValue() {
                return Integer.toString(joueur.getValue().nbJetonsRailsProperty().getValue());
            }
        };

        StringBinding nomJoueurBinding = new StringBinding() {
            {
                super.bind(joueur);
            }
            @Override
            protected String computeValue() {
                return joueur.getValue().getNom();
            }
        };
        i = 0;


        joueur.getValue().argentProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                argentLabel.setText(String.valueOf(joueur.getValue().argentProperty().getValue()));
            }
        });


        StringBinding railsBinding = new StringBinding() {
            {
                super.bind(joueur.getValue().pointsRailsProperty());
            }
            @Override
            protected String computeValue() {
                return Integer.toString(joueur.getValue().pointsRailsProperty().getValue());
            }
        };

        StringBinding nbPointVictoireBinding = new StringBinding() {
            {
                super.bind(joueur.getValue().scoreProperty());
            }
            @Override
            protected String computeValue() {
                return Integer.toString(joueur.getValue().scoreProperty().getValue());
            }
        };

        StringBinding cartesEnMainBinding = new StringBinding() {
            {
                super.bind(joueur.getValue().mainProperty());
            }
            @Override
            protected String computeValue() {
                int size = joueur.getValue().mainProperty().size();
                return Integer.toString(size);
            }
        };

        StringBinding piocheBinding = new StringBinding() {
            {
                super.bind(joueur.getValue().piocheProperty());
            }
            @Override
            protected String computeValue() {
                int size = joueur.getValue().piocheProperty().size();
                return Integer.toString(size);
            }
        };

        StringBinding defausseBinding = new StringBinding() {
            {
                super.bind(joueur.getValue().defausseProperty());
            }
            @Override
            protected String computeValue() {
                int size = joueur.getValue().defausseProperty().size();
                return Integer.toString(size);
            }
        };

        this.styleProperty().bind(new StringBinding() {
            {
                super.bind(joueur);
            }
            @Override
            protected String computeValue() {
                return "-fx-background-color: " + couleursJoueurs.getCouleur(joueur.getValue().getCouleur());
            }
        });

        //argentLabel.textProperty().bind(argentBinding);
        cubes.textProperty().bind(cubesBinding);
        railsLabel.textProperty().bind(railsBinding);
        pointsVictoire.textProperty().bind(nbPointVictoireBinding);
        cartesEnMain.textProperty().bind(cartesEnMainBinding);
        pioche.textProperty().bind(piocheBinding);
        defausse.textProperty().bind(defausseBinding);

        nomJoueur.textProperty().bind(nomJoueurBinding);
*/
    }
}