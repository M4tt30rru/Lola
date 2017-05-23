package il.ac.technion.cs.ssdl.lola.testcase.simple;
import java.io.IOException;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Max {
	@Test
	public void a() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find twice ##Literal(x)\n" //
						+ " ##replace ##(x * 2)\n" //
						+ "twice 1", //
				"2" //
		);
	}

	@Test
	public void b() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find ##Unbalanced ##Any twice ##Literal(x)\n" + " ##replace ##(x * 2)\n" //
						+ "(twice 1", //
				"2" //
		);
	}

	@Test
	public void c() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find ##Unbalanced ##Any twice ##Literal(x)\n" + " ##replace ##(x * 2)\n" //
						+ "(twice 1 + 1", //
				"2 + 1" //
		);
	}

	@Test
	public void d() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find max ##Literal(x)\n" //
						+ " ##replace (##(x) > ##(x) ? ##(x) : ##(x))\n" //
						+ "max 1", //
				"(1 > 1 ? 1 : 1)" //
		);
	}

	@Test
	public void e() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find max(##Literal(x),##Literal(y))\n" //
						+ " ##replace (##(x) > ##(y) ? ##(x) : ##(y))\n" //
						+ "max(1,2)", //
				"(1 > 2 ? 1 : 2)" //
		);
	}

	@Test
	public void f() throws IOException {
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
	public void max() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find(NoCommasExpression)\n" //
						+ " ##Match ##Any ##exceptFor ##Any, ##Any\n" //
						+ "##Find(Expression)\n" //
						+ " ##NoCommasExpression\n" // TODO: no `(#Any)` option
						+ "##Find max(##Expression(x),##Expression(y))\n" //
						+ " ##run {\n" //
						+ "x = '(' + str(x) + ')'\n" //
						+ "y = '(' + str(y) + ')'\n" //
						+ " }\n" //
						+ " ##replace (##(x) > ##(y) ? ##(x) : ##(y))\n" //
						+ "max(X,2)", //
				"((X) > (2) ? (X) : (2))" //
		);
	}
}
