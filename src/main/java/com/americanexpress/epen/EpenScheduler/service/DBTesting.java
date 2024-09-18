package com.americanexpress.epen.EpenScheduler.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBTesting {

    @PersistenceContext
    EntityManager em;

    public List<String> getDataFromDB(){
        List<Object> result;
        List<String> resultSet = new ArrayList<>();
        try{
            String functionCall = "Select * from epen.table_a";
            Query query = em.createNativeQuery(functionCall);
            result = query.getResultList();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }


        for (Object obj: result){
            Object[] tempObj = (Object[]) obj;
            String name = (String)tempObj[1];
            resultSet.add(name);
        }

        return resultSet;
    }
}
