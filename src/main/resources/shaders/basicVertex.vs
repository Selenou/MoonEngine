#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

uniform float customColor;

out vec2 texCoord0;
out vec4 color;

void main(){
    gl_Position = vec4(position, 1.0);
    texCoord0 = texCoord;
    color = vec4(customColor, 0.0, 0.0, 1.0);
}