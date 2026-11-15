package net.krypton.client.addon;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.krypton.client.config.ModuleConfigStore;
import net.krypton.client.module.ClientModule;
import net.krypton.client.module.ModuleRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public final class AddonManager {
   private static final Logger LOGGER = LoggerFactory.getLogger("Krypton");
   public static final String ENTRYPOINT = "krypton";
   private static final String CLIENT_PACKAGE = "net.krypton.client";
   private static List<KryptonAddon> addons;
   private static boolean configLoaded;

   private AddonManager() {
   }

   public static List<KryptonAddon> addons() {
      List<KryptonAddon> loaded = addons;
      return loaded != null ? loaded : List.of();
   }

   public static void init() {
      if (addons == null) {
         List<KryptonAddon> loaded = create();
         loaded.removeIf(addonx -> !run(addonx, "register its categories", KryptonAddon::onRegisterCategories));
         loaded.removeIf(addonx -> {
            if (run(addonx, "initialise", KryptonAddon::onInitialize)) {
               return false;
            }

            removeModules(addonx);
            return true;
         });

         for (KryptonAddon addon : loaded) {
            LOGGER.info(
               "[Krypton] Loaded addon {} v{} by {}",
               new Object[]{addon.name(), addon.version(), addon.authors().isEmpty() ? "unknown" : String.join(", ", addon.authors())}
            );
         }

         addons = List.copyOf(loaded);
      }
   }

   public static void configLoaded() {
      if (addons != null && !configLoaded) {
         configLoaded = true;
         List<KryptonAddon> loaded = new ArrayList<>(addons);
         loaded.removeIf(addon -> {
            if (run(addon, "start", KryptonAddon::onConfigLoaded)) {
               return false;
            }

            removeModules(addon);
            return true;
         });
         addons = List.copyOf(loaded);
      }
   }

   private static List<KryptonAddon> create() {
      List<KryptonAddon> created = new ArrayList<>();

      List<EntrypointContainer<KryptonAddon>> containers;
      try {
         containers = FabricLoader.getInstance().getEntrypointContainers("krypton", KryptonAddon.class);
      } catch (RuntimeException | LinkageError e) {
         LOGGER.error("[Krypton] Could not list addons; starting without them", e);
         return created;
      }

      for (EntrypointContainer<KryptonAddon> container : containers) {
         ModMetadata metadata = container.getProvider().getMetadata();

         KryptonAddon addon;
         String pkg;
         try {
            addon = (KryptonAddon)container.getEntrypoint();
            pkg = addon.getPackage();
         } catch (RuntimeException | LinkageError e) {
            LOGGER.error("[Krypton] Addon {} ({}) could not be created and is skipped", new Object[]{metadata.getName(), metadata.getId(), e});
            continue;
         }

         addon.bind(metadata);
         String problem = packageProblem(addon, pkg, created);
         if (problem != null) {
            LOGGER.error("[Krypton] Addon {} is skipped: {}", addon.name(), problem);
         } else {
            addon.packageName = pkg;
            created.add(addon);
         }
      }

      return created;
   }

   private static String packageProblem(KryptonAddon addon, String pkg, List<KryptonAddon> created) {
      if (pkg != null && addon.getClass().getName().startsWith(pkg + ".")) {
         if (overlap(pkg, "net.krypton.client")) {
            return "its package " + pkg + " overlaps Krypton' own (net.krypton.client)";
         }

         for (KryptonAddon other : created) {
            if (overlap(pkg, other.packageName)) {
               return "its package "
                  + pkg
                  + " overlaps "
                  + other.name()
                  + "'s ("
                  + other.packageName
                  + "); an addon made from the template needs its package renamed";
            }
         }

         return null;
      } else {
         return "getPackage() is \"" + pkg + "\", which doesn't hold its class " + addon.getClass().getName();
      }
   }

   private static boolean overlap(String a, String b) {
      return (a + ".").startsWith(b + ".") || (b + ".").startsWith(a + ".");
   }

   private static boolean run(KryptonAddon addon, String step, Consumer<KryptonAddon> action) {
      try {
         action.accept(addon);
         return true;
      } catch (RuntimeException | LinkageError e) {
         LOGGER.error("[Krypton] Addon {} failed to {} and is skipped{}", new Object[]{addon.name(), step, reportHint(addon), e});
         return false;
      }
   }

   private static String reportHint(KryptonAddon addon) {
      try {
         KryptonAddon.GithubRepo repo = addon.getRepo();
         String where = repo != null ? repo.url() + "/issues" : addon.getWebsite();
         return where != null ? " - report it at " + where : "";
      } catch (RuntimeException | LinkageError e) {
         return "";
      }
   }

   private static void removeModules(KryptonAddon addon) {
      String prefix = addon.packageName + ".";

      for (ClientModule module : ModuleRegistry.all()) {
         if (module.getClass().getName().startsWith(prefix)) {
            ModuleConfigStore.getInstance().release(module);
            if (module.isEnabled()) {
               module.setEnabled(false);
            }

            ModuleRegistry.remove(module);
         }
      }
   }
}
