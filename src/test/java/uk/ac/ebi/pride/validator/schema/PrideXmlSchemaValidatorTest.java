package uk.ac.ebi.pride.validator.schema;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrideXmlSchemaValidatorTest {

    private PrideXmlSchemaValidator prideXmlSchemaValidator;

    @Before
    public void setUp() throws Exception {
        prideXmlSchemaValidator = new PrideXmlSchemaValidator(new URI("http://ftp.pride.ebi.ac.uk/pride/resources/schema/pride/pride.xsd"));
    }

    @Test
    public void testSupported() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./pridexml/invalid.xml");
        assertTrue(prideXmlSchemaValidator.support(new File(resource.toURI())));
    }

    @Test
    public void testSchemaInvalid() throws Exception {
        URL resource = MzIdentMLSchemaValidatorTest.class.getClassLoader().getResource("./pridexml/invalid.xml");
        List<String> results = prideXmlSchemaValidator.validate(new File(resource.toURI()));
        assertFalse(results.isEmpty());
    }

}