package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import static org.junit.Assert.*;

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
				 + "##Find Nest[##Any(_f),##Expression(_expr),##Literal(_n)];\n"
				 + "	##run {\n"
				 + "fs = [str(_f)+'(' for _f in range(##(_n))]\n"
				 + "}\n" //
				 + "	##replace ##(fs);\n"
//				 + "		##ForEach if(##(_t)>0) return ##(_t) else return -##(_t);\n"
				 + "Nest[f,x,3];"; 
		System.out.println(s);
		String result = "f(f(f(x)));\n";
		auxz.runStringTest(s, result);
	}
}
