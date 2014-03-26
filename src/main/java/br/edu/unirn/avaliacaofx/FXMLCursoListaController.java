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

import br.edu.unirn.padavaliacao.dao.CursoDAO;
import br.edu.unirn.padavaliacao.model.Curso;

/**
 * FXML Controller class
 * 
 * @author anderson
 */
public class FXMLCursoListaController implements Initializable, ModelInterface {

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
	private ObservableList<Curso> cursos;
	private CursoDAO cursoDAO;
	@FXML
	private TableView<Curso> table;
	@FXML
	private TableColumn<Curso, Long> colIdentificacao;
	@FXML
	private TableColumn<Curso, String> colNome;

	/**
	 * @alunoSelecionado será utilizado quando selecionar um registro na tabela
	 */
	Curso cursoSelecionado;

	/**
	 * Initializes the controller class.
	 */
	public void initialize(URL url, ResourceBundle rb) {

		tfIdentificacao.setEditable(false);
		cbPesquisar.getSelectionModel().selectFirst();

		cursoDAO = new CursoDAO();
		cursos = FXCollections.observableList((List<Curso>) cursoDAO.findAll());

		colIdentificacao
				.setCellValueFactory(new PropertyValueFactory<Curso, Long>(
						"idCurso"));
		colNome.setCellValueFactory(new PropertyValueFactory<Curso, String>(
				"denominacao"));

		table.setItems(cursos);
	}

	public void gravar() {
		try {
			Curso curso = new Curso();
			/**
			 * Se o curso selecionado estiver instanciado, então direciono para
			 * o objeto curso para concluir a tarefa.
			 */
			if (!(cursoSelecionado == null)) {
				curso = cursoSelecionado;
			}
			CursoDAO cursoDAO = new CursoDAO();
			curso.setDenominacao(tfNome.getText());
			/**
			 * Se tiver
			 */
			if (!(cursoSelecionado == null)) {
				cursoDAO.update(curso);
			} else {
				cursoDAO.create(curso);
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
			if (cursoSelecionado == null) {
				JOptionPane
						.showMessageDialog(null,
								"Favor selecionar um registro da tabela antes de excluir.");
			} else {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja excluir o registro?",
						"Confirmação", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					cursoDAO = new CursoDAO();
					cursoDAO.delete(cursoSelecionado);
					tfIdentificacao.clear();
					tfNome.clear();
					cursoSelecionado = null;
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
			cursoDAO = new CursoDAO();
			List<Curso> listaCurso = cursoDAO.findAllLike("denominacao",
					tfPesquisar.getText());
			cursos = FXCollections.observableList(listaCurso);
			table.setItems(cursos);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao pesquisar: " + e.getMessage());
		}
	}

	public void selecionarRegistro() {
		if (!(table.getSelectionModel().getSelectedItem() == null)) {
			cursoSelecionado = (Curso) table.getSelectionModel()
					.getSelectedItem();
			if (cursoSelecionado.getIdCurso() > 0) {
				tfIdentificacao.setText(cursoSelecionado.getIdCurso()
						.toString());
				tfNome.setText(cursoSelecionado.getDenominacao());
			} else {
				tfIdentificacao.clear();
				tfNome.clear();
			}
		}
	}

	public void novo() {
		tfIdentificacao.clear();
		tfNome.clear();
		cursoSelecionado = null;
	}

	public void pesquisarInstantanea() {
		if (chkPesquisar.isSelected()) {
			pesquisar();
		}

	}

}
