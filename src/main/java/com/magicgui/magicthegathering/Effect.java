package com.magicgui.magicthegathering;

public abstract class Effect {
    void cast(Player targetPlayer, Card targetCard, Card castingCard, Player enemyPlayer) {
    }

    void endTurn(Card castingCard) {
    }
}