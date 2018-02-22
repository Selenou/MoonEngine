#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

uniform mat4 viewProjectionMatrix;
uniform mat4 modelMatrix;
uniform mat4 normalMatrix;

out vec2 outTexCoord;
out vec3 mVertexNormal;
out vec3 mVertexPosition;

void main(){
    vec4 mPosition = modelMatrix * vec4(position, 1.0);
    gl_Position = viewProjectionMatrix * mPosition;
    mVertexNormal = (normalMatrix * vec4(normal, 0.0)).xyz;
    mVertexPosition = mPosition.xyz;
    outTexCoord = texCoord;
}