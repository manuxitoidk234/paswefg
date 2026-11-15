package net.krypton.client.gui.mainmenu;

import imgui.ImDrawList;
import imgui.ImFont;
import imgui.ImGui;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.krypton.client.account.Account;
import net.krypton.client.account.AccountType;
import net.krypton.client.account.Accounts;
import net.krypton.client.gui.EgyptDraw;

public final class KryptonPlayerShowcase {
   private static final int ACCENT = -14494465;
   private static final int CORE = -1;
   private static final int GOLD = -12192;
   private static final int HOODIE = -15723494;
   private static final int MAX_FACES = 96;
   private static final float[] SX = new float[384];
   private static final float[] SY = new float[384];
   private static final float[] SU = new float[384];
   private static final float[] SV = new float[384];
   private static final float[] SZ = new float[96];
   private static final int[] SC = new int[96];
   private static final int[] ORDER = new int[96];
   private static int faceCount;
   private static boolean placed;
   private static boolean held;
   private static boolean prevDown;
   private static float dollX;
   private static float dollY;
   private static float grabOX;
   private static float grabOY;
   private static float vx;
   private static float vy;
   private static float yaw = 18.0F;
   private static float pitch;
   private static float yawVel;
   private static float pitchVel;
   private static float unit = 10.0F;
   private static final int GRAB_BODY = 1;
   private static final int GRAB_RARM = 2;
   private static final int GRAB_LARM = 3;
   private static final int GRAB_RLEG = 4;
   private static final int GRAB_LLEG = 5;
   private static final int GRAB_HEAD = 6;
   private static int grab;
   private static float grabT;
   private static float simAccum;
   private static boolean sleeping;
   private static boolean stick;
   private static int gB0 = -2;
   private static int gB1 = -2;
   private static float gT;
   private static float prevVx;
   private static float prevVy;
   private static float prevFwd;
   private static float prevSide;
   private static float rightArm;
   private static float leftArm;
   private static float rightLeg;
   private static float leftLeg;
   private static float rightArmVel;
   private static float leftArmVel;
   private static float rightLegVel;
   private static float leftLegVel;
   private static float rightSpread;
   private static float leftSpread;
   private static float rightLegSpread;
   private static float leftLegSpread;
   private static float rightSpreadVel;
   private static float leftSpreadVel;
   private static float rightLegSpreadVel;
   private static float leftLegSpreadVel;
   private static float jointX;
   private static float jointY;
   private static float jointVx;
   private static float jointVy;
   private static float prevMx;
   private static float bodyRoll;
   private static float bodyRollVel;
   private static float comX;
   private static float comY;
   private static float comVx;
   private static float comVy;
   private static boolean comReady;
   private static float hangBlend;
   private static float hangSpread0;
   private static float hangRoll0;
   private static boolean lastSlim;
   private static final int NP = 11;
   private static final int HEAD = 0;
   private static final int NECK = 1;
   private static final int PELVIS = 2;
   private static final int RELBOW = 3;
   private static final int RHAND = 4;
   private static final int LELBOW = 5;
   private static final int LHAND = 6;
   private static final int RKNEE = 7;
   private static final int RFOOT = 8;
   private static final int LKNEE = 9;
   private static final int LFOOT = 10;
   private static final float[] PX = new float[11];
   private static final float[] PY = new float[11];
   private static final float[] VX = new float[11];
   private static final float[] VY = new float[11];
   private static final float[] MASS = new float[]{1.3F, 2.6F, 3.6F, 0.75F, 0.42F, 0.75F, 0.42F, 0.95F, 0.55F, 0.95F, 0.55F};
   private static final float[] FX = new float[11];
   private static final float[] FY = new float[11];
   private static final float[] OX = new float[11];
   private static final float[] OY = new float[11];
   private static final float[] AX = new float[4];
   private static final float[] AY = new float[4];
   private static boolean ragReady;
   private static boolean ragdoll = true;
   private static boolean frozen;
   private static boolean gravityOn = true;
   private static boolean yawDrag;
   private static float zoom = 1.0F;
   private static float spawnX;
   private static float spawnY;
   private static int hoverPart;
   private static float rsx;
   private static float rsy;
   private static float lsx;
   private static float lsy;
   private static float rhx;
   private static float rhy;
   private static float lhx;
   private static float lhy;
   private static float uiScale = 1.0F;
   private static float outA;
   private static float outV;
   private static Method glfwMouse;
   private static boolean glfwFailed;
   private static float b0x;
   private static float b0y;
   private static float b1x;
   private static float b1y;
   private static int b0;
   private static int b1;
   private static int bonePick = 0;
   private static float boneBest = 1.0E9F;
   private static float hotX0;
   private static float hotY0;
   private static float hotX1;
   private static float hotY1;
   private static float hotX2;
   private static float hotY2;
   private static float hotX3;
   private static float hotY3;
   private static int hotGlow;
   private static boolean hotOn;
   private static float tipX;
   private static float tipY;
   private static float tipZ;
   private static final float[] QX = new float[4];
   private static final float[] QY = new float[4];
   private static final float[] QZ = new float[4];
   private static float pnx;
   private static float pny;
   private static float pnz;
   private static volatile String lookupName = "";
   private static volatile UUID lookupUuid;
   private static volatile boolean lookupStarted;

   private KryptonPlayerShowcase() {
   }

   public static boolean capturesPointer(float var0, float var1) {
      return false;
   }

   public static void render(MenuContext var0, float var1, float var2, float var3, float var4, float var5) {
      if (var0 != null && !(var5 <= 0.02F)) {
         ImDrawList var6 = var0.bg();
         if (var6 != null) {
            float var7 = Math.min(1.0F, Math.max(0.0F, var5));
            float var8 = var0.height();
            unit = clamp(var8 * 0.013F, 8.0F, 15.0F);
            float var9 = var1 + var3 * 0.5F;
            float var10 = var2 + var4 * 0.86F;
            dollX = var9;
            dollY = var10;
            placed = true;
            yaw = 18.0F;
            pitch = 0.0F;
            bodyRoll = 0.0F;
            rightArm = 0.0F;
            leftArm = 0.0F;
            rightLeg = 0.0F;
            leftLeg = 0.0F;
            rightSpread = 0.0F;
            leftSpread = 0.0F;
            rightLegSpread = 0.0F;
            leftLegSpread = 0.0F;
            KryptonPlayerShowcase.SessionInfo var11 = resolveSession(var0);
            ensureUuid(var11);
            lastSlim = var11.slim;
            int var12 = resolveSkinGlId(var11);
            if (var12 != 0) {
               drawSkin(var6, var12, var9, var10, unit, 18.0F, 0.0F, var11.slim, var7);
            } else {
               drawFallback(var6, var11, var7);
            }

            float var13 = var9;
            float var14 = var10 - 34.0F * unit;
            drawBadge(var0, var6, var13, var14, var11, var7);
         }
      }
   }

   private static void stepPhysics(MenuContext var0, float var1, float var2, float var3) {
      uiScale = var0.scale();
      float var4 = clamp(var2 * 0.013F * zoom, 7.0F, 20.0F);
      if (ragReady && simScale(var4)) {
         unit = var4;
      } else {
         unit = var4;
      }

      float var5 = var0.mouseX();
      float var6 = var0.mouseY();
      if (!ragReady) {
         spawnX = dollX;
         spawnY = var2 - 16.0F;
         poseStand(spawnX, spawnY);
         ragReady = true;
      }

      boolean var7 = mouseButton(0);
      boolean var8 = var7 && !prevDown;
      boolean var9 = mouseButton(1);
      hoverPart = pickBone(var5, var6);
      float var10 = mouseWheel();
      if (grab == 0 && Math.abs(var10) > 0.01F && hoverPart != 0) {
         zoom = clamp(zoom + var10 * 0.12F, 0.65F, 1.6F);
      }

      if (var9 && !yawDrag && hoverPart != 0) {
         yawDrag = true;
      }

      if (!var9) {
         yawDrag = false;
      }

      if (yawDrag) {
         yaw = yaw + (var5 - prevMx) * 0.65F;
      }

      int var11 = controlAt(var5, var6);
      if (var8 && var11 >= 0 && hoverPart == 0) {
         if (var11 == 0) {
            poseStand(PX[2], var2 - 16.0F);
         } else if (var11 == 1) {
            ragdoll = !ragdoll;
         } else if (var11 == 2) {
            frozen = !frozen;
         } else if (var11 == 3) {
            impulse(520.0F, -1100.0F);
         } else if (var11 == 4) {
            gravityOn = !gravityOn;
         } else {
            poseStand(spawnX, spawnY);
         }

         grab = 0;
      } else if (var8 && hoverPart != 0 && ragdoll && !frozen) {
         grab = hoverPart;
         anchors();
         boneEnds(hoverPart);
         float var12 = b1x - b0x;
         float var13 = b1y - b0y;
         float var14 = var12 * var12 + var13 * var13;
         grabT = var14 < 1.0F ? 1.0F : clamp(((var5 - b0x) * var12 + (var6 - b0y) * var13) / var14, 0.0F, 1.0F);
      }

      if (grab != 0 && !var7) {
         grab = 0;
      }

      if (ragdoll && !frozen) {
         simulate(var5, var6, var3, var1, var2);
      }

      dollX = (PX[8] + PX[10]) * 0.5F;
      dollY = (PY[8] + PY[10]) * 0.5F;
      prevMx = var5;
      prevDown = var7;
   }

   private static boolean simScale(float var0) {
      if (!(unit < 1.0F) && !(Math.abs(var0 - unit) < 0.02F)) {
         float var1 = var0 / unit;
         float var2 = PX[2];
         float var3 = PY[2];

         for (int var4 = 0; var4 < 11; var4++) {
            PX[var4] = var2 + (PX[var4] - var2) * var1;
            PY[var4] = var3 + (PY[var4] - var3) * var1;
            VX[var4] = VX[var4] * var1;
            VY[var4] = VY[var4] * var1;
         }

         return true;
      } else {
         return true;
      }
   }

   private static void poseStand(float var0, float var1) {
      float var2 = unit;
      float var3 = var1 - 12.0F * var2;
      float var4 = var3 - 12.0F * var2;
      float var5 = 6.0F * var2;
      float var6 = 2.0F * var2;
      setP(0, var0, var4 - 6.0F * var2);
      setP(1, var0, var4);
      setP(2, var0, var3);
      setP(3, var0 - var5, var4 + 6.0F * var2);
      setP(4, var0 - var5, var4 + 12.0F * var2);
      setP(5, var0 + var5, var4 + 6.0F * var2);
      setP(6, var0 + var5, var4 + 12.0F * var2);
      setP(7, var0 - var6, var3 + 6.0F * var2);
      setP(8, var0 - var6, var1);
      setP(9, var0 + var6, var3 + 6.0F * var2);
      setP(10, var0 + var6, var1);

      for (int var7 = 0; var7 < 11; var7++) {
         VX[var7] = 0.0F;
         VY[var7] = 0.0F;
      }

      bodyRoll = 0.0F;
      bodyRollVel = 0.0F;
      simAccum = 0.0F;
      sleeping = false;
   }

   private static void setP(int var0, float var1, float var2) {
      PX[var0] = var1;
      PY[var0] = var2;
   }

   private static void impulse(float var0, float var1) {
      sleeping = false;
      float var2 = PX[2];
      float var3 = PY[2];

      for (int var4 = 0; var4 < 11; var4++) {
         VX[var4] = VX[var4] + (var0 + (PY[var4] - var3) * 1.6F);
         VY[var4] = VY[var4] + (var1 - (PX[var4] - var2) * 1.6F);
      }
   }

   private static void simulate(float var0, float var1, float var2, float var3, float var4) {
      if (!(var2 < 0.0F)) {
         if (var2 > 0.05F) {
            var2 = 0.05F;
         }

         if (grab != 0) {
            sleeping = false;
         }

         if (!sleeping) {
            simAccum += var2;
            if (simAccum > 0.1F) {
               simAccum = 0.1F;
            }

            float var5 = 0.016666668F;
            int var6 = 0;

            while (simAccum >= var5 && var6 < 6) {
               stepSim(var0, var1, var5, var3, var4);
               simAccum -= var5;
               var6++;
               if (sleeping) {
                  simAccum = 0.0F;
                  return;
               }
            }
         }
      }
   }

   private static void stepSim(float var0, float var1, float var2, float var3, float var4) {
      float var5 = var4 - 16.0F;
      float var6 = 10.0F;
      float var7 = var3 - 10.0F;

      for (int var8 = 0; var8 < 11; var8++) {
         FX[var8] = 0.0F;
         FY[var8] = 0.0F;
         OX[var8] = PX[var8];
         OY[var8] = PY[var8];
         if (gravityOn) {
            FY[var8] = FY[var8] + MASS[var8] * 2600.0F;
         }
      }

      applyGrabForce(var0, var1);
      float var12 = (float)Math.exp(-var2 * 0.2F);

      for (int var9 = 0; var9 < 11; var9++) {
         VX[var9] = (VX[var9] + FX[var9] / MASS[var9] * var2) * var12;
         VY[var9] = (VY[var9] + FY[var9] / MASS[var9] * var2) * var12;
         VX[var9] = clamp(VX[var9], -700.0F, 700.0F);
         VY[var9] = clamp(VY[var9], -700.0F, 700.0F);
         PX[var9] = PX[var9] + VX[var9] * var2;
         PY[var9] = PY[var9] + VY[var9] * var2;
      }

      cacheGrab();

      for (int var13 = 0; var13 < 10; var13++) {
         solveLinks(-1);
         collide(var5, var6, var7);
      }

      if (grab == 0) {
         limitJoints();
      }

      if (grab != 0) {
         nudgeGrabPoint(var0, var1);
         cacheGrab();
         stick = true;

         for (int var14 = 0; var14 < 6; var14++) {
            solveLinks(-1);
            collide(var5, var6, var7);
         }

         settleHang();

         for (int var15 = 0; var15 < 4; var15++) {
            solveLinks(-1);
            collide(var5, var6, var7);
         }

         stick = false;
      }

      boolean var16 = false;

      for (int var10 = 0; var10 < 11; var10++) {
         if (Float.isNaN(PX[var10]) || Float.isNaN(PY[var10]) || Float.isNaN(VX[var10]) || Float.isNaN(VY[var10])) {
            poseStand(spawnX, var4 - 16.0F);
            return;
         }

         VX[var10] = clamp((PX[var10] - OX[var10]) / var2, -900.0F, 900.0F);
         VY[var10] = clamp((PY[var10] - OY[var10]) / var2, -900.0F, 900.0F);
         if (PY[var10] >= var5 - 1.25F) {
            if (PY[var10] > var5) {
               PY[var10] = var5;
            }

            if (VY[var10] > 180.0F) {
               VY[var10] = -VY[var10] * 0.1F;
            } else if (VY[var10] > 0.0F) {
               VY[var10] = 0.0F;
            }

            VX[var10] = VX[var10] * 0.35F;
            var16 = true;
            if (Math.abs(VX[var10]) < 10.0F) {
               VX[var10] = 0.0F;
            }
         }

         if (PX[var10] < var6) {
            PX[var10] = var6;
            if (VX[var10] < 0.0F) {
               VX[var10] = 0.0F;
            }
         } else if (PX[var10] > var7) {
            PX[var10] = var7;
            if (VX[var10] > 0.0F) {
               VX[var10] = 0.0F;
            }
         }
      }

      if (grab != 0) {
         dampOrbit(var2);
      }

      float var17 = 0.0F;

      for (int var11 = 0; var11 < 11; var11++) {
         var17 = Math.max(var17, (float)Math.hypot(VX[var11], VY[var11]));
      }

      if (grab == 0 && var16 && var17 < 48.0F && maxGap() < 2.5F) {
         for (int var18 = 0; var18 < 11; var18++) {
            PX[var18] = OX[var18];
            PY[var18] = OY[var18];
            VX[var18] = 0.0F;
            VY[var18] = 0.0F;
         }

         sleeping = true;
      }
   }

   private static void applyGrabForce(float var0, float var1) {
      if (grab != 0) {
         boneEnds(grab);
         float var2 = clamp(grabT, 0.0F, 1.0F);
         float var3 = b0x + (b1x - b0x) * var2;
         float var4 = b0y + (b1y - b0y) * var2;
         float var5 = b0 >= 0 ? MASS[b0] : MASS[1] + MASS[2];
         float var6 = MASS[b1];
         float var7 = b0 >= 0 ? VX[b0] : VX[1];
         float var8 = b0 >= 0 ? VY[b0] : VY[1];
         float var9 = var7 * (1.0F - var2) + VX[b1] * var2;
         float var10 = var8 * (1.0F - var2) + VY[b1] * var2;
         float var11 = (var0 - var3) * 3200.0F - var9 * 650.0F;
         float var12 = (var1 - var4) * 3200.0F - var10 * 650.0F;
         float var13 = (float)Math.hypot(var11, var12);
         if (var13 > 65000.0F) {
            var11 *= 65000.0F / var13;
            var12 *= 65000.0F / var13;
         }

         if (!(var5 < 1.0E-4F) && !(var6 < 1.0E-4F)) {
            if (b0 >= 0) {
               FX[b0] = FX[b0] + var11 * (1.0F - var2);
               FY[b0] = FY[b0] + var12 * (1.0F - var2);
            } else {
               float var14 = MASS[1] / var5;
               FX[1] = FX[1] + var11 * (1.0F - var2) * var14;
               FY[1] = FY[1] + var12 * (1.0F - var2) * var14;
               FX[2] = FX[2] + var11 * (1.0F - var2) * (1.0F - var14);
               FY[2] = FY[2] + var12 * (1.0F - var2) * (1.0F - var14);
            }

            FX[b1] = FX[b1] + var11 * var2;
            FY[b1] = FY[b1] + var12 * var2;
         }
      }
   }

   private static void nudgeGrabPoint(float var0, float var1) {
      boneEnds(grab);
      float var2 = clamp(grabT, 0.0F, 1.0F);
      float var3 = b0x + (b1x - b0x) * var2;
      float var4 = b0y + (b1y - b0y) * var2;
      float var5 = var0 - var3;
      float var6 = var1 - var4;
      float var7 = (float)Math.hypot(var5, var6);
      if (!(var7 < 0.4F)) {
         float var8 = Math.min(var7, 5.0F);
         var5 *= var8 / var7;
         var6 *= var8 / var7;
         if (var2 >= 0.5F) {
            PX[b1] = PX[b1] + var5;
            PY[b1] = PY[b1] + var6;
         } else if (b0 >= 0) {
            PX[b0] = PX[b0] + var5;
            PY[b0] = PY[b0] + var6;
         } else {
            PX[1] = PX[1] + var5;
            PY[1] = PY[1] + var6;
            PX[2] = PX[2] + var5;
            PY[2] = PY[2] + var6;
         }
      }
   }

   private static void settleHang() {
      boneEnds(grab);
      float var0 = clamp(grabT, 0.0F, 1.0F);
      float var1 = b0x + (b1x - b0x) * var0;
      float var2 = 0.0F;
      float var3 = 0.0F;

      for (int var4 = 0; var4 < 11; var4++) {
         var3 += PX[var4] * MASS[var4];
         var2 += MASS[var4];
      }

      if (!(var2 < 0.001F)) {
         var3 /= var2;
         float var7 = (var1 - var3) * 0.25F;
         if (var7 > 4.0F) {
            var7 = 4.0F;
         } else if (var7 < -4.0F) {
            var7 = -4.0F;
         }

         for (int var5 = 0; var5 < 11; var5++) {
            if ((!(var0 >= 0.5F) || var5 != gB1) && (!(var0 < 0.5F) || var5 != gB0)) {
               PX[var5] = PX[var5] + var7;
            }
         }
      }
   }

   private static void dampOrbit(float var0) {
      if (grab != 0) {
         boneEnds(grab);
         float var1 = clamp(grabT, 0.0F, 1.0F);
         float var2 = b0x + (b1x - b0x) * var1;
         float var3 = b0y + (b1y - b0y) * var1;
         float var4 = 0.85F;

         for (int var5 = 0; var5 < 11; var5++) {
            float var6 = PX[var5] - var2;
            float var7 = PY[var5] - var3;
            float var8 = var6 * var6 + var7 * var7;
            if (!(var8 < 16.0F)) {
               float var9 = 1.0F / (float)Math.sqrt(var8);
               float var10 = -var7 * var9;
               float var11 = var6 * var9;
               float var12 = VX[var5] * var10 + VY[var5] * var11;
               VX[var5] = VX[var5] - var10 * var12 * var4;
               VY[var5] = VY[var5] - var11 * var12 * var4;
            }
         }
      }
   }

   private static void cacheGrab() {
      if (grab == 0) {
         gB0 = -2;
         gB1 = -2;
         gT = 0.0F;
      } else {
         boneEnds(grab);
         gB0 = b0;
         gB1 = b1;
         gT = clamp(grabT, 0.0F, 1.0F);
      }
   }

   private static float invM(int var0) {
      if (stick && grab != 0 && var0 == gB1 && gT >= 0.5F) {
         return 0.0F;
      }

      if (stick && grab != 0 && gB0 >= 0 && var0 == gB0 && gT < 0.5F) {
         return 0.0F;
      }

      float var1 = 1.0F;
      if (grab != 0) {
         if (var0 == gB1) {
            var1 += gT * 8.0F;
         }

         if (var0 == gB0) {
            var1 += (1.0F - gT) * 8.0F;
         }
      }

      return 1.0F / (MASS[var0] * var1);
   }

   private static void boneEnds(int var0) {
      anchors();
      if (var0 == 1) {
         b0x = PX[1];
         b0y = PY[1];
         b1x = PX[0];
         b1y = PY[0];
         b0 = 1;
         b1 = 0;
      } else if (var0 == 2) {
         b0x = PX[1];
         b0y = PY[1];
         b1x = PX[2];
         b1y = PY[2];
         b0 = 1;
         b1 = 2;
      } else if (var0 == 3) {
         b0x = rsx;
         b0y = rsy;
         b1x = PX[3];
         b1y = PY[3];
         b0 = -1;
         b1 = 3;
      } else if (var0 == 4) {
         b0x = PX[3];
         b0y = PY[3];
         b1x = PX[4];
         b1y = PY[4];
         b0 = 3;
         b1 = 4;
      } else if (var0 == 5) {
         b0x = lsx;
         b0y = lsy;
         b1x = PX[5];
         b1y = PY[5];
         b0 = -1;
         b1 = 5;
      } else if (var0 == 6) {
         b0x = PX[5];
         b0y = PY[5];
         b1x = PX[6];
         b1y = PY[6];
         b0 = 5;
         b1 = 6;
      } else if (var0 == 7) {
         b0x = rhx;
         b0y = rhy;
         b1x = PX[7];
         b1y = PY[7];
         b0 = -1;
         b1 = 7;
      } else if (var0 == 8) {
         b0x = PX[7];
         b0y = PY[7];
         b1x = PX[8];
         b1y = PY[8];
         b0 = 7;
         b1 = 8;
      } else if (var0 == 9) {
         b0x = lhx;
         b0y = lhy;
         b1x = PX[9];
         b1y = PY[9];
         b0 = -1;
         b1 = 9;
      } else {
         b0x = PX[9];
         b0y = PY[9];
         b1x = PX[10];
         b1y = PY[10];
         b0 = 9;
         b1 = 10;
      }
   }

   private static int pinOf(int var0) {
      if (var0 == 1) {
         return 0;
      } else if (var0 == 3) {
         return 3;
      } else if (var0 == 4) {
         return 4;
      } else if (var0 == 5) {
         return 5;
      } else if (var0 == 6) {
         return 6;
      } else if (var0 == 7) {
         return 7;
      } else if (var0 == 8) {
         return 8;
      } else if (var0 == 9) {
         return 9;
      } else {
         return var0 == 10 ? 10 : -1;
      }
   }

   private static boolean held(int var0, int var1) {
      return grab != 2 ? var0 == var1 : var0 == 1 || var0 == 2;
   }

   private static void anchors() {
      float var0 = PX[2] - PX[1];
      float var1 = PY[2] - PY[1];
      float var2 = (float)Math.hypot(var0, var1);
      float var3 = var2 < 0.001F ? 0.0F : 1.0F / var2;
      float var4 = var0 * var3;
      float var5 = var1 * var3;
      float var6 = -var5;
      float var7 = var4;
      float var8 = 6.0F * unit;
      float var9 = 2.0F * unit;
      rsx = PX[1] + var6 * var8;
      rsy = PY[1] + var7 * var8;
      lsx = PX[1] - var6 * var8;
      lsy = PY[1] - var7 * var8;
      rhx = PX[2] + var6 * var9;
      rhy = PY[2] + var7 * var9;
      lhx = PX[2] - var6 * var9;
      lhy = PY[2] - var7 * var9;
   }

   private static void solveLinks(int var0) {
      float var1 = unit;
      anchors();
      link(1, 2, 12.0F * var1, var0);
      link(0, 1, 6.0F * var1, var0);
      anchorLink(3, rsx, rsy, 6.0F * var1);
      link(4, 3, 6.0F * var1, var0);
      anchorLink(5, lsx, lsy, 6.0F * var1);
      link(6, 5, 6.0F * var1, var0);
      anchorLink(7, rhx, rhy, 6.0F * var1);
      link(8, 7, 6.0F * var1, var0);
      anchorLink(9, lhx, lhy, 6.0F * var1);
      link(10, 9, 6.0F * var1, var0);
   }

   private static void link(int var0, int var1, float var2, int var3) {
      float var4 = var3 == var0 ? 0.0F : invM(var0);
      float var5 = var3 == var1 ? 0.0F : invM(var1);
      float var6 = var4 + var5;
      if (!(var6 < 1.0E-5F)) {
         float var7 = PX[var1] - PX[var0];
         float var8 = PY[var1] - PY[var0];
         float var9 = (float)Math.hypot(var7, var8);
         if (var9 < 0.001F) {
            var7 = 0.0F;
            var8 = var2;
            var9 = var2;
         }

         float var10 = var9 - var2;
         if (!(Math.abs(var10) < 0.35F)) {
            float var11 = var10 / var9;
            float var12 = var7 * var11;
            float var13 = var8 * var11;
            float var14 = (float)Math.hypot(var12, var13);
            if (var14 > 6.0F) {
               var12 *= 6.0F / var14;
               var13 *= 6.0F / var14;
            }

            PX[var0] = PX[var0] + var12 * var4 / var6;
            PY[var0] = PY[var0] + var13 * var4 / var6;
            PX[var1] = PX[var1] - var12 * var5 / var6;
            PY[var1] = PY[var1] - var13 * var5 / var6;
         }
      }
   }

   private static void anchorLink(int var0, float var1, float var2, float var3) {
      float var4 = PX[var0] - var1;
      float var5 = PY[var0] - var2;
      float var6 = (float)Math.hypot(var4, var5);
      if (!(var6 < 0.001F)) {
         float var7 = var6 - var3;
         if (!(Math.abs(var7) < 0.35F)) {
            float var8 = var7 / var6;
            float var9 = invM(var0);
            float var10 = 1.0F / (MASS[1] + MASS[2]);
            if (grab >= 3) {
               var10 *= 4.0F;
            }

            float var11 = var9 + var10;
            float var12 = var4 * var8;
            float var13 = var5 * var8;
            float var14 = (float)Math.hypot(var12, var13);
            if (var14 > 6.0F) {
               var12 *= 6.0F / var14;
               var13 *= 6.0F / var14;
            }

            PX[var0] = PX[var0] - var12 * var9 / var11;
            PY[var0] = PY[var0] - var13 * var9 / var11;
            float var15 = var12 * var10 / var11;
            float var16 = var13 * var10 / var11;
            PX[1] = PX[1] + var15;
            PY[1] = PY[1] + var16;
            PX[2] = PX[2] + var15;
            PY[2] = PY[2] + var16;
         }
      }
   }

   private static void collide(float var0, float var1, float var2) {
      for (int var3 = 0; var3 < 11; var3++) {
         if (PY[var3] > var0) {
            PY[var3] = var0;
         }

         if (PX[var3] < var1) {
            PX[var3] = var1;
         }

         if (PX[var3] > var2) {
            PX[var3] = var2;
         }
      }
   }

   private static void limitJoints() {
      anchors();
      bend(rsx, rsy, 3, 4, -0.15F, 2.4F);
      bend(lsx, lsy, 5, 6, -2.4F, 0.15F);
      bend(rhx, rhy, 7, 8, -0.1F, 2.3F);
      bend(lhx, lhy, 9, 10, -2.3F, 0.1F);
      float var0 = PX[1] - PX[2];
      float var1 = PY[1] - PY[2];
      float var2 = PX[0] - PX[1];
      float var3 = PY[0] - PY[1];
      float var4 = (float)Math.hypot(var0, var1);
      float var5 = (float)Math.hypot(var2, var3);
      if (grab != 1) {
         if (var4 > 1.0F && var5 > 1.0F) {
            float var6 = (float)Math.atan2(var0 * var3 - var1 * var2, var0 * var2 + var1 * var3);
            float var7 = clamp(var6, -0.8F, 0.8F);
            if (var6 != var7) {
               float var8 = (float)Math.cos(var7);
               float var9 = (float)Math.sin(var7);
               float var10 = PX[1] + (var0 * var8 - var1 * var9) / var4 * var5;
               float var11 = PY[1] + (var0 * var9 + var1 * var8) / var4 * var5;
               PX[0] = PX[0] + (var10 - PX[0]) * 0.35F;
               PY[0] = PY[0] + (var11 - PY[0]) * 0.35F;
            }
         }
      }
   }

   private static void bend(float var0, float var1, int var2, int var3, float var4, float var5) {
      if (grab == 0 || var2 != gB0 && var2 != gB1 && var3 != gB0 && var3 != gB1) {
         float var6 = PX[var2] - var0;
         float var7 = PY[var2] - var1;
         float var8 = PX[var3] - PX[var2];
         float var9 = PY[var3] - PY[var2];
         float var10 = (float)Math.hypot(var6, var7);
         float var11 = (float)Math.hypot(var8, var9);
         if (!(var10 < 1.0F) && !(var11 < 1.0F)) {
            float var12 = (float)Math.atan2(var6 * var9 - var7 * var8, var6 * var8 + var7 * var9);
            float var13 = clamp(var12, var4, var5);
            if (var12 != var13) {
               float var14 = (float)Math.cos(var13);
               float var15 = (float)Math.sin(var13);
               float var16 = PX[var2] + (var6 * var14 - var7 * var15) / var10 * var11;
               float var17 = PY[var2] + (var6 * var15 + var7 * var14) / var10 * var11;
               float var18 = (var16 - PX[var3]) * 0.35F;
               float var19 = (var17 - PY[var3]) * 0.35F;
               float var20 = (float)Math.hypot(var18, var19);
               if (var20 > 5.0F) {
                  var18 *= 5.0F / var20;
                  var19 *= 5.0F / var20;
               }

               PX[var3] = PX[var3] + var18;
               PY[var3] = PY[var3] + var19;
            }
         }
      }
   }

   private static float maxGap() {
      anchors();
      float var0 = unit;
      float var1 = gap(PX[1], PY[1], PX[2], PY[2], 12.0F * var0);
      var1 = Math.max(var1, gap(PX[0], PY[0], PX[1], PY[1], 6.0F * var0));
      var1 = Math.max(var1, gap(PX[3], PY[3], rsx, rsy, 6.0F * var0));
      var1 = Math.max(var1, gap(PX[4], PY[4], PX[3], PY[3], 6.0F * var0));
      var1 = Math.max(var1, gap(PX[5], PY[5], lsx, lsy, 6.0F * var0));
      var1 = Math.max(var1, gap(PX[6], PY[6], PX[5], PY[5], 6.0F * var0));
      var1 = Math.max(var1, gap(PX[7], PY[7], rhx, rhy, 6.0F * var0));
      var1 = Math.max(var1, gap(PX[8], PY[8], PX[7], PY[7], 6.0F * var0));
      var1 = Math.max(var1, gap(PX[9], PY[9], lhx, lhy, 6.0F * var0));
      return Math.max(var1, gap(PX[10], PY[10], PX[9], PY[9], 6.0F * var0));
   }

   private static float gap(float var0, float var1, float var2, float var3, float var4) {
      return Math.abs((float)Math.hypot(var0 - var2, var1 - var3) - var4);
   }

   private static int pickBone(float var0, float var1) {
      if (!ragReady) {
         return 0;
      }

      anchors();
      boneBest = 1.0E9F;
      int var2 = 0;
      float var3 = 12.0F * Math.max(1.0F, unit * 0.7F);
      var2 = nearerBone(var2, 1, var0, var1, PX[1], PY[1], PX[0], PY[0], var3);
      var2 = nearerBone(var2, 2, var0, var1, PX[1], PY[1], PX[2], PY[2], var3);
      var2 = nearerBone(var2, 3, var0, var1, rsx, rsy, PX[3], PY[3], var3);
      var2 = nearerBone(var2, 4, var0, var1, PX[3], PY[3], PX[4], PY[4], var3);
      var2 = nearerBone(var2, 5, var0, var1, lsx, lsy, PX[5], PY[5], var3);
      var2 = nearerBone(var2, 6, var0, var1, PX[5], PY[5], PX[6], PY[6], var3);
      var2 = nearerBone(var2, 7, var0, var1, rhx, rhy, PX[7], PY[7], var3);
      var2 = nearerBone(var2, 8, var0, var1, PX[7], PY[7], PX[8], PY[8], var3);
      var2 = nearerBone(var2, 9, var0, var1, lhx, lhy, PX[9], PY[9], var3);
      return nearerBone(var2, 10, var0, var1, PX[9], PY[9], PX[10], PY[10], var3);
   }

   private static int nearerBone(int var0, int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = distToSeg(var2, var3, var4, var5, var6, var7);
      if (!(var9 < var8) || var0 != 0 && !(var9 < boneBest)) {
         if (var0 == 0) {
            boneBest = 1.0E9F;
         }

         return var0;
      } else {
         boneBest = var9;
         bonePick = var1;
         return var1;
      }
   }

   private static int controlAt(float var0, float var1) {
      if (!ragReady) {
         return -1;
      }

      float var2 = 78.0F * uiScale;
      float var3 = 16.0F * uiScale;
      float var4 = 3.0F * uiScale;
      float var5 = PX[1] - 10.0F * unit - var2;
      if (var5 < 8.0F) {
         var5 = 8.0F;
      }

      float var6 = PY[1] - var3;

      for (int var7 = 0; var7 < 6; var7++) {
         float var8 = var6 + var7 * (var3 + var4);
         if (var0 >= var5 && var0 <= var5 + var2 && var1 >= var8 && var1 <= var8 + var3) {
            return var7;
         }
      }

      return -1;
   }

   private static void drawControls(MenuContext var0, ImDrawList var1, float var2) {
      if (ragReady && var1 != null) {
         float var3 = 78.0F * uiScale;
         float var4 = 16.0F * uiScale;
         float var5 = 3.0F * uiScale;
         float var6 = PX[1] - 10.0F * unit - var3;
         if (var6 < 8.0F) {
            var6 = 8.0F;
         }

         float var7 = PY[1] - var4;
         ImFont var8 = var0.tagFont();
         if (var8 == null) {
            var8 = var0.menuFont();
         }

         String[] var9 = new String[]{
            "Reset", ragdoll ? "Physics" : "Posed", frozen ? "Frozen" : "Freeze", "Throw", gravityOn ? "Gravity" : "No grav", "Respawn"
         };
         float var10 = var0.mouseX();
         float var11 = var0.mouseY();

         for (int var12 = 0; var12 < 6; var12++) {
            float var13 = var7 + var12 * (var4 + var5);
            boolean var14 = var10 >= var6 && var10 <= var6 + var3 && var11 >= var13 && var11 <= var13 + var4;
            boolean var15 = var12 == 1 && ragdoll || var12 == 2 && frozen || var12 == 4 && gravityOn;
            int var16 = var14 ? EgyptDraw.col(-14494465, var2 * 0.28F) : EgyptDraw.col(-16117736, var2 * 0.88F);
            if (var15 && !var14) {
               var16 = EgyptDraw.col(-14494465, var2 * 0.16F);
            }

            var1.addRectFilled(var6, var13, var6 + var3, var13 + var4, var16, 4.0F);
            var1.addRect(var6, var13, var6 + var3, var13 + var4, EgyptDraw.col(-14494465, var2 * (var14 ? 0.9F : 0.45F)), 4.0F, 0, 1.0F);
            float var17 = var0.textWidth(var8, var9[var12]);
            var0.text(
               var1, var8, var6 + (var3 - var17) * 0.5F, var13 + (var4 - var0.textHeight(var8)) * 0.5F, EgyptDraw.col(-1, var2), uiScale * 0.72F, var9[var12]
            );
         }
      }
   }

   private static void drawRagdoll(ImDrawList var0, int var1, boolean var2, float var3) {
      anchors();
      faceCount = 0;
      float var4 = (float)Math.toRadians(yaw);
      int var5 = var2 ? 3 : 4;
      float var6 = unit;
      boneBox(0, 0, 8, 8, 8, PX[1], PY[1], PX[0], PY[0], 8.0F * var6, 8.0F * var6, false, 1, var4, var3, 0.2F);
      boneBox(16, 16, 8, 12, 4, PX[1], PY[1], PX[2], PY[2], 8.0F * var6, 4.0F * var6, true, 2, var4, var3, 0.0F);
      halfLimb(40, 16, var5, 4, rsx, rsy, PX[3], PY[3], PX[4], PY[4], var5 * var6, 4.0F * var6, 3, 4, var4, var3);
      halfLimb(32, 48, var5, 4, lsx, lsy, PX[5], PY[5], PX[6], PY[6], var5 * var6, 4.0F * var6, 5, 6, var4, var3);
      halfLimb(0, 16, 4, 4, rhx, rhy, PX[7], PY[7], PX[8], PY[8], 4.0F * var6, 4.0F * var6, 7, 8, var4, var3);
      halfLimb(16, 48, 4, 4, lhx, lhy, PX[9], PY[9], PX[10], PY[10], 4.0F * var6, 4.0F * var6, 9, 10, var4, var3);
      flushFaces(var0, var1);
      int var7 = grab != 0 ? grab : hoverPart;
      if (var7 != 0) {
         strokeBone(var0, var7, var3, grab != 0);
      }
   }

   private static void strokeBone(ImDrawList var0, int var1, float var2, boolean var3) {
      float var4 = 0.0F;
      float var5 = 0.0F;
      float var6 = 0.0F;
      float var7 = 0.0F;
      if (var1 == 1) {
         var4 = PX[1];
         var5 = PY[1];
         var6 = PX[0];
         var7 = PY[0];
      } else if (var1 == 2) {
         var4 = PX[1];
         var5 = PY[1];
         var6 = PX[2];
         var7 = PY[2];
      } else if (var1 == 3) {
         var4 = rsx;
         var5 = rsy;
         var6 = PX[3];
         var7 = PY[3];
      } else if (var1 == 4) {
         var4 = PX[3];
         var5 = PY[3];
         var6 = PX[4];
         var7 = PY[4];
      } else if (var1 == 5) {
         var4 = lsx;
         var5 = lsy;
         var6 = PX[5];
         var7 = PY[5];
      } else if (var1 == 6) {
         var4 = PX[5];
         var5 = PY[5];
         var6 = PX[6];
         var7 = PY[6];
      } else if (var1 == 7) {
         var4 = rhx;
         var5 = rhy;
         var6 = PX[7];
         var7 = PY[7];
      } else if (var1 == 8) {
         var4 = PX[7];
         var5 = PY[7];
         var6 = PX[8];
         var7 = PY[8];
      } else if (var1 == 9) {
         var4 = lhx;
         var5 = lhy;
         var6 = PX[9];
         var7 = PY[9];
      } else {
         var4 = PX[9];
         var5 = PY[9];
         var6 = PX[10];
         var7 = PY[10];
      }

      int var8 = EgyptDraw.col(-14494465, var2 * (var3 ? 0.95F : 0.55F));
      var0.addLine(var4, var5, var6, var7, var8, var3 ? 3.2F : 1.8F);
   }

   private static void halfLimb(
      int var0,
      int var1,
      int var2,
      int var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      int var12,
      int var13,
      float var14,
      float var15
   ) {
      boneBox(var0, var1, var2, 6, var3, var4, var5, var6, var7, var10, var11, true, var12, var14, var15, 0.05F);
      boneBox(var0, var1 + 6, var2, 6, var3, var6, var7, var8, var9, var10, var11, true, var13, var14, var15, 0.08F);
   }

   private static void boneBox(
      int var0,
      int var1,
      int var2,
      int var3,
      int var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      boolean var11,
      int var12,
      float var13,
      float var14,
      float var15
   ) {
      float var16 = var7 - var5;
      float var17 = var8 - var6;
      float var18 = (float)Math.hypot(var16, var17);
      if (!(var18 < 0.5F)) {
         float var19 = 1.0F / var18;
         float var20 = var16 * var19;
         float var21 = var17 * var19;
         float var22 = -var21;
         float var23 = var20;
         float var24 = var9 * 0.5F;
         float var25 = var10 * 0.5F;
         float var26 = (float)Math.cos(var13);
         float var27 = (float)Math.sin(var13);
         boolean var28 = var12 == hoverPart || var12 == grab;
         if (var26 > 0.12F) {
            faceLocal(
               var0 + var4,
               var1 + var4,
               var0 + var4 + var2,
               var1 + var4 + var3,
               var5,
               var6,
               var20,
               var21,
               var22,
               var23,
               0.0F,
               var18,
               -var24,
               var24,
               var25,
               var26,
               var27,
               var14,
               1.0F,
               var15,
               var28,
               var11
            );
         }

         if (var26 < -0.12F) {
            faceLocal(
               var0 + var4 + var2 + var4,
               var1 + var4,
               var0 + var4 + var2 + var4 + var2,
               var1 + var4 + var3,
               var5,
               var6,
               var20,
               var21,
               var22,
               var23,
               0.0F,
               var18,
               var24,
               -var24,
               -var25,
               var26,
               var27,
               var14,
               0.62F,
               var15,
               var28,
               var11
            );
         }

         if (var27 > 0.12F) {
            faceLocal(
               var0 + var4 + var2,
               var1 + var4,
               var0 + var4 + var2 + var4,
               var1 + var4 + var3,
               var5,
               var6,
               var20,
               var21,
               var22,
               var23,
               0.0F,
               var18,
               var25,
               -var25,
               var24,
               var26,
               var27,
               var14,
               0.78F,
               var15 + 0.02F,
               var28,
               var11
            );
         }

         if (var27 < -0.12F) {
            faceLocal(
               var0,
               var1 + var4,
               var0 + var4,
               var1 + var4 + var3,
               var5,
               var6,
               var20,
               var21,
               var22,
               var23,
               0.0F,
               var18,
               -var25,
               var25,
               -var24,
               var26,
               var27,
               var14,
               0.78F,
               var15 + 0.02F,
               var28,
               var11
            );
         }
      }
   }

   private static void faceLocal(
      int var0,
      int var1,
      int var2,
      int var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18,
      float var19,
      boolean var20,
      boolean var21
   ) {
      float var22 = var14;
      put(0, var4, var5, var6, var7, var8, var9, var10, var12, var22, var15, var16);
      put(1, var4, var5, var6, var7, var8, var9, var11, var12, var22, var15, var16);
      put(2, var4, var5, var6, var7, var8, var9, var11, var13, var22, var15, var16);
      put(3, var4, var5, var6, var7, var8, var9, var10, var13, var22, var15, var16);
      float var23 = (AX[1] - AX[0]) * (AY[2] - AY[0]) - (AY[1] - AY[0]) * (AX[2] - AX[0]);
      if (!(var23 <= 1.2F)) {
         if (faceCount < 96) {
            int var24 = faceCount++;
            SZ[var24] = (AY[0] + AY[1] + AY[2] + AY[3]) * 0.25F + var19 * 40.0F;
            int var25 = var24 * 4;
            float var26 = 0.015625F;
            float var27 = var0 * var26;
            float var28 = var2 * var26;
            float var29 = var1 * var26;
            float var30 = var3 * var26;
            if (!var21) {
               float var31 = var29;
               var29 = var30;
               var30 = var31;
            }

            SX[var25] = AX[0];
            SY[var25] = AY[0];
            SU[var25] = var27;
            SV[var25] = var29;
            SX[var25 + 1] = AX[1];
            SY[var25 + 1] = AY[1];
            SU[var25 + 1] = var27;
            SV[var25 + 1] = var30;
            SX[var25 + 2] = AX[2];
            SY[var25 + 2] = AY[2];
            SU[var25 + 2] = var28;
            SV[var25 + 2] = var30;
            SX[var25 + 3] = AX[3];
            SY[var25 + 3] = AY[3];
            SU[var25 + 3] = var28;
            SV[var25 + 3] = var29;
            int var33 = Math.max(0, Math.min(255, Math.round(255.0F * var18)));
            SC[var24] = EgyptDraw.col(0xFF000000 | var33 << 16 | var33 << 8 | var33, var17);
            if (var20) {
               int var32 = EgyptDraw.col(-14494465, var17 * 0.95F);
               hotX0 = AX[0];
               hotY0 = AY[0];
               hotX1 = AX[1];
               hotY1 = AY[1];
               hotX2 = AX[2];
               hotY2 = AY[2];
               hotX3 = AX[3];
               hotY3 = AY[3];
               hotGlow = var32;
               hotOn = true;
            }
         }
      }
   }

   private static void put(
      int var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11
   ) {
      float var12 = var8 * var10 + var9 * var11;
      AX[var0] = var1 + var3 * var7 + var5 * var12;
      AY[var0] = var2 + var4 * var7 + var6 * var12;
   }

   private static float mouseWheel() {
      try {
         Object var0 = ImGui.class.getMethod("getIO").invoke(null);
         Object var1 = var0.getClass().getMethod("getMouseWheel").invoke(var0);
         if (var1 instanceof Float) {
            return (Float)var1;
         }

         if (var1 instanceof Double) {
            return ((Double)var1).floatValue();
         }
      } catch (Throwable var2) {
      }

      return 0.0F;
   }

   private static void stepPhysicsLegacyStart(MenuContext var0, float var1, float var2, float var3) {
      float var4 = var0.mouseX();
      float var5 = var0.mouseY();
      boolean var6 = mouseDown();
      boolean var7 = var6 && !prevDown;
      if (grab != 0 && !var6) {
         grab = 0;
         held = false;
      }

      if (grab == 0 && var7) {
         int var8 = pickPart(var4, var5);
         if (var8 != 0) {
            grab = var8;
            held = true;
            if (var8 == 1) {
               grabOX = var4 - dollX;
               grabOY = var5 - dollY;
               pitchVel = 0.0F;
               yawVel = 0.0F;
               vy = 0.0F;
               vx = 0.0F;
            } else {
               comReady = false;
            }
         }
      }

      if (grab == 0) {
         comReady = false;
      }

      if (grab == 1 && !var7) {
         float var13 = var4 - prevMx;
         yaw += var13 * 2.2F;
         yawVel = clamp(var13 / var3 * 2.2F, -1600.0F, 1600.0F);
      } else if (grab != 0) {
         yaw = yaw + yawVel * var3;
         yawVel = yawVel * (float)Math.exp(-var3 * 4.0F);
      }

      pitch = pitch + (0.0F - pitch) * Math.min(1.0F, var3 * 3.0F);
      if (grab == 1) {
         float var14 = var4 - grabOX;
         float var9 = var5 - grabOY;
         float var10 = var14 - dollX;
         float var11 = var9 - dollY;
         float var12 = 1.0F / var3;
         vx = clamp(var10 * var12, -1600.0F, 1600.0F);
         vy = clamp(var11 * var12, -1600.0F, 1600.0F);
         dollX = var14;
         dollY = var9;
      } else if (grab != 0) {
         holdPoint(var4, var5, var3, var2, grab);
      } else {
         boolean var15 = dollY < var2 - 20.0F || vy < -40.0F;
         if (var15) {
            vy += 2800.0F * var3;
         }

         dollX = dollX + vx * var3;
         dollY = dollY + vy * var3;
         yaw = yaw + yawVel * var3;
         bodyRoll = bodyRoll + bodyRollVel * var3;

         while (bodyRoll > (float) Math.PI) {
            bodyRoll -= (float) (Math.PI * 2);
         }

         while (bodyRoll < (float) -Math.PI) {
            bodyRoll += (float) (Math.PI * 2);
         }

         vx = vx * (float)Math.exp(-var3 * 0.45);
         vy = vy * (float)Math.exp(-var3 * 0.18);
         yawVel = yawVel * (float)Math.exp(-var3 * 0.28);
         bodyRollVel = bodyRollVel * (float)Math.exp(-var3 * 0.22);
         if (!var15) {
            bodyRollVel = bodyRollVel + (-7.0F * bodyRoll - 2.8F * bodyRollVel) * var3;
         }

         if (Math.abs(vx) < 8.0F) {
            vx = 0.0F;
         }

         if (!var15 && Math.abs(vy) < 90.0F) {
            vy = 0.0F;
         }

         if (Math.abs(yawVel) < 8.0F) {
            yawVel = 0.0F;
         }

         bounce(var1, var2);
      }

      stepLimbs(var3);
      if (grab == 0 || grab == 1) {
         dollX = clamp(dollX, 20.0F, Math.max(21.0F, var1 - 20.0F));
         dollY = clamp(dollY, 18.0F, var2 - 12.0F);
      }

      prevMx = var4;
      prevDown = var6;
   }

   private static void bounce(float var0, float var1) {
      float var2 = 28.0F;
      float var3 = var0 - 28.0F;
      if (dollX < var2) {
         dollX = var2;
         vx = Math.abs(vx) * 0.62F;
         yawVel += 180.0F;
         bodyRollVel += 2.4F;
         rightSpreadVel += 4.8F;
         leftSpreadVel -= 4.4F;
         rightArmVel += 3.2F;
         leftArmVel -= 2.8F;
         rightLegSpreadVel += 2.2F;
         leftLegSpreadVel -= 2.0F;
      } else if (dollX > var3) {
         dollX = var3;
         vx = -Math.abs(vx) * 0.62F;
         yawVel -= 180.0F;
         bodyRollVel -= 2.4F;
         rightSpreadVel -= 4.8F;
         leftSpreadVel += 4.4F;
         rightArmVel -= 3.2F;
         leftArmVel += 2.8F;
         rightLegSpreadVel -= 2.2F;
         leftLegSpreadVel += 2.0F;
      }

      float var4 = 12.0F;
      float var5 = var1 - 12.0F;
      if (dollY < var4) {
         dollY = var4;
         vy = Math.abs(vy) * 0.45F;
         rightSpreadVel -= 2.4F;
         leftSpreadVel += 2.2F;
         rightLegSpreadVel += 1.8F;
         leftLegSpreadVel -= 1.6F;
      } else if (dollY > var5) {
         dollY = var5;
         if (Math.abs(vy) < 260.0F) {
            vy = 0.0F;
         } else {
            vy = -Math.abs(vy) * 0.48F;
         }

         yawVel *= 0.9F;
         bodyRollVel = bodyRollVel + (vx * 0.004F + (vy == 0.0F ? 0.0F : 3.2F));
         rightSpreadVel += 7.5F;
         leftSpreadVel -= 5.1F;
         rightArmVel += 3.6F;
         leftArmVel -= 3.2F;
         rightLegSpreadVel -= 3.4F;
         leftLegSpreadVel += 3.1F;
      }
   }

   private static void stepLimbs(float var0) {
      float var1 = (float)Math.toRadians(yaw);
      float var2 = (float)Math.toRadians(pitch);
      float var3 = vx * (float)Math.sin(var1) + vy * (float)(Math.cos(var1) * Math.sin(var2));
      float var4 = vx * (float)Math.cos(var1);
      float var5 = var3 - prevFwd;
      float var6 = var4 - prevSide;
      float var7 = vy - prevVy;
      prevFwd = var3;
      prevSide = var4;
      prevVx = vx;
      prevVy = vy;
      float var8 = grab == 0 ? 0.0F : clamp(-bodyRoll, -1.1F, 1.1F);
      float var9 = grab == 0 ? 0.0F : clamp(-bodyRoll * 0.45F, -0.7F, 0.7F);
      if (grab != 0) {
         float var10 = Math.min(1.0F, var0 * 5.0F);
         if (grab != 2) {
            rightSpread = rightSpread + (var8 - rightSpread) * var10;
            rightArm = rightArm + (var8 * 0.45F - rightArm) * var10;
            rightArmVel = 0.0F;
            rightSpreadVel = 0.0F;
         }

         if (grab != 3) {
            leftSpread = leftSpread + (var8 - leftSpread) * var10;
            leftArm = leftArm + (var8 * 0.45F - leftArm) * var10;
            leftArmVel = 0.0F;
            leftSpreadVel = 0.0F;
         }

         if (grab != 4) {
            rightLegSpread = rightLegSpread + (var9 - rightLegSpread) * var10;
            rightLeg = rightLeg + (var9 - rightLeg) * var10;
            rightLegVel = 0.0F;
            rightLegSpreadVel = 0.0F;
         }

         if (grab != 5) {
            leftLegSpread = leftLegSpread + (var9 - leftLegSpread) * var10;
            leftLeg = leftLeg + (var9 - leftLeg) * var10;
            leftLegVel = 0.0F;
            leftLegSpreadVel = 0.0F;
         }
      } else {
         if (grab != 2) {
            stepAng(rightSpread, rightSpreadVel, var0, 1.7F, 0.85F, 0.0052F, var6 + var7 * 0.4F, var8, -1.7F, 1.7F);
            rightSpread = outA;
            rightSpreadVel = outV;
            stepAng(rightArm, rightArmVel, var0, 1.3F, 0.7F, 0.006F, var5, var8 * 0.8F, -1.2F, 1.2F);
            rightArm = outA;
            rightArmVel = outV;
         }

         if (grab != 3) {
            stepAng(leftSpread, leftSpreadVel, var0, 1.7F, 0.85F, 0.0052F, var6 + var7 * 0.4F, var8, -1.7F, 1.7F);
            leftSpread = outA;
            leftSpreadVel = outV;
            stepAng(leftArm, leftArmVel, var0, 1.3F, 0.7F, 0.006F, var5, var8 * 0.8F, -1.2F, 1.2F);
            leftArm = outA;
            leftArmVel = outV;
         }

         if (grab != 4) {
            stepAng(rightLegSpread, rightLegSpreadVel, var0, 3.2F, 1.4F, 0.003F, var6 * 0.7F + var7 * 0.25F, var9, -1.2F, 1.2F);
            rightLegSpread = outA;
            rightLegSpreadVel = outV;
            stepAng(rightLeg, rightLegVel, var0, 2.6F, 1.2F, 0.0034F, var5, var9, -1.05F, 1.05F);
            rightLeg = outA;
            rightLegVel = outV;
         }

         if (grab != 5) {
            stepAng(leftLegSpread, leftLegSpreadVel, var0, 3.2F, 1.4F, 0.003F, var6 * 0.7F + var7 * 0.25F, var9, -1.2F, 1.2F);
            leftLegSpread = outA;
            leftLegSpreadVel = outV;
            stepAng(leftLeg, leftLegVel, var0, 2.6F, 1.2F, 0.0034F, var5, var9, -1.05F, 1.05F);
            leftLeg = outA;
            leftLegVel = outV;
         }
      }
   }

   private static void stepAng(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      var1 += -var6 * var5;
      var1 += (-var3 * (var0 - var7) - var4 * var1) * var2;
      var1 = clamp(var1, -9.0F, 9.0F);
      var0 += var1 * var2;
      outA = clamp(var0, var8, var9);
      outV = var1;
   }

   private static void holdPoint(float var0, float var1, float var2, float var3, int var4) {
      float var5 = wrapDeg(-yaw);
      yaw = yaw + var5 * Math.min(1.0F, var2 * 5.0F);
      yawVel = 0.0F;
      pitch = pitch + (0.0F - pitch) * Math.min(1.0F, var2 * 8.0F);
      if (!comReady) {
         comX = screenX(0.0F, 16.0F, 0.0F);
         comY = screenY(0.0F, 16.0F, 0.0F);
         comVx = vx;
         comVy = vy;
         hangBlend = 0.0F;
         hangRoll0 = bodyRoll;
         hangSpread0 = var4 == 6 ? 0.0F : spreadOf(var4);
         comReady = true;
      }

      float var6 = hangSpread(var4);
      if (var4 != 6) {
         setLimb(var4, var6, 0.0F);
      }

      loadPin(var4);
      float var7 = rawX(tipX, tipZ) - rawX(0.0F, 0.0F);
      float var8 = rawY(tipX, tipY, tipZ) - rawY(0.0F, 16.0F, 0.0F);
      float var9 = (float)Math.hypot(var7, var8);
      if (var9 < 8.0F) {
         var9 = 8.0F;
      }

      float var10 = (float)Math.atan2(var7, var8);
      float var11 = Math.max(var2, 0.001F);
      if (hangBlend < 1.0F) {
         hangBlend = Math.min(1.0F, hangBlend + var2 * 3.2F);
         float var24 = hangBlend * hangBlend * (3.0F - 2.0F * hangBlend);
         if (var4 != 6) {
            setLimb(var4, hangSpread0 + (var6 - hangSpread0) * var24, 0.0F);
         }

         bodyRoll = lerpAngle(hangRoll0, wrap((float) Math.PI - var10), var24);
         bodyRollVel = 0.0F;
         loadPin(var4);
         dollX = dollX + (var0 - screenX(tipX, tipY, tipZ));
         dollY = dollY + (var1 - screenY(tipX, tipY, tipZ));
         float var25 = comX;
         float var26 = comY;
         comX = screenX(0.0F, 16.0F, 0.0F);
         comY = screenY(0.0F, 16.0F, 0.0F);
         comVx = clamp((comX - var25) / var11, -1400.0F, 1400.0F);
         comVy = clamp((comY - var26) / var11, -1400.0F, 1400.0F);
         vx = comVx;
         vy = comVy;
      } else {
         float var12 = comX;
         float var13 = comY;
         comVy += 2800.0F * var2;
         comX = comX + comVx * var2;
         comY = comY + comVy * var2;
         float var14 = comX - var0;
         float var15 = comY - var1;
         float var16 = (float)Math.hypot(var14, var15);
         if (var16 < 1.0F) {
            var14 = 0.0F;
            var15 = var9;
            var16 = var9;
         }

         float var17 = var14 / var16;
         float var18 = var15 / var16;
         comX = var0 + var17 * var9;
         comY = var1 + var18 * var9;
         comVx = (comX - var12) / var11;
         comVy = (comY - var13) / var11;
         float var19 = comVx * var17 + comVy * var18;
         comVx -= var19 * var17;
         comVy -= var19 * var18;
         float var20 = (float)Math.exp(-var2 * 0.35);
         comVx = clamp(comVx * var20, -1400.0F, 1400.0F);
         comVy = clamp(comVy * var20, -1400.0F, 1400.0F);
         float var21 = (float)Math.atan2(var0 - comX, var1 - comY);
         float var22 = wrap(var21 - var10);
         bodyRollVel = clamp(wrap(var22 - bodyRoll) / var11, -6.0F, 6.0F);
         bodyRoll = var22;
         float var23 = (float)Math.cos(Math.toRadians(pitch));
         dollX = comX;
         dollY = comY + 16.0F * var23 * unit;
         vx = comVx;
         vy = comVy;
      }
   }

   private static float hangSpread(int var0) {
      if (var0 != 6 && var0 != 4 && var0 != 5) {
         float var1 = jointOf(var0)[0];
         float var2 = clamp(-var1 / 12.0F, -1.0F, 1.0F);
         float var3 = -((float)Math.sqrt(Math.max(0.0F, 1.0F - var2 * var2)));
         return (float)Math.atan2(var2, var3);
      } else {
         return 0.0F;
      }
   }

   private static void loadPin(int var0) {
      if (var0 == 6) {
         tipX = 0.0F;
         tipY = 30.0F;
         tipZ = 0.0F;
      } else {
         float[] var1 = jointOf(var0);
         limbTip(var1[0], var1[1], spreadOf(var0), 0.0F);
      }
   }

   private static float lerpAngle(float var0, float var1, float var2) {
      return wrap(var0 + wrap(var1 - var0) * var2);
   }

   private static float wrapDeg(float var0) {
      while (var0 > 180.0F) {
         var0 -= 360.0F;
      }

      while (var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   private static void setLimb(int var0, float var1, float var2) {
      if (var0 == 2) {
         rightSpread = var1;
         rightSpreadVel = 0.0F;
         rightArm = var2;
         rightArmVel = 0.0F;
      } else if (var0 == 3) {
         leftSpread = var1;
         leftSpreadVel = 0.0F;
         leftArm = var2;
         leftArmVel = 0.0F;
      } else if (var0 == 4) {
         rightLegSpread = var1;
         rightLegSpreadVel = 0.0F;
         rightLeg = var2;
         rightLegVel = 0.0F;
      } else if (var0 == 5) {
         leftLegSpread = var1;
         leftLegSpreadVel = 0.0F;
         leftLeg = var2;
         leftLegVel = 0.0F;
      }
   }

   private static float wrap(float var0) {
      while (var0 > (float) Math.PI) {
         var0 -= (float) (Math.PI * 2);
      }

      while (var0 < (float) -Math.PI) {
         var0 += (float) (Math.PI * 2);
      }

      return var0;
   }

   private static float unspinX(float var0, float var1) {
      float var2 = var0 - dollX;
      float var3 = var1 - pivotY();
      float var4 = (float)Math.cos(bodyRoll);
      float var5 = (float)Math.sin(bodyRoll);
      return dollX + var2 * var4 + var3 * var5;
   }

   private static float unspinY(float var0, float var1) {
      float var2 = var0 - dollX;
      float var3 = var1 - pivotY();
      float var4 = (float)Math.cos(bodyRoll);
      float var5 = (float)Math.sin(bodyRoll);
      return pivotY() - var2 * var5 + var3 * var4;
   }

   private static float spreadOf(int var0) {
      if (var0 == 2) {
         return rightSpread;
      } else if (var0 == 3) {
         return leftSpread;
      } else {
         return var0 == 4 ? rightLegSpread : leftLegSpread;
      }
   }

   private static float swingOf(int var0) {
      if (var0 == 2) {
         return rightArm;
      } else if (var0 == 3) {
         return leftArm;
      } else {
         return var0 == 4 ? rightLeg : leftLeg;
      }
   }

   private static void limbTip(float var0, float var1, float var2, float var3) {
      float var4 = (float)Math.cos(-var3);
      float var5 = (float)Math.sin(-var3);
      float var6 = (float)Math.cos(var2);
      float var7 = (float)Math.sin(var2);
      float var8 = -12.0F * var4;
      float var9 = -12.0F * var5;
      tipX = var0 - var8 * var7;
      tipY = var1 + var8 * var6;
      tipZ = var9;
   }

   private static void pinLimb(int var0, float var1, float var2) {
      if (var0 == 2) {
         rightSpreadVel = (var1 - rightSpread) / var2;
         rightSpread = var1;
         rightArmVel = (0.0F - rightArm) / var2;
         rightArm = 0.0F;
      } else if (var0 == 3) {
         leftSpreadVel = (var1 - leftSpread) / var2;
         leftSpread = var1;
         leftArmVel = (0.0F - leftArm) / var2;
         leftArm = 0.0F;
      } else if (var0 == 4) {
         rightLegSpreadVel = (var1 - rightLegSpread) / var2;
         rightLegSpread = var1;
         rightLegVel = (0.0F - rightLeg) / var2;
         rightLeg = 0.0F;
      } else if (var0 == 5) {
         leftLegSpreadVel = (var1 - leftLegSpread) / var2;
         leftLegSpread = var1;
         leftLegVel = (0.0F - leftLeg) / var2;
         leftLeg = 0.0F;
      }
   }

   private static float[] jointOf(int var0) {
      float var1 = lastSlim ? 3.0F : 4.0F;
      float var2 = (lastSlim ? -7.0F : -8.0F) + var1 * 0.5F;
      float var3 = 4.0F + var1 * 0.5F;
      if (var0 == 2) {
         return new float[]{var2, 24.0F, 0.0F};
      } else if (var0 == 3) {
         return new float[]{var3, 24.0F, 0.0F};
      } else {
         return var0 == 4 ? new float[]{-2.0F, 12.0F, 0.0F} : new float[]{2.0F, 12.0F, 0.0F};
      }
   }

   private static float aimSpread(float var0, float var1, float var2, float var3, float var4) {
      float var5 = (var0 - screenX(var2, var3, var4)) / unit;
      float var6 = (var1 - screenY(var2, var3, var4)) / unit;
      float var7 = (float)Math.cos(Math.toRadians(yaw));
      float var8 = (float)Math.cos(Math.toRadians(pitch));
      if (Math.abs(var7) > 0.3F) {
         var5 /= var7;
      }

      if (Math.abs(var8) > 0.3F) {
         var6 /= var8;
      }

      return clamp((float)Math.atan2(var5, var6), -3.05F, 3.05F);
   }

   private static int pickPart(float var0, float var1) {
      float var2 = lastSlim ? 3.0F : 4.0F;
      float var3 = (lastSlim ? -7.0F : -8.0F) + var2 * 0.5F;
      float var4 = 4.0F + var2 * 0.5F;
      float var5 = screenX(0.0F, 30.0F, 0.0F);
      float var6 = screenY(0.0F, 30.0F, 0.0F);
      if ((float)Math.hypot(var0 - var5, var1 - var6) < 9.0F * unit) {
         return 6;
      }

      byte var7 = 0;
      float var8 = 12.0F * unit;
      var8 = nearer(var8, var0, var1, var3, 24.0F, rightSpread, rightArm);
      if (var8 < 12.0F * unit) {
         var7 = 2;
      }

      float var9 = nearer(1.0E9F, var0, var1, var4, 24.0F, leftSpread, leftArm);
      if (var9 < var8) {
         var8 = var9;
         var7 = 3;
      }

      var9 = nearer(1.0E9F, var0, var1, -2.0F, 12.0F, rightLegSpread, rightLeg);
      if (var9 < var8) {
         var8 = var9;
         var7 = 4;
      }

      var9 = nearer(1.0E9F, var0, var1, 2.0F, 12.0F, leftLegSpread, leftLeg);
      if (var9 < var8) {
         var7 = 5;
      }

      if (var7 != 0) {
         return var7;
      } else {
         return overDoll(var0, var1) ? 1 : 0;
      }
   }

   private static float nearer(float var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = (float)Math.cos(-var6);
      float var8 = (float)Math.sin(-var6);
      float var9 = (float)Math.cos(var5);
      float var10 = (float)Math.sin(var5);
      float var11 = -12.0F * var7;
      float var12 = -12.0F * var8;
      float var13 = -var11 * var10;
      float var14 = var11 * var9;
      float var15 = screenX(var3 + var13, var4 + var14, var12);
      float var16 = screenY(var3 + var13, var4 + var14, var12);
      float var17 = screenX(var3, var4, 0.0F);
      float var18 = screenY(var3, var4, 0.0F);
      float var19 = (float)Math.hypot(var1 - var15, var2 - var16);
      float var20 = distToSeg(var1, var2, var17, var18, var15, var16);
      float var21 = Math.min(var19 * 0.78F, var20);
      return var21 < var0 ? var21 : var0;
   }

   private static float distToSeg(float var0, float var1, float var2, float var3, float var4, float var5) {
      float var6 = var4 - var2;
      float var7 = var5 - var3;
      float var8 = var6 * var6 + var7 * var7;
      float var9 = var8 < 1.0F ? 0.0F : clamp(((var0 - var2) * var6 + (var1 - var3) * var7) / var8, 0.0F, 1.0F);
      return (float)Math.hypot(var0 - (var2 + var6 * var9), var1 - (var3 + var7 * var9));
   }

   private static float screenX(float var0, float var1, float var2) {
      return spinX(rawX(var0, var2), rawY(var0, var1, var2));
   }

   private static float screenY(float var0, float var1, float var2) {
      return spinY(rawX(var0, var2), rawY(var0, var1, var2));
   }

   private static float rawX(float var0, float var1) {
      float var2 = (float)Math.toRadians(yaw);
      return dollX + (var0 * (float)Math.cos(var2) + var1 * (float)Math.sin(var2)) * unit;
   }

   private static float rawY(float var0, float var1, float var2) {
      float var3 = (float)Math.toRadians(yaw);
      float var4 = (float)Math.toRadians(pitch);
      float var5 = -var0 * (float)Math.sin(var3) + var2 * (float)Math.cos(var3);
      float var6 = var1 * (float)Math.cos(var4) - var5 * (float)Math.sin(var4);
      return dollY - var6 * unit;
   }

   private static float pivotY() {
      float var0 = (float)Math.toRadians(pitch);
      return dollY - 16.0F * (float)Math.cos(var0) * unit;
   }

   private static float spinX(float var0, float var1) {
      float var2 = var0 - dollX;
      float var3 = var1 - pivotY();
      float var4 = (float)Math.cos(bodyRoll);
      float var5 = (float)Math.sin(bodyRoll);
      return dollX + var2 * var4 - var3 * var5;
   }

   private static float spinY(float var0, float var1) {
      float var2 = var0 - dollX;
      float var3 = var1 - pivotY();
      float var4 = (float)Math.cos(bodyRoll);
      float var5 = (float)Math.sin(bodyRoll);
      return pivotY() + var2 * var5 + var3 * var4;
   }

   private static boolean overDoll(float var0, float var1) {
      float var2 = 14.0F * unit;
      float var3 = dollY - 38.0F * unit;
      float var4 = dollY + 8.0F * unit;
      return var0 >= dollX - var2 && var0 <= dollX + var2 && var1 >= var3 && var1 <= var4;
   }

   private static boolean mouseDown() {
      return mouseButton(0);
   }

   private static boolean mouseButton(int var0) {
      try {
         if (glfwMouse == null) {
            Class var1 = Class.forName("org.lwjgl.glfw.GLFW");
            glfwMouse = var1.getMethod("glfwGetMouseButton", long.class, int.class);
         }

         Class var9 = Class.forName("net.minecraft.class_310");
         Object var2 = var9.getMethod("method_1551").invoke(null);
         Object var3 = var2.getClass().getMethod("method_22683").invoke(var2);
         long var4 = (Long)var3.getClass().getMethod("method_4490").invoke(var3);
         Object var6 = glfwMouse.invoke(null, var4, var0);
         if (var6 instanceof Integer) {
            return (Integer)var6 == 1;
         }
      } catch (Throwable var8) {
         glfwFailed = true;
      }

      try {
         return ImGui.isMouseDown(var0);
      } catch (Throwable var7) {
         return false;
      }
   }

   private static void drawSkin(ImDrawList var0, int var1, float var2, float var3, float var4, float var5, float var6, boolean var7, float var8) {
      var5 = 18.0F;
      var6 = 0.0F;
      float var9 = (float)Math.toRadians(var5);
      float var10 = (float)Math.toRadians(var6);
      float var11 = (float)Math.cos(var9);
      float var12 = (float)Math.sin(var9);
      float var13 = (float)Math.cos(var10);
      float var14 = (float)Math.sin(var10);
      faceCount = 0;
      int var15 = var7 ? 3 : 4;
      float var16 = var7 ? -7.0F : -8.0F;
      addPart(0, 16, 4, 12, 4, -4.0F, 0.0F, -2.0F, 0.0F, var11, var12, var13, var14, var2, var3, var4, var8, 0.0F, 0.0F, 0.0F);
      addPart(16, 48, 4, 12, 4, 0.0F, 0.0F, -2.0F, 0.0F, var11, var12, var13, var14, var2, var3, var4, var8, 0.0F, 0.0F, 0.0F);
      addPart(16, 16, 8, 12, 4, -4.0F, 12.0F, -2.0F, 0.0F, var11, var12, var13, var14, var2, var3, var4, var8, 0.0F, 0.0F, 0.0F);
      addPart(40, 16, var15, 12, 4, var16, 12.0F, -2.0F, 0.0F, var11, var12, var13, var14, var2, var3, var4, var8, 0.0F, 0.0F, 0.0F);
      addPart(32, 48, var15, 12, 4, 4.0F, 12.0F, -2.0F, 0.0F, var11, var12, var13, var14, var2, var3, var4, var8, 0.0F, 0.0F, 0.0F);
      addPart(0, 0, 8, 8, 8, -4.0F, 24.0F, -4.0F, 0.0F, var11, var12, var13, var14, var2, var3, var4, var8, 0.0F, 0.0F, 0.0F);
      float var17 = 0.5F;
      addPart(0, 32, 4, 12, 4, -4.0F, 0.0F, -2.0F, var17, var11, var12, var13, var14, var2, var3, var4, var8, 0.45F, 0.0F, 0.0F);
      addPart(0, 48, 4, 12, 4, 0.0F, 0.0F, -2.0F, var17, var11, var12, var13, var14, var2, var3, var4, var8, 0.45F, 0.0F, 0.0F);
      addPart(16, 32, 8, 12, 4, -4.0F, 12.0F, -2.0F, var17, var11, var12, var13, var14, var2, var3, var4, var8, 0.45F, 0.0F, 0.0F);
      addPart(40, 32, var15, 12, 4, var16, 12.0F, -2.0F, var17, var11, var12, var13, var14, var2, var3, var4, var8, 0.45F, 0.0F, 0.0F);
      addPart(48, 48, var15, 12, 4, 4.0F, 12.0F, -2.0F, var17, var11, var12, var13, var14, var2, var3, var4, var8, 0.45F, 0.0F, 0.0F);
      addPart(32, 0, 8, 8, 8, -4.0F, 24.0F, -4.0F, var17, var11, var12, var13, var14, var2, var3, var4, var8, 0.45F, 0.0F, 0.0F);
      flushFaces(var0, var1);
   }

   private static void addPart(
      int var0,
      int var1,
      int var2,
      int var3,
      int var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18,
      float var19
   ) {
      float var20 = var5 + var2 * 0.5F;
      float var21 = var6 + var3;
      float var22 = var7 + var4 * 0.5F;
      float var23 = (float)Math.cos(-var18);
      float var24 = (float)Math.sin(-var18);
      float var25 = (float)Math.cos(var19);
      float var26 = (float)Math.sin(var19);
      float var27 = var5 - var8;
      float var28 = var6 - var8;
      float var29 = var7 - var8;
      float var30 = var5 + var2 + var8;
      float var31 = var6 + var3 + var8;
      float var32 = var7 + var4 + var8;
      posed(0, var27, var31, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(1, var30, var31, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(2, var30, var28, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(3, var27, var28, var32, var20, var21, var22, var23, var24, var25, var26);
      poseNormal(0.0F, 0.0F, 1.0F, var23, var24, var25, var26);
      face(
         QX[0],
         QY[0],
         QZ[0],
         QX[1],
         QY[1],
         QZ[1],
         QX[2],
         QY[2],
         QZ[2],
         QX[3],
         QY[3],
         QZ[3],
         pnx,
         pny,
         pnz,
         var0 + var4,
         var1 + var4,
         var0 + var4 + var2,
         var1 + var4 + var3,
         var9,
         var10,
         var11,
         var12,
         var13,
         var14,
         var15,
         var16,
         1.0F,
         var17
      );
      posed(0, var30, var31, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(1, var27, var31, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(2, var27, var28, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(3, var30, var28, var29, var20, var21, var22, var23, var24, var25, var26);
      poseNormal(0.0F, 0.0F, -1.0F, var23, var24, var25, var26);
      face(
         QX[0],
         QY[0],
         QZ[0],
         QX[1],
         QY[1],
         QZ[1],
         QX[2],
         QY[2],
         QZ[2],
         QX[3],
         QY[3],
         QZ[3],
         pnx,
         pny,
         pnz,
         var0 + var4 + var2 + var4,
         var1 + var4,
         var0 + var4 + var2 + var4 + var2,
         var1 + var4 + var3,
         var9,
         var10,
         var11,
         var12,
         var13,
         var14,
         var15,
         var16,
         0.72F,
         var17
      );
      posed(0, var27, var31, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(1, var27, var31, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(2, var27, var28, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(3, var27, var28, var32, var20, var21, var22, var23, var24, var25, var26);
      poseNormal(-1.0F, 0.0F, 0.0F, var23, var24, var25, var26);
      face(
         QX[0],
         QY[0],
         QZ[0],
         QX[1],
         QY[1],
         QZ[1],
         QX[2],
         QY[2],
         QZ[2],
         QX[3],
         QY[3],
         QZ[3],
         pnx,
         pny,
         pnz,
         var0,
         var1 + var4,
         var0 + var4,
         var1 + var4 + var3,
         var9,
         var10,
         var11,
         var12,
         var13,
         var14,
         var15,
         var16,
         0.84F,
         var17
      );
      posed(0, var30, var31, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(1, var30, var31, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(2, var30, var28, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(3, var30, var28, var29, var20, var21, var22, var23, var24, var25, var26);
      poseNormal(1.0F, 0.0F, 0.0F, var23, var24, var25, var26);
      face(
         QX[0],
         QY[0],
         QZ[0],
         QX[1],
         QY[1],
         QZ[1],
         QX[2],
         QY[2],
         QZ[2],
         QX[3],
         QY[3],
         QZ[3],
         pnx,
         pny,
         pnz,
         var0 + var4 + var2,
         var1 + var4,
         var0 + var4 + var2 + var4,
         var1 + var4 + var3,
         var9,
         var10,
         var11,
         var12,
         var13,
         var14,
         var15,
         var16,
         0.84F,
         var17
      );
      posed(0, var27, var31, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(1, var30, var31, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(2, var30, var31, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(3, var27, var31, var32, var20, var21, var22, var23, var24, var25, var26);
      poseNormal(0.0F, 1.0F, 0.0F, var23, var24, var25, var26);
      face(
         QX[0],
         QY[0],
         QZ[0],
         QX[1],
         QY[1],
         QZ[1],
         QX[2],
         QY[2],
         QZ[2],
         QX[3],
         QY[3],
         QZ[3],
         pnx,
         pny,
         pnz,
         var0 + var4,
         var1,
         var0 + var4 + var2,
         var1 + var4,
         var9,
         var10,
         var11,
         var12,
         var13,
         var14,
         var15,
         var16,
         0.96F,
         var17
      );
      posed(0, var27, var28, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(1, var30, var28, var32, var20, var21, var22, var23, var24, var25, var26);
      posed(2, var30, var28, var29, var20, var21, var22, var23, var24, var25, var26);
      posed(3, var27, var28, var29, var20, var21, var22, var23, var24, var25, var26);
      poseNormal(0.0F, -1.0F, 0.0F, var23, var24, var25, var26);
      face(
         QX[0],
         QY[0],
         QZ[0],
         QX[1],
         QY[1],
         QZ[1],
         QX[2],
         QY[2],
         QZ[2],
         QX[3],
         QY[3],
         QZ[3],
         pnx,
         pny,
         pnz,
         var0 + var4 + var2,
         var1,
         var0 + var4 + var2 + var2,
         var1 + var4,
         var9,
         var10,
         var11,
         var12,
         var13,
         var14,
         var15,
         var16,
         0.58F,
         var17
      );
   }

   private static void posed(int var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      float var11 = var1 - var4;
      float var12 = var2 - var5;
      float var13 = var3 - var6;
      float var14 = var12 * var7 - var13 * var8;
      float var15 = var12 * var8 + var13 * var7;
      float var16 = var11 * var9 - var14 * var10;
      float var17 = var11 * var10 + var14 * var9;
      QX[var0] = var4 + var16;
      QY[var0] = var5 + var17;
      QZ[var0] = var6 + var15;
   }

   private static void poseNormal(float var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = var1 * var3 - var2 * var4;
      float var8 = var1 * var4 + var2 * var3;
      pnx = var0 * var5 - var7 * var6;
      pny = var0 * var6 + var7 * var5;
      pnz = var8;
   }

   private static void face(
      float var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18,
      float var19,
      float var20,
      float var21,
      float var22,
      float var23,
      float var24,
      float var25,
      float var26,
      float var27,
      float var28
   ) {
      float var29 = normZ(var12, var13, var14, var19, var20, var21, var22);
      if (!(var29 <= 0.04F)) {
         if (faceCount < 96) {
            float var30 = projectZ(var0, var1, var2, var19, var20, var21, var22);
            float var31 = projectZ(var3, var4, var5, var19, var20, var21, var22);
            float var32 = projectZ(var6, var7, var8, var19, var20, var21, var22);
            float var33 = projectZ(var9, var10, var11, var19, var20, var21, var22);
            int var34 = faceCount++;
            SZ[var34] = (var30 + var31 + var32 + var33) * 0.25F + var28;
            int var35 = var34 * 4;
            SX[var35] = projectX(var0, var2, var19, var20, var23, var25);
            SY[var35] = projectY(var0, var1, var2, var19, var20, var21, var22, var24, var25);
            SX[var35 + 1] = projectX(var3, var5, var19, var20, var23, var25);
            SY[var35 + 1] = projectY(var3, var4, var5, var19, var20, var21, var22, var24, var25);
            SX[var35 + 2] = projectX(var6, var8, var19, var20, var23, var25);
            SY[var35 + 2] = projectY(var6, var7, var8, var19, var20, var21, var22, var24, var25);
            SX[var35 + 3] = projectX(var9, var11, var19, var20, var23, var25);
            SY[var35 + 3] = projectY(var9, var10, var11, var19, var20, var21, var22, var24, var25);
            float var36 = 0.015625F;
            SU[var35] = var15 * var36;
            SV[var35] = var16 * var36;
            SU[var35 + 1] = var17 * var36;
            SV[var35 + 1] = var16 * var36;
            SU[var35 + 2] = var17 * var36;
            SV[var35 + 2] = var18 * var36;
            SU[var35 + 3] = var15 * var36;
            SV[var35 + 3] = var18 * var36;
            int var37 = Math.max(0, Math.min(255, Math.round(255.0F * var27)));
            SC[var34] = EgyptDraw.col(0xFF000000 | var37 << 16 | var37 << 8 | var37, var26);
         }
      }
   }

   private static float projectX(float var0, float var1, float var2, float var3, float var4, float var5) {
      return var4 + (var0 * var2 + var1 * var3) * var5;
   }

   private static float projectY(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = -var0 * var4 + var2 * var3;
      float var10 = var1 * var5 - var9 * var6;
      return var7 - var10 * var8;
   }

   private static float projectZ(float var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = -var0 * var4 + var2 * var3;
      return var1 * var6 + var7 * var5;
   }

   private static float normZ(float var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      float var7 = -var0 * var4 + var2 * var3;
      return var1 * var6 + var7 * var5;
   }

   private static void flushFaces(ImDrawList var0, int var1) {
      int var2 = faceCount;
      int var3 = 0;

      while (var3 < var2) {
         ORDER[var3] = var3++;
      }

      for (int var8 = 1; var8 < var2; var8++) {
         int var4 = ORDER[var8];
         float var5 = SZ[var4];

         int var6;
         for (var6 = var8 - 1; var6 >= 0 && SZ[ORDER[var6]] > var5; var6--) {
            ORDER[var6 + 1] = ORDER[var6];
         }

         ORDER[var6 + 1] = var4;
      }

      long var9 = var1;

      for (int var10 = 0; var10 < var2; var10++) {
         int var11 = ORDER[var10];
         int var7 = var11 * 4;
         var0.addImageQuad(
            var9,
            SX[var7],
            SY[var7],
            SX[var7 + 1],
            SY[var7 + 1],
            SX[var7 + 2],
            SY[var7 + 2],
            SX[var7 + 3],
            SY[var7 + 3],
            SU[var7],
            SV[var7],
            SU[var7 + 1],
            SV[var7 + 1],
            SU[var7 + 2],
            SV[var7 + 2],
            SU[var7 + 3],
            SV[var7 + 3],
            SC[var11]
         );
      }
   }

   private static void drawFallback(ImDrawList var0, KryptonPlayerShowcase.SessionInfo var1, float var2) {
      float var3 = unit;
      float var4 = dollX;
      float var5 = dollY - 32.0F * var3;
      int var6 = EgyptDraw.col(-15723494, var2 * 0.96F);
      var0.addRectFilled(var4 - 2.0F * var3, dollY - 12.0F * var3, var4, dollY, var6, 2.0F);
      var0.addRectFilled(var4, dollY - 12.0F * var3, var4 + 2.0F * var3, dollY, var6, 2.0F);
      var0.addRectFilled(var4 - 4.0F * var3, dollY - 24.0F * var3, var4 + 4.0F * var3, dollY - 12.0F * var3, var6, 3.0F);
      var0.addRectFilled(var4 - 7.0F * var3, dollY - 24.0F * var3, var4 - 4.0F * var3, dollY - 12.0F * var3, var6, 2.0F);
      var0.addRectFilled(var4 + 4.0F * var3, dollY - 24.0F * var3, var4 + 7.0F * var3, dollY - 12.0F * var3, var6, 2.0F);
      var0.addRectFilled(var4 - 4.0F * var3, var5, var4 + 4.0F * var3, var5 + 8.0F * var3, EgyptDraw.col(-14011584, var2), 2.0F);
      tryDrawFace(var0, var1, var4 - 4.0F * var3, var5, 8.0F * var3, var2);
   }

   private static boolean drawSkinDoll(
      ImDrawList var0, KryptonPlayerShowcase.SessionInfo var1, float var2, float var3, float var4, float var5, float var6, float var7
   ) {
      int var8 = resolveSkinGlId(var1);
      if (var8 == 0) {
         return false;
      }

      float var9 = (float)Math.cos(Math.toRadians(var5));
      float var10 = (float)Math.sin(Math.toRadians(var5));
      float var11 = var6 * 0.35F * var4 * 0.12F;
      boolean var12 = var1.slim;
      float var13 = 8.4F * var4;
      float var14 = 8.0F * var4;
      float var15 = 12.0F * var4;
      float var16 = (var12 ? 3.0F : 4.0F) * var4;
      float var17 = 12.0F * var4;
      float var18 = 4.0F * var4;
      float var19 = 12.0F * var4;
      float var20 = 0.55F + 0.45F * Math.abs(var9);
      float var21 = var13 * var20;
      float var22 = var14 * var20;
      float var23 = var16 * var20;
      float var24 = var18 * var20;
      float var25 = var3 + var11;
      float var26 = var25 - var13 * 0.98F;
      float var27 = var25 + var15 * 0.92F;
      float var28 = var27 + var19 * 0.92F;
      int var29 = EgyptDraw.col(-1, var7);
      float var30 = Math.max(0.35F, Math.abs(var9));
      int var31 = EgyptDraw.col(-1, var7 * (0.75F + 0.25F * var30));
      float var32 = 0.35F * var4;
      float var33 = var10 * var4 * 0.9F;
      skinQuad(var0, var8, var2 - var32 - var24 + var33 * 0.2F, var27, var24, var19 * 0.92F, 0.0625F, 0.3125F, 0.125F, 0.5F, var31);
      skinQuad(var0, var8, var2 + var32 + var33 * 0.2F, var27, var24, var19 * 0.92F, 0.3125F, 0.8125F, 0.375F, 1.0F, var31);
      skinQuad(var0, var8, var2 - var22 * 0.5F + var10 * var4 * 0.4F, var25, var22, var15 * 0.92F, 0.3125F, 0.3125F, 0.4375F, 0.5F, var31);
      float var34 = var22 * 0.52F;
      float var35 = var12 ? 0.6875F : 0.6875F;
      float var36 = var12 ? 0.734375F : 0.75F;
      skinQuad(var0, var8, var2 - var34 - var23 - var10 * var4 * 0.3F, var25, var23, var17 * 0.9F, var35, 0.3125F, var36, 0.5F, var31);
      float var37 = var12 ? 0.5625F : 0.5625F;
      float var38 = var12 ? 0.609375F : 0.625F;
      skinQuad(var0, var8, var2 + var34 + var10 * var4 * 0.3F, var25, var23, var17 * 0.9F, var37, 0.8125F, var38, 1.0F, var31);
      float var39 = var2 - var21 * 0.5F + var10 * var4 * 0.5F;
      skinQuad(var0, var8, var39, var26, var21, var13, 0.125F, 0.125F, 0.25F, 0.25F, var31);
      skinQuad(
         var0,
         var8,
         var39 - 0.15F * var4,
         var26 - 0.15F * var4,
         var21 + 0.3F * var4,
         var13 + 0.3F * var4,
         0.625F,
         0.125F,
         0.75F,
         0.25F,
         EgyptDraw.col(-1, var7 * 0.85F)
      );
      var0.addRect(var39 - 1.0F, var26 - 1.0F, var39 + var21 + 1.0F, var26 + var13 + 1.0F, EgyptDraw.col(-14494465, var7 * 0.18F), 2.0F, 0, 1.1F);
      return true;
   }

   private static void skinQuad(
      ImDrawList var0, int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10
   ) {
      if (!(var4 < 0.5F) && !(var5 < 0.5F)) {
         var0.addImage(var1, var2, var3, var2 + var4, var3 + var5, var6, var7, var8, var9, var10);
      }
   }

   private static int resolveSkinGlId(KryptonPlayerShowcase.SessionInfo var0) {
      try {
         UUID var1 = var0.uuid;
         boolean var2 = var0.cracked;
         if (var1 == null && var0.account != null) {
            try {
               var1 = UUID.fromString(var0.account.uuid());
               var2 = var0.account.type() == AccountType.CRACKED;
            } catch (Exception var12) {
            }
         }

         if (var1 == null) {
            return 0;
         }

         Method var3 = PlayerFaces.class.getDeclaredMethod("skin", UUID.class, boolean.class);
         var3.setAccessible(true);
         Object var4 = var3.invoke(null, var1, var2);
         if (var4 == null) {
            return 0;
         }

         try {
            for (Method var8 : var4.getClass().getMethods()) {
               if (var8.getParameterCount() == 0) {
                  String var9 = var8.getName().toLowerCase();
                  if (var9.contains("model") || var9.contains("slim") || var9.contains("type")) {
                     Object var10 = var8.invoke(var4);
                     if (var10 != null) {
                        String var11 = var10.toString().toLowerCase();
                        if (var11.contains("slim") || var11.contains("alex")) {
                           var0.slim = true;
                        }
                     }
                  }
               }
            }
         } catch (Throwable var13) {
         }

         Object var15 = var4.getClass().getMethod("comp_1626").invoke(var4);
         if (var15 == null) {
            return 0;
         }

         Object var16 = var15.getClass().getMethod("comp_3627").invoke(var15);
         if (var16 == null) {
            return 0;
         }

         Method var17 = null;

         for (Method var23 : PlayerFaces.class.getDeclaredMethods()) {
            if ("texture".equals(var23.getName()) && var23.getParameterCount() == 1 && var23.getReturnType() == int.class) {
               var17 = var23;
               break;
            }
         }

         if (var17 == null) {
            return 0;
         }

         var17.setAccessible(true);
         Object var19 = var17.invoke(null, var16);
         if (var19 instanceof Integer) {
            int var21 = (Integer)var19;
            return var21 > 0 ? var21 : 0;
         }
      } catch (Throwable var14) {
      }

      return 0;
   }

   private static void drawBadge(MenuContext var0, ImDrawList var1, float var2, float var3, KryptonPlayerShowcase.SessionInfo var4, float var5) {
      float var6 = var0.scale();
      ImFont var7 = var0.tagFont();
      if (var7 == null) {
         var7 = var0.menuFont();
      }

      String var8 = var4.name != null && !var4.name.isEmpty() ? var4.name : "KryptonUser";
      String var9 = "Premium";
      float var10 = var0.textWidth(var7, var8);
      float var11 = var0.textWidth(var7, var9);
      float var12 = 12.0F * var6;
      float var13 = 6.0F * var6;
      float var14 = 12.0F * var6;
      float var15 = var0.textHeight(var7);
      float var16 = Math.max(var10 + var14 + 8.0F * var6, var11);
      float var17 = var12 * 2.0F + var16;
      float var18 = var15 * 2.0F + var13 * 2.0F + 2.0F * var6;
      float var19 = var2 - var17 * 0.5F;

      for (int var20 = 0; var20 < 3; var20++) {
         float var21 = (3 - var20) * 2.5F * var6;
         var1.addRectFilled(
            var19 - var21,
            var3 - var21 * 0.35F,
            var19 + var17 + var21,
            var3 + var18 + var21 * 0.35F,
            EgyptDraw.col(-14494465, var5 * (0.035F + var20 * 0.025F)),
            var18 * 0.5F + var21
         );
      }

      var1.addRectFilled(var19, var3, var19 + var17, var3 + var18, EgyptDraw.col(-16117736, var5 * 0.9F), var18 * 0.5F);
      var1.addRect(var19, var3, var19 + var17, var3 + var18, EgyptDraw.col(-14494465, var5 * 0.42F), var18 * 0.5F, 0, 1.25F * var6);
      float var23 = var3 + var13 * 0.8F;
      float var24 = var10 + var14 + 8.0F * var6;
      float var22 = var19 + (var17 - var24) * 0.5F;
      drawCrown(var1, var22 + var14 * 0.5F, var23 + 2.2F * var6, var14, var5);
      var0.text(var1, var7, var22 + var14 + 5.0F * var6, var23, EgyptDraw.col(-1, var5), var6, var8);
      var0.text(var1, var7, var19 + (var17 - var11) * 0.5F, var23 + var15 + 1.5F * var6, EgyptDraw.col(-12192, var5 * 0.95F), var6, var9);
   }

   private static void drawCrown(ImDrawList var0, float var1, float var2, float var3, float var4) {
      float var5 = var3;
      float var6 = var3 * 0.72F;
      int var7 = EgyptDraw.col(-12192, var4);
      var0.addTriangleFilled(var1 - var5 * 0.5F, var2 + var6 * 0.55F, var1 - var5 * 0.18F, var2, var1 - var5 * 0.05F, var2 + var6 * 0.55F, var7);
      var0.addTriangleFilled(var1 - var5 * 0.22F, var2 + var6 * 0.55F, var1, var2 - var6 * 0.15F, var1 + var5 * 0.22F, var2 + var6 * 0.55F, var7);
      var0.addTriangleFilled(var1 + var5 * 0.05F, var2 + var6 * 0.55F, var1 + var5 * 0.18F, var2, var1 + var5 * 0.5F, var2 + var6 * 0.55F, var7);
      var0.addRectFilled(var1 - var5 * 0.5F, var2 + var6 * 0.5F, var1 + var5 * 0.5F, var2 + var6 * 0.85F, var7, 1.0F);
   }

   private static boolean tryDrawFace(ImDrawList var0, KryptonPlayerShowcase.SessionInfo var1, float var2, float var3, float var4, float var5) {
      try {
         if (var1.account != null) {
            PlayerFaces.draw(var0, var1.account, var2, var3, var4, 3.0F, var5);
            return true;
         }

         if (var1.uuid != null) {
            PlayerFaces.draw(var0, var1.uuid, var1.cracked, var2, var3, var4, 3.0F, var5);
            return true;
         }
      } catch (Throwable var7) {
      }

      return false;
   }

   private static void ensureUuid(KryptonPlayerShowcase.SessionInfo var0) {
      if (var0.uuid == null) {
         if (var0.name != null && !var0.name.isBlank() && !"KryptonUser".equals(var0.name)) {
            if (var0.name.equals(lookupName) && lookupUuid != null) {
               var0.uuid = lookupUuid;
            } else if (!lookupStarted || !var0.name.equals(lookupName)) {
               lookupStarted = true;
               lookupName = var0.name;
               String var1 = var0.name;
               Thread var2 = new Thread(
                  () -> {
                     try {
                        HttpURLConnection var1x = (HttpURLConnection)new URL("https://api.mojang.com/users/profiles/minecraft/" + var1).openConnection();
                        var1x.setConnectTimeout(4000);
                        var1x.setReadTimeout(4000);
                        var1x.setRequestProperty("User-Agent", "KryptonClient");
                        if (var1x.getResponseCode() != 200) {
                           return;
                        }

                        String var2x;
                        try (InputStream var3 = var1x.getInputStream()) {
                           var2x = new String(var3.readAllBytes(), StandardCharsets.UTF_8);
                        }

                        int var11 = var2x.indexOf("\"id\"");
                        if (var11 < 0) {
                           return;
                        }

                        int var4 = var2x.indexOf(34, var2x.indexOf(58, var11) + 1);
                        int var5 = var2x.indexOf(34, var4 + 1);
                        if (var4 < 0 || var5 <= var4) {
                           return;
                        }

                        String var6 = var2x.substring(var4 + 1, var5).replace("-", "");
                        if (var6.length() != 32) {
                           return;
                        }

                        String var7 = var6.substring(0, 8)
                           + "-"
                           + var6.substring(8, 12)
                           + "-"
                           + var6.substring(12, 16)
                           + "-"
                           + var6.substring(16, 20)
                           + "-"
                           + var6.substring(20);
                        lookupUuid = UUID.fromString(var7);
                     } catch (Throwable var10) {
                     }
                  },
                  "krypton-skin-lookup"
               );
               var2.setDaemon(true);
               var2.start();
            }
         }
      }
   }

   private static KryptonPlayerShowcase.SessionInfo resolveSession(MenuContext var0) {
      KryptonPlayerShowcase.SessionInfo var1 = new KryptonPlayerShowcase.SessionInfo();

      try {
         String var2 = var0.displayName();
         if (var2 != null && !var2.isBlank()) {
            var1.name = var2;
         }
      } catch (Throwable var13) {
      }

      try {
         Accounts var18 = Accounts.get();
         if (var18 != null) {
            Account var3 = var18.active();
            if (var3 != null) {
               var1.account = var3;
               var1.name = var3.name();
               var1.cracked = var3.type() == AccountType.CRACKED;
               var1.premium = var3.type() == AccountType.MICROSOFT || var3.type() == AccountType.SESSION;

               try {
                  var1.uuid = UUID.fromString(var3.uuid());
               } catch (Exception var12) {
               }
            }
         }
      } catch (Throwable var17) {
      }

      if (var1.uuid == null) {
         try {
            Class var19 = Class.forName("net.minecraft.class_310");
            Object var20 = var19.getMethod("method_1551").invoke(null);
            Object var4 = var20.getClass().getMethod("method_1548").invoke(var20);
            if (var1.name == null || var1.name.isBlank()) {
               Object var5 = var4.getClass().getMethod("method_1676").invoke(var4);
               if (var5 != null) {
                  var1.name = var5.toString();
               }
            }

            if (var1.uuid == null) {
               for (Method var8 : var4.getClass().getMethods()) {
                  if (var8.getParameterCount() == 0) {
                     try {
                        Object var9 = var8.invoke(var4);
                        if (var9 instanceof UUID) {
                           var1.uuid = (UUID)var9;
                           break;
                        }

                        if (var9 != null) {
                           String var10 = var9.toString();
                           if (var10.length() == 32 || var10.length() == 36) {
                              try {
                                 if (var10.length() == 32) {
                                    var10 = var10.substring(0, 8)
                                       + "-"
                                       + var10.substring(8, 12)
                                       + "-"
                                       + var10.substring(12, 16)
                                       + "-"
                                       + var10.substring(16, 20)
                                       + "-"
                                       + var10.substring(20);
                                 }

                                 var1.uuid = UUID.fromString(var10);
                                 break;
                              } catch (IllegalArgumentException var14) {
                              }
                           }
                        }
                     } catch (Throwable var15) {
                     }
                  }
               }
            }
         } catch (Throwable var16) {
         }
      }

      var1.premium = true;
      if (var1.name == null || var1.name.isBlank()) {
         var1.name = "KryptonUser";
      }

      return var1;
   }

   private static float clamp(float var0, float var1, float var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   private static final class SessionInfo {
      Account account;
      UUID uuid;
      String name = "KryptonUser";
      boolean cracked;
      boolean premium = true;
      boolean slim;
   }
}
