package alarm;

import java.io.IOException;

import animatefx.animation.Pulse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlarmUI {
	private static AlarmUI alarmUIInstance;

	private AlarmUI() {}

	public static AlarmUI getInstance() {
		if(alarmUIInstance == null) {
			synchronized (AlarmUI.class) {
				alarmUIInstance = new AlarmUI();
			}
		}

		return alarmUIInstance;
	}

	public void display(Stage alarmStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AlarmView.fxml"));

        Scene scene = new Scene(root);

        alarmStage.setTitle("Welcome");
        alarmStage.setScene(scene);
        alarmStage.show();

        new Pulse(root).play();
	}


}
