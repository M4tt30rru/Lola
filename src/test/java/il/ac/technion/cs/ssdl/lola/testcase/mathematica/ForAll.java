package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

public class ForAll {
	
//	ForAll[x,expr]
//			represents the statement that expr is True for all values of x.
//			ForAll[x,cond,expr]
//			states that expr is True for all x satisfying the condition cond.
//			ForAll[{x1,x2,…},expr]
//			states that expr is True for all values of all the xi.
	
	String expression = "##Find(NoCommaExpression)\n"
			+ " ##Match ##Any ##exceptFor ##Any, ##Any\n"
			+ "##Find(Expression)\n"
			+ " ##Either ##NoCommaExpression ##or (##Any)\n";

	
	@Test
	public void forAll1() {
		String s = ""
				+ expression
				+ "##Find(TestExpression)\n"
				+ "	##Expression(test), ##Identifier(i)\n"
				+ "##Find Which[##OneOrMore ##TestExpression(te) ##separator ;];\n"
				+ "	##run{\n"
				+ "n=len(tes)\n"
				+ "}\n"
				+ "	##replace {\n"
				+ "		if(##(tes[0].test)) return ##(tes[0].i);\n"
				+ "		else if(##(tes[n-1].test)) return ##(tes[n-1].i);\n"
				+ "		##If(n>2)\n"
				+ "			##ForEach(tes[1:n-1])\n"
				+ "				else if(##(_.test)) return ##(_.i);\n"
				+ "	}\n"
				+ "Which[a==1,a;b==2,b];"; 
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
	
	public class Foo{
		
		public void main(final String[] args){
			
		}
		
		public Boolean foo(List<Integer> l){
			l.stream()
			.filter(i -> i > 10).allMatch(i -> i % 2 == 0);
			
			return false;
			
			
			
		}
	}

}

