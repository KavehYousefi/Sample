/* 
 * PROJECT: NyARToolkit Java3d sample program.
 * --------------------------------------------------------------------------------
 * The MIT License
 * Copyright (c) 2008 nyatla
 * airmail(at)ebony.plala.or.jp
 * http://nyatla.jp/nyartoolkit/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package owntest;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.media.j3d.*;

import com.sun.j3d.utils.universe.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.vecmath.*;

import jp.nyatla.nyartoolkit.core.*;
import jp.nyatla.nyartoolkit.java3d.utils.*;

import com.sun.j3d.utils.geometry.Cone;

/**
 * Java3Dサンプルプログラム
 * Hiroマーカ1個の上に、カラーキューブを表示します。
 * このサンプルでは、Java3dのTransformGroupをマーカの位置姿勢に合致させるBehaviorと、
 * 背景のオブジェクト一つを使います。
 *
 */
public class OwnMultiMarkers001 extends JFrame implements NyARSingleMarkerBehaviorListener
{
	private static final long serialVersionUID = -8472866262481865377L;

	private final String CARCODE_FILE = "./Data/patt.hiro";
//	private final String CARCODE_FILE = "./Data/marker_001.pat";

	private final String PARAM_FILE = "./Data/camera_para.dat";

	//NyARToolkit関係
	private NyARSingleMarkerBehaviorHolder nya_behavior;

	private J3dNyARParam ar_param;

	//universe関係
	private Canvas3D canvas;

	private Locale locale;

	private VirtualUniverse universe;

	public static void main(String[] args)
	{
		try {
			OwnMultiMarkers001 frame = new OwnMultiMarkers001();

			frame.setVisible(true);
			Insets ins = frame.getInsets();
			frame.setSize(320 + ins.left + ins.right, 240 + ins.top + ins.bottom);
			frame.startCapture();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onUpdate(boolean i_is_marker_exist, Transform3D i_transform3d)
	{
		/*
		 * TODO:Please write your behavior operation code here.
		 * マーカーの姿勢を元に他の３Dオブジェクトを操作するときは、ここに処理を書きます。*/

	}

	public void startCapture() throws Exception
	{
		nya_behavior.start();
	}

	public OwnMultiMarkers001() throws Exception
	{
		super("Java3D Example NyARToolkit");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				nya_behavior.stop();
				System.exit(0);
			}
		});
		//NyARToolkitの準備
		NyARCode ar_code = NyARCode.createFromARPattFile(new FileInputStream(CARCODE_FILE),16, 16);
		ar_param = J3dNyARParam.loadARParamFile(new FileInputStream(PARAM_FILE));
		ar_param.changeScreenSize(320, 240);

		//localeの作成とlocateとviewの設定
		universe = new VirtualUniverse();
		locale = new Locale(universe);
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		View view = new View();
		ViewPlatform viewPlatform = new ViewPlatform();
		view.attachViewPlatform(viewPlatform);
		view.addCanvas3D(canvas);
		view.setPhysicalBody(new PhysicalBody());
		view.setPhysicalEnvironment(new PhysicalEnvironment());

		//視界の設定(カメラ設定から取得)
		Transform3D camera_3d = ar_param.getCameraTransform();
		view.setCompatibilityModeEnable(true);
		view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
		view.setLeftProjection(camera_3d);

		//視点設定(0,0,0から、Y軸を180度回転してZ+方向を向くようにする。)
		TransformGroup viewGroup = new TransformGroup();
		Transform3D viewTransform = new Transform3D();
		viewTransform.rotY(Math.PI);
		viewTransform.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		viewGroup.setTransform(viewTransform);
		viewGroup.addChild(viewPlatform);
		BranchGroup viewRoot = new BranchGroup();
		viewRoot.addChild(viewGroup);
		locale.addBranchGraph(viewRoot);

		//バックグラウンドの作成
		Background background = new Background();
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(10.0);
		background.setApplicationBounds(bounds);
		background.setImageScaleMode(Background.SCALE_FIT_ALL);
		background.setCapability(Background.ALLOW_IMAGE_WRITE);
		BranchGroup root = new BranchGroup();
		root.addChild(background);

		//TransformGroupで囲ったシーングラフの作成
		TransformGroup transform = new TransformGroup();
		transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transform.addChild(createSceneGraph());
		root.addChild(transform);

		//NyARToolkitのBehaviorを作る。(マーカーサイズはメートルで指定すること)
		nya_behavior = new NyARSingleMarkerBehaviorHolder(ar_param, 30f, ar_code, 0.08);
		//Behaviorに連動するグループをセット
		nya_behavior.setTransformGroup(transform);
		nya_behavior.setBackGround(background);

		//出来たbehaviorをセット
		root.addChild(nya_behavior.getBehavior());
		nya_behavior.setUpdateListener(this);
		
		
//		Bounds sphereBounds = null;
//		sphereBounds = new BoundingSphere (new Point3d (), 1000.0);
		root.addChild (createLight0 (bounds));
		root.addChild (createLight1 (bounds));
		root.addChild (createLight2 (bounds));
		
		
		//表示ブランチをLocateにセット
		locale.addBranchGraph(root);

		//ウインドウの設定
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
	}

	/**
	 * シーングラフを作って、そのノードを返す。
	 * このノードは40mmの色つき立方体を表示するシーン。ｚ軸を基準に20mm上に浮かせてる。
	 * @return
	 */
	private Node createSceneGraph()
	{
		TransformGroup tg = new TransformGroup();
		Transform3D mt = new Transform3D();
//		mt.setTranslation(new Vector3d(0.00, 0.0, 20 * 0.001));
		// 大きさ 40mmの色付き立方体を、Z軸上で20mm動かして配置）
		tg.setTransform(mt);
		
//		tg.addChild(new ColorCube(20 * 0.001));
//		tg.addChild (new Cone
//		(
//		  0.02f,
//		  0.02f,
//		  Cone.GENERATE_NORMALS,
//		  createAppearanceMap ().get ("gold")
//		));
		
//    tg.addChild (new Box
//    (
//      0.02f,
//      0.02f,
//      0.02f,
//      Box.GENERATE_NORMALS,
//      createAppearanceMap ().get ("gold")
//    ));
		
//    tg.addChild (new Cylinder
//    (
//      0.02f,
//      0.02f,
//      Cylinder.GENERATE_NORMALS,
//      createAppearanceMap ().get ("gold")
//    ));
		
		
//		BranchGroup objectModel = null;
//		objectModel = new ObjectLoader006_XJ3D ().getScene ();
		
//		tg.addChild (objectModel);
//		objectModel.setBoundsAutoCompute (true);
//		System.out.println (objectModel.getBounds ());
		
		
		Transform3D rotation = new Transform3D ();
		rotation.setRotation (new AxisAngle4d (1.0, 0.0, 0.0, Math.PI / 2.0));
		rotation.mul (mt);
		tg.setTransform (rotation);
		
//    tg.addChild (new Cylinder
//    (
//      0.02f,
//      0.02f,
//      Cylinder.GENERATE_NORMALS,
//      createAppearanceMap ().get ("blue-plastic")
//    ));
		
    tg.addChild (new Cone
    (
      0.02f,
      0.02f,
      Cone.GENERATE_NORMALS,
      createAppearanceMap ().get ("blue-plastic")
    ));
		
    
    
    
    MarkerDetector markerDetector = null;
    MarkerModel    markerModel01  = null;
    
    markerDetector = new MarkerDetector ();
    markerModel01  = new MarkerModel    ("./Data/patt.hiro");
    
    markerModel01.setCharacterObject (new CharacterConeObject ());
    markerDetector.addMarkerModel    (markerModel01);
    
    
    
    
		return tg;
	}
	
	
	
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private AmbientLight createLight0 (Bounds influencingBounds)
  {
    AmbientLight light = new AmbientLight ();
    
    light.setColor             (new Color3f (Color.BLACK));
    light.setInfluencingBounds (influencingBounds);
    
    return light;
  }
  
  private DirectionalLight createLight1 (Bounds influencingBounds)
  {
    DirectionalLight light = new DirectionalLight ();
    
    light.setColor             (new Color3f (Color.GRAY));
    light.setDirection         (2.0f, -2.0f, 0.0f);
    light.setInfluencingBounds (influencingBounds);
    
    return light;
  }
  
  private PointLight createLight2 (BoundingSphere bounds)
  {
    PointLight pointLight = null;
    
    pointLight = new PointLight     ();
    pointLight.setAttenuation       (0.5f, 0.0f, 0.0f);
    pointLight.setColor             (new Color3f (Color.WHITE));
    pointLight.setPosition          (1.0f, 0.0f, 1.0f);
    pointLight.setInfluencingBounds (bounds);
    
    return pointLight;
  }
	
  // Appearances taken from -> "http://cs.boisestate.edu/~tcole/cs221/fall08/vg/cgslides09.ppt"
  private Map<String, Appearance> createAppearanceMap ()
  {
    Map<String, Appearance> appearanceMap = null;
    Color3f                 ambDiffColor  = null;
    Color3f                 specularColor = null;
    Color3f                 emissiveColor = null;
    float                   shininess     = 1.0f;
    
    appearanceMap = new HashMap<String, Appearance> ();
    
    // Copper:
    ambDiffColor  = new Color3f (0.3f, 0.1f, 0.0f);
    specularColor = new Color3f (0.75f, 0.3f, 0.0f);
    emissiveColor = new Color3f (0.0f, 0.0f, 0.0f);
    shininess     = 10.0f;
    appearanceMap.put
    (
      "copper",
      createAppearanceFor (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Red alloy:
    ambDiffColor  = new Color3f (0.34f, 0.0f, 0.34f);
    specularColor = new Color3f (0.84f, 0.0f, 0.0f);
    emissiveColor = new Color3f (0.0f, 0.0f, 0.0f);
    shininess     = 15.0f;
    appearanceMap.put
    (
      "red-alloy",
      createAppearanceFor (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Gold:
    ambDiffColor  = new Color3f (0.49f, 0.34f, 0.0f);
    specularColor = new Color3f (0.89f, 0.79f, 0.0f);
    emissiveColor = new Color3f (0.0f, 0.0f, 0.0f);
    shininess     = 17.0f;
    appearanceMap.put
    (
      "gold",
      createAppearanceFor (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Aluminium:
    ambDiffColor  = new Color3f (0.37f, 0.37f, 0.37f);
    specularColor = new Color3f (0.89f, 0.89f, 0.89f);
    emissiveColor = new Color3f (0.0f, 0.0f, 0.0f);
    shininess     = 17.0f;
    appearanceMap.put
    (
      "aluminium",
      createAppearanceFor (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Black onyx:
    ambDiffColor  = new Color3f (0.0f, 0.0f, 0.0f);
    specularColor = new Color3f (0.72f, 0.72f, 0.72f);
    emissiveColor = new Color3f (0.0f, 0.0f, 0.0f);
    shininess     = 23.0f;
    appearanceMap.put
    (
      "black-onyx",
      createAppearanceFor (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Blue plastic:
    ambDiffColor  = new Color3f (0.2f, 0.2f, 0.7f);
    specularColor = new Color3f (0.85f, 0.85f, 0.85f);
    emissiveColor = new Color3f (0.0f, 0.0f, 0.0f);
    shininess     = 22.0f;
    appearanceMap.put
    (
      "blue-plastic",
      createAppearanceFor (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    return appearanceMap;
  }
  
  private static Appearance createAppearanceFor
  (
    Color3f ambDiffColor,
    Color3f specularColor,
    Color3f emissiveColor,
    float   shininess
  )
  {
    Appearance        appearance        = new Appearance ();
    PolygonAttributes polygonAttributes = null;
    Material          material          = null;
    
    polygonAttributes = new PolygonAttributes ();
    polygonAttributes.setCullFace (PolygonAttributes.CULL_NONE);
    
    material = new Material
    (
      ambDiffColor,
      emissiveColor,
      ambDiffColor,
      specularColor,
      shininess
    );
    material.setLightingEnable (true);
    
    appearance.setPolygonAttributes (polygonAttributes);
    appearance.setMaterial          (material);
    
    return appearance;
  }
}
