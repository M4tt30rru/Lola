package il.ac.technion.cs.ssdl.lola.testcase.simple;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Range {
	@Test
	public void f0r() {
		auxz.runStringTest(
				"" //
						+ "##Find for(##Identifier(id) = ##Literal(_from) .. ##Literal(_to))\n" //
						+ " ##replace for(int ##(id) = ##(_from); ##(id) <= ##(_to); ++##(id))\n" //
						+ "for(i = 1 .. 100)", //
				"for(int i = 1; i <= 100; ++i)");
	}
}
