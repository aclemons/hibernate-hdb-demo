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

The simplest way is to download the HANA Cloud SDK and extract the driver from it.
It can be found here: [HANA Cloud SDK](https://repo1.maven.org/maven2/com/sap/cloud/neo-java-web-sdk/3.20.3.1/neo-java-web-sdk-3.20.3.1.zip).

    $ wget https://repo1.maven.org/maven2/com/sap/cloud/neo-java-web-sdk/3.20.3.1/neo-java-web-sdk-3.20.3.1.zip && \
      unzip neo-java-web-sdk-3.20.3.1.zip repository/.archive/lib/ngdbc.jar && \
      mvn install:install-file -Dfile=repository/.archive/lib/ngdbc.jar -DgroupId=com.sap.db.hdb -DartifactId=com.sap.db.jdbc -Dversion=1.120.6.3b4cf1b21ea34a62ca636a789f25ab6f3e5dafb1 -Dpackaging=jar && \
      rm -r repository
