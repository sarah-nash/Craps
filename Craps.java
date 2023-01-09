import java.util.Random;
import static java.lang.Integer.parseInt;

/**
 * Craps is a popular casino game involving dice rolls.
 * This Craps program has 4 main functions, which can be run by using arguments in the terminal:
 * 'java Craps'
 *      will simulate a regular game of craps, with Phase I the Come Out and Phase II the Point.
 * 'java Craps test'
 *      will simulate a game until the player is forced to yield the dice.
 * 'java Craps odds'
 *      will compute the odds of winning over 1,000 games.
 * 'java Craps odds [int # games]'
 *      will computer the odds of winning over [#] games.
 *
 * @author Sarah Nash
 */

public class Craps {
/*
Rolls (2) 6-sided dice, and returns the sum of the two rolls.
If verbose, then the throw is printed to the terminal.
 */
    public static int rollDice(boolean verbose){
        Random rand = new Random();
        int sides = 6;
        int roll1 = rand.nextInt(6) + 1; //returns 1-6
        int roll2 = rand.nextInt(6) + 1;//returns 1-6
        int sum = roll1 + roll2;
        if (verbose){
            System.out.println("\tThrew " + roll1 + " + " + roll2 + " = " + sum );
        }
        return sum;
    }
/*
Performs the "Come Out" phase, or Phase I of the game. Rolls 2 dice to establish a target/point.
If verbose, print the throw of the phase.
 */
    public static int comeOut(boolean verbose){
        if(verbose){ System.out.println("Phase I, the Come Out"); }
        int target = rollDice(verbose);
        if(verbose){ System.out.println("\tTarget established: " + target); }
        return target;
    }
/*
Performs the "Point" phase, or Phase II of the game.
Rolls dice until the player re-rolls the initial target, or rolls a 7 (craps out).
Will return whether the play wins or loses the game.
If verbose, the progress will be printed.
 */
    public static boolean point(int target, boolean verbose){
       if (verbose){ System.out.println("Phase II, the Point");}
       int total = rollDice(verbose);
       while(total != 7 && total != target){
           total = rollDice(verbose);
       }
       boolean win = target==total;
       return win;
    }
/*
Plays a game of craps with the Come Out and the Point phases.
Returns a boolean[] gameStatus:
 gameStatus[0] is true if the player wins and false if they lose,
 gameStatus[1] is true is the player can play again and false if they must yield the dice.

 */
    public static boolean[] playCraps(boolean verbose){
        boolean[] gameStatus = new boolean[2];
        boolean win=false;
        boolean  replay=false;

        int point = comeOut(verbose);
        if (point == 7 || point == 11){ //win if first roll is 7 or 11
            if (verbose){
                System.out.println("A natural! You've won the game, but must yield the dice to the next player.");
            }
            win = true;
            replay = false;
        }
        else if (point ==2 || point==3 || point==12){ //lose if first roll is 7 or 11
            if(verbose){
                System.out.println("You've crapped out, but may play again.");
            }
            win= false;
            replay = true;
        }
        else{
            win = point(point, verbose);
            if (verbose){
                if (win){
                    System.out.println("A pass! You've won the game, and may play again.");
                    replay = true;
                }
                else{
                    System.out.println("You've sevened out, and must yield the dice to the next player.");
                    replay = false;
                }
            }
        }
        gameStatus[0] = win;
        gameStatus[1] = replay;
        return gameStatus;
    }
/*
Test how craps runs until the player sevens out and loses.
 */
    public static void test(){
        System.out.println("Welcome to the craps table.");

        boolean[] status = playCraps(true);
        boolean replay = status[1];
        while (replay){
            System.out.println("\nPlaying again...");
            replay = playCraps(true)[1];
        }

    }
/*
Computes the odds of how likely a player is to win a game of craps.
Does not print game progress out to terminal.
 */
    public static double computeOdds(int numGames){
        int wins = 0;
        for (int i=0; i<numGames; i++){
            if (playCraps(false)[0]){
                wins ++;
            }
        }
        double odds = (double) wins / numGames;
        System.out.println("\nOut of " + numGames + " games: ");
        System.out.println("\tWins:" + wins);
        System.out.println("\tOdds:" + odds);
        return odds;
    }


    public static void main(String[] args) {
        //Note: java does not count 'Craps' as arg, while python does
        if (args.length > 0){
            if (args[0].equals("test")){test();}
            else if (args[0].equals("odds")){
                int numGames = 1000;
                if (args.length > 1){ numGames = parseInt(args[1]); }
                computeOdds(numGames);
            }
        }
        else{ playCraps(true);}
    }
}

