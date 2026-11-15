package net.krypton.client.module.impl.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.event.impl.PacketSendEvent;
import net.krypton.client.module.ClientModule;
import net.krypton.client.module.ModuleCategory;
import net.minecraft.class_2828;
import net.minecraft.class_9836;

@Environment(EnvType.CLIENT)
public final class AirStuckModule extends ClientModule {
   public static volatile AirStuckModule active;

   public AirStuckModule() {
      super("air-stuck", "Air Stuck", "Freezes you in place, even mid-air.", ModuleCategory.DONUT);
   }

   @Override
   public boolean persistEnabled() {
      return false;
   }

   @Override
   protected void onEnable() {
      active = this;
      this.on(PacketSendEvent.TYPE, this::onPacketSend);
   }

   @Override
   protected void onDisable() {
      active = null;
   }

   private void onPacketSend(PacketSendEvent event) {
      if (event.packet instanceof class_9836 || event.packet instanceof class_2828) {
         event.setCancelled(true);
      }
   }
}
