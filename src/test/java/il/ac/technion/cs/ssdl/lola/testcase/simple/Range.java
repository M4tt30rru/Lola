package il.ac.technion.cs.ssdl.lola.testcase.simple;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Range {
	@Test
	public void a() {
		auxz.runStringTest(
				"" //
						+ "##Find ##Literal(_from) ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "1 2", //
				"replaced");
	}

	@Test
	public void b() {
		auxz.runStringTest(
				"" //
						+ "##Find ##Literal(_from) .. ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "1 .. 2", //
				"replaced");
	}

	@Test
	public void c() {
		auxz.runStringTest(
				"" //
						+ "##Find ##Identifier(id) ##Literal(_from) .. ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "X 1 .. 2", //
				"replaced");
	}

	@Test
	public void d() {
		auxz.runStringTest(
				"" //
						+ "##Find ##Identifier(id) = ##Literal(_from) .. ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "X = 1 .. 2", //
				"replaced");
	}

	@Test
	public void e() {
		auxz.runStringTest(
				"" //
						+ "##Find for ##Identifier(id) = ##Literal(_from) .. ##Literal(_to)\n" //
						+ " ##replace replaced\n" //
						+ "for X = 1 .. 2", //
				"replaced");
	}

	@Test
	public void f() {
		auxz.runStringTest(
				"" //
						+ "##Find for(##Identifier(id) = ##Literal(_from) .. ##Literal(_to))\n" //
						+ " ##replace replaced\n" //
						+ "for(X = 1 .. 2)", //
				"replaced");
	}

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
