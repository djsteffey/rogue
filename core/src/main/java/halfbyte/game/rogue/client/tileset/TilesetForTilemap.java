package halfbyte.game.rogue.client.tileset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import halfbyte.game.rogue.common.tilemap.TilemapTile;

public class TilesetForTilemap extends Tileset {
    // variables


    // methods
    public TilesetForTilemap(AssetManager am){
        super(am.get("tiles_24x24.png", Texture.class), 24);
    }

    public TextureRegion getTextureRegion(TilemapTile.EType type){
        int index = -1;
        switch (type){
            case NONE: index = -1; break;
            case WALL: index = 0; break;
            case FLOOR: index = 3; break;
        }
        if (index == -1){
            return  null;
        }
        return this.getTilesetTileTextureRegion(index);
    }
}
