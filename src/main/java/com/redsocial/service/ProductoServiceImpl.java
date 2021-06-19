package com.redsocial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.redsocial.entidades.Producto;
import com.redsocial.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository repository;

	@Override
	public List<Producto> listaproducto(String filtro, Pageable pageable) {
		return repository.listaproducto(filtro, pageable);
	}

}
