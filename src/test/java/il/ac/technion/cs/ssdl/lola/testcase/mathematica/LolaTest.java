package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import static org.junit.Assert.*;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

/**
 * Testing lola sintax
 * @author matteo
 *
 */

public class LolaTest {

	@Test
	public void lola1() {
		
		String s = ""
				+ "##Find which ##Literal(_l);\n"
				+ "	##replace W ##(_l);\n"
				+ "which 1;"; 
		String result = "W 1;";
		auxz.runStringTest(s, result);
		
	}
	
}
