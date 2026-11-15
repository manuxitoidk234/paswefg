package net.krypton.client.gui.mainmenu;

import imgui.ImDrawList;
import imgui.ImFont;
import java.util.List;
import java.util.Optional;
import net.krypton.client.gui.EgyptDraw;
import net.krypton.client.gui.ThemeImages;

public final class KryptonMainMenu {
   private static final int ACCENT = -14494465;
   private static final int CORE = -1;
   private static final String KR_LOGO = "/assets/krypton/textures/gui/kr_logo.png";
   private static final String KR_HEADER = "/assets/krypton/textures/gui/kr_header.png";
   private static final String KR_GLYPH = "/assets/krypton/textures/misc/kr_glyph.png";
   private static final String VERSION = "Krypton Client v0.9.9 | NEW GEN";
   private static final float[] FLASH = new float[8];
   private static final float[] SCALE = new float[8];

   private KryptonMainMenu() {
   }

   private static float contentColumn(float var0, float var1, float var2) {
      float var3 = 980.0F * var2;
      float var4 = var1 * 1.15F;
      float var5 = Math.min(var3, var4);
      var5 = Math.min(var5, var0 * 0.55F);
      return Math.max(320.0F * var2, var5);
   }

   public static void render(MenuContext var0) {
      if (var0 != null) {
         ImDrawList var1 = var0.bg();
         if (var1 != null) {
            float var2 = var0.width();
            float var3 = var0.height();
            float var4 = var0.scale();
            if (var4 < 0.05F) {
               var4 = 1.0F;
            }

            float var5 = var0.time();
            float var6 = var0.lookX();
            float var7 = var0.lookY();
            float var8 = var0.intro(0.15F);
            if (!(var8 <= 0.02F)) {
               float var9 = contentColumn(var2, var3, var4);
               float var10 = (var2 - var9) * 0.5F;
               float var11 = DuskScene.horizon(var3);
               float var12 = var2 * 0.18F + var6 * 18.0F * var4;
               float var13 = var3 * 0.16F + var7 * 10.0F * var4;
               float var14 = 150.0F * var4;
               DuskScene.draw(var1, var2, var3, var4, var5, var11, var12, var13, var14, var6, var7);
               drawCenterBrand(var0, var1, var2, var3, var4, var5, var6, var7, var8, var10, var9);
               drawButtons(var0, var1, var2, var3, var4, var5, var8, var10, var9);
               KryptonDiscordButton.render(var0);
               drawVersion(var0, var1, var2, var3, var4, var8);
               float var15 = Math.min(240.0F * var4, Math.max(200.0F * var4, Math.min(260.0F * var4, var2 * 0.14F)));
               float var16 = Math.min(420.0F * var4, var3 * 0.66F);
               float var17 = var2 - var15 - 36.0F * var4;
               float var18 = var3 * 0.17F;
               KryptonPlayerShowcase.render(var0, var17, var18, var15, var16, var8 * var0.intro(0.45F));
            }
         }
      }
   }

   private static ThemeImages.Texture resolveKrMark() {
      ThemeImages.Texture var0 = EgyptDraw.texture("/assets/krypton/textures/gui/kr_logo.png");
      if (var0 != null && var0.id() != 0) {
         return var0;
      }

      var0 = EgyptDraw.texture("/assets/krypton/textures/gui/kr_header.png");
      if (var0 != null && var0.id() != 0) {
         return var0;
      }

      var0 = EgyptDraw.texture("/assets/krypton/textures/misc/kr_glyph.png");
      return var0 != null && var0.id() != 0 ? var0 : null;
   }

   private static void drawCenterBrand(
      MenuContext var0, ImDrawList var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10
   ) {
      float var11 = var9 + var10 * 0.5F + var6 * 6.0F * var4;
      float var12 = var3 * 0.168F + var7 * 5.0F * var4;
      float var13 = (float)(Math.sin(var5 * 1.35) * 0.05 + 0.95);
      ThemeImages.Texture var14 = resolveKrMark();
      float var15 = Math.min(112.0F * var4 * var13, var10 * 0.22F);
      if (var14 != null && var14.id() != 0) {
         for (int var16 = 0; var16 < 5; var16++) {
            float var17 = 1.0F - var16 / 4.0F;
            var1.addCircleFilled(var11, var12, var15 * (0.4F + var17 * 0.62F), EgyptDraw.col(-14494465, var8 * 0.036F * var17 * var17), 36);
         }

         float var36 = var11 - var15 * 0.5F;
         float var38 = var12 - var15 * 0.5F;
         var1.addImage(var14.id(), var36, var38, var36 + var15, var38 + var15, 0.0F, 0.0F, 1.0F, 1.0F, EgyptDraw.col(-1, var8));
      }

      float var37 = var15 * 0.78F;
      float var39 = var5 * 1.15F;
      float var18 = 0.55F;
      float var19 = (float)Math.cos(var18);
      float var20 = (float)Math.sin(var18);
      float var21 = 0.42F;
      byte var22 = 64;

      for (int var23 = 0; var23 < var22; var23++) {
         float var24 = var39 + (float)var23 / var22 * (float) (Math.PI * 2);
         float var25 = var39 + (float)(var23 + 1) / var22 * (float) (Math.PI * 2);
         float var26 = (float)Math.sin((float)var23 / var22 * Math.PI * 5.0 + var5 * 3.2);
         float var27 = var8 * (0.08F + 0.42F * Math.max(0.0F, var26));
         float var28 = (float)Math.cos(var24) * var37;
         float var29 = (float)Math.sin(var24) * var37 * var21;
         float var30 = (float)Math.cos(var25) * var37;
         float var31 = (float)Math.sin(var25) * var37 * var21;
         float var32 = var11 + var28 * var19 - var29 * var20;
         float var33 = var12 + var28 * var20 + var29 * var19;
         float var34 = var11 + var30 * var19 - var31 * var20;
         float var35 = var12 + var30 * var20 + var31 * var19;
         var1.addLine(var32, var33, var34, var35, EgyptDraw.col(-14494465, var27), (1.2F + 1.1F * Math.max(0.0F, var26)) * var4);
      }
   }

   private static void drawButtons(MenuContext var0, ImDrawList var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      List var9 = MenuAction.MAIN;
      if (var9 != null && !var9.isEmpty()) {
         float var10 = Math.min(320.0F * var4, Math.min(var8 * 0.58F, 340.0F * var4));
         var10 = Math.max(280.0F * var4, var10);
         float var11 = Math.max(46.0F * var4, Math.min(50.0F * var4, 48.0F * var4));
         float var12 = 10.0F * var4;
         int var13 = Math.min(var9.size(), 5);
         float var14 = var13 * var11 + (var13 - 1) * var12 + 12.0F * var4;
         float var15 = var3 * 0.455F;
         float var16 = var3 - 100.0F * var4 - var14;
         if (var15 > var16) {
            var15 = Math.max(var3 * 0.4F, var16);
         }

         float var17 = var7 + var8 * 0.5F;
         float var18 = var17 - var10 * 0.5F;

         for (int var19 = 0; var19 < var13; var19++) {
            MenuAction var20 = (MenuAction)var9.get(var19);
            float var21 = var15 + var19 * (var11 + var12);
            if (var20 == MenuAction.QUIT) {
               var21 += 6.0F * var4;
            }

            float var22 = var0.intro(0.35F + var19 * 0.05F);
            boolean var24 = KryptonPlayerShowcase.capturesPointer(var0.mouseX(), var0.mouseY());
            float var23;
            if (var22 > 0.45F && !var24) {
               var23 = var0.control(var20, var18, var21, var10, var11);
            } else {
               var23 = var0.hover(var20, false);
            }

            float var25 = 1.0F + var23 * 0.03F;
            float var26 = SCALE[var19];
            if (var26 < 0.5F) {
               var26 = 1.0F;
            }

            var26 += (var25 - var26) * Math.min(1.0F, var0.dt() * 14.0F);
            SCALE[var19] = var26;
            if (!var24 && var23 > 0.85F && var0.clicked() && var0.inside(var18, var21, var10, var11)) {
               FLASH[var19] = 1.0F;
            }

            FLASH[var19] = Math.max(0.0F, FLASH[var19] - var0.dt() * 3.5F);
            float var27 = (float)var19 / Math.max(1, var13 - 1);
            KryptonMenuChrome.frame(var0, var18, var21, var10, var11, var23, var27, var22 * var6);
            if (FLASH[var19] > 0.01F) {
               var1.addRectFilled(var18, var21, var18 + var10, var21 + var11, EgyptDraw.col(-1, FLASH[var19] * 0.22F * var6), 10.0F * var4);
            }

            drawButtonIcon(var1, var20, var18 + 22.0F * var4, var21 + var11 * 0.5F, 11.0F * var4, var23, var22 * var6);
            drawButtonLabel(var0, var1, var20, var18, var21, var10, var11, var23, var22 * var6);
         }
      }
   }

   private static void drawButtonLabel(
      MenuContext var0, ImDrawList var1, MenuAction var2, float var3, float var4, float var5, float var6, float var7, float var8
   ) {
      ImFont var9 = var0.itemFont();
      if (var9 == null) {
         var9 = var0.menuFont();
      }

      String var10 = var2.label();
      float var11 = 2.2F * var0.scale();
      float var12 = var0.spacedWidth(var9, var10, var11);
      float var13 = var0.textHeight(var9);
      float var14 = var3 + (var5 - var12) * 0.5F + 8.0F * var0.scale();
      float var15 = var4 + (var6 - var13) * 0.5F;
      int var16 = EgyptDraw.mix(-4668208, -1, var7);
      if (var2 == MenuAction.QUIT) {
         var16 = EgyptDraw.mix(-6642512, -1, var7);
      }

      var0.spacedText(var1, var9, var14, var15 + 1.2F * var0.scale(), var11, EgyptDraw.col(-16777216, var8 * 0.45F), var8, var10);
      var0.spacedText(var1, var9, var14, var15, var11, EgyptDraw.col(var16, var8), var8, var10);
   }

   private static void drawButtonIcon(ImDrawList var0, MenuAction var1, float var2, float var3, float var4, float var5, float var6) {
      int var7 = EgyptDraw.col(EgyptDraw.mix(-14494465, -1, var5 * 0.35F), var6 * (0.75F + 0.25F * var5));
      float var8 = 1.6F;
      String var9 = var1 == null ? "" : var1.name();
      if ("SINGLEPLAYER".equals(var9) || var1 == MenuAction.SINGLEPLAYER) {
         var0.addTriangleFilled(var2 - var4 * 0.45F, var3 - var4 * 0.7F, var2 - var4 * 0.45F, var3 + var4 * 0.7F, var2 + var4 * 0.75F, var3, var7);
      } else if ("MULTIPLAYER".equals(var9) || var1 == MenuAction.MULTIPLAYER) {
         var0.addCircleFilled(var2 - var4 * 0.45F, var3 - var4 * 0.25F, var4 * 0.32F, var7, 12);
         var0.addCircleFilled(var2 + var4 * 0.45F, var3 - var4 * 0.25F, var4 * 0.32F, var7, 12);
         var0.addCircleFilled(var2 - var4 * 0.45F, var3 + var4 * 0.45F, var4 * 0.42F, var7, 12);
         var0.addCircleFilled(var2 + var4 * 0.45F, var3 + var4 * 0.45F, var4 * 0.42F, var7, 12);
      } else if ("REALMS".equals(var9) || var1 == MenuAction.REALMS) {
         var0.addTriangleFilled(var2 - var4, var3 + var4 * 0.35F, var2 - var4 * 0.35F, var3 - var4 * 0.55F, var2, var3 + var4 * 0.15F, var7);
         var0.addTriangleFilled(var2 - var4 * 0.4F, var3 + var4 * 0.35F, var2, var3 - var4 * 0.85F, var2 + var4 * 0.4F, var3 + var4 * 0.35F, var7);
         var0.addTriangleFilled(var2, var3 + var4 * 0.15F, var2 + var4 * 0.35F, var3 - var4 * 0.55F, var2 + var4, var3 + var4 * 0.35F, var7);
         var0.addRectFilled(var2 - var4, var3 + var4 * 0.3F, var2 + var4, var3 + var4 * 0.7F, var7, 1.0F);
      } else if ("OPTIONS".equals(var9) || var1 == MenuAction.OPTIONS) {
         var0.addCircle(var2, var3, var4 * 0.55F, var7, 16, var8);

         for (int var10 = 0; var10 < 6; var10++) {
            double var11 = var10 / 6.0 * Math.PI * 2.0;
            float var13 = var2 + (float)Math.cos(var11) * var4 * 0.35F;
            float var14 = var3 + (float)Math.sin(var11) * var4 * 0.35F;
            float var15 = var2 + (float)Math.cos(var11) * var4 * 0.95F;
            float var16 = var3 + (float)Math.sin(var11) * var4 * 0.95F;
            var0.addLine(var13, var14, var15, var16, var7, var8 * 1.3F);
         }

         var0.addCircleFilled(var2, var3, var4 * 0.22F, var7, 10);
      } else if ("QUIT".equals(var9) || var1 == MenuAction.QUIT) {
         var0.addRect(var2 - var4 * 0.7F, var3 - var4 * 0.55F, var2 + var4 * 0.25F, var3 + var4 * 0.55F, var7, 2.0F, 0, var8);
         var0.addLine(var2 - var4 * 0.1F, var3, var2 + var4 * 0.85F, var3, var7, var8 * 1.4F);
         var0.addLine(var2 + var4 * 0.4F, var3 - var4 * 0.4F, var2 + var4 * 0.85F, var3, var7, var8 * 1.2F);
         var0.addLine(var2 + var4 * 0.4F, var3 + var4 * 0.4F, var2 + var4 * 0.85F, var3, var7, var8 * 1.2F);
      }
   }

   private static void drawVersion(MenuContext var0, ImDrawList var1, float var2, float var3, float var4, float var5) {
      ImFont var6 = var0.tagFont();
      if (var6 == null) {
         var6 = var0.menuFont();
      }

      String var7 = "Krypton Client v0.9.9 | NEW GEN";

      try {
         Class var8 = Class.forName("net.fabricmc.loader.api.FabricLoader");
         Object var9 = var8.getMethod("getInstance").invoke(null);
         if (var9.getClass().getMethod("getModContainer", String.class).invoke(var9, "kryptonplus") instanceof Optional var11 && var11.isPresent()) {
            Object var12 = var11.get();
            Object var13 = var12.getClass().getMethod("getMetadata").invoke(var12);
            Object var14 = var13.getClass().getMethod("getVersion").invoke(var13);
            if (var14 != null) {
               String var15 = var14.toString();
               int var16 = var15.indexOf(43);
               if (var16 > 0) {
                  var15 = var15.substring(0, var16);
               }

               if (var15.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                  String[] var17 = var15.split("\\.");
                  var15 = var17[0] + "." + var17[1] + "." + var17[2];
               }

               var7 = "Krypton Client v" + var15 + " | NEW GEN";
            }
         }
      } catch (Throwable var18) {
      }

      float var19 = var0.textWidth(var6, var7);
      var0.text(var1, var6, var2 - var19 - 28.0F * var4, var3 - 28.0F * var4, EgyptDraw.col(-1, var5 * 0.72F), var4, var7);
   }
}
