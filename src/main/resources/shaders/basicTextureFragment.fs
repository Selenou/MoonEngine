#version 330 core
out vec4 FragColor;

in vec2 texCoord0;

uniform sampler2D sampler;

void main(){
    FragColor = texture2D(sampler, texCoord0.xy);
}