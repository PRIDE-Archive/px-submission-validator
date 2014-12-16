package uk.ac.ebi.pride.validator;

import java.util.List;
import java.util.Map;

/**
 * Validator interface for validating one or multiple input objects.
 *
 * @author Rui Wang
 * @version $Id$
 */
public interface IValidator<V, T> {

    /**
     * Check whether an input is supported by the validator
     *
     * @param input input object
     * @return true means supported
     */
    boolean support(V input);

    /**
     * Validate a given object
     *
     * @param input input object
     * @return a list of validation results
     */
    List<T> validate(V input);

    /**
     * Validate a given list of objects
     *
     * @param inputs a list of objects
     * @return a map of validation results
     */
    Map<V, List<T>> validate(List<V> inputs);

    /**
     * Validate a given array of objects
     *
     * @param inputs an array of objects
     * @return a map of validation results
     */
    Map<V, List<T>> validate(V... inputs);

}
