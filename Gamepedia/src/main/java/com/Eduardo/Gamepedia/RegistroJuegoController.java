package com.Eduardo.Gamepedia;

import java.util.List;

import com.Eduardo.Gamepedia.Model.Categoria.Categoria;
import com.Eduardo.Gamepedia.Model.Categoria.CategoriaDAO;
import com.Eduardo.Gamepedia.Model.Juego.Juego;
import com.Eduardo.Gamepedia.Model.Juego.JuegoDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ChoiceBoxSkin;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RegistroJuegoController {
	ObservableList<Categoria> options = FXCollections.observableArrayList(CategoriaDAO.GetAllCategoria());

	@FXML
	private TextField ImagenJuego;

	@FXML
	private Button Agregar;

	@FXML
	private TextField NombreJuego;

	@FXML
	private TextField DescripcionCategoria;
	@FXML
	private ChoiceBox<String> c;

	public static JuegoDAO juego;

	List<Categoria> lista = CategoriaDAO.GetAllCategoria();

	@FXML
	private void initialize() {
		for (Categoria e : lista) {
			c.getItems().add(e.getNombre());
		}

	}

	@FXML
	private void CrearJuego() {

		if (juego.getId() >= 0) {
			String Imagen = ImagenJuego.getText();
			String Nombre = NombreJuego.getText();
			String Descrip = DescripcionCategoria.getText();
			String nombre = c.getSelectionModel().getSelectedItem();
			int id = 0;
			for (Categoria e : lista) {
				if (e.getNombre().equals(nombre)) {
					id = e.getId();
				}
			}
			juego.setImagen(Imagen);
			juego.setNombre(Nombre);
			juego.setDescripcion(Descrip);
			juego.setId_categoria(id);
			juego.save();
		} else {

			String Imagen = ImagenJuego.getText();
			String Nombre = NombreJuego.getText();
			String Descrip = DescripcionCategoria.getText();
			String nombre = c.getSelectionModel().getSelectedItem();
			int id = 0;
			for (Categoria e : lista) {
				if (e.getNombre().equals(nombre)) {
					id = e.getId();
				}
			}
			JuegoDAO j = new JuegoDAO(Imagen, Nombre, Descrip, id);
			j.save();
			if (j.getId_categoria() == AppController.idCategoria) {
				AppController.Juegos.add(j);
			}
		}

		App.closeScene((Stage) Agregar.getScene().getWindow());

	}

	public static Juego getJuego() {
		return juego;
	}

	public static void setJuego(JuegoDAO juego) {
		RegistroJuegoController.juego = juego;
	}
	
	

}
