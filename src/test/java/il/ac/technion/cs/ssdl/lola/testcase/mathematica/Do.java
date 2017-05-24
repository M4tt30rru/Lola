package il.ac.technion.cs.ssdl.lola.testcase.mathematica;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Do {
	@Test
	public void do1() {
		auxz.runStringTest(
				"" //
						+ "##Find do[##Any(_e),{##Identifier(_i),##Literal(_max)}];\n" //
						+ " ##replace for(int ##(_i)=0;##(_i)<##(_max);++##(_i)) {##(_e);}\n" //
						+ "do[f(i);g(i * 2),{i,100}];", //
				"for(int i=0;i<100;++i) {f(i);g(i * 2);}");
	}

	@Test
	public void do2() {
		auxz.runStringTest(
				"" //
						+ "##Find do[##Any(_e),{##Identifier(_i),##Literal(_min),##Literal(_max),##Literal(_step)}];\n" //
						+ " ##replace for(int ##(_i)=##(_min);##(_i)<=##(_max);##(_i)+=##(_step)) {##(_e);}\n" //
						+ "do[f(i);g(i * 2),{i,10,100,5}];", //
				"for(int i=10;i<=100;i+=5) {f(i);g(i * 2);}");
	}

	@Test
	public void do3() {
		auxz.runStringTest(
				"" //
						+ "##Find do[##Any(_e),{##Any(_i),##Any(_list)}];\n" //
						+ " ##replace for(##(_i):##(_list)) {##(_e);}\n" //
						+ "do[f(o),{MyObject o,myOs}];", //
				"for(MyObject o:myOs) {f(o);}");
	}

	@Test
	public void do4() {
		auxz.runStringTest(
				"" //
						+ "##Find do[##Any(_e),{##Literal(_times)}];\n" //
						+ " ##run {\n" //
						+ "if 'x' not in locals():\n" //
						+ " x=0\n" //
						+ "else:\n" //
						+ " x=x+1\n" //
						+ "_doName='_i'+str(x)\n" //
						+ " }\n" //
						+ " ##replace for(int ##(_doName)=0;##(_doName)<##(_times);++##(_doName)) {##(_e);}\n" //
						+ "do[f(),{5}];\n" //
						+ "do[g(),{15}];" //
				,
				"" + //
						"for(int _i0=0;_i0<5;++_i0) {f();}\n" + //
						"for(int _i1=0;_i1<15;++_i1) {g();}");
	}
	
	@Test
	public void do5() {
		auxz.runStringTest(
				"" //
						+ "##Find do[##Any(_e),{##Literal(_times)}];\n" //
						+ " ##run {\n" //
						+ "if 'x' not in locals():\n" //
						+ " x=0\n" //
						+ "else:\n" //
						+ " x=x+1\n" //
						+ "_doName='_i'+str(x)\n" //
						+ " }\n" //
						+ " ##replace for(int ##(_doName)=0;##(_doName)<##(_times);++##(_doName)) {##(_e);}\n" //
						+ "public void foo(){"
						+ "do[f(),{5}];\n" //
						+ "do[g(),{15}];"
						+ "}" //
				,
				"" + //
						"public void foo(){" //
						+ "for(int _i0=0;_i0<5;++_i0) {f();}\n" // 
						+ "for(int _i1=0;_i1<15;++_i1) {g();}" //
						+ "}");
	}
}
