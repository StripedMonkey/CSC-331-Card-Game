import java.util.List;


// Not complete...

public class Game {
    private Player player;
    private Player computer;

    public Game() {
        this.player = new Player( Card.buildDeckFromFolder("Path"));
        this.computer = new Player( Card.buildDeckFromFolder("Path"));
    }

    //Updates player Field and Hand.
    public void updatePlayerField(int handIndex, int fieldIndex){
        this.player.placeCard(handIndex, fieldIndex);
    }

    public void invokePlayerAttack() {

        Card[] playField = player.getPlayField();
        Card[] compField = computer.getPlayField();
        for (int i = 0; i < 6; i++) {
            //Player Attacks Computer. i.e. Update computer field.
            try {
                // playField[i].castCard();
                // Should we apply damage that exceeds the defending card's health to enemy?
                compField[i].health -= playField[i].attack;
                playField[i].endTurn();
            } catch (NullPointerException e) {
                if (compField[i] == null) {
                    computer.damageHealth(playField[i].attack);
                    playField[i].endTurn();
                    // else player doesn't have a card in current loc.
                }
            }
        }
    }

    public void updateComputerField(){};


    public void invokeComputerAttack(){

    }
    // I.e. The end of both turns.
    public void onEndCompleteTurn(){
        /* Reset remaining cards. */
        player.resetField();
        player.addMana(); // +3 per round?

        computer.resetField();
        computer.addMana();
    }

}