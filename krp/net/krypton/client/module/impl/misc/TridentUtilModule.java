package net.krypton.client.module.impl.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.ClientModule;
import net.krypton.client.module.ModuleCategory;
import net.krypton.client.module.ModuleRegistry;
import net.krypton.client.module.setting.BooleanSetting;
import net.krypton.client.module.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public final class TridentUtilModule extends ClientModule {
   private final NumberSetting chargeScale = this.addSetting("scale", new NumberSetting("Charge Scale", 0.0F, 0.0F, 1.0F).step(0.1F));
   private final BooleanSetting noWater = this.addSetting("no-water", new BooleanSetting("No Water", true));

   public TridentUtilModule() {
      super("trident-util", "Trident Util", "Use tridents out of water and charge them faster.", ModuleCategory.DONUT);
   }

   private static TridentUtilModule active() {
      TridentUtilModule module = ModuleRegistry.get(TridentUtilModule.class);
      return module != null && module.isEnabled() ? module : null;
   }

   public static boolean allowOutOfWater() {
      TridentUtilModule module = active();
      return module != null && module.noWater.get();
   }

   public static int minChargeTicks(int vanilla) {
      TridentUtilModule module = active();
      return module == null ? vanilla : Math.max(1, Math.round(vanilla * module.chargeScale.get()));
   }
}
