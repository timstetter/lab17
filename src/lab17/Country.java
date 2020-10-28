package lab17;

import java.text.NumberFormat;
import java.util.Comparator;

public class Country {

	private String name;
	private int population;

	public Country(String name, int population) {
		this.name = name;
		this.population = population;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public String getFormattedPopulation() {
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		return myFormat.format(population);
	}

	public static Comparator<Country> countryNameComparator = new Comparator<Country>() {

		public int compare(Country c1, Country c2) {
			String CountryName1 = c1.getName().toUpperCase();
			String CountryName2 = c2.getName().toUpperCase();

			return CountryName1.compareTo(CountryName2);

		}
	};

	public static Comparator<Country> countryPopComparator = new Comparator<Country>() {

		public int compare(Country c1, Country c2) {
			int CountryPop1 = c1.getPopulation();
			int CountryPop2 = c2.getPopulation();

			return CountryPop1 - CountryPop2;

		}
	};

	public void setPopulation(int population) {
		this.population = population;
	}

	@Override
	public String toString() {
		return name + " (pop " + getFormattedPopulation() + ")";

	}

}
