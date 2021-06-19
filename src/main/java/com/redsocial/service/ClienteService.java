package com.redsocial.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.redsocial.entidades.Cliente;

public interface ClienteService {

	public abstract List<Cliente> listaCliente(String filtro , Pageable pageable) ;
	
}
