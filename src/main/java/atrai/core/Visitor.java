package atrai.core;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.function.Consumer;
import java.util.function.Function;

import static atrai.core.MatchAndReplace.matchAndReplace;

/**
 * A visitor for trees with pre and post accept hooks.
 *
 * @author Koushik Sen
 * @author Alex Reinking
 */
public class Visitor {
    private final Lexer lexer;
    private final ObjectArrayList<TransformationStep> preTransformers;
    private final ObjectArrayList<TransformationStep> postTransformers;

    /**
     * Create a new visitor for trees built from the lexer
     *
     * @param lexer the lexer used by the trees to be processed
     */
    public Visitor(Lexer lexer) {
        this.lexer = lexer;
        this.preTransformers = new ObjectArrayList<>();
        this.postTransformers = new ObjectArrayList<>();
    }

    private void addTransformer(String pattern, Consumer<Object[]> pureModifier, String replacer, boolean isPre) {
        if (isPre) {
            this.preTransformers.push(new TransformationStep(pattern, pureModifier, replacer, lexer));
        } else {
            this.postTransformers.push(new TransformationStep(pattern, pureModifier, replacer, lexer));
        }
    }

    private void addTransformer(String pattern, Function<Object[], Object[]> pureModifier, String replacer, boolean isPre) {
        if (isPre) {
            this.preTransformers.push(new TransformationStep(pattern, pureModifier, replacer, lexer));
        } else {
            this.postTransformers.push(new TransformationStep(pattern, pureModifier, replacer, lexer));
        }
    }

    /**
     * Replace matched trees before their subtrees are processed
     *
     * @param pattern  the pattern to match
     * @param replacer the replacementTemplate to use
     * @return this
     */
    public Visitor addTransformerPre(String pattern, String replacer) {
        addTransformer(pattern, (Consumer<Object[]>) null, replacer, true);
        return this;
    }

    /**
     * Replace matched trees after their subtrees have been processed
     *
     * @param pattern  the pattern to match
     * @param replacer the replacementTemplate to use
     * @return this
     */
    public Visitor addTransformerPost(String pattern, String replacer) {
        addTransformer(pattern, (Consumer<Object[]>) null, replacer, false);
        return this;
    }

    /**
     * Modify and replace matched trees before their subtrees have been processed
     *
     * @param pattern      the pattern to match
     * @param pureModifier the modifier to apply
     * @param replacer     the replacementTemplate to use
     * @return this
     */
    public Visitor addTransformerPre(String pattern, Function<Object[], Object[]> pureModifier, String replacer) {
        addTransformer(pattern, pureModifier, replacer, true);
        return this;
    }

    /**
     * Modify and replace matched trees after their subtrees have been processed
     *
     * @param pattern      the pattern to match
     * @param pureModifier the modifier to apply
     * @param replacer     the replacementTemplate to use
     * @return this
     */
    public Visitor addTransformerPost(String pattern, Function<Object[], Object[]> pureModifier, String replacer) {
        addTransformer(pattern, pureModifier, replacer, false);
        return this;
    }

    /**
     * Modify and replace matched trees before their subtrees have been processed
     *
     * @param pattern  the pattern to match
     * @param modifier the modifier to apply
     * @param replacer the replacementTemplate to use
     * @return this
     */
    public Visitor addTransformerPre(String pattern, Consumer<Object[]> modifier, String replacer) {
        addTransformer(pattern, modifier, replacer, true);
        return this;
    }

    /**
     * Modify and replace matched trees after their subtrees have been processed
     *
     * @param pattern  the pattern to match
     * @param modifier the modifier to apply
     * @param replacer the replacementTemplate to use
     * @return this
     */
    public Visitor addTransformerPost(String pattern, Consumer<Object[]> modifier, String replacer) {
        addTransformer(pattern, modifier, replacer, false);
        return this;
    }


    /**
     * Modify matched trees before their subtrees have been processed
     *
     * @param pattern  the pattern to match
     * @param modifier the modifier to apply
     * @return this
     */
    public Visitor addObserverPre(String pattern, Consumer<Object[]> modifier) {
        addTransformer(pattern, modifier, null, true);
        return this;
    }

    /**
     * Modify matched trees after their subtrees have been processed
     *
     * @param pattern  the pattern to match
     * @param modifier the modifier to apply
     * @return this
     */
    public Visitor addObserverPost(String pattern, Consumer<Object[]> modifier) {
        addTransformer(pattern, modifier, null, false);
        return this;
    }


    /**
     * Parse a string into an untyped tree using {@link #lexer} and then {@code visit} it.
     *
     * @param source the string to parse
     * @return the serialized, transformed tree
     */
    public UntypedTree parseAndVisit(String source) {
        UntypedTree st = UntypedTree.parse(source, lexer);
        st = this.visit(st);
        return st;
    }

    /**
     * Visit the {@code treeNode} and return the modified tree
     *
     * @param treeNode the root of the tree to be modified
     * @return the modified and freshly created tree
     */
    public Object visit(Object treeNode) {
        return visit(treeNode, null);
    }

    /**
     * Visit the {@code treeNode} and return the modified tree
     *
     * @param treeNode the root of the tree to be modified
     * @param context context to be passed to the modifiers
     * @return the modified and freshly created tree
     */
    public Object visit(Object treeNode, Object context) {
        treeNode = matchAndReplace(this.preTransformers, treeNode, context);
        if (treeNode instanceof InternalNode) {
            InternalNode node = (InternalNode) treeNode;
            treeNode = node.accept(this, context);
        }
        return matchAndReplace(this.postTransformers, treeNode, context);
    }

    /**
     * Apply the transformations from a visitor to an untyped tree recursively.  Does not modify {@code st}
     *
     * @param st the untyped tree to be visited
     * @return the modified tree
     */
    public UntypedTree visit(UntypedTree st) {
        Object tmp = this.visit(st.getRoot());
        if (tmp != st.getRoot()) {
            return new UntypedTree(tmp, st.locations);
        } else {
            return st;
        }
    }

    /**
     * Apply the transformations from a visitor to an untyped tree recursively.  Does not modify {@code st}
     *
     * @param st the untyped tree to be visited
     * @param context the context to be passed to the modifiers
     * @return the modified tree
     */
    public UntypedTree visit(UntypedTree st, Object context) {
        Object tmp = this.visit(st.getRoot(), context);
        if (tmp != st.getRoot()) {
            return new UntypedTree(tmp, st.locations);
        } else {
            return st;
        }
    }

}
