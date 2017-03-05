package atrai.interpreters.FUN;

import atrai.core.UntypedTree;
import atrai.interpreters.common.SemanticException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ksen on 2/18/17.
 */
public class FunInterpreterTest {

    @Test
    public void inter1() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        UntypedTree st = inp.parseString("let x = 3, y = x + 1, z = y + 1 in x + y + z");
        Object res = inp.interpret(st);
        assertEquals(12, res);
    }

    @Test
    public void inter2() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        UntypedTree st = inp.parseString("let x = 3, y = x + 1, z = y + 1 in { x = z }");
        System.out.println(st.toIndentedString());
        Object res = inp.interpret( st);
        assertEquals(5, res);
    }


    @Test
    public void inter3() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        UntypedTree st = inp.parseString("let x = 3, y = x + 1, z = y + 1 in { x = z; x = x + y; x = x + z  }");
        System.out.println(st.toIndentedString());
        Object res = inp.interpret( st);
        assertEquals(14, res);
    }
    @Test
    public void inter4() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        UntypedTree st = inp.parseString("let i = 0, sum = 0 in {while i < 10 do {i = i + 1; sum = sum + i; print sum}; sum} ");
        System.out.println(st.toIndentedString());
        Object res = inp.interpret( st);
        assertEquals(55, res);
    }

    @Test
    public void inter5() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        UntypedTree st = inp.parseString("(fun (x) = fun(y) = fun(z) = x + y + z)(3)( (fun (x) = fun(y) = x + y)(3)(4))( (fun (x) = fun(y) = x + y)(3)(4))");
        System.out.println(st.toIndentedString());
        Object res = inp.interpret(st);
        assertEquals(17, res);
    }

    @Test
    public void inter6() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        Object res = inp.interpretString("let odd = fun(x) = 1, even = fun(y) = 2 in " +
                "{" +
                "odd = fun(x) = if x == 0 then 0 else {print x + \" \"; even(x-1)} ; " +
                "even = fun(y) = if y == 0 then 1 else {print y + \" \"; odd(y-1)}; " +
                "even(9)" +
                "}");
        assertEquals(0, res);
    }

    @Test
    public void inter7() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        Object res = inp.interpretString("let odd = fun(x) = 1, even = fun(y) = 2 in " +
                "{" +
                "odd = fun(x) = if x == 0 then 0 else {print x + \" \"; even(x-1)} ; " +
                "even = fun(y) = if y == 0 then 1 else {print y + \" \"; odd(y-1)}; " +
                "even(10)" +
                "}");
        assertEquals(1, res);
    }

    @Test
    public void inter8() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        Object res = inp.interpretString("4 + 5 * 6 - 3 + 6 / 3 - 8 / 4 / 2");
        assertEquals(32, res);
    }

    @Test
    public void inter9() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        Object res = inp.interpretString("4 == 4");
        assertEquals(true, res);
        res = inp.interpretString("4 == 5");
        assertEquals(false, res);

        res = inp.interpretString("4 != 5");
        assertEquals(true, res);
        res = inp.interpretString("4 != 4");
        assertEquals(false, res);

        res = inp.interpretString("4 < 5");
        assertEquals(true, res);
        res = inp.interpretString("4 < 4");
        assertEquals(false, res);

        res = inp.interpretString("4 <= 5");
        assertEquals(true, res);
        res = inp.interpretString("4 <= 4");
        assertEquals(true, res);
        res = inp.interpretString("4 <= 3");
        assertEquals(false, res);

        res = inp.interpretString("4 > 3");
        assertEquals(true, res);
        res = inp.interpretString("4 > 4");
        assertEquals(false, res);

        res = inp.interpretString("4 >= 3");
        assertEquals(true, res);
        res = inp.interpretString("4 >= 4");
        assertEquals(true, res);
        res = inp.interpretString("4 >= 5");
        assertEquals(false, res);
    }

    @Test
    public void inter10() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        Object res = inp.interpretString("fun(x)=x+1");
        assertEquals("Lambda[null, x, (%FUN 3 expr (%FUN 4 expr (%FUN 5 iden x%)%) + (%FUN 6 expr (%FUN 7 num 1%)%)%)]", res.toString());
        res = inp.interpretString("fun()=1");
        assertEquals("Lambda[null, (%FUN 2 expr (%FUN 3 num 1%)%)]", res.toString());
    }

    @Test(expected = SemanticException.class)
    public void inter11() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        inp.interpretString("1(2)");
    }

    @Test
    public void inter12() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        try {
            inp.interpretString("x + 1");
            throw new RuntimeException("read undeclared var");
        } catch (SemanticException e) {
            // expected: can't read undeclared var
        }

        try {
            inp.interpretString("let y = 3 in x + 1");
            throw new RuntimeException("read undeclared var");
        } catch (SemanticException e) {
            // expected: can't read undeclared var
        }

        try {
            inp.interpretString("x = 1");
            throw new RuntimeException("wrote undeclared var");
        } catch (SemanticException e) {
            // expected: can't assign to undeclared var
        }

        try {
            inp.interpretString("let y = 2 in x = 1");
            throw new RuntimeException("wrote undeclared var");
        } catch (SemanticException e) {
            // expected: can't assign to undeclared var
        }
    }

    @Test
    public void inter13() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        System.out.println(inp.parseString("11 + \" Hello \" + null"));

        Object res = inp.interpretString("11 + \" Hello \" + null");
        assertEquals("11 Hello null", res);

        res = inp.interpretString("\"Hello\" + \"World\"");
        assertEquals("HelloWorld", res);

        res = inp.interpretString("\"Hello\" + 10");
        assertEquals("Hello10", res);

        res = inp.interpretString("11 + 11 + \"Hello\"");
        assertEquals("22Hello", res);
    }

    @Test
    public void inter14() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        Object res = inp.interpretString("let foo = fun() = 10 in foo()");
        assertEquals(10, res);
    }

    @Test
    public void inter15() throws Exception {
        FunInterpreter inp = new FunInterpreter();
        Object res = inp.interpretString("let foo = fun(x) = fun(y) = x + y in foo(9)(10)");
        assertEquals(19, res);
    }
}
