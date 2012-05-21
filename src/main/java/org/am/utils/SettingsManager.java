package org.am.utils;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * The Settings manager. Loads and stores application's settings. 
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class SettingsManager {
  public static final String SECTION_START = "[";
  public static final String SECTION_END = "]";
  public static final String DEFAULT_SECTION_NAME = "General";
  public static final String DELIM_LINE = System.getProperty("line.separator");
  public static final String EQUALS = "=";
  public static final String UNDER = "_";

  private String fileName;
  private Map<String, Map> sections;

  /**
   * Creates a new SettingsManager object.
   *
   * @param fileName settings file name
   */
  public SettingsManager(String fileName) {
    this.fileName = fileName;
    sections = new TreeMap<String, Map>();
    sections.put(DEFAULT_SECTION_NAME, new TreeMap<String, String>());
  }

  /**
   * Adds a setting to the store.
   *
   * @param section a section
   * @param key     a key
   * @param value   a value
   */
  public void addSetting(String section, String key, String value) {
    if (sections.containsKey(section)) {
      Map<String, String> sec = sections.get(section);
      sec.put(key, value);
    } else {
      Map<String, String> sec = new TreeMap<String, String>();
      sec.put(key, value);
      sections.put(section, sec);
    }
  }

  /**
   * Adds a setting to the store.
   *
   * @param key   a key
   * @param value a value
   */
  public void addSetting(String key, String value) {
    addSetting(DEFAULT_SECTION_NAME, key, value);
  }

  /**
   * Adds a setting to the store.
   *
   * @param section a section
   * @param key     a key
   * @param comp    a component.
   *
   * Supported components are JTextComponent, JComboBox.
   */
  public void addSetting(String section, String key, JComponent comp) {
    if (comp instanceof JTextComponent) {
      addSetting(section, key, getString(comp));
    } else if (comp instanceof JComboBox) {
      String[] array = getStringArray(comp);
      if (array != null) {
        for (int i = 0; i < array.length; i++) {
          StringBuffer keyb  = new StringBuffer();
          keyb.append(key);
          keyb.append(UNDER);
          keyb.append(i);
          addSetting(section, keyb.toString(), array[i]);
        }
      }
    }
  }

  /**
   * Adds a setting to the store.
   *
   * @param key     a key
   * @param comp    a component.
   *
   * Supported components are JTextComponent, JComboBox.
   */
  public void addSetting(String key, JComponent comp) {
    addSetting(DEFAULT_SECTION_NAME, key, comp);
  }

  /**
   * Gets a setting.
   *
   * @param key a key
   * @return    a value
   */
  public String getSetting(String key) {
    return getSetting(DEFAULT_SECTION_NAME, key);
  }

  /**
   * Gets a setting.
   *
   * @param section a section
   * @param key     a key
   * @return        a value
   */
  public String getSetting(String section, String key) {
    if (sections.containsKey(section)) {
      Map<String, String> sec = sections.get(section);
      return sec.get(key);
    }
    return null;
  }

  /**
   * Gets a setting's array
   *
   * @param section a section
   * @param key     a key
   * @return        a value
   */
  public String[] getSettingArray(String section, String key) {
    if (sections.containsKey(section)) {
      List<String> list = new ArrayList<String>();
      Map<String, String> sec = sections.get(section);
      for (String k : sec.keySet()) {
        if (k.startsWith(key)) {
          list.add(sec.get(k));
        }
      }
      return list.toArray(new String[1]);
    }
    return null;
  }

  /**
   * Gets a setting's array
   *
   * @param key     a key
   * @return        a value
   */
  public String[] getSettingArray(String key) {
    return getSettingArray(DEFAULT_SECTION_NAME, key);
  }

  /**
   * Saves all settings.
   *
   * @throws IOException an exception
   */
  public void save() throws IOException {
    FileUtils.writeFile(fileName, toString(), false);
  }

  /**
   * Loads all settings.
   *
   * @throws IOException an exception
   */
  public void load() throws IOException {
    StringBuffer sb = FileUtils.readFile(fileName);
    sections = new TreeMap<String, Map>(); //new sections

    StringTokenizer tokens = new StringTokenizer(sb.toString(), DELIM_LINE);
    String token;
    String sectionName = null;
    while (tokens.hasMoreTokens()) {
      token = tokens.nextToken();
      if (token.startsWith(SECTION_START) && token.endsWith(SECTION_END)) {
        sectionName = token.substring(1, token.length() - 1);
      } else if (token.contains(EQUALS)) {
        String key = token.substring(0, token.indexOf(EQUALS));
        String value = token.substring(token.indexOf(EQUALS) + 1, token.length());
        addSetting(sectionName, key, value);
      }
    }
  }

  /**
   * Gets a string array from a component.
   *
   * @param comp a component
   * @return     the string array
   *
   * Supported components are JComboBox,...
   */
  private String[] getStringArray(JComponent comp) {
    if (comp instanceof JComboBox) {
      JComboBox comboBox = (JComboBox)comp;
      List<String> list = new ArrayList<String>();

      if (comboBox.getItemCount() > 0) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
          if (!isEmpty(comboBox.getItemAt(i))) {
            add(list, comboBox.getItemAt(i));
          } else if (!isEmpty(comboBox.getSelectedItem())){
            add(list, comboBox.getSelectedItem());
          }
        }

        if (list.size() > 0) {
          return list.toArray(new String[1]);
        } else {
          return null;
        }

      } else {
        if (!isEmpty(comboBox.getSelectedItem())) {
          add(list, comboBox.getSelectedItem());
          return list.toArray(new String[1]);
        }
      }
    }
    return null;
  }

  /**
   * Adds an object to a List.
   *
   * @param l a List
   * @param o an object
   */
  private void add(List l, Object o) {
    if (!l.contains(o)) {
      l.add(o);
    }
  }

  /**
   * Checks is object empty or not.
   *
   * @param o an object
   * @return  true if an object empty, false otherwise
   */
  private boolean isEmpty(Object o) {
    if (o == null || ((String)o).trim().length() < 1) {
      return true;
    }
    return false;
  }

  /**
   * Gets a string from a component.
   *
   * @param comp a component
   * @return     a string
   *
   * Supported components are JTextComponent, JComboBox.
   */
  private String getString(JComponent comp) {
    if (comp instanceof JTextComponent) {
      return ((JTextComponent)comp).getText();
    } else if (comp instanceof JComboBox) {
      JComboBox comboBox = (JComboBox)comp;
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < comboBox.getItemCount(); i++) {
        sb.append(comboBox.getItemAt(i));
        sb.append(DELIM_LINE);
      }
      return sb.toString();
    }
    return null;
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (Map.Entry<String, Map> section : sections.entrySet()) {
      if (section.getValue().size() > 0) {
        sb.append(SECTION_START);
        sb.append(section.getKey());
        sb.append(SECTION_END);
        sb.append(DELIM_LINE);

        Map<String, String> secValues = section.getValue();
        for (Map.Entry<String, String> value : secValues.entrySet()) {
          sb.append(value.getKey());
          sb.append(EQUALS);
          sb.append(value.getValue());
          sb.append(DELIM_LINE);
        }
      }
    }
    return sb.toString();
  }
}
