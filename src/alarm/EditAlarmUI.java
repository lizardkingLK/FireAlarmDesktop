package alarm;

import java.io.IOException;

import animatefx.animation.Pulse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditAlarmUI {
	private static EditAlarmUI editAlarmInstance;

	private EditAlarmUI() {}

	public static EditAlarmUI getInstance() {
		if(editAlarmInstance == null) {
			synchronized (EditAlarmUI.class) {
				editAlarmInstance = new EditAlarmUI();
			}
		}

		return editAlarmInstance;
	}

	public void display(Stage editAlarmStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("EditAlarmView.fxml"));

		Scene scene = new Scene(root);

		editAlarmStage.setTitle("Edit Alarm");
        editAlarmStage.setScene(scene);
        editAlarmStage.show();

        editAlarmStage.show();

		// animate
		new Pulse(root).play();
	}


}
