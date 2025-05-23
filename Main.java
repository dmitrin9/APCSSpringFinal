import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class Main {
	public static void main(String[] args) {
		Handler handler = new Handler();
		handler.handle();
	}
}

class Handler extends StringParser { // Very hacky inheritence.
	public Handler() {
		super();
	}

	public void handle() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print(" > ");
			String in = scanner.nextLine();
			if (in.equals("q")) {
				break;
			}
			ArrayList<String> arr = parseStrings(in);
			for (String s : arr) {
				Parser parser = new Parser(s);
				double result = parser.expr();
				System.out.println(result);
			}
		}
	}
}

class StringParser {
	public static ArrayList<String> parseStrings(String s) {
		ArrayList<String> arr = new ArrayList<String>();

		StringBuilder parseBuffer = new StringBuilder();
		boolean insideString=false;
		for (int i=0; i<s.length(); ++i) {
			if (s.charAt(i) == '"') {
				insideString = !insideString;
			}
			if (insideString) {
				parseBuffer.append(s.charAt(i));
			}
			else if (parseBuffer.length() > 0) {
				arr.add(parseBuffer.toString());
				parseBuffer.delete(0, parseBuffer.length());
			}
		}

		return arr;
	}
}

class Parser {
	static ArrayList<String> tokens;
	static int instruction_pointer;
	static String current;
	static double result;

	public Parser(String input) {
		instruction_pointer=0;
		tokens = _tok_scan(input);
		current = tokens.get(instruction_pointer);
		result=factor();
	}

	public static ArrayList<String> _tok_scan(String input) {
		ArrayList<String> arr = new ArrayList<String>();

		Pattern re = Pattern.compile("\\*|\\/|\\+|-|\\(|\\)|[0-9]+\\.[0-9]|[0-9]+");

		Matcher matcher = re.matcher(input);
		while (matcher.find()) {
			arr.add(matcher.group());
		}
		return arr;
	}

	public static void eat() {
		if (instruction_pointer+1 < tokens.size()) {
			instruction_pointer += 1;
			current = tokens.get(instruction_pointer);
		} else {
			instruction_pointer = -1;
		}
	}

	public static double expr() {
		eat();

		if (current.equals("+")) {
			eat();
			result += factor();
		}
		if (current.equals("-")) {
			eat();
			result -= factor();
		}

		if (instruction_pointer != -1) {
			return expr();
		}
		return result;
	}

 	public static double factor() {
		return Double.parseDouble(current);
 	}
}
