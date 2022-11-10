package br.com.empresas.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.empresas.converter.RamoAtividadeConverter;
import br.com.empresas.model.Empresa;
import br.com.empresas.model.RamoAtividade;
import br.com.empresas.model.TipoEmpresa;
import br.com.empresas.repository.EmpresasDAO;
import br.com.empresas.repository.RamoAtividadesDAO;
import br.com.empresas.util.FacesMessages;

@Named
@ViewScoped
public class GestaoEmpresasBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	private FacesMessages messages;
	
	@Inject
	private RamoAtividadesDAO ramoAtividadesDAO;
	
	private List<Empresa> listaEmpresas;

	private String termoPesquisa;

	private Converter ramoAtividadeConverter;
	
	private Empresa empresa;
	
	public void prepararNovaEmpresa() {
		empresa = new Empresa();
	}
	
	public void prepararEdicao() {
		ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(empresa.getRamoAtividade()));
	}
	
	public void salvar() {
		empresasDAO.salvar(empresa);
		
		atualizarRegistros();
		
		PrimeFaces.current().ajax().update(Arrays.asList("frm:empresasDataTable", "frm:messages"));
		
		messages.info("Empresa salva com sucesso!");
	
	}

	public void excluir() {
		empresasDAO.remover(empresa);
		
		empresa = null;
		
		atualizarRegistros();

		messages.info("Empresa excluída com sucesso!");
	}
	
	public void pesquisar() {
		listaEmpresas = empresasDAO.pesquisar(termoPesquisa);

		if (listaEmpresas.isEmpty()) {
			messages.info("Sua consulta não retornou registros");
		}
	}

	public void todasEmpresas() {
		listaEmpresas = empresasDAO.ListaTodasEmpresas();
	}
	
	public List<RamoAtividade> completarRamoAtividade(String termo) {
		List<RamoAtividade> listaRamoAtividades = ramoAtividadesDAO.pesquisar(termo);
		
		ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);
		
		return listaRamoAtividades;
	}

	private boolean jaHouvePesquisa() {
		return termoPesquisa != null && "".equals(termoPesquisa);
	}
	
	private void atualizarRegistros() {
		if (jaHouvePesquisa()) {
			pesquisar();
		} else {
			todasEmpresas();
		}
	}
	
	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public TipoEmpresa[] getTiposEmpresa() {
		return TipoEmpresa.values();
	}
	
	public Converter getRamoAtividadeConverter() {
		return ramoAtividadeConverter;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public boolean isEmpresaSelecionada() {
		return empresa != null && empresa.getId() != null;
	}
}
