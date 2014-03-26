/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unirn.avaliacaofx;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.JOptionPane;

import br.edu.unirn.padavaliacao.dao.AlunoDAO;
import br.edu.unirn.padavaliacao.dao.MatriculaDAO;
import br.edu.unirn.padavaliacao.dao.TurmaDAO;
import br.edu.unirn.padavaliacao.model.Aluno;
import br.edu.unirn.padavaliacao.model.Matricula;
import br.edu.unirn.padavaliacao.model.Turma;

/**
 * FXML Controller class
 * 
 * @author anderson
 */
public class FXMLAlunoListaController implements Initializable, ModelInterface {

	@FXML
	private Label lbAlunoSelecionado;
	@FXML
	private TextField tfIdentificacao;
	@FXML
	private TextField tfNome;
	@FXML
	private ComboBox<String> cbPesquisar;
	@FXML
	private TextField tfPesquisar;
	@FXML
	private CheckBox chkPesquisar;
	@FXML
	private ComboBox<Turma> cbTurma;

	/**
	 * Trabalhando com tabela Tabela de aluno
	 */
	private ObservableList<Aluno> alunos;
	private AlunoDAO alunoDAO;
	@FXML
	private TableView<Aluno> table;
	@FXML
	private TableColumn<Aluno, Long> colIdentificacao;
	@FXML
	private TableColumn<Aluno, String> colNome;

	/**
	 * Tabela de Matriculas
	 */

	MatriculaDAO matriculaDAO;
	@FXML
	private TableView<Matricula> tableMatricula;
	@FXML
	private TableColumn<Matricula, String> colMatriculaTurma;

	/**
	 * @registroSelecionado será utilizado quando selecionar um registro na
	 *                      tabela
	 */
	Aluno registroSelecionado;
	Matricula matriculaSelecionada;

	/**
	 * Initializes the controller class.
	 */
	public void initialize(URL url, ResourceBundle rb) {

		lbAlunoSelecionado.setText("Selecione um aluno da lista");
		tfIdentificacao.setEditable(false);
		cbPesquisar.getSelectionModel().selectFirst();

		alunoDAO = new AlunoDAO();
		alunos = FXCollections.observableList((List<Aluno>) alunoDAO.findAll());
		colIdentificacao
				.setCellValueFactory(new PropertyValueFactory<Aluno, Long>(
						"idAluno"));
		colNome.setCellValueFactory(new PropertyValueFactory<Aluno, String>(
				"nome"));
		table.setItems(alunos);

		/**
		 * Matricula
		 */
		preencherComboBoxTurma();
		matriculaDAO = new MatriculaDAO();
		colMatriculaTurma
				.setCellValueFactory(new PropertyValueFactory<Matricula, String>(
						"turma"));
	}

	public void gravar() {
		try {
			Aluno aluno = new Aluno();
			/**
			 * Se o aluno selecionado estiver instanciado, então direciono para
			 * o objeto aluno para concluir a tarefa.
			 */
			if (!(registroSelecionado == null)) {
				aluno = registroSelecionado;
			}
			AlunoDAO alunoDAO = new AlunoDAO();
			aluno.setNome(tfNome.getText());
			/**
			 * Se tiver
			 */
			if (!(registroSelecionado == null)) {
				alunoDAO.update(aluno);
			} else {
				alunoDAO.create(aluno);
			}
			pesquisar();
			JOptionPane
					.showMessageDialog(null, "Registro gravado com sucesso.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao gravar os dados: " + e.getMessage());
		}
	}

	public void excluir() {
		try {
			if (registroSelecionado == null) {
				JOptionPane
						.showMessageDialog(null,
								"Favor selecionar um registro da tabela antes de excluir.");
			} else {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja excluir o registro?",
						"Confirmação", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					alunoDAO = new AlunoDAO();
					alunoDAO.delete(registroSelecionado);
					tfIdentificacao.clear();
					tfNome.clear();
					lbAlunoSelecionado.setText("Selecione um aluno.");
					tableMatricula.getItems().clear();
					registroSelecionado = null;
					pesquisar();
					JOptionPane.showMessageDialog(null,
							"Registro excluído com sucesso.");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir o registro: "
					+ e.getMessage());
		}
	}

	public void pesquisar() {
		try {
			alunoDAO = new AlunoDAO();
			List<Aluno> listaAluno = alunoDAO.findAllLike("nome",
					tfPesquisar.getText());

			alunos = FXCollections.observableList(listaAluno);
			table.setItems(alunos);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao pesquisar: " + e.getMessage());
		}
	}

	public void pesquisarInstantanea() {
		if (chkPesquisar.isSelected()) {
			pesquisar();
		}
	}

	public void selecionarRegistro() {
		if (!(table.getSelectionModel().getSelectedItem() == null)) {
			registroSelecionado = (Aluno) table.getSelectionModel()
					.getSelectedItem();
			if (registroSelecionado.getIdAluno() > 0) {
				tfIdentificacao.setText(registroSelecionado.getIdAluno()
						.toString());
				tfNome.setText(registroSelecionado.getNome());
				lbAlunoSelecionado.setText(registroSelecionado.getNome());
				atualizarListaMatricula();

			} else {
				tfIdentificacao.clear();
				tfNome.clear();
				tableMatricula.getItems().clear();
			}
		}
	}

	public void novo() {
		tfIdentificacao.clear();
		tfNome.clear();
		registroSelecionado = null;
	}

	public void preencherComboBoxTurma() {
		cbTurma.getItems().clear();
		TurmaDAO turmaDAO = new TurmaDAO();
		cbTurma.getItems().addAll(turmaDAO.findAll());
		cbTurma.getSelectionModel().selectFirst();
	}

	public void atualizarListaMatricula() {
		/**
		 * Preechendo dados de acordo com o aluno selecionado.
		 */
		if (!(registroSelecionado == null)
				&& (!(registroSelecionado.getMatriculas() == null))) {
			ObservableList<Matricula> matriculas = FXCollections
					.observableList((List<Matricula>) registroSelecionado
							.getMatriculas());
			tableMatricula.setItems(matriculas);
		}
	}

	public void selecionarMatricula() {
		if (tableMatricula.getSelectionModel().getSelectedItem() == null) {
			matriculaSelecionada = null;
		} else {
			matriculaSelecionada = (Matricula) tableMatricula
					.getSelectionModel().getSelectedItem();
		}
	}

	public void gravarMatricula() {
		boolean temMatricula = false;
		if (registroSelecionado == null) {
			JOptionPane
					.showMessageDialog(null,
							"É necessário selecionar um registro de aluno para confirmar a matrícula.");
		} else {
			Matricula matricula = new Matricula();
			matricula.setAluno(registroSelecionado);
			matricula.setTurma(cbTurma.getSelectionModel().getSelectedItem());

			/**
			 * Testa se existe matrícula para o aluno para posteriormente evitar
			 * o cadastro duplicado em uma turma.
			 */
			if (!(registroSelecionado.getMatriculas() == null)) {
				for (Matricula m : registroSelecionado.getMatriculas()) {
					temMatricula = m
							.getTurma()
							.toString()
							.equals(cbTurma.getSelectionModel()
									.getSelectedItem().toString());
					if (temMatricula) {
						JOptionPane.showMessageDialog(null,
								"Este aluno já está matriculado nesta turma.");
						break;
					}
				}
			}
			if (!temMatricula) {
				if (registroSelecionado.getMatriculas() == null) {
					Collection<Matricula> novaMatricula = new ArrayList<Matricula>();
					novaMatricula.add(matricula);
					registroSelecionado.setMatriculas(novaMatricula);
				} else {
					registroSelecionado.getMatriculas().add(matricula);
				}
				matriculaDAO = new MatriculaDAO();
				matriculaDAO.create(matricula);

				atualizarListaMatricula();

				JOptionPane.showMessageDialog(null,
						"Matrícula confirmada com sucesso.");
			}
		}
	}

	public void excluirMatricula() {
		if (matriculaSelecionada == null) {
			JOptionPane
					.showMessageDialog(
							null,
							"É necessário selecionar um registro de matrícula para proceder com a exclusão.");
		} else {
			registroSelecionado.getMatriculas().remove(matriculaSelecionada);
			matriculaDAO = new MatriculaDAO();
			matriculaDAO.delete(matriculaSelecionada);

			atualizarListaMatricula();

			JOptionPane.showMessageDialog(null, "Matrícula excluída.");
		}
	}

}
