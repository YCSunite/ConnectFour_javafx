import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.ArrayList;


public class GameButton extends Button {
    EndScreen endScreen;
    Stage endStage;
    GridPane BoardGrid;
    VBox gameScene;
    int[][] arr = new int[6][7];
    int player1 = 1;
    int player2 = 2;
    int player = player1;
    Label moveInfo = new Label();






    public ArrayList<String> allMoves = new ArrayList<>();

    public GameButton(VBox gameScene,Stage stage) {
        createBoard();
        this.gameScene = gameScene;
        this.gameScene.getChildren().addAll(BoardGrid,moveInfo);


        this.endStage = stage;
        endStage = new Stage();
        endScreen = new EndScreen(endStage);



    }

    public void createBoard() {
        BoardGrid = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Button button = new Button();
                button.setPadding(new Insets(20));
                button.setId(String.valueOf(i) + "," + String.valueOf(j));
                button.setMinHeight(70);
                button.setMinWidth(70);

                BoardGrid.add(button, j, i);
                BoardGrid.setPadding(new Insets(10));
                BoardGrid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
                BoardGrid.setVgap(10);

                button.setOnAction(event -> {
                    int row = Integer.parseInt(String.valueOf(button.getId().charAt(0)));
                    int col = Integer.parseInt(String.valueOf(button.getId().charAt(2)));
                    String moveToAdd = String.valueOf(row) + String.valueOf(col);

                    if (player == player1 && (row == 5 || arr[row + 1][col] != 0)) {
                        player = player2;
                        arr[row][col] = player;
                        button.setStyle("-fx-background-color: #ff0000; ");
                        button.setDisable(true);
                        allMoves.add(moveToAdd);
                    } else if (player == player2 && (row == 5 || arr[row + 1][col] != 0)) {
                        button.setStyle("-fx-background-color: #ffff00; ");
                        button.setDisable(true);
                        player = player1;
                        arr[row][col] = player;
                        allMoves.add(moveToAdd);

                    }
                    else{
                        allMoves.add("invalid");
                    }
                    int winner = checkWinner();

                    System.out.println("winner is " + winner);

                        if(player1 == checkWinner()){
                            endStage.setScene(new Scene(endScreen.EndScenePlayer1,700,700));
                            endStage.show();
                        }
                        else if(player2 == checkWinner()){
                            endStage.setScene(new Scene(endScreen.EndScenePlayer2,700,700));
                            endStage.show();
                        }

                        showMove(allMoves);



                    //displaying the board in the terminal
                    for (int x = 0; x < 6; x++) {
                        for (int y = 0; y < 7; y++) {
                            System.out.print(arr[x][y]);

                        }
                        System.out.println("");
                    }

                });
            }
        }
        BoardGrid.setAlignment(Pos.BASELINE_CENTER);
        BoardGrid.setPadding(new Insets(50, 10, 10, 10));

    }


    int checkWinner() {
        int maxx = 6;
        int maxy = 7;
        int[][] directions = {{1, 0}, {1, -1}, {1, 1}, {0, 1}};
        for (int[] d : directions) {
            int dx = d[0];
            int dy = d[1];
            for (int x = 0; x < maxx; x++) {
                for (int y = 0; y < maxy; y++) {
                    int lastx = x + 3 * dx;
                    int lasty = y + 3 * dy;
                    if (0 <= lastx && lastx < maxx && 0 <= lasty && lasty < maxy) {
                        int w = arr[x][y];
                        if (w != 0 && w == arr[x + dx][y + dy]
                                && w == arr[x + 2 * dx][y + 2 * dy]
                                && w == arr[lastx][lasty]) {
                            return w;
                        }
                    }
                }
            }
        }
        return 0; // no winner
    }
    private void showMove(ArrayList allMoves) {
        String textToShow = "The moves are:\n ";
        for (Object move: allMoves){
            String theMove = (String) move;
            String toAdd = "";
            if (theMove == "invalid"){
                toAdd = "invalid move, play again! \n";
            }
            else{
                toAdd = "Current player has moved to "+theMove.substring(0,1) + ","+ theMove.substring(1,2) + "\n";
            }

            textToShow+=toAdd;
        }
        moveInfo.setText(textToShow);

    }

    public void restart(){
        allMoves.clear();
    }

}