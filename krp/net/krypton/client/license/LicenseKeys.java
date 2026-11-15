package net.krypton.client.license;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import net.fabricmc.loader.api.FabricLoader;

public final class LicenseKeys {
   private static final byte MASK = 109;
   private static final byte[] URL_BYTES = new byte[]{
      5, 25, 25, 29, 87, 66, 66, 92, 95, 90, 67, 93, 67, 93, 67, 92, 87, 85, 90, 84, 92, 66, 27, 92, 66, 14, 5, 8, 14, 6
   };
   private static final byte[] KEY_BYTES = new byte[]{
      32,
      36,
      36,
      47,
      36,
      7,
      44,
      35,
      47,
      10,
      6,
      28,
      5,
      6,
      4,
      42,
      84,
      26,
      93,
      47,
      44,
      60,
      40,
      43,
      44,
      44,
      34,
      46,
      44,
      60,
      85,
      44,
      32,
      36,
      36,
      47,
      46,
      10,
      38,
      46,
      44,
      60,
      40,
      44,
      21,
      91,
      5,
      42,
      52,
      3,
      89,
      66,
      40,
      70,
      92,
      24,
      30,
      40,
      33,
      52,
      39,
      62,
      57,
      5,
      31,
      21,
      52,
      3,
      12,
      89,
      2,
      60,
      56,
      89,
      63,
      47,
      58,
      5,
      88,
      35,
      27,
      39,
      10,
      29,
      89,
      15,
      47,
      27,
      47,
      21,
      7,
      70,
      94,
      58,
      36,
      61,
      28,
      30,
      12,
      58,
      9,
      4,
      7,
      91,
      28,
      23,
      66,
      40,
      57,
      5,
      21,
      9,
      38,
      8,
      2,
      91,
      2,
      28,
      62,
      63,
      46,
      5,
      34,
      7,
      24,
      8,
      29,
      57,
      26,
      94,
      93,
      56,
      7,
      29,
      28,
      31,
      94,
      94,
      88,
      88,
      95,
      43,
      56,
      46,
      57,
      10,
      70,
      31,
      46,
      59,
      8,
      8,
      85,
      37,
      91,
      5,
      29,
      12,
      11,
      91,
      10,
      23,
      94,
      27,
      38,
      14,
      89,
      84,
      63,
      63,
      31,
      91,
      58,
      29,
      37,
      58,
      1,
      57,
      10,
      47,
      32,
      8,
      60,
      42,
      42,
      11,
      39,
      11,
      89,
      94,
      35,
      61,
      12,
      61,
      61,
      56,
      40,
      5,
      60,
      1,
      5,
      37,
      23,
      84,
      37,
      30,
      12,
      36,
      40,
      8,
      2,
      9,
      57,
      59,
      32,
      38,
      47,
      32,
      15,
      41,
      34,
      29,
      92,
      56,
      12,
      10,
      23,
      21,
      59,
      38,
      93,
      95,
      44,
      91,
      4,
      3,
      24,
      0,
      6,
      21,
      36,
      24,
      43,
      63,
      25,
      9,
      10,
      85,
      47,
      14,
      7,
      4,
      85,
      14,
      7,
      88,
      44,
      90,
      85,
      84,
      84,
      91,
      15,
      39,
      52,
      41,
      91,
      47,
      10,
      8,
      56,
      24,
      30,
      60,
      5,
      40,
      26,
      41,
      94,
      20,
      31,
      44,
      84,
      55,
      0,
      94,
      26,
      5,
      52,
      90,
      88,
      93,
      10,
      14,
      21,
      9,
      52,
      41,
      30,
      92,
      35,
      89,
      70,
      44,
      43,
      35,
      25,
      88,
      89,
      84,
      38,
      88,
      24,
      95,
      58,
      46,
      59,
      11,
      84,
      24,
      32,
      46,
      9,
      53,
      88,
      10,
      3,
      52,
      55,
      70,
      92,
      24,
      26,
      95,
      8,
      60,
      95,
      62,
      7,
      52,
      47,
      70,
      62,
      23,
      6,
      85,
      57,
      66,
      53,
      1,
      27,
      53,
      8,
      89,
      12,
      60,
      93,
      25,
      84,
      57,
      29,
      21,
      31,
      90,
      29,
      95,
      24,
      31,
      90,
      57,
      32,
      2,
      6,
      3,
      58,
      6,
      35,
      46,
      26,
      35,
      60,
      28,
      35,
      59,
      29,
      60,
      36,
      41,
      44,
      60,
      44,
      47
   };
   private static final String ALPHABET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";
   private static final Object LOCK = new Object();
   private static final AtomicBoolean BUSY = new AtomicBoolean();
   private static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4L)).build();
   private static volatile LicenseKeys.Proof proof;
   private static volatile long lastPull;

   private LicenseKeys() {
   }

   public static boolean isUnlocked() {
      if (openSeal()) {
         poke();
         return true;
      } else {
         pullStored();
         return openSeal();
      }
   }

   public static boolean sessionHolds() {
      if (holdSeal()) {
         poke();
         return true;
      } else {
         pullStored();
         return holdSeal();
      }
   }

   public static boolean verify(String var0) {
      LicenseKeys.Proof var1 = pull(fold(var0));
      return var1 != null && openSeal();
   }

   public static boolean unlock(String var0) {
      String var1 = fold(var0);
      LicenseKeys.Proof var2 = pull(var1);
      if (var2 != null && holdSeal()) {
         try {
            Path var3 = storePath();
            Files.createDirectories(var3.getParent());
            Files.writeString(var3, var1, StandardCharsets.UTF_8);
            return openSeal();
         } catch (Exception var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static void poke() {
      if (System.currentTimeMillis() - lastPull >= 60000L) {
         if (BUSY.compareAndSet(false, true)) {
            Thread var0 = new Thread(() -> {
               try {
                  pull(loadStored());
               } finally {
                  BUSY.set(false);
               }
            }, "kr-seal");
            var0.setDaemon(true);
            var0.start();
         }
      }
   }

   private static void pullStored() {
      synchronized (LOCK) {
         if (!openSeal()) {
            pull(loadStored());
         }
      }
   }

   private static LicenseKeys.Proof pull(String var0) {
      if (var0 != null && var0.length() == 24 && var0.startsWith("KRNG-")) {
         String var1 = hwid();

         try {
            HttpRequest var2 = HttpRequest.newBuilder(URI.create(reveal(URL_BYTES)))
               .timeout(Duration.ofSeconds(4L))
               .header("Content-Type", "text/plain; charset=utf-8")
               .POST(BodyPublishers.ofString(var0 + "\n" + var1 + "\n"))
               .build();
            HttpResponse var3 = HTTP.send(var2, BodyHandlers.ofString(StandardCharsets.UTF_8));
            lastPull = System.currentTimeMillis();
            if (var3.statusCode() != 200) {
               return proof;
            }

            String[] var4 = ((String)var3.body()).split("\n", 5);
            if (var4.length >= 4 && "ok".equals(var4[0])) {
               long var5 = Long.parseLong(var4[1].trim());
               if (!var1.equals(var4[2].trim())) {
                  proof = null;
                  return null;
               } else {
                  LicenseKeys.Proof var7 = new LicenseKeys.Proof(var0, var1, var5, var4[3].trim());
                  if (!signed(var7)) {
                     proof = null;
                     return null;
                  } else {
                     proof = var7;
                     return var7;
                  }
               }
            } else {
               proof = null;
               return null;
            }
         } catch (Exception var8) {
            return proof;
         }
      } else {
         proof = null;
         return null;
      }
   }

   private static boolean openSeal() {
      LicenseKeys.Proof var0 = proof;
      if (var0 != null && var0.exp * 1000L >= System.currentTimeMillis()) {
         try {
            Signature var1 = Signature.getInstance("SHA256withRSA");
            var1.initVerify(publicKey());
            var1.update(message(var0).getBytes(StandardCharsets.UTF_8));
            return var1.verify(Base64.getDecoder().decode(var0.sig));
         } catch (Exception var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean holdSeal() {
      LicenseKeys.Proof var0 = proof;
      if (var0 == null) {
         return false;
      }

      if (var0.exp <= System.currentTimeMillis() / 1000L) {
         return false;
      }

      if (var0.key.length() == 24 && var0.hwid.length() >= 8) {
         try {
            byte[] var1 = Base64.getDecoder().decode(var0.sig);
            Signature var2 = Signature.getInstance("SHA256withRSA");
            var2.initVerify(publicKey());
            var2.update(message(var0).getBytes(StandardCharsets.UTF_8));
            boolean var3 = var2.verify(var1);
            return var3 && var0.sig.length() > 40;
         } catch (Exception var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean signed(LicenseKeys.Proof var0) {
      return openSealOf(var0) && holdSealOf(var0);
   }

   private static boolean openSealOf(LicenseKeys.Proof var0) {
      LicenseKeys.Proof var1 = proof;
      proof = var0;
      boolean var2 = openSeal();
      if (!var2) {
         proof = var1;
      }

      return var2;
   }

   private static boolean holdSealOf(LicenseKeys.Proof var0) {
      LicenseKeys.Proof var1 = proof;
      proof = var0;
      boolean var2 = holdSeal();
      if (!var2) {
         proof = var1;
      }

      return var2;
   }

   private static String message(LicenseKeys.Proof var0) {
      return "v1\n" + var0.key + "\n" + var0.hwid + "\n" + var0.exp;
   }

   private static PublicKey publicKey() throws Exception {
      byte[] var0 = Base64.getDecoder().decode(reveal(KEY_BYTES));
      return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(var0));
   }

   private static String reveal(byte[] var0) {
      char[] var1 = new char[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = (char)(var0[var2] ^ 109);
      }

      return new String(var1);
   }

   private static String hwid() {
      String var0 = System.getProperty("os.name", "")
         + "|"
         + System.getProperty("os.arch", "")
         + "|"
         + System.getProperty("user.name", "")
         + "|"
         + System.getenv().getOrDefault("COMPUTERNAME", "");

      try {
         byte[] var1 = MessageDigest.getInstance("SHA-256").digest(var0.getBytes(StandardCharsets.UTF_8));
         StringBuilder var2 = new StringBuilder();

         for (int var3 = 0; var3 < 8; var3++) {
            var2.append(String.format("%02x", var1[var3]));
         }

         return var2.toString();
      } catch (Exception var4) {
         return "unknown-pc";
      }
   }

   private static Path storePath() {
      return FabricLoader.getInstance().getGameDir().resolve("config").resolve("krypton-license.txt");
   }

   private static String loadStored() {
      try {
         Path var0 = storePath();
         if (!Files.isRegularFile(var0)) {
            return null;
         }

         String var1 = fold(Files.readString(var0, StandardCharsets.UTF_8));
         return var1.startsWith("KRNG-") ? var1 : null;
      } catch (Exception var2) {
         return null;
      }
   }

   private static String fold(String var0) {
      if (var0 == null) {
         return "";
      }

      StringBuilder var1 = new StringBuilder();

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var3 = Character.toUpperCase(var0.charAt(var2));
         if (var3 == '-' || "0123456789ABCDEFGHJKMNPQRSTVWXYZ".indexOf(var3) >= 0) {
            var1.append(var3);
         }
      }

      return var1.toString();
   }

   private static final class Proof {
      final String key;
      final String hwid;
      final long exp;
      final String sig;

      Proof(String var1, String var2, long var3, String var5) {
         this.key = var1;
         this.hwid = var2;
         this.exp = var3;
         this.sig = var5;
      }
   }
}
