/*
 * AUTHOR: Viren Patel
 * DATE: August 22, 2019
 * INPUT: An expression to be evaluated following the format outlined in the requirements page of this exercise.
 * OUTPUT: The result of evaluating the given expression.
 * COMMENTS: My initial approach was to structure the input into a binary tree. It would have been very 
 * efficient and clean, but would not be extensible as it only supports two elements. Therefore, my
 * approach is as follows:
 * 1. The original input expression is organized into a list. Elements could be numbers or subexpressions.
 * 2. Then the list is enumerated and the right operation (Add or Multiply) is performed:
 *      a) if the list element is a digit, then perform the operation.
 *      b) if the list element is an expression, recursively repeat the previous steps.
 * This program supports any number of sub-expressions instead of being limited to only 2.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class sExpressionCalculator {
	
	private static final String OPERATOR_ADD = "add";
	private static final String OPERATOR_MULTIPLY = "multiply";
	
	public static void main(String[] args) {
			
		String expression = args[0];
		expression = expression.substring(0, expression.length()-1);
		
		List<String> list = subExpression(expression);
		
		int result = calculateExpression(list);
		
		System.out.println(result);
		
	}
	
	/**
	 * @param list - the list of elements for the current expression.
	 * @return res - the result of the current expression being evaluated.
	 */
	public static int calculateExpression(List<String> list	) {
						
		// the first element in the list is the operation to be performed. Add or Multiply.
		String function = list.get(0);
		list.remove(0); // remove the first element.
		
		int res = getResult(function, list);
		
		return res;
	}
	
	/**
	 * Separate the current expression into Function expression expression ... ...using stack.
	 * @param expression - current expression or sub-expression to be turned into a list.
	 * @return res - a list in the format described above.
	 */
	public static List<String> subExpression(String expression) {
		
		List<String> res = new ArrayList<String>();
		String[] arr = expression.split(" ");
		
		res.add(arr[0].substring(1));
		
		// Determine when a sub-expression is completed by tracking open and close brackets.
		Stack<String> stack = new Stack<String>();
		
		int startIndex = 0;
		
		for(int i = 1; i < arr.length; i++) {
			if(arr[i].startsWith("(")) {
				stack.push(arr[i]);
				if(startIndex == 0) {
					startIndex = i;
				}
			}
			if(arr[i].endsWith(")")) {
				stack.pop();
			}
			if(stack.isEmpty()) {
				if(arr[i].endsWith(")")) {
					res.add(arrayToString(Arrays.copyOfRange(arr, startIndex, i+1)));
					startIndex = 0;
				}else {
					res.add(arr[i]);
				}
				
			}
		}
		return res;
	}
	
	/**
	 * Return if the given string(element in expression) is a digit or not.
	 * @param s - the string to be evaluated.
	 * @return true - if the string can be parsed to an integer. False otherwise.
	 */
	public static boolean isDigit(String s) {
		try{
			Integer.parseInt(s);
		}catch(NumberFormatException e) {
			return false;
		};
		return true;
	}
	
	/**
	 * Helper method to convert an array to a single string.
	 * @param arr - the input array to be converted to string.
	 * @return res - string representation of the array separated by a " ".
	 */
	public static String arrayToString(String[] arr) {
		
		String res = "";
		
		for(int i = 0; i < arr.length; i++) {
			res += arr[i];
			if (i != arr.length-1) {
				res += " ";
			}
		}
		
		return res;
	}
	
	/**
	 * Method facilitating the calculation depending on what operation needs to be performed.
	 * @param function - the operation to be performed. Add or Multiply.
	 * @param list - the list representation of the current expression.
	 * @return the result after the correct operations have been performed.
	 */
	public static int getResult(String function, List<String>list) {
		if(function.trim().replace(" ", "").equalsIgnoreCase(OPERATOR_ADD)) {
			return addElements(list);
		}else if (function.trim().replace(" ", "").equalsIgnoreCase(OPERATOR_MULTIPLY)) {
			return multiplyElements(list);
		}else {
			throw new IllegalArgumentException("Bad Function.");
		}
	}
	
	/**
	 * Method to add elements and use recursion for sub-expressions.
	 * @param elements - list representation of current expression being evaluated.
	 * @return the result after current expression has been completed evaluated.
	 */
	public static int addElements(List<String> elements) {
		int res = 0;
		
		for(String s : elements)	{
			if(isDigit(s)) {
				res += Integer.parseInt(s);
			}else {
				res += calculateExpression(subExpression(s.substring(0, s.length()-1)));
			}
		}
		
		return res;
	}
	
	/**
	 * Method to multiply elements and use recursion for sub-expressions.
	 * @param elements - list representation of current expression being evaluated.
	 * @return the result after current expression has been completed evaluated.
	 */
	public static int multiplyElements(List<String> elements) {
		int res = 1;

		for(String s : elements)	{
			if(isDigit(s)) {
				res *= Integer.parseInt(s);
			}else {
				res *= calculateExpression(subExpression(s.substring(0, s.length()-1)));
			}
		}
		return res;
	}
	
}
