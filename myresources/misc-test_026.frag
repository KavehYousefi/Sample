// 27.06.2015
// -> "http://www.csee.umbc.edu/~olano/s2006c03/noise/shaders/fenv.glsl"
// -> "http://kylehalladay.com/blog/tutorial/2014/02/18/Fresnel-Shaders-From-The-Ground-Up.html"
// -> "http://http.developer.nvidia.com/CgTutorial/cg_tutorial_chapter07.html"

#version 120

uniform float fresnelBias;
uniform float fresnelScale;
uniform float fresnelPower;
uniform vec4  fresnelColor;

varying vec3 fragmentPosition;
varying vec3 fragmentNormal;


void main (void)
{
  vec3  surfaceNormal;
  vec3  surfaceToLightVector;
  vec3  surfaceToEyeVector;
  vec3  halfVector;
  vec4  ambientColor;
  vec4  diffuseColor;
  vec4  emissiveColor;
  vec4  specularColor;
  float diffuseTerm;
  float specularTerm;
  
  surfaceNormal = normalize (fragmentNormal);
  
  // Directional light? => Is already a vector.
  if (gl_LightSource[0].position.w == 0.0)
  {
    surfaceToLightVector = normalize (vec3 (gl_LightSource[0].position));
  }
  // Positional light? => Convert to a vector.
  else
  {
    surfaceToLightVector = normalize (vec3 (gl_LightSource[0].position.xyz - fragmentPosition));
  }
  
  vec3 eyePosition;
  eyePosition = vec3 (0.0, 0.0, 0.0);
  
  surfaceToEyeVector = normalize (vec3 (eyePosition - fragmentPosition));
  halfVector         = normalize (surfaceToLightVector + surfaceToEyeVector);
  
  diffuseTerm  = dot (surfaceNormal, surfaceToLightVector);
  diffuseTerm  = max (0.0, diffuseTerm);
  
  specularTerm = dot (surfaceNormal, halfVector);
  specularTerm = max (0.0, specularTerm);
  specularTerm = pow (specularTerm, gl_FrontMaterial.shininess);
  
  ambientColor  = gl_LightSource[0].ambient  * gl_FrontMaterial.ambient;
  diffuseColor  = gl_LightSource[0].diffuse  * gl_FrontMaterial.diffuse  * diffuseTerm;
  emissiveColor = vec4 (0.0, 0.0, 0.0, 1.0);
  specularColor = gl_LightSource[0].specular * gl_FrontMaterial.specular * specularTerm;
  
  gl_FragColor  = emissiveColor + ambientColor + diffuseColor + specularColor;
  
  
  
  //// Calculate Fresnel term. /////////////////////////////////////////
  
  float fresnelTerm;
  vec3  eyeToSurfaceVector;            // Called "I" in Nvidia source: I = normalize (positionW – eyePositionW).
  float cosBetweenEyeVectorAndNormal;  // dot(I, N)  in Nvidia source.
  
  eyeToSurfaceVector           = -surfaceToEyeVector;
  /* Without own variable for vector from eye to surface:
   *   cosBetweenEyeVectorAndNormal = dot (-surfaceToEyeVector, surfaceNormal);
   * 
   * eyeToSurfaceVector is equal to:
   *   eyeToSurfaceVector = normalize (fragmentPosition - eyePosition);
   */
  cosBetweenEyeVectorAndNormal = dot (eyeToSurfaceVector, surfaceNormal);
  fresnelTerm                  = fresnelBias + fresnelScale * pow (1.0 + cosBetweenEyeVectorAndNormal, fresnelPower);
  //fresnelTerm                  = max (0.0, min (1.0, fresnelTerm));
  
  gl_FragColor = mix (gl_FragColor, fresnelColor, fresnelTerm);
}
