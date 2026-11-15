#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;

out vec3 skyDirection;

void main() {
    // World/sky direction stays in cube-local space so celestials stay sky-locked.
    skyDirection = Position;

    // Strip translation from ModelView so the cube stays camera-centered.
    // Without this, leftover view translation parks the skybox at world origin and
    // ultrawide FOV only sees a rectangular slice (hard vertical cuts / cropped hole).
    vec3 view = mat3(ModelViewMat) * Position;
    // Keep it comfortably outside the near plane across wide FOVs.
    float len = length(view);
    view = (len > 1.0e-4 ? view * (100.0 / len) : vec3(0.0, 0.0, -100.0));
    vec4 clip = ProjMat * vec4(view, 1.0);
    // Classic skybox far-plane trick — always behind world geometry.
    gl_Position = clip.xyww;
}
