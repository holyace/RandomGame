#version 300 es

layout (location = 0) in vec4 ivPosition;
layout (location = 1) in vec3 ivColor;
layout (location = 2) in vec2 ivTextureCoord;

out vec4 vColor;
out vec2 vTextureCoord;

void main() {
    gl_Position = ivPosition;
    vColor = vec4(ivColor, 1f);
    vTextureCoord = ivTextureCoord;
}