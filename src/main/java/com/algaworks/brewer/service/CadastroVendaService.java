package com.algaworks.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.Vendas;
import com.algaworks.brewer.service.exception.AlteracaoVendaProibidaException;

@Service
public class CadastroVendaService {

	@Autowired
	private Vendas vendas;
	
	@Transactional
	public Venda salvar(Venda venda){
		
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}else{
			Venda vendaExistente = vendas.findOne(venda.getCodigo());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
			
			if (vendaExistente.isSalvarProibido()) {
				venda.setStatus(vendaExistente.getStatus());
				throw new AlteracaoVendaProibidaException("Usuário tentando salvar uma venda proibida");
			}			
		}
		
		
		if(venda.getDataEntrega() != null){
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega()
					,venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
		}
		
		return vendas.saveAndFlush(venda);
	}

	@Transactional
	public void emitir(Venda venda) {	
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
	}

	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')") //@EnableGlobalMethodSecurity acesso pelo SecurityConfig.java
	@Transactional
	public void cancelar(Venda venda) {		
		Venda vendaExistente = vendas.findOne(venda.getCodigo());
		
		vendaExistente.setStatus(StatusVenda.CANCELADA);
		
		vendas.save(vendaExistente);	
		
	}
	
}
