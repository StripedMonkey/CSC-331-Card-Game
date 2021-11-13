class AcidEffect extends Effect {
    int duration;

    AcidEffect(int duration) {
        this.duration = duration;
    }

    @Override
    void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        targetCard.addEffect(new AcidEffect(3));
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
