package com.multitrans.wasalliya;

import com.multitrans.wasalliya.service.TourService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("dev")
@TestPropertySource(properties = {"app.optimizer.strategy=ai"})
class WasalliyaApplicationTests {

    @Autowired
    private TourService tourService;

	@Test
	void testOptimizerStrategyIsCorrectlyInjected(){
        String optimizerName = tourService.getOptimizerName().toString();
        System.out.println("the optimizer is working now is " + optimizerName);

        assertNotEquals("NearestNeighbor", optimizerName);
        assertNotEquals("ClarkeWright", optimizerName);
    }

}
