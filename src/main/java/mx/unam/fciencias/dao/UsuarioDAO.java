/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.dao;

import java.util.List;
import mx.unam.fciencias.model.dto.UsuarioDto;

/**
 *
 * @author guillermorojas
 */
public class UsuarioDAO extends AbstractDAO<UsuarioDto>{

    public UsuarioDAO() {
        super(UsuarioDto.class);
    }
    
    public List<UsuarioDto> selectAllUsuarios(){
        return em.createNamedQuery(UsuarioDto.SELECT_ALL).getResultList();
    }
    
    public UsuarioDto findByMail(String correo){
        List<UsuarioDto> usuario= em.createNamedQuery(UsuarioDto.FIND_BY_MAIL).setParameter(1,correo).getResultList();
    if(usuario.size()==0){
        return null;
    }
    else {
        return usuario.get(0);
    }
   }
}