package src.test;


import src.domain.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TestGarden.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TestGarden
{
    private Garden garden;
    /**
     * Default constructor for test class TestGarden
     */
    public TestGarden()
    {
        garden = new Garden();
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        garden = new Garden();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDow0n()
    {
    }

    /**
     * PRUEBAS DE DROSERA
     */

    @Test
    public void droseraShouldDie(){
        Drosera drosera = new Drosera(garden, 20, 20);
        for(int i = 0; i < 3; i++){
            garden.ticTac();
        }
        assertFalse(drosera.isAlive());
    }
    @Test
    public void droseraShouldEatOnce(){
        Drosera Santiago = new Drosera(garden, 16, 20);
        Drosera Daniel = new Drosera(garden, 20, 20);
        Flower flower = new Flower(garden, 15, 15);
        garden.ticTac();
        assertEquals(Daniel.getRow(),20);//Se mantiene en la misma casilla ya que le tocaba comer, pero santiago se comio primero la flor
        assertEquals(Daniel.getColumn(),20);
        assertEquals(Santiago.getRow(),15);//Vemos como efectivamente el que si comio fue Santiago
        assertEquals(Santiago.getColumn(),15);
        for(int i = 0; i < 3; i++){
            garden.ticTac();
            if(i == 1){
                assertFalse(Daniel.isAlive());//Muere una iteracion antes que Santiago
            }
        }
        assertFalse(Santiago.isAlive());
    }
    @Test
    public void droseraMainTest(){
        //Se crea una inquieta
        Drosera drosera = new Drosera(garden, 28, 28);
        //Tiene mucha hambre, para su suerte nacio una nueva flor muy apetitosa
        Flower flower = new Flower(garden, 15,15);
        //No se aguanta las ganas y va a comersela de un mordisco
        garden.ticTac();
        //Pero ahora la inquieta se atasco de tanto comer, necesita hidratarse
        garden.ticTac();
        //Por tomar agua se tuvo que trasladar a la ubicacion de fuente hidrica mas cercana, para que no muera agonizando
        assertEquals(drosera.getRow(),0);
        assertEquals(drosera.getColumn(),0);
        //Pasan los dias, pero dios la abandono, no hay suculentas flores nuevas, solo agua
        // Como no hay flores que comer, se mantiene en la misma posicion
        garden.ticTac();
        assertEquals(drosera.getRow(),0);
        assertEquals(drosera.getColumn(),0);
        garden.ticTac();
        //necesita hidratarse
        assertEquals(drosera.getRow(),36);
        assertEquals(drosera.getColumn(),36);
        garden.ticTac();
        //Tristemente por no poder alimentarse correctamente perece al tercer dia de su ultima comida
        assertFalse(drosera.isAlive());
    }
    @Test
    public void droseraShouldEatAndDrink(){
    }


    /**
     * PRUEBAS DE SAND
     */

    @Test
    public void sandShouldDisappear(){
        Sand sand = new Sand(garden, 0, 20);
        for(int i = 0; i < 100; i++){
            garden.ticTac();
        }
        assertNull(garden.getThing(0,20));
    }


    /**
     * PRUEBAS DE GARDENER
     */
    @Test
    public void gardenerShouldCreateFlower(){
        Carnivorous carnivorous = new Carnivorous(garden, 0, 1); //Se crea para el agent con menor numero sea Flower
        assertEquals(garden.numberOfFlowers, 0);
        Gardener gardener =  new Gardener(garden, 1, 5);
        garden.ticTac();
        assertEquals(garden.numberOfFlowers, 1);
    }
    @Test
    public void gardenerShouldCreateCarnivorous(){
        Flower flower = new Flower(garden, 0, 1); //Se crea para que Carnivorous sea el agente con menos elementos
        assertEquals(garden.numberOfCarnivorous, 0);
        Gardener gardener =  new Gardener(garden, 1, 5);
        garden.ticTac();
        assertEquals(garden.numberOfCarnivorous, 1);
    }
    @Test
    public void gardenerShouldCreateSand(){
        Gardener gardener =  new Gardener(garden, 4, 5);
        Carnivorous carnivorous = new Carnivorous(garden, 9, 5);
        Flower flower = new Flower(garden, 1, 2);
        //Se crean para que Sand sea el agente con menos elementos
        garden.ticTac();
        assertEquals(garden.numberSandBlocks, 1);
    }
    @Test
    public void gardenerCreateFromScratch(){
        //Por el creador de Garden el agente con mas elementos es agua
        //Flower, Sand, Carnivorous Tienen 0 elementos
        Gardener Santiago =  new Gardener(garden, 5, 5);
        Gardener Daniel =  new Gardener(garden, 10, 10);
        //Si hay coincidencia su orden de prioridad es FLOR, CARNIVORA, SAND, WATER
        garden.ticTac(); // Por lo que al finalizar van a existir 1 flor y 1 carnivora
        assertEquals(garden.numberOfFlowers, 1);
        assertEquals(garden.numberOfCarnivorous, 1);
        garden.ticTac(); //Luego el menor agent con elementos sera SAND, entonces se creara uno y como otra vez Flor, Sand y carnivora son iguales, se creara una Flor
        //Asi serian 2 Flores, 1 carnivora, 1, pero el act de Carnivora se comeria una flor quedando flor 1, sand 1, carnivora 1
        assertEquals(garden.numberOfFlowers, 1);
        assertEquals(garden.numberOfCarnivorous, 1);
        assertEquals(garden.numberSandBlocks, 1);
    }
    @Test
    public void gardenerShouldReliveDrosera(){
        Drosera drosera = new Drosera(garden, 0, 1);
        for(int i = 0; i < 3; i++){garden.ticTac();}
        for (int i=1;i<=5;i++){
            for (int j=1;j<=2;j++){
                new Sand(garden,i, j);

            }
        }
        for (int i=21;i<=25;i++){
            for (int j=21;j<=22;j++){
                new Flower(garden,i, j);

            }
        }
        for (int i=21;i<=25;i++){
            for (int j=24;j<=26;j++){
                new Carnivorous(garden,i, j);

            }
        }
        //Se crean los anteriores Agent para que el minimo numero de agente sea mayor de 8 y asi
        //el gardener reviva a la Drosera
        Gardener gardener =  new Gardener(garden, 1, 5);
        assertFalse(drosera.isAlive());
        garden.ticTac();
        assertTrue(drosera.isAlive());

    }


    /**
     * PRUEBAS DE FlOWER
     */
    @Test
    public void flowerShouldDieAndRelive(){
        Flower flower = new Flower(garden, 0, 1);
        assertTrue(flower.isAlive());
        for(int i = 0; i < 3; i++){
            garden.ticTac();
        }
        assertFalse(flower.isAlive()); //Muere despues de 3 tictacs
        for(int i = 0; i < 5; i++){
            garden.ticTac();
        }
        assertTrue(flower.isAlive()); //Revive despues de los 5 tictacs luego de morir
    }


    /**
     * PRUEBAS DE CARNIVOROUS
     */
    @Test
    public void carnivorousShouldEatFlower(){
        Flower flower = new Flower(garden, 0, 1);
        Carnivorous carnivorous = new Carnivorous(garden,6, 6);
        Flower flower1 = new Flower(garden, 0, 6);
        Flower flower2 = new Flower(garden, 0, 5);
        garden.ticTac(); //se come una flor
        assertEquals(garden.numberOfFlowers, 2);
        garden.ticTac(); // se come otra
        assertEquals(garden.numberOfFlowers, 1);
        garden.ticTac(); // se marchita la ultima flor
        assertEquals(garden.numberOfFlowers, 1);
        for(int i = 0; i < 4; i++){
            garden.ticTac();
        }
        assertEquals(garden.numberOfFlowers, 1);
        //Revive la flor
        garden.ticTac(); // finalmente se la come
        assertEquals(garden.numberOfFlowers, 0);
        assertEquals(carnivorous.getRow(),0);
        assertEquals(carnivorous.getColumn(),1);
    }
}