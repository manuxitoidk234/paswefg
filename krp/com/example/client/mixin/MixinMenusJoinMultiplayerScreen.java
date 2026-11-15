package com.example.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.gui.KryptonTitleScreen;
import net.krypton.client.gui.mainmenu.MultiplayerOverlay;
import net.minecraft.class_11908;
import net.minecraft.class_11909;
import net.minecraft.class_2561;
import net.minecraft.class_437;
import net.minecraft.class_500;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(class_500.class)
public abstract class MixinMenusJoinMultiplayerScreen extends class_437 {
   @Unique
   private MultiplayerOverlay krypton$overlay;

   protected MixinMenusJoinMultiplayerScreen(class_2561 title) {
      super(title);
   }

   @Inject(method = "method_25426", at = @At("TAIL"))
   private void krypton$addOverlay(CallbackInfo ci) {
      this.krypton$overlay = KryptonTitleScreen.enabled() ? new MultiplayerOverlay((class_500)this) : null;
      if (this.krypton$overlay != null) {
         this.krypton$overlay.show();
      }
   }

   @Inject(method = "method_48640", at = @At("TAIL"))
   private void krypton$showOverlay(CallbackInfo ci) {
      if (this.krypton$overlay != null) {
         this.krypton$overlay.show();
      }
   }

   @Inject(method = "method_25432", at = @At("TAIL"))
   private void krypton$hideOverlay(CallbackInfo ci) {
      if (this.krypton$overlay != null) {
         this.krypton$overlay.hide();
      }
   }

   @Inject(method = "method_25404", at = @At("HEAD"), cancellable = true)
   private void krypton$overlayKeys(class_11908 event, CallbackInfoReturnable<Boolean> cir) {
      if (this.krypton$overlay != null && this.krypton$overlay.keyPressed(event.comp_4795())) {
         cir.setReturnValue(true);
      }
   }

   public boolean method_25402(class_11909 event, boolean doubleClick) {
      return this.krypton$takesPointer() || super.method_25402(event, doubleClick);
   }

   public boolean method_25406(class_11909 event) {
      return this.krypton$takesPointer() || super.method_25406(event);
   }

   public boolean method_25403(class_11909 event, double dragX, double dragY) {
      return this.krypton$takesPointer() || super.method_25403(event, dragX, dragY);
   }

   public boolean method_25401(double mouseX, double mouseY, double scrollX, double scrollY) {
      return this.krypton$takesPointer() || super.method_25401(mouseX, mouseY, scrollX, scrollY);
   }

   @Unique
   private boolean krypton$takesPointer() {
      return this.krypton$overlay != null && this.krypton$overlay.takesPointer();
   }
}
