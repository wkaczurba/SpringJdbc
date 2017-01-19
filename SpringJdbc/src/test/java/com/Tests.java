package com;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ActiveProfiles;

import com.config.JdbcConfig;
import com.db.CountryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JdbcConfig.class})
@ActiveProfiles("test")
public class Tests {
	
	@Autowired
	CountryRepository countryRepository;

	@Test
	public void countryCodesTest() {
		System.out.println(countryRepository.getAllCountryCodes());
		System.out.println(countryRepository.getAllCountryNames());
		System.out.println(countryRepository.getCountryByCode("POL"));
		System.out.println(countryRepository.getCountryByName("Ireland"));
		
	}
}

