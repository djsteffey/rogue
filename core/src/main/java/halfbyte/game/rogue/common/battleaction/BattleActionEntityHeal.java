package halfbyte.game.rogue.common.battleaction;

public class BattleActionEntityHeal implements BattleAction{
    // variables
    private long m_entity_id;
    private long m_target_id;
    private int m_heal;

    // methods
    public BattleActionEntityHeal(){
        this(-1, -1, -1);
    }

    public BattleActionEntityHeal(long entity_id, long target_id, int heal) {
        this.m_entity_id = entity_id;
        this.m_target_id = target_id;
        this.m_heal = heal;
    }

    public long getEntityId(){
        return this.m_entity_id;
    }

    public long getTargetId(){
        return this.m_target_id;
    }

    public int getHeal(){
        return this.m_heal;
    }
}
