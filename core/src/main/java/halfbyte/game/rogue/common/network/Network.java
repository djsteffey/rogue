package halfbyte.game.rogue.common.network;

import com.esotericsoftware.kryo.Kryo;
import java.util.ArrayList;
import halfbyte.game.rogue.common.ability.Ability;
import halfbyte.game.rogue.common.ability.AbilityMeleeAttack;
import halfbyte.game.rogue.common.ability.AbilityMove;
import halfbyte.game.rogue.common.entity.Entity;
import halfbyte.game.rogue.common.entity.EntityManager;
import halfbyte.game.rogue.common.tilemap.Tilemap;
import halfbyte.game.rogue.common.tilemap.TilemapTile;

public class Network {
    public static final int BUFFER_SIZES = 1024 * 1024 * 2;
    public static final int SERVER_PORT = 12345;

    public static void register(Kryo kryo){
        kryo.register(ArrayList.class);

        kryo.register(Tilemap.class);
        kryo.register(TilemapTile.class);
        kryo.register(TilemapTile[].class);
        kryo.register(TilemapTile[][].class);
        kryo.register(TilemapTile.EType.class);

        kryo.register(EntityManager.class);
        kryo.register(Entity.class);
        kryo.register(Entity.EType.class);

        kryo.register(Ability.class);
        kryo.register(AbilityMove.class);
        kryo.register(AbilityMeleeAttack.class);
    }
}
