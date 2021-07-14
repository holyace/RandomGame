#version 300 es

uniform sampler2D uTextureUnit;

in vec4 vColor;
in vec2 vTextureCoord;

out vec4 fragColor;

void main() {
    fragColor = texture(uTextureUnit, vTextureCoord) * vColor;
}