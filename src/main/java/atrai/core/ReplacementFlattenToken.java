package atrai.core;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

/**
 * Created by ksen on 2/17/17.
 */
class ReplacementFlattenToken extends LeafNode {
    private final int index;

    /**
     * Creates a new replacement token with the given index
     *
     * @param index the capture group to use as a replacement (e.g. $1 or $3)
     */
    public ReplacementFlattenToken(int index) {
        this.index = index;
    }

    /**
     * Invalid to call.
     *
     * @throws RuntimeException always
     */
    public boolean matches(Object other, ObjectArrayList<Object> captures) {
        throw new RuntimeException("Template tokens cannot be used for matching");
    }

    /**
     * Selects the capture at the given index
     *
     * @param captures the captures to use for replacement
     * @return the capture at the given index
     * @throws RuntimeException when captures does not contain an element for the given index
     */
    @Override
    public Object replace(Object[] captures) {
        if (index < 0 || index > captures.length) {
            throw new RuntimeException("Template failed for " + this);
        }
        return captures[index];
    }

    /**
     * Prints the replacement to the given string builder
     *
     * @param sb the string builder to write to
     */
    @Override
    void toSourceString(StringBuilder sb) {
        sb.append(SerializedTreeParser.REPLACEFLATTENPREFIX).append(index);
    }

    /**
     * Pretty prints the replacement to the given string builder
     *
     * @param sb the string builder to write to
     */
    @Override
    public void toIndentedString(StringBuilder sb, String indent) {
        sb.append(SerializedTreeParser.REPLACEFLATTENPREFIX).append(index);
    }

    /**
     * @return string representation of the capture group: "$<i>{@link #index}</i>"
     */
    @Override
    public String toString() {
        return SerializedTreeParser.REPLACEFLATTENPREFIX + index;
    }
}
