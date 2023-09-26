import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main{
    static Boolean leftAnimalCorrect = false;
    static Boolean rightAnimalCorrect = false;

    static String leftAnimal;
    static String rightAnimal;

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        runGame();
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
        File sound = new File(type + ".wav"); 

        System.out.println(type + ".wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        double durationInSeconds = sound.length();  
        long durationInLongSeconds = (long) durationInSeconds / 100;
        clip.start();
        Thread.sleep(durationInLongSeconds);
    }
}

