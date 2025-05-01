package com.auladejpamaven;

import dominio.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App 
{
    public static void main( String[] args )
    {
        // Criar objetos
        Pessoa p1 = new Pessoa(null, "Carlos Silva", "carlos@gmail.com");
        Pessoa p2 = new Pessoa(null, "Arlindo Cruz", "arlindo@gmail.com");
        Pessoa p3 = new Pessoa(null, "Isabel Silveira", "isabel@gmail.com");


        /* para instanciar nosso EntityManagerFactory com as configurações do persistence.xml para conectar
         com o banco de dados e depois
         instanciar nosso EntityManager:  */

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
        EntityManager em = emf.createEntityManager();





        // Inserir no banco
        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.getTransaction().commit();
        System.out.println("Pronto!");



        // Buscar por Id
        Pessoa pBusca = em.find(Pessoa.class, 2);
        System.out.println("Buscado: " + pBusca);



       /* Atualizar os dados, descomente pra testar:

        Pessoa pUpdate = em.find(Pessoa.class, 1);
        System.out.println("Antes da atualização: " + pUpdate);

        pUpdate.setNome("Carlos Atualizado");
        pUpdate.setEmail("carlos.atualizado@gmail.com");

        em.getTransaction().begin();
        em.merge(pUpdate);
        em.getTransaction().commit();

        System.out.println("Depois da atualização: " + em.find(Pessoa.class, 1));
        */

        /* deletar do banco de dados, descomente pra testar:
         Sempre que for uma operação que não seja uma simples consulta é necessario colocar a transação "em.getTransaction().begin();",
         primeiro achamos nosso objeto por ID, depois acionamos a transação e removemos com "em.remove(p)".

         Pessoa pRemover = em.find(Pessoa.class, 2);
        if (pRemover != null) {
            em.getTransaction().begin();
            em.remove(pRemover);
            em.getTransaction().commit();
            System.out.println("Removido com sucesso.");
        } else {
            System.out.println("Pessoa com ID 2 não encontrada.");
        }

         */



        // Boas praticas
        em.close();
        emf.close();






    }
}
