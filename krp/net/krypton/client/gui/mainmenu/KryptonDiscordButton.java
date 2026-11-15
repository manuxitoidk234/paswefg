package net.krypton.client.gui.mainmenu;

import imgui.ImDrawList;
import imgui.ImFont;
import java.awt.Desktop;
import java.net.URI;
import net.krypton.client.gui.EgyptDraw;
import net.krypton.client.gui.ThemeImages;

public final class KryptonDiscordButton {
   public static final String URL = "https://discord.gg/vWnaE9hMD";
   public static final String SHORT = "discord.gg/vWnaE9hMD";
   private static final int BLURPLE = -10983950;
   private static final int GLASS = -15723492;
   private static final int GLASS_HOVER = -15065040;
   private static final int URL_RGB = 12962550;
   private static final String ICON = "/assets/krypton/textures/gui/discord_mark.png";

   private KryptonDiscordButton() {
   }

   public static void render(MenuContext var0) {
      float var1 = var0.scale();
      float var2 = var0.intro(0.75F);
      if (!(var2 <= 0.05F)) {
         float var3 = Math.min(300.0F * var1, Math.max(260.0F * var1, var0.width() * 0.18F));
         float var4 = 48.0F * var1;
         float var5 = 28.0F * var1;
         float var6 = var5;
         float var7 = var0.height() - var5 - var4;
         boolean var8 = var0.inside(var6, var7, var3, var4);
         if (var8 && var0.clicked() && !KryptonPlayerShowcase.capturesPointer(var0.mouseX(), var0.mouseY())) {
            open();
         }

         if (var8) {
            var0.tooltip("discord.gg/vWnaE9hMD", var0.mouseX(), var0.mouseY());
         }

         ImDrawList var9 = var0.bg();
         float var10 = 14.0F * var1;
         int var11 = MenuFrame.col(var8 ? -15065040 : -15723492, var2 * (var8 ? 0.94F : 0.86F));
         var9.addRectFilled(var6, var7, var6 + var3, var7 + var4, var11, var10);
         var9.addRect(var6, var7, var6 + var3, var7 + var4, MenuFrame.col(-10983950, var2 * (var8 ? 0.92F : 0.48F)), var10, 0, Math.max(1.0F, 1.15F * var1));
         float var12 = 32.0F * var1;
         float var13 = var6 + 8.0F * var1;
         float var14 = var7 + (var4 - var12) * 0.5F;
         ThemeImages.Texture var15 = EgyptDraw.texture("/assets/krypton/textures/gui/discord_mark.png");
         if (var15 != null && var15.id() != 0) {
            var9.addImage(var15.id(), var13, var14, var13 + var12, var14 + var12, 0.0F, 0.0F, 1.0F, 1.0F, MenuFrame.col(-1, var2));
         }

         ImFont var16 = var0.menuFont();
         ImFont var17 = var0.tagFont();
         if (var17 == null) {
            var17 = var16;
         }

         float var18 = var13 + var12 + 10.0F * var1;
         float var19 = var0.textHeight(var16);
         float var20 = var0.textHeight(var17);
         float var21 = var19 + 2.0F * var1 + var20;
         float var22 = var7 + (var4 - var21) * 0.5F;
         var0.text(var9, var16, var18, var22, MenuFrame.col(-1, var2), var1, "Join our Discord");
         var0.text(var9, var17, var18, var22 + var19 + 2.0F * var1, argb(12962550, var2 * (var8 ? 0.98F : 0.82F)), var1, "discord.gg/vWnaE9hMD");
         float var23 = var6 + var3 - 18.0F * var1;
         float var24 = var7 + var4 * 0.5F;
         float var25 = 4.2F * var1;
         int var26 = MenuFrame.col(-1644299, var2 * (var8 ? 0.95F : 0.62F));
         float var27 = Math.max(1.2F, 1.35F * var1);
         var9.addLine(var23 - var25 * 0.35F, var24 - var25, var23 + var25 * 0.5F, var24, var26, var27);
         var9.addLine(var23 - var25 * 0.35F, var24 + var25, var23 + var25 * 0.5F, var24, var26, var27);
      }
   }

   private static int argb(int var0, float var1) {
      int var2 = Math.round(Math.max(0.0F, Math.min(1.0F, var1)) * 255.0F);
      return var2 << 24 | var0 & 16777215;
   }

   public static void open() {
      try {
         Class var0 = Class.forName("net.minecraft.class_156");
         Object var1 = var0.getMethod("method_668").invoke(null);
         var1.getClass().getMethod("method_670", String.class).invoke(var1, "https://discord.gg/vWnaE9hMD");
      } catch (Throwable var3) {
         try {
            Desktop.getDesktop().browse(URI.create("https://discord.gg/vWnaE9hMD"));
         } catch (Throwable var2) {
         }
      }
   }
}
