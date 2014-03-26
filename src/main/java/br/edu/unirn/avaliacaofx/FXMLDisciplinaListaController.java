/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unirn.avaliacaofx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.JOptionPane;

import br.edu.unirn.padavaliacao.dao.DisciplinaDAO;
import br.edu.unirn.padavaliacao.model.Disciplina;

/**
 * FXML Controller class
 * 
 * @author anderson
 */
public class FXMLDisciplinaListaController implements Initializable,
		ModelInterface {

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

	/**
	 * Trabalhando com tabela
	 */
	private ObservableList<Disciplina> disciplinas;
	private DisciplinaDAO disciplinaDAO;
	@FXML
	private TableView<Disciplina> table;
	@FXML
	private TableColumn<Disciplina, Long> colIdentificacao;
	@FXML
	private TableColumn<Disciplina, String> colNome;

	/**
	 * @disciplinaSelecionada será utilizado quando selecionar um registro na
	 *                        tabela
	 */
	Disciplina disciplinaSelecionado;

	/**
	 * Initializes the controller class.
	 */
	public void initialize(URL url, ResourceBundle rb) {

		tfIdentificacao.setEditable(false);
		cbPesquisar.getSelectionModel().selectFirst();

		disciplinaDAO = new DisciplinaDAO();
		disciplinas = FXCollections
				.observableList((List<Disciplina>) disciplinaDAO.findAll());

		colIdentificacao
				.setCellValueFactory(new PropertyValueFactory<Disciplina, Long>(
						"idDisciplina"));
		colNome.setCellValueFactory(new PropertyValueFactory<Disciplina, String>(
				"denominacao"));

		table.setItems(disciplinas);
	}

	public void gravar() {
		try {
			Disciplina disciplina = new Disciplina();
			/**
			 * Se a disciplina selecionada estiver instanciado, então direciono
			 * para o objeto disciplina para concluir a tarefa.
			 */
			if (!(disciplinaSelecionado == null)) {
				disciplina = disciplinaSelecionado;
			}
			DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
			disciplina.setDenominacao(tfNome.getText());
			/**
			 * Se tiver
			 */
			if (!(disciplinaSelecionado == null)) {
				disciplinaDAO.update(disciplina);
			} else {
				disciplinaDAO.create(disciplina);
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
			if (disciplinaSelecionado == null) {
				JOptionPane
						.showMessageDialog(null,
								"Favor selecionar um registro da tabela antes de excluir.");
			} else {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja excluir o registro?",
						"Confirmação", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					disciplinaDAO = new DisciplinaDAO();
					disciplinaDAO.delete(disciplinaSelecionado);
					tfIdentificacao.clear();
					tfNome.clear();
					disciplinaSelecionado = null;
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
			disciplinaDAO = new DisciplinaDAO();
			List<Disciplina> listaDisciplina = disciplinaDAO.findAllLike(
					"denominacao", tfPesquisar.getText());
			disciplinas = FXCollections.observableList(listaDisciplina);
			table.setItems(disciplinas);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao pesquisar: " + e.getMessage());
		}
	}

	public void selecionarRegistro() {
		if (!(table.getSelectionModel().getSelectedItem() == null)) {
			disciplinaSelecionado = (Disciplina) table.getSelectionModel()
					.getSelectedItem();
			if (disciplinaSelecionado.getIdDisciplina() > 0) {
				tfIdentificacao.setText(disciplinaSelecionado.getIdDisciplina()
						.toString());
				tfNome.setText(disciplinaSelecionado.getDenominacao());
			} else {
				tfIdentificacao.clear();
				tfNome.clear();
			}
		}
	}

	public void novo() {
		tfIdentificacao.clear();
		tfNome.clear();
		disciplinaSelecionado = null;
	}

	public void pesquisarInstantanea() {
		if (chkPesquisar.isSelected()) {
			pesquisar();
		}

	}

}
