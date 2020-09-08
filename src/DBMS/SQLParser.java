package DBMS;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SQLParser {					//WHILE IT IS THE MAIN CLASS SINCE IT'S CALLED 'SQLPARSER'
											
	
		private static SQLParser ser;
		private ArrayList<QueryCommand>queries;
		private StringBuilder sb;
		public static SQLParser getInstance(){
			if (ser==null){
				ser = new SQLParser();
			}
			return ser;
		}
		//THIS FUNCTION SEPARATES DIFFERENT STATEMENT FOR INDEPENDANT PROCESSING
		//EDITORPANE TEXT IS SPLIT USING SEMICOLONS AS DELIMITERS
		public void constructQueries(String editorField){
			if (sb==null){sb=new StringBuilder();}
			queries=new ArrayList<>();
			for (String statement: editorField.split(";")) {
				sb.append(statement);
				sb.append(";");
				QueryCommand query = new QueryCommand();
				query.setQuery(sb.toString());
				queries.add(query);
				sb.setLength(0);
				}
		}
		//USELESS FUNCTION FOR TESTING PURPOSES
		public void displayQueries(){
			for (QueryCommand q:queries){
				q.display();
			}
		}
		//FUNCTION FROM QUERY.JAVA ON MULTIPLE ARRAYS
		//THE INFORMATION LEAVING THIS FUNCTION GOES INTO THE XML FILES
		//THIS IS WHERE WE SHOULD CONNECT XMLPARSING AND SQLPARSING CLASSES
		public List< List <String> > processQueries(){
			List<List<String>>ret=null;
			for (QueryCommand q:queries){
				ret=q.processQuery();				//THIS FUNCTION RETURNS A LIST OF GENERIC TYPES
												//IT CAN ONLY BE LIST<STRING> OR LIST<LIST<STRING>>
						         				//ORDER OF ITEMS IS WRITTEN INSIDE FUNCTION
			}
			return ret;
		}
}