package uk.ac.ebi.pride.validator;

import java.util.*;

/**
 * Utility class for validators
 *
 * @author Rui Wang
 * @version $Id$
 */
public final class Validators {

    /**
     * Compose a list of validators into a single combined validator
     * NOTE: only one validator is selected and used at one time
     *
     * @param validators input list of validators
     * @param <V>        Input type
     * @param <T>        Validation result type
     * @return a composed validator
     */
    public static <V, T> IValidator<V, T> compose(final List<IValidator<V, ? extends T>> validators) {

        return new IValidator<V, T>() {
            @Override
            public boolean support(V input) {
                for (IValidator<V, ? extends T> validator : validators) {
                    if (validator.support(input)) {
                        return true;
                    }
                }
                return false;
            }

            private IValidator<V, ? extends T> select(V input) {
                for (IValidator<V, ? extends T> validator : validators) {
                    if (validator.support(input)) {
                        return validator;
                    }
                }

                return null;
            }

            @Override
            public List<T> validate(V input) {
                IValidator<V, ? extends T> validator = select(input);

                if (validator == null) {
                    throw new IllegalStateException("Validation on input is not supported");
                }

                return new ArrayList<T>(validator.validate(input));
            }

            @Override
            public Map<V, List<T>> validate(List<V> inputs) {
                Map<V, List<T>> validationResults = new HashMap<V, List<T>>();

                for (V input : inputs) {
                    List<T> results = validate(input);
                    validationResults.put(input, results);
                }

                return validationResults;
            }

            @Override
            public Map<V, List<T>> validate(V... inputs) {
                return validate(Arrays.asList(inputs));
            }
        };
    }


    /**
     * Compose an array of validators into a single combined validator
     * NOTE: only one validator is selected and used at one time
     *
     * @param validators input array of validators
     * @param <V>        Input type
     * @param <T>        Validation result type
     * @return a composed validator
     */
    public static <V, T> IValidator<V, T> compose(final IValidator<V, ? extends T>... validators) {
        return compose(Arrays.asList(validators));
    }
}
