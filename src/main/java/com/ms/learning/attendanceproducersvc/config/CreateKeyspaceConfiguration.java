package com.ms.learning.attendanceproducersvc.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CreateKeyspaceConfiguration extends AbstractCassandraConfiguration implements BeanClassLoaderAware {

    @Value("${cassandra.contact.port}")
    public int Port ;
    private String KEYSPACE = "mslearning";

    @Value("${cassandra.contact.point}")
    private String CONTACT_POINT;



    @Override
    public String getContactPoints() {
        return CONTACT_POINT;
    }
    @Override
    protected int getPort() {
        return Port;
    }

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }
//    @Override
//    protected String getLocalDataCenter() {
//        return "datacenter1";
//    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {

        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.
                createKeyspace("mslearning").ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication();


        return Arrays.asList(specification);
    }

    //Drop KEYSPACE on spp shutdown
    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {

        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(KEYSPACE));
    }


    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.sapient.ms.learning.attendanceproducersvc.entity"};
    }

}
