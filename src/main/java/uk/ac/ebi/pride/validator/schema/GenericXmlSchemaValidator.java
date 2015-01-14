package uk.ac.ebi.pride.validator.schema;

import org.xml.sax.SAXException;

import uk.ac.ebi.pride.tools.ErrorHandlerIface;
import uk.ac.ebi.pride.tools.GenericSchemaValidator;
import uk.ac.ebi.pride.tools.ValidationErrorHandler;
import uk.ac.ebi.pride.validator.IValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * A generic xml file validator based on its schema
 *
 * @author Rui Wang
 * @version $Id$
 */
public abstract class GenericXmlSchemaValidator
		implements IValidator<File, String>
{

	protected final URI schemaLocation;


	public GenericXmlSchemaValidator(URI schemaLocation)
	{
		this.schemaLocation = schemaLocation;
	}


	@Override
	public List<String> validate(File input)
	{
		GenericSchemaValidator genericValidator = getGenericSchemaValidator();

		ErrorHandlerIface handler = new ValidationErrorHandler();
		genericValidator.setErrorHandler(handler);
		BufferedReader br = null;
		try
		{
			InputStream instream = new FileInputStream(input);
			if (input.getName().endsWith(".gz"))
			{
				instream = new GZIPInputStream(instream);
			}
			br = new BufferedReader(new InputStreamReader(instream));
			genericValidator.validate(br);
		}
		catch (FileNotFoundException e)
		{
			throw new IllegalStateException(
				"Failed to find file: " + input.getAbsolutePath(), e);
		}
		catch (SAXException e)
		{
			throw new IllegalStateException(
				"XML parsing execption: " + input.getAbsolutePath(), e);
		}
		catch (IOException e)
		{
			throw new IllegalStateException(
				"IO execption: " + input.getAbsolutePath(), e);
		}

		Collection<String> errorMessages = handler.getErrorMessages();
		return new ArrayList<String>(errorMessages);
	}


	@Override
	public Map<File, List<String>> validate(List<File> inputs)
	{
		Map<File, List<String>> validationResults = new HashMap<File, List<String>>();

		for (File input : inputs)
		{
			List<String> results = validate(input);
			validationResults.put(input, results);
		}

		return validationResults;
	}


	@Override
	public Map<File, List<String>> validate(File... inputs)
	{
		return validate(Arrays.asList(inputs));
	}


	/**
	 * initialize a generic xml schema validator
	 *
	 * @return generic xml schema validator
	 */
	private GenericSchemaValidator getGenericSchemaValidator()
	{
		GenericSchemaValidator genericValidator = new GenericSchemaValidator();
		try
		{
			genericValidator.setSchema(schemaLocation);
		}
		catch (SAXException e)
		{
			throw new IllegalStateException(
				"Failed to parse XML schema: " + schemaLocation.toASCIIString(),
				e);
		}
		catch (MalformedURLException e)
		{
			throw new IllegalStateException(
				"XML schema URI incorrect: " + schemaLocation.toASCIIString(),
				e);
		}
		return genericValidator;
	}
}
