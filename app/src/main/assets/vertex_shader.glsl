#version 300 es
//in vec4 vPosition;
layout (location = 0) in vec4 vPosition;

//uniform mat4 uMatrix;

out vec4 vColor;
void main() {
//    gl_Position = uMatrix * vPosition;
    gl_Position = vPosition;
    gl_PointSize = 10.0;
    vColor = vec4(1.0, 0.0, 0.0, 1.0);
}