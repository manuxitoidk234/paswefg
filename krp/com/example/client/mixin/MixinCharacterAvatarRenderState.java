package com.example.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.impl.render.character.CharacterHolder;
import net.krypton.client.module.impl.render.character.WornCharacter;
import net.minecraft.class_10055;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(class_10055.class)
public abstract class MixinCharacterAvatarRenderState implements CharacterHolder {
   @Unique
   private WornCharacter krypton$character;
   @Unique
   private float krypton$seat;

   @Override
   public WornCharacter krypton$character() {
      return this.krypton$character;
   }

   @Override
   public void krypton$setCharacter(WornCharacter character) {
      this.krypton$character = character;
   }

   @Override
   public float krypton$seat() {
      return this.krypton$seat;
   }

   @Override
   public void krypton$setSeat(float knee) {
      this.krypton$seat = knee;
   }
}
