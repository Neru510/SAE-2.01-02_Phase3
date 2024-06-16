package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.stream.Collectors;

public class VueDefausse extends Pane {
    private Joueur joueurCourant;
    private ImageView deckImageView;
    private Tooltip deckTooltip;

    public VueDefausse(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;

        deckImageView = new ImageView(new Image("images/boutons/defausse.png"));

        deckImageView.fitHeightProperty().bind(this.heightProperty());
        deckImageView.fitWidthProperty().bind(this.widthProperty());

        deckImageView.setPreserveRatio(true);

        deckTooltip = new Tooltip();
        deckTooltip.textProperty().bind(Bindings.createStringBinding(() -> {
            return joueurCourant.getDefausse().stream()
                    .map(Carte::getNom)
                    .collect(Collectors.joining("\n"));
        }, joueurCourant.getDefausse()));
        Tooltip.install(deckImageView, deckTooltip);

        this.getChildren().add(deckImageView);
    }
}