package src.domain;


import java.awt.Color;
import java.io.Serializable;

/**
 * Class representing sand in the garden.
 */
public class Sand implements Thing, Serializable {
    private Color color;
    private Garden garden;
    private int row,column;
    private int time;
    /**
     * Constructor for objects of class Sand.
     *
     * @param garden The garden where the sand is located.
     * @param row The row position of the sand.
     * @param column The column position of the sand.
     */
    public Sand(Garden garden,int row, int column){
        this.garden=garden;
        this.row=row;
        this.column=column;
        garden.setThing(row,column,(Thing)this);
        color=color.darkGray;
        garden.numberSandBlocks++;
        time = garden.time;
    }

    /**
     * Return the type of the thing
     * @return type of the thing, Sand in this case
     */
    @Override
    public String getType(){return "Sand";}

    /**
     * Performs an action for the sand.
     * The sand will lighten its color over time until it disappears.
     */
    public void act(){
        time++;
        if (time== 100)
        {
            garden.setThing(row,column,null);
        }
        else{
            color = color.brighter();
        }
    }

    /**
     * Returns the shape of the sand.
     *
     * @return The shape of the sand.
     */
    public final int shape(){
        return Thing.SQUARE;
    }

    /**
     * Returns the color of the sand.
     *
     * @return The color of the sand.
     */
    public final Color getColor(){
        return color;
    }
}


