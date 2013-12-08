package com.fitdrift.util.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class is responsible for creating one and only one EntityManagerFactory.
 * From the EntityManagerFactory, any number of EntityManager objects can be created.
 * 
 * @author Matthew Allen
 * @version 20131208
 */
public class EntityManagerUtil {
	private static EntityManagerFactory emf;
	static {
		emf = Persistence.createEntityManagerFactory("com.fitdrift.jpa");
	}
	
	public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
