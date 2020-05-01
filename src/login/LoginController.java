package login;

import alarm.AlarmUI;
import animatefx.animation.Pulse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rmi.FASensor;
import rmi.FASensorClient;
import user.User;

public class LoginController {
	@FXML
	public Label status;
	public TextField email;
	public PasswordField pass;
	public Button loginBtn;

	@FXML
	public void Login(ActionEvent event) throws Exception {
		String e = email.getText();
        String p = pass.getText();

        try {
        	FASensor fas = (FASensor) FASensorClient.getInstance().setNewClient();
	        boolean isValid = fas.authenticateUser(e, p);
	        System.out.println(isValid);

			if(isValid) {
				User.getInstance().setEmail(e);
				User.getInstance().setPassword(p);

				status.setText("Login Successful");

				Stage stage = (Stage) loginBtn.getScene().getWindow();
	            stage.close();

	            AlarmUI alarmUIInstance = AlarmUI.getInstance();
	            Stage alarmStage = new Stage();
	            alarmUIInstance.display(alarmStage);
			}
			else
				status.setText("Login Failed");

			// animate
			new Pulse(status).play();
        }
        catch(Exception e1) {
        	System.out.println(e1);
        }
	}

}
