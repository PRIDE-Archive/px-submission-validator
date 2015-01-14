package uk.ac.ebi.pride.validator.schema;

import uk.ac.ebi.pride.data.util.MassSpecFileFormat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * An MGF file validator
 *
 * @author Rui Wang
 * @version $Id$
 */
public class MGFValidator
		extends GenericXmlSchemaValidator
{

	private static final String[] ALLOWED_PREFIXES =
		{
				"#",
				"COM",
				"TITLE",
				"PEPMASS",
				"RTINSECONDS",
				"SCANS",
				"CHARGE",
				"ACCESSION",
				"CLE",
				"CUTOUT",
				"CLE",
				"COMP",
				"DB",
				"DECOY",
				"ERRORTOLERANT",
				"ETAG",
				"FORMAT",
				"FRAMES",
				"INSTRUMENT",
				"IT_MODS",
				"ITOL",
				"ITOLU",
				"LOCUS",
				"MASS",
				"MODS",
				"MULTI_SITE_MODS",
				"PEP_ISOTOPE_ERROR",
				"PFA",
				"PRECURSOR",
				"QUANTITATION",
				"RAWFILE",
				"RAWSCANS",
				"REPORT",
				"REPTYPE",
				"SEARCH",
				"SEG",
				"SEQ",
				"TAG",
				"TAXONOMY",
				"TITLE",
				"TOL",
				"TOLU",
				"USER"
		};


	public MGFValidator()
	{
		super(null);
	}


	@Override
	public boolean support(File input)
	{
		try
		{
			MassSpecFileFormat fileFormat = MassSpecFileFormat
				.checkFormat(input);
			return fileFormat != null && fileFormat
				.equals(MassSpecFileFormat.MGF);
		}
		catch (IOException e)
		{
			throw new IllegalStateException(
				"Failed to check file format: " + input.getAbsolutePath(), e);
		}
	}


	@Override
	public List<String> validate(File input)
	{
		List<String> result = null;
		try
		{
			InputStream instream = new FileInputStream(input);
			if (input.getName().endsWith(".gz"))
			{
				instream = new GZIPInputStream(instream);
			}
			result = validate(instream);

		}
		catch (FileNotFoundException e)
		{
			throw new IllegalStateException(
				"Failed to find file: " + input.getAbsolutePath(), e);
		}
		catch (IOException e)
		{
			throw new IllegalStateException(
				"IO execption: " + input.getAbsolutePath(), e);
		}
		return result;
	}


	private List<String> validate(InputStream instream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(instream));
		String line = new String();
		int lineNbr = 0;
		List<String> response = new ArrayList<String>();
		boolean inSpectrum = false;
		try
		{
			while ((line = in.readLine()) != null)
			{
				lineNbr++;
				if (line.equals("BEGIN IONS"))
				{
					if (inSpectrum)
						response
							.add("Unexpected BEGIN IONS at line:" + lineNbr);
					inSpectrum = true;
				}
				else if (line.equals("END IONS"))
				{
					if (!inSpectrum)
						response.add("Unexpected END IONS at line:" + lineNbr);
					inSpectrum = false;
				}
				else if (!Pattern.matches(
					"^\\d+\\.?\\d*[ \\t]\\d+\\.?\\d*E?-?\\d?", line))
				{
					if (line.length() > 0)
					{
						boolean isOK = false;
						for (String s : ALLOWED_PREFIXES)
						{
							if (line.startsWith(s))
							{
								isOK = true;
								break;
							}
						}
						if (!isOK)
						{
							response
								.add("Unexpected line in MGF:" + lineNbr + ". " + line);
						}
					}

				}
			}
			in.close();
		}

		catch (IOException e)
		{
			response.add(e.getMessage());
		}
		return response;
	}

}
