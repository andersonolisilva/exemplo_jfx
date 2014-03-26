
import br.edu.unirn.padavaliacao.dao.AlunoDAO;
import br.edu.unirn.padavaliacao.model.Aluno;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anderson
 */
public class Test {

    public static void main(String[] args){
        Aluno aluno = new Aluno();
        AlunoDAO alunoDAO = new AlunoDAO();
        aluno.setNome("teste");
        alunoDAO.create(aluno);
                
    }
}
