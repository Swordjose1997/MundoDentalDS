package com.mundodental.control;

import com.mundodental.control.SCitas;
import com.mundodental.conexion.Conexion;
import com.mundodental.conexion.ConexionPool;
import com.mundodental.entidad.Categorias;
import com.mundodental.entidad.Empleados;
import com.mundodental.entidad.Locales;
import com.mundodental.entidad.Menu;
import com.mundodental.entidad.Productos;
import com.mundodental.entidad.Usuario;
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

public class SDoctores extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        HttpSession s = request.getSession();
        List<Menu> per = (List<Menu>) s.getAttribute("Permisos");
        //List<Menu> MenuPrincipal = per.stream().filter(field -> field.getIdpadre() == 0).collect(Collectors.toList());
        //request.setAttribute("MenuPrincipal", MenuPrincipal);
        List<Empleados> em;
        List<Usuario> usua;
        List<Locales> l;
        List<Categorias> ca;
                
                try {
                    ca=getCategorias();
                    request.setAttribute("cat", ca);
                    
                    em=getEmpleados();
                    request.setAttribute("emple", em);
                    
                    usua=getUsuarios();
                    request.setAttribute("u", usua);
                    
                    l=getLocales();
                    request.setAttribute("lo", l);
                } catch (SQLException ex) {
                    Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
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
                
                try{
               /* List<Categorias> cate = Operaciones.getTodos(new Categorias());
                request.setAttribute("categorias", cate);*/
                } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }
            cargarTabla(request, response);

            request.getRequestDispatcher("doctores.jsp").forward(request, response);
        } else if (accion.equals("modificar")) {
            
            cargarTabla(request, response);
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
/*                List<Categorias> cate = Operaciones.getTodos(new Categorias());
                request.setAttribute("categorias", cate);*/
                Empleados emple = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Empleados());
                request.setAttribute("doctores", emple);
                request.setAttribute("modi", 1);
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("doctores.jsp").forward(request, response);
        }else if (accion.equals("eliminar")) {

            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Empleados emple = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Empleados());
                if (emple.getIdEmpleado()!= 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                response.sendRedirect(request.getContextPath() + "/SDoctores");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        switch (accion) {
            case "insertar_modificar": {
                ///String idProducto = request.getParameter("txtIdProducto");
                
                String nombre = request.getParameter("txtNombresEmpleados");
                String apellido = request.getParameter("txtApellidosEmpleados");
                String estado = request.getParameter("txtEstadoEmpleado");
                String idusuario = request.getParameter("txtIdUsuario");
                String idlocal = request.getParameter("txtIdLocal");
                String telefono = request.getParameter("txtTelefonoEmpleado");
                
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();

                    
                    
                    Empleados emple = new Empleados();
                    ///Empleados emple = new Empleados(Integer.parseInt(idempleado), nombre, apellido, estado, Integer.parseInt(idusuario), Integer.parseInt(idlocal), telefono);
                    
                    emple.setNombres(nombre);
                    emple.setApellidos(apellido);
                    emple.setEstado(estado);
                    emple.setIdUsuario(Integer.parseInt(idusuario));
                    emple.setIdLocal(Integer.parseInt(idlocal));
                    emple.setTelefono(telefono);
                    emple = Operaciones.insertar(emple);

                    Operaciones.commit();
                } catch (Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/SDoctores");
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
            if (request.getParameter("txtBusquedaDoctores") != null) {
                sql = "select idEmpleado, nombres, apellidos, estado, usuario, (select l.local from Locales l where em.idLocal=l.idLocal), telefono  from Empleados em where em.nombres like ?";
            } else {
                sql = "select idEmpleado, nombres, apellidos, estado, usuario, (select l.local from Locales l where em.idLocal=l.idLocal), telefono  from Empleados em;";
            }
            String[][] productos = null;
            if (request.getParameter("txtBusquedaDoctores") != null) {
                List<Object> params = new ArrayList<>();
                params.add("%" + request.getParameter("txtBusquedaDoctores").toString() + "%");
                productos = Operaciones.consultar(sql, params);
            } else {
                productos = Operaciones.consultar(sql, null);
            }
            //declaracion de cabeceras a usar en la tabla HTML
            String[] cabeceras = new String[]{"ID Empleado", "Nombres", "Apellidos", "Estado", "Usuario", "Local", "Telefono"};//variable de tipo Tabla para generar la Tabla HTML
            Tabla tab = new Tabla(productos, //array quecontiene los datos
                    "50%", //ancho de la tabla px | % 
                    Tabla.STYLE.TABLE01, //estilo de la tabla
                    Tabla.ALIGN.LEFT, // alineacion de la tabla
                    cabeceras);
            //array con las cabeceras de la tabla
            //boton eliminar
            tab.setEliminable(true);//boton actualizar
            tab.setModificable(true); //url del proyecto
            tab.setPageContext(request.getContextPath());//pagina encargada de eliminar
            tab.setPaginaEliminable("/SDoctores?accion=eliminar");//pagina encargada de actualizacion
            tab.setPaginaModificable("/SDoctores?accion=modificar");//pagina encargada de seleccion para operaciones
            tab.setPaginaSeleccionable("/SDoctores?accion=modificar");//icono para modificar y eliminar
            tab.setIconoModificable("/iconos/edit.png");
            tab.setIconoEliminable("/iconos/delete.png"); //columnas seleccionables
            //tab.setColumnasSeleccionables(new int[]{1});//pie de tabla
            tab.setPie("Resultado de empleados");
            //imprime la tabla enpantalla
            String tabla01 = tab.getTabla();
            request.setAttribute("tabla", tabla01);
            request.setAttribute("valor", request.getParameter("txtBusquedaProducto"));
        } catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private List<Productos> getProductos() throws SQLException{
        List<Productos> pro = new ArrayList();
        try{
            ConexionPool con = new ConexionPool();
            con.conectar();
          
            
            Operaciones.abrirConexion(con);
            pro = Operaciones.getTodos(new Productos());
        }catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pro;
    }
    private List<Empleados> getEmpleados() throws SQLException{
        List<Empleados> emple = new ArrayList();
        try{
            ConexionPool con = new ConexionPool();
            con.conectar();
          
            
            Operaciones.abrirConexion(con);
            emple = Operaciones.getTodos(new Empleados());
        }catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return emple;
    }
    private List<Usuario> getUsuarios() throws SQLException{
        List<Usuario> u = new ArrayList();
        try{
            ConexionPool con = new ConexionPool();
            con.conectar();
          
            
            Operaciones.abrirConexion(con);
            u = Operaciones.getTodos(new Usuario());
        }catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return u;
    }
    private List<Locales> getLocales() throws SQLException{
        List<Locales> l = new ArrayList();
        try{
            ConexionPool con = new ConexionPool();
            con.conectar();
          
            
            Operaciones.abrirConexion(con);
            l = Operaciones.getTodos(new Locales());
        }catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return l;
    }
    private List<Categorias> getCategorias() throws SQLException{
        List<Categorias> c = new ArrayList();
        try{
            ConexionPool con = new ConexionPool();
            con.conectar();
          
            
            Operaciones.abrirConexion(con);
            c = Operaciones.getTodos(new Categorias());
        }catch (Exception ex) {
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SProductos.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(SProductos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(SProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return c;
    }
}
