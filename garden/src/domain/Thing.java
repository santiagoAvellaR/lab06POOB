package src.domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * Interface representing a Thing.
 */
public interface Thing {
    public static final int ROUND = 1;
    public static final int SQUARE = 2;
    public static final int FLOWER = 3;
    public static final int HAT = 4;

    /**
     * Performs an action.
     */
    public void act();

    /**
     * Returns the shape of the Thing.
     *
     * @return The shape of the Thing.
     */
    default public int shape(){
        return SQUARE;
    }

    /**
     * Returns the color of the Thing.
     *
     * @return The color of the Thing.
     */
    default public Color getColor(){
        return Color.blue;
    }

    default public boolean is(){
        return true;
    }
}


