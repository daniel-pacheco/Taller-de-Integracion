package ar.com.santalucia;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;

public class SQLTest {

	public static void main(String[] args) {

		String sql = "SELECT NOMBRE, APELLIDO FROM USUARIO";
		Session sess = HibernateUtil.getSessionFactory().getCurrentSession();
		sess.beginTransaction();
		SQLQuery consulta = sess.createSQLQuery(sql).addEntity(Usuario.class);
		List resultado = consulta.list();

		System.out.println(resultado);

	}
}