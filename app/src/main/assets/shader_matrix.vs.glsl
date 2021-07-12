#version 300 es

uniform mat4 uMVPMatrix;

//attribute vec4 vPosition;
layout (location = 0) in vec4 vPosition;
out vec4 vColor;

void main() {
    gl_Position = uMVPMatrix * vPosition;
//    gl_Position = vPosition;
    vColor = vec4(1.0, 0.0, 0.0, 1.0);
}