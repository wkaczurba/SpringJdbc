package com.db.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.db.CountryRepository;
import com.domain.Continent;
import com.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

//@Repository (skipped for now)
public class JdbcCountryRepository implements CountryRepository {
	
	private final String SELECT_COUNT = "SELECT COUNT(name) FROM country";
	private final String SELECT_COUNTRY = "SELECT `Code`,`Name`,`Continent`,`Region`,`SurfaceArea`,`IndepYear`,`Population`,`LifeExpectancy`,`GNP`,`GNPOld`,`LocalName`,`GovernmentForm`,`HeadOfState`,`Capital`, `Code2` FROM country";
	private final String SELECT_COUNTRY_BY_CODE = SELECT_COUNTRY + " WHERE `code` = ? LIMIT ?";
	private final String SELECT_COUNTRY_BY_NAME = SELECT_COUNTRY + " WHERE `name` = ? LIMIT ?";
	private final String SELECT_ALL_CODES = "SELECT (`code`) FROM country ";
	private final String SELECT_ALL_NAMES = "SELECT (`name`) FROM country ";	
	
	JdbcTemplate jdbcTemplate;
	@Autowired
	public JdbcCountryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int count() {
		return jdbcTemplate.queryForObject(SELECT_COUNT, Integer.class);
	}

	@Override
	public Country getCountryByCode(String code) {
		List<Country> countries = jdbcTemplate.query(SELECT_COUNTRY_BY_CODE, this::mapRow, code, 1);
		
		if (countries.size() == 1)
			return countries.get(0);
		else if (countries.size() == 0)
			return null;
		else throw new RuntimeException("Received more than one country even though the limit was set to 1");
	}

	@Override
	public List<String> getAllCountryCodes() {		
		return jdbcTemplate.queryForList(SELECT_ALL_CODES, String.class);
	}

	@Override
	public Country getCountryByName(String name) {
		List<Country> countries = jdbcTemplate.query(SELECT_COUNTRY_BY_NAME, this::mapRow, name, 1);
		
		if (countries.size() == 1)
			return countries.get(0);
		else if (countries.size() == 0)
			return null;
		else		
			throw new RuntimeException("Received more than one country even though the limit was set to 1");
	}

	@Override
	public List<String> getAllCountryNames() {
		return jdbcTemplate.queryForList(SELECT_ALL_NAMES, String.class);
	}
	
	private Country mapRow(ResultSet rs, int rowNum) throws SQLException {
		Country country = new Country();
		country.setCode(rs.getString("Code"));
		//country.set
		country.setName(rs.getString("name")); // Name`
		country.setContinent(Continent.valueOf( 
				rs.getString("continent").toUpperCase().replace(" ", "_") )); // FIXME: Return type is ENUM.
		
		country.setRegion(rs.getString("region"));
		country.setSurfaceArea(rs.getFloat("SurfaceArea"));
		country.setIndepYear(rs.getInt("IndepYear"));
		country.setPopulation(rs.getInt("Population"));
		country.setLifeExpectancy(rs.getFloat("LifeExpectancy"));
		country.setGnp(rs.getFloat("GNP"));
		country.setGnpold(rs.getFloat("GNPOld")); //`GNPOld`
		country.setLocalName(rs.getString("LocalName")); // `LocalName`
		country.setGovernmentForm(rs.getString("GovernmentForm"));
		country.setHeadOfState(rs.getString("HeadOfState"));
		country.setCapital(rs.getString("Capital"));
		country.setCode2(rs.getString("Code2"));	
		// TODO Auto-generated method stub
		return country;
	}

}
