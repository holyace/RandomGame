#version 300 es

attribute vec3 position;
attribute vec3 normal;
attribute vec2 inputTextureCoordinate;

varying vec3 faceNormal;
varying vec2 textureCoordinate;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    faceNormal = normal;
    textureCoordinate = inputTextureCoordinate;
    gl_Position = projection * view * model * vec4(position.xyz, 1);
}