import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * Creates the rock paper scissors GUI
 * No parameters, returns nothing
 */
public class RockPaperScissorsFrame extends JFrame {
    JPanel mainPnl, titlePnl, displayPnl, cmdPnl;
    JLabel titleLbl, compWinsLbl, userWinsLbl, tiesLbl, rockLbl, paperLbl, scissorsLbl;
    ImageIcon rockImg, paperImg, scissorsImg, xImg;
    JScrollPane scroller;
    JTextArea gameTA;
    JButton rockBtn, paperBtn, scissorsBtn, quitBtn;

    Random rnd = new Random();
    String turn;
    int compWins = 0;
    int userWins = 0;
    int ties = 0;
    int strat = 1;
    int rockCalled = 0;
    int paperCalled = 0;
    int scissorsCalled = 0;

    /**
     * Rock paper scissors constructor
     * No parameters, returns nothing
     */
    public RockPaperScissorsFrame() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);
        createTitlePanel();
        createDisplayPanel();
        createControlPanel();

        setTitle("Rock Paper Scissors!");
        setSize(650,750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Title panel constructor
     * No parameters, returns nothing
     */
    private void createTitlePanel(){
        titlePnl = new JPanel();
        titleLbl = new JLabel("Play a game of Rock Paper Scissors", JLabel.CENTER);

        titlePnl.add(titleLbl);
        mainPnl.add(titlePnl, BorderLayout.NORTH);
    }

    /**
     * Display panel constructor
     * No parameters, returns nothing
     */
    private void createDisplayPanel() {
        displayPnl = new JPanel();

        JPanel statsPnl = new JPanel();
        JPanel turnsPnl = new JPanel();

        userWinsLbl = new JLabel("Wins: " + userWins);
        compWinsLbl = new JLabel("Losses: " + compWins);
        tiesLbl = new JLabel("Ties: " + ties);

        statsPnl.add(compWinsLbl);
        statsPnl.add(userWinsLbl);
        statsPnl.add(tiesLbl);

        rockLbl = new JLabel("Rock: " + rockCalled);
        paperLbl = new JLabel("Paper:  " + paperCalled);
        scissorsLbl = new JLabel("Scissors: " + scissorsCalled);

        turnsPnl.add(rockLbl);
        turnsPnl.add(paperLbl);
        turnsPnl.add(scissorsLbl);

        displayPnl.add(turnsPnl);

        displayPnl.add(statsPnl);

        gameTA = new JTextArea(20,50);
        scroller = new JScrollPane(gameTA);
        displayPnl.add(scroller);
        mainPnl.add(displayPnl);
    }

    /**
     * Control panel constructor
     * No parameters, returns nothing
     */
    private void createControlPanel() {

        rockImg = new ImageIcon("src/rockImg.png");
        paperImg = new ImageIcon("src/paperImg.png");
        scissorsImg = new ImageIcon("src/scissorsImg.png");
        xImg = new ImageIcon("src/xImg.png");

        cmdPnl = new JPanel();
        cmdPnl.setLayout(new GridLayout(1,3));

        rockBtn = new JButton(rockImg);
        paperBtn = new JButton(paperImg);
        scissorsBtn = new JButton(scissorsImg);
        quitBtn = new JButton(xImg);

        // Quit button has additional ability to display a JOptionPane to the screen to confirm exiting the game
        quitBtn.addActionListener((ActionEvent ae) -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

        });

        // Sets turn to rock and forces computer to take its turn
        rockBtn.addActionListener((ActionEvent ae) -> {

            turn = "R";
            gameTA.append(getResults(turn));

        });

        // Sets turn to paper and forces computer to take its turn
        paperBtn.addActionListener((ActionEvent ae) -> {

            turn = "P";
            gameTA.append(getResults(turn));

        });

        // Sets turn to scissors and forces computer to take its turn
        scissorsBtn.addActionListener((ActionEvent ae) -> {

            turn = "S";
            gameTA.append(getResults(turn));

        });

        cmdPnl.add(rockBtn);
        cmdPnl.add(paperBtn);
        cmdPnl.add(scissorsBtn);
        cmdPnl.add(quitBtn);

        mainPnl.add(cmdPnl, BorderLayout.SOUTH);

    }
    /**
     * Generates the results of the game based on what the user has clicked
     * @param turn - the String used to record the player's move
     * @return the String that gets appended to the screen
     */
    private String getResults(String turn){

        String compTurn = "";

        // Stores moves made by player in above variables
        if(turn.equals("R")){
            rockCalled += 1;
            rockLbl.setText("Rock: " + rockCalled);
        }else if(turn.equals("P")){
            paperCalled += 1;
            paperLbl.setText("Paper: " + paperCalled);
        }else if(turn.equals("S")){
            scissorsCalled += 1;
            scissorsLbl.setText("Scissors: " + scissorsCalled);

        }

        /**
         * STRATEGIES!
         * Strategies are initialized to 1, so the first round is always random.
         * Then, any time after, a number from 1-6 is generated
         * Strategy number corresponds to a specific randomized strategy the computer will take:
         * 1 - Random
         * 2 - Computer moves based on what move is used MOST by the player
         * 3 - Computer moves based on what move is used LEAST by the player
         * 4 - Computer cheats & moves based on what the user has picked
         * 5 - Computer lets you win & picks the losing move based on what the user has picked
         * 6 - Forced tie (this was the easiest to code!)
         * Each strategy has an equal chance of being used. I could have gone further to give each one a probability, but I think leaving it this way makes it more unpredictable and healthily chaotic.
         * Have fun!
         */

        // Strategy 1 - Random
        if(strat == 1){
            int rand = rnd.nextInt(3) + 1;

            if(rand == 1){
                compTurn = "R";
            }else if(rand == 2){
                compTurn = "P";
            }else if(rand == 3){
                compTurn = "S";
            }

        //Strategy 2 - Most Used
        }else if(strat == 2){

            if(rockCalled > paperCalled && rockCalled > scissorsCalled){
                compTurn = "R";
            }else if(paperCalled > rockCalled && paperCalled > scissorsCalled){
                compTurn = "P";
            }else if(scissorsCalled > rockCalled && scissorsCalled > paperCalled){
                compTurn = "S";
            }else{
                int rand = rnd.nextInt(3) + 1;
                if(rand == 1){
                    compTurn = "R";
                }else if(rand == 2){
                    compTurn = "P";
                }else if(rand == 3){
                    compTurn = "S";
                }
            }

        // Strategy 3 - Least Used
        }else if(strat == 3){

            if(rockCalled < paperCalled && rockCalled < scissorsCalled){
                compTurn = "R";
            }else if(paperCalled < rockCalled && paperCalled < scissorsCalled){
                compTurn = "P";
            }else if(scissorsCalled < rockCalled && scissorsCalled < paperCalled){
                compTurn = "S";
            }else{
                int rand = rnd.nextInt(3) + 1;
                if(rand == 1){
                    compTurn = "R";
                }else if(rand == 2){
                    compTurn = "P";
                }else if(rand == 3){
                    compTurn = "S";
                }
            }

        // Strategy 4 - Cheating Computer
        }else if(strat == 4){

            if(turn.equals("R")){
                compTurn = "P";
            }else if(turn.equals("P")){
                compTurn = "S";
            }else if(turn.equals("S")){
                compTurn = "R";
            }

        // Strategy 5 - Playing Nice
        }else if(strat == 5){

            if(turn.equals("R")){
                compTurn = "S";
            }else if(turn.equals("P")){
                compTurn = "R";
            }else if(turn.equals("S")){
                compTurn = "P";
            }

        // Strategy 6 - It's Just Gonna Tie
        }else if(strat == 6){

            compTurn = turn;

        }

        // Prints to the screen the strategy used in a roundabout way
        if (strat == 1){
            gameTA.append("I left it up to luck today." + "\n");
        }else if(strat == 2){
            if(rockCalled > paperCalled && rockCalled > scissorsCalled){
                gameTA.append("You seem to use rock a lot." + "\n");
            }else if(paperCalled > rockCalled && paperCalled > scissorsCalled){
                gameTA.append("You seem to use paper a lot." + "\n");
            }else if(scissorsCalled > rockCalled && scissorsCalled > paperCalled){
                gameTA.append("You seem to use scissors a lot." + "\n");
            }else{
                gameTA.append("I couldn't figure out what you used most, so I resorted to luck." + "\n");
            }

        }else if(strat == 3){
            if(rockCalled < paperCalled && rockCalled < scissorsCalled){
                gameTA.append("You don't seem to use rock much." + "\n");
            }else if(paperCalled < rockCalled && paperCalled < scissorsCalled){
                gameTA.append("You don't seem to use paper much." + "\n");
            }else if(scissorsCalled < rockCalled && scissorsCalled < paperCalled){
                gameTA.append("You don't seem to use scissors much." + "\n");
            }else{
                gameTA.append("I couldn't figure out what you used least, so I resorted to luck." + "\n");
            }
        }else if(strat == 4){
            gameTA.append("Ha ha. You didn't stand a chance." + "\n");
        }else if(strat == 5){
            gameTA.append("I went easy on you." + "\n");
        }else if (strat == 6){
            gameTA.append("Who on Earth would cheat the game to force a tie? I would. That's who." + "\n");
        }

        // Randomizes the next strategy
        strat = rnd.nextInt(6) + 1;


        // Return Statements based on results
        if(turn.equals(compTurn)){
            ties++;
            tiesLbl.setText("Ties: " + ties);
            return "It's a draw." + "\n";
        }else if((turn.equals("R") && compTurn.equals("S")) || (turn.equals("P") && compTurn.equals("R")) || (turn.equals("S") && compTurn.equals("P"))){
            userWins++;
            userWinsLbl.setText("Wins: " + userWins);
            return "You win!" + "\n";
        }else if(turn.equals("R") && compTurn.equals("P")){
            compWins++;
            compWinsLbl.setText("Losses: " + compWins);
            return "Your rock has been covered. You lose." + "\n";
        }else if(turn.equals("P") && compTurn.equals("S")){
            compWins++;
            compWinsLbl.setText("Losses: " + compWins);
            return "Your paper has been cut. You lose." + "\n";
        }else if(turn.equals("S") && compTurn.equals("R")){
            compWins++;
            compWinsLbl.setText("Losses: " + compWins);
            return "Your scissors have been smushed. You lose." + "\n";
        }

        // Had to add this here so that IntelliJ doesn't scream at me
        return "placeholder text";
    }
}