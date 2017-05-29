package il.ac.technion.cs.ssdl.lola.testcase.stenography.refine;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import il.ac.technion.cs.ssdl.lola.parser.builders.$Import;
import il.ac.technion.cs.ssdl.lola.util.auxz;
@RunWith(Parameterized.class)
public class Stenography {
	protected static final int STENOGRAPHY_DEFINED_TESTS = 6;
	protected static final int SIMPLE_DEFINED_TESTS = 3;
	protected final String fileName;

	@Test
	public void compare() {
		auxz.runResourceTest(fileName);
	}

	public Stenography(final String fileName, final String testName) {
		this.fileName = fileName;
	}

	@Parameters(name = "{index}. {1}") //
	public static Collection<Object[]> data() {
		final List<Object[]> $ = new LinkedList<>();
		for (int i = 1; i <= STENOGRAPHY_DEFINED_TESTS; ++i)
			$.add(new Object[]{"stenography/sten" + i, "sten" + i});
		for (int i = 1; i <= SIMPLE_DEFINED_TESTS; ++i)
			$.add(new Object[]{"simple/simp" + i, "simp" + i});
		return $;
	}

	@Before
	public void setImportPrefix() {
		$Import.setPrefix("src/test/resources/");
	}
}
