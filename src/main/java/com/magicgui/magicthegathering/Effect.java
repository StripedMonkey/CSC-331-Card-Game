package com.magicgui.magicthegathering;

abstract public class Effect {
    void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
    }

    void endTurn(Card castingCard) {
    }
}