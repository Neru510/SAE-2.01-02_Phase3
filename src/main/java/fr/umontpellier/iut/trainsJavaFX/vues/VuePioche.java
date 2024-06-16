package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.stream.Collectors;

public class VuePioche extends Pane {
    private Joueur joueurCourant;
    private ImageView deckImageView;
    private Tooltip deckTooltip;

    public VuePioche(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;

        // Load the deck image
        deckImageView = new ImageView(new Image("images/boutons/deck.png"));

        deckImageView.fitHeightProperty().bind(this.heightProperty());
        deckImageView.fitWidthProperty().bind(this.widthProperty());

        deckImageView.setPreserveRatio(true);

        // Create the tooltip and bind its text to the deck list
        deckTooltip = new Tooltip();
        deckTooltip.textProperty().bind(Bindings.createStringBinding(() -> {
            return joueurCourant.getPioche().stream()
                    .map(Carte::getNom)
                    .collect(Collectors.joining("\n"));
        }, joueurCourant.getPioche()));
        Tooltip.install(deckImageView, deckTooltip);

        this.getChildren().add(deckImageView);
    }
}