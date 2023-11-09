package br.com.invillia.cdb.wallet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WalletApplicationTests {

//    WalletApplication application = new WalletApplication();
    @Test
    void contextLoads() {
        String[] args = new String[0];
        SpringApplication.run(WalletApplication.class, args);
        assert true;
    }

}
