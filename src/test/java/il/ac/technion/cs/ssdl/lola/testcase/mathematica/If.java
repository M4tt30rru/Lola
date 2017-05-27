package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

public class If {
	
	/*
	 * Mathematica If
	 * 
	 * If[condition,t,f] gives t if condition evaluates to True, and f if it evaluates to False.
	 * If[condition,t,f,u] gives u if condition evaluates to neither True nor False.
	 * 
	 */
			
	// If[true,1,2] 
	
//	"" //
//	+ "##Find do[##Any(_e),{##Identifier(_i),##Literal(_max)}];\n" //
//	+ " ##replace for(int ##(_i)=0;##(_i)<##(_max);++##(_i)) {##(_e);}\n" //
//	+ "do[f(i);g(i * 2),{i,100}];", //
//"for(int i=0;i<100;++i) {f(i);g(i * 2);}");
	
	
	@Test
	public void if1() {
		String s = "" // 
							 + "##Find if[##Literal(_condition),##Literal(_t),##Literal(_f)];\n " //
							 + "	##replace if(##(_condition)) return ##(_t) else return ##(_f);\n"
							 + "if[3,1,2];"; 
		String result = "if(3)  return  1  else  return  2;\n";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void if2() {
		String s = ""
							 + "##Find(##BooleanLiteral) ##Either true ##or false" // 
							 + "##Find if[##BooleanLiteral(_condition),##Literal(_t),##Literal(_f)];\n " //
							 + "	##replace if(##(_condition)) return ##(_t) else return ##(_f);\n"
							 + "if[true,1,2];"; 
		String result = "if(true)  return  1  else  return  2;\n";
		auxz.runStringTest(s, result);
	}
}
