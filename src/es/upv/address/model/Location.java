package es.upv.address.model;

public class Location {
	private Coordinate coord;
	private String name;
	
	public Location() {}
	
	public Location(Coordinate coord, String name) {
		super();
		this.coord = coord;
		this.name = name;
	}

	public Coordinate getCoord() {
		return coord;
	}

	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Location [coord=" + coord.toString() + ", name=" + name + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (coord == null) {
			if (other.coord != null)
				return false;
		} else if (!coord.equals(other.coord))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		return true;
	}
	
	
	
}
