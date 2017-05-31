package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import static org.junit.Assert.*;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

public class Which {
	
// Which[test1,value1,test2,value2,…]
// evaluates each of the testi in turn, returning the value of the valuei corresponding to the first one that yields True.
// a = 2; Which[a == 1, x, a == 2, b]
	
	@Test
	public void which1() {
		
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(TestExpression)\n"
				+ "	##Match ##Expression(_f) , ##Identifier(_i)\n"
				+ "##Find(TestExpressions)\n"
				+ "	##OneOrMore ##TestExpression(_te) ##separator,\n"
				+ "##Find Which[##TestExpressions(_tests)];\n"
				+ "	##replace ##(_te);\n"
				+ "Which[a == 1,x,a == 2, b];"; 
		String result = "f(f(f(x)));";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void which3() {
		
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(TestExpression)\n"
				+ "	##Expression(test), ##Identifier(i)\n"
				+ "##Find Which[##TestExpression(te)];\n"
//				+ " ##run{\n"
//				+ "l = len(##(tes))\n"
//				+ "}\n"
				+ "	##replace if(##(te.test)) return ##(te.i);\n"
				+ "Which[x>0,x];"; 
		String result = "if(x>0) return x;";
		auxz.runStringTest(s, result);
	}
	
	public void which5() {
		
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(TestExpression)\n"
				+ "	##Expression(test), ##Identifier(i)\n"
				+ "##Find Which[##TestExpression(te)];\n"
//				+ " ##run{\n"
//				+ "l = len(##(tes))\n"
//				+ "}\n"
				+ "	##replace if(##(te.test)) return ##(te.i);\n"
				+ "Which[x>0,x];"; 
		String result = "if(x>0) return x;";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void which2() {
		
		String s = ""
				+ "##Find which ##Literal(_l);\n"
				+ "	##replace W ##(_l);\n"
				+ "which 1;"; 
		String result = "W 1;";
		auxz.runStringTest(s, result);
		
	}
	
	@Test
	public void do1() {
		auxz.runStringTest(
				"" //
						+ "##Find do[##Any(_e),{##Identifier(_i),##Literal(_max)}];\n" //
						+ " ##replace for(int ##(_i)=0;##(_i)<##(_max);++##(_i)) {##(_e);}\n" //
						+ "do[f(i);g(i * 2),{i,100}];", //
				"for(int i=0;i<100;++i) {f(i);g(i * 2);}");
	}
}
