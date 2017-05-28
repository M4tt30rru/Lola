package il.ac.technion.cs.ssdl.lola.util;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import il.ac.technion.cs.ssdl.lola.parser.Parser;
/**
 * Auxiliary utility for tests.
 * @author Ori Roth
 * @since May 24, 2017
 */
public enum auxz {
	;
	/**
	 * Equalize lists by item equalization.
	 * @param li1 @JD
	 * @param li2 @JD
	 */
	public static void assertListEquals(final List<?> li1, final List<?> li2) {
		assertEquals(li1.size(), li2.size());
		for (int ¢ = 0; ¢ < li1.size(); ++¢)
			assertEquals(li2.get(¢), li1.get(¢));
	}

	/**
	 * White-space oblivious strings equalization.
	 * @param s1 @JD
	 * @param s2 @JD
	 */
	public static void assertTEquals(final String s1, final String s2) {
		assertEquals(cleanSpaces(s1), cleanSpaces(s2));
	}

	/**
	 * @param s1 @JD
	 * @return it without white-spaces
	 */
	private static String cleanSpaces(final String s1) {
		return s1.replace(" ", "").replace("\n", "").replace("\t", "").replace("\r", "");
	}

	/**
	 * @param ss list of strings
	 * @return concatenation of the strings
	 */
	public static String list2string(final List<String> ss) {
		return ss.stream().reduce("", (s1, s2) -> s1 + s2);
	}

	/**
	 * Parse pure @Lola file.
	 * @param f @Lola file
	 */
	public static void runFileTest(final File f) {
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

	/**
	 * Parse pure @Lola file and compares with expected output. Both files should
	 * be present as test resources, @Lola file with ".lola" extension and
	 * expected output with ".out" extension.
	 * @param test @Lola/expected output file name
	 */
	public static void runResourceTest(final String test) {
		Reader stream;
		try {
			stream = new FileReader(ClassLoader.getSystemResource(test + ".lola").getFile());
			Parser parser = new Parser(stream);
			auxz.assertTEquals( //
					Files.lines(Paths.get(ClassLoader.getSystemResource(test + ".out").toURI())).reduce("",
							(a, b) -> a + "\n" + b),
					auxz.list2string(parser.parse()));
		} catch (final IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse pure @Lola file.
	 * @param fileName @Lola file name
	 */
	public static void runFileTest(final String fileName) {
		runFileTest(new File("./src/il/ac/technion/cs/ssdl/lola/parser/tests/" + fileName + ".lola"));
	}

	/**
	 * Parse pure @Lola file.
	 * @param fileName @Lola file name without extension inside lola_libs
	 *          directory
	 */
	public static void runLibTest(final String fileName) {
		runFileTest(new File("./lola_libs/" + fileName + ".lola"));
	}

	/**
	 * Parse pure @Lola code snippet.
	 * @param ¢ pure @Lola code snippet
	 */
	public static void runStringTest(final String ¢) {
		runStringTest(¢, "");
	}

	/**
	 * Parse pure @Lola code snippet, equalize output to expected result.
	 * @param ¢ @Lola code snippet
	 * @param result expected output
	 */
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
}
