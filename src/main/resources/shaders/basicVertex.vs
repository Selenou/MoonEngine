#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

uniform float customColor;
uniform mat4 transform;

out vec2 texCoord0;
out vec4 color;

void main(){
    gl_Position = transform * vec4(position, 1.0);
    texCoord0 = texCoord;
    color = vec4(clamp(position, 0.0, 1.0), 1.0);
}