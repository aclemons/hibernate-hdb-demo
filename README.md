hibernate-hdb-demo
==================

Demo HDB with Hibernate

I keep getting queries about using Hibernate with SAP HANA. It is actually no  
different to using Hibernate with any other database. Just configure the proper  
dialect and you are finished.

    $ mvn clean install -Djdbc.url=jdbc:sap://localhost:30215 -Djdbc.user=hibernate -Djdbc.password=hibernate

Or to test with the row store (the example uses auto-generated tables):

    $ mvn clean install -Djdbc.url=jdbc:sap://localhost:30215 -Djdbc.user=hibernate -Djdbc.password=hibernate -Dhibernate.dialect=org.hibernate.dialect.HANARowStoreDialect

You will need to manually install the HDB JDBC driver into your local maven  
repository.

It can be found here: [HANA Driver](https://tools.hana.ondemand.com/). Then download the latest neo-java-web-sdk.  

    $ unzip neo-java-web-sdk-2.40.10.2.zip repository/.archive/lib/ngdbc.jar && mvn install:install-file -Dfile=repository/.archive/lib/ngdbc.jar -DgroupId=com.sap.db.hdb -DartifactId=com.sap.db.jdbc -Dversion=1.96.0.24de221c9810b5c802f2c50962c535d8a9018577 -Dpackaging=jar && rm -f repository
