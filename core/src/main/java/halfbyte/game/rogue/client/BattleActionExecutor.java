package halfbyte.game.rogue.client;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import halfbyte.game.rogue.client.entity.EntityManagerRenderer;
import halfbyte.game.rogue.client.entity.EntityRenderer;
import halfbyte.game.rogue.client.tilemap.TilemapRenderer;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityHeal;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityMeleeAttack;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityMove;
import halfbyte.game.rogue.common.entity.Entity;

public class BattleActionExecutor {
    public static void execute(AssetManager am, TilemapRenderer tilemap, EntityManagerRenderer em, BattleActionEntityHeal battle_action, float duration){
        // get handle to entity and target
        Entity entity = em.getEntityById(battle_action.getEntityId());
        EntityRenderer entity_renderer = em.getEntityRendererById(battle_action.getEntityId());
        Entity target_entity = em.getEntityById(battle_action.getTargetId());
        EntityRenderer target_entity_renderer = em.getEntityRendererById(battle_action.getTargetId());

        // actions
        SequenceAction action = new SequenceAction();

        // heal animation on entity
        action.addAction(Actions.moveBy(0.0f, entity_renderer.getHeight() / 2, duration / 2));
        action.addAction(Actions.moveBy(0.0f, -entity_renderer.getHeight() / 2, duration / 2));

        // heal
        action.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                // heal the entity
                target_entity.heal(battle_action.getHeal());

                // make sct
                em.addActor(ScrollingCombatText.create(
                        am,
                        "" + battle_action.getHeal(),
                        Color.GREEN,
                        target_entity_renderer.getX(Align.center),
                        target_entity_renderer.getY(Align.top),
                        150.0f,
                        1.0f
                ));

                // done
                return true;
            }
        });

        // put this action on the entity
        entity_renderer.addAction(action);
    }

    public static void execute(AssetManager am, TilemapRenderer tilemap, EntityManagerRenderer em, BattleActionEntityMeleeAttack battle_action, float duration){
        // get handle to entity and target
        Entity entity = em.getEntityById(battle_action.getEntityId());
        EntityRenderer entity_renderer = em.getEntityRendererById(battle_action.getEntityId());
        Entity target_entity = em.getEntityById(battle_action.getTargetId());
        EntityRenderer target_entity_renderer = em.getEntityRendererById(battle_action.getTargetId());

        // actions
        SequenceAction action = new SequenceAction();

        // bump towards
        action.addAction(Actions.moveBy(
                ((target_entity.getTileX() * Constants.TILE_SIZE) - (entity.getTileX() * Constants.TILE_SIZE)) / 2,
                ((target_entity.getTileY() * Constants.TILE_SIZE) - (entity.getTileY() * Constants.TILE_SIZE)) / 2,
                duration / 2
        ));

        // damage
        action.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                // damage the entity
                target_entity.damage(battle_action.getDamage());

                // make sct
                em.addActor(ScrollingCombatText.create(
                        am,
                        "-" + battle_action.getDamage(),
                        Color.RED,
                        target_entity_renderer.getX(Align.center),
                        target_entity_renderer.getY(Align.top),
                        150.0f,
                        1.0f
                ));

                // check if the entity is now dead
                if (target_entity.getIsAlive() == false){
                    target_entity_renderer.remove();
                }
                return true;
            }
        });

        // bump back
        action.addAction(Actions.moveBy(
                -((target_entity.getTileX() * Constants.TILE_SIZE) - (entity.getTileX() * Constants.TILE_SIZE)) / 2,
                -((target_entity.getTileY() * Constants.TILE_SIZE) - (entity.getTileY() * Constants.TILE_SIZE)) / 2,
                duration / 2
        ));

        // put this action on the entity
        entity_renderer.addAction(action);
    }

    public static void execute(AssetManager am, TilemapRenderer tilemap, EntityManagerRenderer em, BattleActionEntityMove battle_action, float duration){
        // get handle to entity and target
        Entity entity = em.getEntityById(battle_action.getEntityId());
        EntityRenderer entity_renderer = em.getEntityRendererById(battle_action.getEntityId());

        // apply locally
        entity.moveDirection(battle_action.getDirection());

        // but an action on the renderer
        entity_renderer.addAction(Actions.moveTo(
                entity.getTileX() * Constants.TILE_SIZE,
                entity.getTileY() * Constants.TILE_SIZE,
                duration
        ));
    }
}
