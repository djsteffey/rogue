package halfbyte.game.rogue.common.tilemap;

public class TilemapTile {
    public enum EType{
        NONE, WALL, FLOOR
    }

    // variables
    private EType m_type;

    // methods
    public TilemapTile(){
        this.m_type = EType.NONE;
    }

    public TilemapTile(EType type){
        this.m_type = type;
    }

    public EType getType(){
        return this.m_type;
    }

    public void setType(EType type){
        this.m_type = type;
    }
}
