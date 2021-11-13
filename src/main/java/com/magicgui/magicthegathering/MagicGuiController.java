package com.magicgui.magicthegathering;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    @FXML private TextArea PlayerCardDescriptionTextField;
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
        Card newCard = new Card(10, 5, 10, null, "banana", "Creature_Travellers.png");


        // Deck Button add card Event
        DeckButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (aBoolean) {
//                game.addCard();

                StackPane newVisualCard = createCardPane(newCard);
                newVisualCard.setId("card" + String.valueOf(cardsInGame));
                PlayerHandGridPane.add(newVisualCard, cardsInGame % 7, 0);
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

// Listeners for player health once implemented
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

    private StackPane createCardPane(Card currentCard){
        StackPane cardPane = new StackPane();
        cardPane.setMaxHeight(180);
        cardPane.setMaxWidth(135);
        Image cardImage = new Image(getClass().getResourceAsStream(currentCard.getImagePath()), 135, 180, true, true);
        Label cardLabel = new Label();
        cardLabel.setText(String.valueOf(currentCard.getBaseHealth()));
        cardLabel.setTextFill(Paint.valueOf("white"));
        cardLabel.setPadding(new Insets(10));
        StackPane.setAlignment(cardLabel, Pos.BOTTOM_LEFT);


        cardPane.setId("card"+String.valueOf(cardsInGame));
        cardPaneMap.put("card"+String.valueOf(cardsInGame), cardPane);
        cardPane.getChildren().add(new ImageView(cardImage));
        cardPane.getChildren().add(cardLabel);


        currentCard.addPropertyChangeListener("HealthEvent", evt -> {
            if((Integer)evt.getNewValue() < (Integer)evt.getOldValue()){
                Image ngifImage = new Image(getClass().getResourceAsStream("Gif.gif"), 100, 100, true, true);
                cardPane.getChildren().add(new ImageView(ngifImage));
                boolean not = true;
                int no = 0;
                while(not != false){
                    no += 1;
                    if(no == 10000){
                        not = false;
                    }
                }
                cardPane.getChildren().remove(ngifImage);
            }
        });


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

        cardPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PlayerCardDescriptionTextField.setText(currentCard.getDescription());
            }
        });




//        cardPane.getChildren().add(new ImageView(new Image(currentCard.getImagePath())));
//        cardPane.getChildren().add(new Label(String.valueOf(currentCard.getHealth())));



        return cardPane;
    }

}