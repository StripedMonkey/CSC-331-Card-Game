package com.magicgui.magicthegathering;


// Not complete...

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Game implements PropertyChangeListener {
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
            //com.magicgui.magicthegathering.Player Attacks Computer. i.e. Update computer field.
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
    public void onEneTurn(){
        /* Reset remaining cards. */
        player.addMana(); // +3 per round?
        computer.addMana();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "")
    }

}