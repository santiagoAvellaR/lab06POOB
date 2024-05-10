package presentation.src;

import domain.src.*;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GardenGUI extends JFrame {
    public static final int SIDE = 21;
    public static final int SIZE = Garden.LENGTH + 1;
    // Menu
    private JMenuItem nuevo, abrir, guardarComo, importar, exportarComo, cerrar;
    private JButton buttonTicTac;
    private JPanel panelControl;
    private PhotoGarden photo;
    private Garden garden;

    private GardenGUI() {
        garden = new Garden();
        prepareElements();
        prepareActions();
        prepareMenuElements();
    }

    private void prepareElements() {
        setTitle("Garden");
        photo = new PhotoGarden(this);
        buttonTicTac = new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo, BorderLayout.NORTH);
        add(buttonTicTac, BorderLayout.SOUTH);
        setSize(new Dimension(SIDE * SIZE, SIDE * SIZE + 50));
        setResizable(false);
        photo.repaint();
    }

    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buttonTicTac.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttonTicTacAction();
                    }
                });

    }

    private void prepareMenuElements() {
        // Botones-Opciones Menu
        JMenuBar menuBar;
        JMenu opciones;
        menuBar = new JMenuBar();
        opciones = new JMenu("Archivo");
        setJMenuBar(menuBar);
        nuevo = new JMenuItem("Nuevo");
        opciones.add(nuevo);
        opciones.addSeparator();
        abrir = new JMenuItem("Abrir");
        opciones.add(abrir);
        opciones.addSeparator();
        guardarComo = new JMenuItem("Guardar como");
        opciones.add(guardarComo);
        opciones.addSeparator();
        importar = new JMenuItem("Importar");
        opciones.add(importar);
        opciones.addSeparator();
        exportarComo = new JMenuItem("Exportar como");
        opciones.add(exportarComo);
        opciones.addSeparator();
        cerrar = new JMenuItem("Cerrar");
        opciones.add(cerrar);
        menuBar.add(opciones);
        prepareMenuActions();
    }

    private void prepareMenuActions() {
        nuevo.addActionListener(optionNew());

        abrir.addActionListener(optionOpen());

        cerrar.addActionListener(optionExit());

        guardarComo.addActionListener(optionSave());

        exportarComo.addActionListener(optionExport());

        importar.addActionListener(optionImport());
    }

    private ActionListener optionNew(){
        return (event)->{
            garden = new Garden();
            validate();
            repaint();
        };
    }

    private ActionListener optionExit(){
        return (event)->{
            this.setVisible(false);
            System.exit(0);
        };
    }

    private ActionListener optionOpen(){
        return (event)->{
            JFileChooser openFileChooser = new JFileChooser();
            int result = openFileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = openFileChooser.getSelectedFile();
                try {
                    Garden.open(selectedFile);
                }
                catch (GardenException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Abrir jardin", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Error al abrir archivo", "Abrir jardin", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    private ActionListener optionSave(){
        return (event)->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showSaveDialog(this);
            if(option == JFileChooser.APPROVE_OPTION){
                java.io.File selectedFolder = fileChooser.getSelectedFile();
                try {
                    garden.save(selectedFolder);
                }
                catch (GardenException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Guardar jardin", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Error al guardar archivo", "Guardar jardin", JOptionPane.INFORMATION_MESSAGE);

            }
        };
    }

    private ActionListener optionExport(){
        return (event)->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showSaveDialog(this);
            if(option == JFileChooser.APPROVE_OPTION){
                java.io.File selectedFolder = fileChooser.getSelectedFile();
                try {
                    garden.export(selectedFolder);
                }
                catch (GardenException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Exportar jardin", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Error al exportar archivo", "Exportar jardin", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    private ActionListener optionImport(){
        return (event)->{
            JFileChooser openFileChooser = new JFileChooser();
            int result = openFileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = openFileChooser.getSelectedFile();
                try {
                    Garden.importt(selectedFile);
                }
                catch (GardenException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Importar jardin", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Error al importar archivo", "Importar jardin", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    private void buttonTicTacAction() {
        garden.ticTac();
        photo.repaint();
    }

    public Garden getGarden(){
        return garden;
    }

    public static void main(String[] args) {
        GardenGUI cg=new GardenGUI();
        cg.setVisible(true);
    }
}

class PhotoGarden extends JPanel{
    private GardenGUI gui;

    public PhotoGarden(GardenGUI gui) {
        this.gui=gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(gui.SIDE*gui.SIZE, gui.SIDE*gui.SIZE));
    }


    public void paintComponent(Graphics g){
        Garden garden=gui.getGarden();
        super.paintComponent(g);
        int[][] deltas={{gui.SIDE/3, 0},{0,gui.SIDE/6},{2*gui.SIDE/3,gui.SIDE/6},{0,3*gui.SIDE/6},{2*gui.SIDE/3,3*gui.SIDE/6},{gui.SIDE/3,2*gui.SIDE/3}} ;
        for (int f=0;f<=garden.getLength();f++){
            g.drawLine(f*gui.SIDE,0,f*gui.SIDE,garden.getLength()*gui.SIDE);
        }
        for (int c=0;c<=garden.getLength();c++){
            g.drawLine(0,c*gui.SIDE,garden.getLength()*gui.SIDE,c*gui.SIDE);
        }
        for (int f=0;f<garden.getLength();f++){
            for(int c=0;c<garden.getLength();c++){
                if (garden.getThing(f,c)!=null){
                    g.setColor(garden.getThing(f,c).getColor());
                    if (garden.getThing(f,c).shape()==Thing.FLOWER){
                        g.setColor(garden.getThing(f,c).getColor());
                        for (int i=0; i<deltas.length; i++){
                            g.drawOval(gui.SIDE*c+deltas[i][0],gui.SIDE*f+deltas[i][1],gui.SIDE/3-1,gui.SIDE/3-1);
                        }
                        g.setColor(Color.YELLOW);
                        g.drawOval(gui.SIDE*c+gui.SIDE/3,gui.SIDE*f+gui.SIDE/3,gui.SIDE/3,gui.SIDE/3);
                        if (garden.getThing(f,c).is()){
                            g.setColor(garden.getThing(f,c).getColor());
                            for (int i=0; i<deltas.length; i++){
                                g.fillOval(gui.SIDE*c+deltas[i][0],gui.SIDE*f+deltas[i][1],gui.SIDE/3-1,gui.SIDE/3-1);
                            }
                            g.setColor(Color.YELLOW);
                            g.fillOval(gui.SIDE*c+gui.SIDE/3,gui.SIDE*f+gui.SIDE/3,gui.SIDE/3,gui.SIDE/3);
                        }
                    }
                    else if (garden.getThing(f,c).shape()==Thing.SQUARE){
                        g.drawRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);
                        if (garden.getThing(f,c).is()){
                            g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);
                        }
                    }
                    else if (garden.getThing(f,c).shape()==Thing.HAT){
                        g.setColor(garden.getThing(f,c).getColor());
                        g.fillRect(gui.SIDE*c+1, gui.SIDE*f+1, gui.SIDE-2, gui.SIDE/4); // Cuerpo del sombrero
                        g.setColor(new Color(156, 102, 31)); // Color marrón claro
                        g.fillRect(gui.SIDE*c+1, gui.SIDE*f+3*gui.SIDE/4, gui.SIDE-2, gui.SIDE/4); // Ala del sombrero
                        g.setColor(new Color(156, 102, 31)); // Color marrón claro
                        g.fillRect(gui.SIDE*c+gui.SIDE/4, gui.SIDE*f+gui.SIDE/4, gui.SIDE/2, gui.SIDE/2); // Corona del sombrero
                        g.setColor(Color.BLACK);
                        g.drawRect(gui.SIDE*c+gui.SIDE/4, gui.SIDE*f+gui.SIDE/4, gui.SIDE/2, gui.SIDE/2); // Borde de la corona del sombrero
                        g.drawRect(gui.SIDE*c+1, gui.SIDE*f+3*gui.SIDE/4, gui.SIDE-2, gui.SIDE/4); // Borde del ala del sombrero
                    }
                    else{
                        g.drawOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        if (garden.getThing(f,c).is()){
                            g.fillOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        }
                    }
                }
            }
        }
    }
}
