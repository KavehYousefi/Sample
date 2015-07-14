// 27.06.2015

#version 120

varying vec3 fragmentPosition;
varying vec3 fragmentNormal;


void main (void)
{
  fragmentPosition = vec3      (gl_ModelViewMatrix * gl_Vertex);
  fragmentNormal   = normalize (gl_NormalMatrix    * gl_Normal);
  
  gl_Position      = ftransform ();
}
