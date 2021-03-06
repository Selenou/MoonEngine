#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

uniform mat4 MVP;

out vec2 texCoord0;
out vec4 debugColor;

void main(){
    gl_Position = MVP * vec4(position, 1.0);
    texCoord0 = texCoord;
    debugColor = vec4(clamp(position, 0.0, 1.0), 1.0);
}