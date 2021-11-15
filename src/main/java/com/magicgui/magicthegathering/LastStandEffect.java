package com.magicgui.magicthegathering;

class LastStandEffect implements Effect {
    public void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        targetCard.addEffect(this);
    }
}
