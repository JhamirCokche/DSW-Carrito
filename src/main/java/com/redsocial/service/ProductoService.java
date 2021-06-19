package com.redsocial.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.redsocial.entidades.Producto;

public interface ProductoService {

	public List<Producto> listaproducto(String filtro, Pageable pegable);

}
