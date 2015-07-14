// 03.06.2015
// Minnaert material
// "Advanced Lighting and Materials with Shaders", p. 143-144

#version 120

varying vec3 fragmentPosition;
varying vec3 fragmentNormal;


void main (void)
{
  vec3  surfaceToLightVector;
  vec3  surfaceToEyeVector;
  vec3  surfaceNormal;
  float v_dot_n;
  float l_dot_n;
  float irradiance;
  vec4  diffuseColor;
  float power;
  
  diffuseColor = vec4 (1.0, 0.0, 0.0, 1.0);
  power        = 0.8;
  
  if (gl_LightSource[0].position.w == 0.0)
  {
    surfaceToLightVector = normalize (gl_LightSource[0].position.xyz);
  }
  else
  {
    surfaceToLightVector = normalize (gl_LightSource[0].position.xyz - fragmentPosition);
  }
  
  surfaceToEyeVector = normalize (-fragmentPosition);
  surfaceNormal      = normalize ( fragmentNormal);
  
  v_dot_n    = dot (surfaceToEyeVector,   surfaceNormal);
  l_dot_n    = dot (surfaceToLightVector, surfaceNormal);
  irradiance = max (0.0, l_dot_n);
  
  gl_FragColor = gl_LightSource[0].diffuse
                 * pow (v_dot_n * l_dot_n, power)
                 * irradiance;
}
