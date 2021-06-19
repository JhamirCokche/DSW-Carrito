package com.redsocial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redsocial.entidades.Boleta;
import com.redsocial.entidades.ProductoHasBoleta;
import com.redsocial.repository.BoletaRepository;
import com.redsocial.repository.ProductoHasBoletaRepository;

@Service
public class BoletaServiceImpl implements BoletaService{

	@Autowired
	private BoletaRepository boletaRepository;
	
	@Autowired
	private ProductoHasBoletaRepository detalleRepository;
	
	@Override
	@Transactional
	public Boleta insertaBoleta(Boleta obj) {
		Boleta cabecera = boletaRepository.save(obj);
		for (ProductoHasBoleta d : cabecera.getDetallesBoleta()) {
			d.getProductoHasBoletaPK().setIdBoleta(cabecera.getIdboleta());
			detalleRepository.actualizaStock(d.getCantidad(), d.getProductoHasBoletaPK().getIdProducto());
			detalleRepository.save(d);
		}
		return cabecera;
	}

}
