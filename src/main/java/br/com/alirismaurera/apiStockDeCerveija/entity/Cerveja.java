package br.com.alirismaurera.apiStockDeCerveija.entity;

import br.com.alirismaurera.apiStockDeCerveija.enums.TipoCerveja;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cerveja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private int max;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCerveja tipo;



}
