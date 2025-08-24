package storage;

/**
 * Interface for an object that can be saved to DB
 */
public interface SavableToDb {
    String toDbRepresentation();
}
