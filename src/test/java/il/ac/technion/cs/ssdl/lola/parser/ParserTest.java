package il.ac.technion.cs.ssdl.lola.parser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.junit.Ignore;
import org.junit.Test;
import org.python.util.PythonInterpreter;

import il.ac.technion.cs.ssdl.lola.parser.builders.$replace;
import il.ac.technion.cs.ssdl.lola.parser.builders.AST.Node;
import il.ac.technion.cs.ssdl.lola.parser.builders.Elaborator;
import il.ac.technion.cs.ssdl.lola.parser.builders.GeneratingKeyword;
import il.ac.technion.cs.ssdl.lola.parser.builders.HostToken;
import il.ac.technion.cs.ssdl.lola.parser.builders.TriviaToken;
import il.ac.technion.cs.ssdl.lola.parser.lexer.Token;
import il.ac.technion.cs.ssdl.lola.parser.tokenizer.Tokenizer;
import il.ac.technion.cs.ssdl.lola.util.auxz;
/**
 * Unit tests for {@link Parser}
 * 
 * @author Ori Marcovitch
 * @since 2016
 */
public class ParserTest {
	static Parser parser;

	@Test
	public void pyTest() {
		final PythonInterpreter pi = new PythonInterpreter();
		pi.exec("x = [1,2,3]");
		pi.exec("class dummy(object):\n\tpass");
		pi.exec("");
	}

	@Test
	public void test$example() throws IOException {
		final Reader stream = new StringReader("##Find ##Literal(lit) ##replace 7 ##example \"omg\" ##resultsIn 7");
		parser = new Parser(stream);
		parser.parse();
	}

	@Test
	public void test$Literal() throws IOException {
		final Reader stream = new StringReader("##Find ##Literal(lit) ##replace 7\n \"omg\"");
		parser = new Parser(stream);
		auxz.assertTEquals("7", auxz.list2string(parser.parse()));
	}

	@Test
	public void test$OneOrMore() throws IOException {
		final Reader stream = new StringReader(
				"##Find ##OneOrMore ##Literal(lit)\n\t; ##replace ##ForEach(lits) ##(_)7\n \"omg\" \"ooo\";");
		parser = new Parser(stream);
		auxz.assertTEquals("\"omg\"7 \"ooo\"7", auxz.list2string(parser.parse()));
	}

	@Test
	public void testAnchor() {
		try {
			auxz.runStringTest("##Find #anchored b #to\n\t##anchor #anchored\n#anchored 77");
			fail("RuntimeError wasn't thrown!");
		} catch (final RuntimeException e) {
			assertEquals("Anchor Error: while matching to [#anchored]", e.getMessage());
		}
	}

	@Test
	public void testAnchoredVisitor() {
		try {
			auxz.runStringTest("##Import nPatterns.rangeBasedCase\n #Case x #of 1-7 #then 8 #then",
					"#Case x #of 1-7 # then 8 #then");
			fail("RuntimeError wasn't thrown!");
		} catch (final RuntimeException e) {
			auxz.assertTEquals("Anchor Error: while matching to [#Case x #of 1-7 # then 8 #then]", e.getMessage());
		}
	}

	@Test
	public void testAnyMatchesOnlyBalanced() throws IOException {
		final Reader stream = new StringReader(
				"##Find 1 ##Any 2 ##replace replaced\n##example 1(this)2 1((not this)2 1 this 2\n##resultsIn replaced 1((not this)2 replaced");
		parser = new Parser(stream);
		parser.parse();
	}

	@Test
	public void testBugsHuge() throws IOException {
		final Reader stream = new StringReader(//
				"##Find sizeof ##Sequence Bugs\n" + //
						"                   ?huge ##replace ##If(random() >= 0.5) 42" + //
						"										  ##else 6 *" + //
						"											7");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		// printAST(directives.get(0).keyword);
		assertEquals(directives.size(), 1);
		final Node dir = directives.get(0).keyword;
		assertEquals(dir.snippet(), null);
		assertEquals(dir.list().size(), 2);
		assertEquals(((HostToken) dir.list().get(0)).getText(), "sizeof");
	}

	@Test
	public void testCaseOfRangeThen1() {
		auxz.runStringTest("##Find(Range) ##Literal(begin)-##Literal(end)\n"
				+ "##Find  #Case ##Identifier(c) ##Sequence(option) #of ##Range(r) #then ##Literal(exp) #otherwise ##Literal(oexp) #done; ##replace ##(option.r.begin)*##(option.r.end)\n"
				+ "##example int kind= #Case c #of 'a'-'z' #then 1 #otherwise 2 #done; ##resultsIn int kind= 'a'*'z'");
		auxz.runStringTest("##Find(Range) ##Literal(begin)-##Literal(end)\n"
				+ "##Find  #Case ##Identifier(c) ##Sequence(option) #of ##Range(r) #then ##Literal(exp) #otherwise ##Literal(oexp) #done; ##replace ##(option.r.begin)*##(option.r.end)\n"
				+ "##example int kind= #Case c #of '0'-'9' #then 1 #otherwise 2 #done; ##resultsIn int kind= '0'*'9'");
	}

	@Test
	public void testCharIsLiteral() {
		auxz.runStringTest("##Find ##Literal(lit) ##replace replaced\n##example 'a' 'b'\n##resultsIn replaced replaced");
	}

	@Test
	public void testFindAppend() throws IOException {
		final Reader stream = new StringReader("##Find 1 ##append 3\n" + "2 1 1");
		parser = new Parser(stream);
		assertEquals("2 1 3 1 3", auxz.list2string(parser.parse()));
	}

	@Test
	public void testFindDelete() throws IOException {
		final Reader stream = new StringReader("##Find 1\n##delete \n 1 2 1");
		parser = new Parser(stream);
		assertEquals(" 2 ", auxz.list2string(parser.parse()));
	}

	@Test
	public void testFindPrepend() throws IOException {
		final Reader stream = new StringReader("##Find 1 ##prepend 3\n" + "2 1 1");
		parser = new Parser(stream);
		auxz.assertTEquals("2 31 31", auxz.list2string(parser.parse()));
	}

	@Test
	public void testFindReplaceParsing() throws IOException {
		final Reader stream = new StringReader("##Find 2 ##replace 3\n" + "2 2 2");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		assertEquals(directives.size(), 1);
		assertEquals(directives.get(0).keyword.elaborators().get(0).getClass(), $replace.class);
		assertEquals(directives.get(0).keyword.elaborators().get(0).list().size(), 2);
	}

	@Test
	public void testFindReplaceReplacement() throws IOException {
		final Reader stream = new StringReader("##Find 2 ##replace 3\n" + "2 2 2");
		parser = new Parser(stream);
		assertEquals(" 3  3  3", auxz.list2string(parser.parse()));
	}

	@Test
	public void testFindReplaceReplacementTwoFinds() throws IOException {
		final Reader stream = new StringReader("##Find 1 ##replace 3\n" + "##Find 2 ##replace 4\n" + "2 1 2");
		parser = new Parser(stream);
		auxz.assertTEquals("4 3 4", auxz.list2string(parser.parse()));
	}

	@Ignore
	@Test
	public void testFluentList() throws IOException {
		try (final Reader stream = new FileReader(
				new File("./src/il/ac/technion/cs/ssdl/lola/parser/tests/fluentList.lola"))) {
			parser = new Parser(stream);
			auxz.assertTEquals("", auxz.list2string(parser.parse()));
		}
	}

	@Test
	public void testForEach() throws IOException {
		try (final Reader stream = new StringReader("##ForEach([x for x in xrange(1,42)]) 42")) {
			parser = new Parser(stream);
			final List<Lexi> directives = parser.directives();
			assertEquals(directives.size(), 1);
			final Node dir = directives.get(0).keyword;
			assertEquals(dir.snippet().getText(), "([x for x in xrange(1,42)])");
			assertEquals(dir.elaborators().size(), 0);
			assertEquals(dir.list().size(), 2);
			assertEquals(((TriviaToken) dir.list().get(0)).getText(), " ");
			assertEquals(((HostToken) dir.list().get(1)).getText(), "42");
		}
	}

	@Test
	public void testForEachGenration() throws IOException {
		final Reader stream = new StringReader("##Find 4 ##replace ##ForEach([1,2,3]) ##(_) \n4");
		parser = new Parser(stream);
		auxz.assertTEquals(" 1  2 3 ", auxz.list2string(parser.parse()));
	}

	@Test
	public void testForEachGenrationWithIfNone() throws IOException {
		final Reader stream = new StringReader("##Find 4 ##replace ##ForEach([]) ##(_) ##ifNone 8\n4");
		parser = new Parser(stream);
		auxz.assertTEquals("  8", auxz.list2string(parser.parse()));
	}

	@Test
	public void testForEachGenrationWithSeparator() throws IOException {
		final Reader stream = new StringReader("##Find 4 ##replace ##ForEach([1,2,3]) ##(_) ##separator ,\n4");
		parser = new Parser(stream);
		auxz.assertTEquals("  1 , 2 , 3", auxz.list2string(parser.parse()));
	}

	@Test
	public void testForEachIfNone() throws IOException {
		final Reader stream = new StringReader("##ForEach([x for x in xrange(1,42)]) 42 ##ifNone 24");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		assertEquals(directives.size(), 1);
		final Node dir = directives.get(0).keyword;
		assertEquals(dir.snippet().getText(), "([x for x in xrange(1,42)])");
		assertEquals(dir.list().size(), 3);
		assertEquals(((TriviaToken) dir.list().get(0)).getText(), " ");
		assertEquals(((HostToken) dir.list().get(1)).getText(), "42");
		assertEquals(((TriviaToken) dir.list().get(0)).getText(), " ");
		// check elaborator
		assertEquals(1, dir.elaborators().size());
		final Elaborator e = dir.elaborators().get(0);
		assertEquals(e.name(), "$ifNone");
		assertEquals(e.list().size(), 2);
		assertEquals(((TriviaToken) e.list().get(0)).getText(), " ");
		assertEquals(((HostToken) e.list().get(1)).getText(), "24");
	}

	@Test
	public void testForEachIfNoneSeparator() throws IOException {
		final Reader stream = new StringReader("##ForEach([x for x in xrange(1,42)]) 42 ##ifNone 24 ##separator ,");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		assertEquals(directives.size(), 1);
		final Node dir = directives.get(0).keyword;
		assertEquals(dir.snippet().getText(), "([x for x in xrange(1,42)])");
		assertEquals(dir.list().size(), 3);
		assertEquals(((TriviaToken) dir.list().get(0)).getText(), " ");
		assertEquals(((HostToken) dir.list().get(1)).getText(), "42");
		assertEquals(((TriviaToken) dir.list().get(0)).getText(), " ");
		// check elaborator
		assertEquals(2, dir.elaborators().size());
		Elaborator e = dir.elaborators().get(0);
		assertEquals(e.name(), "$ifNone");
		assertEquals(3, e.list().size());
		assertEquals(((TriviaToken) e.list().get(0)).getText(), " ");
		assertEquals(((HostToken) e.list().get(1)).getText(), "24");
		assertEquals(((TriviaToken) e.list().get(2)).getText(), " ");
		e = dir.elaborators().get(1);
		assertEquals(e.name(), "$separator");
		assertEquals(((HostToken) e.list().get(0)).getText(), ",");
	}

	@Test
	public void testIdentifierReplaceWith$() throws IOException {
		final Reader stream = new StringReader("##Find ##Identifier(id) ##replace ##(id) ##example x ##resultsIn x");
		parser = new Parser(stream);
		parser.parse();
	}

	@Test
	public void testIdentifierReplaceWith2$() throws IOException {
		final Reader stream = new StringReader(
				"##Find ##Identifier(id) ##replace ##(id) ##(id) ##example x ##resultsIn x x");
		parser = new Parser(stream);
		parser.parse();
	}

	@Test
	public void testIfElse() throws IOException {
		final Reader stream = new StringReader("##If(x==7) 42 ##else 6*7");
		parser = new Parser(stream);
		assertEquals(parser.directives().size(), 1);
	}

	@Test
	public void testIfElseExecution1() throws IOException {
		final Reader stream = new StringReader("##If(3 + 3 == 6) 6 ##else 7\n4 2");
		parser = new Parser(stream);
		auxz.assertTEquals(" 6 4 2", auxz.list2string(parser.parse()));
	}

	@Test
	public void testIfElseExecution2() throws IOException {
		final Reader stream = new StringReader("##If(3 + 2 == 6) 6 ##else 7\n4 2");
		parser = new Parser(stream);
		auxz.assertTEquals(" 7\n4 2", auxz.list2string(parser.parse()));
	}

	@Test
	public void testIfElseExecution3() throws IOException {
		final Reader stream = new StringReader("##If(3 + 2 == 6) 6 ##elseIf(7 == 9) 7 ##else 2\n4 2");
		parser = new Parser(stream);
		auxz.assertTEquals(" 2\n4 2", auxz.list2string(parser.parse()));
	}

	@Test
	public void testIfElseIfElse() throws IOException {
		final Reader stream = new StringReader("##If(x==7) 42 ##elseIf(x==4) 6*7 ##else 3*14");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		assertEquals(directives.size(), 1);
		final Node dir = directives.get(0).keyword;
		assertEquals(dir.snippet().getText(), "(x==7)");
		assertEquals(dir.elaborators().size(), 2);
		Elaborator e = dir.elaborators().get(0);
		assertEquals(e.name(), "$elseIf");
		assertEquals(e.elaborators().size(), 0);
		assertEquals(5, e.list().size());
		assertEquals(((TriviaToken) e.list().get(0)).getText(), " ");
		assertEquals(((HostToken) e.list().get(1)).getText(), "6");
		assertEquals(((HostToken) e.list().get(2)).getText(), "*");
		assertEquals(((HostToken) e.list().get(3)).getText(), "7");
		assertEquals(((TriviaToken) e.list().get(4)).getText(), " ");
		e = dir.elaborators().get(1);
		assertEquals(e.name(), "$else");
		assertEquals(e.list().size(), 4);
		assertEquals(((TriviaToken) e.list().get(0)).getText(), " ");
		assertEquals(((HostToken) e.list().get(1)).getText(), "3");
		assertEquals(((HostToken) e.list().get(2)).getText(), "*");
		assertEquals(((HostToken) e.list().get(3)).getText(), "14");
	}

	@Test
	public void testIncludeExecution() throws IOException {
		final String fileName = "file.testInclude";
		final PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.println("host1 host2");
		writer.close();
		final Reader stream = new StringReader("##Include \"file.testInclude\"");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		assertEquals(directives.size(), 1);
		final GeneratingKeyword include = (GeneratingKeyword) directives.get(0).keyword;
		final Reader r = new StringReader(include.generate(null));
		final Tokenizer tokenizer = new Tokenizer(r);
		Token token = tokenizer.next_token();
		assertEquals("host1", token.text);
		token = tokenizer.next_token();
		assertEquals(" ", token.text);
		token = tokenizer.next_token();
		assertEquals("host2", token.text);
		new File(fileName).delete();
	}

	@Test
	public void testIncludeIncludexecution() throws IOException {
		final String fileName1 = "file.testInclude";
		final String fileName2 = "file2.testInclude";
		PrintWriter writer = new PrintWriter(fileName1, "UTF-8");
		writer.print("##Include \"file2.testInclude\"");
		writer.close();
		writer = new PrintWriter(fileName2, "UTF-8");
		writer.println("host1 host2");
		writer.close();
		final Reader stream = new StringReader("##Include \"file.testInclude\"");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		assertEquals(directives.size(), 1);
		final GeneratingKeyword include = (GeneratingKeyword) directives.get(0).keyword;
		final Reader r = new StringReader(include.generate(null));
		final Tokenizer tokenizer = new Tokenizer(r);
		Token token = tokenizer.next_token();
		assertEquals("##Include", token.text);
		token = tokenizer.next_token();
		assertEquals(" ", token.text);
		token = tokenizer.next_token();
		assertEquals("\"file2.testInclude\"", token.text);
		new File(fileName1).delete();
		new File(fileName2).delete();
	}

	@Test
	public void testIncludeParsing() throws IOException {
		final Reader stream = new StringReader("##Include \"file.testInclude\"");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		assertEquals(directives.size(), 1);
		assertEquals(directives.get(0).keyword.snippet().getText(), "\"file.testInclude\"");
	}

	@Ignore
	@Test
	public void testLibs() {
		for (final File ¢ : FileUtils.listFiles(new File("./lola_libs"), new RegexFileFilter(".*\\.lola"),
				DirectoryFileFilter.DIRECTORY))
			auxz.runFileTest(¢);
	}

	@Test
	public void testLiteralReplaceWith$() throws IOException {
		final Reader stream = new StringReader("##Find ##Literal(lit) ##replace ##(lit) ##example 7 ##resultsIn 7");
		parser = new Parser(stream);
		parser.parse();
	}

	@Test
	public void testLiteralReplaceWith2$() throws IOException {
		final Reader stream = new StringReader(
				"##Find ##Literal(lit) ##replace ##(lit) ##(lit) \n##example 7 ##resultsIn 7 7");
		parser = new Parser(stream);
		parser.parse();
	}

	@Test
	public void testNullConditionalMemberAccess() throws IOException {
		auxz.runLibTest("operators/optionalSign");
	}

	@Test
	public void testOneOrMoreRanges() {
		auxz.runStringTest("##Find(Range) ##Literal(begin)-##Literal(end)\n"
				+ "##Find ##OneOrMore ##Sequence(option) ##Range(r) ##replace ##ForEach(options) ##(_.r.begin)"
				+ "##example 'a'-'b' 'c'-'d' ##resultsIn 'a' 'c'");
	}

	@Test
	public void testOneOrMoreRangesOpenerCloser() {
		auxz.runStringTest("##Find(Range) ##Literal(begin)-##Literal(end)\n"
				+ "##Find ##OneOrMore ##Range(r) ##opener d ##closer d ##replace ##ForEach(rs) ##(_.begin)"
				+ "\n##example d 'a'-'b'  'c'-'d' d ##resultsIn 'a' 'c'");
	}

	@Test
	public void testRange() {
		auxz.runStringTest(
				"##Find(Range) ##Literal(begin)-##Literal(end) ##replace ##(begin)*##(end)\n##example 'a'-'b'\n##resultsIn 'a'*'b'");
		auxz.runStringTest(
				"##Find(Range) ##Literal(begin)-##Literal(end) ##replace ##(begin)*##(end)\n##example 1-7\n##resultsIn 1*7");
	}

	@Test
	public void testRun() throws IOException {
		final Reader stream = new StringReader("##Find 1 ##run{if 'x' not in locals():\n\t x = 0\nelse:\n\tx +=1}\n1 1 1");
		parser = new Parser(stream);
		auxz.assertListEquals(parser.parse(), new ArrayList<>(Arrays.asList(new String[]{"1", " ", "1", " ", "1"})));
	}

	@Test
	public void testSequence() throws IOException {
		final Reader stream = new StringReader("##Sequence(identifier) 1 ##followedBy 2 ##followedBy 3");
		parser = new Parser(stream);
		final List<Lexi> directives = parser.directives();
		assertEquals(directives.size(), 1);
		final Node dir = directives.get(0).keyword;
		assertEquals(dir.snippet().getText(), "(identifier)");
		assertEquals(dir.list().size(), 1);
		assertEquals(((HostToken) dir.list().get(0)).getText(), "1");
		// check elaborator
		assertEquals(2, dir.elaborators().size());
		// first
		Elaborator e = dir.elaborators().get(0);
		assertEquals(e.name(), "$followedBy");
		assertEquals(1, e.list().size());
		assertEquals(((HostToken) e.list().get(0)).getText(), "2");
		// second
		e = dir.elaborators().get(1);
		assertEquals(e.name(), "$followedBy");
		assertEquals(1, e.list().size());
		assertEquals(((HostToken) e.list().get(0)).getText(), "3");
	}

	@Test
	public void testSimpleExample() throws IOException {
		auxz.runLibTest("examples/SimpleExample");
	}

	@Test
	public void testSquared() throws IOException {
		auxz.runLibTest("examples/squared");
	}

	@Test
	public void testSquared0() {
		auxz.runStringTest("##Find squared ##Literal(f)\n	##replace ##(f * f)\ndouble sixteen = squared squared 2.0;",
				"double sixteen = 16.0;");
	}

	@Test
	public void testStritch() throws IOException {
		final Reader stream = new FileReader(new File("./lola_libs/examples/stritch.lola"));
		parser = new Parser(stream);
		auxz.assertTEquals("if(str.equals(\"omg\")){ print(7);} if(str.equals(\"ooo\")){ print(8);}",
				auxz.list2string(parser.parse()));
	}

	@Test
	public void testTwoSeparatedAny() {
		auxz.runStringTest("##Find ##Any b c ##Any d\n ##replace \n7 b c 7 d", "");
	}

	@Test
	public void testUnbalancedAnyMatchesEverything() throws IOException {
		auxz.runStringTest(
				"##Find 1 ##Unbalanced ##Any 2 ##replace replaced\n##example 1(this)2 1((not this)2 1 this 2\n##resultsIn replaced replaced replaced");
	}

	@Test
	public void testUserDefinedFind() throws IOException {
		auxz.runStringTest("##Find(Lol) {##OneOrMore lol\n\t} \n##Find ##Lol ##replace hah\n{lol lol lol}", "hah");
	}

	@Test
	public void testUserDefinedRange() {
		auxz.runStringTest(
				"##Find(Range) ##Literal(begin)-##Literal(end)\n##Find ##Range(r) ##replace ##(r.begin)*##(r.end)\n##example here: 'a'-'b' ##resultsIn here: 'a'*'b'");
	}

	@Test
	public void a() {
		auxz.runStringTest(
				"##Find\n ##NoneOrMore ##Identifier(entry)\n		##separator ,\n		##closer ;\n ##delete\n\na,b,c;", "");
	}
}
