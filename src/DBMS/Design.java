package DBMS;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultStyledDocument;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;

public class Design extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			QueryHandler.ValidateXml("rania");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XMLStreamException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Design frame = new Design();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Design() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 542, 339);
		contentPane.add(scrollPane);
		
		JEditorPane editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){				//THE RUN BUTTON, TAKES TEXT FROM EDITORPANE & OPERATES ON IT
				SQLParser.getInstance().constructQueries(editorPane.getText());
				String query=editorPane.getText();
		 List<List<String> >data  = SQLParser.getInstance().processQueries();
		 if(data!=null){
			 Parser parser = new Parser();
			  if(data.get(0).get(0).equalsIgnoreCase("CREATE")||data.get(0).get(0).equalsIgnoreCase("DROP")){
				 try {
					System.out.println(parser.executeStructureQuery(query));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 else if(data.get(0).get(0).equalsIgnoreCase("INSERT")||data.get(0).get(0).equalsIgnoreCase("DELETE")){
				 try {
					System.out.println(parser.executeUpdateQuery(query));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 else if(data.get(0).get(0).equalsIgnoreCase("SELECT")){
				 Object[][] x=null;
				try {
					x = parser.executeRetrievalQuery(query);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  for(int i=0;i<x.length;i++){
					  for(int j=0;j<x[i].length;j++)
					     System.out.println(x[i][j]);
				  }
			 }
			 /// prints the query after being processed
		/*  for(int i=0;i<print.size();i++){	
			  for(int j=0;j<print.get(i).size();j++){
				 System.out.print(print.get(i).get(j)+" - ");
			 }
			 System.out.println();
		  }*/
		 }
		 else{
			 System.out.println("ERROR");
		 }
			}
		});
		btnRun.setBounds(226, 365, 97, 25);
		contentPane.add(btnRun);
	}
}
