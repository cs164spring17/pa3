package atrai.interpreters.LETREC;

import atrai.core.UntypedTree;
import atrai.interpreters.common.SemanticException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the LETREC language interpreter
 *
 * @author Koushik Sen
 * @author Alex Reinking
 */
public class LetrecInterpreterTest {

    @Test
    public void inter1() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        UntypedTree st = inp.parseString("2+3");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(5, res);
    }

    @Test
    public void inter2() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        UntypedTree st = inp.parseString("2+3 + 5");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(10, res);
    }

    @Test
    public void inter3() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        UntypedTree st = inp.parseString("2+ 3 + 5*2*2");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(25, res);
    }

    @Test
    public void inter5() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        UntypedTree st = inp.parseString("let x = 3 in let y = 4 in  x * y");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(12, res);
    }

    @Test
    public void inter6() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        UntypedTree st = inp.parseString("let x = 7  in let y = 2 in let y = let x = x - 1 in x - y in x - 8 - y");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(-5, res);

    }

    @Test(expected= SemanticException.class)
    public void inter7_1() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        Object res = inp.interpretString("x + 1");
    }

    @Test(expected= SemanticException.class)
    public void inter7_3() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        Object res = inp.interpretString("(3 > 2) + 1");
    }

    @Test(expected= SemanticException.class)
    public void inter7_4() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        Object res = inp.interpretString("(3  2)");
    }

    @Test
    public void inter10() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        UntypedTree st = inp.parseString("letrec double(x) = if x == 0 then 0 else (double (x-1)) + 2 in (double 6)");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(12, res);

    }

    @Test
    public void inter11() throws Exception {
        LetrecInterpreter inp = new LetrecInterpreter();
        UntypedTree st = inp.parseString("letrec fac(x) = if x == 1 then 1 else x*(fac (x-1)) in (fac 4)");
        System.out.println(st);
        Object res = inp.interpret( st);
        assertEquals(24, res);

    }

}