package com.ussshenzhou.rainbow6.util;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSounds {
    public static SoundEvent MUTE = new SoundEvent(new ResourceLocation("rainbow6","mute")).setRegistryName("mute");
    public static SoundEvent BARRICADE_BREAK = new SoundEvent(new ResourceLocation("rainbow6","barricade_break")).setRegistryName("barricade_break");
    public static SoundEvent BARRICADE_PLACE = new SoundEvent(new ResourceLocation("rainbow6","barricade_place")).setRegistryName("barricade_place");
    public static SoundEvent IMPACT_GRENADE_THROW = new SoundEvent(new ResourceLocation("rainbow6","impact_grenade_throw")).setRegistryName("impact_grenade_throw");
    public static SoundEvent NITRO_CELL_THROW = new SoundEvent(new ResourceLocation("rainbow6","nitro_cell_throw")).setRegistryName("nitro_cell_throw");
    public static SoundEvent NITRO_CELL_HIT = new SoundEvent(new ResourceLocation("rainbow6","nitro_cell_hit")).setRegistryName("nitro_cell_hit");
    public static SoundEvent REINFORCEMENT_PLACE = new SoundEvent(new ResourceLocation("rainbow6","reinforcement_place")).setRegistryName("reinforcement_place");
    public static SoundEvent BLACKMIRROR_SET = new SoundEvent(new ResourceLocation("rainbow6","blackmirror_set")).setRegistryName("blackmirror_set");
}
