package il.ac.technion.cs.ssdl.lola.testcase.simple.refine;
import java.io.IOException;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Max {
	// TODO Roth: open an issue on `##or (##Any)` problem, i.e. paranthesis
	// considered as snippet.
	@Test
	public void max() throws IOException {
		auxz.runStringTest(
				"" //
						+ "##Find(NoCommaExpression)\n" //
						+ " ##Match ##Any ##exceptFor ##Any, ##Any\n" //
						+ "##Find(Expression)\n" //
						+ "	##Either ##NoCommaExpression ##or (##Any)\n" //
						+ "##Find max(##Expression(x),##Expression(y))\n" //
						+ " ##run {\n" //
						+ "x = '(' + str(x) + ')'\n" //
						+ "y = '(' + str(y) + ')'\n" //
						+ " }\n" //
						+ " ##replace (##(x) > ##(y) ? ##(x) : ##(y))\n" //
						+ "max(X,max(Y,Z))", //
				"((X) > (((Y) > (Z) ? (Y) : (Z))) ? (X) : (((Y) > (Z) ? (Y) : (Z))))" //
		);
	}
}
