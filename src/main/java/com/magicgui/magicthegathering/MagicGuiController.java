package com.magicgui.magicthegathering;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;


public class MagicGuiController {
    int cardsInGame = 0;
    @FXML private Button DeckButton;
    @FXML private Button EndTurnButton;
    @FXML private GridPane PlayerFieldGrid;
    @FXML private GridPane ComputerFieldGrid;
    @FXML private ProgressBar HealthProgressBar;
    @FXML private Label HealthProgressBarLabel;
    @FXML private ProgressBar ManaProgressBar;
    @FXML private Label ManaProgressBarLabel;
    @FXML private ProgressBar CPUHealthProgressBar;
    @FXML private Label CPUHealthProgressBarLabel;
    @FXML private ProgressBar CPUManaProgressBar;
    @FXML private Label CPUManaProgressBarLabel;
    @FXML private TextField PlayerCardDescriptionTextField;
    @FXML private GridPane PlayerHandGridPane;
    private static Map<String, Pane> cardPaneMap;



//    @FXML
//    void DraggingMainItem(MouseEvent event) {
//        Node currentCard = (Node) event.getTarget();
//        Dragboard db = currentCard.startDragAndDrop(TransferMode.MOVE);
//        ClipboardContent content = new ClipboardContent();
//        content.putString(currentCard.getId());
//        db.setDragView(((ImageView) event.getTarget()).getImage());
//        content.putImage(((ImageView) currentCard).getImage());
//
//        db.setContent(content);
//        event.consume();
//    }


    public void initialize() {
//        Game game = new Game;

        cardPaneMap = new HashMap<String, Pane>();


        // Deck Button add card Event
        DeckButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (aBoolean) {
//                game.addCard();
                ImageView newVisualCard = new ImageView();
                Pane newCard = createCardPane();

                // temp card visual
//                if (cardsInGame % 2 == 0) {
//                    newVisualCard.setImage((new Image(getClass().getResourceAsStream("Travellers.png"), 135, 180, false, true)));
//                } else {
//                    newVisualCard.setImage((new Image(getClass().getResourceAsStream("card.jpg"), 135, 180, false, true)));
//                }
//                newVisualCard.addEventHandler(MouseEvent.DRAG_DETECTED, this::DraggingMainItem);
//                newVisualCard.addEventFilter(MouseEvent.DRAG_DETECTED, this::DraggingMainItem);
                newVisualCard.setId("card" + String.valueOf(cardsInGame));
                PlayerHandGridPane.add(newCard, cardsInGame % 7, 0);
                cardsInGame++;
            }
        });

        // End turn button Event
        EndTurnButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {
//            if (aBoolean){game.endTurn();}
        });
        PlayerFieldGrid.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                ImageView currentSpace = (ImageView) event.getPickResult().getIntersectedNode();

                if (db.hasString()) {
                    Integer cIndex = GridPane.getColumnIndex(currentSpace);
                    int  battleFieldLocation = cIndex == null ? 0 : cIndex;
                    PlayerFieldGrid.add(cardPaneMap.get(db.getString()), battleFieldLocation, 0);
                    // not sure exactly how to find location of where card is dragged from just yet
                    // game.passCardDragLocation(battleFieldLocation, playerHandLocation);
                    success = true;
                } else {
//            PlayerHandGridPane.add(new ImageView(db.getImage()), )
                }
                event.setDropCompleted(success);
                event.consume();}
        });

        PlayerFieldGrid.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != PlayerFieldGrid
                        && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();}
        });


//        game.getPlayer().addListener("HandEvent", (Game game, String propertyName, Object oldValue, Object newValue) -> {
//
//        });

//        game.getComputer().addListener("HealthEvent", ( (Player Cpu, String propertyName, Object oldValue, Object newValue) -> {
//            CPUHealthProgressBar.setProgress(newValue / Cpu.getMaxHealth());
//            HealthProgressBarLabel.setText(String.format("Health: %f / %f", newValue, Cpu.getMaxMana);
//        });
//        game.getPlayer().addListener("HealthEvent", ( (Player player, String propertyName, Object oldValue, Object newValue) -> {
//            HealthProgressBar.setProgress(newValue / player.getMaxHealth());
//            HealthProgressBarLabel.setText(String.format("Health: %f / %f", newValue, game.getMaxMana);
//        });
//
//
//        game.getComputer().addListener("ManaEvent", ( (Player Cpu, String propertyName, Object oldValue, Object newValue) -> {
//            ManaProgressBar.setProgress(newValue / Cpu.getMaxMana());
//            ManaProgressBarLabel.setText(String.format("Health: %f / %f", newValue, game.getMaxMana);
//        });
//
//        game.getPlayer().addListener("HealthEvent", ( (Player player, String propertyName, Object oldValue, Object newValue) -> {
//            HealthProgressBar.setProgress(newValue / player.getMaxHealth());
//            ManaProgressBarLabel.setText(String.format("Health: %f / %f", newValue, game.getMaxMana);
//        });
    }
    private Pane createCardPane(){
        Pane cardPane = new Pane();
        Image cardImage = new Image(getClass().getResourceAsStream("Travellers.png"), 135, 180, true, true);
        Label cardLabel = new Label("Bananana");
        cardLabel.setTextFill(Paint.valueOf("white"));
        cardPane.setId(String.valueOf("card"+String.valueOf(cardsInGame)));
        cardPaneMap.put("card"+String.valueOf(cardsInGame), cardPane);
        cardPane.getChildren().add(new ImageView(cardImage));
        cardPane.getChildren().add(cardLabel);


        cardPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = cardPane.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(cardPane.getId());
                db.setDragView(cardImage);
                content.putImage(cardImage);

                db.setContent(content);
                event.consume();
            }
        });
//        cardPane.getChildren().add(new ImageView(new Image(currentCard.getImagePath())));
//        cardPane.getChildren().add(new Label(String.valueOf(currentCard.getHealth())));



        return cardPane;
    }

}