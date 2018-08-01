import org.abalong.utilities.SessionFactoryUtil;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionTester extends Assert {

    private SessionFactory sessionFactory;

    @Test
    public void test_getSessionFactory() {
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        assertNotNull(sessionFactory);
    }
}
