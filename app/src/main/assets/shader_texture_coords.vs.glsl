#version 300 es

layout (location = 0) in vec4 ivPosition;

out vec3 vTextureCoord;

void main() {
    gl_Position = ivPosition;
    vTextureCoord = vec3(ivPosition.xyz);
}