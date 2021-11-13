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
        return generateCreatureCard();
    }

    static Card generateCreatureCard() {
        Random generator = new Random();
        int attack = generator.nextInt(0, 4);
        int health = generator.nextInt(1, 5 - attack);
        int cost = (attack + health) / 2;

        Effect effect = generateRandomEffect();
        List<Effect> effects = new ArrayList<>();

        return new Card(attack, health, cost, effects, "Beautiful default description", imagePath);

    }

    static Effect generateRandomEffect() {
        return new PoisonEffect(3);
    }
}
