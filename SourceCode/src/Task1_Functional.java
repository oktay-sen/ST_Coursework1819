import static org.junit.Assert.assertEquals;

import org.junit.Assert;
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
	
	@Test
	public void doesnt_work_with_num_as_first_char() {
		try {
			parser.add("5output", "o", Parser.STRING);
			parser.parse("--5output=output.txt");
			assertEquals(parser.getString("o"), "output.txt");
			Assert.fail("Exception not thrown");
		} catch (RuntimeException e) {
			return;
		}
	}
	
	public void option_names_are_case_sensitive() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("outPUT"), "");
	}
	
	public void shortcuts_are_case_sensitive() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("O"), "");
	}
	
	@Test
	public void shortcut_can_be_same_as_name_of_another_option() {
		parser.add("o", Parser.INTEGER);
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o=str");
		assertEquals(parser.getString("output"), "str");
		parser.parse("--o=345");
		assertEquals(parser.getInteger("o"), 345);
	}
	
	@Test
	public void works_with_integer() {
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output=123");
		assertEquals(parser.getInteger("output"), 123);
	}
	
	@Test
	public void works_with_boolean() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=true");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void works_with_char() {
		parser.add("output", "o", Parser.CHAR);
		parser.parse("--output=H");
		assertEquals(parser.getChar("output"), 'H');
	}
	
	@Test
	public void boolean_true_with_empty() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void boolean_true_with_any_string() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=hello");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void boolean_false_with_zero() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=0");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void boolean_false_with_false() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=false");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void boolean_false_with_false_case_insensitive() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=FaLsE");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void parser_works_with_equals_sign() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=text");
		assertEquals(parser.getString("output"), "text");
	}
	
	@Test
	public void parser_works_with_space() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output text");
		assertEquals(parser.getString("output"), "text");
	}

	@Test
	public void parser_works_with_single_quotes() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output='text with space'");
		assertEquals(parser.getString("output"), "text with space");
	}

	@Test
	public void parser_works_with_double_quotes() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=\"text with space\"");
		assertEquals(parser.getString("output"), "text with space");
	}
	

	@Test
	public void parser_works_with_nested_single_quotes() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=\"text with 'single quotes'\"");
		assertEquals(parser.getString("output"), "text with 'single quotes'");
	}
	
	@Test
	public void parser_works_with_nested_double_quotes() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output='text with \"double quotes\"'");
		assertEquals(parser.getString("output"), "text with \"double quotes\"");
	}

	@Test
	public void parser_accepts_last_occurance_of_option() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o first --output second");
		assertEquals(parser.getString("o"), "second");
	}
	
	@Test
	public void parse_can_be_called_multiple_times() {
		parser.add("output", "o", Parser.STRING);
		parser.add("input", "i", Parser.STRING);
		parser.parse("--output=output.txt");
		parser.parse("-i=input.txt");
		assertEquals(parser.getString("output"), "output.txt");
		assertEquals(parser.getString("input"), "input.txt");
	}
	

	@Test
	public void integer_default_value_is_zero() {
		assertEquals(parser.getInteger("o"), 0);
	}
	
	@Test
	public void boolean_default_value_is_false() {
		assertEquals(parser.getBoolean("o"), false);
	}

	@Test
	public void string_default_value_is_empty_string() {
		assertEquals(parser.getString("o"), "");
	}
	
	@Test
	public void char_default_value_is_null_character() {
		assertEquals(parser.getChar("o"), '\0');
	}

	@Test
	public void get_prioritises_option_name() {
		parser.add("o", Parser.STRING);
		parser.add("output", "o", Parser.STRING);
		parser.parse("--o full_name -o shortcut");
		assertEquals(parser.getString("o"), "full_name");
	}
}
