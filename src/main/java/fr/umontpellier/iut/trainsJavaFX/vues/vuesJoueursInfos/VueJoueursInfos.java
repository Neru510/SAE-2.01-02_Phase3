package fr.umontpellier.iut.trainsJavaFX.vues.vuesJoueursInfos;

import fr.umontpellier.iut.trainsJavaFX.mecanique.CouleurJoueur;
import fr.umontpellier.iut.trainsJavaFX.vues.CouleursJoueurs;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;

public abstract class VueJoueursInfos extends HBox{
    protected BorderPane vue;
    protected Label argentLabel;
    protected Label railsLabel;
    protected Label pointsVictoire;
    protected Label cubes;
    protected Label cartesEnMain;
    protected Label pioche;
    protected Label defausse;

    protected CouleursJoueurs couleursJoueurs;
    protected Label nomJoueur;

    public VueJoueursInfos(Label argentLabel, Label railsLabel, Label pointsVictoire, Label cubes, Label cartesEnMain, Label pioche, Label defausse, Label nomJoueur, CouleursJoueurs couleurJoueur, BorderPane vue){
        this.argentLabel = argentLabel;
        this.railsLabel = railsLabel;
        this.pointsVictoire = pointsVictoire;
        this.cubes = cubes;
        this.cartesEnMain = cartesEnMain;
        this.pioche = pioche;
        this.defausse = defausse;
        this.nomJoueur = nomJoueur;
        this.couleursJoueurs = couleurJoueur;
        this.vue = vue;
    }

    public void fillVue(ImageView cube){
        HBox box = new HBox();
        List<Label> labelList = new ArrayList<>();
        labelList.add(argentLabel);
        labelList.add(railsLabel);
        labelList.add(pointsVictoire);
        labelList.add(cubes);
        labelList.add(cartesEnMain);
        labelList.add(pioche);
        labelList.add(defausse);
        List<ImageView> viewList = new ArrayList<>();
        viewList.add(new ImageView(new Image("images/boutons/coins.png")));
        viewList.add(new ImageView(new Image("images/boutons/rail.png")));
        viewList.add(new ImageView(new Image("images/boutons/star.png")));
        viewList.add(cube);
        viewList.add(new ImageView(new Image("images/icons/main.png")));
        viewList.add(new ImageView(new Image("images/icons/pioche.png")));
        viewList.add(new ImageView(new Image("images/icons/cards.png")));
        for (int i = 0; i < labelList.size(); i++){
            viewList.get(i).setFitWidth(20);
            viewList.get(i).setFitHeight(20);
            HBox hbox = new HBox();
            hbox.getChildren().addAll(viewList.get(i), labelList.get(i));
            box.getChildren().add(hbox);
        }
        box.setSpacing(10);
        vue.setRight(box);
        vue.setLeft(nomJoueur);
        HBox.setHgrow(vue, Priority.ALWAYS);
    }
}
