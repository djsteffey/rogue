package halfbyte.game.rogue.client.tilemap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import java.util.EnumMap;
import java.util.Map;
import halfbyte.game.rogue.client.Constants;
import halfbyte.game.rogue.client.tileset.TilesetForTilemap;
import halfbyte.game.rogue.common.tilemap.Tilemap;
import halfbyte.game.rogue.common.tilemap.TilemapTile;

public class TilemapRenderer extends Group {
    // variables
    private Tilemap m_tilemap;
    private TilesetForTilemap m_tileset;
    private Map<TilemapTile.EType, Integer> m_map_tile_type_to_tileset_index;

    // methods
    public TilemapRenderer(Tilemap tilemap, TilesetForTilemap tileset){
        // save
        this.m_tilemap = tilemap;
        this.m_tileset = tileset;

        // size
        this.setSize(this.m_tilemap.getWidthInTiles() * Constants.TILE_SIZE, this.m_tilemap.getHeightInTiles() * Constants.TILE_SIZE);

        // setup mapping
        this.m_map_tile_type_to_tileset_index = new EnumMap<TilemapTile.EType, Integer>(TilemapTile.EType.class);
        this.m_map_tile_type_to_tileset_index.put(TilemapTile.EType.FLOOR, 3);
        this.m_map_tile_type_to_tileset_index.put(TilemapTile.EType.WALL, 0);
        this.m_map_tile_type_to_tileset_index.put(TilemapTile.EType.NONE, -1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // loop through all the tiles
        for (int y = 0; y < this.m_tilemap.getHeightInTiles(); ++y){
            for (int x = 0; x < this.m_tilemap.getWidthInTiles(); ++x){
                // get the type and texture region
                TilemapTile.EType type = this.m_tilemap.getTilemapTileType(x, y);

                // get the texture region
                TextureRegion tr = this.m_tileset.getTextureRegion(type);

                // draw if we can
                if (tr != null){
                    // get the texture region
                    batch.draw(tr, x * Constants.TILE_SIZE + this.getX(), y * Constants.TILE_SIZE + this.getY(), Constants.TILE_SIZE, Constants.TILE_SIZE);
                }
            }
        }
    }
}
