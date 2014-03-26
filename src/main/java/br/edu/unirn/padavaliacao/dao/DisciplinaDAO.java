package br.edu.unirn.padavaliacao.dao;

import br.edu.unirn.padavaliacao.model.Disciplina;

public class DisciplinaDAO extends GenericDAO<Disciplina> {

	@Override
	public Class<Disciplina> getClassType() {
		return Disciplina.class;
	}

}
