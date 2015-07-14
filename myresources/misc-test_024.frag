// 12.06.2015
// Anisotropic Ward shader.
// "Advanced Lighting and Materials with Shaders", p. 149-151
// Another useful source: "http://content.gpwiki.org/index.php/D3DBook:%28Lighting%29_Ward"

#version 120

varying vec3 fragmentPosition;
varying vec3 fragmentNormal;


void main (void)
{
  vec3  surfaceNormal;
  vec3  surfaceToLightVector;
  vec3  surfaceToEyeVector;
  vec3  halfVector;
  
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
  
  
  
  
  //// Anisotropic ward shader. ////////////////////////////////////////
  
  /* <!> IMPORTANT <!>
   *   HLSL replaces missing values for float4 variables like this:
   *     x = 0.0
   *     y = 0.0
   *     z = 0.0
   *     w = 0.0
   *   This is important, when implementing the variables
   *   "firstTerm", "secondTerm" and "thirdTerm".
   *   See: -> "http://stackoverflow.com/questions/29728349/hlsl-sv-position-why-how-from-float3-to-float4"
   */
  
  const float PI = 3.14159;
  
  vec2  roughnessParams;
  float cosTheta;
  float cosDelta;
  float firstTerm;
  float secondTerm;
  vec3  direction;
  vec3  x;
  vec3  y;
  float hDotX;
  float hDotY;
  float hDotN;
  float a;
  float b;
  float thirdTerm;
  //vec4  irradiance;      // Using vec4.
  float irradiance;
  
  //vec4  specularTerm;    // Called "specular" in the source.
  float specularTerm;
  vec4  diffuseColor;    // D
  vec4  specularColor;   // S
  vec4  fragmentColor;
  
  /* Roughness and direction could be implemented as
   * input parameters.
   */
  roughnessParams = vec2 (0.9, 0.1);
  //roughnessParams = vec2 (roughness, roughness / 9.0);
  direction       = vec3 (0.0, 0.0, 1.0);
  
  cosTheta   = dot (surfaceNormal, surfaceToLightVector);
  cosDelta   = dot (surfaceNormal, surfaceToEyeVector);
  // 1 / sqrt ((N . L) * (N . V))
  firstTerm  = 1.0 / sqrt (cosTheta * cosDelta);
  // 1 / (4*PI * sigmaX * sigmaY)
  secondTerm = 1.0 / (4.0 * PI * roughnessParams.x * roughnessParams.y);
  x          = normalize (cross (surfaceNormal, direction));
  y          = normalize (cross (surfaceNormal, x));
  hDotX      = dot (halfVector, x);
  hDotY      = dot (halfVector, y);
  hDotN      = dot (halfVector, surfaceNormal);
  a          = -2.0 * (pow ((hDotX / roughnessParams.x), 2.0) +
                       pow ((hDotY / roughnessParams.y), 2.0));
  b          =  1.0 + hDotN;
  thirdTerm  = exp (a / b);
  // (N . L)
  //irradiance = vec4 (max (0.0, cosTheta), 0.0, 0.0, 1.0); // Using vec4. => Probably wrong.
  //irradiance = vec4 (max (0.0, cosTheta));                // Using vec4 with HLSL "scalar promotion".
  irradiance = max (0.0, cosTheta);
  
  /*
  // Using vec4. => Probably wrong.
  specularTerm  = vec4 (firstTerm,  0.0, 0.0, 1.0) *
                  vec4 (secondTerm, 0.0, 0.0, 1.0) *
                  vec4 (thirdTerm,  0.0, 0.0, 1.0);
  */
  /*
  // Using vec4 with HLSL "scalar promotion": vec4(a) = vec4(a, a, a,a )
  specularTerm = vec4 (firstTerm) * vec4 (secondTerm) * vec4 (thirdTerm);
   */
  specularTerm  = firstTerm * secondTerm * thirdTerm;
  specularColor = gl_LightSource[0].specular * gl_FrontMaterial.specular * specularTerm;
  diffuseColor  = gl_LightSource[0].diffuse  * gl_FrontMaterial.diffuse;
  //diffuseColor  = gl_LightSource[0].diffuse  * gl_FrontMaterial.diffuse * max (0.0, dot (surfaceNormal, surfaceToLightVector));
  
  //////////////////////////////////////////////////////////////////////
  
  fragmentColor = fragmentColor
                  + ((diffuseColor + specularColor) * irradiance);
                  + gl_LightSource[0].ambient  * gl_FrontMaterial.ambient;
  
  gl_FragColor = fragmentColor;
}
