package net.krypton.client.gui.mainmenu;

import imgui.ImDrawList;
import imgui.ImGui;
import net.krypton.client.license.LicenseKeys;

public final class LicensePopup {
   public static final LicensePopup INSTANCE = new LicensePopup();
   private final DuskWidgets ui = new DuskWidgets();
   private final MenuFrame frame = new MenuFrame();
   private final DuskWidgets.TextField field = new DuskWidgets.TextField(64)
      .placeholder("KRNG-XXXX-XXXX-XXXX-XXXX")
      .allowedChars("0123456789ABCDEFGHJKMNPQRSTVWXYZ-");
   private DuskWidgets.Dialog dialog;
   private long openedAt;
   private int notice;
   private boolean opening;

   private LicensePopup() {
   }

   public static boolean isOpen() {
      DuskWidgets.Dialog var0 = INSTANCE.dialog;
      return var0 != null && !var0.isClosed();
   }

   public static boolean blocksMenu(CreditsPopup var0) {
      return var0.isOpen() || isOpen();
   }

   public static boolean intercept(MenuAction var0) {
      if (LicenseKeys.isUnlocked()) {
         return false;
      }

      if (!worldEntry(var0)) {
         return false;
      }

      open();
      return true;
   }

   public static boolean hold(MenuAction var0) {
      if (!worldEntry(var0)) {
         return false;
      }

      if (LicenseKeys.sessionHolds()) {
         return false;
      }

      open();
      return true;
   }

   public static void open() {
      if (!isOpen()) {
         INSTANCE.present();
      }
   }

   public static boolean keyPressed(int var0) {
      if (!isOpen()) {
         return false;
      }

      if (var0 == 256) {
         INSTANCE.dialog.dismiss();
      }

      return true;
   }

   public void render(ImDrawList var1, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8) {
      if (isOpen()) {
         float var9 = (float)(System.nanoTime() - this.openedAt) / 1.0E9F;
         boolean var10 = var8 && !this.opening;
         this.opening = false;
         ImGui.setNextWindowPos(0.0F, 0.0F);
         ImGui.setNextWindowSize(var2, var3);
         ImGui.setNextWindowFocus();
         ImGui.pushStyleVar(2, 0.0F, 0.0F);
         ImGui.pushStyleVar(4, 0.0F);
         ImGui.begin("##krypton_license", 447);
         ImGui.popStyleVar(2);

         try {
            this.frame.begin(ImGui.getWindowDrawList(), var1, var2, var3, var4, var9, var5, var6, var7, var10);
            this.ui.begin(this.frame);
            this.ui.dialog(this.dialog);
         } finally {
            ImGui.end();
         }
      }
   }

   private void present() {
      this.notice = 0;
      this.opening = true;
      this.openedAt = System.nanoTime();
      this.field.focus();
      this.dialog = new DuskWidgets.Dialog("License", "Enter your license key")
         .content(84.0F, this::paintBody)
         .submit("Unlock", this::commitTyped)
         .cancel("Close");
   }

   private boolean commitTyped() {
      String var1 = this.field.text();
      int var2 = grade(LicenseKeys.verify(var1), LicenseKeys.unlock(var1));
      this.notice = var2;
      return var2 == 0;
   }

   private static int grade(boolean var0, boolean var1) {
      int var2 = (var0 ? 1 : 0) | (var1 ? 2 : 0);
      return var2 == 3 ? 0 : var2 + 1;
   }

   private void paintBody(DuskWidgets var1, DuskWidgets.Dialog var2, float var3, float var4, float var5, float var6) {
      MenuFrame var7 = var1.frame();
      float var8 = var7.px(40.0F);
      var1.textField(this.field, var3, var4, var5, var8);
      if (this.notice != 0) {
         var7.text(var7.bg(), var7.bodyFont(), var3, var4 + var8 + var7.px(6.0F), -45747, var1.alpha(), "invalid key");
      }
   }

   private static boolean worldEntry(MenuAction var0) {
      return var0 == MenuAction.SINGLEPLAYER || var0 == MenuAction.MULTIPLAYER || var0 == MenuAction.REALMS;
   }
}
