import java.io.File;
import java.util.List;

public class Card {
    public int baseHealth;
    int attack;
    int health;
    int cost;
    List<Effect> effects;
    boolean isSpellCard;
    Player ownerPlayer;

    String description;
    String imagePath;


    Card(int attack, int health, int cost, List<Effect> effects, String description, String imagePath) {
        this.attack = attack;
        this.health = health;
        this.cost = cost;
        this.effects = effects;
        this.description = description;
        this.imagePath = imagePath;
    }

    static List<Card> buildDeckFromFolder(File folder) {
        return null;
    }

    static List<Card> buildDeckFromFolder(String folder) {
        return buildDeckFromFolder(new File(folder));
    }

    String getImagePath() {
        return imagePath;
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
        health -= damage;
    }
}


