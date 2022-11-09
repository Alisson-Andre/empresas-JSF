package br.com.empresas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresas.model.Empresa;

public class EmpresasDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public EmpresasDAO() {

	}

	public EmpresasDAO(EntityManager manager) {
		this.manager = manager;
	}

	public Empresa porId(Long id) {
		return manager.find(Empresa.class, id);
	}

	public List<Empresa> pesquisar(String nome) {
		String jpql = "from Empresa where razaoSocial like :razaoSocial";

		TypedQuery<Empresa> query = manager.createQuery(jpql, Empresa.class);

		query.setParameter("razaoSocial", nome + "%");
		return query.getResultList();
	}

	public List<Empresa> ListaTodasEmpresas() {
		return manager.createQuery("from Empresa", Empresa.class).getResultList();
	}
	
	public void salvar(Empresa empresa) {
		try {
			manager.getTransaction().begin();
			
			if (empresa.getId() == null) {
				manager.persist(empresa);
			} else {
				manager.merge(empresa);
			}
			
			manager.getTransaction().commit();
			
		} catch (Exception e) {
			manager.getTransaction().rollback();
		}
	}

	public void remover(Empresa empresa) {
		empresa = porId(empresa.getId());
		
		try {
			manager.getTransaction().begin();
			manager.remove(empresa);
			manager.getTransaction().commit();

		} catch (Exception e) {
			manager.getTransaction().rollback();
		}
	}
}
