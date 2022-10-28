package br.com.empresas.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.empresas.model.Empresa;
import br.com.empresas.repository.EmpresasDAO;
import br.com.empresas.util.Transacional;

public class CadastroEmpresaService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresasDAO empresas;
	
	@Transacional
	public void salvar(Empresa empresa) {
		empresas.guardar(empresa);
	}
	
	@Transacional
	public void excluir(Empresa empresa) {
		empresas.remover(empresa);
	}
}
