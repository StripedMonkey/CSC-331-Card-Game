package com.magicgui.magicthegathering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The DeckBuilder class is a helper class that generates cards randomly
 *
 */
public class DeckBuilder {

    /**
     * Generates a list of cards to play with.
     * @param numCards The number of cards to generate
     * @param genSpells Whether to generate spell cards. Specifically for not generating spell cards for the AI
     * @return A list of cards
     */
    static List<Card> buildDeck(int numCards, boolean genSpells) {
        Random generator = new Random();
        List<Card> deck = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            if (generator.nextBoolean()) {
                deck.add(generateCreatureCard());
            } else if (genSpells) {
                deck.add(generateSpellCard());
            }
        }
        return deck;
    }

    /**
     * Generates a single random spell card
     * @return a Spell Card
     */
    static Card generateSpellCard() {
        String[] imagePaths = {
                "Effect_Bleed.png",
                "Effect_Poison.png",
                "Effect_Incinerate.png"
        };
        String imagePath;
        List<Effect> effects = new ArrayList<Effect>();
        Random generator = new Random();
        if (generator.nextBoolean()) {
            effects.add(new AcidEffect(3));
            imagePath = imagePaths[generator.nextInt(0, imagePaths.length)];
        } else {
            effects.add(new LastStandEffect());
            imagePath = "Last_Stand.png";
        }

        return new Card(0, 0, 3, effects, "Spell Card", imagePath, true);
    }

    /**
     * Generates a single random Creature card
     * @return a Creature Card
     */
    static Card generateCreatureCard() {
        String[] imagePaths = {
                "Creature_Travellers.png",
                "Creature_Crazy_Axeman.png",
        };
        Random generator = new Random();
        int attack = generator.nextInt(0, 4);
        int health = generator.nextInt(1, 5 - attack);
        int cost = (attack + health) / 2;

        List<Effect> effects = new ArrayList<>();


        String imagePath = imagePaths[generator.nextInt(0, imagePaths.length)];
        return new Card(attack, health, cost, effects, "Beautiful default description", imagePath, false);

    }

    /**
     * Random Effect chosen by a fair d20 roll
     * See also https://xkcd.com/221/
     * @return random Effect
     */
    static Effect generateRandomEffect() {
        return new PoisonEffect(3);
    }
}
