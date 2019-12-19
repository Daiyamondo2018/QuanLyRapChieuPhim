package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.MainController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import plugin.MyWindows;
import usercontrol.control.AddEditInfo;
import usercontrol.control.MovieCard;
import usercontrol.control.MovieScheduleCard;

public class ScheduleController implements Initializable {
	private static final String[] contents = { "Giá vé", "Số ghế", "Thời gian" };
    @FXML private Line timeLine;
    @FXML private HBox schedulePane;

	private AddEditInfo w2 = new AddEditInfo("Sửa lịch chiếu phim");
    
    @FXML
    void AddScheduleCard(ActionEvent event) {
    	MyWindows w = new MyWindows("../view/AddMovieToSchedule.fxml");
    	w.Resize(940, 600);
    	w.Show();
    	w2.show();
    	MovieScheduleCard card = new MovieScheduleCard();
    	try {
    		card.setInfo((MovieCard) w.getUserData());
    		schedulePane.getChildren().add(card);
    		card.image.setOnMouseClicked(e->{
				if (e.getButton() == MouseButton.PRIMARY) {
					MyWindows bookTicket = new MyWindows("../view/BookTicket.fxml", 50);
    				bookTicket.Show();
    			}
    		});
    		MenuItem edit = new MenuItem("Sửa");
    		MenuItem delete = new MenuItem("Xóa");
    		edit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					MyWindows w = new MyWindows("../view/AddMovieToSchedule.fxml");
			    	w.Resize(940, 600);
			    	w.Show();
			    	w2.show();
				}
			});
    		delete.setOnAction(E->{
    			schedulePane.getChildren().remove(card);
    		});
    		card.contextMenu.getItems().add(edit);
    		card.contextMenu.getItems().add(delete);
		} catch (NullPointerException e) {}    		
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		timeLine.endXProperty().bind(Bindings.max(schedulePane.widthProperty(),MainController.mainPage.widthProperty().add(-105)));
		timeLine.translateYProperty().bind(schedulePane.heightProperty().add(-40));
		w2.AddAll(contents);
	}
}
