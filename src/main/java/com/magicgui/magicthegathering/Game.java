package com.magicgui.magicthegathering;


// Not complete...

public class Game {
    private final Player player;
    private final Player computer;
    private final ComputerOpponent ai;
    private int currentTurn = 0;


    public Game() {
        this.player = new Player(Card.buildDeckFromFolder("Path"));
        this.computer = new Player(Card.buildDeckFromFolder("Path"));
        this.ai = new ComputerOpponent(computer);

    }

    //Updates player Field and Hand.
    // Can be used for spell casting on enemy field.
    public boolean updatePlayerField(int handIndex, int fieldIndex) {
        boolean droppable = this.player.placeCard(handIndex, fieldIndex, this);
        return droppable;
    }

    // I.e. The end of both turns.
    public void onEndTurn() {
        /* Reset remaining cards. */
        currentTurn += 1;
        if (currentTurn % 2 == 0) { // even => computer turn.
            ai.computerDraw();
            ai.computerPlaceCards(this);
            printBoard();
            computer.invokeAttack(player);
            computer.endTurn();
        } else {
            printBoard();
            player.invokeAttack(computer);
            player.endTurn();
            onEndTurn();
        }
    }

    private void printBoard() {
        System.out.println("End round");
        System.out.println("Player Health " + player.getHealth());
        System.out.println("Computer Health " + computer.getHealth());
        Card[] computerPlayField = computer.getPlayField();
        for (Card c : computerPlayField) {
            if (c != null) {
                System.out.printf("|%d/%d|", c.getHealth(), c.getAttack());
            } else {
                System.out.print("|-/-|");
            }
        }
        System.out.println();
        Card[] playerPlayField = player.getPlayField();
        for (Card c : playerPlayField) {
            if (c != null) {
                System.out.printf("|%d/%d|", c.getHealth(), c.getAttack());
            } else {
                System.out.print("|-/-|");
            }
        }
        System.out.println();
    }

    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

}