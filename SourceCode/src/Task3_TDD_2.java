import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;
import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Task3_TDD_2 {

//	1.The order of search is full name of options first and then shortcut

	@Test
	public void testBothShortcutAndFullname() { 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.add("l", Parser.STRING);
		parser.parse("-l=1,10");
		parser.parse("--l=1,2,3");
		List actual = parser.getIntegerList("l");	
		List<Integer> expected = Arrays.asList(1,2,3);
		assertEquals(expected, actual);
	}
		
//	//2.If the option is not provided a value, an empty list is returned.
	@Test
	public void testOptionNoValue() { 
		Parser parser = new Parser();
		parser.add("option", "o", Parser.STRING);
		parser.parse("--option=\"\"");
		List actual = parser.getIntegerList("option");
		
		List<Integer> expected = Arrays.asList();
		
		assertEquals(expected, actual);
	}
	
	//3.Non-number characters except hyphen (-) can be used as separators including commas, dots, spaces etc. 
	@Test
	public void testSeparatorsExceptHyphen1(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=\"1,2 4\"");
		List actual = parser.getIntegerList("list");
		
		List<Integer> expected = Arrays.asList(1,2,4);
		
		assertEquals(expected, actual);
	}
	@Test
	public void testSeparatorsExceptHyphen2(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1,2.4");
		List actual = parser.getIntegerList("list");
		
		List<Integer> expected = Arrays.asList(1,2,4);
		
		assertEquals(expected, actual);
	}
	@Test
	public void testSeparatorsExceptHyphen3(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list={}1<2>4({)");
		List actual = parser.getIntegerList("list");
		
		List<Integer> expected = Arrays.asList(1,2,4);
		
		assertEquals(expected, actual);
	}
	
//	//4.A hyphens (-) indicates an inclusive range of numbers. For example,  4-7  and  7-4  both represents integers 4, 5, 6, 7.
	@Test
	public void testHyphensAsRange1(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=4-7");
		List actual = parser.getIntegerList("list");
		
		List<Integer> expected = Arrays.asList(4, 5, 6, 7);
		
		assertEquals(expected, actual);
	}
	@Test
	public void testHyphensAsRange2(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=7-4");
		List actual = parser.getIntegerList("list");
		
		List<Integer> expected = Arrays.asList(4, 5, 6, 7);
		
		assertEquals(expected, actual);
	}
	@Test
	public void testHyphensAsRange3(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1-2,3,3-4,-4--6,1");
		List actual = parser.getIntegerList("list");
		
		List<Integer> expected = Arrays.asList(-6,-5,-4,1,1,2,3,3,4);
		
		assertEquals(expected, actual);
	}
	//5.The unary prefix hyphen indicates a negative value. For example,  -7  is a negative value, -7--5  includes integers -7, -6, -5 and  -2-1  includes integers -2, -1, 0, 1.
	@Test
	public void testHyphensAsNegativeValue1(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=-7--5");
		List actual = parser.getIntegerList("list");
		
		List<Integer> expected = Arrays.asList(-7, -6, -5);
		
		assertEquals(expected, actual);
	}
	@Test
	public void testHyphensAsNegativeValue2(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=-2-1");
		List actual = parser.getIntegerList("list");	
		List<Integer> expected = Arrays.asList(-2, -1, 0, 1);		
		assertEquals(expected, actual);
	}
	@Test
	public void testHyphensAsNegativeValue3(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=-7---5");
		List actual = parser.getIntegerList("list");
		List<Integer> expected = Arrays.asList();
		
		assertEquals(expected, actual);
	}
	//6. Hyphens cannot be used as a suffix.  3-  , for instance, is invalid and an empty list should be returned.
	@Test
	public void testHyphensAsSuffix(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1-");
		List actual = parser.getIntegerList("list");	
		List<Integer> expected = Arrays.asList();
		assertEquals(expected, actual);
	}
	
	
	//test three numbers separated by 2 hyphens, eg: 1-3-5, return empty
	@Test
	public void testThreeNumberSeparatedByHyphens(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1-3-5");
		List actual = parser.getIntegerList("list");	
		List<Integer> expected = Arrays.asList();
		assertEquals(expected, actual);
	}
	//test"--",return empty
	@Test
	public void testAHyphens(){ 
		Parser parser = new Parser();
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=--");
		List actual = parser.getIntegerList("list");	
		List<Integer> expected = Arrays.asList();
		assertEquals(expected, actual);
	}
		//test"4--3",return -3,-2,-1,0,1,2,3,4
		@Test
		public void testHyphensAsNegativeValue4(){ 
			Parser parser = new Parser();
			parser.add("list", "l", Parser.STRING);
			parser.parse("--list=4--3");
			List actual = parser.getIntegerList("list");	
			List<Integer> expected = Arrays.asList(-3,-2,-1,0,1,2,3,4);
			assertEquals(expected, actual);
		}
		//test two same numbers separated by a hyphen.
		@Test
		public void testSameNumbersSeparatedByAHyphen(){ 
			Parser parser = new Parser();
			parser.add("list", "l", Parser.STRING);
			parser.parse("--list=1-1");
			List actual = parser.getIntegerList("list");	
			List<Integer> expected = Arrays.asList(1);
			assertEquals(expected, actual);
		}
		//test two same negative numbers separated by a hyphen
		@Test
		public void testSameNegativeNumbersSeparatedByAHyphen(){ 
			Parser parser = new Parser();
			parser.add("list", "l", Parser.STRING);
			parser.parse("--list=-1--1");
			List actual = parser.getIntegerList("list");	
			List<Integer> expected = Arrays.asList(-1);
			assertEquals(expected, actual);
		}
		//test "2 2"
				@Test
				public void testSameNum(){ 
					Parser parser = new Parser();
					parser.add("list", "l", Parser.STRING);
					parser.parse("--list=\"2 2\"");
					List actual = parser.getIntegerList("list");	
					List<Integer> expected = Arrays.asList(2,2);
					assertEquals(expected, actual);
				}
		
}
