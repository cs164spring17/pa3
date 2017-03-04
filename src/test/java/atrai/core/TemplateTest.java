package atrai.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ksen on 2/17/17.
 */
public class TemplateTest {

    private Object replace(String pattern, String template, String source) {
        Lexer lexer = new SimpleStringTokenizer();
        UntypedTree s = UntypedTree.parse(source, lexer);
        Pattern p = Pattern.parse(pattern, lexer);
        Template t = Template.parse(template, lexer);
        return t.replace(p.match(s));

    }

    @Test
    public void test1() throws Exception {
        String pattern = "@_";
        String source = "(%hello world%)";
        String template = "(% begin $_1 end %)";
        Object t = replace(pattern, template, source);
        assertEquals("(%begin hello world end%)", t.toString());
        System.out.println(t);
    }

    @Test
    public void test2() throws Exception {
        String pattern = "(% @_ @_ %)";
        String source = "(%hello (%world X%)%)";
        String template = "(% begin $1 $2 end %)";
        Object t = replace(pattern, template, source);
        System.out.println(t);
        assertEquals("(%begin hello (%world X%) end%)", t.toString());
    }

    @Test
    public void test3() throws Exception {
        String pattern = "(% @_ @_ %)";
        String source = "(%hello (%world X%)%)";
        String template = "(% begin `$1 $2 `$3_ end %)";
        Object t = replace(pattern, template, source);
        System.out.println(t);
        assertEquals("(%begin `$1 (%world X%) `$3_ end%)", t.toString());
    }
}