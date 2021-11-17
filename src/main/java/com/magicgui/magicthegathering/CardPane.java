package com.magicgui.magicthegathering;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.Objects;

public class CardPane extends StackPane {
    Label cardHealthLabel = new Label();
    Label cardAttackLabel = new Label();
    Label cardCostLabel = new Label();
    Image cardImage;
    Card card;

    public CardPane(Card currentCard) {
        /**
         * @param currentCard
        */
        card = currentCard;
        this.setMaxHeight(180);
        this.setMaxWidth(135);
        cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(currentCard.getImagePath())), 135, 180, true, true);
        String healthString = String.valueOf(currentCard.getHealth());
        this.formatLabel(this.cardHealthLabel, healthString, Pos.BOTTOM_LEFT);

        String attackString = String.valueOf(currentCard.getAttack());
        this.formatLabel(this.cardAttackLabel, attackString, Pos.BOTTOM_RIGHT);

        String costString = String.valueOf(currentCard.getCost());
        this.formatLabel(this.cardCostLabel, costString, Pos.TOP_RIGHT);

        this.setAlignment(Pos.CENTER);
        this.setId(String.valueOf(currentCard));
        this.getChildren().add(new ImageView(cardImage));
        this.getChildren().add(cardHealthLabel);
        this.getChildren().add(cardCostLabel);
        this.getChildren().add(cardAttackLabel);

        this.setOnDragDetected(event -> {
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(this.getId());
            db.setDragView(cardImage);
            content.putImage(cardImage);

            db.setContent(content);
            event.consume();
        });
    }

    public void setCostLabel(String costString) {cardCostLabel.setText(costString);}

    public void setCardHealthLabel(String healthString) {cardHealthLabel.setText(healthString);}

    public void setCardAttackLabel(String attackString) {cardAttackLabel.setText(attackString);}

    public Card getCard(){return card;}


    private void formatLabel(Label cardLabel, String value, Pos labelPosition) {
        cardLabel.setText(value);
        cardLabel.setTextFill(Paint.valueOf("white"));
        cardLabel.setPadding(new Insets(10));
        StackPane.setAlignment(cardLabel, labelPosition);
    }

    public void damageCard() {
        PauseTransition delay = new PauseTransition(Duration.millis(450));
        ImageView damage = new ImageView(new Image(getClass().getResourceAsStream("Gif.gif"), 135, 180, false, false));
        this.getChildren().add(damage);
        delay.setOnFinished(e -> this.getChildren().remove(damage));
        delay.play();
    }
}
