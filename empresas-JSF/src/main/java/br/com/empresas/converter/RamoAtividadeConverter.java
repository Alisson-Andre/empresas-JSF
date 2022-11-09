package br.com.empresas.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.empresas.model.RamoAtividade;

public class RamoAtividadeConverter implements Converter {

	private List<RamoAtividade> ListaRamoAtividades;
	
	public RamoAtividadeConverter(List<RamoAtividade> ListaRamoAtividades) {
		this.ListaRamoAtividades = ListaRamoAtividades;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
 
		Long id = Long.valueOf(value);
		
		for (RamoAtividade ramoAtividade : ListaRamoAtividades) {
			if (id.equals(ramoAtividade.getId())) {
				return ramoAtividade;
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		
		RamoAtividade ramoAtividade = (RamoAtividade) value;
		
		return ramoAtividade.getId().toString();

	}

}
