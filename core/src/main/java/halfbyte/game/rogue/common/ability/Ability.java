package halfbyte.game.rogue.common.ability;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.util.ArrayList;
import java.util.List;

import halfbyte.game.rogue.common.Util;
import halfbyte.game.rogue.common.battleaction.BattleAction;
import halfbyte.game.rogue.common.entity.Entity;
import halfbyte.game.rogue.common.entity.EntityManager;
import halfbyte.game.rogue.common.tilemap.Tilemap;
import halfbyte.game.rogue.common.tilemap.TilemapTile;

public abstract class Ability {
    public static class AbilityTargetSet {
        public Ability ability;
        public Entity target_entity;
        public AbilityTargetSet(Ability ability, Entity target_entity){
            this.ability = ability;
            this.target_entity = target_entity;
        }
    }

    // variables
    private String m_name;
    private int m_cooldown_current;
    private int m_cooldown;
    private int m_range;
    private boolean m_require_los;

    // methods
    public Ability(String name, int cooldown, int range, boolean require_los){
        this.m_name = name;
        this.m_cooldown_current = 0;
        this.m_cooldown = cooldown;
        this.m_range = range;
        this.m_require_los = require_los;
    }

    public boolean getIsReady(){
        return this.m_cooldown_current == 0;
    }

    public List<AbilityTargetSet> getTargetSets(Entity owner, Tilemap map, EntityManager em){
        // the list to return
        List<AbilityTargetSet> list = new ArrayList<>();

        // see if the ability is ready
        if (this.getIsReady()) {
            // go through each entity
            for (int i = 0; i < em.getEntityCount(); ++i) {
                // get handle to the other entity
                Entity other = em.getEntity(i);

                // check if it is a valid target
                if (this.isValidTarget(owner, other) == false){
                    continue;
                }

                // check if the entity is in range
                if (Util.tileDistance(owner.getTileX(), owner.getTileY(), other.getTileX(), other.getTileY()) > this.m_range) {
                    // out of range
                    continue;
                }

                // check los is needed
                if (this.m_require_los) {
                    if (Util.bresenhamLine(owner.getTileX(), owner.getTileY(), other.getTileX(), other.getTileY(), new Util.IBresenhamLineCallback() {
                        @Override
                        public boolean onPosition(int x, int y) {
                            // if the map here is a wall then we cant keep going
                            if (map.getTilemapTileType(x, y) == TilemapTile.EType.WALL){
                                return false;
                            }

                            // we can keep going on the line
                            return true;
                        }
                    }) == false) {
                        // line didnt get finished
                        continue;
                    }
                }

                // passed the checks
                list.add(new AbilityTargetSet(this, other));
            }
        }

        // done
        return list;
    }

    public void resetCooldown(){
        this.m_cooldown_current = this.m_cooldown;
    }

    public void decrementCooldown(){
        this.m_cooldown_current -= 1;
        if (this.m_cooldown_current < 0){
            this.m_cooldown_current = 0;
        }
    }

    public abstract List<BattleAction> execute(Entity owner, Entity target_entity, Object parameter);

    protected abstract boolean isValidTarget(Entity owner, Entity other);
}
