package com.mundodental.control;

import com.mundodental.control.SCitas;
import com.mundodental.entidad.Productos;
import com.mundodental.conexion.Conexion;
import com.mundodental.conexion.ConexionPool;
import com.mundodental.entidad.Categorias;
import com.mundodental.entidad.Empleados;
import com.mundodental.entidad.Membresias;
import com.mundodental.entidad.Menu;
import com.mundodental.entidad.Pacientes;
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

public class SMembresias extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        HttpSession s = request.getSession();
        List<Menu> per = (List<Menu>) s.getAttribute("Permisos");
        //List<Menu> MenuPrincipal = per.stream().filter(field -> field.getIdpadre() == 0).collect(Collectors.toList());
        //request.setAttribute("MenuPrincipal", MenuPrincipal);

        List<Pacientes> pa;

        try {
            pa = getPacientes();
            request.setAttribute("pacientes", pa);
            
        } catch (SQLException ex) {
            Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex);
        }

        String op = request.getParameter("op");

        if (op != null) {
            List<Menu> PermisosAsignados = per.stream().filter(field -> field.getIdpadre() == Integer.parseInt(op)).collect(Collectors.toList());
            request.setAttribute("PermisosAsignados", PermisosAsignados);

        }
        if (accion == null) {
            if (request.getSession().getAttribute("resultado") != null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");

                try {
                    /* List<Categorias> cate = Operaciones.getTodos(new Categorias());
                request.setAttribute("categorias", cate);*/
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            cargarTabla(request, response);

            request.getRequestDispatcher("mostrarMembresias.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            /*List<Categorias> ca;
            try{
                ca=getCategorias();
                request.setAttribute("cat", ca);
            }catch(SQLException ex){
                Logger.getLogger(SProductos.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            cargarTabla(request, response);
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                /*                List<Categorias> cate = Operaciones.getTodos(new Categorias());
                request.setAttribute("categorias", cate);*/
                Membresias m = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Membresias());
                request.setAttribute("membresias", m);
                request.setAttribute("modi", 1);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("mostrarMembresias.jsp").forward(request, response);
        } else if (accion.equals("eliminar")) {

            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Membresias m = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Membresias());
                if (m.getIdMembresia() != 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/SMembresias");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                ////////////////////////////////////////ARREGLAR
                String idMembresia = request.getParameter("txtIdMembresia");
                String fechaRegistro = request.getParameter("txtFechaRegistro");
                String fechaVencimiento = request.getParameter("txtFechaVencimiento");
                String monto = request.getParameter("txtMonto");
                String expediente = request.getParameter("txtExpediente");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    if (idMembresia != null && !idMembresia.equals("")) {
                        Membresias m = new Membresias();
                        m.setIdMembresia(Integer.parseInt(idMembresia));
                         SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = sdf1.parse(fechaRegistro);
                            java.sql.Date dRegistro = new java.sql.Date(date1.getTime());
                            m.setFechaRegistro(dRegistro);
                            
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                            Date date2 = sdf2.parse(fechaVencimiento);
                            java.sql.Date dVencimiento = new java.sql.Date(date2.getTime());
                            m.setFechaVencimiento(dVencimiento);
                            
                      
                         m.setMonto(Double.parseDouble(monto));
                         m.setExpediente(Integer.parseInt(expediente));
                         m = Operaciones.actualizar(m.getIdMembresia(), m);
                        if (m.getIdMembresia()!= 0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }else{
                  
                            
                    Membresias m = new Membresias();
                       
                       
                         SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = sdf1.parse(fechaRegistro);
                            java.sql.Date dRegistro = new java.sql.Date(date1.getTime());
                            m.setFechaRegistro(dRegistro);
                            
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                            Date date2 = sdf2.parse(fechaVencimiento);
                            java.sql.Date dVencimiento = new java.sql.Date(date2.getTime());
                            m.setFechaVencimiento(dVencimiento);
                            
                      
                            
                         m.setMonto(Double.parseDouble(monto));
                         m.setExpediente(Integer.parseInt(expediente));
                         m = Operaciones.insertar(m);
                    if(m.getIdMembresia()!=0){
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
                        Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/SMembresias");
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
            if (request.getParameter("txtBusquedaMembresia") != null) {
                sql = "select * from Membresias where fechaRegistro like ?";
            } else {
                sql = "select * from Membresias;";
            }
            String[][] mem = null;
            if (request.getParameter("txtBusquedaMembresia") != null) {
                List<Object> params = new ArrayList<>();
                params.add("%" + request.getParameter("txtBusquedaMembresia").toString() + "%");
                mem = Operaciones.consultar(sql, params);
            } else {
                mem = Operaciones.consultar(sql, null);
            }
            //declaracion de cabeceras a usar en la tabla HTML
            String[] cabeceras = new String[]{"ID Membresia", "Fecha Registro", "Fecha Vencimiento", "monto", "expediente"};//variable de tipo Tabla para generar la Tabla HTML
            Tabla tab = new Tabla(mem, //array quecontiene los datos
                    "50%", //ancho de la tabla px | % 
                    Tabla.STYLE.TABLE01, //estilo de la tabla
                    Tabla.ALIGN.LEFT, // alineacion de la tabla
                    cabeceras);
            //array con las cabeceras de la tabla
            //boton eliminar
            tab.setEliminable(true);//boton actualizar
            tab.setModificable(true); //url del proyecto
            tab.setPageContext(request.getContextPath());//pagina encargada de eliminar
            tab.setPaginaEliminable("/SMembresias?accion=eliminar");//pagina encargada de actualizacion
            tab.setPaginaModificable("/SMembresias?accion=modificar");//pagina encargada de seleccion para operaciones
            tab.setPaginaSeleccionable("/SMembresias?accion=modificar");//icono para modificar y eliminar
            tab.setIconoModificable("/iconos/edit.png");
            tab.setIconoEliminable("/iconos/delete.png"); //columnas seleccionables
            //tab.setColumnasSeleccionables(new int[]{1});//pie de tabla
            tab.setPie("Resultado de membresias");
            //imprime la tabla enpantalla
            String tabla01 = tab.getTabla();
            request.setAttribute("tabla", tabla01);
            request.setAttribute("valor", request.getParameter("txtBusquedaMembresia"));
        } catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Pacientes> getPacientes() throws SQLException {
        List<Pacientes> pa = new ArrayList();
        try {
            ConexionPool con = new ConexionPool();
            con.conectar();

            Operaciones.abrirConexion(con);
            pa = Operaciones.getTodos(new Pacientes());
        } catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Membresias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pa;
    }

   
}
