package net.krypton.client.module.impl.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_10055;
import net.minecraft.class_11659;
import net.minecraft.class_2960;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_591;

@Environment(EnvType.CLIENT)
public final class PharaohHeaddressLayer extends class_3887<class_10055, class_591> {
   private static final class_2960 TEXTURE = class_2960.method_60655("krypton", "textures/cosmetic/headdress_base.png");
   private static final float HEAD_CENTRE = -0.25F;

   public PharaohHeaddressLayer(class_3883<class_10055, class_591> parent) {
      super(parent);
   }

   public void submit(class_4587 poseStack, class_11659 collector, int light, class_10055 state, float yRot, float xRot) {
   }
}
