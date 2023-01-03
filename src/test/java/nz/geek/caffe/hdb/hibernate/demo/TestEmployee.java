/**
 * Copyright 2014,2016 Andrew Clemons
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nz.geek.caffe.hdb.hibernate.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

/**
 */
public class TestEmployee {

    SessionFactory sessionFactory;

    private TransactionOperations transactionTemplate;

    /**
     */
    @BeforeEach
    public void setUp() {
        final DataSource ds = new DriverManagerDataSource(System.getProperty("jdbc.url", "jdbc:sap://localhost:30115"),
                System.getProperty("jdbc.user", "hibernate"), System.getProperty("jdbc.password", "hibernate"));

        final LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(ds);
        builder.setProperty(AvailableSettings.HBM2DDL_AUTO, "create-drop");
        builder.setProperty("hibernate.dialect.hana.use_unicode_string_types", "true");

        builder.addAnnotatedClass(Employee.class);

        this.sessionFactory = builder.buildSessionFactory();

        final HibernateTransactionManager txnMgr = new HibernateTransactionManager();
        txnMgr.setDataSource(ds);
        txnMgr.setSessionFactory(this.sessionFactory);
        txnMgr.afterPropertiesSet();

        this.transactionTemplate = new TransactionTemplate(txnMgr);
    }

    /**
     */
    @Test
    public void testHdb() {
        final String name = "Timmi Tester";

        final Integer id = this.transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(final TransactionStatus status) {
                final Employee e = new Employee();
                e.setName(name);

                TestEmployee.this.sessionFactory.getCurrentSession().persist(e);

                return Integer.valueOf(e.getId());
            }
        });

        final Employee employee;
        final Session session = TestEmployee.this.sessionFactory.openSession();
        try {
            employee = session.get(Employee.class, id);
        } finally {
            SessionFactoryUtils.closeSession(session);
        }

        assertEquals(name, employee.getName());
    }
}
