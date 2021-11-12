

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Not complete...

public class Player {
    private int health = 10; //will change..
    private int mana = 3;
    private Stack<Card> deck = new Stack<>();
    private List<Card> hand = new ArrayList<>(); // always > 0
    private List<Card> graveYard = new ArrayList<>();
    private Card[] playField = new Card[5];


    public Player(List<Card> cardDeck){
        //init deck
        for (Card card: cardDeck){
            deck.push(card);
        }
    }

    /**
     * Will alter mana.
     * We could track hand indices here.
     * @param handIndex : Represents the index value of hand loc.
     */
    public boolean buyCard(int handIndex) {
        //Index will always conform to index boundary.
        if (this.mana >= hand.get(handIndex).cost) {
            this.mana -= hand.get(handIndex).cost;
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
            c.health = c.baseHealth;
        }
    }

    public void drawCard() {  deck.pop(); }


    //Accessors
    public Card getPlayFieldCard(int index){ return playField[index]; } //Shouldn't be used
    public Card[] getPlayField(){ return playField; }
    public boolean isDead() {return this.health < 0;}

    //Mutators
    public void addMana() { this.mana+=3; }
    public void damageHealth(int damage) { this.health -= damage; }
    public void resetHealth() { this.health = 10;}


}



