import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("Math > ");
			String in = scanner.nextLine();
			if (in.equals("q")) {
				break;
			}
			Parser parser = new Parser(in);
			double result = parser.expr();
			System.out.println(result);
		}
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
		System.out.println(tokens);
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
