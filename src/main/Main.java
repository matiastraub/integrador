package main;

import database.UsuarioDAO;
import java.util.ArrayList;
import model.Usuario;

public class Main {

	public static void main(String[] args) {
		// UsuarioModel.createTable();
		ArrayList<Usuario> usuarios = UsuarioDAO.getAllUsuarios();
		// Insertar usuarios
		// UsuarioDAO.insertUsuario(
		// new Usuario(3, "Alicia", "Cornejo", "625856987", "1998-01-01", "12345678",
		// "alicia@email.com"));

		Usuario usuario = UsuarioDAO.getUsuario(1);
		System.out.println("Usuario con ID 1: " + usuario);

		// Actualizar usuario
		// if (!usuarios.isEmpty()) {
		// int userId = 4;// usuarios.get(0).getId();
		// UsuarioDAO.updateUsuario(userId, "Alicia", "Maravilla", "929856989",
		// "1998-01-01", "12345678",
		// "aliciamaravilla@email.com");
		// }

		// Borrar usuario
		// if (!usuarios.isEmpty()) {
		// // int userIdToDelete = usuarios.get(3).getId();
		// int userIdToDelete = 3;
		// UsuarioDAO.deleteUsuario(userIdToDelete);
		// }

		// Obtener todos los usuarios
		System.out.println("Todos los usuarios:");
		// usuarios = UsuarioDAO.getAllUsuarios();
		usuarios.forEach(System.out::println);

	}
}
