package halfbyte.game.rogue.common.entity;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

import halfbyte.game.rogue.common.Util;
import halfbyte.game.rogue.common.ability.Ability;

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
    private int m_hp_current;
    private int m_hp_max;
    private List<Ability> m_abilities;

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
        this.m_hp_max = Util.getRandomIntInRange(20, 30);
        this.m_hp_current = Util.getRandomIntInRange(10, this.m_hp_max);
        this.m_abilities = new ArrayList<>();
    }

    public long getId(){
        return this.m_id;
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

    public void moveDirection(Util.EDirection direction){
        GridPoint2 gp = direction.toGridPoint2();
        this.m_tile_x += gp.x;
        this.m_tile_y += gp.y;
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

    public List<Ability> getAbilities(){
        return this.m_abilities;
    }

    public int getHpCurrent(){
        return this.m_hp_current;
    }

    public int getHpMax(){
        return this.m_hp_max;
    }

    public void damage(int amount){
        this.m_hp_current -= amount;
        if (this.m_hp_current < 0){
            this.m_hp_current = 0;
        }
    }

    public void heal(int amount){
        this.m_hp_current += amount;
        if (this.m_hp_current > this.m_hp_max){
            this.m_hp_current = this.m_hp_max;
        }
    }

    public boolean getIsAlive(){
        return this.m_hp_current > 0;
    }
}
