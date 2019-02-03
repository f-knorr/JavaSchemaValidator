# JavaSchemaValidator
Simple Java programm to validate an existing xml-file against one or more schema files xsd

## Usage
After exporting the project as a jar-File (e.g., Your_jar_file.jar), there are two possibilities to run the code
* specify the xml-file to be validated and a directory that contains all XSDs: `java -jar Your_jar_file.jar /home/test.xml /home/xsd`
* specify the xml-file to be validated and each XSD you want the xml-file to be validated against: `java -jar Your_jar_file.jar /home/test.xml /home/1.xsd /home/2.xsd`

## Sample Output
```
Benutze Schema: /home/Schreibtisch/schema/XML_Validate_Schemas_1810_S3/Schemas_1810_S3/Mod.xsd
Benutze Schema: /home/Schreibtisch/schema/XML_Validate_Schemas_1810_S3/Schemas_1810_S3/Chapter.xsd
Validating 9691278347.xml against XSDs [/home/Schreibtisch/schema/XML_Validate_Schemas_1810_S3/Schemas_1810_S3/Mod.xsd, /home/Schreibtisch/schema/XML_Validate_Schemas_1810_S3/Schemas_1810_S3/Chapter.xsd]...
ERROR: Konnte die Datei 9691278347.xml nicht validieren gegen XSDs [/home/Schreibtisch/schema/XML_Validate_Schemas_1810_S3/Schemas_1810_S3/Mod.xsd, /home/Schreibtisch/schema/XML_Validate_Schemas_1810_S3/Schemas_1810_S3/Chapter.xsd]


FEHLER: org.xml.sax.SAXParseException; systemId: file:/home/Schreibtisch/schema/XML_Validate_Schemas_1810_S3/9691278347.xml; lineNumber: 49; columnNumber: 86567; cvc-complex-type.2.1: Element 'spe:PARAMETER' darf kein Zeichen- oder Elementinformationselement [untergeordnete Elemente] haben, da der Contenttyp des Typs leer ist.
```
