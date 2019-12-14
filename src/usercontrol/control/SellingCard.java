package usercontrol.control;

import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class SellingCard extends BorderPane {
    @FXML private ImageView image;
    @FXML private Text name;
    @FXML private Text description;
    @FXML private Label _cost;
    
    private IntegerProperty cost = new SimpleIntegerProperty(0);

    public SellingCard() {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SellingCard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		_cost.textProperty().bind(cost.asString());
    }
    
    public ObjectProperty<Image> imageProperty() {
    	return image.imageProperty();
    }
    
    public StringProperty nameProperty() {
    	return name.textProperty();
    }
    
    public StringProperty descriptionProperty() {
    	return description.textProperty();
    }
    
    public IntegerProperty costProperty() {
    	return cost;
    }
}
