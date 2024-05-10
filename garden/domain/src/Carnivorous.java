package domain.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;


/**
 * Class representing a carnivorous plant in the garden.
 * Extends Flower.
 */
public class Carnivorous extends Flower{
    // instance variables - replace the example below with your own
    private Garden garden;

    /**
     * Constructor for objects of class Carnivorous.
     *
     * @param garden The garden where the carnivorous plant is located.
     * @param row The row position of the carnivorous plant.
     * @param column The column position of the carnivorous plant.
     */
    public Carnivorous(Garden garden, int row, int column){
        super(garden, row, column);
        garden.numberOfFlowers--;
        this.garden = garden;
        this.color = color.blue;
        nextState = Agent.ALIVE;
        changeState(nextState);
        garden.numberOfCarnivorous++;
        setTime(garden.time);
    }

    /**
     * Finds the closest alive flower to the given position.
     *
     * @param targetRow The row position to search around.
     * @param targetColumn The column position to search around.
     * @return An array containing the row and column positions of the closest alive flower.
     */
    private int[] findClosestFlowerAlive(int targetRow, int targetColumn) {
        int[] closestPosition = new int[]{-1, -1};
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < garden.LENGTH; i++) {
            for (int j = 0; j < garden.LENGTH; j++) {
                if ( (garden.getThing(i, j) instanceof Flower && !(garden.getThing(i, j) instanceof Drosera) && !(garden.getThing(i, j) instanceof Carnivorous ))) {
                    Flower flower = (Flower) garden.getThing(i, j);
                    if (flower.isAlive()) {
                        int distance = Math.abs(targetRow - i) + Math.abs(targetColumn - j);
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestPosition[0] = i;
                            closestPosition[1] = j;
                        }
                    }
                }
            }
        }
        return closestPosition;
    }

    /**
     * Performs an action for the carnivorous plant.
     * The carnivorous plant moves towards the closest alive flower and consumes it.
     */
    @Override
    public void act() {
        if (getTime()==garden.time){
            int[] closestFlowerPosition = findClosestFlowerAlive(row, column);
            if (closestFlowerPosition[0] != -1 && closestFlowerPosition[1] != -1) {
                Flower flower = (Flower) garden.getThing(closestFlowerPosition[0], closestFlowerPosition[1]);
                flower.changeState('d');
                move(closestFlowerPosition[0], closestFlowerPosition[1]);
                garden.numberOfFlowers--;
            }
            turn();
        }
    }

    /**
     * Moves the carnivorous plant to the specified position.
     *
     * @param row The new row position.
     * @param column The new column position.
     */
    @Override
    public void move(int row, int column){
        garden.setThing(this.row, this.column, null);
        this.row = row;
        this.column = column;
        garden.setThing(row, column, this);
    }
}


