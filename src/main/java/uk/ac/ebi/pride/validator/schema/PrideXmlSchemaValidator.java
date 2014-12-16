package uk.ac.ebi.pride.validator.schema;

import org.iso_relax.verifier.VerifierConfigurationException;
import org.xml.sax.SAXException;
import uk.ac.ebi.pride.data.util.MassSpecFileFormat;
import uk.ac.ebi.pride.tools.cl.PrideXmlClValidator;
import uk.ac.ebi.pride.tools.cl.XMLValidationErrorHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Xml schema validator for PRIDE XML files
 *
 * @author Rui Wang
 * @version $Id$
 */
public class PrideXmlSchemaValidator extends GenericXmlSchemaValidator {

    public PrideXmlSchemaValidator(URI schemaLocation) {
        super(schemaLocation);
    }

    @Override
    public boolean support(File input) {
        try {
            MassSpecFileFormat fileFormat = MassSpecFileFormat.checkFormat(input);
            return fileFormat != null && fileFormat.equals(MassSpecFileFormat.PRIDE);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to check file format: " + input.getAbsolutePath(), e);
        }
    }

    @Override
    public List<String> validate(File input) {
        PrideXmlClValidator validator = getPrideXmlSchemaValidator();

        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            XMLValidationErrorHandler xveh = validator.validate(br);
            String errorMessages = xveh.getErrorsFormattedAsPlainText();
            return Arrays.asList(errorMessages);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse PRIDE Xml file: " + input.getAbsolutePath(), e);
        } catch (VerifierConfigurationException e) {
            throw new IllegalStateException("PRIDE Xml verifier configuration exception: " + input.getAbsolutePath(), e);
        } catch (SAXException e) {
            throw new IllegalStateException("Failed to parse PRIDE Xml file: " + input.getAbsolutePath(), e);
        }
    }

    @Override
    public Map<File, List<String>> validate(List<File> inputs) {
        Map<File, List<String>> validationResults = new HashMap<File, List<String>>();

        for (File input : inputs) {
            List<String> results = validate(input);
            validationResults.put(input, results);
        }

        return validationResults;
    }

    @Override
    public Map<File, List<String>> validate(File... inputs) {
        return validate(Arrays.asList(inputs));
    }

    /**
     * initialize a generic xml schema validator
     *
     * @return generic xml schema validator
     */
    private PrideXmlClValidator getPrideXmlSchemaValidator() {
        PrideXmlClValidator validator = new PrideXmlClValidator();
        try {
            validator.setSchema(schemaLocation.toURL());
        } catch (SAXException e) {
            throw new IllegalStateException("Failed to parse XML schema: " + schemaLocation.toASCIIString(), e);
        } catch (VerifierConfigurationException e) {
            throw new IllegalStateException("XML verifier configuration exception: " + schemaLocation.toASCIIString(), e);
        } catch (IOException e) {
            throw new IllegalStateException("XML schema URI incorrect: " + schemaLocation.toASCIIString(), e);
        }
        return validator;
    }
}
