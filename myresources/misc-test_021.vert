// 03.06.2015
// Minnaert material
// "Advanced Lighting and Materials with Shaders", p. 143-144

#version 120

varying vec3 fragmentPosition;
varying vec3 fragmentNormal;


void main (void)
{
  fragmentPosition = vec3 (gl_ModelViewMatrix * gl_Vertex);
  fragmentNormal   = normalize (gl_NormalMatrix * gl_Normal);
  
  gl_Position      = ftransform ();
}
