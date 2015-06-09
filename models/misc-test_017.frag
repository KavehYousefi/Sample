// 29.05.2016
// -> "http://faculty.kaust.edu.sa/sites/markushadwiger/Documents/CS380_spring2015_lecture_07.pdf", p. 32-33

#version 120

varying vec3 fragmentNormal;
varying vec3 fragmentPosition;

void main (void)
{
  vec3 normalizedSurfaceNormal;
  vec3 surfaceToLightVector;
  vec3 surfaceToEyeVector;
  vec3 halfVector;
  
  vec4 ambientTerm;
  vec4 diffuseTerm;
  vec4 specularTerm;
  
  if (gl_LightSource[0].position.w == 0.0)
  {
    surfaceToLightVector = normalize (gl_LightSource[0].position.xyz);
  }
  else
  {
    surfaceToLightVector = normalize (gl_LightSource[0].position.xyz - fragmentPosition);
  }
  
  normalizedSurfaceNormal = normalize (fragmentNormal);
  surfaceToEyeVector      = normalize (-fragmentPosition);
  halfVector              = normalize (surfaceToLightVector + surfaceToEyeVector);
  
  ambientTerm  = gl_FrontLightProduct[0].ambient;
  diffuseTerm  = gl_FrontLightProduct[0].diffuse
                 * max (dot (normalizedSurfaceNormal, surfaceToLightVector), 0.0);
  specularTerm = gl_FrontLightProduct[0].specular
                 * pow (max (dot (normalizedSurfaceNormal, halfVector), 0.0),
                        gl_FrontMaterial.shininess);
  
  gl_FragColor = gl_FrontLightModelProduct.sceneColor
                 + ambientTerm
                 + diffuseTerm
                 + specularTerm;
}
