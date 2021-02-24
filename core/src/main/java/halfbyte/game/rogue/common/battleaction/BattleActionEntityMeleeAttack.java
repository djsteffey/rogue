package halfbyte.game.rogue.common.battleaction;

public class BattleActionEntityMeleeAttack implements BattleAction{
    // variables
    private long m_entity_id;
    private long m_target_id;
    private int m_damage;

    // methods
    public BattleActionEntityMeleeAttack(){
        this(-1, -1, -1);
    }

    public BattleActionEntityMeleeAttack(long entity_id, long target_id, int damage) {
        this.m_entity_id = entity_id;
        this.m_target_id = target_id;
        this.m_damage = damage;
    }

    public long getEntityId(){
        return this.m_entity_id;
    }

    public long getTargetId(){
        return this.m_target_id;
    }

    public int getDamage(){
        return this.m_damage;
    }
}
