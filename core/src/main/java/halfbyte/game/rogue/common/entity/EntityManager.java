package halfbyte.game.rogue.common.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    // variables
    private List<Entity> m_entities;

    // methods
    public EntityManager(){
        this.m_entities = new ArrayList<>();
    }

    public void addEntity(Entity entity){
        this.m_entities.add(entity);
    }

    public int getEntityCount(){
        return this.m_entities.size();
    }

    public Entity getEntity(int index){
        return this.m_entities.get(index);
    }

    public Entity getEntityTurn(){
        while (true) {
            // find turn with most initiative >= 1000
            Entity turn = null;
            for (Entity entity : this.m_entities) {
                if (entity.getInitiative() >= 1000) {
                    if (turn == null || entity.getInitiative() > turn.getInitiative()) {
                        turn = entity;
                    }
                }
            }

            // if we found one then good to go
            if (turn != null){
                return turn;
            }

            // didnt find one so increase all initiative
            for (Entity entity : this.m_entities){
                entity.increaseInitiative();
            }
        }
    }
}
