package com.rfidtag;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"integration", "test"})
@SpringBootTest
class RfidTagApplicationTests {

    @Test
    void contextLoads() {
    }

}
