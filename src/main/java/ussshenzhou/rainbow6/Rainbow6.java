package ussshenzhou.rainbow6;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod("r6ms")
public class Rainbow6 {
    public static final String MOD_ID = "r6ms";

    public Rainbow6() {

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
