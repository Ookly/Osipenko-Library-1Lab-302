
package javalibrarylab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ConnectToDB {
        public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;
        // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
	public static void Conn() throws ClassNotFoundException, SQLException 
	   {
		   conn = null;
		   Class.forName("org.sqlite.JDBC");
		   conn = DriverManager.getConnection("jdbc:sqlite:Lib.s3db");
		   System.out.println("База Подключена!");
                   
	   }

	public static void CreateDB() throws ClassNotFoundException, SQLException
	   {
		statmt = conn.createStatement();
		statmt.execute("CREATE TABLE if not exists 'Books' ('id' INTEGER PRIMARY KEY, 'name' text, 'genre' text, 'author' text, 'mark' INT );");
                statmt.execute("CREATE TABLE if not exists 'Genres'('name' text PRIMARY KEY, 'about' text);");
		System.out.println("Таблица создана или уже существует.");
	   }
        public static void WriteDB(Book b, Genre g) throws SQLException
	{     
		String str1="INSERT INTO 'Books'('id', 'name' , 'genre', 'author', 'mark') VALUES ("+b.getId()+", '"+b.getName()+"', '"+b.getGenre()+"', '"+b.getAuthor()+"', '"+b.getMark()+")";
                String str2="INSERT INTO 'Genres' VALUES ('"+g.getName()+"', '"+g.getAbout()+"')";
		statmt.executeUpdate(str1);
                statmt.executeUpdate(str2);
		System.out.println("Таблица заполнена");
	}
        // -------- Вывод таблицы--------
	public static Collection<Book> ReadDB() throws ClassNotFoundException, SQLException
	   {
               
		resSet = statmt.executeQuery("SELECT * FROM Books ");
                List<Book>bk=new ArrayList<>();
		while(resSet.next())
		{
                 
		int id = resSet.getInt("id");
		String  name = resSet.getString("name");
                String  genre = resSet.getString("genre");
                String  author = resSet.getString("author");
                int mark=resSet.getInt("mark");
		bk.add(new Book(id,name,genre,author,mark));
	         System.out.println( "ID = " + id );
	         System.out.println( "name = " + name );
                 System.out.println( "genre = " + genre );
                 System.out.println( "author = " + author );
	         System.out.println( "mark = "+mark );
	         System.out.println();
		}	
		
		System.out.println("Таблица выведена");
                return bk;
	    }
        public static Collection<Genre> ReadDb()throws ClassNotFoundException, SQLException{
            resSet = statmt.executeQuery("SELECT * FROM Genres");
            List<Genre> g = new ArrayList<>();
            while(resSet.next()){
            String name = resSet.getString("name");
            String about = resSet.getString("about");
            g.add(new Genre(name,about));
            System.out.println( "name = " + name );
            System.out.println( "about = " + about );
            }
            return g;
        }
	public static void DeleteDB(Book b) throws SQLException{
        String str="DELETE FROM 'Books' WHERE id = "+b.getId()+"";
        
	statmt.executeUpdate(str);
        System.out.println("Запись удалена");
        }
        public static void DeletDb(Genre g)throws SQLException{
        String str="Delete FROM 'Genres' WHERE name = '"+g.getName()+"'";
        statmt.executeUpdate(str);
        System.out.println("Запись удалена");
        }
        public static void updateDB(Book b)throws SQLException{
            try{
            statmt = conn.createStatement();
            resSet = statmt.executeQuery("SELECT * FROM Books ");
            List<Book>bk=new ArrayList<>();
            while(resSet.next()){
                int id = resSet.getInt("id");
		String  name = resSet.getString("name");
                String  genre = resSet.getString("genre");
                String  author = resSet.getString("author");
                int mark=resSet.getInt("mark");
                bk.add(new Book(id,name,genre,author,mark));
	         System.out.println( "ID = " + id );
	         System.out.println( "name = " + name );
                 System.out.println( "genre = " + genre );
                 System.out.println( "author = " + author );
	         System.out.println( "mark = "+mark );
	         System.out.println();
            }
            resSet.close();
            statmt.close();
            conn.close();
            }catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
    }
        }
        public static void updateDb(Genre g)throws SQLException{
        try{
            statmt = conn.createStatement();
            resSet = statmt.executeQuery("SELECT * FROM Genres ");
            List<Genre>gn=new ArrayList<>();
            while(resSet.next()){
		String  name = resSet.getString("name");
                String  about = resSet.getString("about");
                
                gn.add(new Genre(name,about));
	         System.out.println( "Name = " + name );
	         System.out.println( "About = " + about );
                
	         System.out.println();
            }
            resSet.close();
            statmt.close();
            conn.close();
            }catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
    }
        
        }
        
		// --------Закрытие--------
		public static void CloseDB() throws ClassNotFoundException, SQLException
		   {
			conn.close();
                        statmt.close();
                        resSet.close();
			System.out.println("Соединения закрыты");
		   }
	
}
