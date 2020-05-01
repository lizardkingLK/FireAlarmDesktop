package alarm;

import java.io.IOException;

import animatefx.animation.Pulse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddAlarmUI {
	private static AddAlarmUI addAlarmUIInstance;

	private AddAlarmUI() {}

	public static AddAlarmUI getInstance() {
		if(addAlarmUIInstance == null) {
			synchronized (AddAlarmUI.class) {
				addAlarmUIInstance = new AddAlarmUI();
			}
		}

		return addAlarmUIInstance;
	}

	public void display(Stage addAlarmStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddAlarmView.fxml"));

        Scene scene = new Scene(root);

        addAlarmStage.setTitle("Add Alarm");
        addAlarmStage.setScene(scene);
        addAlarmStage.show();

        new Pulse(root).play();
	}

}
