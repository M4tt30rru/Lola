package il.ac.technion.cs.ssdl.lola.testcase.simple;
import java.io.IOException;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Examples {
	@Test
	public void sten1() {
		auxz.runStringTest(
				"" //
						+ "##Find(BOpen) {\n" //
						+ "##Find(BClose) }\n" //
						+ "##Find ##BOpen ##BClose\n" //
						+ " ##replace replaced\n" //
						+ "{}", //
				"" //
						+ "replaced");
	}

	@Test
	public void sten2() {
		auxz.runStringTest("" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##POpen ##Any(_args) ##PClose\n" //
				+ " ##replace [##(_args)]\n" //
				+ "(int argc, char **argv)", "[int argc, char **argv]");
	}

	@Test
	public void sten3() {
		auxz.runStringTest("" //
				+ "##Find(BOpen) {\n" //
				+ "##Find(BClose) }\n" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##POpen ##Any(_args) ##PClose ##BOpen ##BClose\n" //
				+ " ##replace [##(_args)] ()\n" //
				+ "(int argc, char **argv) {}", "[int argc, char **argv] ()");
	}

	@Test
	public void sten4() {
		auxz.runStringTest("" //
				+ "##Find(BOpen) {\n" //
				+ "##Find(BClose) }\n" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##POpen ##Any(_args) ##PClose ##BOpen ##Any(_statements) ##BClose\n" //
				+ " ##replace replaced\n" //
				+ "(int argc, char **argv) {f();}", "replaced");
	}

	@Test
	public void sten5() {
		auxz.runStringTest("" //
				+ "##Find(BOpen) {\n" //
				+ "##Find(BClose) }\n" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##POpen ##Any(_args) ##PClose ##BOpen ##Any(_c1) ##POpen # ##PClose ##Any(_c2) ##BClose\n" //
				+ " ##replace replaced\n" //
				+ "(int argc, char **argv) {(#)}", "replaced");
	}

	@Test
	public void sten6() {
		auxz.runStringTest("" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find\n" //
				+ " ##NoneOrMore ##Identifier(_i)\n" //
				+ "  ##separator ,\n" //
				+ " ##replace replaced\n" //
				+ "(argc,argv)", "(replaced)");
	}

	@Test
	public void sten7() {
		auxz.runStringTest("" //
				+ "##Find(Type) ##Any\n" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find\n" //
				+ " ##NoneOrMore ##Type(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##replace replaced\n" //
				+ "(argc,argv)", "(replaced)");
	}

	@Test
	public void max1() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find twice ##Literal(x)\n" //
						+ " ##replace ##(x * 2)\n" //
						+ "twice 1", //
				"2" //
		);
	}

	@Test
	public void max2() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find ##Unbalanced ##Any twice ##Literal(x)\n" + " ##replace ##(x * 2)\n" //
						+ "(twice 1", //
				"2" //
		);
	}

	@Test
	public void max3() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find ##Unbalanced ##Any twice ##Literal(x)\n" + " ##replace ##(x * 2)\n" //
						+ "(twice 1 + 1", //
				"2 + 1" //
		);
	}

	@Test
	public void max4() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find max ##Literal(x)\n" //
						+ " ##replace (##(x) > ##(x) ? ##(x) : ##(x))\n" //
						+ "max 1", //
				"(1 > 1 ? 1 : 1)" //
		);
	}

	@Test
	public void max5() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find max(##Literal(x),##Literal(y))\n" //
						+ " ##replace (##(x) > ##(y) ? ##(x) : ##(y))\n" //
						+ "max(1,2)", //
				"(1 > 2 ? 1 : 2)" //
		);
	}

	@Test
	public void max6() throws IOException {
		auxz.runStringTest(
				"" + "##Find(NoCommasExpression)\n" //
						+ " ##Match ##Any ##exceptFor ##Any, ##Any\n" //
						+ "##Find(Expression)\n" //
						+ " ##NoCommasExpression\n" //
						+ "##Find ##Expression(x)\n" //
						+ " ##replace replaced\n" //
						+ "X", //
				"replaced" //
		);
	}

	@Test
	public void ran1() {
		auxz.runStringTest(
				"" //
						+ "##Find ##Literal(_from) ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "1 2", //
				"replaced");
	}

	@Test
	public void ran2() {
		auxz.runStringTest(
				"" //
						+ "##Find ##Literal(_from) .. ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "1 .. 2", //
				"replaced");
	}

	@Test
	public void ran3() {
		auxz.runStringTest(
				"" //
						+ "##Find ##Identifier(id) ##Literal(_from) .. ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "X 1 .. 2", //
				"replaced");
	}

	@Test
	public void ran4() {
		auxz.runStringTest(
				"" //
						+ "##Find ##Identifier(id) = ##Literal(_from) .. ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "X = 1 .. 2", //
				"replaced");
	}

	@Test
	public void ran5() {
		auxz.runStringTest(
				"" //
						+ "##Find for ##Identifier(id) = ##Literal(_from) .. ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "for X = 1 .. 2", //
				"replaced");
	}

	@Test
	public void ran6() {
		auxz.runStringTest(
				"" //
						+ "##Find for(##Identifier(id) = ##Literal(_from) .. ##Literal(_to))\n" //
						+ " ##replace replaced\n" //
						+ "for(X = 1 .. 2)", //
				"replaced");
	}
}
