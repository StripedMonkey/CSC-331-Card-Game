class DegenEffect extends Effect
{
    int duration;

    DegenEffect(int duration) {
    this.duration = duration;
    }
    @Override
    void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        targetCard.addEffect(new DegenEffect(3));
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
