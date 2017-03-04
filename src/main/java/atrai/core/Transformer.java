package atrai.core;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Manages sequencing patterns, modifiers, and replacers to build a complex
 * chain of tree transformations.
 *
 * @author Koushik Sen
 * @author Alex Reinking
 */
public class Transformer {
    private final Lexer lexer;
    private final ObjectArrayList<TransformationStep> transformers;

    /**
     * Create a new transformer for languages tokenized by {@code lexer}
     *
     * @param lexer the tokenizer for the trees
     */
    public Transformer(Lexer lexer) {
        this.lexer = lexer;
        this.transformers = new ObjectArrayList<>();
    }

    /**
     * Apply the {@code modifier} to trees matching the {@code pattern}
     *
     * @param pattern  the pattern to match
     * @param modifier the modifier to apply
     * @return returns this
     */
    public Transformer addObserver(String pattern, Consumer<Object[]> modifier) {
        this.transformers.push(new TransformationStep(pattern, modifier, null, lexer));
        return this;
    }

    /**
     * Apply the {@code modifier} to trees matching the {@code pattern}
     *
     * @param pattern  the pattern to match
     * @param modifier the modifier to apply
     * @return returns this
     */
    public Transformer addObserver(String pattern, BiConsumer<Object[], Object> modifier) {
        this.transformers.push(new TransformationStep(pattern, modifier, null, lexer));
        return this;
    }

    /**
     * Apply the {@code replacementTemplate} to trees matching the {@code pattern}
     *
     * @param pattern  the pattern to match
     * @param replacer the replacement expression to apply
     * @return returns this
     */
    public Transformer addTransformer(String pattern, String replacer) {
        this.transformers.push(new TransformationStep(pattern, (Consumer<Object[]>)null, replacer, lexer));
        return this;
    }

    /**
     * Apply the {@code directModifier} to trees matching the {@code pattern}
     *
     * @param pattern        the pattern to match
     * @param directModifier the direct modifier to apply.  A direct modifier returns the modified tree directly
     *                       without using nay replacement expression.
     * @return returns this
     */
    public Transformer addTransformer(String pattern, Function<Object[], Object> directModifier) {
        this.transformers.push(new TransformationStep(pattern, directModifier, lexer));
        return this;
    }

    /**
     * Apply the {@code directModifier} to trees matching the {@code pattern}
     *
     * @param pattern        the pattern to match
     * @param directModifier the direct modifier to apply.  A direct modifier returns the modified tree directly
     *                       without using nay replacement expression.
     * @return returns this
     */
    public Transformer addTransformer(String pattern, BiFunction<Object[], Object, Object> directModifier) {
        this.transformers.push(new TransformationStep(pattern, directModifier, lexer));
        return this;
    }

    /**
     * Apply the {@code pureModifier} and {@code replacementTemplate} to trees matching the {@code pattern}
     *
     * @param pattern      the pattern to match
     * @param pureModifier the modifier to apply
     * @param replacer     the replacement expression to apply to the modified tree
     * @return returns this
     */
    public Transformer addTransformer(String pattern, Function<Object[], Object[]> pureModifier, String replacer) {
        this.transformers.push(new TransformationStep(pattern, pureModifier, replacer, lexer));
        return this;
    }

    /**
     * Apply the {@code pureModifier} and {@code replacementTemplate} to trees matching the {@code pattern}
     *
     * @param pattern      the pattern to match
     * @param pureModifier the modifier to apply
     * @param replacer     the replacement expression to apply to the modified tree
     * @return returns this
     */
    public Transformer addTransformer(String pattern, BiFunction<Object[], Object, Object[]> pureModifier, String replacer) {
        this.transformers.push(new TransformationStep(pattern, pureModifier, replacer, lexer));
        return this;
    }

    /**
     * Apply the {@code modifier} and {@code replacementTemplate} to trees matching the {@code pattern}
     *
     * @param pattern  the pattern to match
     * @param modifier the modifier to apply
     * @param replacer the replacement expression to apply to the modified tree
     * @return returns this
     */
    public Transformer addTransformer(String pattern, Consumer<Object[]> modifier, String replacer) {
        this.transformers.push(new TransformationStep(pattern, modifier, replacer, lexer));
        return this;
    }


    /**
     * Apply the {@code modifier} and {@code replacementTemplate} to trees matching the {@code pattern}
     *
     * @param pattern  the pattern to match
     * @param modifier the modifier to apply
     * @param replacer the replacement expression to apply to the modified tree
     * @return returns this
     */
    public Transformer addTransformer(String pattern, BiConsumer<Object[], Object> modifier, String replacer) {
        this.transformers.push(new TransformationStep(pattern, modifier, replacer, lexer));
        return this;
    }



    /**
     * Apply all transformations to the given root tree node and returns a new root node
     *
     * @param treeNode the root of the tree to transform
     * @return the transformed tree
     */
    public Object transform(Object treeNode) {
        return MatchAndReplace.matchAndReplace(transformers, treeNode, null);
    }

    /**
     * Apply all transformations to the given root tree node and returns a new root node
     *
     * @param treeNode the root of the tree to transform
     * @param context the context to be passed to the modifiers
     * @return the transformed tree
     */
    public Object transform(Object treeNode, Object context) {
        return MatchAndReplace.matchAndReplace(transformers, treeNode, context);
    }

    /**
     * Apply all transformations to the given untyped tree and returns a new untyped tree
     *
     * @param st the untyped tree to transform
     * @return a new transformed untyped tree
     */
    public UntypedTree transform (UntypedTree st) {
        return new UntypedTree(transform(st.getRoot()), st.locations);
    }

    /**
     * Apply all transformations to the given untyped tree and returns a new untyped tree
     *
     * @param st the untyped tree to transform
     * @param context the context to be passed to the modifiers
     * @return a new transformed untyped tree
     */
    public UntypedTree transform (UntypedTree st, Object context) {
        Object tmp = transform(st.getRoot(), context);
        if (tmp != st.getRoot()) {
            return new UntypedTree(tmp, st.locations);
        } else {
            return st;
        }
    }

}
