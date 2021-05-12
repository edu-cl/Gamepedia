package com.Eduardo.Gamepedia;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import javax.swing.ImageIcon;

import com.Eduardo.Gamepedia.Model.Categoria.Categoria;
import com.Eduardo.Gamepedia.Model.Categoria.CategoriaDAO;
import com.Eduardo.Gamepedia.Model.Juego.Juego;
import com.Eduardo.Gamepedia.Model.Juego.JuegoDAO;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class JuegoController {

	@FXML
	private Label idLabel;

	@FXML
	private Label nombreLabel;

	@FXML
	private Label Descripcion;
	
	@FXML
	private Button Eliminar;
	@FXML
	private ImageView Imagen = new ImageView();

	@FXML
	TableView<Juego> tablaJuego;

	@FXML
	private TableColumn<Juego, String> idColumna;
	@FXML
	private TableColumn<Juego, String> nombreColumna;

	@FXML
	protected void initialize() {
		System.out.println("Cargando...");

		muestraInfo(null);
		configuraTabla();
		// Cargar de la base de datos!!!!!
		// Cambiare por los DAOOOOO!!!!!!!!!

		tablaJuego.setItems(FXCollections.observableArrayList(AppController.Juegos));
		tablaJuego.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

	private void muestraInfo(Juego p) {

		if (p != null) {
			idLabel.setText(Integer.toString(p.getId()));
			;
			nombreLabel.setText(p.getNombre());
			Descripcion.setText(p.getDescripcion());
			System.out.println(p.getImagen());
			Imagen.setImage(CargarImagen(p.getImagen()));

		} else {
			idLabel.setText("");
			Imagen.setImage(CargarImagen("pure-white-background-85a2a7fd.jpg"));
			nombreLabel.setText("");
			Descripcion.setText("");
		}
	}

	private Image CargarImagen(String url) {
		Image image1 = null;

		File f = new File(url);
		if (f.exists()) {
			try {
				image1 = new Image(new FileInputStream(f));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				image1 = new Image(new FileInputStream("404.png"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return image1;

	}
	
	@FXML
	private void EditaJuego() {
		int id = Integer.parseInt(idLabel.getText());
		if (id >= 0) {
			JuegoDAO j = new JuegoDAO(id);
			RegistroJuegoController.setJuego(j);
			try {
				App.loadScene(new Stage(), "RegistroJuegos", "Actualizar");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Juego f : AppController.Juegos) {
				if (f.getId() == j.getId()) {
					f.setImagen(j.getImagen());
					f.setNombre(j.getNombre());
					f.setDescripcion(j.getDescripcion());
					f.setId_categoria(j.getId_categoria());					
				}
			}
			tablaJuego.refresh();
			initialize();
		}
	}
	
	

	@FXML
	private void AñadirJuego() {
		Stage p = new Stage();
		
		JuegoDAO j=new JuegoDAO();
		RegistroJuegoController.setJuego(j);
		try {
			App.loadScene(p, "RegistroJuegos", "Añadir Juego ");
			
			tablaJuego.refresh();
			initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void mostrarAlertWarning() {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setHeaderText(null);
	    alert.setTitle("Eliminar Juego");
	    alert.setContentText("Se cerrara la pestaña y borrara un juego de la base de datos");
	    alert.showAndWait();
	}
	@FXML
	private void EliminarJuego() {
		int id = Integer.parseInt(idLabel.getText());
		if (id >= 0) {
			JuegoDAO p = new JuegoDAO(id);
			Juego j=new Juego(p.getId(),p.getImagen(),p.getNombre(),p.getDescripcion(),p.getId_categoria());
			AppController.Juegos.remove(j);
			p.eliminar();
			mostrarAlertWarning();
			App.closeScene((Stage) Eliminar.getScene().getWindow());

		}

	}
}
