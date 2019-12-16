package runtime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import compiler.FunctionModel;
import java.io.*;
import java.util.*;

/**
 * The BestRuntime class takes the intermediate code file and produces a result
 * file after execution of the intermediate code. It displays an exception if
 * there is error during runtime.
 *
 * @author Mounika, Anusha, Salil, Abdul
 *
 */
public class BestRuntime {

	private static ArrayList<ArrayList<String>> code = new ArrayList<>();
	private static int currentLine = 0;
	private static StringBuffer finalCode = new StringBuffer();
	private static HashMap<String, Integer> variables = new HashMap<String, Integer>();
	private static HashMap<String, FunctionModel> functions = new HashMap<String, FunctionModel>();
	private static Stack<Integer> stack = new Stack<Integer>();
	private static Stack<Boolean> conditionStack = new Stack<Boolean>();
	private static Stack<Boolean> functionStack = new Stack<Boolean>();
	private static Stack<HashMap<String, Integer>> currFuncVars;

	public static void main(String[] args) throws IOException {
		String line;
		String filename = "Loop.ICG";
		if (args.length != 0) {
			filename = args[0];
		}
		BufferedReader br = new BufferedReader(new FileReader("data/acm/" + filename));
		filename = filename.substring(0, filename.lastIndexOf('.'));
		File file = new File("data/output/" + filename + ".RESULT");
		currFuncVars = new Stack<>();

		while ((line = br.readLine()) != null) {
			ArrayList<String> words = new ArrayList<>();
			Collections.addAll(words, line.split(" "));
			code.add(words);
		}
		for (; currentLine < code.size(); currentLine++) {
			start(code.get(currentLine));
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(finalCode.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The start function takes in byte code words and matches the instruction and
	 * processes the operand.
	 *
	 * @param words
	 */
	private static void start(ArrayList<String> words) {
		int num1, num2;
		String functionName;
		switch (words.get(0)) {
		case "STORE":
			if (!variables.containsKey(words.get(1))) {
				variables.put(words.get(1), 0);
			} else {
				finalCode.append("EXCEPTION: variable ").append(words.get(1)).append(" already declared");
				currentLine = code.size() - 1;
			}
			break;

		case "MOV":
			if (variables.containsKey(words.get(1))) {
				variables.put(words.get(1), stack.pop());
			} else {
				finalCode.append("EXCEPTION: variable ").append(words.get(1)).append(" does not exist");
				currentLine = code.size() - 1;
			}
			break;

		case "PUSH":
			if (isNumeric(words.get(1))) {
				stack.push(Integer.parseInt(words.get(1)));
			} else if (words.get(1).equals("True")) {
				stack.push(-1);
			} else if (words.get(1).equals("False")) {
				stack.push(-2);
			} else if (variables.containsKey(words.get(1))) {
				stack.push(variables.get(words.get(1)));
			} else {
				finalCode.append("EXCEPTION: variable ").append(words.get(1)).append(" does not exist");
				currentLine = code.size() - 1;
			}
			break;

		case "WHENEVER":
			String wheneverIndex = words.get(1);
			while (!code.get(currentLine).get(0).equals("WHENEVER_START_" + wheneverIndex)) {
				currentLine++;
				start(code.get(currentLine));
			}
			currentLine++;
			if (conditionStack.pop()) {
				while (!code.get(currentLine).get(0).equals("THEN_END_" + wheneverIndex)) {
					start(code.get(currentLine));
					currentLine++;
				}
				while (!code.get(currentLine).get(0).equals("WHENEVER_END_" + wheneverIndex)) {
					currentLine++;
				}
			} else {
				while (!(code.get(currentLine).get(0).equals("THEN_END_" + wheneverIndex))) {
					currentLine++;
				}
				if (code.get(currentLine).get(0).equals("OTHER_" + wheneverIndex)) {
					while (!code.get(currentLine).get(0).equals("WHENEVER_END_" + wheneverIndex)) {
						start(code.get(currentLine));
						currentLine++;
					}
				}
			}
			currentLine++;
			break;

		case "LOOP":
			String loopIndex = words.get(1);
			int saveLoopIndex = currentLine;
			while (!code.get(++currentLine).get(0).equals("LOOP_START_" + loopIndex)) {
				start(code.get(currentLine));
			}
			if (conditionStack.pop()) {
				while (!code.get(++currentLine).get(0).equals("LOOP_END_" + loopIndex)) {
					start(code.get(currentLine));
				}
				currentLine = saveLoopIndex;
				start(code.get(currentLine));
			} else {
				while (!code.get(++currentLine).get(0).equals("LOOP_END_" + loopIndex)) {
				}
			}
			break;

		case "PRINT":
			if (words.get(1).equals("START_PRINT")) {
				break;
			} else if (words.get(1).equals("STOP_PRINT")) {
				if (stack.peek() == -1) {
					stack.pop();
					finalCode.append("TRUE");
				} else if (stack.peek() == -2) {
					stack.pop();
					finalCode.append("False");
				} else {
					finalCode.append(stack.pop() + "\n");
				}
				break;
			}
			if (variables.containsKey(words.get(1)))
				finalCode.append(variables.get(words.get(1)));
			else
				finalCode.append(words.get(1).replaceAll("\"", ""));
			finalCode.append("\n");
			break;

		case "EQUAL":
			num1 = stack.pop();
			num2 = stack.pop();
			conditionStack.push(num2 == num1);
			break;

		case "NOTEQ":
			num1 = stack.pop();
			num2 = stack.pop();
			conditionStack.push(num2 != num1);
			break;

		case "LESS":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				conditionStack.push(false);
			} else {
				conditionStack.push(num2 < num1);
			}
			break;

		case "LESSEQ":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				conditionStack.push(false);
			} else {
				conditionStack.push(num2 <= num1);
			}
			break;

		case "GREATER":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				conditionStack.push(false);
			} else {
				conditionStack.push(num2 > num1);
			}
			break;

		case "GREATEREQ":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				conditionStack.push(false);
			} else {
				conditionStack.push(num2 >= num1);
			}
			break;

		case "ADD":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				stack.push(-2);
			} else {
				stack.push(num2 + num1);
			}
			break;

		case "SUB":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				stack.push(-2);
			} else {
				stack.push(num2 - num1);
			}
			break;

		case "DIV":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				stack.push(-2);
			} else {
				stack.push(num2 / num1);
			}
			break;

		case "MUL":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				stack.push(-2);
			} else {
				stack.push(num2 * num1);
			}
			break;

		case "MOD":
			num1 = stack.pop();
			num2 = stack.pop();
			if (num1 < 0 || num2 < 0) {
				stack.push(-2);
			} else {
				stack.push(num2 % num1);
			}
			break;

		case "METHOD":
			functionName = words.get(1);
			if (functions.containsKey(functionName)) {
				finalCode.append("EXCEPTION: function ").append(words.get(1)).append(" already exist");
				currentLine = code.size() - 1;
				break;
			}
			FunctionModel newFunction = new FunctionModel();
			HashMap<String, Integer> functionVars = newFunction.getFuncVars();
			ArrayList<String> varNames = newFunction.getVarNames();

			while (!code.get(++currentLine).get(0).equals("METHOD_START_" + functionName)) {
				ArrayList<String> currWords = code.get(currentLine);
				if (!functionVars.containsKey(currWords.get(1))) {
					varNames.add(currWords.get(1));
					functionVars.put(currWords.get(1), 0);

				} else {
					finalCode.append("EXCEPTION: variable ").append(currWords.get(1)).append(" already declared");
					currentLine = code.size() - 1;
				}
			}
			newFunction.setStart(currentLine + 1);
			while (!code.get(++currentLine).get(0).equals("METHOD_END_" + functionName)) {
			}
			newFunction.setEnd(currentLine - 1);
			newFunction.setVarNames(varNames);
			newFunction.setFuncVars(functionVars);
			functions.put(functionName, newFunction);
			break;

		case "CALL":
			functionName = words.get(1);
			int i = 0;
			FunctionModel currFunction = functions.get(words.get(1));
			ArrayList<String> currVarNames = currFunction.getVarNames();

			HashMap<String, Integer> hm = new HashMap<>();
			for (Map.Entry<String, Integer> currVarName : variables.entrySet()) {
				hm.put(currVarName.getKey(), currVarName.getValue());
			}
			currFuncVars.push(hm);

			ArrayList<String> x;
			while (!(x = code.get(++currentLine)).get(0).equals("CALL_END_" + functionName)) {
				if (isNumeric(x.get(1))) {
					if (variables.containsKey(currVarNames.get(i))) {
						variables.put(currVarNames.get(i), Integer.parseInt(x.get(1)));
					} else {
						finalCode.append("EXCEPTION: parameter ").append(variables.get(i)).append(" does not exist");
						currentLine = code.size() - 1;
						break;
					}
				} else {
					if (variables.containsKey(x.get(1))) {
						variables.put(currVarNames.get(i), variables.get(x.get(1)));
					} else {
						finalCode.append("EXCEPTION: variable ").append(words.get(1)).append(" does not exist");
						currentLine = code.size() - 1;
						break;
					}
				}
				i++;
			}
			int saveCurrentLine = currentLine;
			for (currentLine = currFunction.getStart(); currentLine <= currFunction.getEnd(); currentLine++) {
				start(code.get(currentLine));
			}

			if (!currFuncVars.peek().containsValue(null))
				variables = currFuncVars.pop();

			currentLine = saveCurrentLine;
			break;
		}
	}

	private static boolean isNumeric(String str) {
		return str.matches("-?\\d+?");
	}

	private static void printStack(Stack<Integer> stack) {
		String values = Arrays.toString(stack.toArray());
	}

}
