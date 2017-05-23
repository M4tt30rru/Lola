package il.ac.technion.cs.ssdl.lola.util;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import il.ac.technion.cs.ssdl.lola.parser.Parser;
public enum auxz {
	;
	public static void assertListEquals(final List<?> li1, final List<?> li2) {
		assertEquals(li1.size(), li2.size());
		for (int ¢ = 0; ¢ < li1.size(); ++¢)
			assertEquals(li2.get(¢), li1.get(¢));
	}

	public static void assertTEquals(final String s1, final String s2) {
		assertEquals(cleanSpaces(s1), cleanSpaces(s2));
	}

	private static String cleanSpaces(final String s1) {
		return s1.replace(" ", "").replace("\n", "").replace("\t", "").replace("\r", "");
	}

	public static String list2string(final List<String> ss) {
		return ss.stream().reduce("", (s1, s2) -> s1 + s2);
	}

	public static void runFileTest(final File f) {
		// System.out.println("***" + f.getName());
		Reader stream;
		try {
			stream = new FileReader(f);
			Parser parser = new Parser(stream);
			auxz.assertTEquals("", auxz.list2string(parser.parse()));
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void runFileTest(final String fileName) {
		runFileTest(new File("./src/il/ac/technion/cs/ssdl/lola/parser/tests/" + fileName + ".lola"));
	}

	public static void runLibTest(final String fileName) {
		runFileTest(new File("./lola_libs/" + fileName + ".lola"));
	}

	public static void runStringTest(final String ¢) {
		runStringTest(¢, "");
	}

	public static void runStringTest(final String s, final String result) {
		final Reader stream = new StringReader(s);
		Parser parser = new Parser(stream);
		try {
			auxz.assertTEquals(result, auxz.list2string(parser.parse()));
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printList(final List<String> ss) {
		for (final String ¢ : ss)
			System.out.println(¢);
	}
}
