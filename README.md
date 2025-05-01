# 💻 Projeto: Persistência com JPA + Hibernate + PostgreSQL (Maven)

Este projeto demonstra como configurar e utilizar **JPA (Jakarta Persistence API)** com **Hibernate** e banco de dados **PostgreSQL**, utilizando o **Maven** como gerenciador de dependências dentro do **IntelliJ IDEA**.  
A aplicação realiza operações básicas de persistência com uma entidade chamada `Pessoa`.

---

## 🧠 Objetivo

- Criar um projeto Maven no IntelliJ IDEA.  
- Configurar JPA com Hibernate como provedor.  
- Conectar o projeto ao banco PostgreSQL.  
- Persistir, consultar e remover dados com uma entidade JPA.  
- Estruturar e documentar o projeto para fins de aprendizagem e portfólio.

---

## 📚 Pré-requisitos

- Java JDK 17 instalado  
- PostgreSQL instalado e rodando localmente  
- IntelliJ IDEA (Community ou Ultimate)  
- pgAdmin (opcional, para gerenciar visualmente o banco)  
- Conexão com a internet para baixar dependências Maven

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia       | Versão         |
|------------------|----------------|
| Java             | 17             |
| Maven            | 4.x            |
| Hibernate        | 5.6.15.Final   |
| JPA (Jakarta)    | via Hibernate  |
| PostgreSQL       | 15+            |
| JDBC Driver      | 42.7.2         |
| IntelliJ IDEA    | 2023+          |

---

## 🧱 Etapas de Configuração

### ✅ 1. Criar Projeto Maven no IntelliJ

1. Vá em **File > New > Project**
2. Escolha **Maven**
3. Desmarque a opção de archetype ou selecione `maven-archetype-quickstart`
4. Preencha:
   - `GroupId`: `com.auladejpamaven`
   - `ArtifactId`: `aulajpamaven`
5. Clique em **Finish**

---

### ✅ 2. Estrutura de Diretórios
```text
src/
├── main/
│   ├── java/
│   │   ├── com.auladejpamaven/     ← Classe App.java
│   │   └── dominio/                ← Entidade Pessoa.java
│   └── resources/
│       └── META-INF/
│           └── persistence.xml     ← Configuração JPA
```

🔥 A pasta `META-INF` dentro de `resources` é obrigatória para o `persistence.xml`.

### ✅ 3. Criar o banco de dados no PostgreSQL

Execute no terminal ou no pgAdmin:

```sql
CREATE DATABASE aulajpa;
```

### ⚙️ Configure seu Pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.auladejpamaven</groupId>
    <artifactId>aulajpamaven</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Hibernate -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.6.15.Final</version>
        </dependency>

        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.6.15.Final</version>
        </dependency>

        <!-- PostgreSQL JDBC Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.2</version>
        </dependency>

        <!-- JUnit (opcional para testes) -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

### ⚙️ Configure seu Persistence.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence
                                 https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="3.0">

    <persistence-unit name="exemplo-jpa" transaction-type="RESOURCE_LOCAL">
        <properties>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/aulajpa"/>
            <property name="jakarta.persistence.jdbc.driver" value="org.postgresql.Driver"/>

            <!-- "postgres" é o nome de usuário padrão do PostgreSQL -->
            <property name="jakarta.persistence.jdbc.user" value="postgres"/>

            <!-- Altere para a sua senha local do banco -->
            <property name="jakarta.persistence.jdbc.password" value="SUA_SENHA_AQUI"/>

            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
            <property name="hibernate.show_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

### 🧩 Entidade JPA – Pessoa.java
```java
package dominio;

import javax.persistence.*;

@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;

    // Construtores, getters, setters e toString()
}
```

### 🚀 Classe Principal – App.java
```java

package com.auladejpamaven;

import dominio.Pessoa;
import javax.persistence.*;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
        EntityManager em = emf.createEntityManager();

        // Criar e inserir pessoas
        Pessoa p1 = new Pessoa(null, "Carlos Silva", "carlos@gmail.com");

        em.getTransaction().begin();
        em.persist(p1);
        em.getTransaction().commit();

        // Buscar por ID
        Pessoa p = em.find(Pessoa.class, 1);
        System.out.println(p);

        /* Remover do banco de dados:
         Sempre que for uma operação que não seja uma simples consulta é necessario colocar a transação "em.getTransaction().begin();",
         primeiro achamos nosso objeto por ID, depois acionamos a transação e removemos com "em.remove(p)". */

         Pessoa p = em.find(Pessoa.class, 2);
         em.getTransaction().begin();
         em.remove(p);
         em.getTransaction().commit();

         

        em.close();
        emf.close();
    }
}
```

### ✅ Resultado Esperado
- Tabela pessoa criada automaticamente no banco aulajpa

- Dados persistidos corretamente via JPA
 
- SQLs exibidos no console (hibernate.show_sql=true)

### 📌 Dicas Finais
- ✅ Verifique se o persistence.xml está dentro de src/main/resources/META-INF/

- ⚠️ O valor de persistence-unit name deve ser exatamente igual ao usado em Persistence.createEntityManagerFactory("exemplo-jpa")
