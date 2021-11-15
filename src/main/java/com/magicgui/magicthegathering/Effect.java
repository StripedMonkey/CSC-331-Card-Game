package com.magicgui.magicthegathering;

/**
 * TODO: Document me
 */
public interface Effect {
    /**
     * TODO: Document me
     * @param targetPlayer
     * @param targetCard
     * @param castingCard
     * @param enemyPlayer
     */
    default void cast(Player targetPlayer, Card targetCard, Card castingCard, Player enemyPlayer) {
    }

    default void endTurn(Card castingCard) {
    }
}