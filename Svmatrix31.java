package fifthmarch;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Svmatrix31{
    
    Connection con;
    Statement st,st1;
	public Svmatrix31()
	{
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con =DriverManager.getConnection("jdbc:mysql://localhost:3306/twitterdatabase","root",""); 
            st=con.createStatement();
            st1=con.createStatement();
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(Svmatrix31.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
            Logger.getLogger(Svmatrix31.class.getName()).log(Level.SEVERE, null, ex);
       }  
       catch(Exception e)
       {
           System.out.println(e);
       }
	}
	
     public void chooseClass(HashMap<Integer,String> hm)
    {
        
    	String str1,id1="",str="";   
       multiclass1 object=new multiclass1();
      
        //int rows=3898,col=2011;
      //  int rows=3238,col=1996;
        int rows=hm.size();
        int col=1995;
       // int lastcol=1997;
        int[][] mat=new int[rows][col];
        String[]keywords=new String[col];
       // String category="";
        int i=0,j=0,index=0,cnt1=0,cnt2=0,cnt3=0,lt=288,lt1=1132,match=0;
        try
        {
                BufferedReader br=new BufferedReader(new FileReader("F:\\Education\\B.E\\Mega\\New\\cleanedTable3test.txt"));
	        // BufferedReader br=new BufferedReader(new FileReader("F:\\Mega\\Code\\code\\SVMInput\\intId.txt"));
                // BufferedReader br=new BufferedReader(new FileReader("F:\\Mega\\Code\\code\\tweets\\cleanedTest2.txt"));
	         BufferedReader br1=new BufferedReader(new FileReader("F:\\Education\\B.E\\Mega\\keyword\\spoliedu.txt"));
	    
	          FileWriter fr,fw,fw1,fws,fwh,fwp,fall,fkeys;
	       //   fr = new FileWriter("F:\\Mega\\Code\\code\\SVMinput\\InputToSvmTrain2.txt");
                   fr = new FileWriter("F:\\Education\\B.E\\Mega\\Svminput\\threadstest.txt");
	          fw= new FileWriter("F:\\Education\\B.E\\Mega\\Svminput\\AmbiguityTable2.txt");
                  fw1= new FileWriter("F:\\Education\\B.E\\Mega\\Svminput\\NoDomainTable2.txt");
                  fws= new FileWriter("F:\\Education\\B.E\\Mega\\Svmoutput\\classifiedSportsTable.txt");
                  fwh= new FileWriter("F:\\Education\\B.E\\Mega\\Svmoutput\\classifiedPoliticsTable.txt");
                  fwp= new FileWriter("F:\\Education\\B.E\\Mega\\Svmoutput\\classifiededucationTable.txt");
                   fkeys= new FileWriter("F:\\Education\\B.E\\Mega\\Svmoutput\\MatchedKeywords.txt");
                    // fall= new FileWriter("F:\\Education\\B.E\\Mega\\Svmoutput\\TwClasses.txt");
	          while((str1=br1.readLine())!=null)
	          {  
	        	  keywords[index]=str1;
	        	  index++;
	          }         
             while(((str=br.readLine())!=null))
             {
                 fkeys.write("\nTweet: "+str+"\n");
            	 cnt1=cnt2=cnt3=0;
            	 match=0;
            	             	
                 str=str.replace(","," ");
                 String[]arr=str.split(" ");
                 
                  fkeys.write("Matched: \n");
                    for(int k=0;k<arr.length;k++)
                    {
                         for(j=0;(j<keywords.length-1)&&(k<arr.length);j++)
                         {
                             if(arr[k].equalsIgnoreCase(keywords[j]))
                             {
                                     match++;
                                 if(j<lt)
                                 {
                                     System.out.println("1->"+keywords[j]);
                                     fkeys.write("sports->"+keywords[j] +"\t");
                                     fkeys.flush();
                                     mat[i][j]=1;
                                     k++;
                                     cnt1++;                   
                                 }
                                 else if(j>=lt && j<lt1)
                                 {
                                    
                                        mat[i][j]=2;  
                                    System.out.println("2->"+keywords[j]);
                                      fkeys.write("politics -> "+keywords[j] +"\t");
                                        fkeys.flush();
                                    k++;
                                    cnt2++;
                                 }
                                 else if(j>=lt1)
                                 {
                                  
                                     mat[i][j]=3;  
                                    System.out.println("3->"+keywords[j]);
                                      fkeys.write("education -> "+keywords[j] +"\t");
                                        fkeys.flush();
                                    k++;                     
                                    cnt3++;
                                 }

                             }
                             
                         }
                         if(k==arr.length)
                         {
                             if(match==0)
                             {
                                     fw1.write(str);
                                     fw1.write("\n");
                                     fw1.flush();  

                                     for(k=0; k<keywords.length-1 ;k++)
                                     {
                                             mat[i][j]=0;
                                     }
                             }
                              else
                             {
                                     for(k=col-1;(k>=0)&&(j<keywords.length-1);k--,j++)
                                     {
                                             mat[i][j]=0;
                                     }
                             }
                         }
                    }//outer for 
                 
                 if(cnt1>cnt2 && cnt1>cnt3)
                 {
                	 mat[i][j]=11;                                
                	
                         fws.write(str);                        
                         fws.write("\n");
                         fws.flush();
                 }	
                 else if(cnt2>cnt1 && cnt2>cnt3)
                 {
                	mat[i][j]=22;
                	
                        fwh.write(str);
                        fwh.write("\n");
                        fwh.flush();
                 }
                 else if(cnt3>cnt1 && cnt3>cnt2)
                 {
                	mat[i][j]=33;     	 
                	
                        fwp.write(str);
                        fwp.write("\n");
                        fwp.flush();
                 }   
                 else
                 {
                	 mat[i][j]=333;               	 
                 }
                 i++;
                 if(i>=rows)
                         break;
             }//while
              
             for (int k=0;k<i;k++)
             {     
            	 //get id of tweet corresponding to row no. of tweet(starts from 1 in hash map)
            	 String tweet_id=hm.get(k+1);
            	 
            	 for (int l = 0; l<col; l++)
            	 {  		 
	                 if(mat[k][l]==11)
	                 {
	                	 fr.write("sports"); 
	                	String qry="Update threadstest set class='sports' where id='"+tweet_id+"'";
                                String qry1="select tweet,class from threadstest";
	                	 st.executeUpdate(qry);	    
                                     ResultSet rs = st.executeQuery(qry1);	
                                   
	                 }
	                 else if(mat[k][l]==22)
	                 {
	                	 fr.write("politics");
	                	 String qry="Update threadstest set class='politics' where id='"+tweet_id+"'";
                                 String qry1="select tweet,class from threadstest";
	                	 st.executeUpdate(qry);	
                                  ResultSet rs=st.executeQuery(qry1);
                                  while(rs.next()){
   
                                    /* fall.write(rs.getString(1)+"\t");
                                     fall.write(rs.getString(2));
                                     fall.write("\n");
                                     fall.flush();*/
                                  }
	                 }
	                 else if(mat[k][l]==33)
	                 {
	                	 fr.write("education");
	                	 String qry="Update threadstest set class='education' where id='"+tweet_id+"'";
                                 String qry1="select tweet,class from threadstest";
	                	 st.executeUpdate(qry);	
                                 ResultSet rs= st.executeQuery(qry1);	
                                 while(rs.next()){
   
                                     /*fall.write(rs.getString(1)+"\t");
                                     fall.write(rs.getString(2));
                                     fall.write("\n");
                                     fall.flush();*/
                                  }
	                 }
	                 else if(mat[k][l]==333)
	                 {
	                	 fr.write("no domain");
	                	 String qry="Update threadstest set class='no domain' where id='"+tweet_id+"'";
                                 String qry1="select tweet,class from threadstest";
	                	 st.executeUpdate(qry);	
                                  ResultSet rs=st.executeQuery(qry1);	
                                  while(rs.next()){
   
                                    /* fall.write(rs.getString(1)+"\t");
                                     fall.write(rs.getString(2));
                                     fall.write("\n");
                                     fall.flush();*/
                                  }
	                 }
	                 else
	                	 fr.write(mat[k][l] + ",");
            	 }
                
	             System.out.println();
	             fr.write("\n");
	             fr.flush();
             }
             
             /*for(int g=0;g<17;g++)
             {               
                     System.err.println(mat[g][1994]);
             }*/
             }   
        
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }  
	        //object.classify(hm);      
     }



   }