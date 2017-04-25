//package e-commerce_project;

import java.sql.*;
import java.util.Vector;
import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;

import java.awt.*;
public class main_project {
	
	public static void main(String[] args)  throws ParseException {
		
		String c_id="",p_id=""; 
		Connection connection;
		JOptionPane.showMessageDialog(null, "Welcome to E-Commerce System");
		DriverRegistration();
		connection = CreateConnection();
		if (connection != null) {
			while(true)
			{
				JComboBox<String> wc = new JComboBox<String>();
				String welcome;
				wc.addItem("Log in");
				wc.addItem("Register");
				wc.addItem("Exit");
				
				int choice = 0;
				Object [] message = { "Enter your choice",wc};
				int result = JOptionPane.showConfirmDialog(null, message, "What YOu Want",JOptionPane.OK_CANCEL_OPTION);
				if(result==JOptionPane.OK_OPTION){
					choice = wc.getSelectedIndex();
					if(choice==2)
					{
						try {
							connection.close();
							JOptionPane.showMessageDialog(null," Good Bye!");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					else if(choice==0)
					{
						welcome = login();
						//JOptionPane.showMessageDialog(null, "Your query = " + welcome);
						try {
							connection.setAutoCommit(true); // changes shall be committed, not lost
							Statement stmt = connection.createStatement() ;
							ResultSet rs = stmt.executeQuery(welcome);
							while(rs.next()){
							c_id = rs.getString(1);
							}
							int cc= Integer.parseInt(c_id)+1;
							//JOptionPane.showMessageDialog(null, "Your result = " + cc);
						} // function displaying result in a table format
						
						 catch (SQLException e) {
							// TODO Auto-generated catch block
							if(welcome.isEmpty())
								JOptionPane.showMessageDialog(null, "Empty Values");
							else
								JOptionPane.showMessageDialog(null, "Not Find");
							e.printStackTrace();
						}
						String boo = "1";
						if(boo.equals(c_id))
						{
							adminpanel(connection);
						}
						else
						{
							customerpanel(connection,c_id);
							//JOptionPane.showMessageDialog(null, "fff = " + c_id);
						}
					}
					else if(choice == 1)
					{
						welcome = signin();
						ExecuteUpdateSQL(connection, welcome);
					}
						
					else
						welcome();
					//JOptionPane.showMessageDialog(null, "You ssdsdv  !   "+choice);
				}
			}
			//welcome();
			
			/**/
			//JOptionPane.showMessageDialog(null, "Database Connection Successful");
		} else {
			JOptionPane.showMessageDialog(null, "Database Connection Failed!");
			return;
		}
	}
	
	public static void DriverRegistration(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			//System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}
		//JOptionPane.showMessageDialog(null, "Oracle JDBC Driver Registered!");
	}
	
	public static Connection CreateConnection(){
		Connection conn = null;
		String s = "system";
		String p = "123456";
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost", s, p);
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public static void ExecuteUpdateSQL(Connection conn, String s){
		try {
			conn.setAutoCommit(true); // changes shall be committed, not lost
			Statement stmt = conn.createStatement() ;
			stmt.executeUpdate(s);
			JOptionPane.showMessageDialog(null, "Add Complete !!!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(s.isEmpty())
				JOptionPane.showMessageDialog(null, "Empty Values");
			else
				JOptionPane.showMessageDialog(null, "Error in SQL");
			e.printStackTrace();
		}
	}
	
	public static Integer welcome(){
		
		return 0;
	}
	
	public static String signin()  {
		
		//String cc = "";
		//cc= "select count(customer_id) from customer";
		String s= "";
		JTextField id = new JTextField(20);
		JTextField name = new JTextField(20);
		JTextField uname = new JTextField(20);
		JTextField password = new JTextField(20);
		JTextField email = new JTextField(20);
		JTextField address = new JTextField(30);
		JTextField phone = new JTextField(20);
		JTextField pt = new JTextField(20);
		JTextField pn = new JTextField(20);
		
		//JOptionPane.showMessageDialog(null, "You ssdsdv!"+cc);
		
		Object [] message = {"ID",id,"Name",name,"User Name",uname,"Password",password,"E-mail",email,"Address",address,"Phone",phone,"Payment in",pt,"Payment-Number",pn};
		int result = JOptionPane.showConfirmDialog(null, message,"Open An Account",JOptionPane.OK_CANCEL_OPTION);
		String a = "oshy";
		if(a.equals(uname.getText()))
		{
			JOptionPane.showMessageDialog(null, "It is admins user name, Give another User Name!");
			signin();
		}
		
		else if(result == JOptionPane.OK_OPTION){
			s = "insert into customer values ('" + id.getText() + "','" + name.getText()+ "','" + uname.getText() + "','" + password.getText() + "','" + email.getText() + "','" + address.getText() + "'," + phone.getText() + ",'" + pt.getText() + "'," + pn.getText() + ")";
			//JOptionPane.showMessageDialog(null, "You successfully Registered!" + a);
			System.out.println(s);
			return s;
		}
		else
			signin();
		return s;
		
	}
	
	public static String login(){

		String s= "";
		JTextField uname = new JTextField(20);
		JTextField pass = new JTextField(20);
		
		Object [] message = {"User Name",uname,"Password",pass};
		int result = JOptionPane.showConfirmDialog(null, message,"Log In",JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			s = "select customer_id from customer where user_name='"+ uname.getText() + "' and password='" + pass.getText()+"'";
			//JOptionPane.showMessageDialog(null, "You successfully Registered!");
			System.out.println(s);
			return s;
		}
		else
			signin();
		return s;
	}
	
	public static void adminpanel(Connection conn)  {
		
		JComboBox<String> wc = new JComboBox<String>();
		String welcome;
		wc.addItem("Add Category");
		wc.addItem("Add Product");
		wc.addItem("Add Stock");
		wc.addItem("Log Out");
		
		int choice = 0;
		Object [] message = { "Enter your choice",wc};
		int result = JOptionPane.showConfirmDialog(null, message, "What YOu Want",JOptionPane.OK_CANCEL_OPTION);
		if(result==JOptionPane.OK_OPTION){
			choice = wc.getSelectedIndex();
			
			if(choice==0)
			{
				welcome = category();
				ExecuteUpdateSQL(conn, welcome);
				adminpanel(conn);
			}
			else if(choice==1)
			{
				welcome = product();
				ExecuteUpdateSQL(conn, welcome);
				adminpanel(conn);
			}
			else if(choice==2)
			{
				welcome = stock();
				ExecuteUpdateSQL(conn, welcome);
				welcome = p_stock();
				ExecuteUpdateSQL(conn, welcome);
				adminpanel(conn);
			}
			
		}
		
		
		
	}
	
	public static String category(){
		String s="";
		
		JTextField id = new JTextField(20);
		JTextField name = new JTextField(20);
		JTextField des = new JTextField(30);
		
		Object [] message = {"ID",id,"Name",name,"Description",des};
		int result = JOptionPane.showConfirmDialog(null, message,"Enter The Category",JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			s = "insert into category values ('" + id.getText() + "','" + name.getText()+ "','" + des.getText() + "')";
			//JOptionPane.showMessageDialog(null, "You successfully Registered!" + a);
			System.out.println(s);
			return s;
		}
		else
			category();
		
		return s;
	}
	
	public static String product(){
		String s="";
		
		JTextField id = new JTextField(20);
		JTextField name = new JTextField(20);
		JTextField price = new JTextField(20);
		JTextField dis = new JTextField(20);
		JTextField c_id = new JTextField(20);
		
		Object [] message = {"ID",id,"Name",name,"Price",price,"Discount",dis,"Category_id",c_id};
		int result = JOptionPane.showConfirmDialog(null, message,"Enter the Product",JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			s = "insert into products values ('" + id.getText() + "','" + name.getText()+ "','" + c_id.getText() + "'," + price.getText() + "," + dis.getText() + ")";
			//JOptionPane.showMessageDialog(null, "You successfully Registered!" + a);
			System.out.println(s);
			return s;
		}
		else
			product();
		
		return s;
	}
	public static String stock(){
		String s="";
		
		JTextField id = new JTextField(20);
		JTextField q = new JTextField(20);
		JTextField b = new JTextField(20);
		JTextField d = new JTextField(20);
		
		Object [] message = {"ID",id,"Quantity",q,"Buying_Price",b,"Date",d};
		int result = JOptionPane.showConfirmDialog(null, message,"Enter A tock",JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			s = "insert into stock values ('" + id.getText() + "'," + q.getText() + "," + b.getText() + ",'" + d.getText()+ "')";
			//JOptionPane.showMessageDialog(null, "You successfully Registered!" + a);
			System.out.println(s);
			return s;
		}
		else
			stock();
		
		return s;
	}
	
	public static String p_stock(){
		String s="";
		
		JTextField p_id = new JTextField(20);
		JTextField s_id = new JTextField(20);
		
		Object [] message = {"Product_ID",p_id,"Stock_id",s_id};
		int result = JOptionPane.showConfirmDialog(null, message,"Add stock and product relation",JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			s = "insert into p_stock values ('" + p_id.getText() + "','" + s_id.getText()+ "')";
			//JOptionPane.showMessageDialog(null, "You successfully Registered!" + a);
			System.out.println(s);
			return s;
		}
		else
			p_stock();
		
		return s;
	}
	public static void customerpanel(Connection conn,String y_id)  {
		
		while(true)
		{
			JComboBox<String> wc = new JComboBox<String>();
			String cus;
			wc.addItem("Buy");
			wc.addItem("Check Detail");
			wc.addItem("Check yourself");
			wc.addItem("Log Out");
			
			int choice = 0;
			Object [] message = { "Enter your choice",wc};
			int result = JOptionPane.showConfirmDialog(null, message, "What YOu Want",JOptionPane.OK_CANCEL_OPTION);
			if(result==JOptionPane.OK_OPTION){
				choice = wc.getSelectedIndex();
				
				if(choice== 0)
				{
					buy(conn,y_id);
				}
				else if(choice== 1)
				{
					String c2= "Select * from orders where customer_id="+y_id;
					Vector<String> cnames = new Vector<String>();
					Vector<Vector<String>> data = new Vector<Vector<String>>();
					
					try {
						Statement stmt = conn.createStatement() ;
						ResultSet rs = stmt.executeQuery(c2);
						ResultSetMetaData rsmd = rs.getMetaData();
						Integer columnsNumber = rsmd.getColumnCount();
						for(int i=1; i<=columnsNumber; i++){
							cnames.addElement(rsmd.getColumnName(i));
						}
						while(rs.next()){
							Vector<String> row = new Vector<String>();
							for(int i=1; i<=columnsNumber; i++){
								row.addElement(rs.getString(i));
							}
							data.addElement(row);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Error in SQL");
						e.printStackTrace();
					}
					
					String caption = "Your Buying Details";
					JPanel panel = new JPanel();
					JTable table = new JTable(data, cnames);
					JScrollPane scrollPane = new JScrollPane(table);
					
					panel.setLayout( new BorderLayout());
					panel.setSize(400, 300);
					panel.add(scrollPane, BorderLayout.CENTER);
					
					Object [] c2message = {panel};
					JOptionPane.showMessageDialog(null, c2message, caption, JOptionPane.INFORMATION_MESSAGE);
				}
				else if(choice== 2)
				{
					String c3= "Select * from customer where customer_id="+y_id;
					Vector<String> cnames = new Vector<String>();
					Vector<Vector<String>> data = new Vector<Vector<String>>();
					
					try {
						Statement stmt = conn.createStatement() ;
						ResultSet rs = stmt.executeQuery(c3);
						ResultSetMetaData rsmd = rs.getMetaData();
						Integer columnsNumber = rsmd.getColumnCount();
						for(int i=1; i<=columnsNumber; i++){
							cnames.addElement(rsmd.getColumnName(i));
						}
						while(rs.next()){
							Vector<String> row = new Vector<String>();
							for(int i=1; i<=columnsNumber; i++){
								row.addElement(rs.getString(i));
							}
							data.addElement(row);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Error in SQL");
						e.printStackTrace();
					}
					
					String caption = "Your Details";
					JPanel panel = new JPanel();
					JTable table = new JTable(data, cnames);
					JScrollPane scrollPane = new JScrollPane(table);
					
					panel.setLayout( new BorderLayout());
					panel.setSize(400, 300);
					panel.add(scrollPane, BorderLayout.CENTER);
					
					Object [] c3message = {panel};
					JOptionPane.showMessageDialog(null, c3message, caption, JOptionPane.INFORMATION_MESSAGE);
				}
				if(choice==3)
					break;
			}
		}
		
		
	}
	
	public static int buy(Connection conn,String y_id)  {
		
		int a=0,cc=0,tot_bill,x,y,z,c,discount,d,pa_status;
		String or_c="",s,p_c="",p_p="", pay_id="",del_id="";
		s = "select count(order_id) from orders";
		try {
			conn.setAutoCommit(true); // changes shall be committed, not lost
			Statement stmt = conn.createStatement() ;
			ResultSet rs = stmt.executeQuery(s);
			while(rs.next()){
			or_c = rs.getString(1);
			}
			cc= Integer.parseInt(or_c)+1;
			or_c=""+cc;
			//JOptionPane.showMessageDialog(null, "Your result = " + cc);
		} // function displaying result in a table format
		
		 catch (SQLException e) {
			// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Not Find");
			e.printStackTrace();
		}
		
		tot_bill=x=0;
		while(true)
		{
			String c_q="select category_id,category_name from category";
			//Vector<String> ca_names = new Vector<String>();
			//Vector<String> ca_id = new Vector<String>();
			String[] ca_names= new String[100];
			String[] ca_id= new String[100];
			a=0;
			try {
				Statement stmt = conn.createStatement() ;
				ResultSet rs = stmt.executeQuery(c_q);
				ResultSetMetaData rsmd = rs.getMetaData();
				Integer columnsNumber = rsmd.getColumnCount();
				while(rs.next()){
					a++;
					for(int i=1; i<=columnsNumber; i++){
						if(i==1)
						ca_id[a]=rs.getString(i);
						if(i==2)
							ca_names[a]=rs.getString(i);
					}
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				if(s.isEmpty())
					JOptionPane.showMessageDialog(null, "Empty Values");
				else
					JOptionPane.showMessageDialog(null, "Error in SQL");
				e.printStackTrace();
			}
			JComboBox<String> ca = new JComboBox<String>();
			for(int i=1; i<=a; i++)
			{
				ca.addItem(ca_names[i]);
			}
			ca.addItem("Exit");
			int c1=0;
			Object [] message = { "Choose Category",ca};
			int result = JOptionPane.showConfirmDialog(null, message, "Choose the Category",JOptionPane.OK_CANCEL_OPTION);
			if(result==JOptionPane.OK_OPTION)
			{
				c1 = ca.getSelectedIndex();
				if(c1==a)
				{
					break;
				}
				p_c=ca_id[c1+1];
				//JOptionPane.showMessageDialog(null, "sfsf = " + p_c+"  fgdgf   " +c1);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "You are not selected Category!!! ");
			}
			
			String p_q="";
			p_q = "select product_id,product_name,price from products where category_id="+p_c;
			//JOptionPane.showMessageDialog(null, "Your query = " + p_q);
			String[] pa_names= new String[100];
			String[] pa_id= new String[100];
			String[] pa_price= new String[100];
			a=0;
			
			try {
				Statement stmt = conn.createStatement() ;
				ResultSet rs = stmt.executeQuery(p_q);
				ResultSetMetaData rsmd = rs.getMetaData();
				Integer columnsNumber = rsmd.getColumnCount();
				while(rs.next()){
					a++;
					for(int i=1; i<=columnsNumber; i++){
						if(i==1)
						pa_id[a]=rs.getString(i);
						if(i==2)
							pa_names[a]=rs.getString(i);
						if(i==3)
							pa_price[a]=rs.getString(i);
					}
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				if(p_q.isEmpty())
					JOptionPane.showMessageDialog(null, "Empty Values");
				else
					JOptionPane.showMessageDialog(null, "Error in SQL");
				e.printStackTrace();
			}
			
			JComboBox<String> po = new JComboBox<String>();
			for(int i=1; i<=a; i++)
			{
				po.addItem(pa_names[i]);
			}
			po.addItem("Exit");
			int p1=0;
			Object [] message1 = { "Choose Products",po};
			int result1 = JOptionPane.showConfirmDialog(null, message1, "Choose Products",JOptionPane.OK_CANCEL_OPTION);
			if(result1==JOptionPane.OK_OPTION)
			{
				p1 = po.getSelectedIndex();
				if(p1==a)
				{
					break;
				}
				p_p=pa_id[p1+1];
				z = Integer.parseInt(p_p);
				x = Integer.parseInt(pa_price[z]);
				String qu = (String)JOptionPane.showInputDialog(null,"Enter Quantity","Input Dialog", JOptionPane.PLAIN_MESSAGE);
				c = Integer.parseInt(qu);
				y = x * c;
				tot_bill+=y;
				
				String ords = "insert into order_details values ('" + or_c + "','" + p_p+ "'," + c + ")";
	
				//JOptionPane.showMessageDialog(null, "your query "+ords);
				ExecuteUpdateSQL(conn, ords);
				//JOptionPane.showMessageDialog(null, "problem in order details "+or_c+" "+p_p+" "+c);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Not Selected Item");
			}
			
		}
		
		s = "select count(payment_id) from payment";
		try {
			conn.setAutoCommit(true); // changes shall be committed, not lost
			Statement stmt = conn.createStatement() ;
			ResultSet rs = stmt.executeQuery(s);
			while(rs.next()){
			pay_id = rs.getString(1);
			}
			d= Integer.parseInt(pay_id)+1;
			pay_id=""+d;
		} // function displaying result in a table format
		
		 catch (SQLException e) {
			// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Not Find");
			e.printStackTrace();
		}
		
		s = "select count(delivery_id) from delivery";
		try {
			conn.setAutoCommit(true); // changes shall be committed, not lost
			Statement stmt = conn.createStatement() ;
			ResultSet rs = stmt.executeQuery(s);
			while(rs.next()){
			del_id = rs.getString(1);
			}
			d= Integer.parseInt(del_id)+1;
			del_id=""+d;
		} // function displaying result in a table format
		
		 catch (SQLException e) {
			// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Not Find");
			e.printStackTrace();
		}
		
		//JOptionPane.showMessageDialog(null, "p "+ pay_id+" d "+del_id);
		//SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
		//Date date = new Date();
		
		String disc = (String)JOptionPane.showInputDialog(null,"Enter Discount","Input Dialog", JOptionPane.PLAIN_MESSAGE);
		discount= Integer.parseInt(disc);
		double subs = (double)(discount/100)*tot_bill;
		tot_bill= tot_bill-(int)subs;
		//String jala="04/April/2015";
		pa_status = 1;
		//java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		
		String e = "Paid";
		
		String payment = "insert into payment values ('" + pay_id + "','" +e + "')";
		
		ExecuteUpdateSQL(conn, payment);
		
		e= "Non customize";
		int f = 101;
		
		String delivery = "insert into delivery values ('" + del_id + "','" +e + "'," +f + ")";
		
		ExecuteUpdateSQL(conn, delivery);
		String orders = "insert into orders values ('" + or_c + "','" + y_id+ "','" + pay_id+ "','" + del_id+ "','" + "04/April/2015"+ "'," + tot_bill + "," + (int)subs + "," + pa_status + ")";
		
		ExecuteUpdateSQL(conn, orders);
		//JOptionPane.showMessageDialog(null, "your query "+orders);
		return 0;
		
		
	}


}


