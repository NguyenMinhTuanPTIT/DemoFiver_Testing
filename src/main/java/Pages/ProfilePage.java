package Pages;

import Base.BasePage;
import com.microsoft.playwright.Page;
import Locators.ProfileLocator;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProfilePage extends BasePage {
    public ProfilePage(Page page, String baseUrl) {
        super(page, baseUrl);
    }

    public void updateProfile(
            Optional<String> newPhone,
            Optional<String> newName,
            Optional<String> newBirthday,
            Optional<Boolean> isMale,
            Optional<String> certificate,
            Optional<String> skill
    ) {
        AtomicBoolean hasUpdate = new AtomicBoolean(false);

        newPhone.ifPresent(phone -> {
            fill(ProfileLocator.editPhoneNumber, phone);
            hasUpdate.set(true);
        });

        newName.ifPresent(name -> {
            fill(ProfileLocator.editName, name);
            hasUpdate.set(true);
        });

        newBirthday.ifPresent(birthday -> {
            fill(ProfileLocator.editBirthday, birthday);
            hasUpdate.set(true);
        });

        isMale.ifPresent(male -> {
            click(male ? ProfileLocator.opionMales : ProfileLocator.opionFemales);
            hasUpdate.set(true);
        });

        certificate.ifPresent(cer -> {
            fill(ProfileLocator.editCetificate, cer);
            hasUpdate.set(true);
        });

        skill.ifPresent(sk -> {
            fill(ProfileLocator.editSkill, sk);
            hasUpdate.set(true);
        });

        if (hasUpdate.get()) {
            click(ProfileLocator.btnSave);
        } else {
            click(ProfileLocator.btnCancel);
        }
    }

}
