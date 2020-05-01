package image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyImage {
    static String NW = "resources/ico/NW.png/";
    static String F = "resources/ico/F.png/";

    public MyImage() {
		super();
	}

	public static ImageView getNW() {
		return new ImageView(new Image(NW));
	}

	public static ImageView getF() {
		return new ImageView(new Image(F));
	}
}
