package projeect_snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.LinkedList;
import java.awt.Point;
import java.nio.file.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


public class Projeect_snake extends Application {
    //the dimantions of scene 2 that the snake exist
    private static final int WIDTH = 481;
    private static final int HEIGHT = 648;
    private static final int ROWS = 25;
    private static final int COLUMNS = 25;
    private static final int SQUARE_SIZE = 481/25;
    
    //the directions that the snake move and it suits to work with boolean
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private static int currentDirection = 0;                     //the constant to move the snake in four directions
    
    private static int score = 0;                               //the constant that calculated when snake eat the food and it not saved (5)
    private static int bestScore =0;                            //the constant that calculated when snake eat the food and it not saved
    private static int fruitEaten = 0;                          //the constant that calculated when snake eat the food and it not saved (1)
    
    //this constant to help us in restart and pause methods in if condition to ON-OFF
    private static boolean pause = false;
    private static boolean gameOver = false;                      //the constant to end the game when the sanke fail
    
    //photos of the food of the snake
    private static final String[] FOOD_IMAGES = new String[]{"/image/ic_apple.png","/image/ic_berry.png","/image/ic_cherry.png","/image/ic_coconut.png","/image/ic_orange.png","/image/ic_peach.png","/image/ic_watermelon.png","/image/ic_pomegranate.png","/image/ic_tomato.png"};
    
    //the difienation of gc to draw the scene of the game
    private GraphicsContext gc ;
    //this linked list help us to make the body of the snake it easier than arraylist in delet and replacing
    private LinkedList<Point> snake_body  = new LinkedList();
    private Point snake_head;                                           //the constant to make the first point of the snake when we start the game
    private Timeline timeline;                                          //the constant of the movment of snake
    private MediaPlayer mediaPlayer;
    private ComboBox<String> colorComboBox = new ComboBox<>();         //coboBox to control the colors of background of the game
    private Image food;                                                //the constant that help us to choose images of photo random
   //the constant to set the food in X-Y random
    private int foodX;
    private int foodY;
    private String selectedColor = "BLACK";                            //the defualt of the color of scene2 is black
    private boolean sound_clicked = false;                             //to know if the off button was clicked or no
    Image image1 = new Image("/image/MN.jpg");                          //the image of scene1
    Image image2 = new Image("image/MH.jpg");                           //the image of scene3
    Image image3 = new Image("/image/N.jpg");
    
    //use to appeare the photos of scene1-scene3-scene4
    ImageView imageView1 = new ImageView(image1);                      
    ImageView imageView2 = new ImageView(image2);
    ImageView imageView3 = new ImageView(image3);
    
    Button play = new Button("play");                                   //button in scene1 that start the start the game
    Button btnsitting = new Button("⚙");                                //button in scene1 that go to steeing
    Button back1 = new Button("➡");                                     //button in scene3 that return to scene1
    Button back2 = new Button("➡");                                     //button in scene4 that return to scene1
    Button on = new Button("ON");                                       //button in scene3 that turn on the sound if it close
    Button off = new Button("OFF");                                     //button in scene3 that turn off the sound if it open
    Button exit = new Button("Exit");                                   //button in scene1 that exit the game
    Button about = new Button("About");                                 //button in scene1 that describe the game
    
    Label t1 = new Label("Chang THEME :");                               //label in scene3 that change the colors of background 
    
    //labeles in scene3 that describe how to play the game 
    Label t2 = new Label("Controls :");
    Label t3 = new Label("Pause / Start : Space");
    Label t4 = new Label("Restart : Enter");
    Label t5 = new Label("Move Up : Arrow Up");
    Label t6 = new Label("Move Down : Arrow Down");
    Label t7 = new Label("Move Left : Arrow Left");
    Label t8 = new Label("Move Right : Arrow Right");
    Label t10 = new Label("Back : Escape");
    
    Label t9 = new Label("Music 🎵 :");                                         //label that refere to music
   
    public void start(Stage primaryStage) {
    //the dimantions of image2 in scene1
    imageView2.setFitWidth(300);
    imageView2.setFitHeight(400);
        
    StackPane root1 = new StackPane(imageView1,play,about,exit,btnsitting);      //pane of scene1 that contain the background, 3 main button and button of setting
    Pane root2 = new Pane();
    StackPane root3 = new StackPane(imageView2,back1,colorComboBox,on,off,t1,t2,t3,t4,t5,t6,t7,t8,t10,t9);      //pane of scene3 "setting" that contain the background, 2comboBox, labels and button of the back
    
    Scene scene1 = new Scene(root1);         
    Scene scene2 = new Scene(root2);
    Scene scene3 = new Scene(root3,300,400);
    
    //the dimantions of button "play" in scene1 
    play.setPrefWidth(150);
    play.setPrefHeight(20);
    play.setStyle("-fx-text-fill: black; -fx-font-size: 20px;");               //the apperance of button "play"
    play.setTranslateX(0);                                                     //the positiion of the button for the scene in stage
    play.setTranslateY(-30);
    play.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, new CornerRadii(5), Insets.EMPTY)));
    //the shadow back of the button "play" in scene1
    DropShadow shadow1 = new DropShadow(20, Color.ALICEBLUE);
                play.setOnMouseMoved(e ->play.setEffect(shadow1));
                play.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                        play.setEffect(null);
                });  
         //the dimantions of button "exit" ,appperance ,and position in scene1       
        exit.setPrefWidth(150);
        exit.setPrefHeight(20);
        exit.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, new CornerRadii(5), Insets.EMPTY)));
        exit.setStyle("-fx-text-fill: black; -fx-font-size: 20px;");
        exit.setTranslateX(0);
        exit.setTranslateY(90);
        //the shadow back of the button "exit" in scene1
        DropShadow shadow2 = new DropShadow(20, Color.RED);
                exit.setOnMouseMoved(e -> exit.setEffect(shadow2));
                exit.addEventHandler(MouseEvent.MOUSE_EXITED,e-> exit.setEffect(null));
                exit.setOnAction(e-> primaryStage.close());
         //the dimantions of button "about" ,appperance ,and position in scene1          
        about.setPrefWidth(150);
        about.setPrefHeight(20);
        about.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, new CornerRadii(5), Insets.EMPTY)));
        about.setStyle("-fx-text-fill: black; -fx-font-size: 20px;");
        about.setTranslateX(0);
        about.setTranslateY(30);
        //the shadow back of the button "about" in scene1
        DropShadow shadow3 = new DropShadow(20, Color.ALICEBLUE);
                about.setOnMouseMoved(e -> about.setEffect(shadow3));
                about.addEventHandler(MouseEvent.MOUSE_EXITED,e -> about.setEffect(null));
        about.setOnAction(e -> primaryStage.setScene(about()));
        //the dimantions of button "sitting" ,appperance ,and position in scene1
        btnsitting.setPrefWidth(150);
        btnsitting.setPrefHeight(50);
        btnsitting.setBackground(Background.EMPTY);
        btnsitting.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");
        btnsitting.setTranslateX(220);
        btnsitting.setTranslateY(-300);
       //the dimantions of button "back1" ,appperance ,and position in scene3
        back1.setBackground(Background.EMPTY);
        back1.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        back1.setTranslateX(140);
        back1.setTranslateY(-190);
        back1.setOnAction(e-> primaryStage.setScene(scene1));
        //the dimantions of button "back2" ,appperance ,and position in scene3
        back2.setBackground(Background.EMPTY);
        back2.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        back2.setTranslateX(220);
        back2.setTranslateY(-300);
        back2.setOnAction(e-> {        
                 primaryStage.setScene(scene1);      
         });
        //the dimantions of button "on" ,appperance ,font ,and position in scene3
        on.setFont(new Font("Cambria", 10));  
        on.setTranslateX(60);
        on.setTranslateY(-60);
        on.setStyle("-fx-background-color: white;");  
        on.setOnAction(e -> {mediaPlayer.play(); sound_clicked = false;});                    //the action of button "on" to turn on sound if it off
        //the dimantions of button "off" ,appperance ,font ,and position in scene3
        off.setFont(new Font("Cambria", 10));
        off.setTranslateX(100);
        off.setTranslateY(-60);
        off.setStyle("-fx-background-color: white;");
        off.setOnAction(e -> {mediaPlayer.pause(); sound_clicked = true;});                   //the action of button "off" to turn on sound if it on
         //the type of the font for labels apperance ,and position
        Label[] t = {t1,t2,t3,t4,t5,t5,t6,t7,t8,t9,t10};
        for(int i = 0; i<11 ; i++){
        t[i].setFont(new Font("Cambria", 15));
        t[i].setBackground(Background.EMPTY);
        t[i].setStyle("-fx-text-fill: white;");
        }
        t1.setFont(new Font("Cambria", 20));
        t2.setFont(new Font("Cambria", 20));
        t9.setFont(new Font("Cambria", 20));
        t1.setTranslateX(-80);
        t1.setTranslateY(-140);
        t2.setTranslateX(-100);
        t2.setTranslateY(-20);
        t2.setStyle("-fx-text-fill: black;");
        t3.setTranslateX(-80);
        t3.setTranslateY(0);
        t4.setTranslateX(-98);
        t4.setTranslateY(20);
        t5.setTranslateX(-80);
        t5.setTranslateY(40);
        t6.setTranslateX(-60);
        t6.setTranslateY(60);
        t7.setTranslateX(-75);
        t7.setTranslateY(80);
        t8.setTranslateX(-65);
        t8.setTranslateY(100);
        t9.setTranslateX(-95);
        t9.setTranslateY(-60);
        t10.setTranslateX(-105);
        t10.setTranslateY(120);
                           
        //the apperance of comboBox1 and position
        colorComboBox.setStyle("-fx-background-color:white; -fx-text-fill:#333333; -fx-font-family: Arial");
        colorComboBox.setTranslateX(80);
        colorComboBox.setTranslateY(-140);
        colorComboBox.getItems().addAll("BLACK","WHITE","BURLYWOOD","GREY");                       //the options of coboBox1 that contain themes
        colorComboBox.getSelectionModel().select(0);                                               //to appera BLACK as your default choice
        colorComboBox.setOnAction(e ->selectedColor = colorComboBox.getValue());                   //the action that open comboBox
        //set action for button "play" that exist in scene2 and appear in primary stage
        play.setOnAction(e-> { 
            primaryStage.setScene(scene2);
            timeline.play();
            restartGame();     
         });
        btnsitting.setOnAction(e-> primaryStage.setScene(scene3));
        music();                                                              //call music's methods
        mediaPlayer.setCycleCount(Timeline.INDEFINITE);                       //to make the music on all time
        mediaPlayer.play();                                                   //turn on the music one open the game
        //set actions form key board to move snake-restart-pause-back
        scene2.setOnKeyPressed(e -> {
           if(e.getCode() == KeyCode.RIGHT     && currentDirection != LEFT) { currentDirection = RIGHT;}
           else if(e.getCode() == KeyCode.LEFT && currentDirection != RIGHT){ currentDirection = LEFT;}
           else if(e.getCode() == KeyCode.UP   && currentDirection != DOWN) { currentDirection = UP;}
           else if(e.getCode() == KeyCode.DOWN && currentDirection != UP)   { currentDirection = DOWN;}
           else if(e.getCode() == KeyCode.SPACE){
               if(pause)  resumeGame();
               else pauseGame();}
           else if(e.getCode() == KeyCode.ENTER ){restartGame();}
           else if (e.getCode() == KeyCode.ESCAPE) { primaryStage.setScene(scene1);pauseGame();}});
       
        timeline = new Timeline(new KeyFrame(Duration.millis(150), e ->moving_snake()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        //set scene1 in main stage "primary stage" and appeare it with title
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Snake 2D");
        primaryStage.show();
        Canvas canva = new Canvas(WIDTH,HEIGHT);                             //call canvas to use animation and appeare it
        root2.getChildren().addAll(canva);
        gc = canva.getGraphicsContext2D();                                               
        buliding_snake();                                                     //call the method to build the snake body
        making_food();                                                        //call the method to make the food 
    }  
    //this method to move snake in scene2 when we change diracton by keyboard
    public void moving_snake(){
         //if the snake crush in the frame or the body it appear gameover in blue color and stop music
        if(gameOver){
           mediaPlayer.pause();
           gc.setFill(Color.BLUE);
           gc.setFont(new Font("Digital-7",70));
           gc.fillText("Game Over",72,325);
           return;
        }
        background();
        drawing_food();
        score();
        bestScore();
        fruitEaten();
        drawing_snake();
     //move each point to the next point as the snake eat food To make the snake longer   
     for(int i = snake_body.size() - 1; i>0;i--){
     snake_body.get(i).x= snake_body.get(i-1).x;
     snake_body.get(i).y= snake_body.get(i-1).y;}
    
    switch (currentDirection){
        case RIGHT:
            snake_head.x++;
            break;
        case LEFT:
            snake_head.x--;
            break;
        case UP:
            snake_head.y--;
            break;    
        case DOWN:
            snake_head.y++;
            break;
    }    
    gameOver();                                //call the gameover method
    eating_food();                             //call the eating food method
    }
    
    //this method to build the first body of the snake that contain head and 2 square in the body
    public void buliding_snake(){
    for(int i = 0 ;i < 3;i++)
        snake_body.add(new Point(5,ROWS/2));
        snake_head = snake_body.get(0);
    }
   
    //the method that drawing snake in scene2 and control the size and color of snake
    public void drawing_snake(){
    gc.setFill(Color.BLUE);
    gc.fillRoundRect(snake_head.getX()* SQUARE_SIZE,snake_head.getY()* SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE,40,40);
    for(int i = 1;i < snake_body.size();i++)
        gc.fillRoundRect(snake_body.get(i).getX()* SQUARE_SIZE,snake_body.get(i).getY()* SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE,10,10);}
    
    //method that generate food randomly in scene exept on the snake or out the frame
    public void making_food(){
     foodX = 3 + (int) (Math.random()*14);
     foodY = 3 + (int) (Math.random()*20);
     food = new Image(FOOD_IMAGES[(int)(Math.random()* FOOD_IMAGES.length)]);
    }
    
//method that generate in size of square
    public void drawing_food(){gc.drawImage(food,foodX * SQUARE_SIZE,foodY * SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);}

    //method that calculate score-bestscore-fruiteaten when snake eat the food when the point of heat exist in food
    public void eating_food(){
    if(snake_head.x == foodX && snake_head.y == foodY){
        snake_body.add(new Point(-1,-1));                            //generate new square in point (-1,-1) and increase it by the body of snake
        making_food();                                                //call method of making food to generate new food in new place
        score+=5;
        fruitEaten++;}
    }

    //method that control the colors of background in scene2 and control change the colors by comboBox
    public void background(){
    gc.setFill(Color.BLACK);                   //for the lower background,
    gc.fillRect(0, 0, 481, 648);
    gc.setFill(Color.GRAY);                    // for the border        
        for (int i = 4; i <= 466; i += 17) {
            for (int j = 2; j <= 645; j += 17) {
                gc.fillRect(i, j, 13, 13);}}
    switch (selectedColor) {                            //check for the selectedColor from the comboBox to put it for the scene2
            case "WHITE":
                gc.setFill(Color.WHITE);
                break;
            case "BLACK":
                gc.setFill(Color.BLACK);
                break;
            case "BURLYWOOD":
                gc.setFill(Color.BURLYWOOD);
                break;
            case "GREY":
                gc.setFill(Color.GREY);
                break;
            default:
                gc.setFill(Color.WHITE);
                break;}
        gc.fillRect(17, 70, 443, 560);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 481, 50);
        gc.setFill(Color.CYAN);              //to make space for the title of the game
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        gc.fillText("Snake 2D", 3, 25);
    }

    //method to calculate the score didn't store
    public void score(){
        gc.setFill(Color.CYAN);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " , 3, 45);
        gc.fillText(""+score , 70, 45);}

    //method to calculate the best score until close the main stage store
    public void bestScore(){
    if (bestScore<=score){
                bestScore=score;}
        gc.setFill(Color.CYAN);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Best Score: " , 120, 45);
        gc.fillText(""+bestScore , 230, 45);
    }
    
    //method to calculat the numbers of fruit from one point
    public void fruitEaten(){
        gc.setFill(Color.CYAN);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Fruit Eaten: " , 300, 45);
        gc.fillText(""+fruitEaten , 420, 45);
    }    
    
    //method that appear "gameover" when the sanke crush to frame or his body 
     public void gameOver() {
        if (snake_head.x < 1 || snake_head.y < 1 || snake_head.x * SQUARE_SIZE >= 443 || snake_head.y * SQUARE_SIZE >= 620  || snake_head.y * SQUARE_SIZE <= 56) {
            gameOver = true;}
        
        for (int i = 1; i < snake_body.size(); i++) {
            if (snake_head.x == snake_body.get(i).getX() && snake_head.getY() == snake_body.get(i).getY()) {
                gameOver = true;
                break;}}}
     
    public void pauseGame(){pause = true; timeline.pause();}                      //this method to pause the game when we enter the space if the game on
    
    public void resumeGame(){pause = false; timeline.play();}                     //this method to resume the game when we enter the space if the game off
   
//thes method to restart the game and clear the number of score and fruitEaten without clear the number of best score
    public void restartGame(){
        if(!sound_clicked){
         mediaPlayer.play();
        }
        gameOver = false;
        score = 0;
        fruitEaten = 0;
        currentDirection = RIGHT;
        snake_body.clear();
        buliding_snake();
        moving_snake();
        drawing_food();
        making_food();
        timeline.play();
    }

    //method to play music when we start the game 
 public void music() {      
   String s = "C:\\Users\\DELL\\Documents\\NetBeansProjects\\projeect_snake\\src\\sound\\Fluffing-a-Duck.mp3";
   Media h = new Media(Paths.get( s ).toUri().toString());
   mediaPlayer = new MediaPlayer(h); 
   mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);                           //to make the music indefinite through the game and not end 
 }

 /* this method that control the appearance of scene4 when we click "about" button
 it contain 10 labels and the logo's photo */
public Scene about(){
    Label l1 = new Label("ABOUT US : ");
    Label l2 = new Label("Mariam George");
    Label l3 = new Label("Mariam Nader");
    Label l4 = new Label("Menna Gamal");
    Label l5 = new Label("Manal Ali");
    Label l6 = new Label("Mai Mohamed");
    Label l7 = new Label("Noura Hamza");
    Label l8 = new Label("Naira Ebrahim");
    Label l9 = new Label("Heba Mohamed");
    Label l10 = new Label("Heba Ragheb");
    Label[] l = {l1,l2,l3,l4,l5,l6,l7,l8,l9,l10};
    StackPane root4 = new  StackPane(back2,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,imageView3);
   //the dimantions of image3
    imageView3.setFitHeight(200);
    imageView3.setFitWidth(200);
    imageView3.setTranslateX(0);
    imageView3.setTranslateY(-200);
    //the appearance of 10 labels and there style  
    for(int i=0 ; i <10 ; i++){
        l[i].setStyle("-fx-font-size: 25; -fx-font-weight: Cambria; -fx-text-fill: white ;");
        l[i].setTranslateX(0);}
        
        l1.setStyle("-fx-font-size: 25; -fx-font-weight: Cambria; -fx-text-fill: yellow ;");
        l1.setTranslateY(-75);
        l2.setTranslateY(-45);
        l3.setTranslateY(-15);
        l4.setTranslateY(15);
        l5.setTranslateY(45);
        l6.setTranslateY(75);
        l7.setTranslateY(105);
        l8.setTranslateY(135);
        l9.setTranslateY(165);
        l10.setTranslateY(200);
    //the defualt color of scene4 is black
    root4.setStyle("-fx-background-color: black");
    Scene scene4 = new Scene(root4, 481, 648);
    return scene4; 
}

public static void main(String[] args){Application.launch(args);}   
}
