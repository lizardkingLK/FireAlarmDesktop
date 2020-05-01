package location;

import java.io.IOException;

import animatefx.animation.Pulse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rmi.FASensorClient;

public class AddLocationViewController {
	@FXML
    public TextField fxFloorNo;
    public TextField fxRoomNo;
    public Button fxAddButton;
    public Label fxStatus;

    @FXML
    public void addLocation(ActionEvent event) throws IOException {
    	String f = fxFloorNo.getText();
    	String r = fxRoomNo.getText();
    	String lid = (f+r);

    	if(f.length() == 0 || r.length() == 0)
			fxStatus.setText("Set values first");
    	else {
	    	boolean success = FASensorClient.addLocation(f,r,lid);

	    	if(success)
	    		fxStatus.setText("Location added");
	    	else
	    		fxStatus.setText("Add location failed. Check your inputs.");
    	}

    	// animate
    	new Pulse(fxStatus).play();
    }
}
