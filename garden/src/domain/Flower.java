package src.domain;


import java.awt.Color;

/**
 * Class representing a flower in the garden.
 * Extends Agent and implements Thing.
 */
public class Flower extends Agent implements Thing{
    protected char nextState;
    protected Color color;
    private Garden garden;
    protected int row,column;

    /**
     * Creates a new flower at the specified position in the garden.
     * The flower is initially set to be alive in the following state.
     *
     * @param garden The garden where the flower is located.
     * @param row The row position of the flower.
     * @param column The column position of the flower.
     */
    public Flower(Garden garden,int row, int column){
        this.garden=garden;
        this.row=row;
        this.column=column;
        nextState=Agent.ALIVE;
        garden.setThing(row,column,(Thing)this);
        color=Color.red;
        changeState(nextState);
        garden.numberOfFlowers++;
        setTime(garden.time);
    }

    /**Returns the shape
     @return
     */
    public final int shape(){
        return Thing.FLOWER;
    }

    /**Returns the row
     @return
     */
    public final int getRow(){
        return row;
    }

    /**Returns tha column
     @return
     */
    public final int getColumn(){
        return column;
    }

    /**Returns the color
     @return
     */
    public final Color getColor(){
        return color;
    }

    /**
     * Moves the flower to a new position (not implemented for flowers).
     *
     * @param row The new row position.
     * @param column The new column position.
     */
    public void move(int row, int column){
    }

    /**
     * Changes the state of the flower.
     *
     * @param nextState The next state to transition to.
     */
    public void changeState(char nextState){
        state = nextState;
        this.nextState = nextState;
    }

    /**
     * Performs an action for the flower.
     * Flowers change color and state based on the elapsed time.
     */
    public void act(){
        turn();
        if(getTime() == 3){
            changeState('d');
            color = Color.orange;
        }
        else if((getTime()-3)%8==0){
            changeState('d');
            color = Color.orange;
        }
        else if((getTime()%8==0)){
            changeState('a');
            color = Color.red;
        }
    }
}


