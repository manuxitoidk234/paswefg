package net.krypton.client.event.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.event.CancellableEvent;
import net.krypton.client.event.PooledCancellableEventType;
import net.minecraft.class_2596;

@Environment(EnvType.CLIENT)
public final class PacketReceiveNettyEvent extends CancellableEvent {
   public static final PooledCancellableEventType<PacketReceiveNettyEvent> TYPE = new PooledCancellableEventType<>(
      "PacketReceiveNettyEvent", PacketReceiveNettyEvent::new, 1024
   );
   public class_2596<?> packet;

   private PacketReceiveNettyEvent() {
   }

   public static boolean dispatchSync(class_2596<?> packet) {
      if (!TYPE.hasPreListeners()) {
         return false;
      }

      PacketReceiveNettyEvent event = TYPE.pool.acquire();
      event.packet = packet;
      event.resetCancellation();

      try {
         TYPE.dispatch(event);
         return event.isCancelled();
      } finally {
         event.packet = null;
         TYPE.pool.release(event);
      }
   }
}
