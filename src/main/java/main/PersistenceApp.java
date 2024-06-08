package main;

import entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceApp {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceAppPU");
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Factura f1 = new Factura();
			
			f1.setNumero(12);
			f1.setFecha("10/08/2020");
			
			Domicilio dom = new Domicilio("San Martin", 1222);
			
			Cliente cli = new Cliente("Pablo", "Muñoz", 15245778);
			
			cli.setDomicilio(dom);
			dom.setCliente(cli);
			
			f1.setCliente(cli);
			
			Categoria perecederos = new Categoria("Perecederos");
			Categoria lacteos = new Categoria("Lacteos");
			
			Articulo a1 = new Articulo(200, "Yogurt Ser sabor frutilla", 20);
			Articulo a2 = new Articulo(300, "Detergente Magistral", 80);
			
			a1.getCategorias().add(perecederos);
			a1.getCategorias().add(lacteos);
			
			lacteos.getArticulos().add(a1);
			perecederos.getArticulos().add(a1);
			
			DetalleFactura df1 = new DetalleFactura();
			
			df1.setArticulo(a1);
			df1.setCantidad(2);
			df1.setSubtotal(40);
			
			a1.getDetalles().add(df1);
			f1.getDetalles().add(df1);
			df1.setFactura(f1);
			
			DetalleFactura df2 = new DetalleFactura();
			
			df2.setArticulo(a2);
			df2.setCantidad(1);
			df2.setSubtotal(80);
			
			a2.getDetalles().add(df2);
			f1.getDetalles().add(df2);
			df2.setFactura(f1);
			
			f1.setTotal(120);
			
			em.persist(f1);
			
			em.flush();
			em.getTransaction().commit();
		}
		catch (Exception e){
			em.getTransaction().rollback();
		}
		em.close();
		emf.close();
	}

}
