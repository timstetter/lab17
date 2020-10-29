package lab17;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		while (true) {
			System.out.println("Welcome to the Countries Maintenance Application!");
			System.out.println("1 - See the list of countries\n" 
							 + "2 - Add a country\n" 
							 + "3 - Sort countries alphabetically\n"
							 + "4 - Sort countries by population (descending)\n"
							 + "5 - Remove a country from the list\n"
					         + "6 - Exit\n");
			int option = Validator.getIntInRange(scnr, "Enter a menu number: ", 1, 6);
			if (option == 6) {
				break;
			} else if (option == 1) {
				List<Country> countries = readFile();
				for (Country country : countries) {
					System.out.println(country);
				}
				System.out.println();
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
				System.out.println();
			} else if (option == 4) {
				List<Country> countries = readFile();
				ArrayList<Country> countryList = new ArrayList<Country>();
				for (Country country : countries) {
					countryList.add(country);
				}
				System.out.println();
				Collections.sort(countryList, Country.countryPopComparator);
				for (Country country : countryList) {
					System.out.println(country);
				}
				System.out.println();
			} else if (option == 5) {
				String userInput = Validator.getString(scnr, "Please enter country to be removed: ");
				System.out.println("Deleting: " + userInput + "\n");
				removeLineFromFile(userInput);
				List<Country> countries = readFile();
				for (Country country : countries) {
					System.out.println(country);
				}
				System.out.println();
			} else {
				System.out.println("Invalid option.");
			}
		}
		
		
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
	
	public static void removeLineFromFile(String country) {
		File inputFile = new File("countries.txt");
		File tempFile = new File("myTempFile.txt");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String lineToRemove = country;
		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.startsWith(lineToRemove)) continue;
		    writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close(); 
		reader.close(); 
		inputFile.delete();
		tempFile.renameTo(inputFile);
		} catch (IOException e) {
			System.out.println("Couldn't remove country");
		}
	}
}
