package com.magicgui.magicthegathering;

class AcidEffect extends Effect {
    int duration;

    AcidEffect(int duration) {
        this.duration = duration;
    }

    @Override
    void cast(Player targetPlayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        targetCard.addEffect(this);
    }

    @Override
    void endTurn(Card castingCard) {
        duration -= 1;
        if (duration > 0) {
            castingCard.damage(1);
        } else {
            castingCard.removeEffect(this);
        }
    }
}
