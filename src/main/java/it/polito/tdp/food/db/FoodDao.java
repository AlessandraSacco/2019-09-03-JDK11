package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public void listAllPortions(Map<String, Portion> idMap){
		String sql = "SELECT * FROM porzione " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(!idMap.containsKey("portion_display_name")) {
					Portion p = new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							);
					
					idMap.put(p.getPortion_display_name(), p);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
		

		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}
	
	public List<Portion> getVertici(Map<String, Portion> idMap, Double calories){
		String sql = "SELECT DISTINCT(p.portion_display_name) "
				+ "FROM porzione p "
				+ "WHERE p.calories< ? "
				+ "ORDER BY p.portion_display_name " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDouble(1, calories);
		    List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(idMap.containsKey(res.getString("p.portion_display_name"))) {
						Portion p = idMap.get(res.getString("p.portion_display_name"));
						list.add(p);
					}
				
				
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}

	public List<Arco> getArchi(Map<String, Portion> idMap, Double calories) {
		String sql = "SELECT p1.portion_display_name, p2.portion_display_name, COUNT(DISTINCT(p1.food_code)) AS peso "
				+ "FROM porzione p1, porzione p2 "
				+ "WHERE p1.food_code=p2.food_code AND p1.portion_display_name>p2.portion_display_name "
				+ "AND p1.calories=p2.calories AND p1.calories< ? AND p1.portion_id<>p2.portion_id "
				+ "GROUP BY p1.portion_display_name, p2.portion_display_name " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDouble(1, calories);
		    List<Arco> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(idMap.containsKey(res.getString("p1.portion_display_name")) && idMap.containsKey(res.getString("p2.portion_display_name")) ) {
						Arco a = new Arco(idMap.get(res.getString("p1.portion_display_name")),idMap.get(res.getString("p2.portion_display_name")),res.getInt("peso"));
						list.add(a);
					}
				
				
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	

}
