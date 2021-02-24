package halfbyte.game.rogue.client.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import halfbyte.game.rogue.client.Constants;
import halfbyte.game.rogue.client.ScrollingCombatText;
import halfbyte.game.rogue.client.tileset.TilesetForEntity;
import halfbyte.game.rogue.common.Util;
import halfbyte.game.rogue.common.entity.Entity;
import halfbyte.game.rogue.common.entity.EntityManager;

public class EntityManagerRenderer extends Group {
    // variables
    private AssetManager m_asset_manager;
    private EntityManager m_entity_manager;
    private TilesetForEntity m_tileset;
    private Map<Entity.EType, Integer> m_map_entity_type_to_tileset_index;
    private Map<Long, EntityRenderer> m_entity_renderers;

    // methods
    public EntityManagerRenderer(AssetManager asset_manager, EntityManager entity_manager, TilesetForEntity tileset){
        // save
        this.m_asset_manager = asset_manager;
        this.m_entity_manager = entity_manager;
        this.m_tileset = tileset;

        // setup mapping
        this.m_map_entity_type_to_tileset_index = new EnumMap<Entity.EType, Integer>(Entity.EType.class);
        this.m_map_entity_type_to_tileset_index.put(Entity.EType.HUNTER, 23);
        this.m_map_entity_type_to_tileset_index.put(Entity.EType.MAGE, 24);
        this.m_map_entity_type_to_tileset_index.put(Entity.EType.ROGUE, 25);
        this.m_map_entity_type_to_tileset_index.put(Entity.EType.WARRIOR, 26);

        // now entity renderers
        this.m_entity_renderers = new HashMap<>();
        for (int i = 0; i < this.m_entity_manager.getEntityCount(); ++i){
            Entity entity = this.m_entity_manager.getEntity(i);
            EntityRenderer renderer = new EntityRenderer(
                    this.m_asset_manager,
                    entity,
                    this.m_tileset
            );
            this.addActor(renderer);
            this.m_entity_renderers.put(entity.getId(), renderer);
        }
    }

    public Entity getEntityById(long id){
        return this.m_entity_manager.getEntityById(id);
    }

    public EntityRenderer getEntityRendererById(long id){
        return this.m_entity_renderers.get(id);
    }
}
