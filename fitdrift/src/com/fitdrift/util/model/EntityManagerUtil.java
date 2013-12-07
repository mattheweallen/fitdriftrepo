package com.fitdrift.util.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static EntityManagerFactory emf;
	static {
		emf = Persistence.createEntityManagerFactory("com.fitdrift.jpa");
	}
	
	public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
