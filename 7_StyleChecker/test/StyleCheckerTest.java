import bg.sofia.uni.fmi.mjt.stylechecker.StyleChecker;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

public class StyleCheckerTest {
    private StyleChecker checker = new StyleChecker();

    @Test
    public void testOnlyOneStatementInALine1() {
        Reader input = new StringReader("sayHello();sayHello();sayHello();");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("// FIXME Only one statement per line is allowed\n"
                 + "sayHello();sayHello();sayHello();", actual.strip());
    }

    @Test
    public void testOnlyOneStatementInALine2() {
        Reader input = new StringReader("sayHello();;;");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("sayHello();;;", actual.strip());
    }

    @Test
    public void testOnlyOneStatementInALine3() {
        Reader input = new StringReader("sayHello();;;sayHello();;");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("// FIXME Only one statement per line is allowed\n"
                + "sayHello();;;sayHello();;", actual.strip());
    }

    @Test
    public void testWildcardInImport() {
        Reader input = new StringReader("import org.junit.*");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("// FIXME Wildcards are not allowed in import statements\n"
                + "import org.junit.*", actual.strip());
    }

    @Test
    public void testCurlyBracketOnNewLine() {
        Reader input = new StringReader("public static void sayHello()\n" +
                "    {\n" +
                "        String hello;");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("public static void sayHello()\n" +
                "// FIXME Opening brackets should be placed on the " +
                "same line as the declaration\n" +
                "    {\n" +
                "        String hello;", actual.strip());
    }

    @Test
    public void testCurlyBracketOnNewLine2() {
        Reader input = new StringReader("public static void sayHello() {\n" +
                "        String hello;");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("public static void sayHello() {\n" +
                "        String hello;", actual.strip());
    }

    @Test
    public void testUpperCaseWordsInPackageName() {
        Reader input = new StringReader("package bg.fmi.Uni");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("// FIXME Package name should not contain upper-case " +
                "letters or underscores\n" +
                "package bg.fmi.Uni", actual.strip());
    }

    @Test
    public void testUnderscoreInPackageName() {
        Reader input = new StringReader("package bg.fmi.sofia_uni");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("// FIXME Package name should not contain upper-case " +
                "letters or underscores\n" +
                "package bg.fmi.sofia_uni", actual.strip());
    }

    @Test
    public void testLineTooLong() {
        Reader input = new StringReader("if (areThereUpperCaseWordsInPackageName(line)) {\n" +
                "bufferedWriter.write(\"// FIXME Package name " +
                "should not contain upper-case letters or underscores bla bla\");");
        Writer output = new StringWriter();

        checker.checkStyle(input, output);
        String actual = output.toString();

        assertEquals("if (areThereUpperCaseWordsInPackageName(line)) {\n" +
                "// FIXME Length of line should not exceed 100 characters\n" +
                "bufferedWriter.write(\"// FIXME Package name " +
                "should not contain upper-case letters or underscores bla bla\");", actual.strip());
    }

}
