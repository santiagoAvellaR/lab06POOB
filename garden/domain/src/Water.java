package domain.src;


/**
 * Class representing water in the garden.
 */
public final class Water  implements Thing{
    private Garden garden;
    private int row;
    private int column;
    /**
     * Constructor for objects of class Water.
     *
     * @param garden The garden where the water is located.
     * @param row The row position of the water.
     * @param column The column position of the water.
     */
    public Water(Garden garden, int row, int column){
        this.garden=garden;
        this.row=row;
        this.column=column;
        garden.setThing(row,column,(Thing)this);
        garden.numberWaterBlocks++;
    }

    /**
     * Performs an action for the water.
     * Water does not have any specific actions.
     */
    public void act(){
    }
}
