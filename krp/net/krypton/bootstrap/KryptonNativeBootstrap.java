package net.krypton.bootstrap;

import imgui.KryptonAndroidNative;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public final class KryptonNativeBootstrap implements PreLaunchEntrypoint {
   public void onPreLaunch() {
      KryptonAndroidNative.prepare();
   }
}
