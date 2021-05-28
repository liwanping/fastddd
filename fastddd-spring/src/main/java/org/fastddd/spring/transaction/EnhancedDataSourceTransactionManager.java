package org.fastddd.spring.transaction;

import org.fastddd.core.injector.InjectorFactory;
import org.fastddd.core.session.SessionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;

/**
 * Enhanced datasource transaction manager
 * @author: frank.li
 * @date: 2021/3/29
 */
public class EnhancedDataSourceTransactionManager extends DataSourceTransactionManager {

    private SessionManager sessionManager = InjectorFactory.getInstance(SessionManager.class);

    public EnhancedDataSourceTransactionManager() {
        super();
    }

    public EnhancedDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        sessionManager.registerSession(true);
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {
        sessionManager.requireSession().commit();
        super.doCommit(status);
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        sessionManager.requireSession().rollback();
        super.doRollback(status);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        try {
            super.doCleanupAfterCompletion(transaction);
            sessionManager.requireSession().cleanupAfterCompletion();
        } finally {
            sessionManager.closeSession();
        }
    }
}
