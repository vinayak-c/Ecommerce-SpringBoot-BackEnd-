package com.vinayak.ecommerce.config;

import com.vinayak.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import java.util.stream.Collectors;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager){
        this.entityManager=theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for Product: PUT, POST and DELETE
        disableHttpMethods(Product.class,config, theUnsupportedActions);

        // disable HTTP methods for ProductCategory: PUT, POST and DELETE
        disableHttpMethods(ProductCategory.class,config, theUnsupportedActions);

        // disable HTTP methods for State: PUT, POST and DELETE
        disableHttpMethods(State.class,config, theUnsupportedActions);

        // disable HTTP methods for Country: PUT, POST and DELETE
        disableHttpMethods(Country.class,config, theUnsupportedActions);

        // disable HTTP methods for Country: PUT, POST and DELETE
        disableHttpMethods(Order.class,config, theUnsupportedActions);

        exposeIds(config);

        //configure cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        /*
        //--expose entity ids

        //--get list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //--create an array of entity types
        List<Class> entityClasses=new ArrayList<>();

        //--get entity types for the entities
        for(EntityType tempEntityType:entities){
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainTypes=entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);*/

        config.exposeIdsFor(entityManager.
                            getMetamodel().getEntities().stream().map(e->e.getJavaType())
                           .collect(Collectors.toList()).toArray(new Class[0]));

    }
}
