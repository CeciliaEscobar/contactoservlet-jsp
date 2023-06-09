/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cecilia;

import com.cecilia.dao.ContactoDAO;
import com.cecilia.dao.ContactoDAOImpl;
import com.cecilia.model.Contacto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author escob
 */

@WebServlet(urlPatterns="/ContactoServlet")
public class ContactoServlet extends HttpServlet {
    
    private ContactoDAO contactoDao;
    
    public ContactoServlet(){
        
        super();
        contactoDao = new ContactoDAOImpl();
    }
    
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.procesarSolicitud(req, resp);
       
    }
    
   @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        this.procesarSolicitud(req, resp);
        
    }
    
    protected void procesarSolicitud(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        switch (request.getParameter("action")){
            case "list":
                this.list(request,response);
                break;
              case "create":
                this.create(request,response);
                break;
                 case "read":
                this.read(request,response);
                break;
                 case "update":
                this.update(request,response);
                break;
                 case "delete":
                this.delete(request,response);
                break;
                 case "showRegister":
                this.showRegister(request,response);
                break;
                 case "index":
                this.index(request,response);
                break;
                 default:
                  this.index(request,response);
                   break;
                
        }
        
       
    }
     private void index (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
     private void showRegister(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
          RequestDispatcher dispatcher = request.getRequestDispatcher("/view/create.jsp");
            dispatcher.forward(request, response);
     }
      private void create (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
          //recoger los datos desde la peticion
          String name = request.getParameter("nombre");
          String apellido = request.getParameter("apellido");
          String email = request.getParameter("email");
          String descrip = request.getParameter("descripcion");
          
          //crear el objeto que se envia al BD
          
          Contacto objContacto = new Contacto();
          objContacto.setNombre(name);
          objContacto.setApellido(apellido);
          objContacto.setEmail(email);
          objContacto.setDescripcion(descrip);
          
          contactoDao.insert(objContacto);
          
          //Redireccionar al "index"
          
          this.index(request, response);
     }
     private void list(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
          List<Contacto> listaContactos = this.contactoDao.findAll();
          request.setAttribute ("lista", listaContactos);
          
           RequestDispatcher dispatcher = request.getRequestDispatcher("/view/list.jsp");
            dispatcher.forward(request, response);
     }
     private void read (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
         //recoge el id del elemento a buscar
         Integer id = Integer.parseInt(request.getParameter("id"));
         
         Contacto datosObjContacto = new Contacto();
         
         datosObjContacto = this.contactoDao.findById(id);
         
         request.setAttribute("contacto", datosObjContacto);
         
          RequestDispatcher dispatcher = request.getRequestDispatcher("/view/read.jsp");
            dispatcher.forward(request, response);
         
     }
     
      
     private void update (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
         
         Integer id = Integer.parseInt(request.getParameter("id"));
         
         Contacto objContacto = new Contacto();
         
         objContacto = this.contactoDao.findById(id);
         
          String name = request.getParameter("nombre");
          String apellido = request.getParameter("apellido");
          String email = request.getParameter("email");
          String descrip = request.getParameter("descripcion");
         
         
          objContacto.setNombre(name);
          objContacto.setApellido(apellido);
          objContacto.setEmail(email);
          objContacto.setDescripcion(descrip);
         
         
        //actualizar los datos en la base de datos
        
        contactoDao.updateById(id, objContacto);
        
        this.list(request, response);
         
     }
      private void delete (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
         
         Integer id = Integer.parseInt(request.getParameter("id"));
         
         Contacto objContacto = new Contacto();
         
         objContacto = this.contactoDao.findById(id);
         
        if (!objContacto.getId().equals (0)){
            
            this.contactoDao.deleteById(id);
        }else{
            System.out.println ("No existe el elemento con este id");
        }
        
        this.list(request, response);
         
     }
}
