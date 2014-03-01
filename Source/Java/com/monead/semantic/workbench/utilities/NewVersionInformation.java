package com.monead.semantic.workbench.utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Populated with information for a newer version of the program.
 * 
 * @author David Read
 * 
 */
public class NewVersionInformation {
  private String latestVersionString;
  private List<String> newFeatures = new ArrayList<String>();
  private URL urlToDownloadPage;
  private URL urlToSourceCode;

  /**
   * No operation
   */
  public NewVersionInformation() {

  }

  /**
   * Get the instructions for obtaining the new version.
   * 
   * TODO populate from website source
   * 
   * @return Instructions for obtaining a new version
   */
  public String getDownloadInformation() {
    StringBuilder message;
    
    message = new StringBuilder();
    
    if (getUrlToDownloadPage() != null) {
      message.append("The latest installer is located at:\n");
      message.append(getUrlToDownloadPage().toString());
      message.append("\n");
    }
    
    if (getUrlToSourceCode() != null) {
      message.append("The source code is located at:\n");
      message.append(getUrlToSourceCode().toString());
      message.append("\n");
    }
    
    return message.toString();
  }

  /**
   * Get the latest version number
   * 
   * @return The version number
   */
  public String getLatestVersion() {
    return latestVersionString;
  }

  /**
   * Set the latest version number
   * 
   * @param latestVersionString
   *          The version number
   */
  public void setLatestVersion(String latestVersionString) {
    this.latestVersionString = latestVersionString;
  }

  /**
   * Get the description of new features
   * 
   * @return Listing of new features
   */
  public String getNewFeaturesDescription() {
    StringBuffer message = new StringBuffer();

    for (String line : newFeatures) {
      message.append(line);
      message.append('\n');
    }

    return message.toString();
  }

  /**
   * Add a new feature description
   * 
   * @param newFeatureMessage
   *          New feature in the more recent version
   */
  public void addNewFeatureMessage(String newFeatureMessage) {
    newFeatures.add("  * " + newFeatureMessage);
  }

  /**
   * Get the URL for the application's download page. This will be null if the
   * value could not be loaded.
   * 
   * @return The URL to the program download web page. It will be null if the
   *         information could not be loaded.
   */
  public URL getUrlToDownloadPage() {
    return urlToDownloadPage;
  }

  /**
   * Set the URL for the application's download web page.
   * 
   * @param urlToDownloadPage
   *          The URL for the program's download page
   */
  public void setUrlToDownloadPage(URL urlToDownloadPage) {
    this.urlToDownloadPage = urlToDownloadPage;
  }

  /**
   * Get the URL for the application's source code repository location. This
   * will be null if the value could not be loaded.
   * 
   * @return The URL to the program's source code download location. It will be
   *         null if the information could not be loaded.
   */
  public URL getUrlToSourceCode() {
    return urlToSourceCode;
  }

  /**
   * Set the URL for the application's source code location
   * 
   * @param urlToSourceCode
   *          The URL for the program's source code location
   */
  public void setUrlToSourceCode(URL urlToSourceCode) {
    this.urlToSourceCode = urlToSourceCode;
  }
}
