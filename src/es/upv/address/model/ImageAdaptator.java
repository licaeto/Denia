package es.upv.address.model;

import java.awt.image.BufferedImage;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javafx.embed.swing.SwingFXUtils;
import java.awt.Image;

public class ImageAdaptator extends XmlAdapter<Image, javafx.scene.image.Image>{

	public ImageAdaptator() {}
	
	@Override
	public javafx.scene.image.Image unmarshal(Image v) throws Exception {
		return SwingFXUtils.toFXImage((BufferedImage) v, null);
	}
	
	@Override
	public BufferedImage marshal(javafx.scene.image.Image v) throws Exception{
		return (BufferedImage) SwingFXUtils.fromFXImage(v, null);
	}
	
}
