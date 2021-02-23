package halfbyte.game.rogue.common.entity;

import halfbyte.game.rogue.common.Util;

public class Entity {
    public enum EType{
        WARRIOR, MAGE, ROGUE, HUNTER;
        public static EType getRandom(){
            return EType.values()[Util.getRandomIntInRange(0, EType.values().length - 1)];
        }
    }

    // variables
    private long m_id;
    private EType m_type;
    private int m_tile_x;
    private int m_tile_y;
    private int m_initiative;
    private int m_speed;

    // methods
    public Entity(){
        this(EType.WARRIOR);
    }

    public Entity(EType type){
        this(type, -1, -1);
    }

    public Entity(EType type, int tile_x, int tile_y){
        this.m_id = Util.generateId();
        this.m_type = type;
        this.m_tile_x = tile_x;
        this.m_tile_y = tile_y;
        this.m_initiative = 0;
        this.m_speed = 1;
    }

    public EType getType(){
        return this.m_type;
    }

    public int getTileX(){
        return this.m_tile_x;
    }

    public int getTileY(){
        return this.m_tile_y;
    }

    public void setTilePositionX(int tile_x){
        this.m_tile_x = tile_x;
    }

    public void setTilePositionY(int tile_y){
        this.m_tile_y = tile_y;
    }

    public void setTilePosition(int tile_x, int tile_y){
        this.m_tile_x = tile_x;
        this.m_tile_y = tile_y;
    }

    public int getInitiative(){
        return this.m_initiative;
    }

    public void resetInitiative(){
        this.m_initiative -= 1000;
    }

    public void increaseInitiative(){
        this.m_initiative += this.m_speed;
    }
}
