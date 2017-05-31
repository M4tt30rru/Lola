package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

public class Nest {
	
	// Nest[f,expr,n]
	// example
	// Nest[f, x, 3]
	// f[f[f[x]]]
	
	// f(f(f(x)))
		
	@Test
	public void nest1() {
		String s = ""
				 + "##Find(NoCommasExpression)\n" //
				 + "	##Match ##Any ##exceptFor ##Any, ##Any\n" //
				 + "##Find(Expression)\n" //
				 + "	##NoCommasExpression\n" //
				 + "##Find Nest[##Identifier(_f), ##Identifier(_expr) , ##Literal(_n)];\n"
				 + "	##run {\n"
				 + "_fs = str(_expr) # (_f.name)\n"
//				 + "fs = [str(##(_f))+'(' for _f in range(##(_n))]\n"
				 + "}\n" //
				 + "	##replace ##(_f);"
//				 + "		##ForEach if(##(_t)>0) return ##(_t) else return -##(_t);\n"
				 + "Nest[f,x,3];"; 
//		System.out.println(s);
		String result = "f(f(f(x)));";
		auxz.runStringTest(s, result);
	}

	@Test
	public void nest2() {
		String s = ""
				 + "##Find Nest[##Identifier(_f), ##Identifier(_x) , ##Literal(_n)];\n"
				 + "	##replace ##(_f);"
				 + "Nest[f,x,3];"; 
		String result = "f(f(f(x)));";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void nest3() {
		String s = ""
				 + "##Find Nest[##Identifier(_f)];\n"
				 + "	##replace ##(_f)(x);"
				 + "Nest[f];"; 
		String result = "f(x);";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void nest4() {
		String s = ""
				 + "##Find if[##Literal(_n)];\n"
				 + "	##replace ##(_n);"
				 + "if[3];"; 
		String result = "3;";
		auxz.runStringTest(s, result);
	}

}


class Foo {
	
	
	
}