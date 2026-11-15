package net.krypton.client.gui.mainmenu;

import imgui.ImDrawList;

public final class KryptonMenuChrome {
   private static final int ACCENT = -14494465;
   private static final int FILL = -16117736;
   private static final int FILL_HOVER = -15853020;

   private KryptonMenuChrome() {
   }

   public static void frame(MenuFrame var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (var0 != null && !(var7 <= 0.02F) && !(var3 <= 1.0F) && !(var4 <= 1.0F)) {
         ImDrawList var8 = var0.bg();
         if (var8 != null) {
            float var9 = var0.scale();
            if (var9 < 0.05F) {
               var9 = 1.0F;
            }

            float var10 = Math.min(1.0F, Math.max(0.0F, var7));
            float var11 = Math.min(1.0F, Math.max(0.0F, var5));
            float var12 = 0.92F + 0.08F * (float)Math.sin(var0.time() * 2.1 + var6 * 6.0);
            float var13 = var10 * var12 * (0.55F + 0.45F * var11);
            float var14 = Math.min(var4 * 0.38F, Math.min(14.0F * var9, var3 * 0.08F));
            if (var11 > 0.02F || var13 > 0.2F) {
               float[] var15 = new float[]{10.0F, 5.0F, 2.2F};
               float[] var16 = new float[]{0.06F, 0.13F, 0.26F};

               for (int var17 = 0; var17 < var15.length; var17++) {
                  float var18 = var15[var17] * var9 * (0.6F + 0.6F * var11);
                  fillChamfer(
                     var8,
                     var1 - var18,
                     var2 - var18 * 0.55F,
                     var3 + var18 * 2.0F,
                     var4 + var18 * 1.1F,
                     var14 + var18 * 0.4F,
                     MenuFrame.col(-14494465, var13 * var16[var17] * (0.5F + 0.5F * var11))
                  );
               }
            }

            fillChamfer(var8, var1, var2, var3, var4, var14, MenuFrame.col(var11 > 0.35F ? -15853020 : -16117736, var10 * (0.78F + 0.18F * var11)));
            strokeChamfer(var8, var1, var2, var3, var4, var14, MenuFrame.col(-14494465, var13 * 0.95F), (1.6F + 0.8F * var11) * var9);
            float var20 = 7.0F * var9;
            int var21 = MenuFrame.col(-14494465, var13 * (0.55F + 0.45F * var11));
            var8.addLine(var1 + var14, var2, var1 + var14 + var20, var2, var21, 1.5F * var9);
            var8.addLine(var1 + var3 - var14 - var20, var2, var1 + var3 - var14, var2, var21, 1.5F * var9);
            var8.addLine(var1 + var14, var2 + var4, var1 + var14 + var20, var2 + var4, var21, 1.5F * var9);
            var8.addLine(var1 + var3 - var14 - var20, var2 + var4, var1 + var3 - var14, var2 + var4, var21, 1.5F * var9);
            if (var11 > 0.05F) {
               float var22 = (float)(Math.sin(var0.time() * 3.2 + var1 * 0.01) * 0.5 + 0.5);
               float var23 = var1 + var3 * (0.12F + 0.76F * var22);
               float var19 = 18.0F * var9;
               var8.addRectFilled(
                  var23 - var19 * 0.5F,
                  var2 + 3.0F * var9,
                  var23 + var19 * 0.5F,
                  var2 + var4 - 3.0F * var9,
                  MenuFrame.col(-14494465, var10 * var11 * 0.12F),
                  4.0F * var9
               );
            }
         }
      }
   }

   private static void fillChamfer(ImDrawList var0, float var1, float var2, float var3, float var4, float var5, int var6) {
      var0.pathClear();
      var0.pathLineTo(var1 + var5, var2);
      var0.pathLineTo(var1 + var3 - var5, var2);
      var0.pathLineTo(var1 + var3, var2 + var5);
      var0.pathLineTo(var1 + var3, var2 + var4 - var5);
      var0.pathLineTo(var1 + var3 - var5, var2 + var4);
      var0.pathLineTo(var1 + var5, var2 + var4);
      var0.pathLineTo(var1, var2 + var4 - var5);
      var0.pathLineTo(var1, var2 + var5);
      var0.pathFillConvex(var6);
   }

   private static void strokeChamfer(ImDrawList var0, float var1, float var2, float var3, float var4, float var5, int var6, float var7) {
      var0.pathClear();
      var0.pathLineTo(var1 + var5, var2);
      var0.pathLineTo(var1 + var3 - var5, var2);
      var0.pathLineTo(var1 + var3, var2 + var5);
      var0.pathLineTo(var1 + var3, var2 + var4 - var5);
      var0.pathLineTo(var1 + var3 - var5, var2 + var4);
      var0.pathLineTo(var1 + var5, var2 + var4);
      var0.pathLineTo(var1, var2 + var4 - var5);
      var0.pathLineTo(var1, var2 + var5);
      var0.pathLineTo(var1 + var5, var2);
      var0.pathStroke(var6, 0, var7);
   }
}
