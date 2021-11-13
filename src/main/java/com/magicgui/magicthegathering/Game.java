package com.magicgui.magicthegathering;


// Not complete...

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Game {
    private Player player;
    private Player computer;
    private ComputerOpponent ai;
    private int currentTurn = 1;

    public Game() {
        this.player = new Player( Card.buildDeckFromFolder("Path"));
        this.computer = new Player( Card.buildDeckFromFolder("Path"));
        this.ai = new ComputerOpponent(computer);
    }

    //Updates player Field and Hand.
    public void updatePlayerField(int handIndex, int fieldIndex){
        this.player.placeCard(handIndex, fieldIndex);
    }


    // I.e. The end of both turns.
    public void onEndTurn(){
        /* Reset remaining cards. */
        if (currentTurn % 2 == 0){ // even => computer turn.
            computer.addMana();
            computer.invokeAttack(player);
            currentTurn += 1;
        }
        else{
            player.invokeAttack(computer);
            player.addMana();
            ai.computerDraw();
            ai.computerPlaceCards();
            currentTurn += 1;
            onEndTurn();
        }

    }

}