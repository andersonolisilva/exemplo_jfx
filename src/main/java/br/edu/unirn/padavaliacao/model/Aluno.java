package br.edu.unirn.padavaliacao.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aluno", schema = "public")
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_aluno")
	@SequenceGenerator(name = "seq_aluno", initialValue = 1, allocationSize = 1, sequenceName = "seq_aluno")
	private Long idAluno;
	private String nome;
	
	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Matricula> matriculas;
	
	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(Collection<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

}
