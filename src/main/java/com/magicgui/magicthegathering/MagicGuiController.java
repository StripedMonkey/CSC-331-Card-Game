package com.magicgui.magicthegathering;

import java.beans.PropertyChangeEvent;
import java.util.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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



    public void initialize() {
        Game game = new Game();

        ManaProgressBar.setProgress(1);
        ManaProgressBarLabel.setText(String.format("Mana: %d / %d", game.getComputer().getMaxMana(), game.getComputer().getMaxMana()));
        HealthProgressBar.setStyle("-fx-accent: red");
        HealthProgressBarLabel.setText(String.format("Health: %d / %d", game.getPlayer().getBaseHealth(), game.getPlayer().getBaseHealth()));
        HealthProgressBar.setProgress(1);

        CPUManaProgressBar.setProgress(1);
        CPUManaProgressBarLabel.setText(String.format("Mana: %d / %d", game.getComputer().getMaxMana(), game.getComputer().getMaxMana()));
        CPUHealthProgressBar.setProgress(1);
        CPUHealthProgressBar.setStyle("-fx-accent: red");
        CPUHealthProgressBarLabel.setText(String.format("Health: %d / %d", game.getComputer().getBaseHealth(), game.getComputer().getBaseHealth()));


        cardPaneMap = new HashMap<String, Pane>();
        Card newCard = new Card(10, 5, 10, new ArrayList<>(), "banana", "Creature_Travellers.png", false);
        Card newCard1 = new Card(5, 10, 3, new ArrayList<>(), ":Hey", "Creature_Crazy_Axeman.png", false);


        // Deck Button add card Event
        DeckButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (aBoolean){
                game.getPlayer().drawCard();
            }});

        // End turn button Event
        EndTurnButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (aBoolean){
                game.onEndTurn();
            }});


        PlayerFieldGrid.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                Node currentSpace = (Node) event.getPickResult().getIntersectedNode();

                if (db.hasString()) {
                    Integer cHandIndex = GridPane.getColumnIndex(cardPaneMap.get(db.getString()));
                    Integer cFieldIndex = GridPane.getColumnIndex(currentSpace);
                    int  fieldLocation = cFieldIndex == null ? 0 : cFieldIndex;
                    int handLocation = cHandIndex == null ? 0 : cHandIndex;

                    boolean droppable = game.updatePlayerField(handLocation, fieldLocation);
                    if (droppable) {
                        PlayerFieldGrid.add(cardPaneMap.get(db.getString()), fieldLocation, 0);
                        success = true;
                    }
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
        game.getComputer().addPropertyChangeListener("HealthEvent", PropertyChangeEvent -> {
            int newHealth = (int)PropertyChangeEvent.getNewValue();
            int baseHealth = ((Player)PropertyChangeEvent.getSource()).getBaseHealth();
            CPUHealthProgressBar.setProgress((float)newHealth / baseHealth);
            CPUHealthProgressBarLabel.setText(String.format("Health: %d / %d", newHealth, baseHealth));
        });

        game.getComputer().addPropertyChangeListener("ManaEvent", PropertyChangeEvent -> {
            int maxMana = ((Player)PropertyChangeEvent.getSource()).getMaxMana();
            int newMana = (int)PropertyChangeEvent.getNewValue();
            CPUManaProgressBar.setProgress((float)newMana / maxMana);
            CPUManaProgressBarLabel.setText(String.format("Mana: %d / %d", newMana, maxMana));
        });

        game.getPlayer().addPropertyChangeListener("ManaEvent", PropertyChangeEvent -> {
            int maxMana = ((Player)PropertyChangeEvent.getSource()).getMaxMana();
            int newMana = (int)PropertyChangeEvent.getNewValue();
            ManaProgressBar.setProgress((float)newMana / maxMana);
            ManaProgressBarLabel.setText(String.format("Mana: %d / %d", newMana, maxMana));
        });

        game.getPlayer().addPropertyChangeListener("FieldEvent", PropertyChangeEvent -> {

        });

        game.getPlayer().addPropertyChangeListener("HandEvent", PropertyChangeEvent ->{
            StackPane newVisualCard = createCardPane((Card) PropertyChangeEvent.getOldValue());
            PlayerHandGridPane.add(newVisualCard, (Integer) PropertyChangeEvent.getNewValue(), 0);
        });

    }

    private StackPane createCardPane(Card currentCard){
        StackPane cardPane = new StackPane();
        cardPane.setMaxHeight(180);
        cardPane.setMaxWidth(135);
        Image cardImage = new Image(getClass().getResourceAsStream(currentCard.getImagePath()), 135, 180, true, true);

        Label cardHealthLabel = new Label();
        cardHealthLabel.setText(String.valueOf(currentCard.getBaseHealth()));
        cardHealthLabel.setTextFill(Paint.valueOf("white"));
        cardHealthLabel.setPadding(new Insets(10));
        StackPane.setAlignment(cardHealthLabel, Pos.BOTTOM_LEFT);

        Label cardCostLabel = new Label();
        cardCostLabel.setText(String.valueOf(currentCard.getCost()));
        cardCostLabel.setTextFill(Paint.valueOf("white"));
        cardCostLabel.setPadding(new Insets(10));
        StackPane.setAlignment(cardCostLabel, Pos.BOTTOM_RIGHT);


        cardPane.setId(String.valueOf(currentCard));
        cardPaneMap.put(String.valueOf(currentCard), cardPane);
        cardPane.getChildren().add(new ImageView(cardImage));
        cardPane.getChildren().add(cardHealthLabel);
        cardPane.getChildren().add(cardCostLabel);


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

        currentCard.addPropertyChangeListener("DeadEvent", PropertyChangeEvent -> {

        });
        currentCard.addPropertyChangeListener("HealthEvent", PropertyChangeEvent -> {
            cardHealthLabel.setText(String.valueOf(PropertyChangeEvent.getNewValue()));
        });


        return cardPane;
    }

}