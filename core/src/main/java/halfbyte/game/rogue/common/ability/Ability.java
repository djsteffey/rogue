package halfbyte.game.rogue.common.ability;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.util.ArrayList;
import java.util.List;

import halfbyte.game.rogue.common.entity.Entity;
import halfbyte.game.rogue.common.entity.EntityManager;
import halfbyte.game.rogue.common.tilemap.Tilemap;

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
    private Entity m_owner;
    private String m_name;
    private int m_cooldown_current;
    private int m_cooldown;
    private int m_range;
    private boolean m_require_los;

    // methods
    public Ability(Entity owner, String name, int cooldown, int range, boolean require_los){
        this.m_owner = owner;
        this.m_name = name;
        this.m_cooldown_current = 0;
        this.m_cooldown = cooldown;
        this.m_range = range;
        this.m_require_los = require_los;
    }

    public boolean getIsReady(){
        return this.m_cooldown == 0;
    }

    public List<AbilityTargetSet> getTargetSets(Tilemap map, EntityManager em){
        // the list to return
        List<AbilityTargetSet> list = new ArrayList<>();
        return list;

        /*

        // see if the ability is ready
        if (this.getIsReady()) {
            // go through each entity
            for (int i = 0; i < em.getEntityCount(); ++i) {
                // get handle to the other entity
                Entity other = em.getEntity(i);

                // check if the entity is in range
                if (Util.tileDistance(this.m_owner.getTileX(), this.m_owner.getTileY(), other.getTilePositionX(), other.getTilePositionY()) > this.m_range) {
                    // out of range
                    continue;
                }

                // check los is needed
                if (this.m_require_los) {
                    if (Util.bresenhamLine(owner.getTilePositionX(), owner.getTilePositionY(), other.getTilePositionX(), other.getTilePositionY(), new Util.IBresenhamLineCallback() {
                        @Override
                        public boolean onPosition(int x, int y) {
                            // if the map here is a wall then we cant keep going
                            if (map.getTilemapTileType(x, y) == TilemapTile.EType.WALL){
                                return false;
                            }
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
         */
    }

    public void decrementCooldown(){
        this.m_cooldown -= 1;
    }

    public abstract SequenceAction getExecutionAction(Entity target_entity, Object parameter);
}
