package com.vinayak.ecommerce.dao;

import com.vinayak.ecommerce.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel="countries",path="countries")
public interface CountryRepository extends JpaRepository<Country,Integer> {
}
