package br.edu.unirn.padavaliacao.dao;

import br.edu.unirn.padavaliacao.model.Serie;

public class SerieDAO extends GenericDAO<Serie>{

	@Override
	public Class<Serie> getClassType() {
		return Serie.class;
	}
	

}
