package br.com.wstravelpoint.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.wstravelpoint.models.Usuario;
import br.com.wstravelpoint.service.UsuarioService;

@RestController
@RequestMapping(value="/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value={"/{id}"},method= RequestMethod.GET)
	public Usuario findUsuarioById(@PathVariable("id") Integer id){
		return this.usuarioService.findById(id);
	}
	
	@RequestMapping(value={"","/"},method= RequestMethod.GET)
	private Usuario getUsuario(Usuario usuario){
		return this.usuarioService.getUsuarioByEmailAndSenha(usuario);
	}
	
	@RequestMapping(value={"","/"},method= RequestMethod.POST)
	private String cadastrar(@RequestBody Usuario usuario){
		this.usuarioService.save(usuario);
		return "O Usuário "+usuario.getNome()+" foi cadastrada com succeso!";
	}
	
	@RequestMapping(value={"","/"},method= RequestMethod.PUT)
	private String atualizar(@RequestBody Usuario usuario){
		this.usuarioService.save(usuario);
		return "O Usuario "+usuario.getNome()+" foi atualizada com succeso!";
	}
	
	@RequestMapping(value={"/{id}"},method= RequestMethod.DELETE)
	private String deletar(@PathVariable("id") Integer id){
		this.usuarioService.delete(id);
		return "Registro apagado com sucesso!";
	}	
}
