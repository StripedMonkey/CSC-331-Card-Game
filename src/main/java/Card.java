import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;
import java.beans.PropertyChangeEvent;

public class Card {
    PropertyChangeSupport eventHelper;
    List<Effect> effects;
    Player ownerPlayer;
    String description;
    String imagePath;
    private boolean isSpellCard;
    private int attack;
    private int health;
    private int cost;

    Card(int attack, int health, int cost, List<Effect> effects, String description, String imagePath) {
        this.attack = attack;
        this.health = health;
        this.cost = cost;
        this.effects = effects;
        this.description = description;
        this.imagePath = imagePath;
        eventHelper = new PropertyChangeSupport(this);
    }

    static List<Card> buildDeckFromFolder(File folder) {
        return null;
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
        eventHelper.firePropertyChange("AttackUpdate", oldAttack, this.attack);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        int oldHealth = this.health;
        this.health = health;
        eventHelper.firePropertyChange("HealthUpdate", oldHealth, this.health);

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        int oldCost = this.health;
        this.cost = cost;
        eventHelper.firePropertyChange("CostUpdate", oldCost, this.cost);
    }

    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
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

    void cast(Player targetplayer, Card targetCard, Card castingCard, Player castingPlayer) {
        for (Effect effect : effects) {
            effect.cast(targetplayer, targetCard, castingCard, castingPlayer);
        }
    }

    void endTurn() {
        for (Effect effect : effects) {
            effect.endTurn(this);
        }
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


