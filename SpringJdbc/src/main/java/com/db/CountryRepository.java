package com.db;

import java.util.List;
import com.domain.Country;

public interface CountryRepository {
	int count();
	Country getCountryByCode(String code);
	List<String> getAllCountryCodes();
	Country getCountryByName(String name);
	List<String> getAllCountryNames();
}
