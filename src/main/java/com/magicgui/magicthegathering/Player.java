package com.magicgui.magicthegathering;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

// Not complete...

public class Player{
    private PropertyChangeSupport support;
    private static final int baseHealth = 20;
    private int health = baseHealth;
    private int maxMana = 3;
    private int mana = 3;
    private Stack<Card> deck = new Stack<>();
    private List<Card> hand = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null));
    private Card[] playField = new Card[5];
    private enum drawType{CANT_AFFORD, SPELL, OCCUPIED, PLACE}


    public Player(List<Card> cardDeck){
        support = new PropertyChangeSupport(this);
        //init deck
        for (Card card: cardDeck){
            deck.push(card);
        }
    }

    public void invokeAttack(Player enemy){
        Card[] enemyPlayField = enemy.getPlayField();
        for (int i = 0; i < 6; i++) {
            try {
                enemyPlayField[i].damage(playField[i].getAttack());
                // playField[i].endTurn();
            } catch (NullPointerException e) {
                if (enemyPlayField[i] == null) {
                    enemy.damageHealth(playField[i].getAttack());
                    // playField[i].endTurn();
                    // else player doesn't have a card in current loc.
                }
            }
        }
    }

    /**
     * Will alter mana.
     * @param handIndex : Represents the index value of hand loc.
     */
    public drawType buyCard(int handIndex) {
        //Index will always conform to index boundary.
        if (this.mana >= hand.get(handIndex).getCost()) {
            if (hand.get(handIndex).isSpellCard()){
                return drawType.SPELL;
            }
            else {
                int initialMana = this.mana;
                this.mana -= hand.get(handIndex).getCost();
                support.firePropertyChange("Mana Changed.", initialMana, this.mana);
                return drawType.PLACE;
            }
        }
        else {
            return drawType.CANT_AFFORD;
        }
    }

    /**
     * @param  handIndex : Represents the index value of hand loc.
     * @param fieldIndex : Represents the index value of playField loc.
     */
    public void placeCard(int handIndex, int fieldIndex) {
        drawType cardType = buyCard(handIndex);
        //buyCard check will update mana.
        if (playField[fieldIndex] == null && cardType.equals(drawType.PLACE)){
            playField[fieldIndex] = hand.get(handIndex);
            support.firePropertyChange("FieldEvent", hand.get(handIndex), fieldIndex);
            playField[fieldIndex].addPropertyChangeListener("DeadEvent", evt -> playField[fieldIndex] = null);
            Card toRemove = hand.get(handIndex);
            hand.remove(handIndex);
            support.firePropertyChange("HandEvent", toRemove, handIndex);
            System.out.println(this.getPlayField());
            System.out.println("Printing playfield | Backend.");
            for (Card c: this.getPlayField()){
                System.out.printf("%s ", c.getDescription());
            }
        }
        if (cardType.equals(drawType.CANT_AFFORD)){
            //can't afford
        }
    }

    public void drawCard() {
        Card toAdd = deck.pop();
        hand.set(hand.indexOf(null), toAdd);
        support.firePropertyChange("HandEvent", toAdd, hand.indexOf(toAdd));
    }


    public Card getPlayFieldCard(int index){ return playField[index]; } //Shouldn't be used
    public Card[] getPlayField(){ return playField; }
    public int getMaxMana() {return this.maxMana;}
    public int getMaxHealth() {return baseHealth;}
    public boolean isDead() {return this.health < 0;}

    public void incMaxMana(){
        this.maxMana += 4;
    }

    public void addMana() {
        int initialMana = this.mana;
        this.mana+=3;
        support.firePropertyChange("ManaEvent", initialMana, this.mana);
    }
    public void damageHealth(int damage) {
        int initialHealth = this.health;
        this.health -= damage;
        support.firePropertyChange("DamageEvent", initialHealth, this.health);
    }

    public void resetHealth() {
        int initialHealth = this.health;
        this.health = baseHealth;
        support.firePropertyChange("HealthEvent", initialHealth, this.health);
    }

    public void addPropertyChangeListener(String pName, PropertyChangeListener pcl){
        support.addPropertyChangeListener(pName, pcl);
    }

    public void removePropertyListener(String pName, PropertyChangeListener pcl){
        support.removePropertyChangeListener(pName, pcl);
    }
}



