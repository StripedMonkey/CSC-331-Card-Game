package com.magicgui.magicthegathering;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MagicGuiController {
    private static Map<String, Pane> cardPaneMap;
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

        // Deck Button add card Event
        DeckButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (aBoolean) {
                game.getPlayer().drawCard();
            }
        });

        // End turn button Event
        EndTurnButton.pressedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (aBoolean) {
                game.onEndTurn();
            }
        });

        ComputerFieldGrid.setOnDragDropped(event -> {
            Node currentSpace = event.getPickResult().getIntersectedNode();
            Dragboard db = event.getDragboard();

            if (db.hasString()){
                Integer cHandIndex = GridPane.getColumnIndex(cardPaneMap.get(db.getString()));
                Integer cFieldIndex = GridPane.getColumnIndex(currentSpace);
                int fieldLocation = cFieldIndex == null ? 0 : cFieldIndex;
                int handLocation = cHandIndex == null ? 0 : cHandIndex;

                boolean droppable = game.updatePlayerField(handLocation, fieldLocation);

                if (droppable){
                    PlayerHandGridPane.getChildren().remove(cardPaneMap.get(db.getString()));
                }
            }
        });
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

            if (db.hasString()) {
                Integer cHandIndex = GridPane.getColumnIndex(cardPaneMap.get(db.getString()));
                Integer cFieldIndex = GridPane.getColumnIndex(currentSpace);
                int fieldLocation = cFieldIndex == null ? 0 : cFieldIndex;
                int handLocation = cHandIndex == null ? 0 : cHandIndex;

                boolean droppable = game.updatePlayerField(handLocation, fieldLocation);
                if (droppable) {
                    PlayerFieldGrid.add(cardPaneMap.get(db.getString()), fieldLocation, 0);
                    success = true;
                }
            }
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
            CPUHealthProgressBar.setProgress((float) newHealth / baseHealth);
            CPUHealthProgressBarLabel.setText(String.format("Health: %d / %d", newHealth, baseHealth));
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
            HealthProgressBar.setProgress((float) newHealth / baseHealth);
            HealthProgressBarLabel.setText(String.format("Health: %d / %d", newHealth, baseHealth));
        });


        game.getPlayer().addPropertyChangeListener("FieldEvent", PropertyChangeEvent -> {

        });

        game.getPlayer().addPropertyChangeListener("HandEvent", PropertyChangeEvent -> {
            StackPane newVisualCard = createCardPane((Card) PropertyChangeEvent.getOldValue());
            PlayerHandGridPane.add(newVisualCard, (Integer) PropertyChangeEvent.getNewValue(), 0);
        });

        game.getComputer().addPropertyChangeListener("FieldEvent", PropertyChangeEvent -> {
            StackPane newVisualCard = createCardPane((Card) PropertyChangeEvent.getOldValue());
            ComputerFieldGrid.add(newVisualCard, (Integer) PropertyChangeEvent.getNewValue(), 0);
        });

    }

    private StackPane createCardPane(Card currentCard) {
        StackPane cardPane = new StackPane();
        cardPane.setMaxHeight(180);
        cardPane.setMaxWidth(135);
        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(currentCard.getImagePath())), 135, 180, true, true);

        Label cardHealthLabel = new Label();
        String healthString = String.format("%s", currentCard.getHealth());
        formatLabel(cardHealthLabel, healthString, Pos.BOTTOM_LEFT);


        Label cardAttackLabel = new Label();
        String attackString = String.format("%s", currentCard.getAttack());
        formatLabel(cardAttackLabel,attackString, Pos.BOTTOM_RIGHT);

        Label cardCostLabel = new Label();
        String costString = String.format("%s", currentCard.getCost());
        formatLabel(cardCostLabel, costString, Pos.TOP_RIGHT);


        cardPane.setId(String.valueOf(currentCard));
        cardPaneMap.put(String.valueOf(currentCard), cardPane);
        cardPane.getChildren().add(new ImageView(cardImage));
        cardPane.getChildren().add(cardHealthLabel);
        cardPane.getChildren().add(cardCostLabel);
        cardPane.getChildren().add(cardAttackLabel);


        cardPane.setOnDragDetected(event -> {
            Dragboard db = cardPane.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(cardPane.getId());
            db.setDragView(cardImage);
            content.putImage(cardImage);

            db.setContent(content);
            event.consume();
        });

        cardPane.setOnMouseEntered(mouseEvent -> PlayerCardDescriptionTextField.setText(currentCard.getDescription()));
        cardPane.setOnMouseExited(mouseEvent -> PlayerCardDescriptionTextField.setText(""));

        currentCard.addPropertyChangeListener("DeadEvent", PropertyChangeEvent -> ((GridPane) cardPane.getParent()).getChildren().remove(cardPane));
        currentCard.addPropertyChangeListener("HealthEvent", PropertyChangeEvent -> cardHealthLabel.setText(String.valueOf(PropertyChangeEvent.getNewValue())));


        return cardPane;
    }

    private void formatLabel(Label cardLabel, String value, Pos labelPosition) {
        cardLabel.setText(value);
        cardLabel.setTextFill(Paint.valueOf("white"));
        cardLabel.setPadding(new Insets(10));
        StackPane.setAlignment(cardLabel, labelPosition);
    }

}