package com.ussshenzhou.rainbow6.util;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
/**
 * @author USS_Shenzhou
 */
public class ModSounds {
    public static SoundEvent MUTE = new SoundEvent(new ResourceLocation("rainbow6","mute")).setRegistryName("mute");
    public static SoundEvent BARRICADE_BREAK = new SoundEvent(new ResourceLocation("rainbow6","barricade_break")).setRegistryName("barricade_break");
    public static SoundEvent BARRICADE_PLACE = new SoundEvent(new ResourceLocation("rainbow6","barricade_place")).setRegistryName("barricade_place");
    public static SoundEvent IMPACT_GRENADE_THROW = new SoundEvent(new ResourceLocation("rainbow6","impact_grenade_throw")).setRegistryName("impact_grenade_throw");
    public static SoundEvent NITRO_CELL_THROW = new SoundEvent(new ResourceLocation("rainbow6","nitro_cell_throw")).setRegistryName("nitro_cell_throw");
    public static SoundEvent NITRO_CELL_HIT = new SoundEvent(new ResourceLocation("rainbow6","nitro_cell_hit")).setRegistryName("nitro_cell_hit");
    public static SoundEvent REINFORCEMENT_PLACE = new SoundEvent(new ResourceLocation("rainbow6","reinforcement_place")).setRegistryName("reinforcement_place");
    public static SoundEvent BLACKMIRROR_SET = new SoundEvent(new ResourceLocation("rainbow6","blackmirror_set")).setRegistryName("blackmirror_set");
    public static SoundEvent FRAGGRENADE_READY = new SoundEvent(new ResourceLocation("rainbow6","fraggrenade_ready")).setRegistryName("fraggrenade_ready");
    public static SoundEvent FRAGGRENADE_TOUCH = new SoundEvent(new ResourceLocation("rainbow6","fraggrenade_touch")).setRegistryName("fraggrenade_touch");
    public static SoundEvent BLACKMIRROR_BREAK = new SoundEvent(new ResourceLocation("rainbow6","blackmirror_break")).setRegistryName("blackmirror_break");
    public static SoundEvent REMOTEGASGRENADE_THROW = new SoundEvent(new ResourceLocation("rainbow6","remotegasgrenade_throw")).setRegistryName("remotegasgrenade_throw");
    public static SoundEvent REMOTEGASGRENADE_EXPLODE = new SoundEvent(new ResourceLocation("rainbow6","remotegasgrenade_explode")).setRegistryName("remotegasgrenade_explode");
    public static SoundEvent EXPLODER_CLICK = new SoundEvent(new ResourceLocation("rainbow6","exploder_click")).setRegistryName("exploder_click");
    public static SoundEvent PROXIMITY_ALARM = new SoundEvent(new ResourceLocation("rainbow6","proximity_alarm")).setRegistryName("proximity_alarm");
}
