package com.inicu.postgres.utility;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.inicu.config.PersistenceManager;

/**
 * it includes functionalities for database query..
 * 
 * @author iNICU
 *
 */

@Repository
public class InicuPostgresUtililty {

	public static EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
	// private EntityManager emLocal =
	// PersistenceManager.INSTANCE.getEntityManager();

	public EntityManager getEntityManager() {
		EntityManager em = null;
		em = PersistenceManager.INSTANCE.getEntityManager();
		return em;
	}

	@SuppressWarnings("finally")
	public List executeMappedObjectCustomizedQuery(String query) throws Exception { // do some custom job on a table
																					// that is mapped to a Java
																					// entities,
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		List resultList = null;
		try {
			System.out.println("#############for query " + query);
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}

			Query list1 = emLocal.createQuery(query);
			resultList = list1.getResultList();
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();
			if (!BasicUtils.isEmpty(resultList) && resultList.get(0) == null) {
				resultList = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw ex;
		} finally {
			if (emLocal.isOpen())
				emLocal.close();
		}
		return resultList;
	}

	public List<?> executeMappedObjectCustomizedRowLimitQuery(String query, Integer maxCount) throws Exception {
		// do some custom job on a table that is mapped to a Java entities,
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		List<?> resultList = null;
		try {
			System.out.println("#############for query " + query);
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}

			Query list1 = emLocal.createQuery(query);
			list1.setMaxResults(maxCount);
			resultList = list1.getResultList();
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();
			if (!BasicUtils.isEmpty(resultList) && resultList.get(0) == null) {
				resultList = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw ex;
		} finally {
			if (emLocal.isOpen())
				emLocal.close();
		}
		return resultList;
	}

	public List<?> saveMultipleObject(List<?> obj) throws Exception {

		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		try {

			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}

			for (int i = 0; i < obj.size(); i++) {
				emLocal.merge(obj.get(i));
			}
			emLocal.getTransaction().commit();

			emLocal.clear();
			emLocal.close();
			return obj;

		} catch (Exception e) {
			e.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
				emLocal.close();
			}
			throw e;
		}

		finally {
			if (emLocal.isOpen())
				emLocal.close();
		}

	}

	@SuppressWarnings("finally")
	public Object saveObject(Object obj) throws Exception {
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		try {

			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}
			obj = emLocal.merge(obj);
			emLocal.getTransaction().commit();
			// @Sourabh Verma added on 10-05-2017
			// to update trigger based fields such as Creationtime/modificationtime
			emLocal.refresh(obj);
			emLocal.clear();
			emLocal.close();
			return obj;

		} catch (Exception e) {
			e.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
				emLocal.close();
			}
			throw e;
		}

		finally {
			if (emLocal.isOpen())
				emLocal.close();
		}

	}

	@SuppressWarnings("finally")
	public Object updateObject(Object obj) throws Exception { // also using this functoin for saving transient object
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		try {

			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}
			emLocal.persist(obj);
			emLocal.getTransaction().commit();
			// @Sourabh Verma added on 10-05-2017
			// to update trigger based fields such as Creationtime/modificationtime
			emLocal.refresh(obj);
			emLocal.clear();
			emLocal.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw e;
		}

		finally {
			if (emLocal.isOpen())
				emLocal.close();
		}
	}

	public List executePsqlDirectQuery(String query) {// to-do some custom job on a table that is not mapped to any of
														// my Java entities,
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		List resultList = null;
		try {
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}
			Query list1 = emLocal.createNativeQuery(query);
			resultList = list1.getResultList();
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();
			if (!BasicUtils.isEmpty(resultList) && resultList.get(0) == null) {
				resultList = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw ex;
		} finally {
			if (emLocal.isOpen())
				emLocal.close();
		}

		return resultList;
	}


	public int executeDirectQuery(String query) {// to-do some custom job on a table that is not mapped to any of
		// my Java entities,
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		int resultList = 0;
		try {
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}
			Query list1 = emLocal.createNativeQuery(query);
			resultList = list1.executeUpdate();
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw ex;
		} finally {
			if (emLocal.isOpen())
				emLocal.close();
		}

		return resultList;
	}

	public void removeObject(Object obj) throws Exception {
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		try {
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}
			emLocal.remove(obj);
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw e;
		}

		finally {
			if (emLocal.isOpen())
				emLocal.close();
		}
	}

	public void updateOrDelQuery(String delQuery) throws Exception {
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		List resultList = null;
		try {
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}
			emLocal.createQuery(delQuery).executeUpdate();
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw ex;
		}

		finally {
			if (emLocal.isOpen())
				emLocal.close();
		}
	}

	public void executeInsertQuery(String query) {// to-do some custom job on a table that is not mapped to any of my
													// Java entities,
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		try {
			System.out.println(query);
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}
			Query list1 = emLocal.createNativeQuery(query);
			System.out.println(list1);
			int flag = list1.executeUpdate();
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw ex;
		}

		finally {
			if (emLocal.isOpen())
				emLocal.close();
		}
	}


	public List getListFromNativeQuery(String query) {
		// do some custom job on a table that is mapped to a Java entities,
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		List resultList = null;
		try {
			System.out.println("#############for query " + query);
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}

			Query list1 = emLocal.createNativeQuery(query);
			resultList = list1.getResultList();
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();
			if (!BasicUtils.isEmpty(resultList) && resultList.get(0) == null) {
				resultList = null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw ex;

		} finally {
			if (emLocal.isOpen())
				emLocal.close();
		}

		return resultList;

	}

	public void updateOrDelNativeQuery(String delQuery) throws Exception {
		EntityManager emLocal = PersistenceManager.INSTANCE.getEntityManager();
		List resultList = null;
		try {
			if (!emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().begin();
			}
			emLocal.createNativeQuery(delQuery).executeUpdate();
			emLocal.getTransaction().commit();
			emLocal.clear();
			emLocal.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (emLocal.getTransaction().isActive()) {
				emLocal.getTransaction().rollback();
			}
			throw ex;
		}

		finally {
			if (emLocal.isOpen())
				emLocal.close();
		}
	}

	public List testPostGresQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}
}
