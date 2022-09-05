package info.kgeorgiy.ja.nagibin.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Implementor implements Impler {
    public Implementor() {
    }

    private static final String ROUND_OPEN_BRACKET = "(";
    private static final String ROUND_CLOSE_BRACKET = ")";
    private static final String CURLY_OPEN_BRACKET = "{";
    private static final String CURLY_CLOSE_BRACKET = "}";
    private static final String COMMA = ", ";
    private static final String SEMICOLON = ";";
    private static final String TAB = "\t";
    private static final String DOUBLE_TAB = "\t\t";
    private static final String SPACE = " ";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final char FILE_SEPARATOR = File.separatorChar;
    private static final char DOT = '.';

    private static final String EMPTY_STRING = "";
    private static final String NULL = "null";
    private static final String FALSE = "false";
    private static final String ZERO = "0";

    private static final String IMPL_SUFFIX = "Impl";
    private static final String JAVA_SUFFIX = ".java";

    private static final String PACKAGE = "package";
    private static final String PUBLIC = "public";
    private static final String CLASS = "class";
    private static final String IMPLEMENTS = "implements";
    private static final String EXTENDS = "extends";
    private static final String THROWS = "throws";
    private static final String SUPER = "super";
    private static final String RETURN = "return";

    public static String generateClass(final Class<?> token) throws ImplerException {
        return String.join(
                LINE_SEPARATOR,
                generatePackage(token),
                generateClassHeading(token),
                String.join(LINE_SEPARATOR, generateConstructors(token)),
                String.join(LINE_SEPARATOR, generateMethods(token)),
                CURLY_CLOSE_BRACKET
        );
    }

    public static String generatePackage(final Class<?> token) {
        Package tokenPackage = token.getPackage();
        return Objects.isNull(tokenPackage) ?
                EMPTY_STRING :
                PACKAGE + SPACE + tokenPackage.getName() + SEMICOLON + LINE_SEPARATOR;
    }

    public static String generateClassHeading(final Class<?> token) {
        return String.join(
                SPACE,
                PUBLIC,
                CLASS,
                getFullName(token),
                token.isInterface() ? IMPLEMENTS : EXTENDS,
                token.getCanonicalName(),
                CURLY_OPEN_BRACKET
        );
    }

    public static String getFullName(final Class<?> token) {
        return token.getSimpleName() + IMPL_SUFFIX;
    }

    private static List<String> generateConstructors(final Class<?> token) throws ImplerException {
        if (token.isInterface()) {
            return Collections.emptyList();
        }

        final List<String> constructors = Arrays.stream(token.getDeclaredConstructors())
                .filter(it -> !Modifier.isPrivate(it.getModifiers()))
                .map(Implementor::generateConstructor).collect(Collectors.toList());

        if (constructors.isEmpty()) {
            throw new ImplerException("Public or protected constructor expected for " + token.getName());
        }

        return constructors;
    }

    private static String generateConstructor(final Constructor<?> constructor) {
        return String.join(
                LINE_SEPARATOR,
                TAB +
                        generateExecutable(getFullName(constructor.getDeclaringClass()), constructor),
                DOUBLE_TAB +
                        SUPER +
                        generateEnumerateOfArguments(constructor.getParameters(), false) +
                        SEMICOLON,
                TAB +
                        CURLY_CLOSE_BRACKET
        );
    }

    private static String generateExecutable(final String name, final Executable executable) {
        return String.join(
                SPACE,
                PUBLIC,
                name,
                generateEnumerateOfArguments(executable.getParameters(), true),
                generateEnumerateOfExceptions(executable.getExceptionTypes()),
                CURLY_OPEN_BRACKET
        );
    }

    private static String generateEnumerateOfArguments(final Parameter[] parameters, final boolean isDeclaration) {
        return ROUND_OPEN_BRACKET +
                        Arrays
                                .stream(parameters)
                                .map(it -> ((!isDeclaration ? EMPTY_STRING : it.getType().getCanonicalName()) + SPACE) +
                                        it.getName())
                                .collect(Collectors.joining(COMMA)) +
                        ROUND_CLOSE_BRACKET;
    }

    private static String generateEnumerateOfExceptions(final Class<?>[] exceptionTypes) {
        return exceptionTypes.length == 0 ?
                EMPTY_STRING :
                String.join(
                        SPACE,
                        THROWS,
                        Arrays.stream(exceptionTypes)
                                .map(Class::getCanonicalName)
                                .collect(Collectors.joining(COMMA))
                );
    }


    private static List<String> generateMethods(final Class<?> token) {
        final Set<Implementor.DataMethod> methods = collectDataMethods(token.getMethods());

        Class<?> temporaryToken = token;
        do {
            methods.addAll(collectDataMethods(temporaryToken.getDeclaredMethods()));
        } while ((temporaryToken = temporaryToken.getSuperclass()) != null);

        return methods
                .stream()
                .map(Implementor.DataMethod::method)
                .filter(it -> Modifier.isAbstract(it.getModifiers()))
                .map(Implementor::generateMethod)
                .collect(Collectors.toList());
    }

    private static Set<Implementor.DataMethod> collectDataMethods(final Method[] methods) {
        return Arrays
                .stream(methods)
                .map(Implementor.DataMethod::new)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private static String generateMethod(final Method method) {
        return String.join(
                LINE_SEPARATOR,
                TAB + generateExecutable(
                        String.join(
                                SPACE,
                                method.getReturnType().getCanonicalName(),
                                method.getName()),
                        method
                ),
                String.join(
                        SPACE,
                        DOUBLE_TAB + RETURN,
                        generateDefaultValue(method.getReturnType())
                ) + SEMICOLON,
                TAB + CURLY_CLOSE_BRACKET
        );
    }

    private static String generateDefaultValue(final Class<?> returnValue) {
        if (!returnValue.isPrimitive()) {
            return NULL;
        } else if (returnValue.equals(void.class)) {
            return EMPTY_STRING;
        } else if (returnValue.equals(boolean.class)) {
            return FALSE;
        }
        return ZERO;
    }

    public static Path generateClassPath(final Class<?> token, final Path rootDirectory) throws ImplerException {
        final Path path;
        try {
            path = rootDirectory
                    .resolve(token.getPackageName().replace(DOT, FILE_SEPARATOR))
                    .resolve(getFullName(token) + JAVA_SUFFIX);
        } catch (final InvalidPathException e) {
            throw new ImplerException("Given root directory is invalid: " + rootDirectory);
        }
        try {
            Files.createDirectories(path.getParent());
        } catch (final IOException e) {
            throw new ImplerException("Can't create directory " + path.getParent());
        }
        return path;
    }

    @Override
    public void implement(final Class<?> token, final Path root) throws ImplerException {
        if (token.isPrimitive() || token.isArray() || token == Enum.class || token.isEnum()) {
            throw new ImplerException("Invalid token: " + token.getName());
        }
        final int modifiers = token.getModifiers();
        if (Modifier.isFinal(modifiers) || Modifier.isPrivate(modifiers)) {
            throw new ImplerException("Invalid modifiers of token: " + token.getName());
        }

        final Path classPath = generateClassPath(token, root);
        try (BufferedWriter writer = Files.newBufferedWriter(classPath)) {
            writer.write(generateClass(token));
        } catch (IOException e) {
            throw new ImplerException("Inaccessible output file: " + classPath);
        }
    }

    private record DataMethod(Method method) {
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            final Implementor.DataMethod that = (Implementor.DataMethod) obj;
            return Objects.equals(method.getName(), that.method.getName()) &&
                    Objects.equals(method.getReturnType(), that.method.getReturnType()) &&
                    Arrays.equals(method.getParameterTypes(), that.method.getParameterTypes());
        }

        @Override
        public int hashCode() {
            return Objects.hash(method.getName(), method.getReturnType(), Arrays.hashCode(method.getParameterTypes()));
        }
    }
}