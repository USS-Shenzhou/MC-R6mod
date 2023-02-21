package cn.ussshenzhou.rainbow6.mixinproxy;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
public interface LevelRendererProxy {
    default LevelRendererProxy enableOrthographic(float cameraDistance1){
        return this;
    }

    default void disableOrthographic(){}

    default void setClipRoof$r6ms(boolean clipRoof$r6ms) {}
}
