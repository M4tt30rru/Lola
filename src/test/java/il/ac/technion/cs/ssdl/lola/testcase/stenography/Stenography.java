package il.ac.technion.cs.ssdl.lola.testcase.stenography;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Stenography {
	@Test
	public void sten1() {
		auxz.runStringTest(
				"" //
						+ "##Find(BOpen) {\n" //
						+ "##Find(BClose) }\n" //
						+ "##Find ##BOpen ##BClose\n" //
						+ " ##replace replaced\n" //
						+ "{}", //
				"" //
						+ "replaced");
	}

	@Test
	public void sten2() {
		auxz.runStringTest("" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##POpen ##Any(_args) ##PClose\n" //
				+ " ##replace [##(_args)]\n" //
				+ "(int argc, char **argv)", "[int argc, char **argv]");
	}

	@Test
	public void sten3() {
		auxz.runStringTest("" //
				+ "##Find(BOpen) {\n" //
				+ "##Find(BClose) }\n" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##POpen ##Any(_args) ##PClose ##BOpen ##BClose\n" //
				+ " ##replace [##(_args)] ()\n" //
				+ "(int argc, char **argv) {}", "[int argc, char **argv] ()");
	}

	@Test
	public void sten4() {
		auxz.runStringTest("" //
				+ "##Find(BOpen) {\n" //
				+ "##Find(BClose) }\n" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##POpen ##Any(_args) ##PClose ##BOpen ##Any(_statements) ##BClose\n" //
				+ " ##replace replaced\n" //
				+ "(int argc, char **argv) {f();}", "replaced");
	}

	@Test
	public void sten5() {
		auxz.runStringTest("" //
				+ "##Find(BOpen) {\n" //
				+ "##Find(BClose) }\n" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##POpen ##Any(_args) ##PClose ##BOpen ##Any(_c1) ##POpen # ##PClose ##Any(_c2) ##BClose\n" //
				+ " ##replace replaced\n" //
				+ "(int argc, char **argv) {(#)}", "replaced");
	}

	@Test
	public void sten6() {
		auxz.runStringTest("" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find\n" //
				+ " ##NoneOrMore ##Identifier(_i)\n" //
				+ "  ##separator ,\n" //
				+ " ##replace replaced\n" //
				+ "(argc,argv)", "(replaced)");
	}

	@Test
	public void sten7() {
		auxz.runStringTest("" //
				+ "##Find(Type) ##Any\n" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find\n" //
				+ " ##NoneOrMore ##Type(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##replace replaced\n" //
				+ "(argc,argv)", "(replaced)");
	}

	@Test
	public void stenography1() {
		auxz.runStringTest("" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find(Type) ##Any\n" //
				+ "##Find (\n" //
				+ " ##NoneOrMore ##Type(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##PClose { ##Any(_c1) ##POpen # ##PClose ##Any(_c2) }\n" //
				+ " ##replace (##(','.join([_type.name + ' ' + _arg.name for _type, _arg in zip(_types, _args)]))) {##(_c1) (##(','.join(str(_arg) for _arg in _args))) ##(_c2)}\n" //
				+ "##example \n" //
				+ "void main(int argc, char** argv) {\n" //
				+ "  printf(\"Hello!\");\n" //
				+ "  do_main(#);" //
				+ "}\n" + "##resultsIn\n" //
				+ "void main (int argc, char** argv) {\n" //
				+ "  printf(\"Hello!\");\n" //
				+ "  do_main(argc, argv);" //
				+ "}");
	}
}
