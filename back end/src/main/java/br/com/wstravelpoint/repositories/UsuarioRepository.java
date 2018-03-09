package br.com.wstravelpoint.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wstravelpoint.models.Usuario;
import br.com.wstravelpoint.utils.repositories.GenericRepository;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario, Integer>{
	
	@Query("from Usuario u where u.email = :email and u.senha = :senha ")
	Usuario getUsuarioByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
}
