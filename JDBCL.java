/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcl;
import java.sql.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author zulfiqar.bscs13seecs
 */
public class JDBCL {

       // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/GeoLiteCity";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
   public static void main(String[] args) throws SQLException{
   Connection conn = null;
   Statement stmt = null;
   try{
          //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql,sql1;
//     // sql = "SELECT id, first, last, age FROM Employees";
//      //ResultSet rs = stmt.executeQuery(sql);
//
       String csvFile = "C:\\Users\\zulfiqar.bscs13seecs\\Downloads\\GeoLiteCity-Location.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        int row=0,count = 0;
	try {
                System.out.println("Populating Database");
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
                        
		        // use comma as separator
			String[] data = line.split(cvsSplitBy,-1);
                        for(int i =0; i<9;i++){
                            if(data[i].length()==0){
                                data[i] = "Not Available";
                            }
                            
                        }
			 sql = "INSERT INTO cities (ID, country, region,city,postal_code,latitude,longitude,metro_code,area_code) values (?,?,?,?,?,?,?,?,?)";
                        PreparedStatement statement = conn.prepareStatement(sql);
                           statement.setString(1,data[0]);
                           statement.setString(2,data[1]);
                           statement.setString(3,data[2]);
                           statement.setString(4,data[3]);
                           statement.setString(5,data[4]);
                           statement.setString(6,data[5]);
                           statement.setString(7,data[6]);
                           statement.setString(8,data[7]);
                           statement.setString(9,data[8]);
                           row = statement.executeUpdate();
                           //if(row>0){
                               count++;
                           //}
                               if(count == 500){
                                   break;
                               }
		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
       System.out.println("1)Find City Details\n2)Find Nearby Cities");
       System.out.print("Option:");
       Scanner in = new Scanner(System.in);
       Scanner in2 = new Scanner(System.in);
       int option = in.nextInt();
       if(option == 1){
           System.out.print("City:");
           String city_name = "";
           city_name = in2.nextLine();
           sql1 = "SELECT* FROM cities WHERE city =\"\""+city_name+"\"\"";
           
           stmt = conn.createStatement();
      
      
      ResultSet rs = stmt.executeQuery(sql1);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("ID");
         String country = rs.getString("country");
         String region = rs.getString("region");
         String city = rs.getString("city");
         String postal_code = rs.getString("postal_code");
         String latitude = rs.getString("latitude");
         String longitude = rs.getString("longitude");
         String metro_code = rs.getString("metro_code");
         String area_code = rs.getString("area_code");
         //Display values
         System.out.print("ID: " + id);
         System.out.print(", country: " + country);
         System.out.print(", region: " + region);
         System.out.print(", city: " + city);
         System.out.print(", postal_code: " + postal_code);
         System.out.print(", latitude: " + latitude);
         System.out.print(", longitude: " + longitude);
         System.out.print(", metro_code: " + metro_code);
         System.out.println(", area_code: " + area_code);
         
           
           
       }
       }
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
    
}
