#version 330 core

in vec2 outTexCoord;
in vec3 mVertexNormal;
in vec3 mVertexPosition;

out vec4 FragColor;

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
    sampler2D diffuseMap;
    sampler2D specularMap;
};

struct Light {
    vec3 position;
    vec3 color;
    float intensity;
};

uniform vec3 cameraPosition;
uniform Material material;
uniform Light light;

void main(){
    //ambient
    vec3 ambient = material.ambient * light.color * vec3(texture(material.diffuseMap, outTexCoord.xy));

    //diffuse
    vec3 normalNormalized = normalize(mVertexNormal);
    vec3 lightDirection = normalize(light.position - mVertexPosition);
    float diff = max(dot(normalNormalized, lightDirection), 0.0);
    vec3 diffuse = light.color * diff * material.diffuse * light.intensity * vec3(texture(material.diffuseMap, outTexCoord.xy));

    //specular
    vec3 cameraDirection = normalize(cameraPosition - mVertexPosition);
    vec3 reflectDirection = reflect(-lightDirection, normalNormalized);
    float specularFactor = pow(max(dot(cameraDirection, reflectDirection), 0.0), material.shininess);
    vec3 specular = light.color * material.specular * specularFactor * vec3(texture(material.specularMap, outTexCoord.xy));

    //total
    vec3 result = ambient + diffuse + specular;
    FragColor = vec4(result, 1.0);
}