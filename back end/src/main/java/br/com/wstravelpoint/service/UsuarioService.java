package br.com.wstravelpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wstravelpoint.models.Usuario;
import br.com.wstravelpoint.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public void save(Usuario usuario){
		 this.usuarioRepository.save(usuario);
	}
	
	public Usuario getUsuarioByEmailAndSenha(Usuario usuario){
		return this.usuarioRepository.getUsuarioByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
	}

	public void delete(Integer id) {
		this.usuarioRepository.delete(id);
		
	}

	public Usuario findById(Integer id) {
		return this.usuarioRepository.findOne(id);
	}
	
}
