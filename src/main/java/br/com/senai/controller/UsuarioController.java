package br.com.senai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.senai.model.Usuario;
import br.com.senai.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/pagina_inicial")
	public String paginaInicial() {
		return "index.html";
	}
	
	@GetMapping("/usuario")
	public String listaUsuarios(Model model) {
		
		List<Usuario> usuarios = usuarioRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		return "usuarios";
	}
	
	@GetMapping("/cadastro")
	public String formularioCadastro(Usuario usuario) {
		return "cadastrar_usuario";
	}
	
	@PostMapping("/adicionarUsuario")
	public String adicionarUsuario(@Valid Usuario usuario, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "usuarios";
		}
		usuarioRepository.save(usuario);
		return "redirect:/usuario";
	}
	
	@GetMapping("/editar/{id}")
	public String atualizacaoCadastro(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Usuário não encontrado: " + id));
		model.addAttribute("usuario", usuario);
		return "atualizar_usuario";
	}
	
	@PostMapping("/atualizar/{id}")
	public String atualizaUsuario(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			usuario.setId(id);
			return "atualizar_usuario";
		}
		usuarioRepository.save(usuario);
		return "redirect:/usuario";
	}
	
	@GetMapping("/delete/{id}")
	public String excluirUsuario(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Usuário não encontrado: " + id));
			usuarioRepository.delete(usuario);
		return "redirect:/usuario";
	}

}
