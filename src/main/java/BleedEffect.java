class BleedEffect extends Effect
{
    int duration;

    BleedEffect(int duration) {
        this.duration = duration;
    }
    @Override
    void cast(Player targetplayer, Card targetCard, Card castingCard, Player enemyPlayer) {
        targetCard.addEffect(new BleedEffect(2));
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
