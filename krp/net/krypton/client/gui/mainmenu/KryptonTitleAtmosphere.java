package net.krypton.client.gui.mainmenu;

import imgui.ImDrawList;
import net.krypton.client.gui.EgyptDraw;
import net.krypton.client.gui.GuiPalette;

public final class KryptonTitleAtmosphere {
   private static final int ACCENT = -14494465;
   private static final int ACCENT_SOFT = -15030068;
   private static final int CORE = -1;
   private static final int DEEP = -16710904;
   private static final int CHARCOAL = -16513266;
   private static final int NAVY = -16247775;
   private static final int MID = -16050134;
   private static final int PARTICLE_COUNT = 92;
   private static final int EDGE_ORB_COUNT = 5;
   private static final float[] PX = new float[92];
   private static final float[] PY = new float[92];
   private static final float[] PZ = new float[92];
   private static final float[] PS = new float[92];
   private static final float[] PA = new float[92];
   private static final float[] PT = new float[92];
   private static final int[] PC = new int[92];
   private static final float[] OX = new float[5];
   private static final float[] OY = new float[5];
   private static final float[] OR = new float[5];
   private static final float[] OS = new float[5];
   private static final float[] OA = new float[5];
   private static boolean seeded;
   private static float seedW;
   private static float seedH;

   private KryptonTitleAtmosphere() {
   }

   public static void render(
      ImDrawList var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10
   ) {
      if (var0 != null && !(var1 <= 1.0F) && !(var2 <= 1.0F)) {
         float var11 = var3 > 0.05F ? var3 : 1.0F;
         seed(var1, var2);
         float var12 = var9 * 22.0F * var11;
         float var13 = var10 * 12.0F * var11;
         float var14 = var4 > 0.0F ? var4 : (float)(System.currentTimeMillis() % 600000L) / 1000.0F;
         baseGradient(var0, var1, var2);
         drawPhotoCover(var0, var1, var2);
         horizonEnergy(var0, var1, var2, var11, var14, var12, var13, var5);
         particles(var0, var1, var2, var11, var14, var12, var13);
         vignette(var0, var1, var2);
      }
   }

   private static void drawPhotoCover(ImDrawList var0, float var1, float var2) {
   }

   private static void baseGradient(ImDrawList var0, float var1, float var2) {
      var0.addRectFilled(0.0F, 0.0F, var1, var2, GuiPalette.abgr(-16314081));
      var0.addRectFilledMultiColor(
         0.0F, 0.0F, var1, var2, GuiPalette.abgr(-16314081), GuiPalette.abgr(-16314081), GuiPalette.abgr(-15453601), GuiPalette.abgr(-15453601)
      );

      for (int var3 = 0; var3 < 84; var3++) {
         float var4 = ((float)Math.sin(var3 * 12.9898F) * 0.5F + 0.5F) * var1;
         float var5 = ((float)Math.sin(var3 * 78.233F + 1.7F) * 0.5F + 0.5F) * var2;
         float var6 = var3 % 6 * 0.28F + 0.85F;
         var0.addCircleFilled(var4, var5, var6, GuiPalette.abgr(-788481), 8);
      }
   }

   private static void volumetricBlooms(
      ImDrawList var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9
   ) {
      float var10 = (float)(Math.sin(var4 * 0.38) * 0.5 + 0.5);
      float var11 = (float)(Math.sin(var4 * 0.27 + 1.7) * 0.5 + 0.5);
      float var12 = var7 > 1.0F ? var7 + var5 * 0.28F : var1 * 0.16F + var5 * 0.4F;
      float var13 = var8 > 1.0F ? var8 + var6 * 0.22F : var2 * 0.15F + var6 * 0.28F;
      if (Math.abs(var12 - var1 * 0.5F) < var1 * 0.22F) {
         var12 = var1 * 0.15F;
      }

      float var14 = (var9 > 1.0F ? var9 * 0.58F : 86.0F * var3) * (0.9F + 0.1F * (float)Math.sin(var4 * 0.48));
      softOrb(var0, var12, var13, var14, 0.07F + 0.022F * var10, true);
      float var15 = var1 * 0.5F + var5 * 0.1F;
      float var16 = var2 * 0.16F + var6 * 0.09F;
      softOrb(var0, var15, var16, 118.0F * var3 * (0.93F + 0.07F * var10), 0.048F + 0.012F * var11, true);
      softOrb(var0, var15, var16 + 8.0F * var3, 58.0F * var3, 0.028F, false);
      softOrb(var0, var1 * 0.06F + var5 * 0.45F, var2 * 0.58F + var6 * 0.18F, 165.0F * var3, 0.028F + 0.012F * var10, false);
      softOrb(var0, var1 * 0.94F - var5 * 0.4F, var2 * 0.5F + var6 * 0.14F, 148.0F * var3, 0.026F + 0.01F * (1.0F - var10), false);
      softOrb(var0, var1 * 0.82F + var5 * 0.22F, var2 * 0.18F + var6 * 0.2F, 100.0F * var3, 0.024F + 0.008F * var11, false);
      softOrb(var0, var1 * 0.12F + var5 * 0.18F, var2 * 0.32F + var6 * 0.12F, 78.0F * var3, 0.018F + 0.006F * var10, false);
   }

   private static void softOrb(ImDrawList var0, float var1, float var2, float var3, float var4, boolean var5) {
      byte var6 = 9;

      for (int var7 = 0; var7 < var6; var7++) {
         float var8 = 1.0F - (float)var7 / (var6 - 1);
         float var9 = var3 * (0.18F + var8 * 0.82F);
         float var10 = var4 * var8 * var8 * var8;
         int var11 = var7 < 3 ? -14494465 : -15030068;
         var0.addCircleFilled(var1, var2, var9, EgyptDraw.col(var11, var10), 56);
      }

      if (var5) {
         var0.addCircleFilled(var1, var2, var3 * 0.12F, EgyptDraw.col(-14494465, var4 * 0.55F), 24);
         var0.addCircleFilled(var1, var2, var3 * 0.045F, EgyptDraw.col(-1, var4 * 0.5F), 16);
      }
   }

   private static void edgeLightOrbs(ImDrawList var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = var1 * 0.5F - Math.min(220.0F * var3, var1 * 0.15F);
      float var8 = var1 * 0.5F + Math.min(220.0F * var3, var1 * 0.15F);
      float var9 = var2 * 0.34F;
      float var10 = var2 * 0.86F;

      for (int var11 = 0; var11 < 5; var11++) {
         float var12 = var4 * OS[var11] + var11 * 1.7F;
         float var13 = OX[var11] + (float)Math.sin(var12) * (38.0F + var11 * 10.0F) * var3 + var5 * (0.15F + var11 * 0.04F);
         float var14 = OY[var11] + (float)Math.cos(var12 * 0.72F) * (22.0F + var11 * 7.0F) * var3 + var6 * (0.1F + var11 * 0.03F);
         if (var13 > var7 && var13 < var8 && var14 > var9 && var14 < var10) {
            var13 = var13 < var1 * 0.5F ? var7 - 24.0F * var3 : var8 + 24.0F * var3;
         }

         float var15 = (float)(Math.sin(var12 * 1.4) * 0.5 + 0.5);
         float var16 = OR[var11] * var3 * (0.88F + 0.12F * var15);
         softOrb(var0, var13, var14, var16, OA[var11] * (0.75F + 0.25F * var15), var11 % 2 == 0);
      }
   }

   private static void horizonEnergy(ImDrawList var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var7 > 1.0F && var7 < var2 ? var7 + var6 * 0.07F : var2 * 0.715F + var6 * 0.07F;
      float var9 = (float)(Math.sin(var4 * 0.33) * 0.5 + 0.5);
      float var10 = Math.max(14.0F * var3, var2 * 0.022F);
      float var11 = Math.max(1.2F * var3, 1.5F);
      var0.addRectFilledMultiColor(
         0.0F,
         var8 - var10 * 2.6F,
         var1,
         var8,
         EgyptDraw.col(-14494465, 0.0F),
         EgyptDraw.col(-14494465, 0.0F),
         EgyptDraw.col(-14494465, 0.03F + 0.014F * var9),
         EgyptDraw.col(-14494465, 0.03F + 0.014F * var9)
      );
      var0.addRectFilledMultiColor(
         0.0F,
         var8,
         var1,
         var8 + var10 * 1.8F,
         EgyptDraw.col(-14494465, 0.036F + 0.016F * var9),
         EgyptDraw.col(-14494465, 0.036F + 0.016F * var9),
         EgyptDraw.col(-14494465, 0.0F),
         EgyptDraw.col(-14494465, 0.0F)
      );
      float var12 = var1 * 0.06F;
      var0.addRectFilled(var12, var8 - var11 * 0.5F, var1 - var12, var8 + var11 * 0.5F, EgyptDraw.col(-14494465, 0.22F + 0.1F * var9));
      var0.addRectFilled(var12, var8 - var11 * 2.2F, var1 - var12, var8 + var11 * 2.2F, EgyptDraw.col(-14494465, 0.06F + 0.03F * var9));
      float var13 = 9.5F;
      float var14 = var4 % var13 / var13;
      float var15 = var12 + (var1 - var12 * 2.0F) * var14 + var5 * 0.05F;
      float var16 = 10.0F * var3 * (0.85F + 0.15F * var9);

      for (int var17 = 0; var17 < 6; var17++) {
         float var18 = 1.0F - var17 / 5.0F;
         var0.addCircleFilled(var15, var8, var16 * (0.35F + var18 * 1.4F), EgyptDraw.col(-14494465, 0.055F * var18 * var18), 28);
      }

      var0.addCircleFilled(var15, var8, var16 * 0.28F, EgyptDraw.col(-1, 0.55F + 0.2F * var9), 12);
      float var19 = var12 + (var1 - var12 * 2.0F) * ((var14 + 0.42F) % 1.0F);
      var0.addCircleFilled(var19, var8, var16 * 0.55F, EgyptDraw.col(-14494465, 0.04F), 16);
      var0.addCircleFilled(var19, var8, var16 * 0.16F, EgyptDraw.col(-1, 0.28F), 8);
   }

   private static void seed(float var0, float var1) {
      if (!seeded || !(Math.abs(seedW - var0) < 2.0F) || !(Math.abs(seedH - var1) < 2.0F)) {
         seeded = true;
         seedW = var0;
         seedH = var1;

         for (int var2 = 0; var2 < 92; var2++) {
            PX[var2] = hash(var2, 1) * var0;
            PY[var2] = hash(var2, 2) * var1;
            PZ[var2] = 0.22F + hash(var2, 3) * 1.7F;
            float var3 = hash(var2, 4);
            PS[var2] = var3 < 0.55F ? 0.55F + var3 * 1.4F : 1.4F + var3 * 2.8F;
            PA[var2] = 0.18F + hash(var2, 5) * 0.5F;
            PT[var2] = hash(var2, 6) * 6.28F;
            PC[var2] = hash(var2, 7) > 0.38F ? 0 : 1;
         }

         float[][] var4 = new float[][]{{0.1F, 0.22F}, {0.88F, 0.2F}, {0.07F, 0.68F}, {0.93F, 0.62F}, {0.18F, 0.88F}};

         for (int var5 = 0; var5 < 5; var5++) {
            OX[var5] = var4[var5][0] * var0;
            OY[var5] = var4[var5][1] * var1;
            OR[var5] = 36.0F + hash(var5, 11) * 42.0F;
            OS[var5] = 0.12F + hash(var5, 12) * 0.18F;
            OA[var5] = 0.016F + hash(var5, 13) * 0.018F;
         }
      }
   }

   private static float hash(int var0, int var1) {
      int var2 = var0 * 374761393 + var1 * 668265263;
      var2 = (var2 ^ var2 >>> 13) * 1274126177;
      var2 ^= var2 >>> 16;
      return (var2 & 2147483647) / 2.1474836E9F;
   }

   private static void particles(ImDrawList var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = var1 * 0.5F - Math.min(215.0F * var3, var1 * 0.148F);
      float var8 = var1 * 0.5F + Math.min(215.0F * var3, var1 * 0.148F);
      float var9 = var2 * 0.34F;
      float var10 = var2 * 0.86F;

      for (int var11 = 0; var11 < 92; var11++) {
         float var12 = PZ[var11];
         float var13 = (4.5F + var12 * 17.0F) * var3;
         float var14 = (3.5F + var12 * 13.0F) * var3;
         float var15 = (float)Math.sin(var4 * (0.55 + var12 * 0.2) + PT[var11]) * 6.0F * var3;
         float var16 = (PX[var11] + var4 * var13 + var5 * 0.14F + var15) % var1;
         if (var16 < 0.0F) {
            var16 += var1;
         }

         float var17 = (PY[var11] - var4 * var14 + var6 * 0.11F) % var2;
         if (var17 < 0.0F) {
            var17 += var2;
         }

         if (!(var16 > var7) || !(var16 < var8) || !(var17 > var9) || !(var17 < var10)) {
            float var18 = (float)(Math.sin(var4 * (1.25 + var12 * 0.85) + PT[var11]) * 0.38 + 0.62);
            float var19 = (float)Math.max(0.0, Math.sin(var4 * (0.7 + var12 * 0.3) + PT[var11] * 2.1) - 0.82) * 4.5F;
            float var20 = PS[var11] * var3 * (0.55F + var18 * 0.45F + var19 * 0.25F);
            float var21 = PA[var11] * var18 * (0.52F + var19 * 0.35F);
            boolean var22 = PC[var11] == 1;

            for (int var23 = 1; var23 <= 3; var23++) {
               float var24 = 1.0F - var23 / 3.5F;
               float var25 = var16 - var13 * (0.018F * var23);
               float var26 = var17 + var14 * (0.018F * var23);
               var0.addCircleFilled(var25, var26, var20 * (1.2F + var23 * 0.35F), EgyptDraw.col(-14494465, var21 * 0.07F * var24), 8);
            }

            var0.addCircleFilled(var16, var17, var20 * 2.8F, EgyptDraw.col(-14494465, var21 * 0.12F), 12);
            var0.addCircleFilled(var16, var17, var20 * 1.35F, EgyptDraw.col(-14494465, var21 * 0.28F), 10);
            if (var22) {
               var0.addCircleFilled(var16, var17, var20 * 0.55F, EgyptDraw.col(-1, var21 * 0.85F), 8);
            } else {
               var0.addCircleFilled(var16, var17, var20 * 0.58F, EgyptDraw.col(-14494465, var21 * 0.8F), 8);
               var0.addCircleFilled(var16, var17, var20 * 0.22F, EgyptDraw.col(-1, var21 * 0.45F), 6);
            }
         }
      }
   }

   private static void vignette(ImDrawList var0, float var1, float var2) {
      float var3 = Math.min(var1, var2) * 0.26F;
      int var4 = EgyptDraw.col(-16777216, 0.0F);
      int var5 = EgyptDraw.col(-16777216, 0.62F);
      int var6 = EgyptDraw.col(-16777216, 0.28F);
      var0.addRectFilledMultiColor(0.0F, 0.0F, var3, var2, var5, var4, var4, var5);
      var0.addRectFilledMultiColor(var1 - var3, 0.0F, var1, var2, var4, var5, var5, var4);
      var0.addRectFilledMultiColor(0.0F, 0.0F, var1, var3 * 0.9F, var5, var5, var4, var4);
      var0.addRectFilledMultiColor(0.0F, var2 - var3 * 1.05F, var1, var2, var4, var4, var5, var5);
      float var7 = var3 * 0.45F;
      var0.addRectFilledMultiColor(0.0F, 0.0F, var7, var2, var6, var4, var4, var6);
      var0.addRectFilledMultiColor(var1 - var7, 0.0F, var1, var2, var4, var6, var6, var4);
   }
}
