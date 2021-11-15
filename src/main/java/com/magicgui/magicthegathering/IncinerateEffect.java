package com.magicgui.magicthegathering;

class IncinerateEffect implements Effect {
    int duration;

    IncinerateEffect(int duration) {
        this.duration = duration;
    }

    public void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
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

