package com.magicgui.magicthegathering;

class IncinerateEffect extends Effect {
    int duration;

    IncinerateEffect(int duration) {
        this.duration = duration;
    }

    @Override
    void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
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

