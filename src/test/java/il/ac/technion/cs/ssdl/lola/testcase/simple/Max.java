package il.ac.technion.cs.ssdl.lola.testcase.simple;
import java.io.IOException;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Max {
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
