package com.magicgui.magicthegathering;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

public class MagicGuiController {
    int cardsInGame = 0;
    @FXML private GridPane PlayerFieldGrid;
    @FXML private GridPane ComputerFieldGrid;
    @FXML private ProgressBar HealthProgressBar;
    @FXML private Label HealthProgressBarLabel;
    @FXML private ProgressBar ManaProgressBar;
    @FXML private Label ManaProgressBarLabel;
    @FXML private ProgressBar CPUHealthProgressBar;
    @FXML private Label CPUHealthProgressBarLabel;
    @FXML private TextField PlayerCardDescriptionTextField;
    @FXML private GridPane PlayerHandGridPane;


    @FXML
    void DragOverDetected(DragEvent event) {
        if (event.getGestureSource() != PlayerFieldGrid
                && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }


    @FXML
    void DragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        ImageView currentSpace = (ImageView) event.getPickResult().getIntersectedNode();

        System.out.println(event.getPickResult().getIntersectedNode());

        if (db.hasString() && currentSpace.getImage() != db.getImage()) {
            Integer cIndex = GridPane.getColumnIndex(currentSpace);
            int  battleFieldLocation = cIndex == null ? 0 : cIndex;
            PlayerFieldGrid.add(new ImageView(db.getImage()), battleFieldLocation, 0,1,1);
            // not sure exactly how to find location of where card is dragged from just yet
            // game.passCardDragLocation(battleFieldLocation, playerHandLocation);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }


    @FXML
    void DraggingMainItem(MouseEvent event) {
        Node currentCard = (Node) event.getTarget();
        Dragboard db = currentCard.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(currentCard.getId());
        db.setDragView(((ImageView) event.getTarget()).getImage());
        currentCard.setVisible(false);
        content.putImage(((ImageView) currentCard).getImage());

        db.setContent(content);
        event.consume();
    }

    @FXML
    void DeckAddCard(ActionEvent event) {
        // creates new random card
//         Card randomCard = game.getRandomCard();
//         ImageView newVisualCard = new ImageView(new Image(getClass().getResourceAsStream(newCard.getImageLink()), 100, 100, false, true));

        // temp card visual
        ImageView newVisualCard = new ImageView(new Image(getClass().getResourceAsStream("card.jpg"), 100, 100, false, true));

        newVisualCard.addEventHandler(MouseEvent.DRAG_DETECTED, this::DraggingMainItem);
        newVisualCard.addEventFilter(MouseEvent.DRAG_DETECTED, this::DraggingMainItem);
        newVisualCard.setId("card" + String.valueOf(cardsInGame));
        PlayerHandGridPane.add(newVisualCard, cardsInGame%7, 0);
        cardsInGame++;

    }


    @FXML
    void EndCurrentTurn(ActionEvent event) {
//        game.endTurn();
    }

    public void initialize() {
//        Game game = new Game;
//        ObservableValue<Integer> playerHealth = game.getPlayerHealth();
//        ObservableValue<Integer> playerMana = game.getPlayerMana();
//        ObservableValue<Integer> CPUMana = game.getCPUMana();
//        ObservableValue<Integer> CPUHealth = game.getCPUHealth();

        // ChangeListeners for health and mana
//        ChangeListener healthListener = new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
//                HealthProgressBar.setProgress(newValue / game.getMaxHealth);
//                HealthProgressBarLabel.setText(String.format("Health: %f / %f", newValue, game.getMaxHealth);
//            }};
//
//        ChangeListener manaListener = new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
//                ManaProgressBar.setProgress(newValue / game.getMaxMana);
//                ManaProgressBarLabel.setText(String.format("Health: %f / %f", newValue, game.getMaxMana);
//            }};
//        playerHealth.addListener(healthListener);
//        playerMana.addListener(manaListener);
    }

}