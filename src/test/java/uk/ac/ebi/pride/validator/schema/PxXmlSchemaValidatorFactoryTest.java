package uk.ac.ebi.pride.validator.schema;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.validator.IValidator;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PxXmlSchemaValidatorFactoryTest {

    private IValidator<File, String> schemaValidator;

    @Before
    public void setUp() throws Exception {
        schemaValidator = PxXmlSchemaValidatorFactory.getPxXmlSchemaValidator();

    }

    @Test
    public void testGetPxXmlSchemaValidator() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./mzml/valid.mzML");
        List<String> results = schemaValidator.validate(new File(resource.toURI()));
        assertTrue(results.isEmpty());
    }


    @Test
    public void testGetPxXmlSchemaValidatorInvalid() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./mzidentml/invalid.mzid");
        List<String> results = schemaValidator.validate(new File(resource.toURI()));
        assertFalse(results.isEmpty());
    }
}