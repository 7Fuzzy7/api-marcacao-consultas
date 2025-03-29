package com.fiap.ecr.api_marcacao_consultas.config;
import com.fiap.ecr.api_marcacao_consultas.model.Consulta;
import com.fiap.ecr.api_marcacao_consultas.model.Especialidade;
import com.fiap.ecr.api_marcacao_consultas.model.Usuario;
import com.fiap.ecr.api_marcacao_consultas.repository.ConsultaRepository;
import com.fiap.ecr.api_marcacao_consultas.repository.EspecialidadeRepository;
import com.fiap.ecr.api_marcacao_consultas.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Configuration
public class DataInitializer {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PersistenceContext
    private EntityManager entityManager;

    private static final String INIT_FLAG_FILE = "./data/db_initialized.flag";
    @Bean
    @Transactional
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            EspecialidadeRepository especialidadeRepository,
            ConsultaRepository consultaRepository) {

        return args -> {
            File flagFile = new File(INIT_FLAG_FILE);

            if (flagFile.exists()) {
                System.out.println("Banco de dados já foi inicializado anteriormente. Pulando inicialização.");
                return;
            }

            System.out.println("Limpando banco de dados...");
            consultaRepository.deleteAllInBatch();
            usuarioRepository.deleteAllInBatch();
            especialidadeRepository.deleteAllInBatch();

            try {
                entityManager.createNativeQuery("ALTER TABLE consultas ALTER COLUMN id RESTART WITH 1").executeUpdate();
                entityManager.createNativeQuery("ALTER TABLE usuarios ALTER COLUMN id RESTART WITH 1").executeUpdate();
                entityManager.createNativeQuery("ALTER TABLE especialidades ALTER COLUMN id RESTART WITH 1").executeUpdate();
            } catch (Exception e) {
                System.out.println("Aviso: Não foi possível resetar as sequências de ID. Isso é normal na primeira execução.");
            }

            System.out.println("Inicializando banco de dados com dados de exemplo...");

            // Criando especialidades
            List<Especialidade> especialidades = new ArrayList<>();
            String[] nomesEspecialidades = {"Cardiologia", "Dermatologia", "Ortopedia", "Pediatria", "Neurologia"};

            for (String nome : nomesEspecialidades) {
                Especialidade especialidade = new Especialidade();
                especialidade.setNome(nome);
                especialidades.add(especialidade);
            }

            especialidadeRepository.saveAll(especialidades);
            System.out.println("Especialidades criadas: " + especialidades.size());

            criarArquivoFlag();
            System.out.println("Inicialização de dados concluída com sucesso!");
        };
    }

    private void criarArquivoFlag() {
        try {
            Path diretorio = Paths.get("./data");
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            Path flagPath = Paths.get(INIT_FLAG_FILE);
            Files.createFile(flagPath);
            Files.write(flagPath, ("Banco de dados inicializado em: " + java.time.LocalDateTime.now().toString()).getBytes());

            System.out.println("Arquivo de flag criado. O banco não será reinicializado nas próximas execuções.");
        } catch (IOException e) {
            System.err.println("Aviso: Não foi possível criar o arquivo de flag: " + e.getMessage());
        }
    }
}