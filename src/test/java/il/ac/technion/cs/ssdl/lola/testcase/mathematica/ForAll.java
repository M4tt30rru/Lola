package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

/**
 * ForAll[x,expr]
 * represents the statement that expr is True for all values of x.
 * ForAll[x,cond,expr] 
 * states that expr is True for all x satisfying the condition cond. 
 * ForAll[{x1,x2,…},expr] 
 * states that expr is True for all values of all the xi.
*/

public class ForAll {
		
	String expression = "##Find(NoCommaExpression)\n"
			+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
			+ "##Find(Expression)\n"
			+ " ##Either ##NoCommaExpression ##or (##Any)\n";

	@Test
	public void forAll1() {
		String s = ""
				+ expression
				+ "ForAll[x,expr];"; 
		String result = "{if(a==1) return a;\n else if(b==2) return b;\n}\n";
		auxz.runStringTest(s, result);
	}
	
	public static void main(final String[] args){
		List<Integer> l = Arrays.asList(1,2,3,4,5,6);
		System.out.println(l.stream()
		 .filter(i -> i % 2 != 0)
		 .mapToInt(i -> i)
		 .allMatch(i -> i < 10));
	}

}

