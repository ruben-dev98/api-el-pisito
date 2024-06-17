package com.ipartek.springboot.backend.elpisito.models.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "imagenes")
public class Imagen implements Serializable {

	@Serial
	private static final long serialVersionUID = 8852854363773260187L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Es un incremental para MySQL
	@Column
	private Long id;
	@Column
	private String nombre; // 36565365363565635653.jpg
	@Column
	private Integer activo = 1;
	@ManyToOne
	@JoinColumn(name = "inmueble")
	private Inmueble inmueble;

}
