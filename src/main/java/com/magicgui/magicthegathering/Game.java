package com.magicgui.magicthegathering;


/**
 * The game class is the driver code that drives the backend of the game.
 */
public class Game {
    private final Player player;
    private final Player computer;
    private final ComputerOpponent ai;
    private int currentTurn = 0;

    /**
     * The game constructor...
     */
    public Game() {
        this.player = new Player(Card.buildDeckFromFolder("Path"));
        this.computer = new Player(Card.buildDeckFromFolder("Path"));
        this.ai = new ComputerOpponent(computer);

    }

    /**
     * Updates the Back end player field and hand.
     * @param handIndex Represents the location of the hand to which the card is being taken from.
     * @param fieldIndex Represents the location of the Player-field to which the card must be placed.
     * @return droppable, represents the state to which the card can either be dropped or not.
     */
    public boolean updatePlayerField(int handIndex, int fieldIndex) {
        return this.player.placeCard(handIndex, fieldIndex, this, 0);
    }

    /**
     * onEndTurn will invoke the necessary methods to perform an end turn.
     * i.e. attacking, and any card effects that must be applied.
     */
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

    /**
     * Used for debugging.
     */
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

    //Accessors

    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

}