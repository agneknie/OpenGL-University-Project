# COM3503 3D Computer Graphics Assignment

### My assignment for the 3D Computer Graphics Module at the University of Sheffield. Achieved Grade: 71%.

## Start
The main start of the program is [Museum.java](src/start/Museum.java) in the [start package](src/start).
When starting the program, make sure JOGL is added to the path.

### From Terminal
- Compile all the classes with `javac`;
- Start the program with `java Museum`. The [Museum.java](src/start/Museum.java) file resides 
  in the [start package](src/start);
- You might need to alter the paths in [TextureLibrary](src/textures/TextureLibrary.java) and
  [Shader](src/core/structure/Shader.java) for the program to find the images (src/textures/images)
  and the shaders (src/core/shaders) respectfully.

### Script File
- Start the [start.bat](src/start.bat) file to start the program;
- You might need to alter the paths in [TextureLibrary](src/textures/TextureLibrary.java) and
  [Shader](src/core/structure/Shader.java) for the program to find the images (src/textures/images)
  and the shaders (src/core/shaders) respectfully.
  
### IDE
- The program is developed with IntelliJ IDEA;
- Start the file [Museum.java](src/start/Museum.java);
- You might need to alter the paths in [TextureLibrary](src/textures/TextureLibrary.java) and
  [Shader](src/core/structure/Shader.java) for the program to find the images (src/textures/images)
  and the shaders (src/core/shaders) respectfully if you are using a different IDE.

## About
The Assignment was to model a museum room, which consists of different objects with different
specificities. The room has a robot which is exploring and can be displayed in 5 poses.
Some of the core features are outlined below:
- The mobile phone and the egg exhibits have specular and diffuse light mapping applied;
- The spotlight is swinging and its effects can be seen on the ground;
- The view through the window has texture effects applied, hence it appears that the 
  clouds are moving;
- The robot is a hierarchical model and is capable of making 5 distinct poses by varying its 
  rotation, eye, lip, ear, head and body movement;
- The robot gains awareness that the world around it is an empty dark abyss, hence during animation
it looks at it with caution.

## Maintenance & Extendability

The Assignment was developed in such a way, that it could be extended as a future project if needs be.
Hence, the classes are structured into packages and are aimed to be extended easily. A few examples of
this could be:
- Robot could have more parts added to it, by defining the part in [RobotPartName.java](src/core/objects/constructed/robot/RobotPartName.java)
  and [RobotPart.java](src/core/objects/constructed/robot/RobotPart.java) and extending the relevant constructors in
  [RobotPart.java](src/core/objects/constructed/robot/RobotPart.java) and [Robot.java](src/core/objects/constructed/robot/Robot.java);
- More objects could be added to the scene by extending [ConstructedObjectBase.java](src/core/objects/base/ConstructedObjectBase.java)
  and only implementing the `getCalculatedMatrices()` method, which defines object shape and position;
- The vertex and fragment [shaders](src/core/shaders) could be reused, as their names indicate their primary purpose 
  and are reused throughout the project to save resources;
- User Interface is contained in [InterfacePanel.java](src/ui/InterfacePanel.java) class and it could be easily
  extended to support more button;
- Etc.  
  
## Development
- OpenGL and Java is used for the development of this project. Due to java being used, JOGL is
equipped to work with OpenGL libraries;
- GLSL files are used as vertex and fragment shaders.
