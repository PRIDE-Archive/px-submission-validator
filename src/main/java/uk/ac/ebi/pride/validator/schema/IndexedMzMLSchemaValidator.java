package uk.ac.ebi.pride.validator.schema;

import uk.ac.ebi.pride.data.util.MassSpecFileFormat;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Schema validator for Indexed mzML
 *
 * @author Rui Wang
 * @version $Id$
 */
public class IndexedMzMLSchemaValidator extends GenericXmlSchemaValidator {

    public IndexedMzMLSchemaValidator(URI schemaLocation) {
        super(schemaLocation);
    }

    @Override
    public boolean support(File input) {
        try {
            MassSpecFileFormat fileFormat = MassSpecFileFormat.checkFormat(input);
            return fileFormat != null && fileFormat.equals(MassSpecFileFormat.INDEXED_MZML);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to check file format: " + input.getAbsolutePath(), e);
        }
    }
}
