px-submission-validator
=======================

This is a simplified version of the ProteomeXchange submission validation process. 

At the moment, the px-submission-validator supports:
  1. Xml schema validation on PRIDE XML, mzML, mzIdentML files
  
## How To

The px-submission-validator currently offers a group of XML schema validators. You can either use individual validator
 or use the aggregated validator.

For individual validator, providing you know the file format, you can choose a validator in the _uk.ac.ebi.pride.validator.schema_ package.

For example, you have a mzML file need to be validated. You can do the following in your code:

```code
  MzMLSchemaValidator mzmlSchemaValidator = new MzMLSchemaValidator(new URI("http://www.psidev.info/files/ms/mzML/xsd/mzML1.1.0.xsd"));
  mzmlSchemaValidator.validate([your file])
```

For aggregated validator, in case of you want to valid multiple files in different file format, you can use _PxXmlSchemaValidatorFactory_:

```code
  IValidator<File, String> schemaValidator = PxXmlSchemaValidatorFactory.getPxXmlSchemaValidator();
  schemaValidator.validate([your file])
```

## Maven dependency
You need to add the following repository to your project's pom file:

```xml
  <repository>
    <id>nexus-ebi-repo</id>
    <name>The EBI internal repository</name>
    <url>http://www.ebi.ac.uk/intact/maven/nexus/content/repositories/ebi-repo/</url>
    <releases>
    </releases>
    <snapshots>
    <enabled>false</enabled>
    </snapshots>
  </repository>
```
  
More importantly, you need the dependency below:

```xml
  <dependency>
    <groupId>uk.ac.ebi.pride</groupId>
    <artifactId>px-submission-validator</artifactId>
    <version>1.0.0</version>
  </dependency>
```
