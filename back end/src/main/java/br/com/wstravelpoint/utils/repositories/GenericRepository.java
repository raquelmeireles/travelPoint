package br.com.wstravelpoint.utils.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface utilizada para marcar os repositorios, usada para simplificar os extends das interfaces necessarias
 * para criaÃ§Ã£o de um repositorio com Spring Data
 *
 * @param <Entity>
 * @param <Serializable>
 */
@NoRepositoryBean
public interface GenericRepository<Entity, Serializable extends java.io.Serializable> extends JpaRepository<Entity, Serializable>, JpaSpecificationExecutor<Entity> {

}
