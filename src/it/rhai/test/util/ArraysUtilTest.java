package it.rhai.test.util;

import static org.junit.Assert.*;
import it.rhai.util.ArraysUtil;

import org.junit.Test;

public class ArraysUtilTest {

	@Test
	public void testToString() {
		String[] array = { "a", "b", "c" };
		assertEquals("$ a - b - c $",
				ArraysUtil.toString(array, " - ", "$ ", " $"));
	}

	@Test
	public void testIndex() throws Exception {
		String[] array = {"a", "b", "a", "a"};
		assertEquals(2, ArraysUtil.firstIndexOfOnly("a", array));
	}
}
