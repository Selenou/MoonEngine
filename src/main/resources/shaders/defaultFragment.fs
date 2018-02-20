#version 330 core

in vec2 texCoord0;
in vec4 debugColor;

out vec4 FragColor;

uniform sampler2D sampler;
uniform vec3 diffuseColor;

void main(){
    vec4 textureColor = texture(sampler, texCoord0.xy);

    if(textureColor == vec4(0,0,0,1))
        FragColor = debugColor;
    else
        FragColor = texture(sampler, texCoord0.xy) * vec4(diffuseColor,1.0);
}