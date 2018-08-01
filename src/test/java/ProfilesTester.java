import org.abalong.controller.services.ProfilesService;
import org.abalong.model.entities.Profile;
import org.junit.Assert;
import org.junit.Test;

public class ProfilesTester extends Assert {

    private ProfilesService service = new ProfilesService();

    @Test
    public void test_getProfile() {
        Profile admin = service.getProfile("root");
        assertNotNull(admin);
    }

    @Test
    public void test_SaveUpdateDeleteProfileSuccess() {

        // Save
        String email = "smb@gmail.com";
        String password = "pass";
        Profile profile = new Profile(email, password);
        service.save(profile);
        profile = service.getProfile(email);
        assertNotNull(profile);

        // Update
        profile = service.getProfile(email);
        assertNotNull(profile);
        String name = "Some name";
        profile.setFirstName(name);
        service.update(profile);
        profile = service.getProfile(email);
        assertNotNull(profile);
        assertEquals(name, profile.getFirstName());

        // Delete
        profile = service.getProfile(email);
        assertNotNull(profile);
        service.delete(profile);
        profile = service.getProfile(email);
        assertNull(profile);
    }
}
