package com.magicgui.magicthegathering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeckBuilder {

    static List<Card> buildDeck(int numCards) {
        Random generator = new Random();
        List<Card> deck = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            if (generator.nextBoolean()) {
                deck.add(generateCreatureCard());
            } else {
                deck.add(generateSpellCard());
            }
        }
        return deck;
    }

    static Card generateSpellCard() {
        List<Effect> effects = new ArrayList<Effect>();
        Random generator = new Random();
        if (generator.nextBoolean()) {
            effects.add(new AcidEffect(3));
        } else {
            effects.add(new LastStandEffect());
        }

        return new Card(0, 0, 3, effects, "Spell Card", "Effect_Bleed.png", true);
    }

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

    static Effect generateRandomEffect() {
        return new PoisonEffect(3);
    }
}
