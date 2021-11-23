import camera.Camera;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import gmaths.Vec3;

/**
 * Class, which handles the main program- Museum Assignment for COM3503.
 *
 * Made with code examples from COM3503 Online Tutorial Materials by
 * Dr Steve Maddock at The University of Sheffield.
 *
 * @author Agne Knietaite
 */
public class Museum_GLEventListener implements GLEventListener {
    private Camera camera;

    public Museum_GLEventListener(Camera camera){
        this.camera = camera;
        this.camera.setPosition(new Vec3(4f,12f,18f));
    }
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
