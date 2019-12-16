package compiler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The FunctionModel class defines the fields in a function
 * 
 * @author Anusha
 *
 */
public class FunctionModel {
	private int start;
	private int end;
	private HashMap<String, Integer> funcVars;
	private ArrayList<String> varNames;
	private String returnVal;

	public HashMap<String, Integer> getFuncVars() {
		return funcVars;
	}

	public void setFuncVars(HashMap<String, Integer> funcVars) {
		this.funcVars = funcVars;
	}

	public ArrayList<String> getVarNames() {
		return varNames;
	}

	public void setVarNames(ArrayList<String> varNames) {
		this.varNames = varNames;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setReturnVal(String returnVal) {
		this.returnVal = returnVal;
	}

	public FunctionModel() {
		funcVars = new HashMap<>();
		varNames = new ArrayList<>();
		returnVal = "";
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String getReturnVal() {
		return returnVal;
	}
}
