package com.magicgui.magicthegathering;

class BleedEffect implements Effect {
    int duration;

    BleedEffect(int duration) {
        this.duration = duration;
    }

    public void cast(Player targetPlayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        targetCard.addEffect(this);
    }

    public void endTurn(Card castingCard) {
        duration -= 1;
        if (duration > 0) {
            castingCard.damage(1);
        } else {
            castingCard.removeEffect(this);
        }
    }
}
