package il.ac.technion.cs.ssdl.lola.testcase.stenography.refine;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import il.ac.technion.cs.ssdl.lola.parser.builders.$Import;
import il.ac.technion.cs.ssdl.lola.util.auxz;
@RunWith(Parameterized.class)
public class Stenography {
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
		for (final String f : filesOf("simple", "stenography", "multitude"))
			$.add(new Object[]{f, f});
		$.sort(new Comparator<Object[]>() {
			@Override
			public int compare(Object[] o1, Object[] o2) {
				return ((String) o1[0]).compareTo((String) o2[0]);
			}
		});
		return $;
	}

	@Before
	public void setImportPrefix() {
		$Import.setPrefix("src/test/resources/");
	}

	private static Collection<String> filesOf(String... directories) {
		final Set<String> $ = new HashSet<>();
		for (final String d : directories)
			$.addAll(Arrays.stream(new File(ClassLoader.getSystemResource(d).getFile()).listFiles())
					.map(f -> FilenameUtils.removeExtension(d + "/" + f.getName())).collect(Collectors.toList()));
		return $;
	}
}
