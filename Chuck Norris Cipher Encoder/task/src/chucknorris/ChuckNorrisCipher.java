package chucknorris;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ChuckNorrisCipher {

	private static final Pattern NORRIS_SEGMENT = Pattern.compile("\\b((0{1,2}) (0{1,8}))\\b");
	private static final Pattern ASCII_BLOCK = Pattern.compile("(?<=\\G.{7})");
	private static final String[] ENCODED_BITS = {" 00 0", " 0 0"};
	private static final String ASCII_BUFFER = "0000000";

	static String encode(final String input) {
		final String binary = input.chars()
		                           .mapToObj(Integer::toBinaryString)
		                           .map(s -> ASCII_BUFFER.substring(s.length()).concat(s))
		                           .collect(Collectors.joining());

		char ch = ' ';
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < binary.length(); i++) {
			if (binary.charAt(i) != ch) {
				ch = binary.charAt(i);
				builder.append(ENCODED_BITS[ch - '0']);
			} else {
				builder.append("0");
			}
		}
		return builder.toString().trim();
	}

	static String decode(final String input) {
		if (!input.chars()
		          .filter(value -> !Character.isSpaceChar(value))
		          .map(Character::getNumericValue)
		          .allMatch(value -> value == 0)) {
			throw new InvalidEncodedStringException();
		}
		if (input.split("\\s").length % 2 != 0 || NORRIS_SEGMENT.split(input).length == 0) {
			throw new InvalidEncodedStringException();
		}
		return NORRIS_SEGMENT.matcher(input).results().map(match -> {
			final String type = match.group(2);
			final String length = match.group(3);
			return (switch (type.length()) {
				case 1 -> "1";
				case 2 -> "0";
				default -> throw new InvalidEncodedStringException();
			}).repeat(length.length());
		}).collect(Collectors.collectingAndThen(Collectors.joining(), s -> {
			if (s.length() % 7 != 0) {
				throw new InvalidEncodedStringException();
			}
			return ASCII_BLOCK.splitAsStream(s);
		})).map(s -> Integer.parseInt(s, 2)).map(Character::toString).collect(Collectors.joining());
	}

}