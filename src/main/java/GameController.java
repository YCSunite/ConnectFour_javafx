import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameController implements EventHandler {
    GameButton gameButton;
    Stage primaryStage;
    VBox homeScreen = new VBox();
    Button startGameBtn = new Button();
    VBox GameScene = new VBox();
    Menu GamePlay;
    Menu Themes;
    Menu Options;



    //constructor for the GameButton class
    public GameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.startScreen();
        startGameBtn.setOnAction(this);
        gameButton = new GameButton(GameScene,primaryStage);

    }

    private void startScreen() {
        //home screen where the start button is located
        homeScreen = new VBox();
        homeScreen.setStyle("-fx-background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDwagvuTjk2zdA9jDLWNrFWAJRiECV75iEaA&usqp=CAU')");
        startGameBtn.setText("Start Game");
        startGameBtn.setFont(new Font("Arial", 24));
        startGameBtn.setStyle("-fx-background-color: #A52A2A");
        homeScreen.getChildren().add(startGameBtn);
        homeScreen.setAlignment(Pos.CENTER);

        //creating a scene for the main game play screen
        GameScene = new VBox();
        GameScene.getChildren().add(this.createMenuBar());
        GameScene.setStyle("-fx-font: normal bold 14px 'serif';-fx-background-color: #E9967A ");

        //setting the primary stage
        primaryStage.setTitle("Welcome to Connect Four!");
        primaryStage.setScene(new Scene(homeScreen, 700, 700));
        primaryStage.show();
    }



    private MenuBar createMenuBar() {

        Themes = new Menu("Themes");
        MenuItem theme1 = new MenuItem("Theme 1");
        theme1.setId("theme1");
        theme1.setOnAction(this);
        MenuItem theme2 = new MenuItem("Theme 2");
        theme2.setId("theme2");
        theme2.setOnAction(this);
        Themes.getItems().addAll(theme1, theme2);

        GamePlay = new Menu("Game Play");
        MenuItem reverseMoves = new MenuItem("Reverse Moves");
        reverseMoves.setId("revMoves");
        reverseMoves.setOnAction(this);
        GamePlay.getItems().addAll(reverseMoves);

        Options = new Menu("Options");
        MenuItem GameInstruction = new MenuItem("How to Play");
        GameInstruction.setId("GameInfo");
        GameInstruction.setOnAction(this);
        MenuItem newGame = new MenuItem("New Game");
        newGame.setId("new");
        newGame.setOnAction(this);
        MenuItem Exit = new MenuItem("Exit Game");
        Exit.setId("Exit");
        Exit.setOnAction(this);
        Options.getItems().addAll(GameInstruction, newGame, Exit);

        MenuBar GameSceneMenuBar = new MenuBar();
        GameSceneMenuBar.getMenus().addAll(GamePlay, Themes, Options);
        return GameSceneMenuBar;
    }

    public void displayGameInfo(String message, String menuInfo) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(message);
        alert.setContentText(menuInfo);
        alert.show();
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == startGameBtn) {
            primaryStage.setScene(new Scene(GameScene, 700, 700));
        }
        else if (((MenuItem) event.getSource()).getId() == "revMoves") {
            String lastMove = gameButton.allMoves.get(gameButton.allMoves.size() - 1);
            // TODO: remove the last move
            if (lastMove == "invalid"){return;}
            gameButton.allMoves.remove(gameButton.allMoves.size() - 1);
            int row = Integer.valueOf(lastMove.substring(0, 1));
            int col = Integer.valueOf(lastMove.substring(1));

            // undo the move from arr
            for (int x = 0; x < 6; x++) {
                for (int y = 0; y < 7; y++) {
                    if (x == row && y == col) {
                        gameButton.arr[x][y] = 0;
                    }
                }
            }

            // undo move in board
            ObservableList<Node> childrens = gameButton.BoardGrid.getChildren();

            for (Node node : childrens) {
                int foundRow = gameButton.BoardGrid.getRowIndex(node);
                int foundCol = gameButton.BoardGrid.getColumnIndex(node);
                if (foundRow == row && foundCol == col) {
                    node.setDisable(false);
                    node.setStyle(null);
                    break;
                }
            }

            // reverse players
            if (gameButton.player == gameButton.player1) {
                gameButton.player = gameButton.player2;
            } else {
                gameButton.player = gameButton.player1;
            }
        }
        else if (((MenuItem) event.getSource()).getId() == "new") {

//            primaryStage.setScene(new Scene(GameScene, 700, 700));
            gameButton.BoardGrid.getChildren().clear();
            gameButton = null;
            gameButton = new GameButton(gameButton.gameScene, primaryStage);
//            gameButton.restart();

        }
        else if (((MenuItem) event.getSource()).getId() == "GameInfo") {
            displayGameInfo("How to Play Connect 4:",util.HowToPlay);

        }
        else if(((MenuItem) event.getSource()).getId() == "theme1"){
            GameScene.setStyle("-fx-font: normal bold 14px 'serif';-fx-background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTo1Udpqp4z9Q2ebAm0yrBqRI2_qy01ReLVA&usqp=CAU') ");
            gameButton.moveInfo.setStyle("-fx-text-fill: white");
        }
        else if(((MenuItem) event.getSource()).getId() == "theme2"){
            GameScene.setStyle("-fx-font: normal bold 14px 'serif';-fx-background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3b40vaEp9DugOTAlOerhc1HTRhK1GzSz0AA&usqp=CAU') ");
            gameButton.moveInfo.setStyle("-fx-text-fill: white");
        }

        else {
            primaryStage.close();
        }
    }


}
