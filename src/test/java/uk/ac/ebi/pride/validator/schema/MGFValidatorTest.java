package uk.ac.ebi.pride.validator.schema;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

public class MGFValidatorTest
{
    private MGFValidator mgfValidator;

    @Before
    public void setUp() throws Exception {
        mgfValidator = new MGFValidator();
    }

    @Test
    public void testSupported() throws Exception {
        URL resource = MGFValidatorTest.class.getClassLoader().getResource("./mgf/valid.mgf");
        assertTrue(mgfValidator.support(new File(resource.toURI())));
    }

    @Test
    public void testSchemaValid() throws Exception {
        URL resource = MGFValidatorTest.class.getClassLoader().getResource("./mgf/valid.mgf");
        List<String> results = mgfValidator.validate(new File(resource.toURI()));
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSchemaInvalid() throws Exception {
        URL resource = MGFValidatorTest.class.getClassLoader().getResource("./mgf/invalid.mgf");
        List<String> results = mgfValidator.validate(new File(resource.toURI()));
        for (String s:results) System.out.println(s);
        assertTrue(results.size()==3);
    }
}
