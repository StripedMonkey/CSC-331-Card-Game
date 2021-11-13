package com.magicgui.magicthegathering;

// Make the AI run behind the scenes
// Grab a card
// Play a card to the field
import java.util.Random;
/** Computer Opponent **/
public class ComputerOpponent {
  private Player ComputerOpponent;
  private Random choice;
  // Initialize the Player
  ComputerOpponent(Player ComputerOpponent) {
    this.ComputerOpponent = ComputerOpponent;
  }


  public void computerDraw() {
    ComputerOpponent.drawCard();
  }

  
  public void computerPlaceCards() {
    {
      for (int x = 0; x <= 5; x++) {
        // iterate over playfield
        int PlaceHand = choice.nextInt((5 - 1) + 1) + 1;
        int PlaceField = choice.nextInt((5 - 1) + 1) + 1;
        // Check if spot on field is empty, if it is not, just increment to next spot
        if (ComputerOpponent.getPlayField() == null) {
          ComputerOpponent.placeCard(PlaceHand, PlaceField);
        }
        // If spot empty just place the card on the field
      }
    }
  }
}

