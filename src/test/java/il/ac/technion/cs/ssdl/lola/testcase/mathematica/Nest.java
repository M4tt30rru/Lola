package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

/**
 * 
 * Testing lola for the Mathematica Nest command
 * Nest[f,expr,n] 
 * 	 example 
 * 	 Nest[f, x, 3] 
 * 	 f[f[f[x]]]
 * @author matteo
 *
 */

public class Nest {
		
	@Test
	public void nest1() {
		String s = ""
				 + "##Find(NoCommasExpression)\n" //
				 + "	##Match ##Any ##exceptFor ##Any, ##Any\n" //
				 + "##Find(Expression)\n" //
				 + "	##NoCommasExpression\n" //
				 + "##Find Nest[##Identifier(f), ##Identifier(expr) , ##Literal(n)];\n"
				 + "	##run {\n"
				 + "fs = ''.join([(str(f) + '(') for i in range(n)])+ 'x' + ''.join([')' for i in range(n)])\n"
				 + ""
				 + "}\n" //
				 + "	##replace ##(fs);\n"
				 + "Nest[f,x,3];"; 
		String result = "f(f(f(x)));";
		auxz.runStringTest(s, result);
	}

	@Test
	public void nest2() {
		String s = ""
				 + "##Find Nest[##Identifier(f), ##Identifier(x), ##Literal(n)];\n"
				 + "	##run{\n"
				 + "s = (str(f)+'(')*n + str(x) + ')'*n\n"
				 + "}\n"
				 + "	##replace ##(s);\n"
				 + "Nest[f,x,3];"; 
		String result = "f(f(f(x)));";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void nest3() {
		String s = ""
				 + "##Find Nest[##Identifier(_f)];\n"
				 + "	##replace ##(_f)(x);\n"
				 + "Nest[f];"; 
		String result = "f(x);";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void nest4() {
		String s = ""
				 + "##Find if[##Literal(_n)];\n"
				 + "	##replace ##(_n);\n"
				 + "if[3];"; 
		String result = "3;";
		auxz.runStringTest(s, result);
	}

}
