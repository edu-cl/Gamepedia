package com.Eduardo.Gamepedia.Model.Juego;


public class Juego {
	
	protected int id;
	protected String imagen;
	protected String nombre;
	protected String descripcion;
	protected int id_categoria;
	
	public Juego() {
		this(-1,"","","",-1);
	}

	public Juego(int id, String imagen, String nombre, String descripcion, int id_categoria) {
		super();
		this.id = id;
		this.imagen = imagen;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.id_categoria = id_categoria;
	}
	
	public Juego(String imagen, String nombre, String descripcion, int id_categoria) {
		super();
		this.id = -1;
		this.imagen = imagen;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.id_categoria = id_categoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}

	@Override
	public String toString() {
		return "Juego [id=" + id + ", imagen=" + imagen + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", id_categoria=" + id_categoria + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Juego other = (Juego) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	

}
