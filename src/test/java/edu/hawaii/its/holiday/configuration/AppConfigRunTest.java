package edu.hawaii.its.holiday.configuration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AppConfigRunTest {

    @Test
    public void construction() {
        AppConfigLocalhost appConfigLocalhost = new AppConfigLocalhost();
        assertNotNull(appConfigLocalhost);

        appConfigLocalhost.init();
        assertNotNull(appConfigLocalhost);
    }

}
