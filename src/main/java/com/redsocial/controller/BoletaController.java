package com.redsocial.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redsocial.entidades.Boleta;
import com.redsocial.entidades.Cliente;
import com.redsocial.entidades.Mensaje;
import com.redsocial.entidades.Producto;
import com.redsocial.entidades.ProductoHasBoleta;
import com.redsocial.entidades.ProductoHasBoletaPK;
import com.redsocial.entidades.Seleccion;
import com.redsocial.entidades.Usuario;
import com.redsocial.service.BoletaService;
import com.redsocial.service.ClienteService;
import com.redsocial.service.ProductoService;

@Controller
public class BoletaController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private BoletaService boletaService;
	
	//Se almacenan los productos seleccionados
	private List<Seleccion> seleccionados = new ArrayList<Seleccion>();
	
	
	@RequestMapping("/verBoleta")
	public String verBoleta() {
		return "boleta";
	}  

	@RequestMapping("/cargaCliente")
	@ResponseBody
	public List<Cliente> listaCliente(String filtro){
		//Los parametros de la paginacion
		int page = 0;
		int size = 5;
		Pageable paginacion = PageRequest.of(page, size);
		List<Cliente> lstCliente = clienteService.listaCliente("%"+filtro+"%", paginacion);
		return lstCliente;
	}
	
	@RequestMapping("/cargaProducto")
	@ResponseBody
	public List<Producto> listaProducto(String filtro){
		//Los parametros de la paginacion
		int page = 0;
		int size = 5;
		Pageable paginacion = PageRequest.of(page, size);
		List<Producto> lstProducto = productoService.listaproducto("%"+filtro+"%", paginacion);
		return lstProducto;
	}
	
	@RequestMapping("/agregarSeleccion")
	@ResponseBody
	public List<Seleccion> agregar(Seleccion obj){
		seleccionados.add(obj);
		return seleccionados;
	}
	
	@RequestMapping("/listaSeleccion")
	@ResponseBody
	public List<Seleccion> lista(){
		return seleccionados;
	}
	
	@RequestMapping("/eliminaSeleccion")
	@ResponseBody
	public List<Seleccion> eliminar(int idProducto){
		//seleccionados.removeIf( obj -> obj.getIdProducto() == idProducto);
		for (Seleccion x : seleccionados) {
			if (x.getIdProducto() == idProducto) {
				seleccionados.remove(x);
				break;
			}
		}
		return seleccionados;
	}
	
	@RequestMapping("/registraBoleta")
	@ResponseBody
	public Mensaje registra(Cliente objCliente) {
		Mensaje m = new Mensaje();
		
		Usuario objusuario = new Usuario();
		objusuario.setIdUsuario(1);
		
		List<ProductoHasBoleta> detalles = new ArrayList<ProductoHasBoleta>();
		for (Seleccion s : seleccionados) {
			
			ProductoHasBoleta phb = new ProductoHasBoleta();
			phb.setCantidad(s.getCantidad());
			phb.setPrecio(s.getPrecio());
			
			ProductoHasBoletaPK pk = new ProductoHasBoletaPK();
			pk.setIdProducto(s.getIdProducto());
			phb.setProductoHasBoletaPK(pk);
			
			detalles.add(phb);
			
		}
		
		Boleta objBoleta = new Boleta();
		objBoleta.setUsuario(objusuario);
		objBoleta.setCliente(objCliente);
		objBoleta.setDetallesBoleta(detalles);
		
		Boleta objBoletaSalida =  boletaService.insertaBoleta(objBoleta);		
		
		String salida = "";

		if (objBoleta != null) {
			salida = "Se generó la boleta con código N° : " + objBoletaSalida.getIdboleta() + "<br><br>";
			salida += "Cliente: " + objBoletaSalida.getCliente().getNombre() + "<br><br>";
			salida += "<table class=\"table\"><tr><td>Producto</td><td>Precio</td><td>Cantidad</td><td>Subtotal</td></tr>";
			double monto = 0;
			for (Seleccion x : seleccionados) {
				salida += "<tr><td>"  + x.getNombre() + "</td><td>" + x.getPrecio() + "</td><td>" + x.getCantidad()
						+ "</td><td>" + x.getTotalParcial() + "</td></tr>";
				monto += x.getCantidad() * x.getPrecio();
			}
			salida += "</table><br>";
			salida += "Monto a pagar : " + monto;

			seleccionados.clear();
		}
		
		m.setTexto(salida);
		
		return m;
	}
	
	
	
	
	
	
	
}
