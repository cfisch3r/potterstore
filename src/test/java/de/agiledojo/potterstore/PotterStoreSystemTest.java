package de.agiledojo.potterstore;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = PotterStore.class)
@AutoConfigureMockMvc
public class PotterStoreSystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mvcExists() {
        Assertions.assertThat(mockMvc).isNotNull();
    }
}
