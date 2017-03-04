package atrai.interpreters.FUN;

import atrai.antlr.ANTLRTokenizer;
import atrai.antlr.GenericAntlrToUntypedTree;
import atrai.antlr.Location;
import atrai.core.InternalNode;
import atrai.core.UntypedTree;
import atrai.core.Transformer;
import atrai.interpreters.common.Environment;
import atrai.interpreters.common.Interpreter;
import atrai.interpreters.common.SemanticException;

import static atrai.interpreters.common.DynamicTypeChecker.*;
import static atrai.interpreters.common.DynamicTypeChecker.i;
import static atrai.interpreters.common.DynamicTypeChecker.s;


/**
 * FunInterpreter for the FUN language
 *
 * @author Koushik Sen
 * @author Alex Reinking
 */
public class FunInterpreter extends Interpreter {
    private String grammarName = "atrai.antlr.FUN";


    public Object interpret(UntypedTree st) {
        return null;
    }


    public UntypedTree parseString(String pgm) {
        return null;
    }

    public UntypedTree parseFile(String pgm) {
        return null;
    }
}
