hibernate-hdb-demo
==================

Demo HDB with Hibernate

I keep getting queries about using Hibernate with SAP HANA. It is actually no  
different to using Hibernate with any other database. Just configure the proper  
dialect and you are ready to start development.

    $ mvn clean install -Djdbc.url=jdbc:sap://localhost:30215 -Djdbc.user=hibernate -Djdbc.password=hibernate

Or to test with the row store (the example uses auto-generated tables):

    $ mvn clean install -Djdbc.url=jdbc:sap://localhost:30215 -Djdbc.user=hibernate -Djdbc.password=hibernate -Dhibernate.dialect=org.hibernate.dialect.HANARowStoreDialect

You will need to manually install the HDB JDBC driver into your local maven  
repository.

The simplest way is to download the HANA Cloud SDK and extract the driver from it.
It can be found here: [HANA Cloud SDK](https://repo1.maven.org/maven2/com/sap/cloud/neo-java-web-sdk/2.107.9/neo-java-web-sdk-2.107.9.zip).

    $ wget https://repo1.maven.org/maven2/com/sap/cloud/neo-java-web-sdk/2.107.9/neo-java-web-sdk-2.107.9.zip && \
      unzip neo-java-web-sdk-2.107.9.zip repository/.archive/lib/ngdbc.jar && \
      mvn install:install-file -Dfile=repository/.archive/lib/ngdbc.jar -DgroupId=com.sap.db.hdb -DartifactId=com.sap.db.jdbc -Dversion=2.3.45.5a9ff585a5853e0cd59765ce72729abf7f2475b5 -Dpackaging=jar && \
      rm -r repository
