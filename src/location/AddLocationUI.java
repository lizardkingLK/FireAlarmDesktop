package location;

import java.io.IOException;

import animatefx.animation.Pulse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddLocationUI {
	private static AddLocationUI addLocationUIInstance;

	private AddLocationUI() {}

	public static AddLocationUI getInstance() {
		if(addLocationUIInstance == null) {
			synchronized (AddLocationUI.class) {
				addLocationUIInstance = new AddLocationUI();
			}
		}

		return addLocationUIInstance;
	}

	public void display(Stage addlocationstage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddLocationView.fxml"));

		Scene scene = new Scene(root);

		addlocationstage.setTitle("Add Location");
		addlocationstage.setScene(scene);
		addlocationstage.show();

        new Pulse(root).play();
	}

}
