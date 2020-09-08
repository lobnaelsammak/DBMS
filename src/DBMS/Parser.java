package DBMS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class Parser implements Database {
     // query validation is just checking on the name of the values

	@Override	
	public boolean executeStructureQuery(String query) throws SQLException {/// create or drop
		SQLParser.getInstance().constructQueries(query);
		List<List<String>>data=SQLParser.getInstance().processQueries();
	    if(data==null)
		   return false;	  
		if(data.get(0).get(0).equalsIgnoreCase("CREATE")){//create
			   String tableName=data.get(0).get(1);
			   QueryHandler.Create(tableName);
			   List<String> element=new ArrayList<String>();
			   List<String> dataType=new ArrayList<String>();
			   for(int i=0;i<data.get(1).size();i++){
				   if(i%2==1){
					   dataType.add(data.get(1).get(i));
					   if(data.get(1).get(i).equals("varchar")==false&&data.get(1).get(i).equals("int")==false){
						   System.out.println("INVALID DATATYPE\n");
						   return false;
					   }
				   }
				   else
					   element.add(data.get(1).get(i));
				//   System.out.println(data.get(1).get(i));
			   }
			   QueryHandler.CreateXSD(tableName, element, dataType);
			   return true;
		}
		else{//Drop
			   String fileName=QueryHandler.getFileName(data.get(0).get(1));
			   System.out.println(fileName);
			   File file=new File(fileName);
				  if(file.exists()){
		              file.delete();
					  return true;
				  }
			   return false;
		}
	}
	@Override
	public Object[][] executeRetrievalQuery(String query) throws SQLException {/// select
		   SQLParser.getInstance().constructQueries(query);
		   List<List<String>>data=SQLParser.getInstance().processQueries();
		   if(data==null)
			   return null;	  
			List<String>elements=new ArrayList<String>();
			String tableName=data.get(0).get(1);
		    String condition=data.get(0).get(2);
		    String []s=condition.split("[\\s<>=';]+");
		    elements.add(s[0]);
		    elements.add(s[1]);
			 if(query.contains("'"))
		        return	QueryHandler.select(elements, 0,tableName ,data.get(1));
		        else if(query.contains("<"))
		        return	QueryHandler.select(elements, 1,tableName, data.get(1));
		        else if(query.contains(">"))
		        return	QueryHandler.select(elements, 2,tableName, data.get(1));
		        else if(query.contains("="))
		        return 	QueryHandler.select(elements, 3,tableName, data.get(1));
		
		return null;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		SQLParser.getInstance().constructQueries(query);
		List<List<String>>data=SQLParser.getInstance().processQueries();
	    if(data==null)
		   return 0;	  
		if(data.get(0).get(0).equalsIgnoreCase("DELETE")){//create
			/// validation is to check that the first element in the list is in the dtd file
			String tableName=data.get(0).get(1);
		    List<String>elements = new ArrayList<String>();
		    String condition=data.get(0).get(2);
		    String []s=condition.split("[;<>=']+");
		    elements.add(s[0]);
		    elements.add(s[1]);
		        if(query.contains("'"))
		        	return	QueryHandler.delete(elements, 0, tableName);
		        else if(query.contains("<"))
		        	return	QueryHandler.delete(elements, 1, tableName);
		        else if(query.contains(">"))
		        	return	QueryHandler.delete(elements, 2, tableName);
		        else if(query.contains("="))
		        	return QueryHandler.delete(elements, 3, tableName);
		}
		else {//insert
			/// assumes data is cool wl validation f queryhandler
			String tableName=data.get(0).get(1);
	       return QueryHandler.insert(data.get(1), tableName);
		}
		return 0;
	}
	
}
