#version 330 core

in vec3 aPos;
in vec3 aNormal;
in vec2 aTexCoord;

out vec4 fragColor;

uniform sampler2D first_texture;
uniform vec3 viewPos;

struct Light {
  vec3 position;
  vec3 ambient;
  vec3 diffuse;
  vec3 specular;
};

uniform Light light1;
uniform Light light2;

struct Material {
  vec3 ambient;
  vec3 diffuse;
  vec3 specular;
  float shininess;
}; 
  
uniform Material material;

vec3 calculateLight(Light light, vec3 norm, vec3 viewDir){
  // Ambient
  vec3 ambient = light.ambient * material.ambient * vec3(texture(first_texture, aTexCoord));

  // Diffuse
  vec3 lightDir = normalize(light.position - aPos);
  float diff = max(dot(norm, lightDir), 0.0);
  vec3 diffuse = light.diffuse * diff * vec3(texture(first_texture, aTexCoord));

  // Specular
  vec3 reflectDir = reflect(-lightDir, norm);
  float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
  vec3 specular = light.specular * spec;    // * vec3(texture(material.specular, aTexCoord))

  return (ambient + diffuse + specular);
}

void main() {

  vec3 norm = normalize(aNormal);
  vec3 viewDir = normalize(viewPos - aPos);

  // Calculates impact of first general light
  vec3 result = calculateLight(light1, norm, viewDir);
  // Calculates impact of second general light
  result += calculateLight(light2, norm, viewDir);

  fragColor = vec4(result, 1.0);
}