package halfbyte.game.rogue.common;

import com.badlogic.gdx.math.GridPoint2;
import java.util.Random;

public class Util {
    // variables
    private static final Random s_random = new Random();
    private static long s_next_id = 1;

    // INTERFACE
    public interface IBresenhamLineCallback{
        boolean onPosition(int x, int y);
    }

    // enums
    public enum EDirection{
        UP, DOWN, LEFT, RIGHT, NONE;
        public static EDirection getRandom(){
            return EDirection.values()[Util.getRandomIntInRange(0, EDirection.values().length - 1)];
        }
        public GridPoint2 toGridPoint2(){
            GridPoint2 gp = new GridPoint2(0, 0);
            switch (this){
                case UP:{
                    gp.x = 0;
                    gp.y = -1;
                } break;
                case DOWN:{
                    gp.x = 0;
                    gp.y = 1;
                } break;
                case LEFT:{
                    gp.x = -1;
                    gp.y = 0;
                } break;
                case RIGHT:{
                    gp.x = 1;
                    gp.y = 0;
                } break;
                case NONE:{
                    gp.x = 0;
                    gp.y = 0;
                } break;
            }
            return gp;
        }
    }

    // misc
    public static int getRandomIntInRange(int min, int max){
        int range = max - min;
        return s_random.nextInt(range + 1) + min;
    }

    public static int tileDistance(int tile0_x, int tile0_y, int tile1_x, int tile1_y){
        return Math.abs(tile1_x - tile0_x) + Math.abs(tile1_y - tile0_y);
    }

    public static boolean bresenhamLine(int x0, int y0, int x1, int y1, IBresenhamLineCallback callback) {
        int dx, dy, p, x, y;
        dx=x1-x0;
        dy=y1-y0;
        x=x0;
        y=y0;
        p=2*dy-dx;
        while(x<x1)
        {
            if(p>=0)
            {
                if (callback.onPosition(x, y) == false){
                    return false;
                }
                y=y+1;
                p=p+2*dy-2*dx;
            }
            else
            {
                if (callback.onPosition(x, y) == false){
                    return false;
                }
                p=p+2*dy;
            }
            x=x+1;
        }
        return true;
    }

    public static long generateId(){
        return s_next_id++;
    }
}
