package com.game.kalah;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.game.kalah.model.Kalah;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void kalahTest() {
        
    	Kalah kalah = new Kalah();
    	
    	assertNotNull(kalah);
    }


}
