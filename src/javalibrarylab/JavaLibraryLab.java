
package javalibrarylab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.sql.PreparedStatement;
import java.util.function.Predicate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import static javalibrarylab.ConnectToDB.conn;

class BookSortName implements Comparator<Book>{

    @Override
    public int compare(Book o1, Book o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
class GenreSortName implements Comparator<Genre>{

    @Override
    public int compare(Genre o1, Genre o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
class BookSortMark implements Comparator<Book>{

    @Override
    public int compare(Book o1, Book o2) {
        return o2.getMark()-o1.getMark();
    }
}
    class BookSortId implements Comparator<Book>{

    @Override
    public int compare(Book o1, Book o2) {
        return o1.getId()-o2.getId();
    }
}
public class JavaLibraryLab {
    public static void main(String[] args)throws ClassNotFoundException, SQLException {
          ConnectToDB.Conn();
        ConnectToDB.CreateDB();
       JLayeredPane lpane = new JLayeredPane();
        
        LibraryWindow frame = new LibraryWindow();
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setLayout(new BorderLayout());
        frame.add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, 600, 400);
       //Book b6 = new Book(3,"Lolita","Novel","Vladimir Nabokov",5);
        
     // ConnectToDB.WriteDB(b6);
//ConnectToDB.DeleteDB();
        
	
    }
    
}
class Genre implements Comparable<Genre> {
private String name; 
private String about;
private SortedSet<Genre>genres;
public Genre (String name, String about){
    this.name=name;
    this.about=about;
    
}
public String getName(){
    return name;
    }
public String getAbout(){
    return about;
}
public Genre( Collection<Genre> genres) {
   
        this.genres = new TreeSet(genres);
       
    }

public boolean addGenre(Genre g){
        if(!genres.contains(g)){
            try {
                ConnectToDB.Conn();
               
               PreparedStatement  pstmt=conn.prepareStatement ("INSERT INTO 'Genres' VALUES ('"+g.getName()+"', '"+g.getAbout()+"')");
                pstmt.executeUpdate();
                pstmt.close();
                genres.add(g);
                return true;
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Genre.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        else return false;
    }
public boolean removeGenre(Genre g){
        if(genres.contains(g)){
           
            genres.remove(g);
            
            return true;
        }
        else return false;
    }
public Collection<Genre> getListOfGenres(Comparator<? super Genre>comparator){
        List<Genre>g=new ArrayList(genres);
        g.sort(comparator);
        System.out.println(name+":");
        g.forEach(b->System.out.println(b));
        return g;
    }

    @Override
    public int compareTo(Genre o) {
        return name.compareTo(o.name);
    }
 @Override
 public String toString(){
 return name+" - " + about + " ";
 }
  

}

class Book implements Comparable<Book>{
    private int id;
    private String name;
    private String genre;
    private String  author;
    private int mark;
    
    public Book (int id,String name, String genre,String author, int mark) {
        this.id=id;
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.mark = mark;

    }
    public Book (int mark) {
        this.id=id;
   
    }
    @Override
    public boolean equals(Object obj) {
        return id==((Book)obj).id; 
    }
    @Override
    public String toString() {
        return  id+"   " + name + "   " + genre + "   " + author + " " + mark + "  ";
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public int getMark() {
        return mark;
    }
    public String getGenre() {
        return genre;
    }
    public String getAuthor() {
        return author;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int compareTo(Book o) {
        return id-o.id;
    }  
}
 class Library{
    
   private String name;
    private SortedSet<Book>books;
    int mark;
  public Library( Collection<Book> books) {
   
        this.books = new TreeSet(books);
       
    }
   
  

    public String getName() {
        return name;
    }
    
  
    public boolean addBook(Book b){
        if(!books.contains(b)){
            try {
                ConnectToDB.Conn();
               
               PreparedStatement  pstmt=conn.prepareStatement ("INSERT INTO 'Books' VALUES ("+b.getId()+", '"+b.getName()+"', '"+b.getGenre()+"', '"+b.getAuthor()+"', '"+b.getMark()+"')");
                pstmt.executeUpdate();
                pstmt.close();
                books.add(b);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }catch(NumberFormatException e ){
                 System.out.println("dasdas");
                 return false;
            }
            
        }
        else return false;
    }
    public boolean removeBook(Book b){
        if(books.contains(b)){
           
            books.remove(b);
            
            return true;
        }
        else return false;
    }
     public boolean modifyMark(Book b){
    if(this.mark == mark){
    return false;
    }
    else{
    this.mark=mark;
    return true;
    }
    }
    public Collection<Book> filterBooks(Predicate<? super Book>pred1,Predicate<? super Book>pred2){
        Iterator<Book>iter=books.iterator();
        List<Book> l=new ArrayList<>();
        while(iter.hasNext()){
            Book temp=iter.next();
            if(pred1.test(temp)&&pred2.test(temp)){
                System.out.println(temp);
                l.add(temp);
            }
            }
        
        return l;
               
    
    }

    public SortedSet<Book> getStudents() {
        return books;
    }
    
    public Collection<Book> getListOfBooks(Comparator<? super Book>comparator){
        List<Book>l=new ArrayList(books);
        l.sort(comparator);
        System.out.println(name+":");
        l.forEach(b->System.out.println(b));
        return l;
    }


    void addBook(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    

