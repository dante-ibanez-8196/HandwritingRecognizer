import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Source.java: The Artificial Neural Network that is a handwriting recognizer
 */
public class Source{
    //take code from ANN P2 and rework it to read that csv file
    public static void main(String [] args) throws FileNotFoundException{
        //include File and Scanner obj to read this damn file
        File inFile = new File("A_Z Handwritten Data.csv");
        Scanner readInContents = new Scanner(inFile);

        //declare strings to read in the inFile, to split contents, and to collect each of the individual string
        String contextInfo = readInContents.nextLine();
        String [] splitInfo = contextInfo.split(",");
        String [] collectStrings = new String[splitInfo.length];

        //collect info to the string with for loop (this is gonna take a looong while)
        for(int i = 0; i < splitInfo.length; i++){
            collectStrings[i] = splitInfo[i];
        }
        //read in contents (to be passed in first layer) and close infile
        firstLayer(collectStrings);
        //close inFile
        readInContents.close();
    }

    /**
     * Pass on info to the firstLayer
     * @param collectedStrings the read in to be taken in and collected
     */
    public static void firstLayer(String [] collectedStrings){
        int networkStructureLength = 5; //do only 5 letters of the alphabet (A-E)
        int [] collectedValues = new int[collectedStrings.length];
        //first layer (read in all contents of the inputted array)
        for(int i = 0; i < collectedStrings.length; i++){
            collectedValues[i] *= collectedStrings.length * networkStructureLength;
        }

        //pass onto the second layer
        secondLayer(collectedValues);
    }

    /**
     * Second layer, to call in sigmoidal to execute and readjust the weights
     * @param collectedValues values to be read in from first layer
     */
    public static void secondLayer(int [] collectedValues){
        //second layer (should only contain 2 nodes at this point )
        int networkStructureLength = 2;
        double inputValue = 0; //value to be collected for sigmoidal function
        double finalValue = 0;
        double errorPercentage = 0;
        //for loop to read in and calculate 
        for(int i = 0; i < collectedValues.length; i++){
            inputValue = collectedValues[i] * networkStructureLength;
            finalValue = sigmoidal(inputValue);
            errorPercentage = calculateErrorPrecentage(inputValue, finalValue);
        }

        finalLayer(inputValue, errorPercentage);
        
    }

    /**
     * Pass onto final layer (mainly to display ephochs, learning rate, and error percentage onto the console)
     * WARNING: This is gonna take a while.
     * @param finalResult The final result to take in
     * @param errorPercentage The error percentage to be calculated to the output console
     */
    public static void finalLayer(double finalResult, double errorPercentage){
        int epochs = 100; //can be modified by the user (784 maximum, 100 to not punish my poor system)
        double learningRate = 0.0; //learning rate from the system
        //final layer
        System.out.println("Current data imported");

        for(int i = 0; i <= epochs; i++){
            learningRate = derivative();
            //display epoch, learning rate, and error percentage values onto the console
            System.out.println("epoch=" + epochs +"lrate = " +learningRate + "error = " + errorPercentage);
            epochs++;
        }
    }

    //additional methods
    //sigmoidal method to recalculate the weights
    /**
     * The sigmoidal weight to be used in the second layer 
     * @param input The inputted double value
     * @return the result after readjusting ther weight
     */
    public static double sigmoidal(double input){
        //declare weight to be used in calculating sigmoid (shoule be a 0 or a 1 initially as DOUBLE)
        //double weight = Math.random();
        double weight = ThreadLocalRandom.current().nextDouble(0, 1 + 1);
        double sigmoidalFunction = weight / (1+ Math.exp(input)); //calculate the sigmoidal method
        return sigmoidalFunction;
    }

    /**
     * calculate the deriviate (unknown use as of now=)
     * @return the resul
     */
    public static double derivative(){
        //calculate and return derivative value
        double oneOrZero = ThreadLocalRandom.current().nextDouble(0, 1 + 1);
        double returnDerivative = Math.exp(oneOrZero * (1 - oneOrZero));
        return returnDerivative;
    }

    /**
     * Calculate the error percentage (based on formula provided in class)
     * @param expectedResult The expected result
     * @param collectedResult The collected result
     * @return result to the console
     */
    public static double calculateErrorPrecentage(double expectedResult, double collectedResult){
        //calculate error percentage
        double errorPercentage = Math.exp(expectedResult - collectedResult);
        return errorPercentage;
    }
}