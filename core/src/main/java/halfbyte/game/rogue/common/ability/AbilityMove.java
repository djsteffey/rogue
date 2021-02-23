package halfbyte.game.rogue.common.ability;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import halfbyte.game.rogue.common.entity.Entity;

public class AbilityMove extends Ability {
    // variables


    // methods
    public AbilityMove(Entity owner) {
        super(owner, "Move", 0, 0, false);
    }

    @Override
    public SequenceAction getExecutionAction(Entity target_entity, Object parameter) {
        // parameter is the direction
        GridPoint2 dir = (GridPoint2)parameter;

        // setup the sequence
        SequenceAction action = new SequenceAction();
        return action;

        /*
        // move
        action.addAction(Actions.moveBy(
                dir.x * Constants.TILE_SIZE,
                dir.y * Constants.TILE_SIZE
        ));

        // update tile position
        action.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                AbilityMove.this.m_owner.setTilePosition(
                        AbilityMove.this.m_owner.getTilePositionX() + dir.x,
                        AbilityMove.this.m_owner.getTilePositionY() + dir.y
                );
                return true;
            }
        });

        // done
        return action;*/
    }
}
