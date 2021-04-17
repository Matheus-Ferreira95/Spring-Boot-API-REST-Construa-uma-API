package br.com.alura.forum.modelo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario implements UserDetails { // para o spring saber que essa é uma classe que representa um usuario no sistema

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;

	@ManyToMany(fetch = FetchType.EAGER) // para quando carregar o usuario, já vir junto a lista de perfis dele (pois precisaremos da lista de perfis dele)
	private List<Perfil> perfis = new ArrayList<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { /* para o spring security, além de termos uma classe de usuario, precisa ter
																	tambem uma classe que representa o perfil do usuario(relacionado as permissoes de acesso dele)
																	essa classe precisa ser tambem uma tabela no nosso banco de dados
																	*/
		return this.perfis;
	}

	@Override
	public String getPassword() { // o spring para saber a senha do usuario vai utilizar esse método
		return this.senha;
	}

	@Override
	public String getUsername() { // o spring para saber o login do usuario vai utilizar esse método, no nosso caso é o email do usuario
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() { // esses metodos que devolvem boolean, é para caso vc faça controle na sua aplicação da conta do usuario
											// do tipo, se a conta ta bloqueada, se tem uma data de expiração oou coisas do genero, nós devolveriamos os atributos
											// que corresponde a essas informações, no nosso caso não teremos controle mais detalhado, devolveremos true para todos
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
