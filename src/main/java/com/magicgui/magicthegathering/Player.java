package com.magicgui.magicthegathering;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

// Not complete...


public class Player {
    private static final int baseHealth = 20;
    private final PropertyChangeSupport support;
    private final Stack<Card> deck = new Stack<>();
    private final List<Card> hand = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null));
    private final Card[] playField = new Card[5];
    private int health = baseHealth;
    private int maxMana = 3;
    private int mana = 3;

    public Player(List<Card> cardDeck) {
        support = new PropertyChangeSupport(this);
        //init deck
        for (Card card : cardDeck) {
            deck.push(card);
        }
    }

    public void invokeAttack(Player enemy) {
        Card[] enemyPlayField = enemy.getPlayField();
        for (int i = 0; i < enemyPlayField.length; i++) {
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
     * Will alter mana.
     *
     * @param handIndex : Represents the index value of hand loc.
     */
    public drawType buyCard(int handIndex) {
        //Index will always conform to index boundary.
        if (this.mana >= hand.get(handIndex).getCost()) {
            if (hand.get(handIndex).isSpellCard()) {
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
     * @param handIndex  : Represents the index value of hand loc.
     * @param fieldIndex : Represents the index value of playField loc.
     */
    public boolean placeCard(int handIndex, int fieldIndex) {
        drawType cardType = buyCard(handIndex);
        //buyCard check will update mana.
        boolean dropped = false;
        if (playField[fieldIndex] == null && cardType.equals(drawType.PLACE)) {
            playField[fieldIndex] = hand.get(handIndex);
            support.firePropertyChange("FieldEvent", hand.get(handIndex), fieldIndex);
            playField[fieldIndex].addPropertyChangeListener("DeadEvent", evt -> playField[fieldIndex] = null);
            Card toRemove = hand.get(handIndex);
            hand.set(handIndex, null);
            System.out.println(this.getPlayField());
            System.out.println("Printing playfield | Backend.");
            dropped = true;
        }
        return dropped;
    }

    public void drawCard() {
        Card toAdd = deck.pop();
        if (hand.contains(null)) {
            hand.set(hand.indexOf(null), toAdd);
            support.firePropertyChange("HandEvent", toAdd, hand.indexOf(toAdd));
        }
    }

    public Card getPlayFieldCard(int index) {
        return playField[index];
    } //Shouldn't be used

    public Card[] getPlayField() {
        return playField;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public boolean isDead() {
        return this.health < 0;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void endTurn() {
        for (Card c : playField) {
            if (c != null) {
                c.endTurn();
            }
        }
        addMana();
    }

    public void addMana() {
        int initialMana = this.mana;
        this.mana += 3;
        if (this.mana > maxMana) {
            maxMana = this.mana;
        }
        support.firePropertyChange("ManaEvent", initialMana, this.mana);
    }

    public void damageHealth(int damage) {
        System.out.println("Damaging Enemy health by " + damage);
        int initialHealth = this.health;
        this.health -= damage;
        if (this.health <= 0) {
            support.firePropertyChange("DeathEvent", initialHealth, this.health);
        }
        else{
            support.firePropertyChange("HealthEvent", initialHealth, this.health);
        }
    }

    public void addPropertyChangeListener(String pName, PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pName, pcl);
    }

    public void removePropertyListener(String pName, PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pName, pcl);
    }

    public int getHealth() {
        return health;
    }

    private enum drawType {CANT_AFFORD, SPELL, PLACE}

}



