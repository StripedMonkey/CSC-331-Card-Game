package com.magicgui.magicthegathering;

class LastStandEffect implements Effect {
    public void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        if (targetCard != null){
            System.out.println("Last stand applied!");
            targetCard.addEffect(this);
        }
        else {
            System.out.println("target is null");
        }
    }
}
