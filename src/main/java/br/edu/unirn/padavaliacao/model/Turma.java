package br.edu.unirn.padavaliacao.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "turma", schema = "public")
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_turma")
	@SequenceGenerator(name = "seq_turma", initialValue = 1, allocationSize = 1, sequenceName = "seq_turma")
	private Long idTurma;

	private String denominacao;

	@ManyToOne
	@JoinColumn(name = "idCurso")
	private Curso curso;

	@OneToMany(mappedBy = "turma")
	private Collection<Matricula> matriculas;

	@ManyToOne
	@JoinColumn(name="idSerie")
	private Serie serie;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TurmaDisciplina", joinColumns = @JoinColumn(name = "idTurma"), inverseJoinColumns = @JoinColumn(name = "idDisciplina"))
	private Collection<Disciplina> disciplinas;

	public Collection<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(Collection<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public Collection<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Collection<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Long getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(Long idTurma) {
		this.idTurma = idTurma;
	}

	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String toString(){
		return this.denominacao+" - Curso: "+this.getCurso().getDenominacao();
	}
}
