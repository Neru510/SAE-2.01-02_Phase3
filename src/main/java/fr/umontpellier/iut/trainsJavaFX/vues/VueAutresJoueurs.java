package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.vues.vuesJoueursInfos.VueJoueurInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends VBox {

    private IJeu jeu;
    private VBox vue;
    public VueAutresJoueurs(IJeu jeu){
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
