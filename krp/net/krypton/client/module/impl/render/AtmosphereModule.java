package net.krypton.client.module.impl.render;

import com.example.client.mixin.AccessorAtmosphereClientLevel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.ClientModule;
import net.krypton.client.module.ModuleCategory;
import net.krypton.client.module.ModuleRegistry;
import net.krypton.client.module.setting.BooleanSetting;
import net.krypton.client.module.setting.ColorSetting;
import net.krypton.client.module.setting.ModeSetting;
import net.krypton.client.module.setting.NumberSetting;
import net.minecraft.class_12076;
import net.minecraft.class_1937;
import net.minecraft.class_1959;
import net.minecraft.class_2338;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_638;
import net.minecraft.class_7833;
import net.minecraft.class_9848;
import org.joml.Vector3fc;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public final class AtmosphereModule extends ClientModule {
   private static final String CUSTOM = "Custom";
   private static final String AURORA_ALWAYS = "Always";
   private static final String AURORA_COLD = "Cold Biomes";
   private static final String AURORA_OFF = "Off";
   private static final float MOON_SCALE = 1.4F;
   private static final AtmosphereModule.Theme[] THEMES = new AtmosphereModule.Theme[]{
      new AtmosphereModule.Theme("Celestia", 134172, 794680, 12099784, 3735432, 9502688, 13691135),
      new AtmosphereModule.Theme("Cosmos", 395284, 792632, 5277904, 2662655, 6844671, 13166847),
      new AtmosphereModule.Theme("Plasma", 1050640, 3151904, 16742464, 16732200, 16769192, 16773848),
      new AtmosphereModule.Theme("Abyss", 132112, 791592, 4219008, 8429760, 14741759, 16317695),
      new AtmosphereModule.Theme("Stellar", 265256, 1583184, 4235488, 2154751, 6332671, 14743807)
   };
   private final ModeSetting theme = this.addSetting(
      "theme", new ModeSetting("Theme", "Celestia", "Celestia", "Cosmos", "Plasma", "Abyss", "Stellar", "Custom")
   );
   private final ColorSetting nightColor = this.addSetting("night-color", new ColorSetting("Night Sky Color", -15720392));
   private final ColorSetting sunsetColor = this.addSetting("sunset-color", new ColorSetting("Sunset Color", -4677432));
   private final ColorSetting auroraColor = this.addSetting("aurora-color", new ColorSetting("Aurora Color", -13041784));
   private final ColorSetting auroraTopColor = this.addSetting("aurora-top-color", new ColorSetting("Aurora Top Color", -7274528));
   private final ModeSetting skyShader = this.addSetting(
      "sky-shader", new ModeSetting("Sky Shader", "Off", "Off", "Space", "Northern Lights", "Ringed Planet", "Black Hole")
   );
   private final NumberSetting shaderDaytime = this.addSetting("sky-shader-daytime", new NumberSetting("Daytime Strength", 1.0F, 0.0F, 1.0F).step(0.85F));
   private final NumberSetting shaderBrightness = this.addSetting("sky-shader-brightness", new NumberSetting("Shader Brightness", 1.0F, 0.2F, 2.0F).step(0.1F));
   private final NumberSetting shaderSpeed = this.addSetting("sky-shader-speed", new NumberSetting("Shader Speed", 1.0F, 0.0F, 3.0F).step(0.1F));
   private final NumberSetting shaderStars = this.addSetting("sky-shader-stars", new NumberSetting("Shader Stars", 1.0F, 0.0F, 2.0F).step(0.1F));
   private final NumberSetting shaderEffect = this.addSetting("sky-shader-effect", new NumberSetting("Glow / Aurora Strength  ", 1.0F, 0.0F, 2.0F).step(0.1F));
   private final NumberSetting shaderBand = this.addSetting("sky-shader-galaxy", new NumberSetting("Galaxy Band", 1.0F, 0.0F, 2.0F).step(0.1F));
   private final BooleanSetting gradientSky = this.addSetting("gradient-sky", new BooleanSetting("Gradient Sky", true));
   private final BooleanSetting keepInRain = this.addSetting("keep-in-rain", new BooleanSetting("Keep In Rain", true));
   private final BooleanSetting celestialGlow = this.addSetting("sun-moon-glow", new BooleanSetting("Sun & Moon Glow", true));
   private final BooleanSetting stars = this.addSetting("stars", new BooleanSetting("Stars", true));
   private final NumberSetting starBrightness = this.addSetting("star-brightness", new NumberSetting("Star Brightness", 1.3F, 0.5F, 2.0F).step(0.1F));
   private final BooleanSetting milkyWay = this.addSetting("milky-way", new BooleanSetting("Milky Way", true));
   private final NumberSetting milkyWayBrightness = this.addSetting(
      "milky-way-brightness", new NumberSetting("Milky Way Brightness", 1.0F, 0.2F, 2.0F).step(0.1F)
   );
   private final BooleanSetting shootingStars = this.addSetting("shooting-stars", new BooleanSetting("Shooting Stars", true));
   private final ModeSetting aurora = this.addSetting("aurora", new ModeSetting("Aurora", "Always", "Always", "Cold Biomes", "Off"));
   private final NumberSetting auroraBrightness = this.addSetting("aurora-brightness", new NumberSetting("Aurora Brightness", 1.0F, 0.2F, 2.0F).step(0.1F));
   private final NumberSetting auroraSpeed = this.addSetting("aurora-speed", new NumberSetting("Aurora Speed", 1.0F, 0.2F, 3.0F).step(0.1F));
   private final BooleanSetting cloudColors = this.addSetting("cloud-colors", new BooleanSetting("Cloud Colors", true));
   private final BooleanSetting endSky = this.addSetting("end-sky", new BooleanSetting("End Sky", true));
   private final BooleanSetting netherColors = this.addSetting("nether-colors", new BooleanSetting("Nether Colors", true));
   private final AtmosphereSky sky = new AtmosphereSky();
   private final AtmosphereStars starField = new AtmosphereStars();
   private final AtmosphereAurora auroraMesh = new AtmosphereAurora();
   private final AtmosphereShaderSky shaderSky = new AtmosphereShaderSky();
   private final class_4587 world = new class_4587();
   private float night;
   private float rainBrightness = 1.0F;
   private float auroraHere;
   private long lastFrame;
   private boolean moonScaled;
   private boolean shaderRendered;

   public AtmosphereModule() {
      super("atmosphere", "Atmosphere", "A custom sky with stars and an aurora.", ModuleCategory.VISUALS);
      this.nightColor.visibleWhen(() -> this.theme.is("Custom"));
      this.sunsetColor.visibleWhen(() -> this.theme.is("Custom"));
      this.auroraColor.visibleWhen(() -> this.theme.is("Custom"));
      this.auroraTopColor.visibleWhen(() -> this.theme.is("Custom"));
      this.shaderDaytime.visibleWhen(this::shaderOn);
      this.shaderBrightness.visibleWhen(this::shaderOn);
      this.shaderSpeed.visibleWhen(this::shaderOn);
      this.shaderStars.visibleWhen(this::shaderOn);
      this.shaderEffect.visibleWhen(this::shaderOn);
      this.shaderBand.visibleWhen(this::shaderOn);
      this.stars.visibleWhen(() -> !this.shaderOn());
      this.starBrightness.visibleWhen(() -> this.stars.get() && !this.shaderOn());
      this.milkyWay.visibleWhen(() -> !this.shaderOn());
      this.milkyWayBrightness.visibleWhen(() -> this.milkyWay.get() && !this.shaderOn());
      this.aurora.visibleWhen(() -> !this.shaderOn());
      this.auroraBrightness.visibleWhen(() -> !this.aurora.is("Off") && !this.shaderOn());
      this.auroraSpeed.visibleWhen(() -> !this.aurora.is("Off") && !this.shaderOn());
   }

   public static AtmosphereModule get() {
      AtmosphereModule module = ModuleRegistry.get(AtmosphereModule.class);
      return module != null && module.isEnabled() ? module : null;
   }

   @Override
   protected void onDisable() {
      this.sky.close();
      this.starField.close();
      this.auroraMesh.close();
      this.shaderSky.close();
      this.shaderRendered = false;
      this.lastFrame = 0L;
      this.auroraHere = 0.0F;
   }

   public void extract(class_638 level, float partialTick, class_12076 state) {
      this.shaderRendered = false;
      this.night = class_3532.method_15363(state.field_63094 * 2.0F, 0.0F, 1.0F);
      float rain = 1.0F - state.field_63093;
      float thunder = level.method_8478(partialTick);
      if (this.keepInRain.get()) {
         this.night = Math.max(this.night, clearNight(state.field_63091) * rain);
         state.field_63094 = Math.max(state.field_63094, this.night * 0.5F);
         state.field_63093 = 1.0F;
         rain = 0.0F;
         thunder = 0.0F;
      }

      this.rainBrightness = 1.0F - rain;
      AtmosphereModule.Theme colours = this.currentTheme();
      int flashTicks = ((AccessorAtmosphereClientLevel)level).krypton$atmosphereSkyFlash();
      this.sky
         .update(
            state.field_63091,
            state.field_64465,
            rain,
            thunder,
            flashTicks > 0 ? Math.min(1.0F, (flashTicks - partialTick) / 2.0F) : 0.0F,
            colours.nightZenith,
            colours.nightHorizon,
            colours.twilight
         );
   }

   public boolean keepsSkyInRain() {
      return this.keepInRain.get();
   }

   private static float clearNight(float sunAngle) {
      float dark = class_3532.method_15363(0.75F - 2.0F * class_3532.method_15362(sunAngle), 0.0F, 1.0F);
      return dark * dark;
   }

   public boolean skyDisc() {
      if (this.gradientSky.get() && this.sky.ready()) {
         if (AtmosphereQuality.mobile() && this.shaderOn() && this.shaderVisibility() >= 1.0F) {
            this.renderShaderSky();
            if (!this.shaderRendered) {
               this.sky.renderDome(this.world);
            }

            return true;
         } else {
            this.sky.renderDome(this.world);
            this.renderShaderSky();
            return true;
         }
      } else {
         return false;
      }
   }

   public void afterSkyDisc() {
      if (!this.shaderRendered) {
         this.renderShaderSky();
      }
   }

   private void renderShaderSky() {
      if (this.shaderOn()) {
         AtmosphereModule.Theme colours = this.currentTheme();
         this.shaderRendered = this.shaderSky
            .render(
               this.skyShader.get(),
               this.shaderVisibility(),
               this.shaderBrightness.get(),
               this.shaderSpeed.get(),
               this.shaderStars.get(),
               this.shaderEffect.get(),
               this.shaderBand.get(),
               colours.auroraLow,
               colours.auroraHigh,
               colours.milkyWay,
               colours.nightZenith,
               colours.nightHorizon
            );
      }
   }

   private boolean shaderOn() {
      return !this.skyShader.is("Off");
   }

   private boolean shaderAurora() {
      return this.skyShader.is("Northern Lights");
   }

   private float shaderVisibility() {
      return class_3532.method_16439(this.night, this.shaderDaytime.get(), 1.0F) * (0.4F + 0.6F * this.rainBrightness);
   }

   public boolean replacesSunrise() {
      return this.gradientSky.get() && this.sky.ready();
   }

   public boolean stars(class_4587 var1) {
      if (this.shaderOn()) {
         return true;
      }

      if (this.shaderRendered) {
         return true;
      }

      float var2 = this.night * this.rainBrightness;
      if (this.milkyWay.get()) {
         this.starField.renderBand(var1, var2 * this.milkyWayBrightness.get(), this.currentTheme().milkyWay);
      }

      if (!this.stars.get()) {
         return false;
      }

      this.starField.renderStars(var1, var2, this.starBrightness.get());
      return true;
   }

   public void beforeMoon(class_4587 pose) {
      this.moonScaled = this.celestialGlow.get();
      if (this.moonScaled) {
         pose.method_22903();
         pose.method_22905(1.4F, 1.0F, 1.4F);
      }
   }

   public void afterMoon(class_4587 pose) {
      if (this.moonScaled) {
         pose.method_22909();
      }

      this.moonScaled = false;
   }

   public void afterCelestials() {
      long now = System.nanoTime();
      float dt = this.lastFrame == 0L ? 0.0F : Math.min((float)(now - this.lastFrame) / 1.0E9F, 0.1F);
      this.lastFrame = now;
      if (this.sky.ready()) {
         if (this.celestialGlow.get()) {
            this.sky.renderHalos(this.world, this.rainBrightness, this.night);
         }

         if (this.shootingStars.get()) {
            this.sky.renderMeteors(this.world, this.night, this.rainBrightness);
         }

         this.auroraHere = this.auroraHere + (this.auroraTarget() - this.auroraHere) * Math.min(1.0F, dt * 0.5F);
         if (this.auroraHere > 0.01F && this.night > 0.01F && !this.shaderRendered) {
            AtmosphereModule.Theme colours = this.currentTheme();
            float time = (float)(now % 3600000000000L) / 1.0E9F * this.auroraSpeed.get();
            float intensity = this.auroraHere * this.night * (0.35F + 0.65F * this.rainBrightness) * this.auroraBrightness.get();
            this.auroraMesh.render(this.world, intensity, time, colours.auroraLow, colours.auroraHigh);
         }
      }
   }

   private float auroraTarget() {
      if (this.aurora.is("Off")) {
         return 0.0F;
      }

      if (this.aurora.is("Always")) {
         return 1.0F;
      }

      class_310 mc = class_310.method_1551();
      if (mc.field_1687 == null) {
         return 0.0F;
      }

      class_2338 at = class_2338.method_49638(mc.field_1773.method_19418().method_71156());
      return ((class_1959)mc.field_1687.method_23753(at).comp_349()).method_33599(at, mc.field_1687.method_8615()) ? 1.0F : 0.0F;
   }

   public void fog(class_4184 camera, class_638 level, Vector4f color) {
      if (level.method_27983() == class_1937.field_25179) {
         if (this.gradientSky.get() && this.sky.ready()) {
            Vector3fc look = camera.method_19335();
            int rgb = this.sky.horizonToward(look.x(), look.z());
            color.x = (rgb >> 16 & 0xFF) / 255.0F;
            color.y = (rgb >> 8 & 0xFF) / 255.0F;
            color.z = (rgb & 0xFF) / 255.0F;
         }

         if (this.shaderOn()) {
            this.shaderFog(color);
         }
      } else if (level.method_27983() == class_1937.field_25180 && this.netherColors.get()) {
         float luma = 0.2126F * color.x + 0.7152F * color.y + 0.0722F * color.z;
         color.x = class_3532.method_15363((luma + (color.x - luma) * 1.45F) * 1.12F, 0.0F, 1.0F);
         color.y = class_3532.method_15363((luma + (color.y - luma) * 1.45F) * 1.12F, 0.0F, 1.0F);
         color.z = class_3532.method_15363((luma + (color.z - luma) * 1.45F) * 1.12F, 0.0F, 1.0F);
      }
   }

   private void shaderFog(Vector4f color) {
      int horizon = this.currentTheme().nightHorizon;
      float scale = this.shaderAurora() ? 0.85F : 0.3F;
      float amount = this.shaderVisibility() * 0.85F;
      color.x = class_3532.method_16439(amount, color.x, (horizon >> 16 & 0xFF) / 255.0F * scale);
      color.y = class_3532.method_16439(amount, color.y, (horizon >> 8 & 0xFF) / 255.0F * scale);
      color.z = class_3532.method_16439(amount, color.z, (horizon & 0xFF) / 255.0F * scale);
   }

   public int clouds(int argb) {
      class_310 mc = class_310.method_1551();
      if (this.cloudColors.get() && this.sky.ready() && mc.field_1687 != null && mc.field_1687.method_27983() == class_1937.field_25179) {
         float golden = this.sky.golden();
         float dark = this.sky.nightness();
         int target = class_9848.method_61319(golden, class_9848.method_61319(dark, 16777215, this.currentTheme().nightHorizon), this.sky.twilightGlow());
         float amount = Math.max(golden * 0.75F, dark * 0.55F);
         return keepBrightness(argb, target, amount);
      } else {
         return argb;
      }
   }

   public boolean endSky() {
      if (!this.endSky.get()) {
         return false;
      }

      long now = System.nanoTime();
      this.sky.renderEndDome(this.world);
      class_4587 turning = new class_4587();
      turning.method_22907(class_7833.field_40716.rotationDegrees((float)(now % 1200000000000L) / 1.0E9F * 0.3F));
      this.starField.renderNebulae(turning, 1.0F);
      this.starField.renderStars(turning, 1.0F, 1.2F);
      return true;
   }

   private AtmosphereModule.Theme currentTheme() {
      for (AtmosphereModule.Theme each : THEMES) {
         if (this.theme.is(each.name)) {
            return each;
         }
      }

      int horizon = this.nightColor.rgb();
      return new AtmosphereModule.Theme(
         "Custom", AtmosphereSky.scale(horizon, 0.3F), horizon, this.sunsetColor.rgb(), this.auroraColor.rgb(), this.auroraTopColor.rgb(), 15263999
      );
   }

   private static int keepBrightness(int argb, int target, float amount) {
      if (amount <= 0.0F) {
         return argb;
      }

      float r = (argb >> 16 & 0xFF) / 255.0F;
      float g = (argb >> 8 & 0xFF) / 255.0F;
      float b = (argb & 0xFF) / 255.0F;
      float tr = (target >> 16 & 0xFF) / 255.0F;
      float tg = (target >> 8 & 0xFF) / 255.0F;
      float tb = (target & 0xFF) / 255.0F;
      float scale = luminance(r, g, b) / Math.max(luminance(tr, tg, tb), 0.001F);
      r = class_3532.method_16439(amount, r, Math.min(1.0F, tr * scale));
      g = class_3532.method_16439(amount, g, Math.min(1.0F, tg * scale));
      b = class_3532.method_16439(amount, b, Math.min(1.0F, tb * scale));
      return argb & 0xFF000000 | channel(r) << 16 | channel(g) << 8 | channel(b);
   }

   private static float luminance(float r, float g, float b) {
      return 0.2126F * r + 0.7152F * g + 0.0722F * b;
   }

   private static int channel(float value) {
      return class_3532.method_15340(Math.round(value * 255.0F), 0, 255);
   }

   @Environment(EnvType.CLIENT)
   private record Theme(String name, int nightZenith, int nightHorizon, int twilight, int auroraLow, int auroraHigh, int milkyWay) {
   }
}
