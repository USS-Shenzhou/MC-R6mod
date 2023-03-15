package cn.ussshenzhou.rainbow6.mixinproxy;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
public interface LevelRendererProxy {
    default LevelRendererProxy r6msEnableOrthographic(float cameraDistance1){
        return this;
    }

    default void r6msDisableOrthographic(){}

    default void setR6msClipRoof(boolean r6msClipRoof) {}
}
