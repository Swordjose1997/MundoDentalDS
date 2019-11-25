package com.mundodental.control;

import com.mundodental.entidad.Pacientes;
import com.mundodental.conexion.Conexion;
import com.mundodental.conexion.ConexionPool;
import com.mundodental.entidad.Menu;
import com.mundodental.operaciones.Operaciones;
import com.mundodental.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SPacientes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        HttpSession s = request.getSession();
        List<Menu> per = (List<Menu>) s.getAttribute("Permisos");
        //List<Menu> MenuPrincipal = per.stream().filter(field -> field.getIdpadre() == 0).collect(Collectors.toList());
        //request.setAttribute("MenuPrincipal", MenuPrincipal);
        String op = request.getParameter("op");
        if (op != null) {
            List<Menu> PermisosAsignados = per.stream().filter(field -> field.getIdpadre() == Integer.parseInt(op)).collect(Collectors.toList());
            request.setAttribute("PermisosAsignados", PermisosAsignados);
        }
        if (accion == null) {
            if (request.getSession().getAttribute("resultado") != null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }
            cargarTabla(request, response);

            request.getRequestDispatcher("pacientes.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            cargarTabla(request, response);
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Pacientes p = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Pacientes());
                request.setAttribute("paciente", p);
                request.setAttribute("modi", 1);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("pacientes.jsp").forward(request, response);
        }else if (accion.equals("eliminar")) {

            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Pacientes p = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Pacientes());
                if (p.getExpediente() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                response.sendRedirect(request.getContextPath() + "/SPacientes");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                String expediente = request.getParameter("txtIdPaciente");
                String nombre = request.getParameter("txtNom");
                String ape = request.getParameter("txtApe");
                String fecha = request.getParameter("txtFecha");
                ///String tel1 = request.getParameter("txtTel1");
                String tel = request.getParameter("txtTel");
                String dir = request.getParameter("txtDir");
                String email = request.getParameter("txtEmail");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    if (expediente != null && !expediente.equals("")) {
                        Pacientes p = new Pacientes();
                    p.setExpediente(Integer.parseInt(expediente));
                    p.setNombres(nombre);
                    p.setApellidos(ape);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(fecha);
                    java.sql.Date d = new java.sql.Date(date.getTime());
                    p.setFechaNacimiento(d);
                    p.setDireccion(dir);
                    p.setTelefono(tel);
                    p.setEmail(email);
                        p = Operaciones.actualizar(p.getExpediente(), p);
                        if (p.getExpediente()!= 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }else{

                    Pacientes p = new Pacientes();
                    p.setNombres(nombre);
                    p.setApellidos(ape);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(fecha);
                    java.sql.Date d = new java.sql.Date(date.getTime());
                    p.setFechaNacimiento(d);
                    p.setDireccion(dir);
                    p.setTelefono(tel);
                    p.setEmail(email);
                    p = Operaciones.insertar(p);
                    if(p.getExpediente()!=0){
                         request.getSession().setAttribute("resultado", 1);
                    }
                    else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }
                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/SPacientes");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }

    private void cargarTabla(HttpServletRequest request, HttpServletResponse response) {
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            String sql = "";
            if (request.getParameter("txtBusqueda") != null) {
                sql = "select * from pacientes where nombres like ?";
            } else {
                sql = "select * from pacientes";
            }
            String[][] pacientes = null;
            if (request.getParameter("txtBusqueda") != null) {
                List<Object> params = new ArrayList<>();
                params.add("%" + request.getParameter("txtBusqueda").toString() + "%");
                pacientes = Operaciones.consultar(sql, params);
            } else {
                pacientes = Operaciones.consultar(sql, null);
            }
            //declaracion de cabeceras a usar en la tabla HTML
            String[] cabeceras = new String[]{"ID Paciente", "Nombre", "Apellido", "Fech. Nac.", "telefono", "direccion", "email"};//variable de tipo Tabla para generar la Tabla HTML
            Tabla tab = new Tabla(pacientes, //array quecontiene los datos
                    "50%", //ancho de la tabla px | % 
                    Tabla.STYLE.TABLE01, //estilo de la tabla
                    Tabla.ALIGN.LEFT, // alineacion de la tabla
                    cabeceras);
            //array con las cabeceras de la tabla
            //boton eliminar
            tab.setEliminable(true);//boton actualizar
            tab.setModificable(true); //url del proyecto
            tab.setPageContext(request.getContextPath());//pagina encargada de eliminar
            tab.setPaginaEliminable("/SPacientes?accion=eliminar");//pagina encargada de actualizacion
            tab.setPaginaModificable("/SPacientes?accion=modificar");//pagina encargada de seleccion para operaciones
            tab.setPaginaSeleccionable("/SPacientes?accion=modificar");//icono para modificar y eliminar
            tab.setIconoModificable("/iconos/edit.png");
            tab.setIconoEliminable("/iconos/delete.png"); //columnas seleccionables
            //tab.setColumnasSeleccionables(new int[]{1});//pie de tabla
            tab.setPie("Resultado de pacientes");
            //imprime la tabla enpantalla
            String tabla01 = tab.getTabla();
            request.setAttribute("tabla", tabla01);
            request.setAttribute("valor", request.getParameter("txtBusqueda"));
        } catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Pacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
