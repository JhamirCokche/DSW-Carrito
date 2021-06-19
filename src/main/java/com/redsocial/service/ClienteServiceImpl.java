package com.redsocial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.redsocial.entidades.Cliente;
import com.redsocial.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Override
	public List<Cliente> listaCliente(String filtro, Pageable pageable) {
		return repository.listaCliente(filtro, pageable);
	}

}
