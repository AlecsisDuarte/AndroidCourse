package mx.com.omnius.preferenciasyalmacenamientoapp.BD;

import java.io.Serializable;

public class Persona implements Serializable {
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer edad;

	public Persona(String nombre, String apellido, Integer edad) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
	}

	public Persona() {
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

    @Override
    public String toString() {
        return "nombre = " + nombre + '\n' +
                "apellido = " + apellido + '\n' +
                "edad =" + edad ;
    }
}
