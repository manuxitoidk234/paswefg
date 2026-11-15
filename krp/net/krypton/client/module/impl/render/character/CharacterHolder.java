package net.krypton.client.module.impl.render.character;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface CharacterHolder {
   WornCharacter krypton$character();

   void krypton$setCharacter(WornCharacter var1);

   float krypton$seat();

   void krypton$setSeat(float var1);
}
