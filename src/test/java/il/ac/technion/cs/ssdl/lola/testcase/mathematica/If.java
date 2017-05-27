package il.ac.technion.cs.ssdl.lola.testcase.mathematica;

import org.junit.Test;

import il.ac.technion.cs.ssdl.lola.util.auxz;

public class If {
	
	/*
	 * Mathematica If
	 * 
	 * If[condition,t,f] gives t if condition evaluates to True, and f if it evaluates to False.
	 * If[condition,t,f,u] gives u if condition evaluates to neither True nor False.
	 * 
	 */
			
	
	@Test
	public void if1() {
		
		String s = null;
		String result = null;
		auxz.runStringTest(s, result);
		
	}
}
