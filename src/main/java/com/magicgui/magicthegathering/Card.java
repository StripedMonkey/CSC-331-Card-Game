package com.magicgui.magicthegathering;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;

/**
 * Card class that contains all the information about a card
 * As well as the actions that a card can perform. This class
 * does not contain any frontend information outside of the
 * imagePath and description
 */
public class Card {
    PropertyChangeSupport eventHelper;
    List<Effect> effects;
    Player ownerPlayer;
    String description;
    String imagePath;
    private boolean isSpellCard;
    private boolean isDead;
    private int attack;
    private int baseHealth;
    private int health = baseHealth;
    private int cost;


    /**
     * Cards have attack, health, and cost associated with them.
     * @param attack damage a card does
     * @param health number of hit points
     * @param cost the mana cost of a card
     * @param effects a list of effects that start on a card
     * @param description the description of a card
     * @param imagePath the image to display on a card
     * @param isSpellCard if a card is a spell card or not
     */
    Card(int attack, int health, int cost, List<Effect> effects, String description, String imagePath, boolean isSpellCard) {
        this.attack = attack;
        this.baseHealth = health;
        this.health = health;
        this.cost = cost;
        this.effects = effects;
        this.description = description;
        this.imagePath = imagePath;
        this.isSpellCard = isSpellCard;
        eventHelper = new PropertyChangeSupport(this);
    }

    /**
     * Generates a deck
     * @param folder Unused
     * @return A list of cards
     */
    static List<Card> buildDeckFromFolder(File folder, boolean genSpells) {
        return DeckBuilder.buildDeck(52, genSpells);
    }

    /**
     * Wrapper that allows you to pass a string instead of a File
     * Unused parameters
     * @param folder Unused
     * @return A List of Cards
     */
    static List<Card> buildDeckFromFolder(String folder, boolean genSpells) {
        return buildDeckFromFolder(new File(folder), genSpells);
    }

    /**
     * Whether or not a card is a spell card.
     * @return a t/f value
     */
    public boolean isSpellCard() {
        return isSpellCard;
    }

    /**
     * Setter for whether a card is a spell card
     * @param spellCard boolean whether a card is a spell card
     */
    public void setSpellCard(boolean spellCard) {
        isSpellCard = spellCard;
    }

    /**
     * returns the attack value of the card
     * @return integer attack value
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Sets the attack value of the card
     * @param attack amount of damage a card does
     */
    public void setAttack(int attack) {
        int oldAttack = this.attack;
        this.attack = attack;
        eventHelper.firePropertyChange("AttackEvent", oldAttack, this.attack);
    }

    /**
     * The base health of a card is the initial health of a card
     * @return the base health
     */
    public int getBaseHealth() {
        return this.baseHealth;
    }

    /**
     * Returns the current health of the card
     * @return the current health as an integer
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the card
     * Also does death checks, and allows for the Last Stand
     * effect to save a card from death as a second chance.
     * @param health
     */
    public void setHealth(int health) {
        int oldHealth = this.health;
        this.health = health;
        eventHelper.firePropertyChange("HealthEvent", oldHealth, this.health);

        if (this.health <= 0) {
            for (Effect effect :
                    effects) {
                if (effect instanceof LastStandEffect) {
                    removeEffect(effect);
                    this.health = 1;
                }
            }
            if (this.health <= 0) {
                setDead(true);
            }

        }
    }

    /**
     * The mana cost of a card
     * @return The cost of a card
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the mana cost of a card
     * @param cost new cost of the card
     */
    public void setCost(int cost) {
        int oldCost = this.health;
        this.cost = cost;
        eventHelper.firePropertyChange("CostEvent", oldCost, this.cost);
    }

    /**
     * returns the player that owns a particular card
     * currently unused
     * @return
     */
    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    /**
     * Currently unused, but allows for the owner of a card to be set.
     * @param ownerPlayer
     */
    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    /**
     * sets whether a card has died
     * @param isDead
     */
    public void setDead(boolean isDead) {
        boolean oldDead = this.isDead;
        this.isDead = isDead;
        if (isDead) {
            System.out.println("Card died");
        }
        eventHelper.firePropertyChange("DeadEvent", oldDead, this.isDead);
    }

    /**
     * Gets the description string of a card
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description string of a card
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the resource path of the card image
     * @return
     */
    String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the resource path of the image of the card
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * removes an effect from a card
     * @param effect
     */
    void removeEffect(Effect effect) {
        effects.remove(effect);
    }

    /**
     * adds an effect to a card
     * @param effect
     */
    void addEffect(Effect effect) {
        effects.add(effect);
    }

    /**
     * Plays a card from the player's hand
     * Acts as an interface for any spells that are run
     * @param targetPlayer
     * @param targetCard
     * @param castingPlayer
     */
    void cast(Player targetPlayer, Card targetCard, Player castingPlayer) {
        for (Effect effect : effects) {
            effect.cast(targetPlayer, targetCard, this, castingPlayer);
        }
    }

    /**
     * The effects have an opportunity to do something at the end of a turn.
     * This function is to be called when a turn is ended.
     */
    void endTurn() {
        for (Effect effect : effects) {
            effect.endTurn(this);
        }
        //setHealth(getBaseHealth());
    }

    /**
     * Deals damage to the card
     * @param damage amount of damage to deal to a card
     */
    public void damage(int damage) {
        System.out.println("Damaging card by " + damage);
        setHealth(getHealth() - damage);
    }

    /**
     * Increases the health of a card
     * @param health amount of health to regenerate
     */
    public void heal(int health) {
        setHealth(getHealth() + health);
    }

    /**
     * Adds a PropertyChangeListener to a card. Allows functions to be called when events are fired.
     * Currently there are 4 Events which can fire:
     * * AttackEvent
     * * HealthEvent
     * * CostEvent
     * * DeadEvent
     * @param propertyName The name of an event
     * @param pcl The listener object which activates when an event is fired
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener pcl) {
        eventHelper.addPropertyChangeListener(propertyName, pcl);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener pcl) {
        eventHelper.removePropertyChangeListener(propertyName, pcl);
    }
}


