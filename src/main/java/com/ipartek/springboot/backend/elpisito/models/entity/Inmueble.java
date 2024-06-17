package com.ipartek.springboot.backend.elpisito.models.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "inmuebles")
public class Inmueble implements Serializable {

	@Serial
	private static final long serialVersionUID = -7456969893172326318L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Es un incremental para MySQL
	@Column
	private Long id;
	@Column
	private String via; // Calle, Plaza, Carretera
	@Column
	private String titular; // Maravilloso piso en ...
	@Column(name = "nombre_via")
	private String nombreVia; // Rua del Percebe
	@Column
	private String planta; // Rua del Percebe
	@Column
	private String puerta; // A,B, Izq ...
	@Column
	private String apertura; // Exterior, Interior
	@Column
	private String orientacion; // Norte, Sur
	@Column(name = "superficie_util")
	private String superficieUtil;
	@Column(name = "superficie_construida")
	private String superficieConstruida;
	@Column
	private Integer precio;
	@Column(name = "numero_habitaciones")
	private String numeroHabitaciones;
	@Column(name = "numero_banhos")
	private String numeroBanhos;
	@Column(length = 3500)
	private String descripcion; // Unas amplias frases sobre la situacion y las caracteristicas del imbueble
	@Column(name = "tipo_calefaccion")
	private String tipoCalefaccion; // Sin, Electrica, Gas Natural
	@Column
	private Integer amueblado; // 0: no amueblado ,1: amueblado
	@Column
	private String numero;
	@Column(name = "numero_balcones")
	private String numeroBalcones;
	@Column(name = "plazas_garaje")
	private String plazasGaraje;
	@Column
	private Integer piscina; // 0: no tiene, 1: tiene
	@Column
	private Integer trastero; // 0: no tiene, 1: tiene
	@Column
	private Integer ascensor; // 0: no tiene, 1: tiene
	@Column
	private Integer jardin; // 0: no tiene, 1: tiene
	@Column
	private Integer tenderero; // 0: no tiene, 1: tiene
	@Column
	private Integer portada = 0; // 0: no aparece en portada, 1: aparece en portada
	@Column
	private Integer activo = 1;
	@Column
	private String cp;
	@Column
	private String operacion; // Venta, Alquiler, Traspaso
	@JsonIgnore
	@OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL)
	private Set<Imagen> imagenes;
	@ManyToOne
	@JoinColumn(name = "poblacion")
	private Poblacion poblacion;
	@ManyToOne
	@JoinColumn(name = "tipo")
	private Tipo tipo;

}
