package net.krypton.client.input;

import imgui.glfw.KryptonKeys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1041;
import net.minecraft.class_3675;

@Environment(EnvType.CLIENT)
public final class KryptonKeyPolling {
   private KryptonKeyPolling() {
   }

   public static boolean isKeyDown(class_1041 window, int key) {
      return KryptonKeys.isAndroid() ? KryptonKeys.getKey(window.method_4490(), key) == 1 : class_3675.method_15987(window, key);
   }
}
