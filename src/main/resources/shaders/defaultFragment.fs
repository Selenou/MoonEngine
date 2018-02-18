#version 330 core

in vec4 color;
in vec2 texCoord0;
out vec4 FragColor;

uniform sampler2D sampler;

void main(){
    FragColor = texture(sampler, texCoord0.xy) * color;
}