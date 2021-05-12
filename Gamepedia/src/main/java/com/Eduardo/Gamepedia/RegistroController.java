package com.Eduardo.Gamepedia;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import com.Eduardo.Gamepedia.Model.Categoria.Categoria;
import com.Eduardo.Gamepedia.Model.Categoria.CategoriaDAO;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroController {

	@FXML
	private TextField NombreCategoria;

	@FXML
	private Button boton;

	public static CategoriaDAO categoria;

	@FXML
	private void CrearCategoria() {

		if (categoria.getId() >= 0) {
			String nombre = NombreCategoria.getText();
			categoria.setNombre(nombre);
			categoria.save();

		} else {
			CategoriaDAO c = new CategoriaDAO();
			String nombre = NombreCategoria.getText();
			c.setNombre(nombre);
			c.save();
		}

		AppController.todas = CategoriaDAO.GetAllCategoria();
		App.closeScene((Stage) boton.getScene().getWindow());
		try {
			App.setRoot("Inicio");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static CategoriaDAO getCategoria() {
		return categoria;
	}

	public static void setCategoria(CategoriaDAO categoria) {
		RegistroController.categoria = categoria;
	}

}
