package atrai.interpreters.LET;

import atrai.core.UntypedTree;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the LET language interpreter
 *
 * @author Koushik Sen
 * @author Alex Reinking
 */
public class LetInterpreterTest {

    @Test
    public void test1() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("3");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr (%LET 2 num 3%)%)%)", st.toString());
    }

    @Test
    public void test2() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("x");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr (%LET 2 iden x%)%)%)", st.toString());
    }

    @Test
    public void test3() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("x + 3");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr (%LET 2 expr (%LET 3 iden x%)%) + (%LET 4 expr (%LET 5 num 3%)%)%)%)", st.toString());
    }

    @Test
    public void test4() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("x + 3 * y");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr (%LET 2 expr (%LET 3 iden x%)%) + (%LET 4 expr (%LET 5 expr (%LET 6 num 3%)%) * (%LET 7 expr (%LET 8 iden y%)%)%)%)%)", st.toString());
    }

    @Test
    public void test5() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("x + 3 + 4");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr (%LET 2 expr (%LET 3 expr (%LET 4 iden x%)%) + (%LET 5 expr (%LET 6 num 3%)%)%) + (%LET 7 expr (%LET 8 num 4%)%)%)%)", st.toString());
    }

    @Test
    public void test6() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 3 in y");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr let (%LET 2 iden x%) = (%LET 3 expr (%LET 4 num 3%)%) in (%LET 5 expr (%LET 6 iden y%)%)%)%)", st.toString());
    }

    @Test
    public void test7() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 3 in let y = 4 in 5");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr let (%LET 2 iden x%) = (%LET 3 expr (%LET 4 num 3%)%) in (%LET 5 expr let (%LET 6 iden y%) = (%LET 7 expr (%LET 8 num 4%)%) in (%LET 9 expr (%LET 10 num 5%)%)%)%)%)", st.toString());
    }

    @Test
    public void test8() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 3 in let y = 4 in if (x + y)*2 > 2  then x*y else x-y");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr let (%LET 2 iden x%) = (%LET 3 expr (%LET 4 num 3%)%) in (%LET 5 expr let (%LET 6 iden y%) = (%LET 7 expr (%LET 8 num 4%)%) in (%LET 9 expr if (%LET 10 expr (%LET 11 expr (%LET 12 expr ( (%LET 13 expr (%LET 14 expr (%LET 15 iden x%)%) + (%LET 16 expr (%LET 17 iden y%)%)%) )%) * (%LET 18 expr (%LET 19 num 2%)%)%) > (%LET 20 expr (%LET 21 num 2%)%)%) then (%LET 22 expr (%LET 23 expr (%LET 24 iden x%)%) * (%LET 25 expr (%LET 26 iden y%)%)%) else (%LET 27 expr (%LET 28 expr (%LET 29 iden x%)%) - (%LET 30 expr (%LET 31 iden y%)%)%)%)%)%)%)", st.toString());
    }

    @Test
    public void test9() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 2 in let y = 3 in if (x+y)*2>1 then x*y else x-1");
        System.out.println(st);
        assertEquals("(%LET 0 prog (%LET 1 expr let (%LET 2 iden x%) = (%LET 3 expr (%LET 4 num 2%)%) in (%LET 5 expr let (%LET 6 iden y%) = (%LET 7 expr (%LET 8 num 3%)%) in (%LET 9 expr if (%LET 10 expr (%LET 11 expr (%LET 12 expr ( (%LET 13 expr (%LET 14 expr (%LET 15 iden x%)%) + (%LET 16 expr (%LET 17 iden y%)%)%) )%) * (%LET 18 expr (%LET 19 num 2%)%)%) > (%LET 20 expr (%LET 21 num 1%)%)%) then (%LET 22 expr (%LET 23 expr (%LET 24 iden x%)%) * (%LET 25 expr (%LET 26 iden y%)%)%) else (%LET 27 expr (%LET 28 expr (%LET 29 iden x%)%) - (%LET 30 expr (%LET 31 num 1%)%)%)%)%)%)%)", st.toString());
    }

    @Test
    public void inter1() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("2+3");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(5, res);
    }

    @Test
    public void inter2() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("2+3 + 5");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(10, res);
    }

    @Test
    public void inter3() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("2+ 3 + 5*2*2");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(25, res);
    }

    @Test
    public void inter4() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("2+ (3 + 5)*2*2");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(34, res);
    }

    @Test
    public void inter5() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 3 in let y = 4 in  x * y");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(12, res);
    }

    @Test
    public void inter6() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 7  in let y = 2 in let y = let x = x - 1 in x - y in x - 8 - y");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(-5, res);

    }

    @Test
    public void inter7() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 2 in let y = 3 in if (x+y)*2>10 then x*y else x-1");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(1, res);

    }

    @Test
    public void inter8() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 2 in let y = 3 in if (x+y)*2 <= 10 then x*y else x-1");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(6, res);

    }

    @Test
    public void inter9() throws Exception {
        LetInterpreter inp = new LetInterpreter();
        UntypedTree st = inp.parseString("let x = 2 in let y = 3 in (x+y)*2");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(10, res);

    }
}