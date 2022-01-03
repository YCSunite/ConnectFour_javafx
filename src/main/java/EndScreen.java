import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndScreen implements EventHandler {

    VBox EndScenePlayer1;
    VBox EndScenePlayer2;
    Stage endStage;
    Button newGame = new Button();


    public EndScreen(Stage endStage) {
        this.endStage = endStage;
        this.endScreen();
        newGame.setOnAction(this);



    }
    private void endScreen(){
        Button newGame = new Button("Exit");
        newGame.setMinHeight(20.0);
        newGame.setMinWidth(40.0);
        newGame.setStyle("-fx-font: normal bold 14px 'serif'");



        EndScenePlayer1 = new VBox();
        EndScenePlayer1.setAlignment(Pos.CENTER);
        EndScenePlayer1.getChildren().addAll(this.label1(),newGame);

        EndScenePlayer2 = new VBox();
        EndScenePlayer2.setAlignment(Pos.CENTER);
        EndScenePlayer2.getChildren().addAll(this.label2(),newGame);


    }
    private Label label1(){
        Label player1Win  = new Label("Player 1 has won! \n");
        player1Win.setStyle("-fx-font: normal bold 24px 'serif' ");
        player1Win.setAlignment(Pos.CENTER);
        return player1Win;

    }
    private Label label2(){
        Label player2Win = new Label("Player 2 has won! \n");
        player2Win.setStyle("-fx-font: normal bold 14px 'serif' ");
        player2Win.setAlignment(Pos.BASELINE_CENTER);

        return player2Win;
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == newGame){
            System.out.println("pressed");
            endStage.close();
//            primaryStage.setScene(new Scene(GameScene, 700, 700));
        }
    }
}
