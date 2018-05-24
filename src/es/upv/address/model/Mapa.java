package es.upv.address.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.scene.image.Image;

public class Mapa {
	
	private String name;
	private ArrayList<Location> locations;
	private Image image;
	
	public Mapa(String name, ArrayList<Location> locations, Image image) {
		super();
		this.name = name;
		this.locations = locations;
		this.image = image;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Location> getLocations() {
		return locations;
	}
	
	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	
	@XmlJavaTypeAdapter(ImageAdaptator.class) 
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
