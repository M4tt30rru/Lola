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
				+ "	##replace if(##(te.test)) return ##(te.i);\n"
				+ "Which[x>0,x];"; 
		String result = "if(x>0) return x;";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void which4() {
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(TestExpression)\n"
				+ "	##Expression(test), ##Identifier(i)\n"
				+ "##Find(TestExpressionList)\n"
				+ "	##OneOrMore ##TestExpression ##separator ;\n"
				+ "##Find Which[##TestExpressionList(tel)];\n"
				+ " ##run{\n"
				+ "tels = [str(te) for te in tels]\n"
				+ "l=len(tels)\n"
				+ "te=tels[0]\n"
				+ "}\n"
				+ "	##replace if(##(te.test)) return {##(te.i);##(l)};\n"
				+ "Which[x>0,x;y>0,y;z>0,z];"; 
		String result = "if(x>0) return x;";
		auxz.runStringTest(s, result);
	}

	@Test
	public void which5() {
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(TestExpression)\n"
				+ "	##Expression(test), ##Identifier(i)\n"
				+ "##Find(TestExpressionList)\n"
				+ "	##OneOrMore ##TestExpression ##separator ;\n"
				+ "##Find Which[##TestExpressionList(tel)];\n"
				+ "	##replace "
				+ "		##ForEach(tels)"
				+ "			(if(##(_.test)) return ##(_.i))"
				+ "\n"
				+ "Which[x>0,x;y>0,y;z>0,z];"; 
		String result = "if(x>0) return x;";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void which6() {
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(TestExpression)\n"
				+ "	##Expression(test), ##Identifier(i)\n"
				+ "##Find Which[##OneOrMore ##TestExpression(te) ##separator ;];\n"
				+ "	##replace {\n"
				+ "		##ForEach(tes)"
				+ "			if(##(_.test)) return ##(_.i);\n"
				+ "	}\n"
				+ "Which[x>0,x;y>0,y;z>0,z];"; 
		String result = "{if(x>0) return x; if(y>0) return y; if(z>0) return z;}";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void which6b() {
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(TestExpression)\n"
				+ "	##Expression(test), ##Identifier(i)\n"
				+ "##Find Which[##OneOrMore ##TestExpression(te) ##separator ;];\n"
				+ "	##run{\n"
				+ "n=len(tes)\n"
				+ "}\n"
				+ "	##replace {\n"
				+ "		if(##(tes[0].test)) return ##(tes[0].i);\n"
				+ "		##ForEach(tes[1:n-1])\n"
				+ "			else if(##(_.test)) return ##(_.i);\n"
				+ "		else return ##(tes[n-1].i);\n"
				+ "	}\n"
				+ "Which[x>0,x;y>0,y;z>0,z];"; 
		String result = "{if(x>0) return x; else if(y>0) return y; else return z;}";
		auxz.runStringTest(s, result);
	}

	
	@Test
	public void which7() {
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(TestExpression)\n"
				+ "	##Expression(test), ##Identifier(i)\n"
				+ "##Find Which[##OneOrMore ##TestExpression(te) ##separator ;];\n"
				+ "	##replace {\n"
				+ "		##ForEach(tes)"
				+ "			if(##(_.test)) return ##(_.i);\n"
				+ "	}\n"
				+ "Which[f(),x;g(),y;h(),z];"; 
		String result = "{if(f()) return x; if(g()) return y; if(h()) return z;}";
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
	
}
