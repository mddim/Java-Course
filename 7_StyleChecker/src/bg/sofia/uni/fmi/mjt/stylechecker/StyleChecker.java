package bg.sofia.uni.fmi.mjt.stylechecker;

import java.io.*;

public class StyleChecker {

    private final static int MAX_LINE_LENGTH = 100;

    public void checkStyle(Reader source, Writer output) {
        String line;
        try {

            BufferedReader bufferedReader = new BufferedReader(source);
            BufferedWriter bufferedWriter = new BufferedWriter(output);

            while ((line = bufferedReader.readLine()) != null) {
                if (isThereOnlyOneStatementInALine(line)) {
                    bufferedWriter.write("// FIXME Only one statement per line is allowed\n");
                }

                if (isThereAWildcardInImport(line)) {
                    bufferedWriter.write("// FIXME Wildcards are not allowed in import statements\n");
                }

                if (isThereCurlyBracketOnNewLine(line)) {
                    bufferedWriter.write("// FIXME Opening brackets should be placed on the " +
                            "same line as the declaration\n");
                }

                if (areThereUpperCaseWordsInPackageName(line)) {
                    bufferedWriter.write("// FIXME Package name should not contain upper-case " +
                            "letters or underscores\n");
                }

                if (isTheLineTooLong(line)) {
                    bufferedWriter.write("// FIXME Length of line should not exceed 100 characters\n");
                }
                bufferedWriter.write(line + "\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isThereOnlyOneStatementInALine(String line) {
        String[] statements = line.split(";");
        return statements.length > 1;
    }

    public boolean isThereAWildcardInImport(String line) {
        if (line.indexOf("import") == 0) {
            if (line.contains("*")) {
                return true;
            }
        }
        return false;
    }

    public boolean isThereCurlyBracketOnNewLine(String line) {
        if (line.contains("{")) {
            line = line.replaceAll("\\s", "");
            if (line.indexOf("{") == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean areThereUpperCaseWordsInPackageName(String line) {
        if (line.contains("package ")) {
            line = line.split(" ")[1];
            if (line.contains("_")) {
                return true;
            }

            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (Character.isUpperCase(chars[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isTheLineTooLong(String line) {
        if (line.contains("import")) {
            return false;
        }
        line.replaceAll("\\s", "");
        return line.length() > MAX_LINE_LENGTH;
    }

}
