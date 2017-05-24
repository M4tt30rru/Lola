package il.ac.technion.cs.ssdl.lola.testcase.stenography;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Stenography {
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
				+ "  do_main_silently(#);" //
				+ "}\n" //
				+ "##resultsIn\n" //
				+ "void main(int argc, char** argv) {\n" //
				+ "  printf(\"Hello!\");\n" //
				+ "  do_main_silently(argc, argv);\n" //
				+ "}");
	}

	@Test
	public void stenography2() {
		auxz.runStringTest("" //
				+ "##Find(Type) ##Any\n" //
				+ "##Find(ConstAss) :=:" + "##Find ##Identifier(_type) #Type(_name) ##ConstAss ##Any(_value);\n" //
				+ " ##replace static final ##(_type) ##(_name) = ##(_value);\n" //
				+ "##example \n" //
				+ "class Cat {\n" //
				+ "  public int LEGS_COUNT :=: 4;\n" //
				+ "  private int SOULS_COUNT :=: 9;\n" //
				+ "  public void mew() {System.out.println(\"MEW\");}" //
				+ "}\n" //
				+ "##resultsIn\n" //
				+ "class Cat {\n" //
				+ "  public static final int LEGS_COUNT = 4;\n" //
				+ "  private static final int SOULS_COUNT = 9;\n" //
				+ "  public void mew() {System.out.println(\"MEW\");}\n" //
				+ "}\n");
	}

	@Test
	public void stenography3() {
		auxz.runStringTest("" //
				+ "##Find(PClose) )\n" //
				+ "##Find(Type) ##Any\n" //
				+ "##Find ##Type(_returnType) ##Identifier(_name) (\n" //
				+ " ##NoneOrMore ##Type(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##PClose = throws ##Any(_value);\n" //
				+ " ##replace ##(_returnType) ##(_name)(##(','.join([_type.name + ' ' + _arg.name for _type, _arg in zip(_types, _args)]))) {throw ##(_value);}\n" //
				+ "##example \n" //
				+ "public Coffe makeCoffe(Milk milk, List<Bean> beans) = throws new UnsupportedOperationException();\n"
				+ "##resultsIn\n" //
				+ "public Coffe makeCoffe(Milk milk, List<Bean> beans) {throw new UnsupportedOperationException();}");
	}

	@Test
	public void stenography4() {
		auxz.runStringTest("" //
				+ "##Find(PClose) )\n" //
				+ "##Find(Type) ##Any\n" //
				+ "##Find(Arrow) ->\n" //
				+ "##Find ##Type(_returnType) ##Identifier(_name) (\n" //
				+ " ##NoneOrMore ##Type(_type) ##Identifier(_arg)\n" //
				+ "  ##separator ,\n" //
				+ " ##PClose ##Arrow ##Any(_delegator);\n" //
				+ " ##replace ##(_returnType) ##(_name)(##(','.join([_type.name + ' ' + _arg.name for _type, _arg in zip(_types, _args)]))) {return ##(_delegator)(##(','.join([_arg.name for _arg in _args])));}\n" //
				+ "##example \n" //
				+ "public Coffe makeCoffe(Milk milk, List<Bean> beans) -> wife.make;\n"
				+ "##resultsIn\n" //
				+ "public Coffe makeCoffe(Milk milk, List<Bean> beans) {return wife.make(milk, beans);}");
	}
}
