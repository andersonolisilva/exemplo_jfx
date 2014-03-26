package br.edu.unirn.avaliacaofx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLPrincipalController implements Initializable {
    
	@FXML 
	public ImageView imagem;
   
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	imagem.setImage(new Image("logo.jpeg"));
    }    
    
    public void openAluno() throws IOException{
        openScene(MainApp.aluno, "Cadastro de aluno.", "FXMLAlunoLista.fxml");
    }
    
    public void openCurso() throws IOException{
        openScene(MainApp.curso, "Cadastro de curso.", "FXMLCursoLista.fxml");
    }

    public void openDisciplina() throws IOException{
        openScene(MainApp.disciplina, "Cadastro de disciplina.", "FXMLDisciplinaLista.fxml");
    }
    
    public void openSerie() throws IOException{
        openScene(MainApp.serie , "Cadastro de série.", "FXMLSerieLista.fxml");
    }
    
    public void openTurma() throws IOException{
        openScene(MainApp.turma,"Cadastro de turma.","FXMLTurmaLista.fxml");
    }
    
    public void openScene( Stage stage, String titulo, String resource) throws IOException{
    	Parent parent = FXMLLoader.load(getClass().getResource(resource));  
        Scene scene = new Scene(parent);  
        stage = new Stage();
        stage.setScene(scene); 
        stage.setTitle(titulo);
        /**
         * Deixando scena
         * */
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    public void sair(){
        MainApp.principal.close();
    }
    
}
