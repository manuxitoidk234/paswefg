#version 330

layout(std140) uniform KryptonSky {
    vec4 Look;
    vec4 Amounts;
    vec4 ColorLow;
    vec4 ColorHigh;
    vec4 ColorBand;
    vec4 Zenith;
    vec4 Horizon;
};

in vec3 skyDirection;

out vec4 fragColor;

const vec3 WHITE_HOT = vec3(1.0, 0.96, 0.88);
const float HOLE_DISTANCE = 20.0;
const float HOLE_CAPTURE = 2.65;
const float DISK_INNER = 2.7;
const float DISK_OUTER = 20.0;

float skyHash12(vec2 p) {
    vec3 q = fract(vec3(p.xyx) * 0.1031);
    q += dot(q, q.yzx + 33.33);
    return fract((q.x + q.y) * q.z);
}

float skyHash13(vec3 p) {
    p = fract(p * 0.1031);
    p += dot(p, p.zyx + 31.32);
    return fract((p.x + p.y) * p.z);
}

vec3 skyHash33(vec3 p) {
    p = fract(p * vec3(0.1031, 0.1030, 0.0973));
    p += dot(p, p.yxz + 33.33);
    return fract((p.xxy + p.yxx) * p.zyx);
}

float skyNoise2(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);
    float a = skyHash12(i);
    float b = skyHash12(i + vec2(1.0, 0.0));
    float c = skyHash12(i + vec2(0.0, 1.0));
    float d = skyHash12(i + vec2(1.0, 1.0));
    return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

float skyNoise3(vec3 p) {
    vec3 i = floor(p);
    vec3 f = fract(p);
    vec3 u = f * f * (3.0 - 2.0 * f);
    float a = skyHash13(i);
    float b = skyHash13(i + vec3(1.0, 0.0, 0.0));
    float c = skyHash13(i + vec3(0.0, 1.0, 0.0));
    float d = skyHash13(i + vec3(1.0, 1.0, 0.0));
    float e = skyHash13(i + vec3(0.0, 0.0, 1.0));
    float g = skyHash13(i + vec3(1.0, 0.0, 1.0));
    float h = skyHash13(i + vec3(0.0, 1.0, 1.0));
    float k = skyHash13(i + vec3(1.0, 1.0, 1.0));
    return mix(mix(mix(a, b, u.x), mix(c, d, u.x), u.y), mix(mix(e, g, u.x), mix(h, k, u.x), u.y), u.z);
}

float skyFbm2(vec2 p) {
    float sum = 0.0;
    float amp = 0.5;
    for (int i = 0; i < 4; i++) {
        sum += amp * skyNoise2(p);
        p = p * 2.11 + vec2(5.3, 1.7);
        amp *= 0.5;
    }
    return sum;
}

float skyFbm3(vec3 p) {
    float sum = 0.0;
    float amp = 0.5;
    for (int i = 0; i < 4; i++) {
        sum += amp * skyNoise3(p);
        p = p * 2.03 + vec3(13.1, 7.7, 3.3);
        amp *= 0.5;
    }
    return sum;
}

float skyFbm2Fast(vec2 p) {
    float sum = 0.0;
    float amp = 0.5;
    for (int i = 0; i < 3; i++) {
        sum += amp * skyNoise2(p);
        p = p * 2.07 + vec2(3.1, 1.9);
        amp *= 0.5;
    }
    return sum;
}

float skyRidge2(vec2 p) {
    float n = skyFbm2(p);
    return 1.0 - abs(n * 2.0 - 1.0);
}

float skyRidge3(vec3 p) {
    float n = skyFbm3(p);
    return 1.0 - abs(n * 2.0 - 1.0);
}

vec2 turn(vec2 v, float angle) {
    float c = cos(angle);
    float s = sin(angle);
    return vec2(v.x * c - v.y * s, v.x * s + v.y * c);
}

vec3 spin(vec3 v, vec3 axis, float angle) {
    float c = cos(angle);
    float s = sin(angle);
    return v * c + cross(axis, v) * s + axis * dot(axis, v) * (1.0 - c);
}

float nightGate() {
    return smoothstep(0.08, 0.55, clamp(Look.y, 0.0, 1.0));
}

float cloudVeil(vec3 dir, float time) {
    float soft = skyFbm3(dir * 1.55 + vec3(time * 0.01, 0.2, -time * 0.007));
    float thick = skyFbm3(dir * 3.1 + vec3(-0.4, time * 0.008, 0.7));
    float veil = smoothstep(0.42, 0.82, soft * 0.55 + thick * 0.55);
    return veil * veil;
}

vec3 themeZenith() {
    vec3 z = max(Zenith.rgb, vec3(0.002));
    return mix(vec3(0.002, 0.005, 0.022), z * 0.45, 0.45);
}

vec3 themeHorizon() {
    vec3 h = max(Horizon.rgb, vec3(0.004));
    return mix(vec3(0.008, 0.012, 0.035), h * 0.4, 0.4);
}

vec3 themeLow() {
    vec3 c = max(ColorLow.rgb, vec3(0.02));
    c = mix(c, vec3(c.b * 0.55, c.g * 0.85, max(c.b, c.r)), 0.35);
    return mix(vec3(0.18, 0.42, 0.98), c, 0.72);
}

vec3 themeHigh() {
    vec3 c = max(ColorHigh.rgb, vec3(0.02));
    float magenta = max(c.r + c.b - 1.4 * c.g, 0.0);
    c = mix(c, vec3(0.35, 0.55, 1.0), clamp(magenta * 0.55, 0.0, 0.55));
    return mix(vec3(0.55, 0.72, 1.0), c, 0.7);
}

vec3 themeBand() {
    vec3 c = max(ColorBand.rgb, vec3(0.05));
    c = mix(c, vec3(0.75, 0.88, 1.0), 0.35);
    return mix(vec3(0.82, 0.90, 1.0), c, 0.75);
}

vec3 starField(vec3 dir, float density, float time) {
    float gate = nightGate();
    density *= gate * gate * 0.42;
    if (density <= 0.00035) return vec3(0.0);

    float veil = cloudVeil(dir, time);
    density *= mix(1.0, 0.12, veil);

    vec3 total = vec3(0.0);
    for (int layer = 0; layer < 2; layer++) {
        float scale = 36.0 + 52.0 * float(layer);
        vec3 p = dir * scale + vec3(float(layer) * 71.3, float(layer) * 19.7, float(layer) * 5.1);
        float pixel = max(length(fwidth(p)), 1.0e-4);
        vec3 cell = floor(p);
        vec3 rnd = skyHash33(cell);

        float cluster = step(0.72, skyHash13(cell * 0.29 + 2.7));
        float rare = step(0.88, rnd.z);
        float threshold = 0.0016 * density
                * (0.25 + 0.75 * float(1 - layer))
                * (0.15 + 0.85 * cluster + 0.55 * rare);
        float present = step(rnd.x, clamp(threshold, 0.0, 0.045));

        vec3 center = cell + 0.5 + (skyHash33(cell + 19.19) - 0.5) * 0.55;
        float dist = length(p - center);
        float bright = pow(rnd.y, 5.5);
        float radius = max(0.018 + 0.22 * bright * bright, pixel * 0.45);
        float shape = 1.0 - smoothstep(radius - pixel, radius + pixel * 1.2, dist);

        float core = exp(-dist * dist / max(radius * radius * 0.35, 1.0e-6));
        float gem = step(0.975, rnd.y);
        shape = max(shape, core * gem * 1.8);

        float pixelScale = Amounts.w > 0.0 ? Amounts.w : 1.0;
        float referencePixel = pixel * pixelScale;
        float referenceRadius = max(0.018 + 0.22 * bright * bright, referencePixel * 0.45);
        float footprint = (referenceRadius + referencePixel) / max(radius + pixel, 1.0e-6);
        shape *= footprint * footprint;

        float twinkle = 0.55 + 0.45 * sin(time * (0.4 + 2.2 * rnd.z) + rnd.y * 61.0);
        vec3 cool = vec3(0.55, 0.72, 1.0);
        vec3 warm = vec3(1.0, 0.82, 0.62);
        vec3 ice = vec3(0.72, 0.95, 1.0);
        vec3 tint = mix(cool, warm, smoothstep(0.15, 0.9, rnd.z));
        tint = mix(tint, ice, step(0.92, rnd.y) * 0.65);

        total += tint * shape * present * twinkle * (0.08 + 2.4 * bright + gem * 2.2);
    }
    return total * gate;
}

// Structured Milky Way + sparse stars — deep ink void, no muddy wash.
vec3 deepSpace(vec3 dir, float time, float nebula) {
    float gate = nightGate();
    vec3 deepInk = vec3(0.0012, 0.0025, 0.010);
    deepInk = mix(deepInk, themeZenith() * 0.25, 0.35);
    vec3 rim = mix(vec3(0.004, 0.007, 0.022), themeHorizon() * 0.3, 0.35);
    vec3 color = mix(rim, deepInk, smoothstep(-0.35, 0.95, dir.y));
    color *= mix(0.1, 1.0, gate);

    vec3 axis = normalize(vec3(0.62, 0.18, 0.76));
    vec3 alongAxis = normalize(cross(axis, vec3(0.0, 1.0, 0.0)));
    float across = dot(dir, axis);
    float along = dot(dir, alongAxis);
    float bandMask = exp(-across * across * 10.5);
    float coreMask = exp(-across * across * 30.0) * exp(-along * along * 0.6);

    vec2 gp = vec2(across * 2.8, along * 1.35);
    float dust = skyFbm2(gp * 1.9 + vec2(time * 0.006, 0.15));
    float dust2 = skyFbm2(gp * 4.6 + dust * 1.8 + vec2(-0.2, time * 0.0035));
    float filaments = pow(skyRidge2(gp * 3.4 + vec2(dust * 0.8, time * 0.004)), 2.4);
    float filaments2 = pow(skyRidge2(gp * 7.2 + vec2(time * 0.002, dust2)), 3.2);
    float darkLanes = smoothstep(0.28, 0.72, dust2);

    vec3 voidBlue = vec3(0.04, 0.08, 0.24);
    vec3 cyanGas = mix(themeLow(), vec3(0.15, 0.55, 1.0), 0.45);
    vec3 iceGas = mix(themeHigh(), vec3(0.45, 0.78, 1.0), 0.5);
    vec3 violetEdge = mix(themeBand(), vec3(0.5, 0.38, 0.92), 0.3);
    vec3 goldCore = mix(themeBand(), vec3(0.95, 0.85, 0.65), 0.4);

    float nebAmt = clamp(nebula, 0.0, 2.0) * gate;
    float gasAmt = max(nebAmt, 0.7 * gate);

    float glow = bandMask * (0.16 + 0.5 * skyFbm3(dir * 2.8 + 1.4));
    vec3 bandGas = mix(voidBlue, cyanGas, smoothstep(0.2, 0.75, dust));
    bandGas = mix(bandGas, iceGas, smoothstep(0.45, 0.9, dust2) * 0.55);
    color += bandGas * glow * 1.05 * gasAmt;

    float filamentMask = bandMask * (0.35 * filaments + 0.65 * filaments2);
    vec3 filamentCol = mix(cyanGas, iceGas, filaments);
    filamentCol = mix(filamentCol, violetEdge, filaments2 * 0.35);
    color += filamentCol * filamentMask * 1.7 * gasAmt;

    color += goldCore * coreMask * (1.35 + 1.0 * dust) * gate;
    color += iceGas * coreMask * filaments * 0.7 * gasAmt;
    color *= 1.0 - 0.65 * darkLanes * bandMask * gate;

    float offWisp = pow(skyRidge3(dir * 2.2 + vec3(time * 0.004, 0.3, -time * 0.003)), 3.5);
    float offMask = (1.0 - bandMask * 0.85) * smoothstep(0.58, 0.93, offWisp);
    color += mix(cyanGas, violetEdge, offWisp) * offMask * 0.22 * nebAmt;

    float starDens = Amounts.x * (0.2 + 1.05 * bandMask * gate + 1.25 * coreMask * gate);
    color += starField(dir, starDens, time);
    color += themeBand() * bandMask * coreMask * 0.7 * Amounts.z * gate;

    float structure = clamp(bandMask * 0.75 + filamentMask * 0.95 + coreMask * 1.25 + offMask * 0.35, 0.0, 1.0);
    color = mix(deepInk * mix(0.12, 1.0, gate), color, mix(0.5, 1.0, structure));
    // Kill any muddy brown leftover from theme horizon bleed
    color = max(color, deepInk * 0.5 * gate);

    return color;
}

// Cheap aurora — still reads as curtains, ~5x fewer layers than before.
vec3 auroraLight(vec3 dir, float time) {
    if (dir.y <= -0.05) return vec3(0.0);
    float gate = nightGate();
    if (gate <= 0.001) return vec3(0.0);

    vec3 sum = vec3(0.0);
    float rise = max(dir.y, 0.02);
    float jitter = fract(52.9829189 * fract(dot(gl_FragCoord.xy, vec2(0.06711056, 0.00583715))));
    vec2 spread = vec2(dir.x / rise, dir.z / rise) * 0.62;
    float depth = mix(1.0, 3.2, smoothstep(0.0, 0.55, dir.y));

    const int LAYERS = 10;
    for (int i = 0; i < LAYERS; i++) {
        float layer = (float(i) + jitter) / float(LAYERS);
        float t = time * (0.06 + 0.03 * layer);
        vec2 at = spread * (0.5 + layer * depth) + vec2(t * 1.15, t * 0.32);
        float sway = skyFbm2Fast(vec2(at.x * 0.26 + t * 0.5, layer * 2.6));
        float field = skyFbm2Fast(vec2(at.x * 0.48 + sway * 2.4, at.y * 1.2 + t));
        float fold = pow(1.0 - abs(field * 2.0 - 1.0), 8.0);
        float pillars = pow(1.0 - abs(skyNoise2(vec2(at.x * 2.6, layer * 3.2 + t * 0.55)) * 2.0 - 1.0), 5.0);
        float sheet = fold * (0.45 + 0.55 * pillars);
        sheet *= exp(-layer * 1.15);

        vec3 green = mix(vec3(0.05, 1.0, 0.32), themeLow(), 0.2);
        vec3 cyan = mix(vec3(0.12, 0.98, 0.95), themeHigh(), 0.16);
        vec3 purple = mix(vec3(0.62, 0.2, 1.0), themeBand(), 0.2);
        vec3 tint = mix(green, cyan, smoothstep(0.08, 0.48, layer));
        tint = mix(tint, purple, smoothstep(0.42, 0.95, layer + 0.25 * sway));
        sum += tint * sheet;
    }

    float fade = smoothstep(-0.02, 0.12, dir.y) * (1.0 - 0.22 * smoothstep(0.78, 1.0, dir.y));
    float veil = cloudVeil(dir, time);
    // Boost to compensate for fewer layers
    return sum * fade * 2.4 * gate * mix(1.0, 0.45, veil);
}

vec3 northernSky(vec3 dir, float time) {
    float gate = nightGate();
    vec3 color = deepSpace(dir, time, Amounts.y * 0.35);
    color = mix(mix(themeHorizon() * 0.25, themeZenith() * 0.55, smoothstep(-0.25, 0.9, dir.y)), color, 0.72 * gate);
    float auroraAmt = max(Amounts.y, 0.9);
    color += auroraLight(dir, time) * auroraAmt;
    float wash = smoothstep(0.0, 0.28, dir.y) * (1.0 - smoothstep(0.28, 0.68, dir.y));
    color += mix(themeLow(), vec3(0.08, 0.7, 0.35), 0.5) * wash * 0.32 * auroraAmt * gate;
    color += vec3(0.1, 0.35, 0.55) * exp(-abs(dir.y) * 6.0) * 0.2 * gate;
    return color;
}

float ringDensity(float r) {
    float body = smoothstep(1.18, 1.32, r) * (1.0 - smoothstep(2.35, 2.55, r));
    float ringlets = 0.45 + 0.55 * skyNoise2(vec2(r * 36.0, 0.7));
    float bands = 0.6 + 0.4 * sin(r * 26.0);
    float cassini = smoothstep(0.014, 0.04, abs(r - 1.78));
    float encke = smoothstep(0.008, 0.022, abs(r - 2.12));
    float keeler = smoothstep(0.006, 0.016, abs(r - 2.32));
    return body * ringlets * bands * cassini * encke * keeler;
}

vec3 ringedPlanet(vec3 dir, float time) {
    float gate = nightGate();
    vec3 color = deepSpace(dir, time, Amounts.y * 0.55);

    vec3 center = normalize(vec3(0.18, 0.42, -0.88)) * 24.0;
    float radius = 1.25;
    vec3 axis = normalize(vec3(0.18, 0.72, 0.67));
    vec3 light = normalize(vec3(0.82, 0.22, 0.48));

    vec3 pale = mix(vec3(0.98, 0.9, 0.74), themeBand(), 0.2);
    vec3 mid = mix(vec3(0.82, 0.64, 0.42), themeHigh(), 0.22);
    vec3 deep = mix(vec3(0.38, 0.24, 0.16), themeLow(), 0.25);
    vec3 air = mix(vec3(0.9, 0.75, 0.5), themeBand(), 0.18);

    float along = dot(dir, center);
    float center2 = dot(center, center);
    float miss2 = max(center2 - along * along, 0.0);
    float miss = sqrt(miss2);
    float edge = max(fwidth(miss), 1.0e-4);
    float cover = along > 0.0 ? 1.0 - smoothstep(radius - edge, radius + edge, miss) : 0.0;
    float under = max(radius * radius - miss2, 0.0);
    float hitAt = along - sqrt(under);

    vec3 limb = (dir * along - center) / max(miss, 1.0e-4);
    float glow = along > 0.0 ? exp(-max(miss - radius, 0.0) / (radius * 0.035)) : 0.0;
    color += air * glow * smoothstep(-0.15, 0.65, dot(limb, light)) * 1.05 * (1.0 - cover) * gate;

    if (cover > 0.001 && hitAt > 0.0) {
        vec3 n = normalize(dir * hitAt - center);
        vec3 turned = spin(n, axis, time * 0.02);
        float lat = dot(n, axis);
        float turb = skyFbm3(turned * 2.8 + 1.7) - 0.5;
        float bands = skyFbm2(vec2(lat * 10.0 + turb * 0.75, 1.8));
        float storms = skyFbm3(turned * 5.8 + vec3(lat * 14.0));
        vec3 surface = mix(deep, mid, smoothstep(0.18, 0.55, bands));
        surface = mix(surface, pale, smoothstep(0.55, 0.88, bands));
        surface = mix(surface, mix(themeHigh(), vec3(0.95, 0.55, 0.25), 0.45), smoothstep(0.62, 0.92, storms) * 0.28);

        float shade = 1.0;
        float lightUp = dot(light, axis);
        if (abs(lightUp) > 1.0e-4) {
            float toPlane = dot(center - dir * hitAt, axis) / lightUp;
            if (toPlane > 0.0) {
                float ringR = length(dir * hitAt + light * toPlane - center) / max(radius, 1.0e-4);
                shade -= 0.92 * ringDensity(ringR);
            }
        }
        float sun = smoothstep(-0.05, 0.7, dot(n, light)) * clamp(shade, 0.05, 1.0);
        float view = max(dot(n, -dir), 0.0);
        vec3 body = surface * (0.05 + 0.95 * sun) * (0.45 + 0.55 * view);
        body += air * pow(1.0 - view, 2.4) * sun * 0.85;
        color = mix(color, body, clamp(cover, 0.0, 1.0));
    }

    float facing = dot(dir, axis);
    float ringAt = abs(facing) > 1.0e-4 ? dot(center, axis) / facing : -1.0;
    if (ringAt > 0.0) {
        vec3 spot = dir * ringAt;
        float r = length(spot - center) / max(radius, 1.0e-4);
        float density = ringDensity(r);
        if (density > 0.0) {
            vec3 toCenter = center - spot;
            float ahead = dot(toCenter, light);
            float gap2 = max(dot(toCenter, toCenter) - ahead * ahead, 0.0);
            float gap = sqrt(gap2);
            float lit = ahead > 0.0 ? smoothstep(radius * 0.92, radius * 1.06, gap) : 1.0;
            vec3 ring = mix(pale, mid, 0.2 + 0.5 * sin(r * 9.0)) * (0.12 + 1.0 * lit);
            ring += vec3(1.0, 0.92, 0.75) * 0.18 * density;
            float shown = (cover > 0.0 && ringAt > hitAt) ? 1.0 - cover : 1.0;
            color = mix(color, ring, clamp(density * 1.15 * shown, 0.0, 1.0));
        }
    }
    return color;
}

vec3 blackHole(vec3 dir, float time) {
    float gate = nightGate();
    vec3 ahead = normalize(vec3(-0.2, 0.4, 0.9));
    vec3 right = normalize(cross(ahead, vec3(0.0, 1.0, 0.0)));
    vec3 up = cross(right, ahead);
    vec3 local = vec3(dot(dir, right), dot(dir, up), dot(dir, ahead));
    float off = length(local.xy);
    float angle = atan(off, max(local.z, -1.0));
    vec2 outward = local.xy / max(off, 1.0e-6);
    float impact = HOLE_DISTANCE * off;
    float edge = max(fwidth(impact), 1.0e-4);
    float open = local.z > 0.0 ? smoothstep(HOLE_CAPTURE - edge, HOLE_CAPTURE + edge, impact) : 1.0;

    float bend = 2.55 / max(impact, HOLE_CAPTURE) * (1.0 - smoothstep(0.8, 1.5, angle));
    float seen = angle - bend;
    vec3 lensed = vec3(outward * sin(seen), cos(seen));

    vec3 glow = vec3(0.0);
    float cover = 0.0;
    vec3 orange = mix(vec3(1.0, 0.48, 0.08), themeHigh(), 0.28);
    vec3 gold = mix(vec3(1.0, 0.88, 0.42), themeBand(), 0.2);
    vec3 blue = mix(vec3(0.35, 0.65, 1.0), themeLow(), 0.3);

    // Soft cone window — fade raymarch instead of hard rectangular cut
    float marchWindow = 1.0 - smoothstep(0.72, 1.05, angle);
    if (marchWindow > 0.001 && local.z > -0.15) {
        vec3 normal = normalize(vec3(0.1, 0.99, -0.1));
        vec3 diskX = normalize(cross(normal, vec3(0.0, 0.0, 1.0)));
        vec3 diskY = cross(normal, diskX);
        vec3 pos = vec3(0.0, 0.0, -HOLE_DISTANCE);
        vec3 vel = local;
        float vlen = length(vel);
        if (vlen < 1.0e-5) vel = vec3(0.0, 0.0, 1.0);
        else vel /= vlen;
        vec3 momentum = cross(pos, vel);
        float h2 = max(dot(momentum, momentum), 1.0e-6);
        float side = dot(pos, normal);

        for (int i = 0; i < 96; i++) {
            float r = length(pos);
            if (r < 0.95 || cover > 0.98 || (r > HOLE_DISTANCE * 1.85 && dot(pos, vel) > 0.0)) break;
            float dt = clamp(0.07 * r, 0.02, 2.4);
            float r2 = max(r * r, 1.0e-4);
            vel -= 1.65 * h2 * pos / (r2 * r2 * r) * dt;
            float vl = length(vel);
            if (vl > 1.0e-5) vel /= vl;
            else break;
            vec3 next = pos + vel * dt * r * 0.35;
            float nextSide = dot(next, normal);
            float denom = side - nextSide;
            if (side * nextSide < 0.0 && abs(denom) > 1.0e-6) {
                vec3 hit = mix(pos, next, clamp(side / denom, 0.0, 1.0));
                float rr = length(hit);
                if (rr > DISK_INNER * 0.82 && rr < DISK_OUTER) {
                    vec2 around = vec2(dot(hit, diskX), dot(hit, diskY)) / max(rr, 1.0e-4);
                    float orbit = time * 0.72 * pow(DISK_INNER / max(rr, 1.0e-3), 1.5);
                    float gas = skyFbm3(vec3(turn(around, -orbit) * 3.4, rr * 0.65));
                    float filament = pow(skyRidge2(turn(around, -orbit) * 5.0 + rr), 2.2);
                    float profile = smoothstep(DISK_INNER * 0.88, DISK_INNER * 1.12, rr)
                            * (1.0 - smoothstep(DISK_OUTER * 0.42, DISK_OUTER, rr));
                    float heat = 1.0 - smoothstep(DISK_INNER, DISK_OUTER * 0.7, rr);
                    float doppler = 0.5 + 0.5 * around.x;
                    vec3 velN = vel;
                    float beam = 1.0 - 0.55 * dot(cross(normal, hit) / max(rr, 1.0e-4), velN)
                            * sqrt(DISK_INNER / max(rr, 1.0e-3));
                    beam = clamp(beam, 0.15, 1.6);
                    vec3 hot = mix(orange, gold, heat * heat);
                    vec3 cool = mix(blue, WHITE_HOT, heat * 0.45);
                    vec3 tint = mix(cool, hot, smoothstep(0.15, 0.85, doppler));
                    tint = mix(tint, WHITE_HOT, heat * heat * 0.65);
                    float alpha = profile * clamp(0.4 + gas * 0.7 + filament * 0.5, 0.0, 1.0) * 1.15 * marchWindow;
                    float boost = (0.75 + 2.2 * gas + 1.4 * filament) * beam * beam
                            * pow(DISK_INNER / max(rr, 1.0), 1.45) * 3.2;
                    glow += (1.0 - cover) * alpha * tint * boost;
                    cover += (1.0 - cover) * alpha * 0.96;
                }
            }
            side = nextSide;
            pos = next;
        }
        float mixL = smoothstep(0.35, 0.95, angle);
        float vl2 = length(vel);
        if (vl2 > 1.0e-5) {
            lensed = normalize(mix(vel / vl2, lensed, mixL));
        }
    }

    float ring = exp(-abs(impact - HOLE_CAPTURE * 1.08) * 12.0) * open;
    glow += mix(orange, WHITE_HOT, 0.7) * ring * 1.25 * (1.0 - cover);
    glow += WHITE_HOT * exp(-abs(impact - HOLE_CAPTURE) * 28.0) * open * 0.55;

    vec3 source = right * lensed.x + up * lensed.y + ahead * lensed.z;
    float srcLen = length(source);
    source = srcLen > 1.0e-5 ? source / srcLen : dir;
    // Fade lensing back to real direction at the cone edge — no hard crop
    source = normalize(mix(dir, source, marchWindow * open));

    vec3 bg = deepSpace(source, time, Amounts.y * 0.65);
    float horizon = 1.0 - open;
    vec3 color = bg * open * (1.0 - cover) + glow;
    color *= (1.0 - horizon * 0.99);
    // Outside capture cone, keep cinematic space instead of void crop
    color = mix(deepSpace(dir, time, Amounts.y * 0.65), color, clamp(open + cover + marchWindow, 0.0, 1.0));
    return color;
}

void main() {
    vec3 dir = normalize(skyDirection);
    if (any(isnan(dir)) || any(isinf(dir))) {
        dir = vec3(0.0, 1.0, 0.0);
    }
    float time = Look.z;
    vec3 color;
    float neb = Amounts.y;
#if defined(KRYPTON_SKY_MODE) && KRYPTON_SKY_MODE == 0
    color = deepSpace(dir, time, neb);
#elif defined(KRYPTON_SKY_MODE) && KRYPTON_SKY_MODE == 1
    color = northernSky(dir, time);
#elif defined(KRYPTON_SKY_MODE) && KRYPTON_SKY_MODE == 2
    color = ringedPlanet(dir, time);
#elif defined(KRYPTON_SKY_MODE) && KRYPTON_SKY_MODE == 3
    color = blackHole(dir, time);
#else
    if (Look.x < 0.5) color = deepSpace(dir, time, neb);
    else if (Look.x < 1.5) color = northernSky(dir, time);
    else if (Look.x < 2.5) color = ringedPlanet(dir, time);
    else color = blackHole(dir, time);
#endif
    color = max(color, vec3(0.0));
    if (any(isnan(color)) || any(isinf(color))) {
        color = deepSpace(dir, time, neb);
    }
    float exposure = max(Look.w, 0.2) * mix(0.95, 1.55, nightGate());
    color = 1.0 - exp(-color * exposure);
    color = pow(max(color, vec3(0.0)), vec3(0.9));
    // Keep alpha high at night so muddy gradient underneath cannot bleed through cuts
    float alpha = clamp(max(Look.y, nightGate() * 0.98), 0.0, 1.0);
    fragColor = vec4(color, alpha);
}
