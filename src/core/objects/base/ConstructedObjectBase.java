package core.objects.base;

import com.jogamp.opengl.GL3;
import core.structure.Model;
import gmaths.Mat4;

import java.util.List;

/**
 * Base class to form constructed objects, which use a single
 * type of object.base model for their construction.
 *
 * @author Agne Knietaite
 */
public abstract class ConstructedObjectBase {

    private final Model model;
    private List<Mat4> calculatedMatrices;

    public ConstructedObjectBase(Model model){
        this.model = model;

        // Calculates the matrix/matrices once, when initialising the object
        calculatedMatrices = getCalculatedMatrices();
    }

    /**
     * Method which replaces a matrix in the calculatedMatrices List
     * at the index location with the given matrix.
     *
     * @param index location of the element to replace
     * @param matrix matrix to replace with
     */
    protected void setCalculatedMatrix(int index, Mat4 matrix){
        calculatedMatrices.set(index, matrix);
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
