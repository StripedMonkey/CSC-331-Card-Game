package magicgame.magicthegathering;
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


  // Attacking must consider how many cards in the hand(null), randomly select from the hand,
  // and randomly place in the field.
  // Starting out with 7 cards


 // while not loop place is null
  public void computerPlaceCards() {
    while (ComputerOpponent.getPlayField() != null){
      for (int x = 0; x <= 5; x++) {
        // iterate over playfield
        int PlaceHand = choice.nextInt((5 - 1) + 1) + 1;
        int PlaceField = choice.nextInt((5 - 1) + 1) + 1;
        // Check if spot on field is empty, if it is not, just increment to next spot
        if (ComputerOpponent.getPlayField() != null) {
          x = x + 1;
        }
        // If spot empty just place the card on the field
        else {
          ComputerOpponent.placeCard(PlaceHand, PlaceField);
        }
      }
    }
  }
}

