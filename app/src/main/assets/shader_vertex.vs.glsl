#version 300 es
layout (location = 0) in vec4 vPosition;

out vec4 vColor;
void main() {
    gl_Position = vPosition;
    gl_PointSize = 10.0;
    vColor = vec4(1.0, 0.0, 0.0, 1.0);
}