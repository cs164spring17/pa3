package atrai.interpreters.LETREC;

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


/**
 * FunInterpreter for the LETREC language
 *
 * @author Koushik Sen
 * @author Alex Reinking
 */
public class LetrecInterpreter extends Interpreter {
    private final static boolean DEBUG3 = Boolean.getBoolean("debug3");


    private String grammarName = "atrai.antlr.LETREC";

    public Object interpret(UntypedTree st) {
        return null;
    }


    public UntypedTree parseString(String pgm) {
        GenericAntlrToUntypedTree p = new GenericAntlrToUntypedTree();
        return p.parseStringToUntypedTree("LETREC", grammarName, "prog", pgm);
    }

    public UntypedTree parseFile(String pgm) {
        GenericAntlrToUntypedTree p = new GenericAntlrToUntypedTree();
        return p.parseFileToUntypedTree("LETREC", grammarName, "prog", pgm);
    }

}
