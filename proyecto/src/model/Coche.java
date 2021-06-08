package model;

public class Coche {

	private String marca;
	private String modelo;
	private String traccion;
	private String aniadidos;
	private String fechaSalida;
	private String reservado;
	
	public Coche(String marca, String modelo, String traccion, String aniadidos, String fechaSalida,
			String reservado) {
		this.marca = marca;
		this.modelo = modelo;
		this.traccion = traccion;
		this.aniadidos = aniadidos;
		this.fechaSalida = fechaSalida;
		this.reservado = reservado;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public String getTraccion() {
		return traccion;
	}

	public String getAniadidos() {
		return aniadidos;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public String getReservado() {
		return reservado;
	}
	
	
}
