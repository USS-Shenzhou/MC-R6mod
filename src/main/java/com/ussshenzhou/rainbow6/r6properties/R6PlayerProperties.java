package com.ussshenzhou.rainbow6.r6properties;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
@OnlyIn(Dist.CLIENT)
public class R6PlayerProperties {
    private static ArrayList<R6PlayerProperty> r6PlayerPropertiesList = new ArrayList<>();

    public void addPlayerToList(R6PlayerProperty r6PlayerProperty){
        r6PlayerPropertiesList.add(r6PlayerProperty);
    }

    public void delPlayerToList(R6PlayerProperty r6PlayerProperty){
        r6PlayerPropertiesList.remove(r6PlayerProperty);
    }
}
