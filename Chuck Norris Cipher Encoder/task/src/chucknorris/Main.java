package chucknorris;

import java.util.Locale;
import java.util.Scanner;

public final class Main {

	public static void main(final String[] args) {
		final Scanner scanner = new Scanner(System.in);
		do {
			final String operation = ask(scanner, "Please input operation (encode/decode/exit):");
			final String command = operation.toLowerCase(Locale.ROOT);
			if (command.equals("encode")) {
				final String input = ask(scanner, "Input string:");
				System.out.printf("Encoded string:%n%s%n", ChuckNorrisCipher.encode(input));
				continue;
			}
			if (command.equals("decode")) {
				final String encodedInput = ask(scanner, "Encoded string:");
				try {
					System.out.printf("Decoded string:%n%s%n", ChuckNorrisCipher.decode(encodedInput));
				} catch (final InvalidEncodedStringException e) {
					System.out.println(e.getMessage());
				}
				continue;
			}
			if (command.equals("exit")) {
				System.out.println("Bye!");
				break;
			}
			System.out.printf("There is no '%s' operation%n", operation);
		} while (true);
	}

	private static String ask(final Scanner scanner, final String question) {
		System.out.println(question);
		return scanner.nextLine();
	}

}