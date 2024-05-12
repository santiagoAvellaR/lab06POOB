import org.junit.jupiter.api.Test;
import src.domain.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class GardenTest {
    private Garden garden;

    // 00 METHODS
    @Test
    public void testSave00() {
        Garden garden = new Garden();
        File file = new File("garden");
        try {
            garden.save00(file);
            fail("Did not throw exception");
        } catch (GardenException e) {
            assertEquals("guardar en construccion. Archivo " + file.getName(), e.getMessage());
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    public void testOpen00() {
        garden = new Garden();
        File file = new File("garden");
        try {
            garden = garden.open00(file);
            fail("Did not throw exception");
        } catch (GardenException e) {

            assertEquals("abrir en construccion. Archivo " + file.getName(), e.getMessage());
        }
    }

    @Test
    public void testImport00() {
        garden = new Garden();
        File file = new File("garden");
        try {
            garden = garden.importt00(file);
            fail("Did not throw exception");
        } catch (GardenException e) {
            assertEquals("importar en construccion. Archivo " + file.getName(), e.getMessage());
        }
    }

    @Test
    public void testExport00() {
        garden = new Garden();
        File file = new File("garden");
        try {
            garden.export00(file);
            fail("Did not throw exception");
        } catch (GardenException e) {

            assertEquals("exportar en construccion. Archivo " + file.getName(), e.getMessage());
        }
    }

    // 01 METHODS
    @Test
    public void testSave01Error(){
        garden = new Garden();
        File file = new File("garden");
        file.delete();
        try {
            garden.save01(file);

        }catch (GardenException e) {
            assertEquals(GardenException.GENERAL_ERROR, e.getMessage());
        }
    }

    @Test
    public void testOpen01Error(){
        garden = new Garden();
        File file = new File("garden");
        file.delete();
        try {
            garden = garden.open01(file);

        }catch (GardenException e) {
            assertEquals(GardenException.GENERAL_ERROR, e.getMessage());
        }
    }

    @Test
    public void testImport01Error(){
        garden = new Garden();
        File file = new File("garden");
        file.delete();
        try {
            garden = garden.importt01(file);

        }catch (GardenException e) {
            assertEquals(GardenException.GENERAL_ERROR, e.getMessage());
        }
    }

    @Test
    public void testExport01Error(){
        garden = new Garden();
        File file = new File("garden");
        file.delete();
        try {
            garden.export01(file);
        }catch (GardenException e) {
            assertEquals(GardenException.GENERAL_ERROR, e.getMessage());
        }
    }

    // 02 TEST
    @Test
    public void testImport02MISSING_INFORMATION() {
        File file = new File("hola");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Sand 10\n");
            writer.close();
            garden = garden.importt02(file);
        }
        catch (IOException e) {
            assertEquals(GardenException.MISSING_INFORMATION, e.getMessage());
        } catch (GardenException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testImport02FileNotFound() {
        File file = new File("hola");
        try{

            file.delete();
            garden = garden.importt02(file);
        }
        catch (GardenException e) {
            assertEquals(GardenException.FILE_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void testImport02ErrorDuringProcessing() {
        String fileName = "archivo";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("Mascota ");
            writer.write("10 ");
            writer.write("15 ");
            writer.close();
            File file = new File(fileName);
            garden = Garden.importt02(file);

        } catch (GardenException e) {
            assertEquals(GardenException.ERROR_DURING_PROCESSING, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testExport02FILE_NOT_FOUND() {

        File file = new File("hola");
        file.delete();
        try {
            Garden garden = new Garden();
            garden.export02(file);

        } catch (GardenException e) {
            assertEquals(GardenException.FILE_NOT_FOUND, e.getMessage());
        }

    }

    // 03 METHODS
    @Test
    public void testSave() {
        garden = new Garden();
        File file = new File("garden");
        try {
            garden.save(file);
            assertTrue(file.exists());
        } catch (GardenException e) {
            fail(e.getMessage());
        }finally {
            file.delete();
        }
    }

    @Test
    public void testOpen() {
        garden = new Garden();
        garden.ticTac();
        File file = new File("garden");
        try {
            garden.save(file);
            garden = garden.open(file);
            assertEquals(garden.getType(2,2), "Flower");
            assertEquals(garden.getType(10,10), "Flower");
            assertEquals(garden.getType(15,15), "Drosera");
            assertEquals(garden.getType(30,29), "Sand");
        }catch (GardenException e) {
            fail(e.getMessage());
        }finally {
            file.delete();
        }
    }

    @Test
    public void testOpenFileNotFound() {
        try {
            File file = new File("garden");
            file.delete();
            garden = garden.open(file);
            fail("Did not throw exception");
        } catch (GardenException e) {
            assertEquals(GardenException.FILE_NOT_FOUND,e.getMessage());
        }
    }

    @Test
    public void testOpenErrorDuringProcessing() {
        String fileName = "archivo";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("Mascota ");
            writer.write("10 ");
            writer.write("15 ");
            writer.close();
            File file = new File(fileName);
            garden = Garden.open(file);

        } catch (GardenException e) {
            assertEquals(GardenException.ERROR_DURING_PROCESSING, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testImportNegative_Row() {
        File file = new File("hola");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Sand -4 2\n");
            writer.close();
            garden = garden.importt03(file);
        }
        catch (IOException e) {
            assertEquals(GardenException.NEGATIVE_ROW, e.getMessage());
        } catch (GardenException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testImportROW_IS_NOT_NUMBER() {
        File file = new File("hola");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Flower e 2\n");
            writer.close();
            garden = garden.importt03(file);
        }
        catch (IOException e) {
            assertEquals(GardenException.ROW_IS_NOT_NUMBER, e.getMessage());
        } catch (GardenException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testImportNONEXISTENT_CLASS() {
        File file = new File("hola");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("hOLA e 2\n");
            writer.close();
            garden = garden.importt03(file);
        }
        catch (IOException e) {
            assertEquals(GardenException.NONEXISTENT_CLASS, e.getMessage());
        } catch (GardenException e) {
            throw new RuntimeException(e);
        }
    }

    //04 METHODS _ BONO METHODS
    @Test
    public void testImportNONEXISTENT_CLASS1(){
        File file = new File("hola");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("hOLA e 2\n");
            writer.close();
            garden = garden.importt(file);
        } catch (GardenException e) {
            assertEquals(GardenException.NONEXISTENT_CLASS, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testImportINVALID_FORMAT(){
        File file = new File("hola");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Drosera e 2\n");
            writer.close();
            garden = garden.importt(file);
        } catch (GardenException e) {
            assertEquals(GardenException.INVALID_FORMAT, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testImportFileNotFoundFinal(){
        File file = new File("hola");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Drosera e 2\n");
            writer.close();
            file.delete();
            garden = garden.importt(file);
        } catch (GardenException e) {
            assertEquals(GardenException.FILE_NOT_FOUND, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}