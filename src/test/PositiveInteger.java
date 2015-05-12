package test;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * Validation of input from CLI
 */
public class PositiveInteger implements IParameterValidator {
    public void validate(String name, String value)
            throws ParameterException {
        int n = Integer.parseInt(value);
        if (n < 0) {
            throw new ParameterException(
                    "Parameter " + name + " should be positive (found " +
                    value + ")");
        }
    }
}