package halfbyte.game.rogue.common.tilemap;

public class Tilemap {
    // variables
    private int m_width_in_tiles;
    private int m_height_in_tiles;
    private TilemapTile[][] m_tiles;

    // methods
    public Tilemap(){
        this.m_width_in_tiles = 0;
        this.m_height_in_tiles = 0;
        this.m_tiles = null;
    }

    public Tilemap(int width_in_tiles, int height_in_tiles){
        // save
        this.m_width_in_tiles = width_in_tiles;
        this.m_height_in_tiles = height_in_tiles;

        // create tiles
        this.m_tiles = new TilemapTile[this.m_width_in_tiles][this.m_height_in_tiles];
        for (int y = 0; y < this.m_height_in_tiles; ++y){
            for (int x = 0; x < this.m_width_in_tiles; ++x) {
                if (x == 0 || x == this.m_width_in_tiles - 1 || y == 0 || y == this.m_height_in_tiles - 1) {
                    this.m_tiles[x][y] = new TilemapTile(TilemapTile.EType.WALL);
                } else{
                    this.m_tiles[x][y] = new TilemapTile(TilemapTile.EType.FLOOR);
                }
            }
        }
    }

    public void setTilemapTileType(int x, int y, TilemapTile.EType type){
        this.m_tiles[x][y].setType(type);
    }

    public TilemapTile.EType getTilemapTileType(int x, int y){
        return this.m_tiles[x][y].getType();
    }

    public int getWidthInTiles(){
        return this.m_width_in_tiles;
    }

    public int getHeightInTiles(){
        return this.m_height_in_tiles;
    }
}
