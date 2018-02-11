#version 330

out vec4 fragColor;

uniform float customColor;

void main(){
    fragColor = vec4(customColor,0.2,1.0,0.1);
}