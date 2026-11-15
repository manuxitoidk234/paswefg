package net.krypton.client.gui;

import com.example.client.mixin.AccessorLevelLoadingScreen;
import com.example.client.mixin.AccessorLoadingOverlay;
import imgui.ImDrawList;
import imgui.ImFont;
import imgui.ImGui;
import imgui.ImGuiIO;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.imgui.ImGuiManager;
import net.minecraft.class_156;
import net.minecraft.class_310;
import net.minecraft.class_3928;
import net.minecraft.class_424;
import net.minecraft.class_425;
import net.minecraft.class_435;
import net.minecraft.class_437;

@Environment(EnvType.CLIENT)
public final class LoadingScreens {
   private static final String HEAD_RESOURCE = "/assets/krypton/textures/gui/krbg.png";
   private static final int BACKGROUND_RGB = 722950;
   private static final float FADE_OUT_SECONDS = 0.45F;
   private static final long ORIGIN = System.nanoTime();
   private static long lastActiveAt = -1L;
   private static float lastProgress;
   private static float lastAlpha;
   private static String lastStatus = "";
   private static float shownFill;

   private LoadingScreens() {
   }

   public static int backgroundColor(int argb) {
      return !KryptonTitleScreen.enabled() ? argb : argb & 0xFF000000 | 722950;
   }

   public static boolean hasWork() {
      if (!KryptonTitleScreen.enabled()) {
         return false;
      }

      if (lastActiveAt >= 0L) {
         return true;
      }

      class_310 mc = class_310.method_1551();
      return mc.method_18506() instanceof class_425
         || mc.field_1755 instanceof class_3928
         || mc.field_1755 instanceof class_424
         || mc.field_1755 instanceof class_435;
   }

   public static void render() {
      if (KryptonTitleScreen.enabled()) {
         class_310 mc = class_310.method_1551();
         long now = System.nanoTime();
         float alpha;
         float progress;
         String status;
         if (mc.method_18506() instanceof class_425 overlay) {
            AccessorLoadingOverlay state = (AccessorLoadingOverlay)overlay;
            alpha = reloadAlpha(state);
            progress = state.krypton$getProgress();
            status = "Loading";
         } else {
            class_437 screen = mc.field_1755;
            if (screen instanceof class_3928 loading) {
               progress = ((AccessorLevelLoadingScreen)loading).krypton$getProgress();
               status = "Entering world";
            } else {
               if (!(screen instanceof class_424) && !(screen instanceof class_435)) {
                  drawFadingOut(now);
                  return;
               }

               progress = -1.0F;
               String title = screen.method_25440().getString();
               status = title.isBlank() ? "Loading" : title;
            }

            alpha = 1.0F;
         }

         lastActiveAt = now;
         lastProgress = progress;
         lastStatus = status;
         lastAlpha = alpha;
         draw(alpha, progress, status, now);
      }
   }

   private static void drawFadingOut(long var0) {
      lastActiveAt = -1L;
   }

   private static float reloadAlpha(AccessorLoadingOverlay state) {
      long millis = class_156.method_658();
      long fadeOutStart = state.krypton$getFadeOutStart();
      long fadeInStart = state.krypton$getFadeInStart();
      float fadeOut = fadeOutStart > -1L ? (float)(millis - fadeOutStart) / 1000.0F : -1.0F;
      float fadeIn = fadeInStart > -1L ? (float)(millis - fadeInStart) / 500.0F : -1.0F;
      if (fadeOut >= 1.0F) {
         return 1.0F - clamp01(fadeOut - 1.0F);
      } else {
         return state.krypton$isFadeIn() ? clamp01(fadeIn) : 1.0F;
      }
   }

   private static void draw(float a, float progress, String status, long now) {
      if (!(a <= 0.001F)) {
         ImGuiIO io = ImGui.getIO();
         float sw = io.getDisplaySizeX();
         float sh = io.getDisplaySizeY();
         float s = ImGuiManager.getInstance().uiScale();
         float t = seconds(now - ORIGIN);
         float dt = Math.max(0.001F, io.getDeltaTime());
         ImDrawList dl = ImGui.getForegroundDrawList();
         dl.addRectFilledMultiColor(
            0.0F, 0.0F, sw, sh, EgyptDraw.col(-15857145, a), EgyptDraw.col(-15857145, a), EgyptDraw.col(-16514302, a), EgyptDraw.col(-16514302, a)
         );
         float headSize = Math.min(sh * 0.3F, 290.0F * s);
         float sunR = headSize * 0.62F;
         float cx = sw * 0.5F;
         float cy = sh * 0.42F;
         float var10004 = t * 0.12F;
         float target = progress >= 0.0F ? clamp01(progress) : (float)(t * 0.45 % 1.0);
         if (!(progress < 0.0F) && !(target < shownFill)) {
            shownFill = shownFill + (target - shownFill) * Math.min(1.0F, dt * 6.0F);
         } else {
            shownFill = target;
         }

         EgyptDraw.texture("/assets/krypton/textures/gui/krbg.png");
         ThemeImages.Texture head = null;
         float x0 = cx - headSize * 0.5F;
         float y0 = cy - headSize * 0.5F;
         float x1 = x0 + headSize;
         float y1 = y0 + headSize;
         if (head != null) {
            EgyptDraw.glow(dl, cx, y1 - headSize * shownFill, headSize * 0.45F, -871878, 0.12F * a);
            dl.addImage(head.id(), 0.0F, 0.0F, sw, sh, 0.0F, 0.0F, 1.0F, 1.0F, EgyptDraw.col(-9743568, 0.35F * a));
            float clipTop = y1 - headSize * shownFill;
            dl.pushClipRect(0.0F, 0.0F, sw, sh, true);
            dl.addImage(head.id(), 0.0F, 0.0F, sw, sh, 0.0F, 0.0F, 1.0F, 1.0F, EgyptDraw.col(-1, a));
            dl.popClipRect();
         }

         ImFont big = ImGuiManager.getInstance().menuFont();
         ImFont small = ImGuiManager.getInstance().menuTagFont();
         float nameY = cy + sunR * 1.34F + 14.0F * s;
         EgyptDraw.drawSpaced(dl, big, cx, nameY + 3.0F * s, "krypton new gen", 11.0F * s, -12133121, a * 0.22F, false);
         EgyptDraw.drawSpaced(dl, big, cx, nameY + 1.35F * s, "krypton new gen", 11.0F * s, -12133121, a * 0.4F, false);
         EgyptDraw.drawSpaced(dl, big, cx, nameY, "krypton new gen", 11.0F * s, -12133121, a, true);
         float bigSize = big != null ? big.getFontSize() : 24.0F * s;
         skipLine(
            dl,
            cx - 34.0F * s,
            nameY + bigSize + 3.5F * s,
            cx + 34.0F * s,
            nameY + bigSize + 3.5F * s,
            EgyptDraw.col(-12133121, a * 0.9F),
            Math.max(1.0F, 1.15F * s)
         );
         float barW = 260.0F * s;
         float barY = nameY + bigSize + 16.0F * s;
         float line = Math.max(1.0F, 1.5F * s);
         float bx = cx - barW * 0.5F;
         dl.addLine(bx, barY, bx + barW, barY, EgyptDraw.col(-12133121, 0.18F * a), line);
         float end = progress >= 0.0F ? bx + barW * shownFill : bx + barW * (0.5F + 0.5F * (float)Math.sin(t * 2.2F));
         float start = progress >= 0.0F ? bx : Math.max(bx, end - barW * 0.25F);
         dl.addLine(start, barY, end, barY, EgyptDraw.col(-12133121, a), line);
         float d = 3.5F * s;
         dl.addQuadFilled(end, barY - d, end + d, barY, end, barY + d, end - d, barY, EgyptDraw.col(-12133121, a));
         String line2 = progress >= 0.0F ? status + "  ·  " + Math.round(clamp01(progress) * 100.0F) + "%" : status;
         EgyptDraw.drawSpaced(dl, small, cx, barY + 12.0F * s, line2.toUpperCase(Locale.ROOT), 5.0F * s, -12133121, a, true);
      }
   }

   private static float seconds(long nanos) {
      return (float)nanos / 1.0E9F;
   }

   private static float clamp01(float value) {
      return Math.max(0.0F, Math.min(1.0F, value));
   }

   private static void skipLine(ImDrawList var0, float var1, float var2, float var3, float var4, int var5, float var6) {
   }
}
