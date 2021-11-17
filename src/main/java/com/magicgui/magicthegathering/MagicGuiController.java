package com.magicgui.magicthegathering;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class MagicGuiController {
    private static Map<String, CardPane> cardPaneMap;
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

    public void initialize() {
        Game game = new Game();
        cardPaneMap = new HashMap<>();


        HealthProgressBar.setStyle("-fx-accent: red");
        CPUHealthProgressBar.setStyle("-fx-accent: red");

        HealthProgressBarLabel.setText(String.format("Health: %d / %d", game.getPlayer().getBaseHealth(), game.getPlayer().getBaseHealth()));
        ManaProgressBarLabel.setText(String.format("Mana: %d / %d", game.getComputer().getMaxMana(), game.getComputer().getMaxMana()));
        CPUHealthProgressBarLabel.setText(String.format("Health: %d / %d", game.getComputer().getBaseHealth(), game.getComputer().getBaseHealth()));
        CPUManaProgressBarLabel.setText(String.format("Mana: %d / %d", game.getComputer().getMaxMana(), game.getComputer().getMaxMana()));

        HealthProgressBar.setProgress(1);
        ManaProgressBar.setProgress(1);
        CPUHealthProgressBar.setProgress(1);
        CPUManaProgressBar.setProgress(1);

        // Deck Button add card Event
        DeckButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {if (aBoolean) {game.getPlayer().drawCard();}});

        // End turn button Event
        EndTurnButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {if (aBoolean) {game.onEndTurn();}});


        ComputerFieldGrid.setOnDragDropped(event -> {
            Node currentSpace = event.getPickResult().getIntersectedNode();
            Dragboard db = event.getDragboard();
            if (currentSpace.getParent() instanceof CardPane){currentSpace = currentSpace.getParent();}

            if (db.hasString()) {
                Integer cHandIndex = GridPane.getColumnIndex(cardPaneMap.get(db.getString()));
                Integer cFieldIndex = GridPane.getColumnIndex(currentSpace);
                int fieldLocation = cFieldIndex == null ? 0 : cFieldIndex;
                int handLocation = cHandIndex == null ? 0 : cHandIndex;

                if (game.updatePlayerField(handLocation, fieldLocation)) {
                    PlayerHandGridPane.getChildren().remove(cardPaneMap.get(db.getString()));}
            }});


        ComputerFieldGrid.setOnDragOver(event -> {
            if (event.getGestureSource() != PlayerFieldGrid
                    && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });


        PlayerFieldGrid.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            Node currentSpace = event.getPickResult().getIntersectedNode();

            if (currentSpace.getParent() instanceof CardPane){currentSpace = currentSpace.getParent();}

            if (db.hasString()) {
                Integer cHandIndex = GridPane.getColumnIndex(cardPaneMap.get(db.getString()));
                Integer cFieldIndex = GridPane.getColumnIndex(currentSpace);
                int fieldLocation = cFieldIndex == null ? 0 : cFieldIndex;
                int handLocation = cHandIndex == null ? 0 : cHandIndex;

                if (game.updatePlayerField(handLocation, fieldLocation)) {
                    if (!cardPaneMap.get(db.getString()).getCard().isSpellCard()) {
                        PlayerFieldGrid.add(cardPaneMap.get(db.getString()), fieldLocation, 0);
                    }
                    success = true;
                }}
            event.setDropCompleted(success);
            event.consume();
        });


        PlayerFieldGrid.setOnDragOver(event -> {
            if (event.getGestureSource() != PlayerFieldGrid
                    && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });


        game.getComputer().addPropertyChangeListener("HealthEvent", PropertyChangeEvent -> {
            int newHealth = (int) PropertyChangeEvent.getNewValue();
            int baseHealth = ((Player) PropertyChangeEvent.getSource()).getBaseHealth();
            if (newHealth <= 0) {
                PlayerCardDescriptionTextField.setText("CONGRATULATIONS YOU WON!!!");
                CPUHealthProgressBar.setProgress((float) newHealth / baseHealth);
                CPUHealthProgressBarLabel.setText(String.format("COMPUTER DOWN"));
                endGame();
            } else {
                PlayerCardDescriptionTextField.setText("Play to win");
                CPUHealthProgressBar.setProgress((float) newHealth / baseHealth);
                CPUHealthProgressBarLabel.setText(String.format("Health: %d / %d", newHealth, baseHealth));
            }
        });


        game.getComputer().addPropertyChangeListener("ManaEvent", PropertyChangeEvent -> {
            int maxMana = ((Player) PropertyChangeEvent.getSource()).getMaxMana();
            int newMana = (int) PropertyChangeEvent.getNewValue();
            CPUManaProgressBar.setProgress((float) newMana / maxMana);
            CPUManaProgressBarLabel.setText(String.format("Mana: %d / %d", newMana, maxMana));
        });


        game.getPlayer().addPropertyChangeListener("ManaEvent", PropertyChangeEvent -> {
            int maxMana = ((Player) PropertyChangeEvent.getSource()).getMaxMana();
            int newMana = (int) PropertyChangeEvent.getNewValue();
            ManaProgressBar.setProgress((float) newMana / maxMana);
            ManaProgressBarLabel.setText(String.format("Mana: %d / %d", newMana, maxMana));
        });


        game.getPlayer().addPropertyChangeListener("HealthEvent", PropertyChangeEvent -> {
            int newHealth = (int) PropertyChangeEvent.getNewValue();
            int baseHealth = ((Player) PropertyChangeEvent.getSource()).getBaseHealth();
            if (newHealth <= 0) {
                PlayerCardDescriptionTextField.setText("You lost this time");
                HealthProgressBar.setProgress((float) newHealth / baseHealth);
                HealthProgressBarLabel.setText(String.format("REST IN RIP"));
                endGame();
            } else {
                PlayerCardDescriptionTextField.setText("Play to win");
                HealthProgressBar.setProgress((float) newHealth / baseHealth);
                HealthProgressBarLabel.setText(String.format("Health: %d / %d", newHealth, baseHealth));
            }
        });

        game.getPlayer().addPropertyChangeListener("FieldEvent", PropertyChangeEvent -> {
        });

        game.getPlayer().addPropertyChangeListener("HandEvent", PropertyChangeEvent -> {
            CardPane newVisualCard = createCardPane((Card) PropertyChangeEvent.getOldValue());
            PlayerHandGridPane.add(newVisualCard, (Integer) PropertyChangeEvent.getNewValue(), 0);
        });

        game.getComputer().addPropertyChangeListener("FieldEvent", PropertyChangeEvent -> {
            CardPane newVisualCard = createCardPane((Card) PropertyChangeEvent.getOldValue());
            ComputerFieldGrid.add(newVisualCard, (Integer) PropertyChangeEvent.getNewValue(), 0);
        });

        // Creating player's initial else
        game.getPlayer().createInitialHand();
    }

    private CardPane createCardPane(Card currentCard) {
        /*
        Creates visual card representation by creating a CardPane object
        Adds required listeners for game objects
         */
        CardPane cardPane = new CardPane(currentCard);
        GridPane.setHalignment(cardPane, HPos.CENTER);
        cardPaneMap.put(String.valueOf(currentCard), cardPane);

        // Drag detecting and handling
        cardPane.setOnMouseEntered(mouseEvent -> PlayerCardDescriptionTextField.setText(currentCard.getDescription()));
        cardPane.setOnMouseExited(mouseEvent -> PlayerCardDescriptionTextField.setText(""));

        // Adding listener for change in card health
        currentCard.addPropertyChangeListener("HealthEvent", PropertyChangeEvent -> {
            cardPane.setCardHealthLabel(String.valueOf(PropertyChangeEvent.getNewValue()));
            if ((Integer) PropertyChangeEvent.getNewValue() < (Integer) PropertyChangeEvent.getOldValue()) {cardPane.damageCard();}
        });

        // Adding listener for when card dies
        currentCard.addPropertyChangeListener("DeadEvent", PropertyChangeEvent -> {
            //Plays the animation and then removes the cardpane
            PauseTransition delay = new PauseTransition(Duration.millis(450));
            ImageView damage = new ImageView(new Image(getClass().getResourceAsStream("Gif.gif"), 135, 180, false, false));
            cardPane.getChildren().add(damage);
            delay.setOnFinished(e -> cardPane.getChildren().remove(damage));
            delay.setOnFinished(e -> ((GridPane) cardPane.getParent()).getChildren().remove(cardPane));
            delay.play();
        });

        return cardPane;
    }

    public void endGame(){
        DeckButton.setVisible(false);
        EndTurnButton.setVisible(false);
    }
}
