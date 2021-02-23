package halfbyte.game.rogue.client.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import java.util.EnumMap;
import java.util.Map;
import halfbyte.game.rogue.client.Constants;
import halfbyte.game.rogue.client.tileset.TilesetForEntity;
import halfbyte.game.rogue.common.entity.Entity;
import halfbyte.game.rogue.common.entity.EntityManager;

public class EntityManagerRenderer extends Group {
    // variables
    private EntityManager m_entity_manager;
    private TilesetForEntity m_tileset;
    private Map<Entity.EType, Integer> m_map_entity_type_to_tileset_index;

    // methods
    public EntityManagerRenderer(EntityManager entity_manager, TilesetForEntity tileset){
        // save
        this.m_entity_manager = entity_manager;
        this.m_tileset = tileset;

        // setup mapping
        this.m_map_entity_type_to_tileset_index = new EnumMap<Entity.EType, Integer>(Entity.EType.class);
        this.m_map_entity_type_to_tileset_index.put(Entity.EType.HUNTER, 23);
        this.m_map_entity_type_to_tileset_index.put(Entity.EType.MAGE, 24);
        this.m_map_entity_type_to_tileset_index.put(Entity.EType.ROGUE, 25);
        this.m_map_entity_type_to_tileset_index.put(Entity.EType.WARRIOR, 26);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // loop through all the entities
        for (int i = 0; i < this.m_entity_manager.getEntityCount(); ++i){
            // get handle
            Entity entity = this.m_entity_manager.getEntity(i);

            // get texture region
            TextureRegion tr = this.m_tileset.getTextureRegion(entity.getType());

            // draw
            batch.draw(
                    tr,
                    entity.getTileX() * Constants.TILE_SIZE + this.getX(),
                    entity.getTileY() * Constants.TILE_SIZE + this.getY(),
                    Constants.TILE_SIZE,
                    Constants.TILE_SIZE
            );
        }
    }
}
