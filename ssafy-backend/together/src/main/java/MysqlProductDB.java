
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MysqlProductDB {

	static final String DB_URL = "jdbc:mysql://localhost:3306/together?useUniCode=yes&characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
	static final String USERNAME = "together";
	static final String PASSWORD = "together1!";

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatementInsert = null;
		PreparedStatement preparedStatementUpdate = null;

		String insertTableSQL = "INSERT IGNORE INTO product" + "(pro_id,pro_name,price,main_category,gram,img) VALUES"
				+ "(?,?,?,?,?,?)";

		try {
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("MysqlDB 연결.");

			connection.setAutoCommit(false);
			Document doc = null;

			String url = "http://emart.ssg.com/category/main.ssg?dispCtgId=0006140000&pgae=2";

			// 각각의 대분류 페이지에 들어가서 다시 화면 긁어오기
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 상품 정보 가져오기
			Elements pro_element = doc.select("div.com_tmpl_main_list");

			Iterator<Element> products = pro_element.select("li.cunit_t232").iterator();

			// 데이터를 db에 넣기 전에 쿼리문 준비
			preparedStatementInsert = connection.prepareStatement(insertTableSQL);

			while (products.hasNext()) {
				Element product = products.next();
				String pro_id = product.select("a").first().attr("data-info").toString();
				String pro_name = product.select("em.tx_ko").text();
				String price = product.select("em.ssg_price").text();
				String gram = product.select("div.unit").text();
				String img = product.select("img").text();

				System.out.println(pro_id +"/"+pro_name+"/"+price+"/"+gram+"/"+img+"\t");
				
//				pro_id,pro_name,price,main_category,gram,img
				preparedStatementInsert.setString(1, pro_id);
				preparedStatementInsert.setString(2, pro_name);
				preparedStatementInsert.setString(3, price);
				preparedStatementInsert.setString(4, "정육/계란류");
				preparedStatementInsert.setString(5, gram);
				preparedStatementInsert.setString(6, img);
				preparedStatementInsert.executeUpdate();
			}
			connection.commit();
			System.out.println("트랜잭션 정상처리");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (preparedStatementInsert != null)
				preparedStatementInsert.close();
			if (preparedStatementUpdate != null)
				preparedStatementUpdate.close();
			if (connection != null)
				connection.close();
		}
		System.out.println("MysqlDB 연결종료.");

	}

}