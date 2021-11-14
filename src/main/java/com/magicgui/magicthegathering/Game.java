package com.magicgui.magicthegathering;


// Not complete...

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
    public boolean updatePlayerField(int handIndex, int fieldIndex){
        boolean droppable = this.player.placeCard(handIndex, fieldIndex);
        return droppable;
    }


    // I.e. The end of both turns.
    public void onEndTurn(){
        /* Reset remaining cards. */
        if (currentTurn % 2 == 0){ // even => computer turn.
            for (Card c: computer.getPlayField()){
                c.endTurn();
            }
            computer.addMana();
            computer.invokeAttack(player);
            currentTurn += 1;
        }
        else{
            for (Card c: player.getPlayField()){
                c.endTurn();
            }
            player.invokeAttack(computer);
            player.addMana();
            ai.computerDraw();
            ai.computerPlaceCards();
            currentTurn += 1;
            onEndTurn();
        }
    }

    public Player getPlayer() { return player; }
    public Player getComputer() { return computer; }

}