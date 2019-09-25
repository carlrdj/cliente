/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.DaoCliente;
import db.ConnectDB;
import dto.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author carlr
 */
public class DaoClienteImpl implements DaoCliente {

    private final ConnectDB db;
    private final StringBuilder sql;

    public DaoClienteImpl() {
        db = new ConnectDB();
        sql = new StringBuilder();
    }

    @Override
    public List<Cliente> obtenerClientes() {
        sql.delete(0, sql.length());
        sql.append("SELECT vc_nombre, vc_apellido, vc_dni, vc_direccion, ch_habilitar FROM t_cliente");
        try (
                // Almacenar conexión en el objeto "cn"
                Connection cn = db.getConnection();
                // Preparar el scrip a ejecutar "ps"
                PreparedStatement ps = cn.prepareCall(sql.toString());
                // Ejecutar el script y almacenar resultado en "rs"
                ResultSet rs = ps.executeQuery();) {
            List<Cliente> listarCliente = new LinkedList<>();

            
            while (rs.next()) {
                // Instancia la clase cliente
                Cliente myCliente = new Cliente();
                // Setear datos
                myCliente.setNombre(rs.getString("vc_nombre"));
                myCliente.setApellido(rs.getString("vc_apellido"));
                myCliente.setDni(rs.getString("vc_dni"));
                myCliente.setDireccion(rs.getString("vc_direccion"));
                myCliente.setHabilitar(rs.getString("ch_habilitar"));
                listarCliente.add(myCliente);
            }
            return listarCliente;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Cliente obtenerCliente(String dni) {
        sql.delete(0, sql.length());
        sql.append("SELECT vc_nombre, vc_apellido, vc_dni, vc_direccion, ch_habilitar"
                + " FROM t_cliente"
                + " WHERE vc_dni = ?");

        try (
                Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());
                ) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Cliente myCliente = new Cliente();
                    myCliente.setNombre(rs.getString("vc_nombre"));
                    myCliente.setApellido(rs.getString("vc_apellido"));
                    myCliente.setDni(rs.getString("vc_dni"));
                    myCliente.setDireccion(rs.getString("vc_direccion"));
                    myCliente.setHabilitar(rs.getString("ch_habilitar"));
                    return myCliente;
                } else {
                    return null;
                }

            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean insertarCliente(Cliente myCliente) {
        sql.delete(0, sql.length());
        sql.append("INSERT INTO t_cliente (vc_nombre, vc_apellido, vc_dni, vc_direccion, ch_habilitar) "
                + " VALUES (?, ?, ?, ?, 'Y')");
        try (
                Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());) 
        {
            ps.setString(3, myCliente.getDni());
            ps.setString(1, myCliente.getNombre());
            ps.setString(2, myCliente.getApellido());
            ps.setString(4, myCliente.getDireccion());
            int registrosInsertados = ps.executeUpdate();
            if (registrosInsertados == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean actualizarCliente(Cliente cliente) {
        String dir_cons = "Las Artes";
        sql.delete(0, sql.length());
        sql.append(""
                + "UPDATE t_cliente "
                + "SET vc_nombre = ?, vc_apellido = ?, vc_direccion = '" + dir_cons + "' "
                + "WHERE vc_dni = ?");
        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDni());
            int i = ps.executeUpdate();
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean habilitarCliente(String estado, String dni) {
        sql.delete(0, sql.length());
        sql.append("UPDATE t_cliente SET ch_habilitar = ? WHERE vc_dni = ?");
        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());) {
            ps.setString(1, estado);
            ps.setString(2, dni);
            int i = ps.executeUpdate();
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
