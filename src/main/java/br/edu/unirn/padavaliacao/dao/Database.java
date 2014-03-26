package br.edu.unirn.padavaliacao.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Database {
	
	private static EntityManager singleton;
	
	private Database(){	}

	private static EntityManager createEntityManager(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConexaoDB");
		emf.getCache().evictAll();
		return singleton = emf.createEntityManager();
	}
	
	public static EntityManager getEntityManager(){
		if ( (singleton==null)
			|| (!singleton.isOpen()) ){
			singleton = createEntityManager();
		}
		return singleton;
	}

}