package it.polito.TdP.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import it.polito.TdP.country.model.Country;
import it.polito.TdP.country.model.CountryPair;

public class CountryDAO {

	public List<Country> listCountry() {
		List<Country> countries = new LinkedList<Country>();
		final String sql = "SELECT CCode, StateAbb, StateNme FROM country;";
		Connection conn = DBConnect.getConnection();

		try {
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(sql);
			while (res.next()) {
				int cCode = res.getInt("CCode");
				String stateAbb = res.getString("StateAbb");
				String stateNme = res.getString("StateNme");
				Country country = new Country(cCode, stateAbb, stateNme);
				countries.add(country);
			}
			res.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return countries;
	}

	public boolean confinanti(Country c1, Country c2) {

		final String sql = "SELECT state1no, state2no FROM contiguity WHERE state1no=? AND state2no=? AND conttype=1";

		try {

			Connection conn = (Connection) DBConnect.getConnection();
			java.sql.PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, c1.getcCode());
			st.setInt(2, c2.getcCode());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				rs.close();
				conn.close();

				return true;

			}

			rs.close();
			conn.close();

			return false;

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return false;
	}

	public List<Country> listAdiacenti(Country c) {

		final String sql = "SELECT country.CCode, country.StateAbb, country.StateNme " + "FROM contiguity, country "
				+ "WHERE contiguity.state2no = country.CCode " + "AND contiguity.conttype =  1 "
				+ "AND contiguity.state1no = ?;";

		try {

			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, c.getcCode());

			ResultSet res = st.executeQuery();

			List<Country> list = new LinkedList<>();

			while (res.next()) {
				Country c2 = new Country(res.getInt("CCode"), res.getString("StateAbb"), res.getString("StateNme"));
				list.add(c2);
			}

			res.close();
			conn.close();

			return list;

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;

		}
	}

	public List<CountryPair> listCoppieCountryConfinanti() {
		final String sql = "SELECT country1.CCode AS CCode1, country1.StateAbb AS StateAbb1, country1.StateNme as StateNme1, " + 
				"country2.CCode AS CCode2, country2.StateAbb AS StateAbb2, country2.StateNme as StateNme2 " + 
				"FROM country AS country1, contiguity, country AS country2 " + 
				"WHERE country1.CCode = state1no AND country2.CCode = state2no " + 
				"AND conttype = 1;";
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			List<CountryPair> list = new LinkedList<>();
			while(res.next()) {
				Country c1 = new Country(res.getInt("CCode1"), res.getString("StateAbb1"), res.getString("StateNme1"));
				Country c2 = new Country(res.getInt("CCode2"), res.getString("StateAbb2"), res.getString("StateNme2"));
				list.add(new CountryPair(c1, c2));
			}
			res.close();
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
