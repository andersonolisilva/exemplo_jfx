package br.edu.unirn.padavaliacao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAO<T> {

	private void change(T c, OperacaoDatabase op) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();

		switch (op) {
		case INSERIR:
			em.persist(c);
			break;
		case ALTERAR:
			em.merge(c);
			break;
		case REMOVER:
			em.remove(c);
			break;

		}
		em.getTransaction().commit();
	}

	public void create(T c) {
		change(c, OperacaoDatabase.INSERIR);
	}

	public void update(T c) {
		change(c, OperacaoDatabase.ALTERAR);
	}

	public void delete(T c) {
		change(c, OperacaoDatabase.REMOVER);
	}

	public abstract Class<T> getClassType();

	public T findByPrimaryKey(long id) {
		EntityManager em = getEntityManager();
		T c = em.find(getClassType(), id);
		return c;
	}

	public List<T> findAll() {
		EntityManager em = getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getClassType());
		TypedQuery<T> typedQuery = em.createQuery(query.select(query
				.from(getClassType())));
		List<T> c = typedQuery.getResultList();
		return c;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllLike(String coluna, String valor) {
		String tabela = getClassType().getSimpleName();
		String jpql = "from " + tabela + " where " + coluna + " like :valor";
		EntityManager em = getEntityManager();
		Query q = em.createQuery(jpql);
		q.setParameter("valor", "%" + valor + "%");
		List<T> retorno = q.getResultList();
		return retorno;
	}

	protected EntityManager getEntityManager() {
		return Database.getEntityManager();
	}
}

enum OperacaoDatabase {
	INSERIR, ALTERAR, REMOVER;
}
