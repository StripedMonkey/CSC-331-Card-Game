package com.magicgui.magicthegathering;


// Not complete...

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Game {
    private Player player;
    private Player computer;
    private int currentTurn = 1;

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
            try {
                // Should we apply damage that exceeds the defending card's health to enemy?
                compField[i].damage(playField[i].getAttack());
                playField[i].endTurn();
            } catch (NullPointerException e) {
                if (compField[i] == null) {
                    computer.damageHealth(playField[i].getAttack());
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
    public void onEndTurn(){
        /* Reset remaining cards. */
        if (currentTurn % 2 == 0){ // even => computer turn.
            computer.addMana();
            computer.invokeAttack(player);

        }
        else{
            player.invokeAttack(computer);
            player.addMana();
        }
        currentTurn += 1;


    }

}