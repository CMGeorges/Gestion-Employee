package com.cmgeorges.examen.mvc.employees;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeesApplication {
    @Bean
    public Hibernate5Module datatypeHibernateModule(){
        Hibernate5Module hibernate5Module= new Hibernate5Module();
        hibernate5Module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        hibernate5Module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);

        return hibernate5Module;
    }



    public static void main(String[] args) {
        SpringApplication.run(EmployeesApplication.class, args);
    }




}
