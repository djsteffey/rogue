package halfbyte.game.rogue.common.ability;

import java.util.ArrayList;
import java.util.List;
import halfbyte.game.rogue.common.Util;
import halfbyte.game.rogue.common.battleaction.BattleAction;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityMeleeAttack;
import halfbyte.game.rogue.common.entity.Entity;

public class AbilityMeleeAttack extends Ability {
    public AbilityMeleeAttack() {
        super("Attack", 0, 1, true);
    }

    @Override
    public List<BattleAction> execute(Entity owner, Entity target_entity, Object parameter) {
        // calculate the damage
        int damage = Util.getRandomIntInRange(1, 5);

        // apply the damage
        target_entity.damage(damage);

        // on cooldown
        this.resetCooldown();

        // build the action
        List<BattleAction> actions = new ArrayList<>();
        actions.add(new BattleActionEntityMeleeAttack(owner.getId(), target_entity.getId(), damage));
        return actions;
    }

    @Override
    protected boolean isValidTarget(Entity owner, Entity other) {
        // no if dead
        if (other.getIsAlive() == false){
            return false;
        }

        // no if ourself
        if (other == owner) {
            return false;
        }

        // good to go
        return true;
    }
}
