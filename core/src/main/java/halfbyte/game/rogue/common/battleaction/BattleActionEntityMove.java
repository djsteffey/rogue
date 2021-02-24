package halfbyte.game.rogue.common.battleaction;

import halfbyte.game.rogue.common.Util;

public class BattleActionEntityMove implements BattleAction{
    // variables
    private long m_entity_id;
    private Util.EDirection m_direction;

    // methods
    public BattleActionEntityMove(){
        this(-1, Util.EDirection.NONE);
    }

    public BattleActionEntityMove(long entity_id, Util.EDirection direction) {
        this.m_entity_id = entity_id;
        this.m_direction = direction;
    }

    public long getEntityId(){
        return this.m_entity_id;
    }

    public Util.EDirection getDirection(){
        return this.m_direction;
    }
}
