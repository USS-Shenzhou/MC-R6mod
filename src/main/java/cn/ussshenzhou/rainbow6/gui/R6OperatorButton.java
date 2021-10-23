package cn.ussshenzhou.rainbow6.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * @author USS_Shenzhou
 */
public class R6OperatorButton extends R6Button {
    public R6OperatorButton(String operator, int x, int y, ITextComponent title, IPressable pressedAction) {
        super(x, y, 32,32, title, pressedAction);
        this.operator = operator;
    }

    public R6OperatorButton(String operator, int x, int y, ITextComponent title, IPressable pressedAction, ITooltip onTooltip) {
        super(x, y, 32,32, title, pressedAction, onTooltip);
        this.operator = operator;
    }

    private String operator = "unknown";
    public final ResourceLocation OPERATORS_LOCATION = new ResourceLocation("rainbow6:textures/gui/operators.png");

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(OPERATORS_LOCATION);
        RenderSystem.enableBlend();
        if (this.isHovered){
            fill(matrixStack,this.x,this.y,this.x+32,this.y+32,0x80ffffff);
        }
        blit(matrixStack, this.x, this.y, InGameClientProperties.getOperatorLogo(operator).getX(), InGameClientProperties.getOperatorLogo(operator).getY(), 32, 32, 128, 576);
    }
}
