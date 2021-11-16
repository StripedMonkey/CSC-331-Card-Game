package com.magicgui.magicthegathering;
// Make the AI run behind the scenes
// Grab a card
// Play a card to the field

import java.util.Random;

/**
 * The computerOpponents operations.
 */
public class ComputerOpponent {
    private final Player ComputerOpponent;
    private final Random choice;

    /**
     * ComputerOpponent Constructor...
     * @param ComputerOpponent The computer player object.
     */
    ComputerOpponent(Player ComputerOpponent) {
        this.ComputerOpponent = ComputerOpponent;
        choice = new Random();
    }

    /**
     * Draws a card from the deck.
     */
    public void computerDraw() {
        ComputerOpponent.drawCard();
    }

    /**
     * Places the card in the computer play Field
     * @param game The game object.
     */
    public void computerPlaceCards(Game game) {
        int PlaceHand = choice.nextInt(4);
        while (ComputerOpponent.getHand().get(PlaceHand) == null) {
            PlaceHand = choice.nextInt(4);
        }
        int PlaceField = choice.nextInt(4);
        if (ComputerOpponent.getPlayField() != null) {
            ComputerOpponent.placeCard(PlaceHand, PlaceField, game, 1);
        }
    }
}

