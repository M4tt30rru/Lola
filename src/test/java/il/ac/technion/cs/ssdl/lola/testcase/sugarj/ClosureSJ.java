package il.ac.technion.cs.ssdl.lola.testcase.sugarj;

import org.junit.Test;
import il.ac.technion.cs.ssdl.lola.util.auxz;

public class ClosureSJ {
	
	@Test
	public void closure1() {
		/**
		 * $Integer(Integer) closure = $Integer(Integer x){return x * factor;};
		 * 
		 */
		
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n"
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n"
						+ "##Find(Type)\n"
						+ "	##Either ##Identifier(n) ##or ##PrimitiveType(n)\n"
						+ "	##Optional <##Any>\n"
						+ " ##NoneOrMore []\n"						
						+ "##Find *##Type(_t1)(##Type(_t2)) ##Identifier(_i) = *##Type(_t1)(##Type(_t2) ##Identifier(_x)){return ##Identifier(_x) * factor};\n" //
						+ " ##replace Closure<##(_t1),##(_t2)> ##(_i) = new Closure<##(_t1),##(_t2)>(){ public ##(_t1) invoke(##(_t2) ##(_x)){ return ##(_x) * factor;}}};\n" //
						+ "*Integer(Integer) closure = *Integer(Integer x){return x * factor};", //
		  	"Closure<Integer,Integer> closure = new Closure<Integer,Integer>(){ public Integer invoke(Integer x){ return x * factor;}}};");
		
	}
	
	@Test
	public void closure1bc() {
		/**
		 * $Integer(Integer) closure = $Integer(Integer x){return x * factor;};
		 * 
		 */
		
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n"
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n"
						+ "##Find(Type)\n"
						+ "	##Either ##Identifier(n) ##or ##PrimitiveType(n)\n"
						+ "	##Optional <##Any>\n"
						+ " ##NoneOrMore []\n"						
						+ "##Find **##Type(_t1)(##Type(_t2)) ##Identifier(_i) = **##Type(_t1)(##Type(_t2) ##Identifier(_x)){return ##Identifier(_x) * factor};\n" //
						+ " ##replace Closure<##(_t1),##(_t2)> ##(_i) = new Closure<##(_t1),##(_t2)>(){ public ##(_t1) invoke(##(_t2) ##(_x)){ return ##(_x) * factor;}}};\n" //
						+ "**Integer(Integer) closure = **Integer(Integer x){return x * factor};", //
		  	"Closure<Integer,Integer> closure = new Closure<Integer,Integer>(){ public Integer invoke(Integer x){ return x * factor;}}};");
		
	}
	
	@Test
	public void closure1bb() {
		/**
		 * $Integer(Integer) closure = $Integer(Integer x){return x * factor;};
		 * 
		 */
		
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n"
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n"
						+ "##Find(Type)\n"
						+ "	##Either ##Identifier(n) ##or ##PrimitiveType(n)\n"
						+ "	##Optional <##Any>\n"
						+ " ##NoneOrMore []\n"						
						+ "##Find *##Type(_t1)(##Type(_t2)) ##Identifier(_i) = *##Type(_t1)(##Type(_t2) ##Identifier(_x)){return ##Identifier(_x)};\n" //
						+ " ##replace Closure<##(_t1),##(_t2)> ##(_i) = new Closure<##(_t1),##(_t2)>(){ public ##(_t1) invoke(##(_t2) ##(_x))};\n" //
						+ "*Integer(Integer) closure = *Integer(Integer x){return x};", //
		  	"Closure<Integer,Integer> closure = new Closure<Integer,Integer>(){ public Integer invoke(Integer x)};");
		
	}
	
	@Test
	public void closure1b() {
		/**
		 * $Integer(Integer) closure = $Integer(Integer x){return x * factor;};
		 * 
		 */
		
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n"
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n"
						+ "##Find(Type)\n"
						+ "	##Either ##Identifier(n) ##or ##PrimitiveType(n)\n"
						+ "	##Optional <##Any>\n"
						+ " ##NoneOrMore []\n"						
						+ "##Find *##Type(_t1)(##Type(_t2)) ##Identifier(_i) = *##Type(_t1)(##Type(_t2) ##Identifier(_x))\n" //
						+ " ##replace Closure<##(_t1),##(_t2)> ##(_i) = new Closure<##(_t1),##(_t2)>()\n" //
						+ "*Integer(Integer) closure = *Integer(Integer x)", //
		  	"Closure<Integer,Integer> closure = new Closure<Integer,Integer>()");
		
	}
	
	@Test
	public void closure1c() {
		/**
		 * $Integer(Integer) closure = $Integer(Integer x){return x * factor;};
		 * 
		 */
		
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n"
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n"
						+ "##Find(Type)\n"
						+ "	##Either ##Identifier(n) ##or ##PrimitiveType(n)\n"
						+ "	##Optional <##Any>\n"
						+ " ##NoneOrMore []\n"						
						+ "##Find *##Type(_t1)(##Type(_t2)) ##Identifier(_i) = *##Type(_t3)(##Type(_t4) ##Identifier(_x))\n" //
						+ " ##replace Closure<##(_t1),##(_t2)> ##(_i) = new Closure<##(_t3),##(_t4)>()\n" //
						+ "*Integer(Integer) closure = *String(Integer x)", // First Type is different on the right end side
		  	"Closure<Integer,Integer> closure = new Closure<String,Integer>()");
		
	}
	
	@Test
	public void closure2() {
		/**
		 * $Integer(Integer) closure = $Integer(Integer x){return x * factor;};
		 * 
		 */
		
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n"
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n"
						+ "##Find(Type)\n"
						+ "	##Either ##Identifier(n) ##or ##PrimitiveType(n)\n"
						+ "	##Optional <##Any>\n"
						+ " ##NoneOrMore []\n"						
						+ "##Find *##Type(_t1)(##Type(_t2)) ##Identifier(_id);\n" //
						+ " ##replace Closure<##(_t1),##(_t2)> ##(_id);\n" //
						+ "*Integer(Integer) closure;\n", //
		  	"Closure<Integer,Integer> closure;\n");
		
	}
	
	@Test
	public void closure3() {
		/**
		 * $Integer(Integer) closure = $Integer(Integer x){return x * factor;};
		 * 
		 */
		
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n"
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n"
						+ "##Find(Type)\n"
						+ "	##Either ##Identifier(n) ##or ##PrimitiveType(n)\n"
						+ "	##Optional <##Any>\n"
						+ " ##NoneOrMore []\n"						
						+ "##Find ##Type(_t) ##Identifier(_n)\n" //
						+ "	##replace Closure<##(_t),##(_n)>\n" //
						+ "Integer closure;\n", //
		  	"Closure<Integer,closure>;\n");
		
	}
	
	@Test
	public void closure4() {
		/**
		 * $Integer(Integer) closure = $Integer(Integer x){return x * factor;};
		 * 
		 */
		
		auxz.runStringTest(
				"" //
						+ "##Find(PrimitiveType)\n"
						+ " ##Either boolean ##or byte ##or char ##or short ##or int ##or long ##or float ##or double ##or void\n"
						+ "##Find(Type)\n"
						+ "	##Either ##Identifier(n) ##or ##PrimitiveType(n)\n"
						+ "	##Optional <##Any>\n"
						+ " ##NoneOrMore []\n"						
						+ "##Find String ##Identifier(_n);\n" //
						+ "	##replace Closure ##(_n);\n" //
						+ "String closure;\n", //
		  	"Closure closure;\n");
		
	}
	
	final static int factor = 3;
	
	public interface Closure<Result, Argument> {
		public Result invoke(Argument argument);
	}
	
	public static Closure<Integer,Integer> closure = new Closure<Integer, Integer>(){
			public Integer invoke(Integer x){
				return x * factor;
			}
	};
	
	public static void main(final String[] args){
		System.out.println(closure.invoke(3));
	}

}
