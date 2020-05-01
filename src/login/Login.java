package login;

import java.rmi.RemoteException;

import animatefx.animation.Pulse;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rmi.FASensorServer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Login extends Application {
	@Override
	public void start(Stage loginStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));

	        Scene scene = new Scene(root);

	        loginStage.setTitle("RedCare Login");
	        loginStage.resizableProperty().setValue(Boolean.TRUE);
	        loginStage.initStyle(StageStyle.DECORATED);
	        loginStage.setScene(scene);
	        loginStage.show();

	        //animate the stage
	        new Pulse(root).play();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws RemoteException {
		FASensorServer.getInstance().startServer();
		launch(args);
	}
}
