package com.magicgui.magicthegathering;
// Make the AI run behind the scenes
// Grab a card
// Play a card to the field

import java.util.Random;

public class ComputerOpponent {
    private final Player ComputerOpponent;
    private final Random choice;

    // Initialize the Player
    ComputerOpponent(Player ComputerOpponent) {
        this.ComputerOpponent = ComputerOpponent;
        choice = new Random();
    }

    public void computerDraw() {
        ComputerOpponent.drawCard();
    }

    // Attacking must consider how many cards in the hand(null), randomly select from the hand,
    // and randomly place in the field.
    // Starting out with 7 cards

    public void computerPlaceCards(Game game) {
        int PlaceHand = choice.nextInt(4);
        while (ComputerOpponent.getHand().get(PlaceHand) == null) {
            PlaceHand = choice.nextInt(4);
        }
        int PlaceField = choice.nextInt(4);
        // Check if spot on field is empty, if it is not, just increment to next spot
        if (ComputerOpponent.getPlayField() != null) {
            ComputerOpponent.placeCard(PlaceHand, PlaceField, game);
        }
    }
}

