/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unirn.avaliacaofx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javax.swing.JOptionPane;

import br.edu.unirn.padavaliacao.dao.CursoDAO;
import br.edu.unirn.padavaliacao.dao.TurmaDAO;
import br.edu.unirn.padavaliacao.model.Curso;
import br.edu.unirn.padavaliacao.model.Turma;

/**
 * FXML Controller class
 * 
 * @author anderson
 */
public class FXMLTurmaListaController implements Initializable, ModelInterface {

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
	private ComboBox<Curso> cboxCurso;

	/**
	 * Trabalhando com tabela
	 */
	private ObservableList<Turma> turmas;
	private TurmaDAO turmaDAO;
	@FXML
	private TableView<Turma> table;
	@FXML
	private TableColumn<Turma, Long> colIdentificacao;
	@FXML
	private TableColumn<Turma, String> colNome;
	@FXML
	private TableColumn<Turma, String> colCurso;

	/**
	 * @registroSelecionado será utilizado quando selecionar um registro na
	 *                      tabela
	 */
	Turma registroSelecionado;

	/**
	 * Initializes the controller class.
	 */
	public void initialize(URL url, ResourceBundle rb) {

		tfIdentificacao.setEditable(false);
		cbPesquisar.getSelectionModel().selectFirst();
		preencherComboBoxCurso();

		turmaDAO = new TurmaDAO();
		turmas = FXCollections.observableList((List<Turma>) turmaDAO.findAll());

		colIdentificacao
				.setCellValueFactory(new PropertyValueFactory<Turma, Long>(
						"idTurma"));
		colNome.setCellValueFactory(new PropertyValueFactory<Turma, String>(
				"denominacao"));
		
		colCurso.setCellValueFactory(new Callback<CellDataFeatures<Turma, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Turma, String> turma) {
                return new SimpleStringProperty(turma.getValue().getCurso().getDenominacao());
            }
		});
		
		table.setItems(turmas);

	}

	public void gravar() {
		try {
			Turma turma = new Turma();
			/**
			 * Se a turma selecionado estiver instanciado, então direciono para
			 * o objeto turma para concluir a tarefa.
			 */
			if (!(registroSelecionado == null)) {
				turma = registroSelecionado;
			}
			TurmaDAO turmaDAO = new TurmaDAO();
			turma.setDenominacao(tfNome.getText());
			turma.setCurso(cboxCurso.getSelectionModel().getSelectedItem());
			/**
			 * Se tiver
			 */
			if (!(registroSelecionado == null)) {
				turmaDAO.update(turma);
			} else {
				turmaDAO.create(turma);
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
					turmaDAO = new TurmaDAO();
					turmaDAO.delete(registroSelecionado);					
					tfIdentificacao.clear();
					tfNome.clear();
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
			turmaDAO = new TurmaDAO();
			List<Turma> listaTurma = turmaDAO.findAllLike("denominacao",
					tfPesquisar.getText());
			turmas = FXCollections.observableList(listaTurma);
			table.setItems(turmas);
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
			registroSelecionado = (Turma) table.getSelectionModel()
					.getSelectedItem();
			if (registroSelecionado.getIdTurma() > 0) {
				tfIdentificacao.setText(registroSelecionado.getIdTurma()
						.toString());
				tfNome.setText(registroSelecionado.getDenominacao());
				cboxCurso.getSelectionModel().select(registroSelecionado.getCurso());
			} else {
				tfIdentificacao.clear();
				tfNome.clear();
			}
		}
	}

	public void novo() {
		tfIdentificacao.clear();
		tfNome.clear();
		registroSelecionado = null;
	}

	public void preencherComboBoxCurso() {
		cboxCurso.getItems().clear();
		CursoDAO cursoDAO = new CursoDAO();
		cboxCurso.getItems().addAll(cursoDAO.findAll());
		cboxCurso.getSelectionModel().selectFirst();
	}
}
