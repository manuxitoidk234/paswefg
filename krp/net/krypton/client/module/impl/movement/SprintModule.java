package net.krypton.client.module.impl.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.ClientModule;
import net.krypton.client.module.ModuleCategory;

@Environment(EnvType.CLIENT)
public final class SprintModule extends ClientModule {
   private static volatile SprintModule active;

   public SprintModule() {
      super("sprint", "Sprint", "Keeps you sprinting while you move forward, like vanilla Toggle Sprint.", ModuleCategory.MOVEMENT);
   }

   @Override
   protected void onEnable() {
      active = this;
   }

   @Override
   protected void onDisable() {
      active = null;
   }

   public static boolean holding() {
      return active != null;
   }
}
