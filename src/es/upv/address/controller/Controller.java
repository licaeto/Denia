package es.upv.address.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import es.upv.address.MainApp;
import es.upv.address.model.Coordinate;
import es.upv.address.model.Location;
import es.upv.address.model.Mapa;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Controller implements Initializable {
	@FXML
	private ImageView image;
	@FXML
	private VBox buttons;
	@FXML
	private Circle bulb;
	@FXML
	private Text instructions;
	@FXML
	private Button reset;
	
	private MainApp app;
	private Coordinate c;
	private Location l;
	private ArrayList<Location> locations;
	private ObservableList<Node> buttonList;
	private Mapa map;

	public Controller() {}

	public MainApp getApp() {
		return app;
	}

	public void setApp(MainApp app) {
		this.app = app;
		
		map = app.getMap();
		locations = map.getLocations();
		image.setImage(map.getImage());
		
		crearBotones();
	}

	private String multiLine(String text) {
		String res = text;
		if(text.length() >= 20) {
			res=text.substring(0, 20)+"\n"+text.substring(20,text.length());
		}
		return res;
	}
	
	private void crearBotones() {
		buttons.setAlignment(Pos.CENTER_LEFT);
		buttons.setSpacing(20.0);
		buttonList = buttons.getChildren();
		buttonList.clear();
		
		for(int i = 0; i < locations.size(); i++) {
			Button b = new Button(multiLine(locations.get(i).getName()));
			b.getStyleClass().add("boton");
			VBox.setMargin(b, new Insets(1,15,1,1));
			b.setWrapText(true);
			b.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Button b = (Button) e.getSource();
					String text = b.getText().replaceAll("\n", "");
					if(l.getName().equals(text)) {
						bulb.setFill(javafx.scene.paint.Color.GREEN);
						instructions.setText("¡CORRECTE! ¡Has encertat!");
						String file = "data/success.mp3";
						playSound(file);
					}
					else {
						bulb.setFill(javafx.scene.paint.Color.RED);
						instructions.setText("¡ERROR! Has fallat...");
						String file = "data/error.mp3";
						playSound(file);
					}
					buttons.setDisable(true);
					reset.setDisable(false);
				}
			});
			buttonList.add(b);
		}
	}
	
	private void playSound(String file) {
		Media sound = new Media(new File(file).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}
	
	@FXML
	private void switchMap(ActionEvent e) {
		Button aux = (Button) e.getSource();
		File file=null;
		switch(aux.getId()) {
		case "castell": 
			file = new File("data/castell.xml");
			break;
		case "marina":
			file = new File("data/marina.xml");
			break;
		case "llunatics":
			file = new File("data/llunatics.xml");
			break;
		}
		if (file != null)
			app.loadLocationDataFromFile(file);
		app.initRootLayout();
	}
	
	@FXML
	private void reiniciar(ActionEvent e) {
		app.initRootLayout();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttons.setDisable(true);
		reset.setDisable(true);
		
		image.setOnMouseClicked(e -> {
			
            c = new Coordinate(new Double(e.getX()).intValue(), new Double(e.getY()).intValue());
            Location aux = new Location(c,"aux");
            if(locations.contains(aux)) {
            	l = locations.get(locations.indexOf(aux));
            	instructions.setText("Fes click al botó que cregues correcte");
            	image.setDisable(true);
            	buttons.setDisable(false);
            }
            else 
            	instructions.setText("Assegura't de fer click a un punt negre");
        });
	}
}
