package net.krypton.client.module.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class ModeSetting extends Setting {
   private List<String> modes;
   private int selectedIndex;
   private String selected;

   public ModeSetting(String name, String defaultMode, String... modes) {
      super(name);
      this.modes = List.copyOf(Arrays.asList(modes));
      this.select(Math.max(0, this.modes.indexOf(defaultMode)));
   }

   public String get() {
      return this.selected;
   }

   public boolean is(String var1) {
      if (var1 != null) {
         String var2 = var1.toLowerCase(Locale.ROOT);
         if (var2.equals("borealis")) {
            var1 = "Celestia";
         } else if (var2.equals("nebula")) {
            var1 = "Cosmos";
         } else if (var2.equals("anubis")) {
            var1 = "Plasma";
         } else if (var2.equals("frost")) {
            var1 = "Abyss";
         } else if (var2.equals("crimson")) {
            var1 = "Stellar";
         }
      }

      return this.selected != null && var1 != null ? this.selected.equalsIgnoreCase(var1) : false;
   }

   public void set(String var1) {
      if (var1 != null) {
         String var4 = var1.toLowerCase(Locale.ROOT);
         if (var4.equals("borealis")) {
            var1 = "Celestia";
         } else if (var4.equals("nebula")) {
            var1 = "Cosmos";
         } else if (var4.equals("anubis")) {
            var1 = "Plasma";
         } else if (var4.equals("frost")) {
            var1 = "Abyss";
         } else if (var4.equals("crimson")) {
            var1 = "Stellar";
         }
      }

      int var2 = -1;

      for (int var3 = 0; var3 < this.modes.size(); var3++) {
         if (var1 != null && this.modes.get(var3).equalsIgnoreCase(var1)) {
            var2 = var3;
            break;
         }
      }

      if (var2 >= 0 && var2 != this.selectedIndex) {
         this.select(var2);
         this.notifyChanged();
      }
   }

   public String value() {
      return this.get();
   }

   public List<String> modes() {
      return this.modes;
   }

   public void setModes(List<String> next) {
      if (!next.isEmpty() && !next.equals(this.modes)) {
         this.modes = List.copyOf(next);
         int idx = this.modes.indexOf(this.selected);
         if (idx >= 0) {
            this.selectedIndex = idx;
         } else {
            this.select(0);
            this.notifyChanged();
         }
      }
   }

   public void next() {
      if (this.modes.size() >= 2) {
         this.select((this.selectedIndex + 1) % this.modes.size());
         this.notifyChanged();
      }
   }

   private void select(int index) {
      this.selectedIndex = index;
      this.selected = this.modes.get(index);
   }

   @Override
   public JsonElement serialize() {
      return new JsonPrimitive(this.get());
   }

   @Override
   protected void deserializeValue(JsonElement var1) {
      if (var1 != null && var1.isJsonPrimitive()) {
         try {
            String var2 = var1.getAsString();
            if (var2 != null) {
               String var4 = var2.toLowerCase(Locale.ROOT);
               if (var4.equals("borealis")) {
                  var2 = "Celestia";
               } else if (var4.equals("nebula")) {
                  var2 = "Cosmos";
               } else if (var4.equals("anubis")) {
                  var2 = "Plasma";
               } else if (var4.equals("frost")) {
                  var2 = "Abyss";
               } else if (var4.equals("crimson")) {
                  var2 = "Stellar";
               }
            }

            int var3 = -1;

            for (int var5 = 0; var5 < this.modes.size(); var5++) {
               if (this.modes.get(var5).equalsIgnoreCase(var2)) {
                  var3 = var5;
                  break;
               }
            }

            if (var3 >= 0) {
               this.select(var3);
            }
         } catch (RuntimeException var6) {
         }
      }
   }
}
