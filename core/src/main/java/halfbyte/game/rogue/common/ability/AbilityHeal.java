package halfbyte.game.rogue.common.ability;

import java.util.ArrayList;
import java.util.List;
import halfbyte.game.rogue.common.Util;
import halfbyte.game.rogue.common.battleaction.BattleAction;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityHeal;
import halfbyte.game.rogue.common.entity.Entity;

public class AbilityHeal extends Ability {
    public AbilityHeal() {
        super("Heal", 4, 5, true);
    }

    @Override
    public List<BattleAction> execute(Entity owner, Entity target_entity, Object parameter) {
        // calculate the heal
        int heal = Util.getRandomIntInRange(1, 5);

        // apply the heal
        target_entity.heal(heal);

        // on cooldown
        this.resetCooldown();

        // build the action
        List<BattleAction> actions = new ArrayList<>();
        actions.add(new BattleActionEntityHeal(owner.getId(), target_entity.getId(), heal));
        return actions;
    }

    @Override
    protected boolean isValidTarget(Entity owner, Entity other) {
        // no if dead
        if (other.getIsAlive() == false){
            return false;
        }

        // no it not ourself
        /*
        if (other != owner) {
            return false;
        }
         */

        // good to go
        return true;
    }
}
