package com.magicgui.magicthegathering;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


/**
 * Player represents a player...
 */
public class Player {
    private static final int baseHealth = 20;
    private final PropertyChangeSupport support;
    private final Stack<Card> deck = new Stack<>();
    private final List<Card> hand = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null));
    private final Card[] playField = new Card[5];
    private int health = baseHealth;
    private int maxMana = 3;
    private int mana = 3;
    private boolean canDraw = true;

    /**
     * The player constructor...
     * @param cardDeck A list of card objects.
     */
    public Player(List<Card> cardDeck) {
        support = new PropertyChangeSupport(this); //Observable support.
        //init deck
        for (Card card : cardDeck) {
            deck.push(card);
        }
    }

    /**
     * Creates the initial hand of cards.
     */
    public void createInitialHand(){
        for (int i = 0; i < 5; i++) {
            hand.set(i, deck.pop());
            support.firePropertyChange("HandEvent", hand.get(i), i);
        }
    }

    /**
     * invokeAttack mimics the attack phase.
     * @param enemy the "enemy" player object.
     */
    public void invokeAttack(Player enemy) {
        Card[] enemyPlayField = enemy.getPlayField();
        for (int i = 0; i < playField.length; i++) {
            if (playField[i] != null) {
                if (enemyPlayField[i] == null) {
                    enemy.damageHealth(playField[i].getAttack());
                } else {
                    enemyPlayField[i].damage(playField[i].getAttack());
                }
            }
        }
    }

    /**
     * Fires a mana property change
     * @param handIndex : Represents the index value of the hand location.
     */
    public drawType buyCard(int handIndex) {
        //Index will always conform to index boundary.
        if (this.mana >= hand.get(handIndex).getCost()) {
            if (hand.get(handIndex).isSpellCard()) {
                int initialMana = this.mana;
                this.mana -= hand.get(handIndex).getCost();
                support.firePropertyChange("ManaEvent", initialMana, this.mana);
                return drawType.SPELL;
            } else {
                int initialMana = this.mana;
                this.mana -= hand.get(handIndex).getCost();
                support.firePropertyChange("ManaEvent", initialMana, this.mana);
                return drawType.PLACE;
            }
        } else {
            return drawType.CANT_AFFORD;
        }
    }

    /**
     * Fires a mana property Change via buyCard
     * @param handIndex  : Represents the index value of hand loc.
     * @param fieldIndex : Represents the index value of playField loc.
     */
    public boolean placeCard(int handIndex, int fieldIndex, Game game, int target) {
        drawType cardType = buyCard(handIndex);
        //buyCard check will update mana.
        boolean dropped = false;
        if (playField[fieldIndex] == null && cardType.equals(drawType.PLACE)) {
            playField[fieldIndex] = hand.get(handIndex);
            support.firePropertyChange("FieldEvent", hand.get(handIndex), fieldIndex);
            playField[fieldIndex].addPropertyChangeListener("DeadEvent", evt -> playField[fieldIndex] = null);
            hand.set(handIndex, null);
            //debug
            System.out.println(this.getPlayField());
            System.out.println("Printing playfield | Backend.");
            //-----
            dropped = true;
        }
        if (cardType.equals(drawType.SPELL)){
            // --- Debug
            System.out.println("Casting spell card player\n printing card: ");
            System.out.println(game.getComputer().getPlayFieldCard(fieldIndex));
            System.out.printf("%s %d%n", "Field target index:", fieldIndex);
            // -----
            if (target == 0){ //drop on playerField
                hand.get(handIndex).cast(game.getComputer(), game.getPlayer().getPlayFieldCard(fieldIndex), game.getPlayer());
            }
            else { //Drop on enemyField
                hand.get(handIndex).cast(game.getComputer(), game.getComputer().getPlayFieldCard(fieldIndex), game.getPlayer());
            }
            hand.set(handIndex, null);
            dropped = true;
        }
        return dropped;
    }

    /**
     * Draws a card from the deck.
     */
    public void drawCard() {
        Card toAdd = deck.pop();
        if (hand.contains(null) && canDraw) {
            hand.set(hand.indexOf(null), toAdd);
            support.firePropertyChange("HandEvent", toAdd, hand.indexOf(toAdd));
        }
        this.canDraw = false;
    }

    /**
     * Will invoke the cards endTurn method in the current play-field.
     * Increments mana and allows the user to draw a card again.
     */
    public void endTurn() {
        for (Card c : playField) {
            if (c != null) {
                c.endTurn();
            }
        }
        this.canDraw = true;
        addMana();
    }

    /**
     * Fires a mana property change, increments the current mana by 3.
     * Increases max mana under the given constraint.
     */
    public void addMana() {
        int initialMana = this.mana;
        this.mana += 3;
        if (this.mana > maxMana) {
            maxMana = this.mana;
        }
        support.firePropertyChange("ManaEvent", initialMana, this.mana);
    }


    /**
     * Will damage the health of the current object.
     * fires a health / death property change.
     * @param damage The damage to be applied.
     */
    public void damageHealth(int damage) {
        System.out.println("Damaging Enemy health by " + damage);
        int initialHealth = this.health;
        this.health -= damage;
        if (this.health <= 0) {
            support.firePropertyChange("DeathEvent", initialHealth, this.health);
        }
        support.firePropertyChange("HealthEvent", initialHealth, this.health);
    }

    // Observe functionality
    public void addPropertyChangeListener(String pName, PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pName, pcl);
    }

    public void removePropertyListener(String pName, PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pName, pcl);
    }

    /**
     * Accessor
     * @param index The play-field index of the card location.
     * @return Returns a Card object at the specified location.
     */
    public Card getPlayFieldCard(int index) {
        return playField[index];
    }

    //Accessors
    public Card[] getPlayField() {
        return playField;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getHealth() {
        return health;
    }

    // Represents the state of draw.
    private enum drawType {CANT_AFFORD, SPELL, PLACE}

}



