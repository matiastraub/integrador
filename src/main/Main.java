package main;

import java.awt.EventQueue;
import view.LoginView;

public class Main {

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			LoginView ve = new LoginView();
			ve.setVisible(true);
		});

	}
}
