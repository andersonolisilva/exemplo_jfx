package br.edu.unirn.padavaliacao.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="curso", schema="public")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_curso")
	@SequenceGenerator(name = "seq_curso", initialValue = 1, allocationSize = 1, sequenceName = "seq_curso")
	private Long idCurso;
	private String denominacao;
	
	@OneToMany(mappedBy="curso")
	private Collection<Turma> turmas;
		
	
	public Collection<Turma> getTurmas() {
		return turmas;
	}
	
	public void setTurmas(Collection<Turma> turmas) {
		this.turmas = turmas;
	}
	public Long getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}
	public String getDenominacao() {
		return denominacao;
	}
	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}
	
	public String toString(){
		return this.denominacao;
	}
	
	
}
