package il.ac.technion.cs.ssdl.lola.testcase.stenography;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Stenography {
	@Test
	public void stenography1() {
		auxz.runStringTest("" //
				+ "##Find(POpen) (\n" //
				+ "##Find(PClose) )\n" //
				+ "##Find (\n" //
				+ " ##NoneOrMore ##Identifier(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##PClose { ##Any(_c1) ##POpen # ##PClose ##Any(_c2) }\n" //
				+ " ##replace (##(','.join([_type.name + ' ' + _arg.name for _type, _arg in zip(_types, _args)]))) {##(_c1) (##(','.join(str(_arg) for _arg in _args))) ##(_c2)}\n" //
				+ "void main(Integer argc, String argv) {\n" //
				+ "  printf(\"Hello!\");\n" //
				+ "  do_main_silently(#);\n" //
				+ "}",
				"" //
						+ "void main(Integer argc, String argv) {\n" //
						+ "  printf(\"Hello!\");\n" //
						+ "  do_main_silently(argc, argv);\n" //
						+ "}");
	}

	@Test
	public void stenography2() {
		auxz.runStringTest(
				"" //
						+ "##Find(ConstAss) :=:\n" //
						+ "##Find ##Identifier(_type) ##Identifier(_name) ##ConstAss ##Any(_value);\n" //
						+ " ##replace static final ##(_type) ##(_name) = ##(_value);\n" //
						+ "class Cat {\n" //
						+ "  public Integer LEGS_COUNT :=: 4;\n" //
						+ "  private Integer SOULS_COUNT :=: 9;\n" //
						+ "  public void mew() {System.out.println(\"MEW\");}\n" //
						+ "}",
				"" //
						+ "class Cat {\n" //
						+ "  public static final Integer LEGS_COUNT = 4;\n" //
						+ "  private static final Integer SOULS_COUNT = 9;\n" //
						+ "  public void mew() {System.out.println(\"MEW\");}\n" //
						+ "}\n");
	}

	@Test
	public void stenography3() {
		auxz.runStringTest("" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##Identifier(_returnType) ##Identifier(_name) (\n" //
				+ " ##NoneOrMore ##Identifier(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##PClose = ##Any(_value);\n" //
				+ " ##replace ##(_returnType) ##(_name)(##(','.join([_type.name + ' ' + _arg.name for _type, _arg in zip(_types, _args)]))) {return ##(_value);}\n" //
				+ "public Integer answer(Question q) = 42;", //
				"public Integer answer(Question q) {return 42;}");
	}

	@Test
	public void stenography4() {
		auxz.runStringTest("" //
				+ "##Find(PClose) )\n" //
				+ "##Find ##Identifier(_returnType) ##Identifier(_name) (\n" //
				+ " ##NoneOrMore ##Identifier(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##PClose = throws ##Any(_value);\n" //
				+ " ##replace ##(_returnType) ##(_name)(##(','.join([_type.name + ' ' + _arg.name for _type, _arg in zip(_types, _args)]))) {throw new ##(_value)();}\n" //
				+ "public Coffe makeCoffe(Milk milk, List beans) = throws UnsupportedOperationException;\n",
				"" //
						+ "public Coffe makeCoffe(Milk milk, List beans) {throw new UnsupportedOperationException();}");
	}

	@Test
	public void stenography5() {
		auxz.runStringTest("" //
				+ "##Find(PClose) )\n" //
				+ "##Find(Arrow) ->\n" //
				+ "##Find ##Identifier(_returnType) ##Identifier(_name) (\n" //
				+ " ##NoneOrMore ##Identifier(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##PClose ##Arrow ##Any(_delegator);\n" //
				+ " ##replace ##(_returnType) ##(_name)(##(','.join([_type.name + ' ' + _arg.name for _type, _arg in zip(_types, _args)]))) {return ##(_delegator)(##(','.join([_arg.name for _arg in _args])));}\n" //
				+ "public Coffe makeCoffe(Milk milk, List beans) -> wife.make;\n",
				"" //
						+ "public Coffe makeCoffe(Milk milk, List beans) {return wife.make(milk, beans);}");
	}
}
