px-submission-validator
=======================

This is a simplified version of the ProteomeXchange submission validation process. 

At the moment, the px-submission-validator supports:
  1. Xml schema validation on PRIDE XML, mzML, mzIdentML files
  
## How To



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
