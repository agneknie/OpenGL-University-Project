# COM3503 3D Computer Graphics Assignment

### My work on the 3D Computer Graphics Module at the University of Sheffield.

## Start
The main start of the program is [Museum.java](src/start/Museum.java) in the [start package](src/start).
When starting the program, make sure JOGL is added to the path.

### From Terminal
- Compile all the classes with `javac`;
- Start the program with `java Museum`. The [Museum.java](src/start/Museum.java) file resides 
  in the [start package](src/start);
- You might need to alter the paths in [TextureLibrary](src/textures/TextureLibrary.java) and
  [Shader](src/core/structure/Shader.java) for the program to find the [images](core/textures/images)
  and the [shaders](src/core/shaders) respectfully.

### Script File
- Start the [start.bat](src/start.bat) file to start the program;
- You might need to alter the paths in [TextureLibrary](src/textures/TextureLibrary.java) and
  [Shader](src/core/structure/Shader.java) for the program to find the [images](core/textures/images)
  and the [shaders](src/core/shaders) respectfully.

## About
The Assignment was to model a museum rum, which consists of different objects with different
specificities. The room has a robot which is exploring and can be displayed in 5 poses.
Some of the core features are outlined below:
- The mobile phone and the egg exhibits have specular and diffuse light mapping applied;
- The spotlight is swinging and its effects can be seen on the ground;
- The view through the window has texture effects applied, hence it appears that the
clouds are moving;
- The robot is a hierarchical model and is capable of making 5 distinct poses by varying its
rotation, eye, lip, ear, head and body movement.
  
## Development
- OpenGL and Java is used for the development of this project. Due to java being used, JOGL is
equipped to work with OpenGL libraries;
- GLSL files are used as vertex and fragment shaders.
