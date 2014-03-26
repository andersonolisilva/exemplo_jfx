/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unirn.avaliacaofx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import br.edu.unirn.utils.SegurancaUtils;

/**
 * FXML Controller class
 *
 * @author anderson
 */
public class FXMLLoginController implements Initializable {

    @FXML private TextField tfUsuario;
    
    @FXML private TextField tfSenha;
    
    @FXML private Button btAcessar;
    
    @FXML private Button btSair;
    
    @FXML private Label lbMensagem;
    
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfUsuario.setText("admin");
        tfSenha.setText("admin");
    }
    
    @FXML private void acessar() throws IOException{

        if (tfUsuario.getText().equals("admin")
                && SegurancaUtils.md5(tfSenha.getText()).equals("21232f297a57a5a743894a0e4a801fc3")) {

            Parent parent = FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml"));  
            Scene scene = new Scene(parent);  
            MainApp.principal = new Stage();
            MainApp.principal.setScene(scene); 
            MainApp.principal.setTitle("Sistema para controle acadêmico.");
            /**
             * maximizando a tela
             */
//            MainApp.principal.centerOnScreen();
//            MainApp.principal.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
//            MainApp.principal.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            /**
             * Fim maximizando
             */
            MainApp.principal.show(); 
            MainApp.login.close();
            
        }else {
           lbMensagem.setText("usuário ou senha INVÁLIDOS.");
        }
    }
    
    @FXML private void sair(){
        MainApp.login.close();
    }
}
