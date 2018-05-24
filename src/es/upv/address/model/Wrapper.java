package es.upv.address.model;

import java.util.ArrayList;

import java.awt.Image;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class Wrapper {
	private String name;
	private ArrayList<Location> locations;
	private Image image;
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "location")
	public ArrayList<Location> getLocations() {
		return locations;
	}
	
	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	
	@XmlElement(name = "image")
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}
