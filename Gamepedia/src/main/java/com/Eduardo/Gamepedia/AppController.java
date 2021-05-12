package com.Eduardo.Gamepedia;

import java.util.ArrayList;
import java.util.List;

import com.Eduardo.Gamepedia.Model.Categoria.Categoria;
import com.Eduardo.Gamepedia.Model.Categoria.CategoriaDAO;
import com.Eduardo.Gamepedia.Model.Juego.Juego;

public class AppController {
	
	public static int idCategoria=0;
	
	public static List<Categoria> todas = CategoriaDAO.GetAllCategoria();

	public static List<Juego> Juegos = new ArrayList<Juego>();
	

}
