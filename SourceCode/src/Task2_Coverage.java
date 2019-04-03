import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import st.Parser;

public class Task2_Coverage {
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
	
	@Test(expected=RuntimeException.class)
	public void doesnt_work_with_num_as_first_char() {
		parser.add("5output", "o", Parser.STRING);
	}
	
	@Test
	public void option_names_are_case_sensitive() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("outPUT"), "");
	}
	
	@Test
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
	
	@Test
	public void getInteger_returns_default_value_for_invalid_input() {
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output=output.txt");
		assertEquals(parser.getInteger("o"), 0);
	}
	
	@Test
	public void getInteger_returns_default_value_for_numbers_too_large() {
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output=9999999999999999999999999999999999999999999");
		assertEquals(parser.getInteger("o"), 0);
	}
	
	@Test
	public void getInteger_returns_zero_for_boolean_options_that_are_false() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=false");
		assertEquals(parser.getInteger("o"), 0);
	}
	
	@Test
	public void getInteger_returns_one_for_boolean_options_that_are_true() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=true");
		assertEquals(parser.getInteger("o"), 1);
	}

	@Test
	public void getInteger_returns_value_of_character_for_char_options() {
		parser.add("output", "o", Parser.CHAR);
		parser.parse("--output=A");
		assertEquals(parser.getInteger("o"), 65);
	}
	
	@Test
	public void getInteger_parses_value_of_string_options() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=123");
		assertEquals(parser.getInteger("o"), 123);
	}
	
	@Test
	public void getInteger_returns_default_value_for_string_options_that_contain_non_digit_characters() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=12a3");
		assertEquals(parser.getInteger("o"), 0);
	}
	
	@Test
	public void parse_returns_zero_for_successful_parse() {
		assertEquals(parser.parse("--output=output.txt"), 0);
	}
	
	@Test
	public void parse_returns_negative_1_for_null_input() {
		assertEquals(parser.parse(null), -1);
	}

	@Test
	public void parse_returns_negative_2_for_input_with_length_zero() {
		assertEquals(parser.parse(""), -2);
	}
	

	@Test
	public void parse_skips_over_spaces_at_the_beginning() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("                                   --output=output.txt");
		assertEquals(parser.getString("o"), "output.txt");
	}
	
	@Test
	public void parse_returns_negative_3_for_input_with_no_options() {
		assertEquals(parser.parse(" "), -3);
	}
	

	@Test
	public void works_with_options_with_underscore_in_the_name() {
		parser.add("out_put", "o", Parser.STRING);
		parser.parse("--out_put=output.txt");
		assertEquals(parser.getString("o"), "output.txt");
	}
	
	@Test
	public void boolean_options_work_with_shortcut_and_empty_value() {
		parser.add("output", "o", Parser.BOOLEAN);
		assertEquals(parser.parse("-o"), 0);
		assertEquals(parser.getBoolean("o"), true);
	}
	
	@Test
	public void boolean_values_with_dash_are_true() {
		parser.add("output", "o", Parser.BOOLEAN);
		assertEquals(parser.parse("-o=-"), 0);
		assertEquals(parser.getBoolean("o"), true);
	}
	
	@Test
	public void parsing_non_boolean_options_starting_with_dash_without_space_returns_negative_3() {
		parser.add("output", "o", Parser.STRING);
		assertEquals(parser.parse("-o -hello"), -3);
	}
	
	@Test
	public void toString_works() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.toString(), "OptionMap [options=\n" + 
				"	{name=output, shortcut=o, type=3, value=output.txt}\n" + 
				"]");
	}
	
	@Test
	public void options_without_value_ending_with_space_work() {
		parser.add("output", "o", Parser.BOOLEAN);
		assertEquals(parser.parse("-o       "), 0);
		assertEquals(parser.getBoolean("o"), true);
	}
	
	@Test
	public void add_doesnt_work_with_empty_() {
		parser.add("output", "o", Parser.BOOLEAN);
		assertEquals(parser.parse("-o=-"), 0);
		assertEquals(parser.getBoolean("o"), true);
	}
	
	@Test(expected=RuntimeException.class)
	public void options_with_null_name_are_invalid() {
		parser.add(null, Parser.STRING);
	}
	
	@Test(expected=RuntimeException.class)
	public void options_with_empty_name_are_invalid() {
		parser.add("", Parser.STRING);
	}

	@Test(expected=RuntimeException.class)
	public void options_with_null_shortcut_are_invalid() {
		parser.add("output", null, Parser.STRING);
	}

	@Test(expected=RuntimeException.class)
	public void options_with_shortcut_beginning_with_number_dont_work() {
		parser.add("output", "5o", Parser.STRING);
	}
	
	@Test(expected=RuntimeException.class)
	public void options_with_non_existant_types_are_invalid() {
		parser.add("output", -100);
	}

	@Test(expected=RuntimeException.class)
	public void options_with_non_existant_types_are_invalid_2() {
		parser.add("output", 100);
	}
}
