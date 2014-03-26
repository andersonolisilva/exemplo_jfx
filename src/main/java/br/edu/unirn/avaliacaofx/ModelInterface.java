/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unirn.avaliacaofx;

/**
 *
 * @author anderson
 */
public interface ModelInterface {

    /**
     * Contrato para realizar o crud.
     */
	public void novo();
	
    public void gravar();
    
    public void excluir();
    
    public void pesquisar();
    
    public void selecionarRegistro();
    
    public void pesquisarInstantanea();
    
}
