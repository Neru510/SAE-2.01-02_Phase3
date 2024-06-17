package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.ListeDeCartes;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.TypeCarte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.EcarteHorairesEstivaux;
import fr.umontpellier.iut.trainsJavaFX.mecanique.etatsJoueur.suitechoix.ChoixPersonnelDeGare;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.*;

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
    private HBox vueGauche;
    private VuePlateau plateau;
    private VueReserve vueReserve;
    private VuePioche vuePioche;
    private VueDefausse vueDefausse;
    private BooleanProperty checkAction;
    private VueInstructionsBoutons infos;


    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        vueReserve = new VueReserve(jeu);
        plateau = new VuePlateau();
        vueCentre = new VBox(plateau);
        vuePioche = new VuePioche(jeu);
        vueDefausse = new VueDefausse(jeu);
        infos = new VueInstructionsBoutons(jeu);
        vueGauche();
        vueDroite();
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(70);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        this.getColumnConstraints().add(col1);
        checkAction = new SimpleBooleanProperty(false);


        vueBas = new HBox();
        add(vueReserve, 0, 0);
        add(vueGauche, 0,1);
        add(vueCentre, 1, 0);
        add(vueDroit, 2, 0, 1, 2);

        this.setStyle("-fx-background-color: #2c2c2c");


        vueBas();
        vueCentre();
        creerBindings();

    }

    public void vueDroite(){
        ScrollPane conteneurInfo = new ScrollPane(infos);
        vueDroit = new VBox(new VueAutresJoueurs(jeu), conteneurInfo, new VueJoueurCourant(jeu));
        vueDroit.minWidthProperty().bind(this.widthProperty().divide(4));
        HBox.setHgrow(vueDroit, Priority.NEVER);
        vueDroit.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(infos, Priority.ALWAYS);
        VBox.setVgrow(conteneurInfo, Priority.ALWAYS);
        //vueDroit.prefHeightProperty().bind(this.heightProperty());
    }

    public void vueGauche(){
        //vueGauche.prefWidthProperty().bind(this.widthProperty().divide(3));
        vueGauche = new HBox(vuePioche, vueDefausse);
        vuePioche.prefHeightProperty().bind(vueGauche.heightProperty());
        vuePioche.prefWidthProperty().bind(vueGauche.widthProperty());

        vueDefausse.prefHeightProperty().bind(vueGauche.heightProperty());
        vueDefausse.prefWidthProperty().bind(vueGauche.widthProperty());

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
            ImageView imageView = new ImageView(VueJoueurCourant.creerURL(c));
            imageView.fitHeightProperty().bind(this.heightProperty().divide(4));
            imageView.setPreserveRatio(true);
            vueBas.getChildren().add(imageView);
            map.put(imageView, c);

            if (jeu.joueurCourantProperty().getValue().nbJetonsRailsProperty().getValue() < 20){
                imageView.setOnMouseClicked(mouseEvent -> {
                    Carte t = map.get(imageView);
                    checkAction.setValue(false);
                    jeu.joueurCourantProperty().getValue().uneCarteDeLaMainAEteChoisie(t.getNom());
                    vueBas.getChildren().remove(imageView);
                    if (t.getNom().contains("Personnel de gare")){
                        Button ferraille = new Button("Ferraille");
                        infos.getChildren().add(ferraille);
                        Button argent = new Button("Argent");
                        infos.getChildren().add(argent);
                        Button piocher = new Button("Deck");
                        infos.getChildren().add(piocher);
                        ferraille.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                ChoixPersonnelDeGare c = new ChoixPersonnelDeGare((Joueur) jeu.joueurCourantProperty().getValue());
                                c.carteEnMainChoisie("Ferraille");
                                infos.getChildren().remove(ferraille);
                                infos.getChildren().remove(argent);
                                infos.getChildren().remove(piocher);
                            }
                        });
                        argent.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                ChoixPersonnelDeGare c = new ChoixPersonnelDeGare((Joueur) jeu.joueurCourantProperty().getValue());
                                c.recevoirArgent();
                                infos.getChildren().remove(ferraille);
                                infos.getChildren().remove(argent);
                                infos.getChildren().remove(piocher);
                            }
                        });
                        piocher.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                ChoixPersonnelDeGare c = new ChoixPersonnelDeGare((Joueur) jeu.joueurCourantProperty().getValue());
                                c.piocheChoisie();
                                infos.getChildren().remove(ferraille);
                                infos.getChildren().remove(argent);
                                infos.getChildren().remove(piocher);
                            }
                        });
                    }
                    if (t.getNom().contains("Parc d'attractions")){
                        ListeDeCartes listeDeCartes = ((Joueur) jeu.joueurCourantProperty().getValue()).getCartesEnJeu();
                        List<Carte> liste = new ArrayList<>();
                        for (Carte carte : listeDeCartes){
                            if (carte.hasType(TypeCarte.TRAIN)){
                                liste.add(carte);
                            }
                        }
                        infos.modifierListeButtonsParcAttractions(liste);
                    }
                    if (t.getNom().contains("Horaires estivaux")){
                        Button ecarter = new Button("Écarter");
                        infos.getChildren().add(ecarter);
                        ecarter.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                EcarteHorairesEstivaux ecarteHorairesEstivaux = new EcarteHorairesEstivaux((Joueur) jeu.joueurCourantProperty().getValue());
                                ecarteHorairesEstivaux.carteEnJeuChoisie("Horaires estivaux");
                                infos.getChildren().remove(ecarter);
                            }
                        });
                    }
                    if (t.getNom().contains("Feu de signalisation")){
                        t.jouer((Joueur) jeu.joueurCourantProperty().getValue());
                        if (!jeu.joueurCourantProperty().getValue().piocheProperty().get().isEmpty()){
                            infos.modifierListeButtonsFeuDeSignalisation(jeu.joueurCourantProperty().get().piocheProperty().remove(0));
                        }
                        else if (!jeu.joueurCourantProperty().getValue().defausseProperty().get().isEmpty()){
                            infos.modifierListeButtonsFeuDeSignalisation(jeu.joueurCourantProperty().get().defausseProperty().remove(0));
                        }
                    }
                    if (t.getNom().contains("Remorquage")){
                        ObservableList<Carte> cartes = jeu.joueurCourantProperty().get().defausseProperty().getValue();
                        List<Carte> carteList = new ArrayList<>();
                        for (Carte carte : cartes){
                            if (carte.hasType(TypeCarte.TRAIN)) {
                                carteList.add(carte);
                            }
                        }
                        infos.modifierListeButtonRemorquage(carteList);
                    }
                    if (t.getNom().contains("Échangeur")){
                        List<Carte> cartes = new ArrayList<>();
                        for (Carte carte : jeu.joueurCourantProperty().getValue().mainProperty().get()){
                            if (carte.hasType(TypeCarte.TRAIN)){
                                cartes.add(carte);
                            }
                        }
                        infos.modifierListeButtonEchangeur(cartes);
                    }
                });
            }
        }

        vueBas.setStyle("-fx-background-color: #efefef");
        vueBas.prefWidthProperty().bind(this.widthProperty());
        vueBas.setAlignment(Pos.CENTER);
        vueBas.prefHeightProperty().bind(this.heightProperty().divide(4));
        vueBas.prefWidthProperty().bind(this.widthProperty());
        vueBas.setSpacing(10);
        HBox.setHgrow(vueBas, Priority.NEVER);
        this.getChildren().remove(vueBas);
        add(vueBas, 1,1);
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
}
