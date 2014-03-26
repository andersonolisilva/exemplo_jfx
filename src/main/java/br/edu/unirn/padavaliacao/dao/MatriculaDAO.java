package br.edu.unirn.padavaliacao.dao;

import br.edu.unirn.padavaliacao.model.Matricula;

public class MatriculaDAO extends GenericDAO<Matricula> {

	@Override
	public Class<Matricula> getClassType() {
		return Matricula.class;
	}

}
