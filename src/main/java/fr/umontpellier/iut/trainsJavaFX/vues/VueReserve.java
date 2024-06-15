package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.ListeDeCartes;
import javafx.beans.property.IntegerProperty;
import javafx.scene.layout.FlowPane;
import java.util.Map;


public class VueReserve extends FlowPane {


    private final ListeDeCartes reserve;

    private final Map<String, IntegerProperty> taillePileParCarte;


    public VueReserve() {
        this.reserve = GestionJeu.getJeu().getReserve();
        this.taillePileParCarte = GestionJeu.getJeu().getTaillesPilesReserveProperties();

        for (Carte res : reserve) {
            getChildren().add(new VueCarte(res));
        }

    }

}