package cn.ussshenzhou.rainbow6.bullets.penetrate;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

/**
 * @author USS_Shenzhou
 */
public class PenetrateDamageCounter {

    private static HashMap<BlockPos,Integer> counter = new HashMap<BlockPos, Integer>();

    public static int call(String type,BlockPos pos){
        if (counter.containsKey(pos)){
            counter.put(pos,counter.get(pos)+1);
        }
        else{
            counter.put(pos,1);
        }
        return counter.get(pos);
    }

    public static void del(BlockPos pos){
        counter.remove(pos);
    }
}
