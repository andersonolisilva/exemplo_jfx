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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="disciplina", schema="public")
public class Disciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_disciplina")
	@SequenceGenerator(name = "seq_disciplina", initialValue = 1, allocationSize = 1, sequenceName = "seq_disciplina")
	private Long idDisciplina;
	private String denominacao;
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="TurmaDisciplina",
	           joinColumns=@JoinColumn(name="idDisciplina"),
	           inverseJoinColumns=@JoinColumn(name="idTurma"))
	private Collection<Turma> turmas;
	public Long getIdDisciplina() {
		return idDisciplina;
	}
	public void setIdDisciplina(Long idDisciplina) {
		this.idDisciplina = idDisciplina;
	}
	public String getDenominacao() {
		return denominacao;
	}
	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}
	public Collection<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(Collection<Turma> turmas) {
		this.turmas = turmas;
	}
	
	

}
