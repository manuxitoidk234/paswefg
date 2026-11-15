package net.krypton.client.gui;

import imgui.ImDrawList;
import imgui.ImFont;
import it.unimi.dsi.fastutil.longs.Long2FloatOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.config.Surface;

@Environment(EnvType.CLIENT)
final class HudChrome {
   static final int TEXT_VALUE = GuiPalette.abgr(-1774344);
   static final int TEXT_NAME = GuiPalette.abgr(-3616030);
   static final int TEXT_LABEL = GuiPalette.abgr(-7695453);
   static final int WARN = GuiPalette.abgr(-47814);
   static final int BORDER = 402653183;
   static final int DIVIDER = 536870911;
   static final int TRACK = 452984831;
   static final int CHIP_FILL = GuiPalette.abgr(-350873571);
   private static final int TEXT_GUARD = -1946157056;
   private static final Long2FloatOpenHashMap DIGIT_CELLS = new Long2FloatOpenHashMap();
   private static final String[] ASCII = new String[128];

   private HudChrome() {
   }

   static void card(ImDrawList var0, Surface var1, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8, float var9) {
      if (!(var7 <= 0.001F)) {
         if (var8) {
            shadow(var0, var2, var3, var4, var5, var6, var7, var9);
         }

         bloomBox(var0, var2, var3, var4, var5, var6, var7, var9);
         var0.addRectFilled(var2, var3, var2 + var4, var3 + var5, scaleAlpha(GuiPalette.fill(var1), var7), var6);
         var0.addRectFilled(var2, var3, var2 + var4, var3 + var5 * 0.38F, scaleAlpha(989849816, var7), var6);
         SurfaceBackground.draw(var0, var1, var2, var3, var4, var5, var6, var7);
         var0.addRect(var2, var3, var2 + var4, var3 + var5, scaleAlpha(GuiPalette.accent(), var7 * 0.72F), var6, 0, 1.0F);
         if (!(var7 <= 0.05F)) {
            float var10 = Math.max(Math.round(2.0F * var9), 2.0F);
            glowRail(var0, var2 + 1.0F, var3 + 1.0F, var2 + 1.0F + var10, var3 + var5 - 1.0F, var7 * 0.85F, var9);
         }
      }
   }

   static void shadow(ImDrawList dl, float x, float y, float w, float h, float r, float alpha, float scale) {
      float s = Math.max(1.0F, scale);
      dl.addRectFilled(x - s, y - 0.5F * s, x + w + s, y + h + 1.5F * s, scaleAlpha(771751936, alpha), r + s);
      dl.addRectFilled(x - 2.0F * s, y + 0.5F * s, x + w + 2.0F * s, y + h + 3.5F * s, scaleAlpha(385875968, alpha), r + 2.0F * s);
      dl.addRectFilled(x - 3.0F * s, y + 1.5F * s, x + w + 3.0F * s, y + h + 6.0F * s, scaleAlpha(201326592, alpha), r + 3.0F * s);
   }

   static void divider(ImDrawList var0, float var1, float var2, float var3, float var4) {
      float var5 = Math.round(var1);
      float var6 = Math.round(var2 - var3 * 0.5F);
      float var7 = Math.round(var3);
      var0.addRectFilled(var5 - 2.0F, var6, var5 + 3.0F, var6 + var7, scaleAlpha(GuiPalette.accent(), var4 * 0.25F));
      var0.addRectFilled(var5 - 1.0F, var6, var5 + 2.0F, var6 + var7, scaleAlpha(GuiPalette.accent(), var4 * 0.45F));
      var0.addRectFilled(var5, var6, var5 + 1.0F, var6 + var7, scaleAlpha(GuiPalette.accent(), var4 * 0.9F));
   }

   static boolean needsTextGuard(Surface surface) {
      return surface != null && (surface.opacity.get() < 0.55F || surface.isImage());
   }

   static int px(float base, float scale) {
      return Math.max(6, Math.round(base * scale));
   }

   static float width(ImFont font, int size, String text) {
      return font != null && !text.isEmpty() ? font.calcTextSizeA(size, Float.MAX_VALUE, 0.0F, text).x : text.length() * size * 0.55F;
   }

   static float ascent(ImFont font, int size) {
      return font != null && !(font.getFontSize() <= 0.0F) ? font.getAscent() * size / font.getFontSize() : size * 0.77F;
   }

   static float baselineOffset(ImFont big, int bigSize, ImFont small, int smallSize) {
      return Math.round(ascent(big, bigSize) - ascent(small, smallSize));
   }

   static void text(ImDrawList var0, ImFont var1, int var2, float var3, float var4, int var5, String var6, boolean var7) {
      if (!var6.isEmpty() && (var5 >>> 24 & 0xFF) != 0) {
         float var8 = (var5 >>> 24 & 0xFF) / 255.0F;
         raw(var0, var1, var2, var3, var4, scaleAlpha(GuiPalette.accent(), var8 * 0.16F), var6);
         raw(var0, var1, var2, var3 + 1.0F, var4, scaleAlpha(GuiPalette.accent(), var8 * 0.08F), var6);
         raw(var0, var1, var2, var3 + -1.0F, var4, scaleAlpha(GuiPalette.accent(), var8 * 0.08F), var6);
         raw(var0, var1, var2, var3, var4 + 1.0F, scaleAlpha(GuiPalette.accent(), var8 * 0.08F), var6);
         raw(var0, var1, var2, var3, var4 + -1.0F, scaleAlpha(GuiPalette.accent(), var8 * 0.08F), var6);
         if (var7) {
            raw(var0, var1, var2, var3 + 1.0F, var4 + 1.0F, scaleAlpha(-1946157056, var8), var6);
         }

         raw(var0, var1, var2, var3, var4, var5, var6);
      }
   }

   private static void raw(ImDrawList dl, ImFont font, int size, float x, float y, int color, String text) {
      if (font != null) {
         dl.addText(font, size, x, y, color, text);
      } else {
         dl.addText(x, y, color, text);
      }
   }

   private static float digitCell(ImFont font, int size) {
      if (font == null) {
         return size * 0.6F;
      }

      long key = (long)System.identityHashCode(font) << 16 ^ size;
      float cached = DIGIT_CELLS.get(key);
      if (!Float.isNaN(cached)) {
         return cached;
      }

      float cell = 0.0F;

      for (char c = '0'; c <= '9'; c++) {
         cell = Math.max(cell, advance(font, size, c));
      }

      if (DIGIT_CELLS.size() > 64) {
         DIGIT_CELLS.clear();
      }

      DIGIT_CELLS.put(key, cell);
      return cell;
   }

   private static float advance(ImFont font, int size, char c) {
      return font.getCharAdvance(c) * size / font.getFontSize();
   }

   static float tabularWidth(ImFont font, int size, String text) {
      if (font == null) {
         return width(null, size, text);
      }

      float cell = digitCell(font, size);
      float w = 0.0F;

      for (int i = 0; i < text.length(); i++) {
         char c = text.charAt(i);
         w += Character.isDigit(c) ? cell : advance(font, size, c);
      }

      return w;
   }

   static void tabular(ImDrawList dl, ImFont font, int size, float x, float y, int color, String text, boolean guard) {
      if (font == null) {
         text(dl, null, size, x, y, color, text, guard);
      } else {
         float cell = digitCell(font, size);
         float cx = x;

         for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            float adv = advance(font, size, c);
            if (Character.isDigit(c)) {
               text(dl, font, size, cx + (cell - adv) * 0.5F, y, color, glyph(c), guard);
               cx += cell;
            } else {
               if (c != ' ') {
                  text(dl, font, size, cx, y, color, glyph(c), guard);
               }

               cx += adv;
            }
         }
      }
   }

   private static String glyph(char c) {
      return c < ASCII.length ? ASCII[c] : String.valueOf(c);
   }

   static int scaleAlpha(int color, float alphaScale) {
      int alpha = color >>> 24 & 0xFF;
      int scaled = Math.round(alpha * Math.max(0.0F, Math.min(1.0F, alphaScale)));
      return color & 16777215 | scaled << 24;
   }

   static {
      DIGIT_CELLS.defaultReturnValue(Float.NaN);

      for (char c = 0; c < ASCII.length; c++) {
         ASCII[c] = String.valueOf(c);
      }
   }

   static void bloomBox(ImDrawList var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      var6 *= pulse();
      float var8 = Math.max(1.0F, var7);
      float var9 = 9.0F * var8;
      var0.addRectFilled(var1 - var9, var2 - var9, var1 + var3 + var9, var2 + var4 + var9, scaleAlpha(GuiPalette.accent(), var6 * 0.05F), var5 + var9 * 0.45F);
      var9 = 6.0F * var8;
      var0.addRectFilled(var1 - var9, var2 - var9, var1 + var3 + var9, var2 + var4 + var9, scaleAlpha(GuiPalette.accent(), var6 * 0.09F), var5 + var9 * 0.45F);
      var9 = 3.5F * var8;
      var0.addRectFilled(var1 - var9, var2 - var9, var1 + var3 + var9, var2 + var4 + var9, scaleAlpha(GuiPalette.accent(), var6 * 0.15F), var5 + var9 * 0.45F);
      var9 = 1.5F * var8;
      var0.addRectFilled(var1 - var9, var2 - var9, var1 + var3 + var9, var2 + var4 + var9, scaleAlpha(GuiPalette.accent(), var6 * 0.24F), var5 + var9 * 0.45F);
   }

   static void glowRail(ImDrawList var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      var5 *= pulse();
      float var7 = Math.max(1.0F, var6);
      float var8 = 5.0F * var7;
      var0.addRectFilled(var1 - var8, var2, var3 + var8, var4, scaleAlpha(GuiPalette.accent(), var5 * 0.1F));
      var8 = 3.0F * var7;
      var0.addRectFilled(var1 - var8, var2, var3 + var8, var4, scaleAlpha(GuiPalette.accent(), var5 * 0.2F));
      var8 = 1.25F * var7;
      var0.addRectFilled(var1 - var8, var2, var3 + var8, var4, scaleAlpha(GuiPalette.accent(), var5 * 0.38F));
      var0.addRectFilled(var1, var2, var3, var4, scaleAlpha(GuiPalette.accent(), var5 * 0.92F));
      var0.addRectFilled(var1 + 1.0F * var7, var2, var3 - 1.0F * var7, var4, scaleAlpha(1728053247, var5 * 0.4F));
   }

   static void washEdge(ImDrawList var0, float var1, float var2, float var3, float var4, float var5, boolean var6) {
      float var7 = Math.max(Math.min((var3 - var1) * 0.55F, 36.0F), 10.0F);
      ImDrawList var10000;
      float var10001;
      float var10002;
      float var10003;
      float var10004;
      if (var6) {
         var10000 = var0;
         var10001 = var3 - var7 * 1.0F;
         var10002 = var2;
         var10003 = var3;
         var10004 = var4;
      } else {
         var10000 = var0;
         var10001 = var1;
         var10002 = var2;
         var10003 = var1 + var7 * 1.0F;
         var10004 = var4;
      }

      var10000.addRectFilled(var10001, var10002, var10003, var10004, scaleAlpha(GuiPalette.accent(), var5 * 0.14F));
      if (var6) {
         var10000 = var0;
         var10001 = var3 - var7 * 0.6F;
         var10002 = var2;
         var10003 = var3;
         var10004 = var4;
      } else {
         var10000 = var0;
         var10001 = var1;
         var10002 = var2;
         var10003 = var1 + var7 * 0.6F;
         var10004 = var4;
      }

      var10000.addRectFilled(var10001, var10002, var10003, var10004, scaleAlpha(GuiPalette.accent(), var5 * 0.08F));
      if (var6) {
         var10000 = var0;
         var10001 = var3 - var7 * 0.3F;
         var10002 = var2;
         var10003 = var3;
         var10004 = var4;
      } else {
         var10000 = var0;
         var10001 = var1;
         var10002 = var2;
         var10003 = var1 + var7 * 0.3F;
         var10004 = var4;
      }

      var10000.addRectFilled(var10001, var10002, var10003, var10004, scaleAlpha(GuiPalette.accent(), var5 * 0.03F));
   }

   static float pulse() {
      return (float)Math.sin(System.currentTimeMillis() / 780.0) * 0.15F + 0.85F;
   }
}
