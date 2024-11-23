package ds212;

import java.io.File;
import java.util.Scanner;

public class Read {
	public static void Load(String fileName) {
		String line = null;
		try {
			File file = new File(fileName);
			Scanner sc = new Scanner(file);

			// skip header
			sc.nextLine();

			// read rest of the file
			while (sc.hasNextLine()) {
				line = sc.nextLine();

				// check for empty lines
				if (line.trim().length() < 3) {
					System.out.println("Skipping this line (empty line)" + line);
					break;
				}

				System.out.println(line);

				String str = line.substring(0, line.indexOf(","));
				int id = Integer.parseInt(str.trim());

				String text = line.substring(line.indexOf(",") + 1).trim();

			}

		} catch (Exception e) {
			System.out.println("An error occurred");
		}
	}

	public static void main(String[] args) {
		Load("dataset.csv");
		Load("stop.txt");

	}
}