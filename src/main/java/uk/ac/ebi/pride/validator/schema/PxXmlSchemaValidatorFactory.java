package uk.ac.ebi.pride.validator.schema;

import uk.ac.ebi.pride.validator.IValidator;
import uk.ac.ebi.pride.validator.Validators;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Xml schema validator for ProteomeXchange submissions
 * <p/>
 * Currently, supports:
 * PRIDE XML
 * mzIdentML
 * mzML
 *
 * @author Rui Wang
 * @version $Id$
 */
public final class PxXmlSchemaValidatorFactory {

    public static final String DEFAULT_PRIDE_XML_SCHEMA = "http://ftp.pride.ebi.ac.uk/pride/resources/schema/pride/pride.xsd";
    public static final String DEFAULT_MZIDENTML_SCHEMA = "http://www.psidev.info/sites/default/files/mzIdentML1.1.0.xsd";
    public static final String DEFAULT_MZML_SCHEMA = "http://www.psidev.info/files/ms/mzML/xsd/mzML1.1.0.xsd";
    public static final String DEFAULT_INDEXED_MZML_SCHEMA = "http://www.psidev.info/files/ms/mzML/xsd/mzML1.1.1_idx.xsd";

    public static IValidator<File, String> getPxXmlSchemaValidator() {
        try {
            // pride xml validator
            PrideXmlSchemaValidator prideXmlSchemaValidator = new PrideXmlSchemaValidator(new URI(DEFAULT_PRIDE_XML_SCHEMA));

            // mzIdentML validator
            MzIdentMLSchemaValidator mzIdentMLSchemaValidator = new MzIdentMLSchemaValidator(new URI(DEFAULT_MZIDENTML_SCHEMA));

            // mzML validator
            IndexedMzMLSchemaValidator indexedMzMLSchemaValidator = new IndexedMzMLSchemaValidator(new URI(DEFAULT_INDEXED_MZML_SCHEMA));

            // mzML validator
            MzMLSchemaValidator mzMLSchemaValidator = new MzMLSchemaValidator(new URI(DEFAULT_MZML_SCHEMA));
            
            // MGF validator
           MGFValidator mgfValidator = new MGFValidator();

            return Validators.compose(prideXmlSchemaValidator, mzIdentMLSchemaValidator, indexedMzMLSchemaValidator, mzMLSchemaValidator, mgfValidator);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to initialize validators", e);
        }
    }

}
