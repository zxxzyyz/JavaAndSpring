package hello.springtx.propagation;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("transaction begin");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("transaction commit begin");
        txManager.commit(status);
        log.info("transaction commit end");
        log.info("transaction end");
        log.info("transaction end");
    }

    @Test
    void rollback() {
        log.info("transaction begin");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("transaction rollback begin");
        txManager.rollback(status);
        log.info("transaction rollback end");
        log.info("transaction end");
    }

    @Test
    void double_commit() {
        log.info("tx1");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx1 commit");
        txManager.commit(tx1);

        log.info("tx2");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx2 commit");
        txManager.commit(tx2);
    }

    @Test
    void inner_commit() {
        log.info("tx1");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx1.isNewTransaction()={}", tx1.isNewTransaction());

        log.info("tx2");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx2.isNewTransaction()={}", tx2.isNewTransaction());

        txManager.commit(tx2);
        txManager.commit(tx1);
    }

    @Test
    void outer_commit() {
        log.info("tx1");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx1.isNewTransaction()={}", tx1.isNewTransaction());

        log.info("tx2");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx2.isNewTransaction()={}", tx2.isNewTransaction());

        txManager.commit(tx2);
        txManager.rollback(tx1);
    }

    @Test
    void inner_rollback() {
        log.info("tx1");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx1.isNewTransaction()={}", tx1.isNewTransaction());

        log.info("tx2");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx2.isNewTransaction()={}", tx2.isNewTransaction());

        txManager.rollback(tx2);
        txManager.commit(tx1);
    }

    @Test
    void inner_rollback_require_new() {
        log.info("tx1");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("tx1.isNewTransaction()={}", tx1.isNewTransaction());

        log.info("tx2");
        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus tx2 = txManager.getTransaction(definition);
        log.info("tx2.isNewTransaction()={}", tx2.isNewTransaction());

        txManager.rollback(tx2);
        txManager.commit(tx1);
    }
}
