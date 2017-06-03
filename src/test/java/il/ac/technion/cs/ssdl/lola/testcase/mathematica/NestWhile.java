package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import static org.junit.Assert.*;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

public class NestWhile {
	//NestWhile[f,expr,test]
	//		starts with expr, then repeatedly applies f until applying test to the result no longer yields True.
	// NestWhile[Log, 100, # > 0 &]
	// Log[Log[Log[100]]]
	
	String expression = "##Find(NoCommaExpression)\n"
			+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
			+ "##Find(Expression)\n"
			+ " ##Either ##NoCommaExpression ##or (##Any)\n";
	
	@Test
	public void NestWhile01() {
		String s = ""
				+ expression
				+ "##Find NestWhile[##Expression(f),##Expression(expr),##Expression(te)];\n"
				+ "	##replace while(##(te)(##(expr))) ##(f)(##(expr));\n"
				+ "NestWhile[f,x+1,test];"; 
		String result = "while(test(x+1))\n f(x+1);";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void NestWhile1() {
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(CompOperator)\n"
				+ "	##Either > ##or < ##or >= ##or <= ##or == ##or !=\n"
				+ "##Find(TestExpression)\n"
				+ "	##Identifier(i) ##CompOperator(op) ##Literal(l)\n"
				+ "##Find NestWhile[##Expression(f),##Expression(expr),##TestExpression(te)];\n"
				+ "	##replace do{##(te.i)=##(f)(##(expr))}\nwhile(##(te))\n"
				+ "NestWhile[Log,100,res>0];"; 
		String result = "do{res=Log(100)}\nwhile(res>0);";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void NestWhile1d() {
		String s = ""
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(CompOperator)\n"
				+ "	##Either > ##or < ##or >= ##or <= ##or == ##or !=\n"
				+ "##Find(TestExpression)\n"
				+ "	##Identifier(i) ##CompOperator(op) ##Literal(l)\n"
				+ "##Find NestWhile[##Expression(f),##Literal(expr),##TestExpression(te)];\n"
				+ "	##replace do{##(te.i)=##(f.name)(##(expr))}\n 'while'(##te)\n"
				+ "NestWhile[Log(),100,res>0];"; 
		String result = "do{res=Log(100)}\nwhile(res>0);";
		auxz.runStringTest(s, result);
	}

	
	@Test
	public void NestWhile1b() {
		String s = "" //
				+ "##Find(NoCommasExpression)\n" //
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n" //
				+ "##Find(Expression)\n" //
				+ " ##Either ##NoCommasExpression ##or (##Any)\n" //
				+ "##Find(CompOperator)\n" //
				+ "	##Either > ##or < ##or >= ##or <= ##or == ##or !=\n" //
				+ "##Find	##Identifier(i) ##CompOperator(op) ##Literal(l);\n" //
//				+	" ##run{\n" //
//				+ "s = 'gt' if(str(op) == '>') else 'other'" //
//				+ "}\n" //
//				+ "	##replace ##(i) ##(op) ##(l);\n"
				+ "	##replace ok;\n"
				+ "res>0;\n"; 
		String result = "re gt 0;";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void NestWhile2() {
		String s = ""
				+ "##Find(POpen) (\n"
				+ "##Find(PClose) )\n"
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find ##POpen ##Expression(x) ##PClose>##Expression(y);\n"
				+ " ##run{\n"
				+ "val='\"true\";' if eval(str(x))>0 else '\"false\";'\n"
				+ "}\n"
				+ "	##replace ##(val)\n"
				+ "(1+3)>0;\n"
				+ "(1-3)>0;\n"; 
		String result = "\"true\";\n"
				+ "\"false\";\n";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void NestWhile1c() {
		String s = ""
				+ "##Find(Minus) -\n"
				+ "##Find(Plus) +\n"
				+ "##Find(Addition)\n"
				+ " ##Literal(x) ##Plus(p) ##Literal(y)\n"
				+ "##Find(Subtraction)\n"
				+ " ##Literal(x) ##Minus(m) ##Literal(y)\n"
				+ " ##run{\n"
				+ "if(p!=None):\n"
				+ "	op = '+'\n"
				+ "else:\n"
				+ "	op = '-'\n"
				+ "}\n"
				+ "	##replace ##(x) ##(op) ##(y)\n"
				+ "(1+3);\n"
				+ "(1-3);\n"; 
		String result = "1 operator 3;\n"
				+ "1 operator 3;\n";
		auxz.runStringTest(s, result);
	}
	
	@Test
	public void NestWhile3() {
		String s = ""
				+ "##Find(POpen) (\n"
				+ "##Find(PClose) )\n"
				+ "##Find(NoCommaExpression)\n"
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
				+ "##Find(Expression)\n"
				+ " ##Either ##NoCommaExpression ##or (##Any)\n"
				+ "##Find(Minus) -\n"
				+ "##Find ##Either ##Literal(x) ##or ##Minus(neg) ##Literal(x) > ##Literal(y);\n"
				+ " ##run{\n"
				+ "x = -x if neg != None else x\n"
				+ "val='\"true\";' if eval(str(x))>0 else '\"false\";'\n"
				+ "}\n"
				+ "	##replace ##(val)\n"
				+ "3>0;\n"
				+ "-3>0;\n"; 
		String result = "\"true\";\n"
				+ "\"false\";\n";
		auxz.runStringTest(s, result);
	}
	
	public static void main(String args[]){
		int a = 100;
		do{
			System.out.println(a);
			a -= 2;
		} while(a>0);
	}
}