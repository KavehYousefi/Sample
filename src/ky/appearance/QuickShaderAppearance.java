// 10.05.2015

package ky.appearance;

import java.io.IOException;

import javax.media.j3d.GLSLShaderProgram;
import javax.media.j3d.Shader;
import javax.media.j3d.ShaderAppearance;
import javax.media.j3d.ShaderAttribute;
import javax.media.j3d.ShaderAttributeSet;
import javax.media.j3d.ShaderProgram;
import javax.media.j3d.SourceCodeShader;

import com.sun.j3d.utils.shader.StringIO;


public class QuickShaderAppearance
extends      ShaderAppearance
{
  // Uniform variables may be omitted, if both the uniformVariableNames
  // and the uniformVariableValues are omitted.
  public QuickShaderAppearance
  (
    String            vertexShaderFilePath,
    String            fragmentShaderFilePath,
    String[]          uniformVariableNames,
    ShaderAttribute[] uniformVariableValues,
    String[]          attributeVariableNames
  )
  {
    super ();
    
    String             vertexShaderCode   = null;
    Shader             vertexShader       = null;
    String             fragmentShaderCode = null;
    Shader             fragmentShader     = null;
    Shader[]           shaders            = null;
    ShaderProgram      shaderProgram      = null;
    ShaderAttributeSet shaderAttributeSet = null;
    
    try
    {
      vertexShaderCode   = StringIO.readFully (vertexShaderFilePath);
      fragmentShaderCode = StringIO.readFully (fragmentShaderFilePath);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    vertexShader   = new SourceCodeShader
    (
      Shader.SHADING_LANGUAGE_GLSL,
      Shader.SHADER_TYPE_VERTEX,
      vertexShaderCode
    );
    fragmentShader = new SourceCodeShader
    (
      Shader.SHADING_LANGUAGE_GLSL,
      Shader.SHADER_TYPE_FRAGMENT,
      fragmentShaderCode
    );
    shaders            = new Shader[] {vertexShader, fragmentShader};
    shaderProgram      = new GLSLShaderProgram  ();
    shaderAttributeSet = new ShaderAttributeSet ();
    
    // ADD UNIFORMS TO ShaderAttributeSet HERE...
    if (uniformVariableValues != null)
    {
      for (ShaderAttribute uniformVariable : uniformVariableValues)
      {
        shaderAttributeSet.put (uniformVariable);
      }
    }
    
    shaderProgram.setShaders   (shaders);
    this.setShaderProgram      (shaderProgram);
    this.setShaderAttributeSet (shaderAttributeSet);
    
    // Pass the uniform variable names to the shader program.
    shaderProgram.setShaderAttrNames (uniformVariableNames);
    // Pass the attribute variable names to the shader program.
    shaderProgram.setVertexAttrNames (attributeVariableNames);
  }
  
  public QuickShaderAppearance
  (
    String vertexShaderFilePath,
    String fragmentShaderFilePath
  )
  {
    this
    (
      vertexShaderFilePath,
      fragmentShaderFilePath,
      null,
      null,
      null
    );
  }
}