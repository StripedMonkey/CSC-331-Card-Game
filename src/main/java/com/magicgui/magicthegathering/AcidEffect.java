package com.magicgui.magicthegathering;

class AcidEffect implements Effect {
    int duration;

    AcidEffect(int duration) {
        this.duration = duration;
    }

    public void cast(Player targetPlayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        if (targetCard != null){
            System.out.println("Acid effect being casted.");
            targetCard.addEffect(this);
        }
        else {
            System.out.println("target is null");
        }
    }

    public void endTurn(Card castingCard) {
        System.out.println("endTurn called | Acid");
        duration -= 1;
        if (duration > 0) {
            System.out.println("damage.");
            castingCard.damage(1);
        } else {
            castingCard.removeEffect(this);
        }
    }
}
