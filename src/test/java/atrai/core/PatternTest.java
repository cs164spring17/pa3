package atrai.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Pattern} class.
 *
 * @author Koushik Sen
 * @author Alex Reinking
 */
public class PatternTest {

    @Test
    public void test1() throws Exception {
        String pattern = "(%hello world %)";
        String source = "(%hello world%)";
        Object[] captures = match(pattern, source);
        assertEquals(1, captures.length);
    }

    private Object[] match(String pattern, String source) {
        Lexer lexer = new SimpleStringTokenizer();
        UntypedTree s = UntypedTree.parse(source, lexer);
        Pattern p = Pattern.parse(pattern, lexer);
        return p.match(s);
    }

    @Test
    public void test2() throws Exception {
        String pattern = "(% @_ world %)";
        String source = "(%hello world%)";
        Object[] captures = match(pattern, source);
        assertEquals(2, captures.length);
        assertEquals("hello", captures[1].toString());
    }

    @Test
    public void test3() throws Exception {
        String pattern = "(% @_ @_ %)";
        String source = "(%hello world%)";
        Object[] captures = match(pattern, source);
        assertEquals(3, captures.length);
        assertEquals("hello", captures[1].toString());
        assertEquals("world", captures[2].toString());
    }

    @Test
    public void test4() throws Exception {
        String pattern = "(% if (@_) (%{ @_ }%) else @_ %)";
        String source = "(%if ( (% x > 0%) ) (% { (%x = (%- x%) %) }%) else (% (% x %) = (%x + 1%)%)%)";
        Object[] captures = match(pattern, source);
        assertEquals(4, captures.length);
        assertEquals("(%x > 0%)", captures[1].toString());
        assertEquals("(%x = (%- x%)%)", captures[2].toString());
        assertEquals("(%(%x%) = (%x + 1%)%)", captures[3].toString());
    }

    @Test
    public void test5() throws Exception {
        String pattern = "(% `@ @_ %)";
        String source = "(%`@ world%)";
        Object[] captures = match(pattern, source);
        assertEquals(2, captures.length);
        assertEquals("world", captures[1].toString());
    }

}
