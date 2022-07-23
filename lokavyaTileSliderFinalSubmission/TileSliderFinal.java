package sunnyTileSliderFinalSubmission;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.collections.ObservableList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.io.File;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

//Attention!!!
//Hello Ms. Braaten. This program gets buggy when used on differnt displays that have different sizes. It works perfectly on 
//my monitor, but shows extra white spaces between each tile on my laptop screen. The spacing within the written code is correct,
//and even after trying fixes and searching online, I wasn't able to find a solution. If there are white spaces between each
//of the 25 tiles on your particular display (assuming you mark it before Friday, April 23rd), I can show you pictures of the 
//program working on my monitor.
//Thanks for understanding!
/**
 * TODO update the header with your details.
 * Write a description of JavaFX class Tile Slider here.
 * This program will play a tile slider game with an image and 25 tiles using JavaFx. The program will allow a user to manually
 * shuffle their tiles, and will detect when they have correctly solved it. The program will also track their number of moves.
 * @author (Sunny Jain)
 * @version (Final Version: April 21st, 2021)
 */
public class TileSliderFinal extends Application
{
    // Track: Create the Tracking Area
    private TextArea trackArea = new TextArea(""); 

    Image[][] pic, rightPic;
    Image blank;

    int blankX, blankY;
    Button startSolve, takeAway, reset;

    int correct;
    AudioClip cheer, shuffle, swap, notPossible;
    int moves, steps;
    String turn;
    GridPane gridPane;

    @Override
    public void start(Stage stage) throws Exception {
        // These are the counters that will be displayed in the text box
        moves=0;
        steps=0;

        // Importing the audio files I'll be using later on in the program.
        String uriString = new File("applause.wav").toURI().toString();
        cheer = new AudioClip(uriString);

        // Adding an extra sound if a move is not possible
        uriString = new File("error.mp3").toURI().toString();
        notPossible = new AudioClip(uriString);

        uriString = new File("click.wav").toURI().toString();
        swap = new AudioClip(uriString);

        uriString = new File("shuffle.wav").toURI().toString();
        shuffle = new AudioClip(uriString);

        // Creating a button for "remove tile" to start the program
        takeAway=new Button("Remove a Tile");
        takeAway.setStyle("-fx-font-size: 12pt;");

        // Create a button for "Start to Solve"
        startSolve=new Button("Start to Solve");
        startSolve.setStyle("-fx-font-size: 12pt;");

        // Create a button for "Reset"
        reset=new Button("Reset");
        reset.setStyle("-fx-font-size: 12pt;");

        // Giving the buttons funcitonality when they are pressed
        takeAway.setOnAction(this::buttonPressed);
        startSolve.setOnAction(this::buttonPressed);
        reset.setOnAction(this::buttonPressed);

        // Load the images into the array by [col][row]
        //Fix the indexing!
        pic=new Image[5][5];
        pic[0][0]=new Image ("raptors.00.jpg");
        pic[1][0]=new Image ("raptors.01.jpg");
        pic[2][0]=new Image ("raptors.02.jpg");
        pic[3][0]=new Image ("raptors.03.jpg");
        pic[4][0]=new Image ("raptors.04.jpg");
        pic[0][1]=new Image ("raptors.10.jpg");
        pic[1][1]=new Image ("raptors.11.jpg");
        pic[2][1]=new Image ("raptors.12.jpg");
        pic[3][1]=new Image ("raptors.13.jpg");
        pic[4][1]=new Image ("raptors.14.jpg");
        pic[0][2]=new Image ("raptors.20.jpg");
        pic[1][2]=new Image ("raptors.21.jpg");
        pic[2][2]=new Image ("raptors.22.jpg");
        pic[3][2]=new Image ("raptors.23.jpg");
        pic[4][2]=new Image ("raptors.24.jpg");
        pic[0][3]=new Image ("raptors.30.jpg");
        pic[1][3]=new Image ("raptors.31.jpg");
        pic[2][3]=new Image ("raptors.32.jpg");
        pic[3][3]=new Image ("raptors.33.jpg");
        pic[4][3]=new Image ("raptors.34.jpg");
        pic[0][4]=new Image ("raptors.40.jpg");
        pic[1][4]=new Image ("raptors.41.jpg");
        pic[2][4]=new Image ("raptors.42.jpg");
        pic[3][4]=new Image ("raptors.43.jpg");
        pic[4][4]=new Image ("raptors.44.jpg");

        // Set an image to be the blank tile
        blank=new Image ("g.jpg");

        // Create a GridPane to hold the tiles
        gridPane = new GridPane();

        // Use nested for loops to load the images into imageViews and 
        // load into gridPane.
        for (int i=0;i<5;i++) {
            for (int j=0;j<5;j++) {
                ImageView imageView = new ImageView(pic[i][j]);
                gridPane.add(imageView,i,j);
            }
        }

        // Use nested for loops to make a copy of pic into rightPic to
        // keep a copy of the solution.
        rightPic=new Image[5][5];
        for (int i=0;i<5;i++) {
            for (int j=0;j<5;j++) {
                rightPic[i][j]=pic[i][j];
            }
        }

        // Creating a border pane, spacing it, creating the vBox which will contain the buttons, and formatting the textArea...
        //so that it cannot be edited by the user
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.setPrefWidth(250); 
        trackArea.setEditable(false);
        trackArea.setFocusTraversable(false);
        trackArea.setMouseTransparent(true);

        // Putting the buttons into a vBox, which will then be inserted into the right side of the border pane
        vBox.getChildren().add(takeAway);
        vBox.getChildren().add(startSolve);
        vBox.getChildren().add(reset);
        vBox.getChildren().add(trackArea);

        // putting the gridPane and the vBox inside the borderPane to the left and right respectively
        borderPane.setRight(vBox);
        borderPane.setCenter(gridPane);

        // Add the borderPane to the Scene
        Scene scene = new Scene(borderPane, 725, 525);

        // Set up a key event handler
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    handleKeyPress (event);
                }
            });

        stage.setScene(scene);
        //This stops the user from resizing the pane.
        stage.setResizable(false);
        stage.show();
    }

    // Now that the window has been set up visually, we need to add functionality to the input buttonsb 
    //also, every time we get input from the "handle key press" event, we execute this section of code
    private void handleKeyPress (KeyEvent event) {
        // TODO: complete this method to include all 4 movements (up, down, left, right)
        // TODO: check the boundaries so that the user cannot go outside the parameters.
        switch (event.getCode()) {
            case W: //UP 
            //Make sure the move is possible, blank is above
            if (blankY < 4) {
                // // Swap the blank tile with the tile below it to appear moving up
                pic[blankX][blankY]=pic[blankX][blankY + 1];
                replaceTile(blankX, blankY, pic[blankX][blankY]);
                blankY = blankY + 1;
                replaceTile(blankX, blankY, blank);

                // Play the swap sound
                swap.play();
            } else {
                //put in an error sound that it can play
                notPossible.play();
                //reduce the number of the counter using if and else
                if (turn.equals("mix")) {
                    moves = (moves -1);
                } else if (turn.equals("solve")){
                    steps = (steps - 1);
                }
            }
            break;

            case S:  //DOWN
            //Make sure the move is possible, blank is below
            if (blankY > 0) {
                // Swap the blank tile with the tile above to appear moving down
                pic[blankX][blankY]=pic[blankX][blankY - 1];
                replaceTile(blankX, blankY, pic[blankX][blankY]);
                blankY = blankY - 1;
                replaceTile(blankX, blankY, blank);

                // Play the swap sound
                swap.play();
            } else {
                //put in an error sound that it can play
                notPossible.play();
                //reduce the number of the counter using if and else
                if (turn.equals("mix")) {
                    moves = (moves -1);
                } else if (turn.equals("solve")){
                    steps = (steps - 1);
                }
            }
            break;
           
            case A:  //LEFT
            // Make sure move is possible, blank is to left
            if (blankX < 4) {
                // Swap the blank tile with the tile to right to appear moving left
                pic[blankX][blankY]=pic[blankX+1][blankY];
                replaceTile(blankX, blankY, pic[blankX][blankY]);
                blankX = blankX + 1;
                replaceTile(blankX, blankY, blank);

                // Play the swap sound
                swap.play();
            } else {
                //put in an error sound that it can play
                notPossible.play();
                //reduce the number of the counter using if and else
                if (turn.equals("mix")) {
                    moves = (moves -1);
                } else if (turn.equals("solve")){
                    steps = (steps - 1);
                }
            }
            break;

            case D: //RIGHT
            //Make sure the move is possible, blank is to the right
            if (blankX > 0) {
                // Swap the blank tile with the tile to left to appear moving right
                pic[blankX][blankY]=pic[blankX-1][blankY];
                replaceTile(blankX, blankY, pic[blankX][blankY]);
                blankX = blankX - 1;
                replaceTile(blankX, blankY, blank);

                // Play the swap sound
                swap.play();
            } else {
                //put in an error sound that it can play.
                notPossible.play();
                //reduce the number of the counter using if and else
                if (turn.equals("mix")) {
                    moves = (moves -1);
                } else if (turn.equals("solve")){
                    steps = (steps - 1);
                }
            }
            break;
        }

        // This is the block of code that executes if the player clicks on "solve".
        if (turn.equals("solve")) {
            steps++;
            correct=0;

            //TODO: Compare the pic array to the rightPic array to check how many match
            for (int x=0;x<5;x++) {
                for (int y=0; y<5; y++) {
                    if (rightPic[x][y] == pic [x][y]){
                        correct = correct + 1;
                    }
                }
            }

            trackArea.setText("Mixing Moves Made: " + moves+"\n");
            trackArea.appendText("Solving Steps Made: "+steps);
        }
        else if (turn.equals("mix")) {
            moves++;
            trackArea.setText("Mixing Moves Made: "+moves);
        }

        // This if statement takes the value of correct and checks if all the available tiles are in the correct location.
        // If so, it replaces the blank tile with the correct tile, makes buttons avaliable, and resets the counter.
        if (correct==24) {
            //remove(solve);
            // Swap the blank tile with the missing tile to complete
            pic[blankX][blankY]=rightPic[blankX][blankY];
            replaceTile(blankX, blankY, pic[blankX][blankY]);
            cheer.play();
            startSolve.setDisable(false);
            takeAway.setDisable(false);
            moves = 0;
            steps = 0;
        }
    }

    /**
     * This method will play multiple roles based on what the user clicks on the right panel in the application. It removes a
     * random tile visially from pic, disables the "start solve" button once it is pressed, and resets the board using the 
     * right pic array as a reference. 
     */
    private void buttonPressed (ActionEvent event)
    {
        //Gets info about the clicked button
        Button button = (Button) event.getSource();

        //Get the text on the button
        String text = button.getText();

        if (text.equals("Remove a Tile")) {

            // Play the suffle sound.
            shuffle.play();
            correct=0; // number of correct pieces

            // Generate a random place for the blank space
            blankX=(int)(Math.random()*5);
            blankY=(int)(Math.random()*5);
            pic[blankX][blankY]=blank;

            // Replace the current tile with a blank.
            replaceTile(blankX, blankY, blank);

            // Disable the take away button.
            takeAway.setDisable(true);

            //set flag to keep track of mix up moves
            turn = "mix"; 
        }
        else if (text.equals("Start to Solve")){
            // Set flag to keep track of solve moves
            turn = "solve"; 

            // Disable the solve button.
            startSolve.setDisable(true);
        }
        else if (text.equals("Reset")){
            // Initiatize the number of moves and steps for tracking
            moves = 0;
            steps = 0;

            //TODO: reset the puzzle to the start and reset the availability of the buttons.

            for (int i=0;i<5;i++) {
                for (int j=0;j<5;j++) {
                    pic[i][j] = rightPic[i][j];
                    replaceTile(i, j, pic[i][j]);
                }
            }

            //Making the buttons avaliable

            startSolve.setDisable(false);
            takeAway.setDisable(false);            
        }
    }

    /**
     * This method will replace an image in the gridPane with a new one
     */
    private void replaceTile (int col, int row, Image image)
    {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == col) {
                result = node;
                gridPane.getChildren().remove(result);
                ImageView imageView = new ImageView(image);
                gridPane.add(imageView,col,row);
                break;
            }
        }
    }
}