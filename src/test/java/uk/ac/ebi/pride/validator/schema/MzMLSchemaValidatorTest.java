package uk.ac.ebi.pride.validator.schema;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MzMLSchemaValidatorTest {

    private MzMLSchemaValidator mzmlSchemaValidator;

    @Before
    public void setUp() throws Exception {
        mzmlSchemaValidator = new MzMLSchemaValidator(new URI("http://www.psidev.info/files/ms/mzML/xsd/mzML1.1.0.xsd"));
    }

    @Test
    public void testSupported() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./mzml/valid.mzML");
        assertTrue(mzmlSchemaValidator.support(new File(resource.toURI())));
    }

    @Test
    public void testSchemaValid() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./mzml/valid.mzML");
        List<String> results = mzmlSchemaValidator.validate(new File(resource.toURI()));
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSchemaInvalid() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./mzml/invalid.mzML");
        List<String> results = mzmlSchemaValidator.validate(new File(resource.toURI()));
        assertFalse(results.isEmpty());
    }
}