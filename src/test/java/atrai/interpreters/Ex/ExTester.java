package atrai.interpreters.Ex;

import atrai.antlr.GenericAntlrToUntypedTree;
import atrai.core.UntypedTree;
import org.junit.Test;

/**
 * Created by ksen on 2/15/17.
 */
public class ExTester {
    @Test
    public void test1() {
        GenericAntlrToUntypedTree g = new GenericAntlrToUntypedTree();
        UntypedTree st = g.parseStringToUntypedTree("Ex", "atrai.antlr.Ex", "stat", "x + y * z;");
        System.out.println(st.toIndentedString());

    }
}
