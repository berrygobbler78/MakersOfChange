import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Main{
    static Boolean leftAnimalCorrect = false;
    static Boolean rightAnimalCorrect = false;

    static String leftAnimal;
    static String rightAnimal;

    static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    static int WIDTH = (int) size.getWidth();
    static int HEIGHT = (int) size.getHeight();

    static JFrame frame = new JFrame("Z's Master Matcher");
    
    static ImageIcon chicken = new ImageIcon("pictures/Chicken.png");
    static ImageIcon cow = new ImageIcon("pictures/Cow.png");
    static ImageIcon goat = new ImageIcon("pictures/Goat.png");  
    static ImageIcon sheep = new ImageIcon("pictures/Sheep.png");
    static ImageIcon telephone = new ImageIcon("pictures/Telephone.png");

    static JLabel labelL = new JLabel();
    static JLabel labelR = new JLabel();
    
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        KeyListener escKL = new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                //If someone click Esc key, this program will exit
                if(evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
                        System.exit(0);
                }
            }
        };

        chicken = new ImageIcon(getScaledImage(chicken.getImage(), 250, 250));
        cow = new ImageIcon(getScaledImage(cow.getImage(), 250, 250));
        goat = new ImageIcon(getScaledImage(goat.getImage(), 250, 250));
        sheep = new ImageIcon(getScaledImage(sheep.getImage(), 250, 250));
        telephone = new ImageIcon(getScaledImage(telephone.getImage(), 250, 250));
        
        

        
        // frame.getContentPane();

        JPanel panel = new JPanel();

        panel.setLayout(null);
        panel.add(labelR);
        panel.add(labelL);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        frame.addKeyListener(escKL);

        runGame();
    }

    public static void updateFrame(String leftAnimal, String rightAnimal) {
        switch(leftAnimal) {
            case "Chicken" :
                labelL.setIcon(chicken);
                break;
            case "Cow" :
                labelL.setIcon(cow);
                break;
            case "Goat" :
                labelL.setIcon(goat);
                break;
            case "Sheep" :
                labelL.setIcon(sheep);
                break;
            case "Telephone" :
                labelL.setIcon(telephone);
                break;
        }

        switch(rightAnimal) {
            case "Chicken" :
                labelR.setIcon(chicken);
                break;
            case "Cow" :
                labelR.setIcon(cow);
                break;
            case "Goat" :
                labelR.setIcon(goat);
                break;
            case "Sheep" :
                labelR.setIcon(sheep);
                break;
            case "Telephone" :
                labelR.setIcon(telephone);
                break;
        }
        
        Dimension labelSizeR = labelR.getPreferredSize();
        labelR.setBounds((WIDTH * 3 / 4) - labelSizeR.width / 2, HEIGHT / 2 - labelSizeR.height / 2, labelSizeR.width, labelSizeR.height);

        Dimension labelSizeL = labelL.getPreferredSize();
        labelL.setBounds((WIDTH / 4) - labelSizeL.width / 2, HEIGHT / 2 - labelSizeL.height / 2, labelSizeL.width, labelSizeR.height);

        SwingUtilities.updateComponentTreeUI(frame);
    }

    public static void runGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        leftAnimalCorrect = false;
        rightAnimalCorrect = false;

        // Gets two random objects
        leftAnimal = randomObjectGenerator();
        rightAnimal = randomObjectGenerator();

        // Ensures that they are not the same
        while(leftAnimal.equals(rightAnimal)) {
            rightAnimal = randomObjectGenerator();
        }

        updateFrame(leftAnimal, rightAnimal);
        
        // Picks a right and a wrong answer
        int chooseRightAndWrong = ThreadLocalRandom.current().nextInt(1, 2 + 1);

        // Plays a sound depending on what is chosen
        if (chooseRightAndWrong == 1) {
            leftAnimalCorrect = true;

            playSound(leftAnimal);

        } else {
            rightAnimalCorrect = true;

            playSound(rightAnimal);
        }

        System.out.println(leftAnimal + " " + rightAnimal);

        checkAnswer();
    }
    
    public static void checkAnswer() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Gets response
        String response = scanner.nextLine();

        // Checks possible responses
        if (response.equals("R") && rightAnimalCorrect == true) {
            System.out.println(rightAnimal);
            playSound(rightAnimal);

            checkAnswer();
        } else if (response.equals("R") && leftAnimalCorrect == true) {
            System.out.println(leftAnimal);
            playSound(leftAnimal);

            checkAnswer();
        } else if (response.equals(rightAnimal) && rightAnimalCorrect == true) {
            System.out.println("You got it bub");

            runGame();
        } else if (response.equals(leftAnimal) && leftAnimalCorrect == true) {
            System.out.println("You got it bub");

            runGame();
        } else { 
            System.out.println("you stink");
            
            runGame();
        }

        scanner.close();
    }
    
    // Generates a random object from the list
    public static String randomObjectGenerator() {
        String randomObject = "default";
        int randomObjectNumber = ThreadLocalRandom.current().nextInt(1, 4 + 1);

        switch (randomObjectNumber) {
            case 1:
                randomObject = "Sheep";
                break;
            case 2:
                randomObject = "Cow";
                break;
            case 3:
                randomObject = "Goat";
                break;
            case 4:
                randomObject = "Telephone";
                break;
            case 5:
                randomObject = "Cat";
                break;
        }

        return randomObject;
    }

    public static void playSound(String type) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        File sound = new File("audio/" + type + ".wav"); 

        System.out.println(type + ".wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        double durationInSeconds = sound.length();  
        long durationInLongSeconds = (long) durationInSeconds / 100;
        clip.start();
        Thread.sleep(durationInLongSeconds);
    }

    private static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }  
}