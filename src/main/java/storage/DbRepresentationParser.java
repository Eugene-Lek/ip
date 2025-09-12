package storage;

/**
 * Interface for parsing a DB representation into its actual type
 */
@FunctionalInterface
public interface DbRepresentationParser<T> {
    T parse(String dbRepresentation) throws Exception;
}
