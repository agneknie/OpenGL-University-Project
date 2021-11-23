package objects;

import com.jogamp.opengl.GL3;
import core.Model;
import gmaths.Mat4;

import java.util.List;

/**
 * Base class to collect objects, which use a single
 * type of model for their construction.
 *
 * @author Agne Knietaite
 */
public abstract class ObjectBase {

    private final Model model;
    private List<Mat4> calculatedMatrices;

    public ObjectBase(Model model){
        this.model = model;

        // Calculates the matrix/matrices once, when initialising the object
        calculatedMatrices = getCalculatedMatrices();
    }

    /**
     * Calculates and assigns matrix/matrices, which are needed for the
     * rendering of this collective object.
     */
    protected abstract List<Mat4> getCalculatedMatrices();

    /**
     * Renders all object components thus rendering
     * the object itself. Instead of recalculating the matrices each time,
     * looks up already calculated matrices.
     *
     * @param gl
     */
    public void render(GL3 gl){
        for(Mat4 calculatedMatrix : calculatedMatrices){
            model.setModelMatrix(calculatedMatrix);
            model.render(gl);
        }
    }
}
