package br.edu.unirn.padavaliacao.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="serie", schema="public")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Serie {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_serie")
	@SequenceGenerator(name="seq_serie", sequenceName="seq_serie", initialValue=1, allocationSize=1)
	private Long idSerie;
	private String denominacao;
	@OneToMany(mappedBy="serie",fetch=FetchType.LAZY)
	private Collection<Turma> turmas;
	
	public Long getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(Long idSerie) {
		this.idSerie = idSerie;
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
