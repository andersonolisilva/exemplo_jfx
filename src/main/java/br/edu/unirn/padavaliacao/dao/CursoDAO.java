package br.edu.unirn.padavaliacao.dao;

import br.edu.unirn.padavaliacao.model.Curso;

public class CursoDAO extends GenericDAO<Curso>{

	@Override
	public Class<Curso> getClassType() {
		return Curso.class;
	}
	

}
