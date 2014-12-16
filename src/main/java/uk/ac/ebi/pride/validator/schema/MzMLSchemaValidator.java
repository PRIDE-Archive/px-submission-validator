package uk.ac.ebi.pride.validator.schema;

import uk.ac.ebi.pride.data.util.MassSpecFileFormat;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Xml schema validator for mzML
 *
 * @author Rui Wang
 * @version $Id$
 */
public class MzMLSchemaValidator extends GenericXmlSchemaValidator {

    public MzMLSchemaValidator(URI schemaLocation) {
        super(schemaLocation);
    }

    @Override
    public boolean support(File input) {
        try {
            MassSpecFileFormat fileFormat = MassSpecFileFormat.checkFormat(input);
            return fileFormat != null && fileFormat.equals(MassSpecFileFormat.MZML);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to check file format: " + input.getAbsolutePath(), e);
        }
    }
}
