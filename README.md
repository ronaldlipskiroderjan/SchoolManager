# SchoolManager

Sistema de Gestão Escolar desenvolvido como projeto acadêmico da disciplina de **Programação Orientada a Objetos / Desenvolvimento de Sistemas (RA3)** — PUCPR · Bacharelado em Engenharia de Software.

---

## Descrição

Aplicação desktop em Java puro com interface gráfica construída manualmente (sem SceneBuilder/FXML), que permite o gerenciamento completo de uma escola por meio de operações de **CRUD** (Cadastrar, Listar, Editar e Excluir) para 10 entidades do domínio escolar. Os dados são persistidos localmente em arquivos de texto (`.txt`).

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 25 |
| Maven | 3.x |
| Interface Gráfica | Java Swing (puro, sem FXML) |
| Persistência | Arquivos `.txt` via I/O |

---

## Estrutura do Projeto

```
br.pucpr.schoolmanager
├── main
│   └── MainApp.java          # Ponto de entrada e menu principal
├── model                     # Entidades de domínio (POJOs)
│   ├── Curso.java
│   ├── Disciplina.java
│   ├── Professor.java
│   ├── Aluno.java
│   ├── SalaAula.java
│   ├── Equipamento.java
│   ├── Turma.java
│   ├── Funcionario.java
│   ├── Matricula.java
│   └── Mensalidade.java
├── repository
│   └── EscolaRepository.java # Leitura e escrita nos arquivos .txt
└── view                      # Telas de cada módulo
    ├── CursoView.java
    ├── DisciplinaView.java
    ├── ProfessorView.java
    ├── AlunoView.java
    ├── SalaAulaView.java
    ├── EquipamentoView.java
    ├── TurmaView.java
    ├── FuncionarioView.java
    ├── MatriculaView.java
    └── MensalidadeView.java
```

---

## Módulos e Responsabilidades

| Aluno | Módulo | Entidades |
|---|---|---|
| Aluno 1 | Estrutura Acadêmica | `Curso`, `Disciplina` |
| Aluno 2 | Pessoas — Acadêmico | `Professor`, `Aluno` |
| Aluno 3 | Infraestrutura | `SalaAula`, `Equipamento` |
| Aluno 4 | Administração | `Turma`, `Funcionario` |
| Aluno 5 | Financeiro / Secretaria | `Matricula`, `Mensalidade` |

---

## Atributos das Entidades

| Entidade | Atributo Texto | Atributo Numérico | Atributo Data |
|---|---|---|---|
| Curso | Nome do Curso | Carga Horária Total | Data de Criação |
| Disciplina | Nome da Matéria | Quantidade de Créditos | Data de Atualização |
| Professor | Nome Completo | Valor da Hora/Aula (R$) | Data de Contratação |
| Aluno | Nome | Número de Matrícula | Data de Nascimento |
| SalaAula | Bloco / Nome da Sala | Capacidade Máxima | Última Inspeção |
| Equipamento | Descrição / Tipo | Número de Patrimônio | Data de Compra |
| Turma | Código da Turma | Semestre Atual | Início das Aulas |
| Funcionario | Nome do Colaborador | Salário (R$) | Data de Admissão |
| Matricula | Status do Registro | Valor Total (R$) | Data de Efetivação |
| Mensalidade | Mês de Referência | Valor Cobrado (R$) | Data de Vencimento |

---

## Como Executar

### Pré-requisitos

- JDK 25+
- Maven 3.x

### Build e execução via Maven

```bash
mvn compile
mvn exec:java -Dexec.mainClass="br.pucpr.schoolmanager.main.MainApp"
```

### Build e execução via IntelliJ IDEA

1. Abra o projeto pela pasta raiz (o IntelliJ detecta o `pom.xml` automaticamente).
2. Aguarde a indexação e o download de dependências.
3. Execute a classe `MainApp` com o botão **Run**.

---

## Regras de Negócio

| # | Regra |
|---|---|
| RN01 | Interface 100% em código Java — proibido SceneBuilder/FXML |
| RN02 | Campos numéricos protegidos com `try-catch` (`NumberFormatException`) e alertas visuais |
| RN03 | Datas no padrão brasileiro `DD/MM/AAAA` com validação de calendário |
| RN04 | Aplicação unificada — `MainApp` centraliza a navegação entre todos os módulos |
| RN06 | Valores monetários e de contagem rejeitam zero e negativos |
| RN07 | Códigos de matrícula tratados como valores numéricos |
| RN08 | Edição e exclusão disparam regravação imediata no arquivo de texto |

---

## Persistência

Cada entidade possui um arquivo `.txt` dedicado gerado em tempo de execução. Os campos são delimitados por ponto e vírgula (`;`), seguindo o padrão:

```
Engenharia de Software;3200;15/02/2024
```

O `EscolaRepository` gerencia todas as operações de leitura e escrita, isolando a camada de persistência das demais.

---

## Licença

Projeto acadêmico — PUCPR 2026. Uso restrito aos integrantes da equipe e à avaliação docente.
