#version 330 core

in vec2 outTexCoord;
in vec3 mVertexNormal;
in vec3 mVertexPosition;

out vec4 FragColor;

uniform sampler2D sampler;
uniform vec3 diffuseColor;
uniform vec3 cameraPosition;

void main(){

    //ambiant
    vec3 lightColor = vec3(1,1,1);
    float ambientStrength = 0.1;
    vec3 ambient = ambientStrength * lightColor;

    vec3 norm = normalize(mVertexNormal);

    //diffuse
    vec3 lightPos = vec3(0,0,1);
    vec3 lightDir = normalize(lightPos - mVertexPosition);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    //specular
    float specularStrength = 0.5;
    vec3 cameraDirection = normalize(cameraPosition - mVertexPosition);
    vec3 fromLightDirection = -lightDir;
    vec3 reflectDir = reflect(fromLightDirection, norm);
    float specularFactor = max(dot(cameraDirection, reflectDir), 0.0);
    float specularPower = 32;
    specularFactor = pow(specularFactor, specularPower);
    vec3 specular = specularStrength * specularFactor * lightColor;

    vec3 result = (ambient + diffuse + specular) * vec3(1,1,1); //obj color
    FragColor = texture(sampler, outTexCoord.xy) * vec4(result, 1.0);
}