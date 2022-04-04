package pokemontypetester;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TypeTester {
	public static void main(String[] args) {
		genMonResToUserInput();
	}

	public static void genMonResToUserInput() {
		System.out.println(
				"""
						Welcome to Pokemon Type Tester!

						This program will return a list of all Pokemon type combinations which resist a set of types.
						Enter the name of a Pokemon type to add it to the list of types to check and enter "done" to finalize your list.
						""");

		String input = "";
		List<PokemonTypes> types = new ArrayList<>();
		try (Scanner kb = new Scanner(System.in)) {
			while (!input.equalsIgnoreCase("done")) {
				System.out.print("Enter the name of a Pokemon type: ");
				input = kb.nextLine();

				if (!input.equalsIgnoreCase("done"))
					try {
						if (types.contains(PokemonTypes.fromString(input)))
							continue;
						types.add(PokemonTypes.fromString(input));
					} catch (IllegalArgumentException e) {
						System.out.println(e.getLocalizedMessage());
					}
				else if (types.size() < 1) {
					System.out.println(
							"Don't know why you used this program if you weren't going to test any types... kinda weird..");
					System.exit(0);
				} else if (types.size() < 2)
					types.add(types.get(0));
			}
		}

		System.out.println(String.format("\nThe following type combination(s) resist %s:",
				types.get(0).compareTo(types.get(1)) == 0 ? types.get(0) : types.toString()));
		Pokemon.genMonResToAll(types.toArray(new PokemonTypes[types.size()])).ifPresentOrElse(
				x -> x.forEach(System.out::println), () -> System.out.println("No such type combination exists."));
	}
}