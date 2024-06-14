package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Binding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class VueInstructionsBoutons extends VBox {
    private IJeu jeu;
    private Label instruction;
    private Button passer;
    private HBox buttons;
    private List<Button> buttonsContent;
    public VueInstructionsBoutons(IJeu jeu){
        this.jeu = jeu;
        this.instruction = new Label();
        this.passer = new Button("Passer");
        this.buttons = new HBox();
        this.buttonsContent = new ArrayList<>();
        buttons.getChildren().addAll(buttonsContent);
        VBox vue = new VBox(instruction, passer, buttons);
        this.getChildren().add(vue);
        creerBindings();
        this.setStyle("-fx-background-color: #f1f1f1; -fx-end-margin: 10; -fx-start-margin: 10");
        vue.setSpacing(10);
        this.setPadding(new Insets(10));
    }

    public void creerBindings(){
        instruction.textProperty().bind(jeu.instructionProperty());
        passer.setOnMouseClicked(mouseEvent -> jeu.passerAEteChoisi());
    }
}
