// 12.06.2015
// Anisotropic Ward shader.
// "Advanced Lighting and Materials with Shaders", p. 149-151
// Another useful source: "http://content.gpwiki.org/index.php/D3DBook:%28Lighting%29_Ward"

#version 120

varying vec3 fragmentPosition;
varying vec3 fragmentNormal;


void main (void)
{
  fragmentPosition = vec3      (gl_ModelViewMatrix * gl_Vertex);
  fragmentNormal   = normalize (gl_NormalMatrix    * gl_Normal);
  
  gl_Position      = ftransform ();
}
