package com.Eduardo.Gamepedia;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.Eduardo.Gamepedia.Model.Categoria.Categoria;
import com.Eduardo.Gamepedia.Model.Categoria.CategoriaDAO;
import com.Eduardo.Gamepedia.Model.Juego.Juego;
import com.Eduardo.Gamepedia.Model.Juego.JuegoDAO;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class InicioController {

	@FXML
	private Label idLabel;

	@FXML
	private Label nombreLabel;

	@FXML
	TableView<Categoria> tablaCategoria;

	@FXML
	private TableColumn<Categoria, String> idColumna;
	@FXML
	private TableColumn<Categoria, String> nombreColumna;

	@FXML
	protected void initialize() {
		System.out.println("Cargando...");
		muestraInfo(null);
		configuraTabla();
		// Cargar de la base de datos!!!!!
		// Cambiare por los DAOOOOO!!!!!!!!!

		tablaCategoria.setItems(FXCollections.observableArrayList(AppController.todas));
		tablaCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			muestraInfo(newValue);
		});
	}

	private void configuraTabla() {
		idColumna.setCellValueFactory(cadacategoria -> {
			SimpleStringProperty c = new SimpleStringProperty();
			c.setValue(Integer.toString(cadacategoria.getValue().getId()));
			return c;
		});
		nombreColumna.setCellValueFactory(cadacategoria -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(cadacategoria.getValue().getNombre());
			return v;
		});
	}

	private void muestraInfo(Categoria p) {

		if (p != null) {
			idLabel.setText(Integer.toString(p.getId()));
			;
			nombreLabel.setText(p.getNombre());
		} else {
			idLabel.setText("");
			nombreLabel.setText("");
		}
	}

	@FXML
	private void EliminarCategoria() {
		int id = Integer.parseInt(idLabel.getText());
		if (id >= 0) {
			CategoriaDAO p = new CategoriaDAO(id);
			mostrarAlertWarning();
			p.eliminar();
			AppController.todas.remove(p);
			System.out.println("ELiminado");
			AppController.todas = CategoriaDAO.GetAllCategoria();
			try {
				App.setRoot("Inicio");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@FXML
	private void mostrarAlertWarning() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setTitle("Eliminar Categoria");
		alert.setContentText("Se va a Eliminar Una categoria de la base de datos");
		alert.showAndWait();
	}

	@FXML
	private void MostrarJuegos() {
		int id = Integer.parseInt(idLabel.getText());
		Stage p = new Stage();
		CategoriaDAO s = new CategoriaDAO(id);

		// s.setJuegos(s.getMiJuegos());
		// AppController.Juegos=CategoriaDAO.buscarJuegosPorCategoria(id);
		AppController.Juegos = s.getJuegos();
		AppController.idCategoria = s.getId();
		System.out.println(AppController.Juegos);
		try {
			App.loadScene(p, "InicioJuegos", "Juegos");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void EditarCategoria() {
		int id = Integer.parseInt(idLabel.getText());
		if (id >= 0) {
			CategoriaDAO c = new CategoriaDAO(id);
			RegistroController.setCategoria(c);
			try {
				App.loadScene(new Stage(), "registro", "Actualizar");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Categoria j : AppController.todas) {
				if (j.getId() == c.getId()) {
					j.setNombre(c.getNombre());

				}
			}
			tablaCategoria.refresh();

		}
	}

	@FXML
	private void AñadirCategoria() {
		Stage p = new Stage();
		try {
			App.loadScene(p, "registro", "Añadir Categoria");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
