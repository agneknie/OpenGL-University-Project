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

struct Spotlight {
  vec3 position;
  vec3 ambient;
  vec3 diffuse;
  vec3 specular;

  vec3 direction;
  float cutOff;
};

uniform Light light1;
uniform Light light2;
uniform Spotlight spotlight;

struct Material {
  vec3 ambient;
  vec3 diffuse;
  vec3 specular;
  float shininess;
}; 
  
uniform Material material;

// Calculates the light impact of the general lights
vec3 calculateLight(Light light, vec3 norm, vec3 viewDir){
  // Ambient
  vec3 ambient = light.ambient * vec3(texture(first_texture, aTexCoord));

  // Diffuse
  vec3 lightDir = normalize(light.position - aPos);
  float diff = max(dot(norm, lightDir), 0.0);
  vec3 diffuse = light.diffuse * diff * vec3(texture(first_texture, aTexCoord));

  // Specular
  vec3 reflectDir = reflect(-lightDir, norm);
  float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
  vec3 specular = light.specular * spec;

  return (ambient + diffuse + specular);
}

// Calcualtes the light impact of the spotlight
vec3 calculateSpotlight(Spotlight spotlight, vec3 norm, vec3 aPos, vec3 viewDir){
  // Initialisation
  vec3 ambient = vec3(0.0, 0.0, 0.0);
  vec3 diffuse = vec3(0.0, 0.0, 0.0);
  vec3 specular = vec3(0.0, 0.0, 0.0);

  // Variables for light direction and spotlight area determination
  vec3 lightDir = normalize(spotlight.position - aPos);
  float theta = dot(lightDir, normalize(-spotlight.direction));

  // If fragment is in the spotlight 'cone', applies the light
  if(theta > spotlight.cutOff){
    // Ambient
    ambient = spotlight.ambient * vec3(texture(first_texture, aTexCoord));

    // Diffuse
    float diff = max(dot(norm, lightDir), 0.0);
    diffuse = spotlight.diffuse * diff * vec3(texture(first_texture, aTexCoord));

    // Specular
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    specular = spotlight.specular * spec;
  }

  // Reduces the intensity of the spotlight for a neater effect
  float normalise = 0.2;
  return (ambient*normalise + diffuse*normalise + specular*normalise);
}

void main() {

  vec3 norm = normalize(aNormal);
  vec3 viewDir = normalize(viewPos - aPos);


  // Calculates impact of first general light
  vec3 result = calculateLight(light1, norm, viewDir);

  // Calculates impact of second general light
  result += calculateLight(light2, norm, viewDir);

  // Calculates the impact of the spotlight
  result += calculateSpotlight(spotlight, norm, aPos, viewDir);

  fragColor = vec4(result, 1.0);
}