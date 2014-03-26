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

import br.edu.unirn.padavaliacao.dao.SerieDAO;
import br.edu.unirn.padavaliacao.model.Serie;

/**
 * FXML Controller class
 * 
 * @author anderson
 */
public class FXMLSerieListaController implements Initializable, ModelInterface {

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
	private ObservableList<Serie> series;
	private SerieDAO serieDAO;
	@FXML
	private TableView<Serie> table;
	@FXML
	private TableColumn<Serie, Long> colIdentificacao;
	@FXML
	private TableColumn<Serie, String> colNome;

	/**
	 * @serieSelecionada será utilizado quando selecionar um registro na tabela
	 */
	Serie serieSelecionado;

	/**
	 * Initializes the controller class.
	 */
	public void initialize(URL url, ResourceBundle rb) {

		tfIdentificacao.setEditable(false);
		cbPesquisar.getSelectionModel().selectFirst();

		serieDAO = new SerieDAO();
		series = FXCollections.observableList((List<Serie>) serieDAO.findAll());

		colIdentificacao
				.setCellValueFactory(new PropertyValueFactory<Serie, Long>(
						"idSerie"));
		colNome.setCellValueFactory(new PropertyValueFactory<Serie, String>(
				"denominacao"));

		table.setItems(series);
	}

	public void gravar() {
		try {
			Serie serie = new Serie();
			/**
			 * Se a serie selecionada estiver instanciado, então direciono para
			 * o objeto serie para concluir a tarefa.
			 */
			if (!(serieSelecionado == null)) {
				serie = serieSelecionado;
			}
			SerieDAO serieDAO = new SerieDAO();
			serie.setDenominacao(tfNome.getText());
			/**
			 * Se tiver
			 */
			if (!(serieSelecionado == null)) {
				serieDAO.update(serie);
			} else {
				serieDAO.create(serie);
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
			if (serieSelecionado == null) {
				JOptionPane
						.showMessageDialog(null,
								"Favor selecionar um registro da tabela antes de excluir.");
			} else {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja excluir o registro?",
						"Confirmação", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					serieDAO = new SerieDAO();
					serieDAO.delete(serieSelecionado);
					tfIdentificacao.clear();
					tfNome.clear();
					serieSelecionado = null;
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
			serieDAO = new SerieDAO();
			List<Serie> listaSerie = serieDAO.findAllLike("denominacao",
					tfPesquisar.getText());
			series = FXCollections.observableList(listaSerie);
			table.setItems(series);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao pesquisar: " + e.getMessage());
		}
	}

	public void selecionarRegistro() {
		if (!(table.getSelectionModel().getSelectedItem() == null)) {
			serieSelecionado = (Serie) table.getSelectionModel()
					.getSelectedItem();
			if (serieSelecionado.getIdSerie() > 0) {
				tfIdentificacao.setText(serieSelecionado.getIdSerie()
						.toString());
				tfNome.setText(serieSelecionado.getDenominacao());
			} else {
				tfIdentificacao.clear();
				tfNome.clear();
			}
		}
	}

	public void novo() {
		tfIdentificacao.clear();
		tfNome.clear();
		serieSelecionado = null;
	}

	public void pesquisarInstantanea() {
		if (chkPesquisar.isSelected()) {
			pesquisar();
		}

	}

}
