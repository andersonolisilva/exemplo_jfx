package br.edu.unirn.padavaliacao.dao;

import br.edu.unirn.padavaliacao.model.Turma;

public class TurmaDAO extends GenericDAO<Turma>{

	@Override
	public Class<Turma> getClassType() {
		return Turma.class;
	}

}
