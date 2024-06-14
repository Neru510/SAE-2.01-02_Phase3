package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;

public class VueJoueursAutresInfos extends VBox {
    private IJeu jeu;
    private VBox vue;
    public VueJoueursAutresInfos(IJeu jeu){
        this.jeu = jeu;
        this.vue = new VBox();
        for (IJoueur joueur : jeu.getJoueurs()){
            if (!joueur.equals(jeu.joueurCourantProperty().getValue())){
                vue.getChildren().add(new VueJoueurInfo(joueur));
            }
        }
        creerBindings();
        this.getChildren().add(vue);
    }

    public void creerBindings(){
        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur Ijoueur, IJoueur t1) {
                vue.getChildren().clear();
                for (IJoueur joueur : jeu.getJoueurs()){
                    if (!joueur.equals(jeu.joueurCourantProperty().getValue())){
                        vue.getChildren().add(new VueJoueurInfo(joueur));
                    }
                }
            }
        });
    }
}
