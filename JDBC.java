/* =======================================================================
   A generic GUI for the JDBC project

   Author: Shun Yan Cheung

   To compile the project: 

	javac JDBC.java

   To run the project: 

	java -cp ".:/home/cs377001/lib/mysql-connector-java.jar" JDBC
	
	Honor Code: This work is completed only by Melanie without consulting others except the instructor and TA
   ======================================================================= */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
public class JDBC  
{
   public static JFrame mainFrame;

   /* ***************************************************************
      The class variables (defined using static) defined in this area
      can be accessed by other "call back" classes

      You can define your SHARED program variables below this line

      SHARED variables are variables that you need to use in
      MULTIPLE methods

      (If you ONLY use a variable inside ONE method, define that
       variable as a LOCAL variable inside THAT method)
      *************************************************************** */
	public  static boolean DatabaseSelected;
 	public	static Statement stmt=null ;
	public static Connection conn=null;
	public static ResultSet rset;
	public static ResultSetMetaData meta;
	public static int NCols=0;
	public static String s="";
	 
                
   /* *********************************************************
      TODO 1: Define you SHARED program variables here

	      SHARED variables are variables that will can 
	      be used by methods in *different* classes
      ********************************************************* */




   /* ********************************************************************
      End area where you define your SHARED program variables
      ******************************************************************* */

   /* vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      This area contain SHARED object variables for the GUI application

      Do NOT make any changes to the variable sections below
      You can use these GUI variables to code your GUI application:

	Input:  a JTextArea object
		Input.getText() will return the SQL query that you
		need to execute
	Output:  a JTextArea object
		(1) Use Output.setText("") to clear the text area
		(2) Then use Output.append( ... ) to print text
		    Use Output.append("\n") to advance to next line

	DBName: a JTextField object. 
		DBName.getText() will return the database name
	Select: a JButton object.
		Activate this object to process "Select database" function
	Execute: a JButton object.
		Activate this object to process "Execute query" function

	Column: a JTextField object.
		Column.getText() will return the column number
	Max: a JButton object.
		Activate this object to process "Max" function
	Min: a JButton object.
		Activate this object to process "Min" function
	Avg: a JButton object.
		Activate this object to process "Avg" function
	Median: a JButton object.
		Activate this object to process "Median" function
      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */

   /* ==========================================================
      The query input area
      ========================================================== */
   public static JTextArea Input = new JTextArea();   // Query input !!!
                                                      // Get the query text here

   /* ==========================================================
      The query output area
      ========================================================== */
   public static JTextArea Output = new JTextArea();  // Query output !!
                                                      // Print tuples here

   /* ==========================================================
      The TOP-RIGHT panel things
      ========================================================== */
   // Database label and name
   public static JLabel DBLabel = new JLabel("Database: ");
   public static JTextField DBName = new JTextField();     // Database name !!

   // The database selection button
   public static JButton Select = new JButton("Select");   // Select button !!

   // The query execution button
   public static JButton Execute = new JButton("Execute"); // Execute button !!


   /* ==========================================================
      The BOTTON-RIGHT panel things
      ========================================================== */
   // The column selection field
   public static JLabel ColumnLabel = new JLabel("Column: ");
   public static JTextField Column = new JTextField();       // Column number !!

   // The MAX output field  and button
   public static JTextField MaxText = new JTextField();
   public static JButton Max = new JButton("Maximum");       // Max button !!

   // The MIN output field  and button
   public static JTextField MinText = new JTextField();
   public static JButton Min = new JButton("Minimum");       // Min button !!

   // The Average output field  and button
   public static JTextField AvgText = new JTextField();
   public static JButton Avg = new JButton("Average");       // Avg button !!

   // The Median output field  and button
   public static JTextField MedianText = new JTextField();
   public static JButton Median = new JButton("Median");     // Median button !!



   /* ********************************************************************
      The main program
      ******************************************************************** */
   public static void main( String[] args )
   {






   try
	   	 {
	   	    // Load the MySQL JDBC driver
	   	    Class.forName("com.mysql.jdbc.Driver");
//		    System.out.println(1);
	   	 }
	   	 catch (Exception e)
	   	 {
	   	    Output.setText("Cannot load the JDBC drive");
	   	    
	   	 }

      /* ===============================================
	 Make the window
         =============================================== */
      MakeGUIWindow();


      /* =====================================================
	 Make the Select button active:
         ===================================================== */
      Select.addActionListener(new SelectListener() );
		// Make Select button execute the actionPerform() method
		// inside the class SelectListener (see below)

      /* **********************************************************
	 TODO 2: make the OTHER buttons active 
                 (Execute, Max, Min, Avg, Median)

		 Make the buttons perform the functions given in HW
         ********************************************************** */

       // I did this in class....
       Execute.addActionListener(new myExecuteClass() );
       Max.addActionListener(new MaxListener());
       Min.addActionListener(new MinListener());
       Avg.addActionListener(new AvgListener());
       Median.addActionListener(new MedianListener());

     /* ***************************************

test should be delete
double myNumber = Double.parseDouble("1234.2345");
				String decimal = "0.";
				
				for (int i = 0;i<2;i++){
					decimal+="0";
				}
  DecimalFormat form = new DecimalFormat(decimal);
				//String number;
String s;
 				s =form.format(myNumber).toString();
System.out.println(s);
				

***************************************** */




   }


   // I did this in class....
   static class myExecuteClass implements ActionListener            
   {
      public void actionPerformed(ActionEvent e)
      {
Output.setText("");
 if ( DatabaseSelected == false) {
    		  Output.setText("No database was selected");
    		  return;
    	  }

	 
 //int[] length = new int[100];
	 s = Input.getText( );
                  try
    	   	    {
    	   	       rset = stmt.executeQuery( s );      // Exec query
    	    
    	   	       meta = rset.getMetaData();  // Get meta data
    	    
    	   	       NCols = meta.getColumnCount();
			System.out.println("rset completed");
    		      int line=0;

                        



			   
    	   	       /* ===================================================
    	   		  **** Print the column names before any data ****
    	   		  =================================================== */
    	   	       for ( int i = 1; i <= NCols; i++ )
    	   	       {
			      String name;
	    		     name = meta.getColumnName(i);


				int columnwidth =0;//initailly, set every column's width to 0, and store column width into this variable later
				
			 if (meta.getColumnType(i) == java.sql.Types.DECIMAL || 
			    meta.getColumnType(i) == java.sql.Types.INTEGER)
		  	{
				columnwidth = 10;
	 			line += columnwidth;
			
			}

			else{//if it is string type
                            columnwidth=Math.max(6,meta.getColumnDisplaySize(i));
			      line += columnwidth;

                             }
    	   		  
			if (name.length()>10){name=name.substring(0,10);}
    	   		  Output.append( name );  // Print field name
    	
    	   
    	   		  /* ----------------------------------------------
    	   		     ****Pad**** the attr name i to columnwidth
    	   		     ---------------------------------------------- */
    	   		  for (int j = name.length(); j < columnwidth+1; j++)
    	   		     Output.append(" ");
			    
    	   	       }
    	   	       Output.append("\n");
    	    
    	               /* ---------------------------------
    	                 Print a dividing line....
    			 --------------------------------- */
    	   	     
    	   		  for ( int j = 1; j <= line+NCols-1; j++)
    	   		      Output.append("=");   // Print a dividing line
    	   	       Output.append("\n");
    	   
    	   	       /* ===========================================
    	   		  Fetch and print one row at a time....
    	   		  =========================================== */
    	   	       while ( rset.next () )    // Advance to next row
    	   	       {
    	   		  /* ===========================================
    	   		     Fetch the columns (attributes) from a row
    	   		     =========================================== */
    	   		  for(int i = 1; i <= NCols; i++)
    	   		  {
 			  int columnwidth =0;
			

		         if (meta.getColumnType(i) == java.sql.Types.DECIMAL || 
		 	    meta.getColumnType(i) == java.sql.Types.INTEGER)
		  	  {//type is decimal or integer
			    columnwidth = 10;
    	   		     String nextItem;
			     int scale;
    	    
    	   		     nextItem = rset.getString(i);
		             scale = meta.getScale(i);
				


			    if (nextItem == null){
				for (int j = 1;j<=(columnwidth-4);j++){Output.append(" ");}//right align,pad space first
				 Output.append("NULL");
				Output.append(" ");
                              }
			   else{//if it is not equal to null
				if (scale!=0){//means it is decimal
   				double myNumber = Double.parseDouble(nextItem);
				String decimal = "0.";
				for (int m= 0;m<scale;m++){
					decimal+="0";
				}
				

                 DecimalFormat form = new DecimalFormat(decimal);
				//String number;

 				nextItem =form.format(myNumber).toString();
				
				}

				
				for (int j = nextItem.length(); j < columnwidth; j++)
    	   			 Output.append(" ");
				Output.append(nextItem);
				Output.append(" ");
				}
			}
			
			else{//type is string, left align
			      columnwidth=Math.max(6,meta.getColumnDisplaySize(i));
			  String nextItem;
    	    
    	   		     nextItem = rset.getString(i);
				
			     if (nextItem == null){
				 Output.append("NULL");
				for (int j = 1;j<=(columnwidth-4);j++){Output.append(" ");}//right align,pad space first
				
				Output.append(" ");
                              }
			   else{
				//if it is not equal to null
				Output.append(nextItem);
				for (int j = nextItem.length(); j < columnwidth; j++)
    	   			 Output.append(" ");
				Output.append(" ");
				}
			}	
    	  }
    	   		  Output.append("\n");
    	    
    	   		}
    	    
    	   		//rset.close();           // Free result set buffers 
    	   	    }
    	   	    catch (Exception error)
    	   	    {
    	   	       String errMsg=error.getMessage();
                       Output.setText(errMsg); // Print the error message
    	   	   
    	   	    }


      }
   }


   /* ===============================================================
      ***** Sample "call back" class

      Listener class for the Select button
      =============================================================== */
   static class SelectListener implements ActionListener            
   {  
      /* =============================================================
         The "actionPerformed( )" method will be called
	 when a button is pressed

	 You must ASSOCIATE a button to this class using this call:

	    ButtonObject.addActionListener( new SelectListener( ) )
         ============================================================= */
      public void actionPerformed(ActionEvent e)
      {  
	 /* ===========================================================
	    You must replace these statements with statements that
	    perform the "SELECT button" function:

		1. make a connection to the database server
		2. allocate statement object to prepare for 
                   query execution
	    =========================================================== */

         // =============================================================
         // Dummy statements to show that the activation was successful
         // =============================================================
     String url = "jdbc:mysql://holland.mathcs.emory.edu:3306/";
   	 String dbName = DBName.getText();
   	 String userName = "cs377";
   	 String password = "abc123";
	DatabaseSelected =false; 
	
	
	

	if(stmt!=null){


              try {
                  stmt.close();
                      System.out.println("Free Statement object resources");
               }catch(Exception error){
                       Output.setText("Cannot Free  Statement object resources");
                }

        }


	if (conn!=null){

                try{
		conn.close();
		System.out.println("Close SQL server connection");


                }catch(Exception error){
			Output.setText("cannot close SQL server connection");
                 } 
           }

	try
   	 {
   	    // Connect to the database
   	    conn = DriverManager.getConnection(url+dbName,userName,password);
   
   	    // Create statement object to send query to SQL server               
   	    stmt = conn.createStatement ();
 DatabaseSelected = true;
    	   	    System.out.println(DatabaseSelected);
 	    System.out.println("Connection established to " + dbName);
   	 }
   	 catch (Exception error)
   	 {

	Output.setText("Cannot open databass"+DBName.getText());
   	 } 

       



	System.out.println("The method actionPerformed"); 
         System.out.println("in the class SelectListener was called\n");
      }
   }

   /* ===============================================================
      TODO 3:
      ***** Write OTHER "call back" classes here for other buttons
            (Execute, Max, Min, Avg, Median)
      =============================================================== */
   
   static class MinListener implements ActionListener {
	   
	   public void actionPerformed(ActionEvent e) {
		   
		   String columnNum = Column.getText();
		   int ColumnNum = Integer.parseInt(columnNum);//this give us the integer of the column we want to calculate
		   if (ColumnNum>NCols) {
			   MinText.setText("col # err");
			   return;
		   }
		   
		   //after handling the error, find certian column and retreive number
		   Double doublemin = Double.POSITIVE_INFINITY;
		   String stringmin = "";
		   try {
			s=Input.getText();
			rset=stmt.executeQuery(s);
			meta=rset.getMetaData();
			if (meta.getColumnType(ColumnNum) == java.sql.Types.DECIMAL ||      
				meta.getColumnType(ColumnNum) == java.sql.Types.INTEGER )
				    {
				       // process column data as float
					if(rset.getString(ColumnNum)!=null){
					doublemin = rset.getDouble(ColumnNum);
					}
                                 	//
				    }
				    else
				    {       while(rset.next()){

					if (rset.getString(ColumnNum)!=null){
					stringmin = rset.getString(ColumnNum);
					rset.beforeFirst();
					break;}

                                    }
				       // process column data as string
				    	
						
				



					//MinText.setText(stringmin);
			    }
		} catch (Exception e1) {
			
		}



                try {
			if (meta.getColumnType(ColumnNum) == java.sql.Types.DECIMAL ||      
				meta.getColumnType(ColumnNum) == java.sql.Types.INTEGER )
				    {
					    while(rset.next()){
						if(rset.getString(ColumnNum)==null) continue;
						if (rset.getDouble(ColumnNum)<doublemin) doublemin=rset.getDouble(ColumnNum);
                                          }

					MinText.setText(String.valueOf(doublemin));


                                     }
			else{
				while(rset.next()){

					if(rset.getString(ColumnNum)==null) continue;
						if (rset.getString(ColumnNum).compareTo(stringmin)<0) stringmin=rset.getString(ColumnNum);

                                }

				MinText.setText(stringmin);
			}





		}catch (Exception e1) {
			
		}
		
		   
		   
		   
		   
	   }
   }
   
   
 static class MaxListener implements ActionListener {
	   
	   public void actionPerformed(ActionEvent e) {
		   String columnNum = Column.getText();
		   int ColumnNum = Integer.parseInt(columnNum);//this give us the integer of the column we want to calculate
		   if (ColumnNum>NCols) {
			   MaxText.setText("col # err");
			   return;
		   }
		   
		   //after handling the error, find certian column and retreive number
		   Double doublemax = 0.00;
		   String stringmax = "";
		  
		     try {
			s=Input.getText();
			rset=stmt.executeQuery(s);
			meta=rset.getMetaData();
			if (meta.getColumnType(ColumnNum) == java.sql.Types.DECIMAL ||      
				meta.getColumnType(ColumnNum) == java.sql.Types.INTEGER )
				    {
				       // process column data as float
					if(rset.getString(ColumnNum)!=null){
					doublemax = rset.getDouble(ColumnNum);
					}
                                 	//
				    }
				    else
				    {       while(rset.next()){

					if (rset.getString(ColumnNum)!=null){
					stringmax = rset.getString(ColumnNum);
					rset.beforeFirst();
					break;}

                                    }
				       // process column data as string
				    	
						
				



					//MinText.setText(stringmin);
			    }
		} catch (Exception e1) {
			
		}



                try {
			if (meta.getColumnType(ColumnNum) == java.sql.Types.DECIMAL ||      
				meta.getColumnType(ColumnNum) == java.sql.Types.INTEGER )
				    {
					    while(rset.next()){
						if(rset.getString(ColumnNum)==null) continue;
						if (rset.getDouble(ColumnNum)>doublemax) doublemax=rset.getDouble(ColumnNum);
                                          }

					MaxText.setText(String.valueOf(doublemax));


                                     }
			else{
				while(rset.next()){

					if(rset.getString(ColumnNum)==null) continue;
						if (rset.getString(ColumnNum).compareTo(stringmax)>0) stringmax=rset.getString(ColumnNum);

                                }

				MaxText.setText(stringmax);
			}





		}catch (Exception e1) {
			
		}
		
		   
		   
	   }
   }
   
   
   
   
   static class AvgListener implements ActionListener {
	   
	   public void actionPerformed(ActionEvent e) {

		String columnNum = Column.getText();
		   int ColumnNum = Integer.parseInt(columnNum);//this give us the integer of the column we want to calculate
		   if (ColumnNum>NCols) {
			   AvgText.setText("col # err");
			   return;
		   }
		   
		   //after handling the error, find certian column and retreive number
		   double doublesum=0;
		  int withoutnullcount=0;
		  
		     try {
			s=Input.getText();
			rset=stmt.executeQuery(s);
			meta=rset.getMetaData();
			if (meta.getColumnType(ColumnNum) == java.sql.Types.DECIMAL ||      
				meta.getColumnType(ColumnNum) == java.sql.Types.INTEGER )
				    {
				       // process column data as float
					if(rset.getString(ColumnNum)!=null){
					doublesum = rset.getDouble(ColumnNum);
					withoutnullcount++;
					}
                                 	//
				    }
				    else
				    {       // process column data as string
					AvgText.setText("Not num");
					return;
                                    }
				      
			    
		} catch (Exception e1) {
			
		}



                try {
			
			
				while(rset.next()){

					if(rset.getString(ColumnNum)==null) continue;
					doublesum += rset.getDouble(ColumnNum);
					withoutnullcount++;

                                }
				float average = (float)doublesum/withoutnullcount;
				AvgText.setText(String.valueOf(average));

		}catch (Exception e1) {
			
		}

		
		   
		   
	   }
   }
   
   static class MedianListener implements ActionListener {
	   
	   public void actionPerformed(ActionEvent e) {

		String columnNum = Column.getText();
	       int ColumnNum = Integer.parseInt(columnNum);//this give us the integer of the column we want to calculate
		   if (ColumnNum>NCols) {
			   MedianText.setText("col # err");
			   return;
		   }
		 int withoutnullcount=0;
		ArrayList<Double> doublelist = new ArrayList<Double>();
		ArrayList<String> stringlist = new ArrayList<String>();

		 try {
			s=Input.getText();
			rset=stmt.executeQuery(s);
			meta=rset.getMetaData();
			if (meta.getColumnType(ColumnNum) == java.sql.Types.DECIMAL ||      
				meta.getColumnType(ColumnNum) == java.sql.Types.INTEGER )
				    {
				       // process column data as float
					if(rset.getString(ColumnNum)!=null){
					doublelist.add(rset.getDouble(ColumnNum));
					withoutnullcount++;
					}
                                 	//
				    }
				    else
				    {       // process column data as string
					if(rset.getString(ColumnNum)!=null){
					stringlist.add(rset.getString(ColumnNum));
					withoutnullcount++;
					}
                                    }
				      
			    
		} catch (Exception e1) {
			
		}


		       try {
			if (meta.getColumnType(ColumnNum) == java.sql.Types.DECIMAL ||      
				meta.getColumnType(ColumnNum) == java.sql.Types.INTEGER )
				    {
					    while(rset.next()){
						if(rset.getString(ColumnNum)==null) continue;
						doublelist.add(rset.getDouble(ColumnNum));
					        withoutnullcount++;
                                          }

					  int num = (withoutnullcount+1)/2;
				    Collections.sort(doublelist);
			            double doublemedian = doublelist.get(num-1);
				    MedianText.setText(String.valueOf(doublemedian));
                                     }
				  
				
			else{
				while(rset.next()){

					if(rset.getString(ColumnNum)==null) continue;
					stringlist.add(rset.getString(ColumnNum));
					 withoutnullcount++;

                                }
				int num =  (withoutnullcount+1)/2;
				Collections.sort(stringlist);
				String stringmedian=stringlist.get(num-1);
				MedianText.setText(stringmedian);
			}





		}catch (Exception e1) {
			
		}
		


		   
		   
	   }
   }
   
   
   
   
   
   
   
   
   
  
   
   
   
   














  /* **********************************************************
     This section of the program makes the GUI

     DO NOT make any changes to the code below !!!
     ********************************************************** */

   /* ===============================================================
      Make GUI window
      =============================================================== */
   public static void MakeGUIWindow()
   {
      Font ss_font = new Font("SansSarif",Font.BOLD,16) ;
      Font ms_font = new Font("Monospaced",Font.BOLD,16) ;

      JPanel P1 = new JPanel();   // Top panel
      JPanel P2 = new JPanel();

      P1.setLayout( new BorderLayout() );
      P2.setLayout( new BorderLayout() );

      /* =============================================
         Make top panel
         ============================================= */
      JScrollPane d1 = new JScrollPane(Input, 
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      Input.setFont( ss_font );

      JPanel s1 = new JPanel(); // Side panel
      s1.setLayout( new GridLayout( 8,1 ) );
      s1.add( DBLabel );
      s1.add( DBName );
      DBName.setEditable(true);
      DBName.setFont( ss_font );
      s1.add( Select );
      s1.add( Execute );
      Execute.setPreferredSize(new Dimension(140, 30)) ;

      P1.add(d1, "Center");
      P1.add(s1, "East");

      /* =============================================
         Make bottom panel
         ============================================= */
      JScrollPane d2 = new JScrollPane(Output, 
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

      Output.setFont( ms_font );
      Output.setEditable(false);

      JPanel s3 = new JPanel(); // Put ColumnLabel and Column on 1 row
      s3.add(ColumnLabel);
      s3.add(Column);
      Column.setFont( ss_font );

      Column.setPreferredSize(new Dimension(40, 30)) ;

      JPanel s2 = new JPanel(); // Side panel
      s2.setLayout( new GridLayout( 10,1 ) );
      s2.add( s3 );

      MaxText.setPreferredSize(new Dimension(140, 30)) ;
      s2.add( MaxText );
      MaxText.setFont( ss_font );
      MaxText.setEditable(false);
      s2.add( Max );

      s2.add( MinText );
      MinText.setFont( ss_font );
      MinText.setEditable(false);
      s2.add( Min );

      s2.add( AvgText );
      AvgText.setFont( ss_font );
      AvgText.setEditable(false);
      s2.add( Avg );

      s2.add( MedianText );
      MedianText.setFont( ss_font );
      MedianText.setEditable(false);
      s2.add( Median );


      P2.add(d2, "Center");
      P2.add(s2, "East");

      mainFrame = new JFrame("CS377 JDBC project");
      mainFrame.getContentPane().setLayout( new GridLayout(2,1) );
      mainFrame.getContentPane().add( P1 );
      mainFrame.getContentPane().add( P2 );
      mainFrame.setSize(900, 700);

      /* =================================================
         Exit application if user press close on window

         This will clean up the network connection....
         ================================================= */
      // mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      mainFrame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent we) {
             System.out.println("\nGood-bye.Thanks for using CS377-sql...\n");
             System.exit(0);
          }
      });


      mainFrame.setVisible(true);
   }
}
