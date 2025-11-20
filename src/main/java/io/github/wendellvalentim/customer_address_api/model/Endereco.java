package io.github.wendellvalentim.customer_address_api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "endereco")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Endereco {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "rua", nullable = false, length = 100)
    private String rua;

    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @Column(name = "cidade", nullable = false, length = 80)
    private String cidade;

    @Column(name = "data_cadastro")
    @CreatedDate
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario ")
    private UUID id_usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
