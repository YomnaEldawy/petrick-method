import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class TruthTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Just testing functions performance
		String expression = "";
		int numberOfDigits;
		int choice;
		Scanner input = new Scanner(System.in);
		System.out.println("Choose one of the following");
		System.out.println("1) Get a truth table");
		System.out.println("2) Test for tautology");
		System.out.println("3) test for Contradiction");
		System.out.println("4) Test for equivalence");
		System.out.println("Expression syntax guide: \n 1) Expressions may use any alphabetical letter from A to Z where A represents most significant bit");
		System.out.println("2) Use the following symbols:\n & to represents AND, | to represent OR, % to represent NAND, $ to represent NOR, # to represent XOR \n " +
				"- to represent XNOR" +
				"/ to represent IFF" +
				"* to represent IMPLIES" +
				"' to represent NOT");
		System.out.println("Example: A & B'");
		choice = input.nextInt();
		if (choice == 1) {
			System.out.println("Enter Expression");
			expression = input.nextLine();
			expression = input.nextLine();
			System.out.println("Enter number of digits");
			numberOfDigits = input.nextInt();
			int[] LHS = setTerms(numberOfDigits);
			int[] result = getFinalResult(expression, numberOfDigits);
			printTable(LHS, numberOfDigits);
			printArray(result);
		} else if (choice == 2) {
			System.out.println("Enter Expression");
			expression = input.nextLine();
			expression = input.nextLine();
			System.out.println("Enter number of digits");
			numberOfDigits = input.nextInt();
			int[] LHS = setTerms(numberOfDigits);
			int[] result = getFinalResult(expression, numberOfDigits);
			if (isTautology(result)) {
				System.out.println("Tautology");
			} else {
				System.out.println("Not a tautology");
			}
			printTable(LHS, numberOfDigits);
			printArray(result);
		} else if (choice == 3) {
			System.out.println("Enter Expression");
			expression = input.nextLine();
			expression = input.nextLine();
			System.out.println("Enter number of digits");
			numberOfDigits = input.nextInt();
			int[] LHS = setTerms(numberOfDigits);
			int[] result = getFinalResult(expression, numberOfDigits);
			if (isContradiction(result)) {
				System.out.println("Contradiction");
			} else {
				System.out.println("Not a Contradiction");
			}
			printTable(LHS, numberOfDigits);
			printArray(result);
		} else if (choice == 4) {
			System.out.println("Enter Expression 1");
			String expression1 = input.nextLine();
			expression1 = input.nextLine();

			System.out.println("Enter Expression 2");
			String expression2 = input.nextLine();

			System.out.println("Enter number of digits");
			numberOfDigits = input.nextInt();
			int[] LHS = setTerms(numberOfDigits);
			int[] result1 = getFinalResult(expression1, numberOfDigits);
			int[] result2 = getFinalResult(expression2, numberOfDigits);
			if (isEquivalent(result1, result2)) {
				System.out.println("Equivalent");
			} else {
				System.out.println("Not Equivalent");
			}
			printTable(LHS, numberOfDigits);
			System.out.println("Expression 1:");
			printArray(result1);
			System.out.println("Expression 2:");
			printArray(result2);
		} else {
			System.out.println("Please choose from 1 to 4");
		}
		input.close();
	}

	public static int[] setTerms(int bitsNumber) {
		int arrSize = (int) Math.pow(2, bitsNumber);
		int[] result = new int[arrSize];
		for (int i = 0; i < result.length; i++) {
			result[i] = i;
		}

		return result;
	}

	public static Map<String, int[]> namesAndTables(int bitsNumber) {
		Map<String, int[]> finalResult = new HashMap<>();
		char variable = 'A' - 1;
		int fillEvery = 0;
		int[] table = new int[(int) Math.pow(2, bitsNumber)];
		for (int i = 0; i < bitsNumber; i++) {
			variable++;
			fillEvery = (int) Math.pow(2, bitsNumber)
					/ ((int) Math.pow(2, i + 1));
			table = fillEvery(fillEvery, bitsNumber);
			String strVar = Character.toString(variable);
			finalResult.put(strVar, table);
		}
		return finalResult;
	}

	public static int[] fillEvery(int n, int bitsNumber) {
		int[] finalResult = new int[(int) Math.pow(2, bitsNumber)];
		int i = 0;
		while (i < finalResult.length) {
			for (int j = 0; j < n; j++) {
				finalResult[i] = 0;
				i++;
			}
			for (int j = 0; j < n; j++) {
				finalResult[i] = 1;
				i++;
			}
		}
		return finalResult;
	}

	public static String intToBinary(int term, int bitsNumber) {
		String binaryString = Integer.toBinaryString(term);
		while (binaryString.length() < bitsNumber) {
			binaryString = "0" + binaryString;
		}
		return binaryString;
	}

	public static void printTable(int[] terms, int bitsNumber) {
		for (int i = 0; i < terms.length; i++) {
			System.out.print(intToBinary(terms[i], bitsNumber) + "  ");
		}
		System.out.println("");
	}

	public static boolean isTautology(int[] fn) {
		for (int i = 0; i < fn.length; i++) {
			if (fn[i] == 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean isContradiction(int[] fn) {
		for (int i = 0; i < fn.length; i++) {
			if (fn[i] == 1) {
				return false;
			}
		}
		return true;

	}

	public static boolean isEquivalent(int[] fn1, int[] fn2) {
		return Arrays.equals(fn1, fn2);
	}

	public static int[] AND(int[] fn1, int[] fn2) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (fn1[i] & fn2[i]);
		}
		return result;

	}

	public static int[] OR(int[] fn1, int[] fn2) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (fn1[i] | fn2[i]);
		}
		return result;

	}

	public static int[] NOT(int[] fn1) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (fn1[i] ^ 1);
		}
		return result;

	}

	public static int[] NAND(int[] fn1, int[] fn2) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (fn1[i] & fn2[i]) ^ 1;
		}
		return result;

	}

	public static int[] IMPLIES(int[] fn1, int[] fn2) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (fn1[i] & (fn2[i] ^ 1) ^ 1);
		}
		return result;

	}

	public static int[] IFF(int[] fn1, int[] fn2) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (fn1[i] ^ fn2[i]) ^ 1;
		}
		return result;

	}

	public static int[] NOR(int[] fn1, int[] fn2) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (fn1[i] | fn2[i]) ^ 1;
		}
		return result;

	}

	public static int[] XOR(int[] fn1, int[] fn2) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = fn1[i] ^ fn2[i];
		}
		return result;

	}

	public static int[] XNOR(int[] fn1, int[] fn2) {
		int[] result = new int[fn1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (fn1[i] ^ fn2[i]) ^ 1;
		}
		return result;

	}

	public static int[] getResultOfTwo(int[] op1, String operation, int[] op2) {
		switch (operation) {

		case "&":
			return AND(op1, op2);
		case "|":
			return OR(op1, op2);
		case "%":
			return NAND(op1, op2);
		case "$":
			return NOR(op1, op2);
		case "#":
			return XOR(op1, op2);
		case "-":
			return XNOR(op1, op2);
		case "/":
			return IFF(op1, op2);
		case "*":
			return IMPLIES(op1, op2);
		default:
			break;
		}
		return null;
	}

	public static int[] getFinalResult(String expression, int bitsNumber) {
		Stack<String> operations = new Stack<String>();
		Stack<int[]> operands = new Stack<int[]>();
		Map<String, int[]> names = new HashMap<String, int[]>();
		names = namesAndTables(bitsNumber);

		for (int i = 0; i < expression.length(); i++) {
			char current = expression.charAt(i);
			String StrCurrent = Character.toString(current);
			if (expression.charAt(i) >= 'A' && expression.charAt(i) <= 'z') {
				operands.push(names.get(StrCurrent.toUpperCase()));
				continue;
			} else if (current == '\'') {
				operands.push(NOT(operands.pop()));
				continue;
			} else if (current == '(') {
				operations.push("(");
				continue;
			}else if (getPrecedence(StrCurrent) == -1 && current != '('
					&& current != ')') {
				continue;
			} else if (operations.isEmpty()) {
				operations.push(StrCurrent);
			} else {
				if (current == ')') {
					// pop until ( found
					while (!operations.isEmpty() && operations.peek() != "("
							&& operands.size() > 2) {
						operands.push(getResultOfTwo(operands.pop(),
								operations.peek(), operands.pop()));

					}
					operations.pop();
				} else {
					while (operands.size() >= 2
							&& operations.size() >= 1
							&& getPrecedence(StrCurrent) >= getPrecedence(operations
									.peek())) {
						operands.push(getResultOfTwo(operands.pop(),
								operations.pop(), operands.pop()));

					}
					operations.push(StrCurrent);
				}
			}
		}
		while (operands.size() > 2 && operations.size() > 1) {
			operands.push(getResultOfTwo(operands.pop(), operations.pop(),
					operands.pop()));
		}
		if (operands.size() == 2 && operations.size() == 1) {
			operands.push(getResultOfTwo(operands.pop(), operations.pop(),
					operands.pop()));
		}

		return operands.peek();
	}

	public static int getPrecedence(String operation) {
		int precedence;
		switch (operation) {
		case "'":
			precedence = 1;
			break;
		case "&":
			precedence = 2;
			break;
		case "|":
			precedence = 3;
			break;
		case "%":
			precedence = 4;
			break;
		case "$":
			precedence = 4;
			break;
		case "#":
			precedence = 4;
			break;
		case "-":
			precedence = 4;
			break;
		case "/":
			precedence = 5;
			break;
		case "*":
			precedence = 5;
			break;

		default:
			precedence = -1;
		}
		return precedence;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + "       ");
		}
		System.out.println(" ");
	}

}
