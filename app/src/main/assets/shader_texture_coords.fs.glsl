#version 300 es

uniform samplerCube uCubeMap;

in vec3 vTextureCoord;

out vec4 fragColor;

void main() {
    fragColor = texture(uCubeMap, vTextureCoord);
}