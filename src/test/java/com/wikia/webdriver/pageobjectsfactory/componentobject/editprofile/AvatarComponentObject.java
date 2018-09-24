package com.wikia.webdriver.pageobjectsfactory.componentobject.editprofile;

import com.wikia.webdriver.common.logging.Log;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;

public class AvatarComponentObject extends WikiBasePageObject {

  @FindBy(id = "avatar-upload-input")
  private WebElement uploadInput;
  @FindBy(css = "#UPPLightboxWrapper [data-event=save]")
  private WebElement saveButton;
  @FindBy(className = "default-avatar")
  private List<WebElement> defaultAvatarImages;

  public void uploadAvatar(String file) {
    File fileCheck = new File(
        "." + File.separator + "src" + File.separator + "test" + File.separator + "resources"
        + File.separator + "ImagesForUploadTests" + File.separator + file);
    if (!fileCheck.isFile()) {
      Log.log("uploadAvatar", "the file doesn't exist", false);
    }
    uploadInput.sendKeys(fileCheck.getAbsoluteFile().toString());
    Log.log("typeInFileToUploadPath", "type file " + file + " to upload it", true, driver);
  }

  /**
   * Select one of the default avatars we offer for users.
   * @return URL of the selected avatar
   */
  public String pickRandomDefaultAvatar() {
    WebElement chosenAvatar = defaultAvatarImages.stream()
        .filter(e -> !"Avatar.jpg".equals(e.getAttribute("data-name")))
        .findAny()
        .get();

    String chosenAvatarSrc = chosenAvatar.getAttribute("src");

    chosenAvatar.click();

    Log.log("pickRandomDefaultAvatar","selected default avatar with source: " + chosenAvatarSrc, true, driver);

    return chosenAvatarSrc;
  }

  public void saveProfile() {
    wait.forElementClickable(saveButton);
    scrollAndClick(saveButton);
    Log.log("save", "save profile button clicked", true);
  }
}
