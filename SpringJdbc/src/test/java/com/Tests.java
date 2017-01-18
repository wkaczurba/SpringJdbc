package com;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.JdbcConfig;

import org.springframework.test.context.ContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JdbcConfig.class})
public class Tests {

	@Test
	public void test1() {
		
	}
}

