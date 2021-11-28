package core.objects.base;

import com.jogamp.opengl.GL3;
import core.structure.Model;
import gmaths.Mat4;

import java.util.List;

/**
 * Base class to form constructed objects from objects.base.
 * Capable of using up to two different models.
 *
 * If one model is used, any number of objects.base can be used
 * to construct the full object (e.g. SideWall, FrontWall, Floor).
 *
 * If two models are used, one object.base can be used for each model
 * to construct the full object (e.g. MobilePhone, ShiningEgg).
 * In this case, first matrix in the list is matched with first model,
 * second matrix in the list is matched with second model.
 *
 * @author Agne Knietaite, 2021
 */
public abstract class ConstructedObjectBase {

    private final Model model;
    private Model model2;

    private List<Mat4> calculatedMatrices;

    protected ConstructedObjectBase(Model model){
        this.model = model;

        // Calculates the matrix/matrices once, when initialising the object
        calculatedMatrices = getCalculatedMatrices();
    }

    protected ConstructedObjectBase(Model model, Model model2){
        this.model = model;
        this.model2 = model2;

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
     */
    public void render(GL3 gl){
        // If only one type of model is provided
        if(model2 == null){
            for(Mat4 calculatedMatrix : calculatedMatrices){
                model.setModelMatrix(calculatedMatrix);
                model.render(gl);
            }
        }

        // If two models are provided, checks if only one object.base matrix is calculated per model
        else if(calculatedMatrices.size()==2){
            // First matrix in the list is matched with first model
            model.setModelMatrix(calculatedMatrices.get(0));
            model.render(gl);

            // Second matrix in the list is matched with second model
            model2.setModelMatrix(calculatedMatrices.get(1));
            model2.render(gl);
        }

        // If something is wrong, alerts the user that render was not successful
        else{
            System.err.println("Rendering of object was not successful in ConstructedObjectBase " +
                    "for object " + this.getClass().getSimpleName());
        }
    }
}
