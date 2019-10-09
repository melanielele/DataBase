#The homework is completed only by Melanie Zhao without consulting any other students except Instructor and TAs



 <html>
 <head>
 <title> CS377 MySQL Web client</title>
 </head>
  <body>

 <H3>
 <HR>
  Answer to the query
 <HR>
 </H3>
 <P> 
 <UL>

 <?php

   $conn = mysqli_connect("holland.mathcs.emory.edu","cs377", "abc123");
   	  
   if (mysqli_connect_errno())            # -----------  check connection error
   {      
      printf("Connect failed: %s\n", mysqli_connect_error());
      exit(1);
   }      
   	  
   if ( ! mysqli_select_db($conn, "spjDB") )          # Select spjDB
   {      
      printf("Error: %s\n", mysqli_error($conn));
      exit(1);
   }      
   	

   $sname = $_POST['sname'];
   $pname = $_POST['pname'];
   $jname = $_POST['jname'];
   $scity = $_POST['scity'];
   $pcity = $_POST['pcity'];
   $jcity = $_POST['jcity'];

   $query = "select supplier.sname as 'Supplier Name' ,supplier.city as 'Supplier City', part.pname as 'Part Name',part.city as 'Part City',
 proj.jname as 'Project Name',proj.city as 'Project City', qty as 'Quantity Shipped'
from spj,supplier,part,proj
where spj.jnum=proj.jnum
and spj.snum=supplier.snum
and spj.pnum=part.pnum";

     if (!empty($sname)){
	 for ( $i = 0; $i < strlen($sname); $i++ )                   
      {
   	  if ( $sname[$i] == '*' )
   	     $sname[$i] = '%';
	  if ( $sname[$i] == '?')
	     $sname[$i] = '_';
      }
        $query .=" and supplier.sname like '\\$sname'";

      }
	if (!empty($pname)){
          for ( $i = 0; $i < strlen($pname); $i++ )                   
      {
          if ( $pname[$i] == '*' )
             $pname[$i] = '%';
          if ( $pname[$i] == '?')
             $pname[$i] = '_';
      }
        $query .=" and part.pname like '\\$pname'";
      }
	if(!empty($jname)){
	  for ( $i = 0; $i < strlen($jname); $i++ )                   
      {
          if ( $jname[$i] == '*' )
             $jname[$i] = '%';
          if ( $jname[$i] == '?')
             $jname[$i] = '_';
      }
        $query .=" and proj.jname like '\\$jname'";

      }
	if(!empty($scity)){
	  for ( $i = 0; $i < strlen($scity); $i++ )                   
      {
          if ( $scity[$i] == '*' )
             $scity[$i] = '%';
          if ( $scity[$i] == '?')
             $scity[$i] = '_';
      }
        $query .=" and supplier.city like '\\$scity'";

 	} 
	if(!empty($pcity)){
      for ( $i = 0; $i < strlen($pcity); $i++ )                   
      {
          if ( $pcity[$i] == '*' )
             $pcity[$i] = '%';
          if ( $pcity[$i] == '?')
             $pcity[$i] = '_';
      }
	$query .=" and part.city like '\\$pcity'";
        }
	if(!empty($jcity)){
      for ( $i = 0; $i < strlen($jcity); $i++ )                   
      {
          if ( $jcity[$i] == '*' )
             $jcity[$i] = '%';
          if ( $jcity[$i] == '?')
             $jcity[$i] = '_';
      }
	$query .=" and proj.city like '\\$jcity'";

         }  



   print("<UL><TABLE bgcolor=\"#FFEEEE\" BORDER=\"5\">\n");
   print("<TR> <TD><FONT color=\"blue\"><B><PRE>\n");
   print( $query );   # echo the query 
   print("</PRE></B></FONT></TD></TR></TABLE></UL>\n");
   print("<P><HR><P>\n");
   	  
   if ( ! ( $result = mysqli_query($conn, $query)) )      # Execute query
   {      
      printf("Error: %s\n", mysqli_error($conn));
      exit(1);
   }      
   	  
   print("<UL>\n");
   print("<TABLE bgcolor=\"lightyellow\" BORDER=\"5\">\n");
   	  
   $printed = false;
   
   while ( $row = mysqli_fetch_assoc( $result ) )
   {      
      if ( ! $printed )
      {   
   	 $printed = true;                 # Print header once...
   	  
   	 print("<TR bgcolor=\"lightcyan\">\n");
   	 foreach ($row as $key => $value)
   	 {
   	    print ("<TH>" . $key . "</TH>");             # Print attr. name
   	 }
   	 print ("</TH>\n");
      }   
   	  
   	  
      print("<TR>\n");
      foreach ($row as $key => $value)
      {   
   	 print ("<TD>" . $value . "</TD>");
      }   
      print ("</TR>\n");
   }      
   print("</TABLE>\n");
   print("</UL>\n");
   print("<P>\n");
   	  
   mysqli_free_result($result);
   	  
   mysqli_close($conn);
   





 ?>     
   	  
   </UL>
   <P> 
   <HR>
   <HR>
   <HR>
   <HR>




