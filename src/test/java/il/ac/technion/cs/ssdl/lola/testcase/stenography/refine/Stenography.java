package il.ac.technion.cs.ssdl.lola.testcase.stenography.refine;
import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;
public class Stenography {
	@Test
	public void stenography1() {
		auxz.runStringTest(
				"" //
						+ "##Find\n" //
						+ " const ##Identifier(_name) = ##Literal(_value);\n" //
						+ " ##run {\n" //
						+ "if isinstance(_value, str):\n" //
						+ " _type = 'String'\n" //
						+ "elif isinstance(_value, (int, long)):\n" //
						+ " _type = 'int'\n" //
						+ "else:\n" //
						+ " _type = 'double'\n" //
						+ " }\n" //
						+ " ##replace public static final ##(_type) ##(_name) = ##(_value);\n" //
						+ "const x = 1;\n" //
						+ "const y = \"1\";\n" //
						+ "const z = 1.0;",
				"" //
						+ "public static final int x = 1;\n" //
						+ "public static final String y = \"1\";\n" //
						+ "public static final double z = 1.0;");
	}

	@Test
	public void stenography2() {
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n" //
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n" //
						+ "##Find(Type)\n" //
						+ " ##Either ##Identifier ##or ##PrimitiveType\n" //
						+ " ##Optional <##Any>\n" //
						+ " ##NoneOrMore []\n" //
						+ "##Find\n" //
						+ " ##Type(_type) ##Identifier(_name) (##Any(_args)) = ##Any(_value);\n" //
						+ " ##replace ##(_type) ##(_name) (##(_args)) {return ##(_value);}\n" //
						+ "double sin(double x) = cos(x + pi/2);",
				"" //
						+ "double sin(double x) {\n" //
						+ " return cos(x + pi/2);\n" //
						+ "}");
	}

	@Test
	public void stenography3() {
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n" //
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n" //
						+ "##Find(Type)\n" //
						+ " ##Either ##Identifier(_name) ##or ##PrimitiveType(_name)\n" //
						+ " ##Optional <##Any>\n" //
						+ " ##NoneOrMore []\n" //
						+ "##Find(MethodDeclaration)\n" //
						+ " ##Type(_type) ##Identifier(_name) (##Any(_args))\n" //
						+ "##Find\n" //
						+ " ##MethodDeclaration(_dec) = default;\n" //
						+ " ##run {\n" //
						+ "__ = _dec._type._name.name.strip()\n" //
						+ "if __ == 'void':\n" //
						+ " _return = None\n" //
						+ "elif __ in ['int', 'short', 'byte']:\n" //
						+ " _return = '0'\n" //
						+ "elif __ in ['double', 'float']:\n" //
						+ " _return = '0.0'\n" //
						+ "else:\n" //
						+ " _return = 'null'\n" //
						+ " }\n" //
						+ " ##replace ##(_dec) {##('' if _return is None else 'return ' + _return + ';')}\n" //
						+ "double f() = default;\n" //
						+ "int g() = default;\n" //
						+ "T h() = default;\n" //
						+ "void k() = default;\n" //
						+ "",
				"" //
						+ "double f() {\n" //
						+ " return 0.0;\n" //
						+ "}\n" //
						+ "int g() {\n" //
						+ " return 0;\n" //
						+ "}\n" //
						+ "T h() {\n" //
						+ " return null;\n" //
						+ "}\n" //
						+ "void k() {\n" //
						+ "}");
	}

	@Test
	public void stenography4() {
		auxz.runStringTest("" //
				+ "##Find(PrimitiveType)\n" //
				+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n" //
				+ "##Find(Type)\n" //
				+ " ##Either ##Identifier(_name) ##or ##PrimitiveType(_name)\n" //
				+ " ##Optional <##Any>\n" //
				+ " ##NoneOrMore []\n" //
				+ "##Find(MethodDeclaration)\n" //
				+ " ##Type(_type) ##Identifier(_name) (##Any(_args))\n" //
				+ "##Find\n" //
				+ " ##MethodDeclaration(_dec) = ##Any(_value);\n" //
				+ " ##replace ##(_dec) {return ##(_value);}\n" //
				+ "T h(int x) = new T(x);", "T h(int x) {return new T(x);}");
	}

	@Test
	public void stenography5() {
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n" //
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n" //
						+ "##Find(Type)\n" //
						+ " ##Either ##Identifier(_name) ##or ##PrimitiveType(_name)\n" //
						+ " ##Optional <##Any>\n" //
						+ " ##NoneOrMore []\n" //
						+ "##Find(MethodDeclaration)\n" //
						+ " ##Type(_type) ##Identifier(_name) (##Any(_args))\n" //
						+ "##Find\n" //
						+ " ##MethodDeclaration(_dec) = throws ##Any(_value);\n" //
						+ " ##replace ##(_dec) {throw new ##(_value);}\n" //
						+ "void error(String message) = throws BadError(message);", //
				"void error(String message) {throw new BadError(message);}");
	}

	@Test
	public void stenography6() {
		auxz.runStringTest("" //
				+ "##Find(NoCommaExpression)\n" //
				+ " ##Match ##Any ##exceptFor ##Any, ##Any\n" //
				+ "##Find(Expression)\n" //
				+ " ##Either ##NoCommaExpression ##or (##Any)\n" //
				+ "##Find(PrimitiveType)\n" //
				+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n" //
				+ "##Find(Type)\n" //
				+ " ##Either ##Identifier(_name) ##or ##PrimitiveType(_name)\n" //
				+ " ##Optional <##Any>\n" //
				+ " ##NoneOrMore []\n" //
				+ "##Find(ArgumentDeclaration)\n" //
				+ " ##Type(_type) ##Identifier(_name)\n" //
				+ "##Find(DefaultableArgumentDeclaration)\n" //
				+ " ##Either ##ArgumentDeclaration(_dec) ##or ##ArgumentDeclaration(_dec) = ##Expression(_value)\n" //
				+ "##Find ##Identifier(_name) (##NoneOrMore ##DefaultableArgumentDeclaration(_dec) ##separator ,) = default;\n" //
				+ " ##replace ##ForEach([i for i, d in enumerate(_decs) if hasattr(d, '_value')] + [len(_decs)]) ##(_name) (##(','.join(d._dec.name for d in _decs[:_]))) {##(''.join('this.' + d._dec._name.name + '=' + d._dec._name.name + ';' for d in _decs[:_])) ##(''.join('this.' + d._dec._name.name + '=' + d._value.name + ';' for d in _decs[_:]))}\n" //
				+ "A(int a, int b, int c=1, int d=2) = default;", //
				"" //
						+ "A(int a, int b) {\n" //
						+ " this.a = a;\n" //
						+ " this.b = b;\n" //
						+ " this.c = 1;\n" //
						+ " this.d=2;\n" //
						+ "}\n" //
						+ "A(int a,int b,int c) {\n" //
						+ " this.a = a;\n" //
						+ " this.b = b;\n" //
						+ " this.c = c;\n" //
						+ " this.d = 2;\n" //
						+ "}\n" //
						+ "A(int a, int b, int c, int d) {\n" //
						+ " this.a = a;\n" //
						+ " this.b = b;\n" //
						+ " this.c = c;\n" //
						+ " this.d = d;\n" //
						+ "}");
	}
}
