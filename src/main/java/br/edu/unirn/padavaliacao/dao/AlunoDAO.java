package br.edu.unirn.padavaliacao.dao;

import br.edu.unirn.padavaliacao.model.Aluno;

public class AlunoDAO extends GenericDAO<Aluno>{

	@Override
	public Class<Aluno> getClassType() {
		return Aluno.class;
	}

}
