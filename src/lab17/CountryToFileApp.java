package lab17;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CountryToFileApp {

	private static Path filePath = Paths.get("countries.txt");
	private static Path binaryFilePath = Paths.get("countries.dat");

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		while (true) {
			System.out.println("Welcome to the Countries Maintenance Application!");
			System.out.println("1 - See the list of countries\n" 
							 + "2 - Add a country\n" 
							 + "3 - Sort countries alphabetically\n"
							 + "4 - Sort countries by population (descending)\n"
					         + "5 - Exit\n");
			int option = Validator.getIntInRange(scnr, "Enter a menu number: ", 1, 5);
			if (option == 5) {
				break;
			} else if (option == 1) {
				List<Country> countries = readFile();
				for (Country country : countries) {
					System.out.println(country);
				}
			} else if (option == 2) {
				Country country = getThingFromUser(scnr);
				System.out.println("Adding " + country);
				appendLineToFile(country);
			} else if (option == 3) {
				List<Country> countries = readFile();
				ArrayList<Country> countryList = new ArrayList<Country>();
				for (Country country : countries) {
					countryList.add(country);
				}
				Collections.sort(countryList, Country.countryNameComparator);
				for (Country country : countryList) {
					System.out.println(country);
				}
			} else if (option == 4) {
				List<Country> countries = readFile();
				ArrayList<Country> countryList = new ArrayList<Country>();
				for (Country country : countries) {
					countryList.add(country);
				}
				Collections.sort(countryList, Country.countryPopComparator);
				for (Country country : countryList) {
					System.out.println(country);
				}
			} else {
				System.out.println("Invalid option.");
			}
		}
		
		textToFileBinary();
		System.out.println("Thanks for using the Country List App!");
		scnr.close();

	}

	private static Country getThingFromUser(Scanner scnr) {
		String name = Validator.getString(scnr, "Enter country: ");
		int population = Validator.getInt(scnr, "Enter population: ");
		return new Country(name, population);
	}

	public static List<Country> readFile() {
		try {
			List<String> lines = Files.readAllLines(filePath);
			List<Country> countries = new ArrayList<>();

			for (String line : lines) {
				String[] parts = line.split(",");
				String name = parts[0];
				int population = Integer.parseInt(parts[1]);

				countries.add(new Country(name, population));
			}

			return countries;

		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new ArrayList<>();
		}
	}

	public static void appendLineToFile(Country country) {
		String line = country.getName() + "," + country.getPopulation();
		List<String> lines = Collections.singletonList(line);
		try {
			Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}
	public static void textToFileBinary() {		
		File file = new File("countries.dat");
		try {
			byte[] allbytes = Files.readAllBytes(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(allbytes);
			fos.close();
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}
	

}
