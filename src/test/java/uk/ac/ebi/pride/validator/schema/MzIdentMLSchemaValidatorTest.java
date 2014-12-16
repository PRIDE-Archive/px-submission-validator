package uk.ac.ebi.pride.validator.schema;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MzIdentMLSchemaValidatorTest {

    private MzIdentMLSchemaValidator mzIdentMLSchemaValidator;

    @Before
    public void setUp() throws Exception {
        mzIdentMLSchemaValidator = new MzIdentMLSchemaValidator(new URI("http://www.psidev.info/sites/default/files/mzIdentML1.1.0.xsd"));
    }

    @Test
    public void testSchemaInvalid() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./mzidentml/invalid.mzid");
        List<String> results = mzIdentMLSchemaValidator.validate(new File(resource.toURI()));
        assertFalse(results.isEmpty());
    }

    @Test
    public void testSupported() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./mzidentml/invalid.mzid");
        assertTrue(mzIdentMLSchemaValidator.support(new File(resource.toURI())));
    }
}