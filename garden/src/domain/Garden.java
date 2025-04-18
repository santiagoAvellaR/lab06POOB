package src.domain;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


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
        Flower rose = new Flower(this, 10, 10);
        Flower violet = new Flower(this, 15, 15);
        Flower lisa = new Flower(this, 2, 2);
        Flower elsa = new Flower(this, 20, 30);
        Flower mary = new Flower(this, 17, 35);
        Flower juliet = new Flower(this, 30, 2);
        //Carnivorous venus = new Carnivorous(this, 16, 16);
        //Carnivorous sundeuos = new Carnivorous(this, 5, 5);
        Sand tatacoa = new Sand(this, 0, 38);
        Sand sahara = new Sand(this, 0, 39);
        //Drosera santiago = new Drosera(this, 2  , 20);
        Drosera daniel = new Drosera(this, 20, 20);
        Gardener nicolas = new Gardener(this, 25, 25);
        Gardener samuel = new Gardener(this, 22, 22);
        Gardener danielA = new Gardener(this, 30, 5);
        Gardener santiago = new Gardener(this, 30, 30);
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

    public String getType(int row, int column){return garden[row][column].getType();}

    private void emptyGarden(){
        for (int r=0;r<LENGTH;r++){
            for (int c=0;c<LENGTH;c++){
                setThing(r, c, null);
            }
        }
    }

    private void printGarden(){
        for (int r=0;r<LENGTH;r++){
            for (int c=0;c<LENGTH;c++){
                if (garden[r][c]!=null){
                    System.out.print(getType(r,c) + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden open(File file) throws GardenException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            String s = (String) in.readObject();
            return (Garden) in.readObject();
        } catch (FileNotFoundException e) {
            throw new GardenException(GardenException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new GardenException(GardenException.ERROR_DURING_PROCESSING);
        } catch (ClassNotFoundException e) {
            throw new GardenException(GardenException.CLASS_NOT_FOUND);
        } catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void save(File file) throws GardenException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file, true))) {
            out.writeObject("Garden storage\n");
            out.writeObject(this);
        } catch (FileNotFoundException e) {
            throw new GardenException(GardenException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new GardenException(GardenException.ERROR_DURING_PROCESSING);
        } catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    public static Garden importt(File file) throws GardenException {
        Garden garden = new Garden();
        garden.emptyGarden();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea = reader.readLine();
            while (linea != null) {
                try {
                    if(!linea.isEmpty()) {
                        String[] info = linea.split(" ");
                        String className = "src.domain." + info[0];
                        Class<?> clazz = Class.forName(className);
                        Constructor<?> constructor = clazz.getConstructor(Garden.class, int.class, int.class);
                        Object obj = constructor.newInstance(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    }
                    linea = reader.readLine();

                } catch (ClassNotFoundException e) {
                    throw new GardenException(GardenException.NONEXISTENT_CLASS);
                } catch (NoSuchMethodException e) {
                    throw new GardenException(GardenException.MISSING_CONSTRUCTOR);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new GardenException(GardenException.ERROR_CREATING_OBJECT);
                } catch (NumberFormatException e) {
                    throw new GardenException(GardenException.INVALID_FORMAT);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new GardenException(GardenException.MISSING_INFORMATION);
                }
            }
            return garden;
        } catch (FileNotFoundException e) {
            throw new GardenException(GardenException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new GardenException(GardenException.ERROR_DURING_PROCESSING);
        }
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void export(File file) throws GardenException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("Garden storage\n");
            for (int i = 0; i < LENGTH; i++) {
                for (int j = 0; j < LENGTH; j++) {
                    if (garden[i][j] != null) {
                        String objectType = getType(i, j) + " " + i + " " + j;
                        writer.write(objectType + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new GardenException(GardenException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new GardenException(GardenException.ERROR_DURING_PROCESSING);
        } catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }







    // VERSIONES PASADAS- PRELIMINARES

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

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void export00(File file) throws GardenException{
        throw new GardenException("exportar en construccion. Archivo " + file.getName());
    }

    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden importt00(File file) throws GardenException{
        Garden garden = null;
        if(true){throw new GardenException("importar en construccion. Archivo " + file.getName());}
        return garden;
    }

    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden open01(File file) throws GardenException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            String s = (String) in.readObject();
            return (Garden) in.readObject();
        }
        catch(Exception e){
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void save01(File file) throws GardenException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file, true))) {
            out.writeObject("Garden storage\n");
            out.writeObject(this);
            out.close();
        }
        catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden importt01(File file) throws GardenException{
        Garden garden = new Garden();
        garden.emptyGarden();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea = reader.readLine();
            while (linea != null) {
                String[] info = linea.split(" ");
                if (info[0].trim().equalsIgnoreCase("Sand")){
                    new Sand(garden,Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                }
                else if (info[0].trim().equalsIgnoreCase(("Water"))){
                    new Water(garden,Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                }
                else if (info[0].trim().equalsIgnoreCase("Drosera")){
                    new Drosera(garden,Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                }
                else if (info[0].trim().equalsIgnoreCase(("Flower"))){
                    new Flower(garden,Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                }
                else if (info[0].trim().equalsIgnoreCase(("Gardener"))){
                    new Gardener(garden,Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                }
                else if (info[0].trim().equalsIgnoreCase(("Carnivorous"))){
                    new Carnivorous(garden,Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                }
                linea = reader.readLine();
            }
            return garden;
        }
        catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void export01(File file) throws GardenException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("Garden storage\n");
            for (int i=0;i<LENGTH;i++){
                for (int j=0;j<LENGTH;j++){
                    if (garden[i][j]!=null){
                        String objectType = getType(i, j) + " " + i + " " + j;
                        writer.write(objectType  + "\n");
                    }
                }
            }
        } catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden importt02(File file) throws GardenException {
        Garden garden = new Garden();
        garden.emptyGarden();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea = reader.readLine();
            linea = reader.readLine();
            while (linea != null) {
                try {
                    String[] info = linea.split(" ");
                    if (info[0].trim().equalsIgnoreCase("Sand")) {
                        new Sand(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Water")) {
                        new Water(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Drosera")) {
                        new Drosera(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Flower")) {
                        new Flower(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Gardener")) {
                        new Gardener(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Carnivorous")) {
                        new Carnivorous(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    }
                    else {
                        throw new GardenException(GardenException.NONEXISTENT_CLASS + info[0].trim());
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    throw new GardenException(GardenException.MISSING_INFORMATION);
                }
                linea = reader.readLine();
            }
            return garden;
        } catch (GardenException e){
            throw new GardenException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new GardenException(GardenException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new GardenException(GardenException.ERROR_DURING_PROCESSING);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new GardenException(GardenException.MISSING_INFORMATION);
        } catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void export02(File file) throws GardenException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("Garden storage\n");
            for (int i = 0; i < LENGTH; i++) {
                for (int j = 0; j < LENGTH; j++) {
                    if (garden[i][j] != null) {
                        String objectType = getType(i, j) + " " + i + " " + j;
                        writer.write(objectType + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new GardenException(GardenException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new GardenException(GardenException.ERROR_DURING_PROCESSING);
        } catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    /**
     * Method that open and read a file for create a Garden
     * @param file where the Garden is contained
     * @return the garden contained in the file
     * @throws GardenException when an error Ocurred
     * */
    public static Garden importt03(File file) throws GardenException {
        Garden garden = new Garden();
        garden.emptyGarden();
        int lineNumber = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            String linea;
            lineNumber = 2;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] info = validateInfo(linea, garden);
                    if (info[0].trim().equalsIgnoreCase("Sand")) {
                        new Sand(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Water")) {
                        new Water(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Drosera")) {
                        new Drosera(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Flower")) {
                        new Flower(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Gardener")) {
                        new Gardener(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else if (info[0].trim().equalsIgnoreCase("Carnivorous")) {
                        new Carnivorous(garden, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    } else {
                        throw new GardenException(GardenException.NONEXISTENT_CLASS + "\nClase obtenida: " + info[0]);
                    }
                    lineNumber += 1;
                }
            }
            reader.close();
            return garden;
        } catch (GardenException e) {
            throw new GardenException(GardenException.GENERAL_ERROR + " " + e.getMessage() + ". En la linea " + lineNumber);
        } catch (FileNotFoundException e) {
            throw new GardenException(GardenException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new GardenException(GardenException.ERROR_DURING_PROCESSING);
        } catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

    private static String[] validateInfo(String linea, Garden garden) throws GardenException {
        String[] info = linea.split(" ");
        if (info.length < 3){throw new GardenException(GardenException.MISSING_INFORMATION + "\nLinea obtenida: " + linea);}
        if (info.length > 3){throw new GardenException(GardenException.INFORMATION_COULD_LOST + "\nLinea obtenido: " + linea);}
        if (!info[1].matches("[0-9]+")) {throw new GardenException(GardenException.COLUMN_IS_NOT_NUMBER + "\nValor obtenido: " + info[1]);}
        if (!info[2].matches("[0-9]+")) {throw new GardenException(GardenException.ROW_IS_NOT_NUMBER + "\nValor obtenido: " + info[2]);}
        if (Integer.parseInt(info[1]) > 39) {throw new GardenException(GardenException.ROW_EXCEEDED + "\nValor obtenido: " +  info[1]);}
        if (Integer.parseInt(info[2]) > 39) {throw new GardenException(GardenException.COLUMN_EXCEEDED + "\nValor obtenido: " + info[2]);}
        if (Integer.parseInt(info[1]) < 0) {throw new GardenException(GardenException.NEGATIVE_ROW + "\nValor obtenido: " + info[1]);}
        if (Integer.parseInt(info[2]) < 0) {throw new GardenException(GardenException.NEGATIVE_COLUMN + "\nValor obtenido: " + info[2]);}
        int row = Integer.parseInt(info[1]);
        int column = Integer.parseInt(info[2]);
        if (garden.getThing(row, column) != null){throw new GardenException(GardenException.SPACE_OCUPIED + garden.getType(row, column));}
        return info;
    }

    /**
     * Method that save the garden
     * @param file where the Garden will be contained
     * @throws GardenException when an error Ocurred
     * */
    public void export03(File file) throws GardenException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("Garden storage\n");
            for (int i = 0; i < LENGTH; i++) {
                for (int j = 0; j < LENGTH; j++) {
                    if (garden[i][j] != null) {
                        String objectType = getType(i, j) + " " + i + " " + j;
                        writer.write(objectType + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new GardenException(GardenException.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new GardenException(GardenException.ERROR_DURING_PROCESSING);
        } catch (Exception e) {
            throw new GardenException(GardenException.GENERAL_ERROR);
        }
    }

}

