import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.jdbc.util.FormatStyle;
import org.hibernate.jdbc.util.Formatter;

public class Main {

	public static void main(String[] args) {

		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		System.out.println("----------------- DDL STATEMENTS ------------------------------------");
		{
			Dialect dialect = Dialect.getDialect(configuration.getProperties());
			String[] dropSQL = configuration.generateDropSchemaScript(dialect);
			String[] createSQL = configuration.generateSchemaCreationScript(dialect);

			Formatter formatter = FormatStyle.DDL.getFormatter();
			try (PrintWriter writer = new PrintWriter(new FileOutputStream("ddl.sql"))) {
				for (String string : dropSQL) {
					System.out.println(formatter.format(string) + ";");
					writer.println(formatter.format(string) + ";");
				}
				System.out.println("\n\n\n");
				writer.println("\n\n\n");
				for (String string : createSQL) {
					System.out.println(formatter.format(string) + ";");
					writer.println(formatter.format(string) + ";");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			System.out.println("\n\n----------------- ONE TO ONE ------------------------------------");
			{
				primarykey.A a1 = new primarykey.A();
				primarykey.A a2 = new primarykey.A();

				a1.setValue1("a1");
				a2.setValue1("a2");
				a1.setValue2("A1");
				a2.setValue2("A2");

				session.persist(a1);
				session.persist(a2);

				for (Object o : session.createCriteria(primarykey.A.class).list())
					System.out.println(o);
				
				primarykey.B b1 = new primarykey.B();
				primarykey.B b2 = new primarykey.B();

				b1.setValue1("b1");
				b2.setValue1("b2");
				b1.setValue2("B1");
				b2.setValue2("B2");

				session.persist(b1);
				session.persist(b2);

				for (Object o : session.createCriteria(primarykey.B.class).list())
					System.out.println(o);

			}

			System.out.println("\n\n----------------- ONE TO ONE ------------------------------------");
			{
				one2one.A a1 = new one2one.A();
				one2one.A a2 = new one2one.A();

				one2one.B b1 = new one2one.B();
				one2one.B b2 = new one2one.B();

				a1.setValue("a1");
				a2.setValue("a2");

				b1.setValue("b1");
				b2.setValue("b2");

				a1.setB(b1);
				a2.setB(b2);

				b1.setA(a1);
				b2.setA(a2);

				session.persist(a1);
				session.persist(a2);
				session.persist(b1);
				session.persist(b2);

				for (Object o : session.createCriteria(one2one.A.class).list())
					System.out.println(o);
				for (Object o : session.createCriteria(one2one.B.class).list())
					System.out.println(o);
			}

			System.out.println("\n\n----------------- ONE TO MANY LIST STRING ----------------------");
			{
				one2many.list.string.A a = new one2many.list.string.A();
				a.setValue("a1");
				List<String> list = new ArrayList<>();
				list.add("one");
				list.add("two");
				list.add("three");
				a.setStrings(list);
				session.persist(a);

				a = new one2many.list.string.A();
				a.setValue("a2");
				list = new ArrayList<>();
				list.add("one");
				list.add("two");
				list.add("three");
				a.setStrings(list);
				session.persist(a);

				for (Object o : session.createCriteria(one2many.list.string.A.class).list())
					System.out.println(o);
			}

			System.out.println("\n\n----------------- ONE TO MANY SET STRING -----------------------");
			{
				one2many.set.string.A a = new one2many.set.string.A();
				a.setValue("a1");
				Set<String> set = new HashSet<>();
				set.add("one");
				set.add("two");
				set.add("three");
				a.setStrings(set);
				session.persist(a);

				a = new one2many.set.string.A();
				a.setValue("a2");
				set = new HashSet<>();
				set.add("one");
				set.add("two");
				set.add("three");
				a.setStrings(set);
				session.persist(a);

				for (Object o : session.createCriteria(one2many.set.string.A.class).list())
					System.out.println(o);
			}
			System.out.println("\n\n----------------- ONE TO MANY LIST BEES ------------------------");
			{
				one2many.list.bees.B b1 = new one2many.list.bees.B();
				one2many.list.bees.B b2 = new one2many.list.bees.B();
				one2many.list.bees.B b3 = new one2many.list.bees.B();
				b1.setValue("b1");
				b2.setValue("b2");
				b3.setValue("b3");
				session.persist(b1);
				session.persist(b2);
				session.persist(b3);

				one2many.list.bees.A a = new one2many.list.bees.A();
				a.setValue("a1");
				List<one2many.list.bees.B> list = new ArrayList<>();

				list.add(b1);
				list.add(b2);
				a.setBees(list);
				session.persist(a);

				a = new one2many.list.bees.A();
				a.setValue("a2");
				list = new ArrayList<>();
				list.add(b3);
				a.setBees(list);
				session.persist(a);

				for (Object o : session.createCriteria(one2many.list.bees.A.class).list())
					System.out.println(o);
			}
			System.out.println("\n\n----------------- ONE TO MANY SET BEES -------------------------");
			{
				one2many.set.bees.B b1 = new one2many.set.bees.B();
				one2many.set.bees.B b2 = new one2many.set.bees.B();
				one2many.set.bees.B b3 = new one2many.set.bees.B();
				b1.setValue("b1");
				b2.setValue("b2");
				b3.setValue("b3");
				session.persist(b1);
				session.persist(b2);
				session.persist(b3);

				one2many.set.bees.A a = new one2many.set.bees.A();
				a.setValue("a1");
				Set<one2many.set.bees.B> set = new HashSet<>();

				set.add(b1);
				set.add(b2);
				a.setBees(set);
				session.persist(a);

				a = new one2many.set.bees.A();
				a.setValue("a2");
				set = new HashSet<>();
				set.add(b3);
				a.setBees(set);
				session.persist(a);

				for (Object o : session.createCriteria(one2many.set.bees.A.class).list())
					System.out.println(o);
			}

			System.out.println("\n\n----------------- MANY TO ONE BEES -----------------------------");

			{
				many2one.bees.B b1 = new many2one.bees.B();
				many2one.bees.B b2 = new many2one.bees.B();
				many2one.bees.B b3 = new many2one.bees.B();
				b1.setValue("b1");
				b2.setValue("b2");
				b3.setValue("b3");
				session.persist(b1);
				session.persist(b2);
				session.persist(b3);

				many2one.bees.A a = new many2one.bees.A();
				a.setValue("a1");
				b1.setA(a);
				b2.setA(a);
				session.persist(a);

				a = new many2one.bees.A();
				a.setValue("a2");
				b3.setA(a);
				session.persist(a);

				for (Object o : session.createCriteria(many2one.bees.A.class).list())
					System.out.println(o);
				for (Object o : session.createCriteria(many2one.bees.B.class).list())
					System.out.println(o);
			}

			System.out.println("\n\n----------------- M2O O2M BIDIR LIST BEES ------------------------");

			{
				many2one_one2many.bidirectional.list.bees.B b1 = new many2one_one2many.bidirectional.list.bees.B();
				many2one_one2many.bidirectional.list.bees.B b2 = new many2one_one2many.bidirectional.list.bees.B();
				many2one_one2many.bidirectional.list.bees.B b3 = new many2one_one2many.bidirectional.list.bees.B();
				many2one_one2many.bidirectional.list.bees.A a1 = new many2one_one2many.bidirectional.list.bees.A();
				many2one_one2many.bidirectional.list.bees.A a2 = new many2one_one2many.bidirectional.list.bees.A();

				b1.setValue("b1");
				b2.setValue("b2");
				b3.setValue("b3");
				a1.setValue("a1");
				a2.setValue("a2");

				b1.setA(a1);
				b2.setA(a1);
				b3.setA(a2);

				List<many2one_one2many.bidirectional.list.bees.B> list1 = new ArrayList<>();
				list1.add(b1);
				list1.add(b2);

				List<many2one_one2many.bidirectional.list.bees.B> list2 = new ArrayList<>();
				list2.add(b3);

				a1.setBees(list1);
				a2.setBees(list2);

				session.persist(b1);
				session.persist(b2);
				session.persist(b3);
				session.persist(a1);
				session.persist(a2);

				for (Object o : session.createCriteria(many2one_one2many.bidirectional.list.bees.A.class).list())
					System.out.println(o);
				for (Object o : session.createCriteria(many2one_one2many.bidirectional.list.bees.B.class).list())
					System.out.println(o);
			}

			System.out.println("\n\n----------------- M2O O2M BIDIR SET BEES ------------------------");

			{
				many2one_one2many.bidirectional.set.bees.B b1 = new many2one_one2many.bidirectional.set.bees.B();
				many2one_one2many.bidirectional.set.bees.B b2 = new many2one_one2many.bidirectional.set.bees.B();
				many2one_one2many.bidirectional.set.bees.B b3 = new many2one_one2many.bidirectional.set.bees.B();
				many2one_one2many.bidirectional.set.bees.A a1 = new many2one_one2many.bidirectional.set.bees.A();
				many2one_one2many.bidirectional.set.bees.A a2 = new many2one_one2many.bidirectional.set.bees.A();

				b1.setValue("b1");
				b2.setValue("b2");
				b3.setValue("b3");
				a1.setValue("a1");
				a2.setValue("a2");

				b1.setA(a1);
				b2.setA(a1);
				b3.setA(a2);

				Set<many2one_one2many.bidirectional.set.bees.B> set1 = new HashSet<>();
				set1.add(b1);
				set1.add(b2);

				Set<many2one_one2many.bidirectional.set.bees.B> set2 = new HashSet<>();
				set2.add(b3);

				a1.setBees(set1);
				a2.setBees(set2);

				session.persist(b1);
				session.persist(b2);
				session.persist(b3);
				session.persist(a1);
				session.persist(a2);

				for (Object o : session.createCriteria(many2one_one2many.bidirectional.set.bees.A.class).list())
					System.out.println(o);
				for (Object o : session.createCriteria(many2one_one2many.bidirectional.set.bees.B.class).list())
					System.out.println(o);
			}

			System.out.println("\n\n------------------- MANY TO MANY BEES ---------------------------");

			{
				many2many.bees.B b1 = new many2many.bees.B();
				many2many.bees.B b2 = new many2many.bees.B();
				many2many.bees.B b3 = new many2many.bees.B();
				many2many.bees.B b4 = new many2many.bees.B();
				many2many.bees.B b5 = new many2many.bees.B();
				many2many.bees.B b6 = new many2many.bees.B();
				many2many.bees.A a1 = new many2many.bees.A();
				many2many.bees.A a2 = new many2many.bees.A();
				many2many.bees.A a3 = new many2many.bees.A();

				b1.setValue("b1");
				b2.setValue("b2");
				b3.setValue("b3");
				b4.setValue("b4");
				b5.setValue("b5");
				b6.setValue("b6");
				a1.setValue("a1");
				a2.setValue("a2");
				a3.setValue("a3");

				Set<many2many.bees.B> setA1 = new HashSet<>();
				setA1.add(b1);
				setA1.add(b2);
				setA1.add(b3);

				Set<many2many.bees.B> setA2 = new HashSet<>();
				setA2.add(b1);
				setA2.add(b4);

				Set<many2many.bees.B> setA3 = new HashSet<>();
				setA3.add(b1);
				setA3.add(b5);
				setA3.add(b6);

				a1.setBees(setA1);
				a2.setBees(setA2);
				a3.setBees(setA3);

				Set<many2many.bees.A> setB1 = new HashSet<>();
				setB1.add(a1);
				setB1.add(a2);
				setB1.add(a3);

				Set<many2many.bees.A> setB2 = new HashSet<>();
				setB2.add(a1);

				Set<many2many.bees.A> setB3 = new HashSet<>();
				setB3.add(a1);

				Set<many2many.bees.A> setB4 = new HashSet<>();
				setB4.add(a2);

				Set<many2many.bees.A> setB5 = new HashSet<>();
				setB5.add(a2);

				Set<many2many.bees.A> setB6 = new HashSet<>();
				setB6.add(a3);

				b1.setAas(setB1);
				b2.setAas(setB2);
				b3.setAas(setB3);
				b4.setAas(setB4);
				b5.setAas(setB5);
				b6.setAas(setB6);

				session.persist(b1);
				session.persist(b2);
				session.persist(b3);
				session.persist(b4);
				session.persist(b5);
				session.persist(b6);
				session.persist(a1);
				session.persist(a2);
				session.persist(a1);
				session.persist(a3);

				for (Object o : session.createCriteria(many2many.bees.A.class).list())
					System.out.println(o);
				for (Object o : session.createCriteria(many2many.bees.B.class).list())
					System.out.println(o);
			}
			
			tx.commit();

			System.out.println("\n\n------------------------ CRITERIA -------------------------------");
			{
				Criteria c = session.createCriteria(one2many.set.bees.A.class);
				c.setFirstResult(0);
				c.setMaxResults(10);
				c.add(Restrictions.and(Restrictions.between("id", 0, 3),
						Restrictions.like("value", "a", MatchMode.START)));
				c.addOrder(Order.asc("id"));

				for (Object o : c.list())
					System.out.println(o);

				// To get total row count.
				c.setProjection(Projections.rowCount());
				for (Object o : c.list())
					System.out.println(o);

				// To get average of a property.
				c.setProjection(Projections.avg("id"));
				for (Object o : c.list())
					System.out.println(o);

				// To get distinct count of a property.
				c.setProjection(Projections.countDistinct("value"));
				for (Object o : c.list())
					System.out.println(o);

				// To get maximum of a property.
				c.setProjection(Projections.max("id"));
				for (Object o : c.list())
					System.out.println(o);

				// To get minimum of a property.
				c.setProjection(Projections.min("id"));
				for (Object o : c.list())
					System.out.println(o);

				// To get sum of a property.
				c.setProjection(Projections.sum("id"));
				for (Object o : c.list())
					System.out.println(o);
			}
			
			System.out.println("\n\n------------------------ HQL QUERY -------------------------------");
			{
				Query q = session.createQuery("from " + one2many.set.bees.A.class.getName() + " where value = :value");
				q.setParameter("value", "a");
				for (Object o : q.list())
					System.out.println(o);
				
				q = session.createQuery("select count(*) from " + one2many.set.bees.A.class.getName() + " where value = :value");
				q.setParameter("value", "a1");
				System.out.println(q.uniqueResult());
			}
		} catch (Exception e1) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			e1.printStackTrace();
		} finally {
			session.close();
		}

	}

}
