package br.edu.unirn.padavaliacao.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="logDatabase", schema="public")
public class LogDatabase {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="seq_log")
	@SequenceGenerator(name="seq_log", sequenceName="seq_log", initialValue=1, allocationSize=1)
	private Long idLogDatabase;
	private String tabela;
	private String acao;
	private Date data = new Date(System.currentTimeMillis());
	public Long getIdLogDatabase() {
		return idLogDatabase;
	}
	public void setIdLogDatabase(Long idLogDatabase) {
		this.idLogDatabase = idLogDatabase;
	}
	public String getTabela() {
		return tabela;
	}
	public void setTabela(String tabela) {
		this.tabela = tabela;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
}
