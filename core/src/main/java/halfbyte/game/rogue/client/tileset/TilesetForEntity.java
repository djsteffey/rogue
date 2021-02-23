package halfbyte.game.rogue.client.tileset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import halfbyte.game.rogue.common.entity.Entity;

public class TilesetForEntity extends Tileset {
    // variables


    // methods
    public TilesetForEntity(AssetManager am){
        super(am.get("actors_24x24.png", Texture.class), 24);
    }

    public TextureRegion getTextureRegion(Entity.EType type){
        int index = -1;
        switch (type){
            case MAGE: index = 22; break;
            case ROGUE: index = 23; break;
            case HUNTER: index = 24; break;
            case WARRIOR: index = 25; break;
        }
        if (index == -1){
            return  null;
        }
        return this.getTilesetTileTextureRegion(index);
    }
}
