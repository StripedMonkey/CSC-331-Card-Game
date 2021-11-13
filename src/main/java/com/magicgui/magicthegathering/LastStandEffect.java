package com.magicgui.magicthegathering;

class LastStandEffect extends Effect
{
    @Override
    void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        targetCard.addEffect(this);
    }
}
