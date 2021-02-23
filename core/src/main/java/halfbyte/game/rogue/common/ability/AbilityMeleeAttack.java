package halfbyte.game.rogue.common.ability;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import halfbyte.game.rogue.common.entity.Entity;

public class AbilityMeleeAttack extends Ability {
    public AbilityMeleeAttack(Entity owner) {
        super(owner, "Attack", 0, 1, true);
    }

    @Override
    public SequenceAction getExecutionAction(Entity target_entity, Object parameter) {
        // sequence of actions
        SequenceAction action = Actions.sequence();
        return action;

        /*
        // get direction of target
        GridPoint2 dir = new GridPoint2();
        if (target_entity.getTilePositionX() > this.m_owner.getTilePositionX()){
            dir.x = 1;
        }
        else if (target_entity.getTilePositionX() < this.m_owner.getTilePositionX()){
            dir.x = -1;
        }
        else if (target_entity.getTilePositionY() > this.m_owner.getTilePositionY()){
            dir.y = 1;
        }
        else if (target_entity.getTilePositionY() < this.m_owner.getTilePositionY()){
            dir.y = -1;
        }

        // bump towards
        action.addAction(Actions.moveBy(
                dir.x * Constants.TILE_SIZE * 0.50f,
                dir.y * Constants.TILE_SIZE * 0.50f,
                0.25f
        ));

        // do the damage
        action.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                target_entity.damage(1);
                return true;
            }
        });

        // bump away
        action.addAction(Actions.moveBy(
                -dir.x * Constants.TILE_SIZE * 0.50f,
                -dir.y * Constants.TILE_SIZE * 0.50f,
                0.25f
        ));

        // done
        return action;*/
    }
}
