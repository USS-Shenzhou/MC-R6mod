package cn.ussshenzhou.rainbow6.items;

import cn.ussshenzhou.rainbow6.capabilities.IR6PlayerCapability;
import cn.ussshenzhou.rainbow6.capabilities.ModCapabilities;
import cn.ussshenzhou.rainbow6.effects.ModEffects;
import cn.ussshenzhou.rainbow6.network.EE1DPack;
import cn.ussshenzhou.rainbow6.network.EE1DPackSend;
import cn.ussshenzhou.rainbow6.utils.ModItemGroups;
import cn.ussshenzhou.rainbow6.utils.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author USS_Shenzhou
 */
public class EE1DController extends Item {
    public static final int ACTIVATING_TIME = 20;
    public static final int PREPARE_TIME = 30;
    public static final int SCAN_TIME = 40;
    private int counter = -1;
    private List<? extends LivingEntity> list;

    public EE1DController() {
        super(new Properties()
                .group(ModItemGroups.Main)
                .maxStackSize(1)
        );
        this.setRegistryName("ee1d_controller");
    }

    private static final Predicate<LivingEntity> LIVING_ENTITY_PREDICATE = new Predicate<LivingEntity>() {
        @Override
        public boolean test(LivingEntity livingEntity) {
            return livingEntity != null;
        }
    };
    private static final Predicate<PlayerEntity> PLAYER_ENTITY_PREDICATE = new Predicate<PlayerEntity>() {
        @Override
        public boolean test(PlayerEntity playerEntity) {
            return playerEntity != null;
        }
    };

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (this.counter == -1){
            if (!worldIn.isRemote) {
                this.counter = 0;
            } else {
                worldIn.playSound(playerIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSounds.EE1D_START, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        } else {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }

    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            if (this.counter >= 0) {
                if (this.counter == ACTIVATING_TIME) {
                    LazyOptional<IR6PlayerCapability> r6PlayerCap = player.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
                    IR6PlayerCapability ir6PlayerCapability = r6PlayerCap.orElse(ModCapabilities.R6_PLAYER_CAPABILITY.getDefaultInstance());
                    if ("player".equals(ir6PlayerCapability.getOperator())) {
                        //out of match
                        list = worldIn.getEntitiesWithinAABB(LivingEntity.class, player.getBoundingBox().grow(50.0d), LIVING_ENTITY_PREDICATE);
                        //list.remove(entityIn);
                        if (!list.isEmpty()) {
                            for (LivingEntity livingEntity : list) {
                                if (livingEntity instanceof PlayerEntity) {
                                    EE1DPackSend.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) livingEntity),
                                            new EE1DPack()
                                    );
                                }
                            }
                        }
                    } else {
                        //in the match
                        list = worldIn.getEntitiesWithinAABB(PlayerEntity.class, player.getBoundingBox().grow(50.0d), PLAYER_ENTITY_PREDICATE);
                        //list.remove(entityIn);
                        if (!list.isEmpty()) {
                            for (LivingEntity livingEntity : list) {
                                EE1DPackSend.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) livingEntity),
                                        new EE1DPack()
                                );
                            }
                        }
                    }
                } else if (this.counter > ACTIVATING_TIME + PREPARE_TIME) {
                    //list.remove(entityIn);
                    for (LivingEntity target : list) {
                        if (target instanceof PlayerEntity) {

                            /*Vector3d motion = target.getMotion();
                            double speed = Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z);
                            LogManager.getLogger().info(target.getName()
                                    + "   "
                                    +speed
                            );*/

                            LazyOptional<IR6PlayerCapability> r6PlayerCap = target.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
                            IR6PlayerCapability ir6PlayerCapability = r6PlayerCap.orElse(ModCapabilities.R6_PLAYER_CAPABILITY.getDefaultInstance());
                            if (!"defender".equals(ir6PlayerCapability.getR6Team())) {
                                continue;
                            }
                        }
                        Vector3d motion = target.getMotion();
                        double speed = Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z);
                        if (speed >= 0.03) {
                            target.addPotionEffect(
                                    new EffectInstance(ModEffects.EXPOSED, SCAN_TIME + ACTIVATING_TIME + SCAN_TIME - counter, 0, false, false)
                            );
                        }
                    }
                }
                this.counter++;
                if (this.counter > ACTIVATING_TIME + PREPARE_TIME + SCAN_TIME) {
                    this.counter = -1;
                }
            }
        }
    }
}
