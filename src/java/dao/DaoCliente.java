/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Cliente;
import java.util.List;

/**
 *
 * @author carlr
 */
public interface DaoCliente {
    
    List<Cliente> obtenerClientes();
    Cliente obtenerCliente(String dni);
    boolean insertarCliente(Cliente myRecibidocliente);
    boolean actualizarCliente(Cliente cliente);
    boolean habilitarCliente(String estado, String dni);
    
}
