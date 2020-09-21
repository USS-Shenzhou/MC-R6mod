package com.ussshenzhou.rainbow6.network;

import com.ussshenzhou.rainbow6.capabilities.IR6PlayerCapability;
import com.ussshenzhou.rainbow6.capabilities.ModCapabilities;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public class SendR6PlayerCapabilityPack {
    /*private String operator;
    private String r6Team;
    private String r6TeamColor;

    public SendR6PlayerCapabilityPack(PacketBuffer buffer){
        operator = buffer.readString(Short.MAX_VALUE);
        r6Team = buffer.readString(Short.MAX_VALUE);
        r6TeamColor = buffer.readString(Short.MAX_VALUE);
    }
    public SendR6PlayerCapabilityPack(String operator, String r6Team, String r6TeamColor){
        this.operator = operator;
        this.r6Team = r6Team;
        this.r6TeamColor = r6TeamColor;
    }
    public void toBytes(PacketBuffer buffer){
        buffer.writeString(this.operator);
        buffer.writeString(this.r6Team);
        buffer.writeString(this.r6TeamColor);
    }
    public void handler(Supplier<NetworkEvent.Context> supplier){
        supplier.get().enqueueWork(()->{
            return this.operator
                }
        );
        supplier.get().setPacketHandled(true);
    }*/
}
