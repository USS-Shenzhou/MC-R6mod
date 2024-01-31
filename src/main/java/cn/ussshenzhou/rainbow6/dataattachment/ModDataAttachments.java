package cn.ussshenzhou.rainbow6.dataattachment;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, R6Constants.MOD_ID);

    public static final Supplier<AttachmentType<ActionData>> ACTION = ATTACHMENT_TYPES.register("action",
            () -> AttachmentType.builder(ActionData::new).build()
    );
    public static final Supplier<AttachmentType<AnimationData>> ANIMATION = ATTACHMENT_TYPES.register("animation",
            () -> AttachmentType.builder(AnimationData::new).build()
    );
}
