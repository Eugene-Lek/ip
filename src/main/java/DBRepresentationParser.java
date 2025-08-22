@FunctionalInterface
public interface DBRepresentationParser<T> {
    T parse(String dbRepresentation);
}
