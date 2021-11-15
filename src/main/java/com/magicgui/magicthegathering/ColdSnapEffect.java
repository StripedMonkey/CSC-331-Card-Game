package com.magicgui.magicthegathering;

class ColdSnapEffect implements Effect {
    int duration;

    ColdSnapEffect(int duration) {
        this.duration = duration;
    }

    public void cast(Player targetPlayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        targetCard.addEffect(this);
    }

    @Override
    public void endTurn(Card castingCard) {
        duration -= 1;
        if (duration > 0) {
            castingCard.damage(1);
        } else {
            castingCard.removeEffect(this);
        }
    }
}

