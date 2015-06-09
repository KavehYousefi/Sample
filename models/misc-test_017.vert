// 29.05.2016
// -> "http://faculty.kaust.edu.sa/sites/markushadwiger/Documents/CS380_spring2015_lecture_07.pdf", p. 32-33

#version 120

varying vec3 fragmentNormal;
varying vec3 fragmentPosition;

void main (void)
{
  fragmentNormal   = normalize (gl_NormalMatrix    * gl_Normal);
  fragmentPosition = vec3      (gl_ModelViewMatrix * gl_Vertex);
  
  gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}
