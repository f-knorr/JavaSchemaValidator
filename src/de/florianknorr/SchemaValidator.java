package de.florianknorr;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;


/**
 * This class is inspired by and modified from
 * http://marxsoftware.blogspot.com/2015/03/validating-xml-against-xsd-in-java.html
 */
public class SchemaValidator {

  /** 
   * Validates provided XML against provided XSD. 
   * 
   * @param arguments XML file to be validated (first argument) and 
   *    XSD against which it should be validated (second and later 
   *    arguments). 
   */  
  public static void main(final String[] arguments)  
  {  
     if (arguments.length < 1)  
     {  
        System.out.println("\nUSAGE: java <xml-Datei> <Ordnerpfad der xsd-Dateien>\n");
        System.out.println("\nUSAGE: java <xml-Datei> <xsd-Datei 1> <xsd-Datei 2> <xsd-Datei 3> ...\n");
        System.out.println("\tReihenfolge des XSDs kann das Ergebnis beeinflussen (nenne die XSDs, die");  
        System.out.println("\tvon anderen abhängen zuletzt)");  
        System.exit(-1);  
     }  
     // Arrays.copyOfRange requires JDK 6; see  
     // http://stackoverflow.com/questions/7970486/porting-arrays-copyofrange-from-java-6-to-java-5  
     // for additional details for versions of Java prior to JDK 6.  
     String[] schemas;
     if (arguments.length == 1) {
    	 File folder = new File("xsd");
       System.err.println("Kein Ordnerpfad für XSD-Datei(en) angegeben.\n"
       		+ "Versuche: "+folder.getAbsolutePath());
       schemas = fillSchemaArray(folder);
     } else if (arguments.length > 2) {
       schemas = Arrays.copyOfRange(arguments, 1, arguments.length);  
     } else {
    	 File folder = new File(arguments[1]);
    	 schemas = fillSchemaArray(folder);   	 
     }
     validateXmlAgainstXsds(arguments[0], schemas);  
  }  

  private static String[] fillSchemaArray(File folder) {
   File[] listOfFiles = folder.listFiles();
   String[] schemaArray = new String[listOfFiles.length];
 	 for (int idx = 0; idx < listOfFiles.length; idx++) {
 	   if (listOfFiles[idx].getName().toLowerCase().endsWith(".xsd")) {
 	  	schemaArray[idx] = listOfFiles[idx].getAbsolutePath();
      System.out.println("Benutze Schema: "+schemaArray[idx]); 
 	   }
 	 }
 	 return schemaArray;
  }
  /** 
   * Validate provided XML against the provided XSD schema files. 
   * 
   * @param xmlFilePathAndName Path/name of XML file to be validated; 
   *    should not be null or empty. 
   * @param xsdFilesPathsAndNames XSDs against which to validate the XML; 
   *    should not be null or empty. 
   */  
  public static void validateXmlAgainstXsds(  
     final String xmlFilePathAndName, final String[] xsdFilesPathsAndNames)  
  {  
     if (xmlFilePathAndName == null || xmlFilePathAndName.isEmpty())  
     {  
        System.err.println("ERROR: Path/name of XML to be validated cannot be null.");  
        return;  
     }  
     if (xsdFilesPathsAndNames == null || xsdFilesPathsAndNames.length < 1)  
     {  
        System.err.println("ERROR: At least one XSD must be provided to validate XML against.");  
        return;  
     }  
     final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);  
    
     final StreamSource[] xsdSources = generateStreamSourcesFromXsdPathsJdk8(xsdFilesPathsAndNames);  
    
     boolean success = true;
     try  
     {  
        final Schema schema = schemaFactory.newSchema(xsdSources);  
        final Validator validator = schema.newValidator();  
        System.out.println(  "Validating " + xmlFilePathAndName + " against XSDs "  
                    + Arrays.toString(xsdFilesPathsAndNames) + "...");  
        validator.validate(new StreamSource(new File(xmlFilePathAndName)));  
     }  
     catch (IOException | SAXException exception)  // JDK 7 multi-exception catch  
     {  
    	  success = false;
        System.err.println(  
             "ERROR: Konnte die Date " + xmlFilePathAndName  
           + " nicht validieren gegen XSDs " + Arrays.toString(xsdFilesPathsAndNames));  
        System.err.println("\n\nERROR: " + exception.toString().replace(";", ";\n"));  
     }
     if (success) {
    	 System.out.println("Validierung erfolgreich abgeschlossen."); 
     }  
  }


  /** 
   * Generates array of StreamSource instances representing XSDs 
   * associated with the file paths/names provided and use JDK 8 
   * Stream API. 
   * 
   * This method can be commented out if using a version of 
   * Java prior to JDK 8. 
   * 
   * @param xsdFilesPaths String representations of paths/names 
   *    of XSD files. 
   * @return StreamSource instances representing XSDs. 
   */  
  private static StreamSource[] generateStreamSourcesFromXsdPathsJdk8(  
     final String[] xsdFilesPaths)  
  {  
     return Arrays.stream(xsdFilesPaths)  
                  .map(StreamSource::new)  
                  .collect(Collectors.toList())  
                  .toArray(new StreamSource[xsdFilesPaths.length]);  
  }
}
