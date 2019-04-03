import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import st.Parser;

public class Task1_Functional {
	private Parser parser;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	
	@Test
	public void works_with_shortcut() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("o"), "output.txt");
	}
	
	@Test
	public void works_with_shortcut_2() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o=output.txt");
		assertEquals(parser.getString("o"), "output.txt");
	}
	@Test
	public void works_with_shortcut_3() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("output"), "output.txt");
	}
	
	@Test
	public void works_with_shortcut_4() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o=output.txt");
		assertEquals(parser.getString("output"), "output.txt");
	}
	@Test
	public void works_with_shortcut_5() {
		parser.add("output", "O", Parser.STRING);
		parser.parse("-O=output.txt");
		assertEquals(parser.getString("output"), "output.txt");
	}
	
	@Test
	public void works_without_shortcut() {
		parser.add("output", Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("output"), "output.txt");
	}
	
	@Test
	public void overrides_existing_option() {
		parser.add("output", "o", Parser.INTEGER);
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=5");
		assertEquals(parser.getString("output"), "5");
	}
}
