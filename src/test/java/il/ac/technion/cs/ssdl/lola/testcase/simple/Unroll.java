package il.ac.technion.cs.ssdl.lola.testcase.simple;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Unroll {
	@Test
	public void unroll() {
		auxz.runStringTest(
				"" //
						+ "##Find unroll ##Identifier(_i) in ##Literal(_from) .. ##Literal(_to)\n" //
						+ "  ##replace for (int ##(_i)=##(_from);##(_i)<##(_to);++##(_i))\n" //
						+ "unroll i in 2 .. 22\n" //
						+ "  printf(\"Cool!\\n\");",
				""//
						+ "for (int i=2;i<22;++i)" //
						+ "  printf(\"Cool!\\n\");");
	}
}
