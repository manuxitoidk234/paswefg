package com.example.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_425;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(class_425.class)
public interface AccessorLoadingOverlay {
   @Accessor("field_17770")
   float krypton$getProgress();

   @Accessor("field_17771")
   long krypton$getFadeOutStart();

   @Accessor("field_18220")
   long krypton$getFadeInStart();

   @Accessor("field_18219")
   boolean krypton$isFadeIn();
}
