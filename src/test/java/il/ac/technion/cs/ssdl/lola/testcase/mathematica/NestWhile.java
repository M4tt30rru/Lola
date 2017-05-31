package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import static org.junit.Assert.*;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

public class NestWhile {
	//NestWhile[f,expr,test]
	//		starts with expr, then repeatedly applies f until applying test to the result no longer yields True.
	// NestWhile[Log, 100, # > 0 &]
	// Log[Log[Log[100]]]
	
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
				+ "	##replace do{##(t)=(f.name)(##(expr))}\nwhile(##(te.i)##(te.op)##(te.l))\n"
				+ "NestWhile[Log,100,res>0];"; 
		String result = "do{res=Log(100)}\nwhile(res>0);";
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
				+ "val='\"true\"' if x>0 else '\"false\"'\n"
				+ "}\n"
				+ "	##replace ##(val)\n"
				+ "(1+3)>0;\n"
				+ "(1-3)>0;\n"; 
		String result = "\"true\";\n"
				+ "\"false\";\n";
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
				+ "##Find ##Literal(x) > ##Literal(y);\n"
				+ " ##run{\n"
				+ "val='\"true\"' if x>0 else '\"false\"'\n"
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