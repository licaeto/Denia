package es.upv.address;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import es.upv.address.controller.Controller;
import es.upv.address.model.Coordinate;
import es.upv.address.model.ImageAdaptator;
import es.upv.address.model.Location;
import es.upv.address.model.Mapa;
import es.upv.address.model.Wrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	private AnchorPane rootLayout;
	private Mapa map;
	Controller controller;
	
	/**
	 * Carga las localizaciones en la tabla hash a partir de un fichero XML
	 * @param file el fichero XML a partir del cual se obtiene la informacion
	 */
	public void loadLocationDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(Wrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			ImageAdaptator adaptor = new ImageAdaptator();
			
			Wrapper wrapper = (Wrapper) um.unmarshal(file);
			map = new Mapa(wrapper.getName(),wrapper.getLocations(),adaptor.unmarshal(wrapper.getImage()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert al = new Alert(AlertType.ERROR);
			al.setTitle("Error"); al.setHeaderText(null);
			al.setContentText("Could not load data from file:\n"+ file.getPath());
			al.showAndWait();
		}
	}
	
	/**
	 * Guarda las localizaciones de la tabla hash en un archivo XML
	 * @param file fichero XML que guarda las localizaciones
	 */
	public void saveLocationDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(Wrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			ImageAdaptator adaptor = new ImageAdaptator();
			
			Wrapper wrapper = new Wrapper();
			wrapper.setName(map.getName());
			wrapper.setLocations(map.getLocations());
			wrapper.setImage(adaptor.marshal(map.getImage()));
			
			m.marshal(wrapper, file);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR); 
			alert.setTitle("Error"); alert.setHeaderText(null); 
			alert.setContentText("Could not save data to file:\n" +file.getPath()); 
			alert.showAndWait();
		} 
	}

	/**
	 * Inicializa la ventana principal
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DeniaOverview.fxml"));
			rootLayout = (AnchorPane) loader.load();
			Controller controller = loader.getController();
			controller.setApp(this);
			
			Scene scene = new Scene(rootLayout);
			String fontSheet = fileToStylesheetString( new File ("css/denia.css") );
			
			if ( fontSheet == null ) {
				//Do Whatever you want with logging/errors/etc.
			} else {
				scene.getStylesheets().add( fontSheet );
			}
			
			//scene.getStylesheets().add("css/denia.css");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String fileToStylesheetString ( File stylesheetFile ) {
	    try {
	        return stylesheetFile.toURI().toURL().toString();
	    } catch ( MalformedURLException e ) {
	        return null;
	    }
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("Denia");
		//createMap();
		//saveAs();
		openAs();
		
		initRootLayout();
	}
	
	public void createMap() {
		ArrayList<Location> locations = new ArrayList<Location>();
		
		locations.add(new Location(new Coordinate(701,68),"Torre de la Senieta S.XVIII"));
		locations.add(new Location(new Coordinate(645,234),"Palau S.XIV - S.XVII"));
		locations.add(new Location(new Coordinate(535,260),"Portal del Baluard S.XI"));
		locations.add(new Location(new Coordinate(546,411),"Torre del consell C.1500"));
		locations.add(new Location(new Coordinate(398,334),"Torre roja S.XVI"));
		locations.add(new Location(new Coordinate(448,414),"Torre del cos de guardia S.XIII - S.XVII"));
		locations.add(new Location(new Coordinate(437,261),"Torre de la polvora S.XV"));
		locations.add(new Location(new Coordinate(790,153),"Torre del galliner S.XVI"));
		locations.add(new Location(new Coordinate(483,298),"Punta del diamant S.XVII"));
		
		Image image = new Image(new File("data/llunatics.jpg").toURI().toString());
		
		map = new Mapa("Llunatics",locations,image);
	}

	/**
	 * Muestra la ventana de dialogo y gestiona el guardado de un mapa.
	 */
	private void saveAs() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new  FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(primaryStage);
		if (file != null) {
			if (!file.getPath().endsWith(".xml"))
				file = new File(file.getPath() + ".xml"); 
			this.saveLocationDataToFile(file);

		}	
	}
	
	/**
	 * Muestra la ventana de dialogo y gestiona el cargado de un mapa.
	 */
	private void openAs() {
		/**
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new  FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"); 
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(this.getPrimaryStage()); 
		**/
		File file = new File("data/castell.xml");
		if (file != null)
			this.loadLocationDataFromFile(file);
		
	}
	
	public Mapa getMap() {
		return map;
	}

	public void setMap(Mapa map) {
		this.map = map;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
