
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Not complete...

public class Player {
    private PropertyChangeSupport support;
    private static final int baseHealth = 10;
    private int health = baseHealth;
    private int mana = 3;
    private Stack<Card> deck = new Stack<>();
    private List<Card> hand = new ArrayList<>();
    private List<Card> graveYard = new ArrayList<>();
    private Card[] playField = new Card[5];


    public Player(List<Card> cardDeck){
        support = new PropertyChangeSupport(this);
        //init deck
        for (Card card: cardDeck){
            deck.push(card);
        }
    }

    /**
     * Will alter mana.
     * @param handIndex : Represents the index value of hand loc.
     */
    public boolean buyCard(int handIndex) {
        //Index will always conform to index boundary.
        if (this.mana >= hand.get(handIndex).getCost()) {
            int initialMana = this.mana;
            this.mana -= hand.get(handIndex).getCost();
            support.firePropertyChange("Mana Changed.", initialMana, this.mana);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @param  handIndex : Represents the index value of hand loc.
     * @param fieldIndex : Represents the index value of playField loc.
     */
    public void placeCard(int handIndex, int fieldIndex) {
        //buyCard check will update mana.
        if (playField[fieldIndex] == null && buyCard(handIndex)){
            playField[fieldIndex] = hand.get(handIndex);
            hand.remove(handIndex);
        }
        else{
            // (invoke ui decision)checkFieldOverride => playField[index] = card; else
        }
    }

    public void resetField(){
        for (Card c: playField){
            c.setHealth(c.getBaseHealth());
        }
    }

    public void drawCard() {  hand.add(deck.pop()); }


    public Card getPlayFieldCard(int index){ return playField[index]; } //Shouldn't be used
    public Card[] getPlayField(){ return playField; }
    public boolean isDead() {return this.health < 0;}

    public void addMana() {
        int initiaMana = this.mana;
        this.mana+=3;
        support.firePropertyChange("Mana Change", initiaMana, this.mana);
    }
    public void damageHealth(int damage) {
        int initialHealth = this.health;
        this.health -= damage;
        support.firePropertyChange("damageHealth", initialHealth, this.health);
    }
    public void resetHealth() {
        int initialHealth = this.health;
        this.health = baseHealth;
        support.firePropertyChange("resetHealth", initialHealth, this.health);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyListener(PropertyChangeListener pcl){
        support.removePropertyChangeListener(pcl);
    }
}



