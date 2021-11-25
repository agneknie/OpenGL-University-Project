#version 330 core

out vec4 fragColor;

uniform vec3 lightIntensity;

void main() {
  fragColor = vec4(lightIntensity, 1.0f);
}