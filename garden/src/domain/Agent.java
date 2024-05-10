package src.domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * Abstract class representing an agent.
 */
public abstract class Agent implements Serializable {
    public final static char UNKNOWN='u', ALIVE='a', DEAD='d';
    protected char state;
    private int time = 0;

    /**Create a new agent
     *
     */
    public Agent(){
        state=UNKNOWN;
        time=0;
    }

    /**The agent turns one life span old
     *
     */
    protected void turn(){
        time++;
    }

    /** The agent moves
     */
    public abstract void move(int row, int column);

    /** The agent change to the nextState
     */
    public abstract void changeState(char nextState);

    /**Returns the time
     @return
     */
    public final int getTime(){
        return time;
    }

    /**
     * Sets the time elapsed for the agent.
     *
     * @param time The time elapsed to set.
     */
    public final void setTime(int time){
        this.time = time;
    }

    /**Returns if alive
     @return true, if ALIVE; false, otherwise
     */
    public final boolean isAlive(){
        return (state == Agent.ALIVE) ;
    }

}

