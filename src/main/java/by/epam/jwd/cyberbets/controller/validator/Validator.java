package by.epam.jwd.cyberbets.controller.validator;

public interface Validator<T> {
    boolean isValid(T t);
}
