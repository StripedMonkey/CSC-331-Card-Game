package com.magicgui.magicthegathering;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;

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

    Card(int attack, int health, int cost, List<Effect> effects, String description, String imagePath,boolean isSpellCard) {
        this.attack = attack;
        this.baseHealth = health;
        this.cost = cost;
        this.effects = effects;
        this.description = description;
        this.imagePath = imagePath;
        this.isSpellCard = isSpellCard;
        eventHelper = new PropertyChangeSupport(this);
    }

    static List<Card> buildDeckFromFolder(File folder) {
        return DeckBuilder.buildDeck(52);
    }

    static List<Card> buildDeckFromFolder(String folder) {
        return buildDeckFromFolder(new File(folder));
    }

    public boolean isSpellCard() {
        return isSpellCard;
    }

    public void setSpellCard(boolean spellCard) {
        isSpellCard = spellCard;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        int oldAttack = this.attack;
        this.attack = attack;
        eventHelper.firePropertyChange("AttackEvent", oldAttack, this.attack);
    }

    public int getBaseHealth() {
        return this.baseHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        int oldHealth = this.health;
        this.health = health;
        if (health <= 0) {
            setDead(true);
        }
        eventHelper.firePropertyChange("HealthEvent", oldHealth, this.health);

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        int oldCost = this.health;
        this.cost = cost;
        eventHelper.firePropertyChange("CostEvent", oldCost, this.cost);
    }

    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    public void setDead(boolean isDead) {
        boolean oldDead = this.isDead;
        this.isDead = isDead;
        eventHelper.firePropertyChange("DeadEvent", oldDead, this.isDead);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    void removeEffect(Effect effect) {
        effects.remove(effect);
    }

    void addEffect(Effect effect) {
        effects.add(effect);
    }

    void cast(Player targetPlayer, Card targetCard, Player castingPlayer) {
        for (Effect effect : effects) {
            effect.cast(targetPlayer, targetCard, this, castingPlayer);
        }
    }

    void endTurn() {
        for (Effect effect : effects) {
            effect.endTurn(this);
        }
        setHealth(getBaseHealth());
    }

    public void damage(int damage) {
        setHealth(getHealth() - damage);
    }

    public void heal(int health) {
        setHealth(getHealth() + health);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener pcl) {
        eventHelper.addPropertyChangeListener(propertyName, pcl);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener pcl) {
        eventHelper.removePropertyChangeListener(propertyName, pcl);
    }
}


