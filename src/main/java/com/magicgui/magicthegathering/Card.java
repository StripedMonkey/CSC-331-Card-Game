package com.magicgui.magicthegathering;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;

/**
 * TODO: document me
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
     * TODO: document me
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
     * TODO: Document me
     * @param folder
     * @return
     */
    static List<Card> buildDeckFromFolder(File folder, boolean genSpells) {
        return DeckBuilder.buildDeck(52, genSpells);
    }

    /**
     * TODO: Document me
     * @param folder
     * @return
     */
    static List<Card> buildDeckFromFolder(String folder, boolean genSpells) {
        return buildDeckFromFolder(new File(folder), genSpells);
    }

    /**
     * TODO: Document me
     * @return
     */
    public boolean isSpellCard() {
        return isSpellCard;
    }

    /**
     * TODO: Document me
     * @param spellCard
     */
    public void setSpellCard(boolean spellCard) {
        isSpellCard = spellCard;
    }

    /**
     * TODO: Document me
     * @return
     */
    public int getAttack() {
        return attack;
    }

    /**
     * TODO: Document me
     * @param attack
     */
    public void setAttack(int attack) {
        int oldAttack = this.attack;
        this.attack = attack;
        eventHelper.firePropertyChange("AttackEvent", oldAttack, this.attack);
    }

    /**
     * TODO: Document me
     * @return
     */
    public int getBaseHealth() {
        return this.baseHealth;
    }

    /**
     * TODO: Document me
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * TODO: Document me
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
     * TODO: Document me
     * @return
     */
    public int getCost() {
        return cost;
    }

    /**
     * TODO: Document me
     * @param cost
     */
    public void setCost(int cost) {
        int oldCost = this.health;
        this.cost = cost;
        eventHelper.firePropertyChange("CostEvent", oldCost, this.cost);
    }

    /**
     * TODO: Document me
     * @return
     */
    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    /**
     * TODO: Document me
     * @param ownerPlayer
     */
    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    /**
     * TODO: Document me
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
     * TODO: Document me
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * TODO: Document me
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * TODO: Document me
     * @return
     */
    String getImagePath() {
        return imagePath;
    }

    /**
     * TODO: Document me
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * TODO: Document me
     * @param effect
     */
    void removeEffect(Effect effect) {
        effects.remove(effect);
    }

    /**
     * TODO: Document me
     * @param effect
     */
    void addEffect(Effect effect) {
        effects.add(effect);
    }

    /**
     * TODO: Document me
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
     * TODO: Document me
     */
    void endTurn() {
        for (Effect effect : effects) {
            effect.endTurn(this);
        }
        //setHealth(getBaseHealth());
    }

    /**
     * TODO: Document me
     * @param damage
     */
    public void damage(int damage) {
        System.out.println("Damaging card by " + damage);
        setHealth(getHealth() - damage);
    }

    /**
     * TODO: Document me
     * @param health
     */
    public void heal(int health) {
        setHealth(getHealth() + health);
    }

    /**
     * TODO: Document me
     * @param propertyName
     * @param pcl
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener pcl) {
        eventHelper.addPropertyChangeListener(propertyName, pcl);
    }

    /**
     * TODO: Document me
     * @param propertyName
     * @param pcl
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener pcl) {
        eventHelper.removePropertyChangeListener(propertyName, pcl);
    }
}


