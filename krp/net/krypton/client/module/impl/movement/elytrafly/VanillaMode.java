package net.krypton.client.module.impl.movement.elytrafly;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.impl.movement.ElytraFlyModule;

@Environment(EnvType.CLIENT)
public final class VanillaMode extends ElytraFlightMode {
   public VanillaMode(ElytraFlyModule elytraFly) {
      super(elytraFly);
   }
}
