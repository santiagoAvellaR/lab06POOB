package src.domain;

import java.io.File;
import java.io.Serializable;
import java.util.*;
/*No olviden adicionar la documentacion*/
public class Garden implements Serializable {
    static public int LENGTH=40;
    private Thing[][] garden;
    public static int time;
    public static int numberOfFlowers;
    public static int numberOfCarnivorous;
    public static int numberOfDroseras;
    public static int numberWaterBlocks;
    public static int numberSandBlocks;
    public static int numberOfGardeners;
    /**
     * Constructor of the Garden class.
     * Initializes the garden and places predefined elements.
     */
    public Garden() {
        time = 0;
        numberOfFlowers = 0;
        numberOfCarnivorous = 0;
        numberOfDroseras = 0;
        numberWaterBlocks = 0;
        numberSandBlocks = 0;
        numberOfGardeners = 0;
        garden=new Thing[LENGTH][LENGTH];
        for (int r=0;r<LENGTH;r++){
            for (int c=0;c<LENGTH;c++){
                garden[r][c]=null;
            }
        }
        new Water(this, 0, 0);
        for (int i=1;i<5;i++){
            for (int j=1;j<5;j++){
                new Water(this,LENGTH-i,LENGTH-j);

            }
        }
        //Gardener Santiago =  new Gardener(this, 5, 5);
        //Gardener Daniel =  new Gardener(this, 10, 10);
        someThings();
    }

    /**
     * Gets the length of the garden.
     *
     * @return The length of the garden.
     */
    public int  getLength(){
        return LENGTH;
    }

    /**
     * Gets the Thing object at the given coordinates.
     *
     * @param r The row where the Thing is located.
     * @param c The column where the Thing is located.
     * @return The Thing at the given coordinates.
     */
    public Thing getThing(int r,int c){
        return garden[r][c];
    }

    /**
     * Sets the Thing object at the given coordinates.
     *
     * @param r The row where the Thing will be placed.
     * @param c The column where the Thing will be placed.
     * @param e The Thing to be placed at the given coordinates.
     */
    public void setThing(int r, int c, Thing e){
        garden[r][c]=e;
    }

    /**
     * Places some elements in the garden (currently commented out).
     */
    public void someThings(){
        //Flower rose = new Flower(this, 10, 10);
        //Flower violet = new Flower(this, 15, 15);
        //Carnivorous venus = new Carnivorous(this, 16, 16);
        //Carnivorous sundeuos = new Carnivorous(this, 5, 5);
        //Sand tatacoa = new Sand(this, 0, 38);
        //Sand sahara = new Sand(this, 0, 39);
        //Drosera santiago = new Drosera(this, 2  , 20);
        Drosera daniel = new Drosera(this, 20, 20);
        Gardener nicolas = new Gardener(this, 25, 25);
        //Gardener samuel = new Gardener(this, 22, 22);
    }

    /**
     * Runs a time step in the garden, triggering the actions of each element in it.
     */
    public void ticTac(){
        for (int r=0;r<LENGTH;r++){
            for (int c=0;c<LENGTH;c++){
                Thing thing = garden[r][c];
                if(thing != null){
                    thing.act();
                }
            }
        }
        time++;
    }

    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden open(File file) throws GardenException{
        Garden garden = null;
        if(true){throw new GardenException(GardenException.GENERAL_ERROR);}
        return garden;
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void save(File file) throws GardenException{
        throw new GardenException(GardenException.GENERAL_ERROR);
    }

    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden importt(File file) throws GardenException{
        Garden garden = null;
        if(true){throw new GardenException("importar en construccion. Archivo " + file.getName());}
        return garden;
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void export(File file) throws GardenException{
        throw new GardenException("exportar en construccion. Archivo " + file.getName());
    }






    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden open00(File file) throws GardenException{
        Garden garden = null;
        if(true){throw new GardenException("abrir en construccion. Archivo " + file.getName());}
        return garden;
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void save00(File file) throws GardenException{
        throw new GardenException("guardar en construccion. Archivo " + file.getName());
    }
}

